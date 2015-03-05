//--------------------------------------------------------------------------------------------------------------------
//-----------------------------------------Assignment 3 - Hill-Cipher-------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------


import java.io.*;
import java.util.*;

public class Encoding
{
	public static void main(String[] args) throws IOException
	{
		Scanner kb = new Scanner(System.in);
		String input,output;

		System.out.println("\n----------------------------------------------------");
		System.out.println("--------Assignment 3: Hill-Cipher Encryption--------");
		System.out.println("----------------------------------------------------");

		//--------------------------------------------
		//User enters the name of file to be encrypted
		//--------------------------------------------

		System.out.print("\nPlease enter the name of the file to be encrypted:  ");
		input = kb.next();

		//------------------------------------------------------------
		//User enters the name of file where encryption will be stored
		//------------------------------------------------------------

		System.out.print("\nPlease enter the name of the file to store encrypted text:  ");
		output = kb.next();

		//-----------------------------------------------------------------------------------------------
		//Creates new HillCipher object, reads in the text file, encrypts it, and writes it to a new file
		//-----------------------------------------------------------------------------------------------

		HillCipher myCipher = new HillCipher(input,output);
		myCipher.readPlainText();
		myCipher.transform();
		myCipher.writeCipherText();
	}
}