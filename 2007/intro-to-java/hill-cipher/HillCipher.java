//--------------------------------------------------------------------------------------------------------------------
//-----------------------------------------Assignment 3 - Hill-Cipher-------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------

import java.io.*;

public class HillCipher
{
	//---------------------------------
	//Declaration of instance variables
	//---------------------------------

	private int numberOfChars;
	private int[] plainText;
	private int[] cipherText;
	private String plainFile;
	private String cipherFile;

	//-----------------------------------------------------
	//HillCipher constructor, finds number of chars in file
	//-----------------------------------------------------

	public HillCipher(String fileName1, String fileName2) throws IOException
		{
			plainFile = fileName1;
			cipherFile = fileName2;
			int ch=0, i=0;

			BufferedReader plain = new BufferedReader(new FileReader(plainFile));

			while(ch!=-1)
			{
				ch = plain.read();
				i = i + 1;
			}

			numberOfChars = i-1;

			plain.close();
		}

	//------------------------------------------------------------------------------------------------------------------
	//readPlainText instance method, assigns each char of file to array of chars, changes unprintable chars to printable
	//------------------------------------------------------------------------------------------------------------------

	public void readPlainText() throws IOException
	{
		BufferedReader inp = new BufferedReader(new FileReader(plainFile));
		plainText = new int[numberOfChars];

		//-------------------------------------------
		//Prints the pre-encrypted file to the screen
		//-------------------------------------------

		System.out.println("\nBefore Encryption ("+ plainFile + ") :\n");

		for(int k=0;k<numberOfChars;k++)
		{
			plainText[k] = inp.read();
			System.out.print((char)plainText[k]);

			if(plainText[k]==169)
				plainText[k] = 127;
			else if(plainText[k]==174)
				plainText[k] = 128;
		}

		inp.close();
	}

	//-------------------------------------------------------------------------------------------------------------------
	//transform instance method, encrypts the text using Hill-Cipher mechanism, stores the encryption in array cipherText
	//-------------------------------------------------------------------------------------------------------------------

	public void transform()
	{
		cipherText = new int[numberOfChars];

		for(int k=0;k<numberOfChars;k++)
		{
			//--------------------------------------------------------------------------------------------------
			//If char is a return character, prints the 13,10 to cipherText, and skips to next char after the 10
			//--------------------------------------------------------------------------------------------------

			if(plainText[k]==13)
			{
				cipherText[k] = 13;
				cipherText[k+1] = 10;
				k = k + 1;
			}

			//-------------------------------------------------------------------
			//If char is the last char, performs Hill-Cipher for single character
			//-------------------------------------------------------------------

			else if (k==numberOfChars-1)
			{
				cipherText[k] = ((plainText[k]-32)*96)%97 + 32;

				if(cipherText[k]==127)
					cipherText[k] = 169;
				else if(cipherText[k]==128)
					cipherText[k] = 174;
			}

			//---------------------------------------------------------------------------------------
			//If a character less than 32 is found, copies it to CipherText without apply Hill-Cipher
			//---------------------------------------------------------------------------------------

			else if (plainText[k]<32)
			{
				cipherText[k] = plainText[k];
			}


			//---------------------------------------------------------------------------
			//If the next char is a 13(return), performs Hill-Cipher for single character
			//---------------------------------------------------------------------------

			else if (plainText[k+1] == 13)
			{
				cipherText[k] = ((plainText[k]-32)*96)%97 + 32;

				if(cipherText[k]==127)
					cipherText[k] = 169;
				else if(cipherText[k]==128)
					cipherText[k] = 174;
			}

			//---------------------------------------------
			//Performs Hill-Cipher for 2 "normal" character
			//---------------------------------------------

			else
			{
				cipherText[k] = (71*(plainText[k]-32) + 2*(plainText[k+1]-32)) % 97 + 32;
				cipherText[k+1] = (2*(plainText[k]-32) + 26*(plainText[k+1]-32)) % 97 + 32;

				if(cipherText[k]==127)
					cipherText[k] = 169;
				else if(cipherText[k+1]==127)
					cipherText[k+1] = 169;
				else if(cipherText[k]==128)
					cipherText[k] = 174;
				else if(cipherText[k+1]==128)
					cipherText[k+1] = 174;

				k = k+1;
			}

		}

	//---------------------------------------
	//Prints the encrypted file to the screen
	//---------------------------------------

		System.out.println("\n\nAfter Encryption ("+ cipherFile + ") : \n");

		for(int k=0;k<numberOfChars;k++)
			System.out.print((char) cipherText[k]);

		System.out.println("\n\n");

	}

	//------------------------------------------------------------------------------
	//writeCipherText instance method, prints the cipherText array to the cipherFile
	//------------------------------------------------------------------------------

	public void writeCipherText() throws IOException
	{
		PrintWriter out = new PrintWriter(new FileOutputStream(cipherFile));

		for(int j=0;j<numberOfChars;j++)
			out.print((char) cipherText[j]);

		out.close();

	}

	public static void main (String[] args)
	{
	}
}