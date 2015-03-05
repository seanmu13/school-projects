/*
Purpose:  Facilitates the secure entry of passwords by not echoing user entry through an erasing method.

NOTE:

This code has been acquired from the java.sun.com tutorials for masking passwords.
The link to the code can be found below:

http://java.sun.com/developer/technicalArticles/Security/pwordmask/

ACCESSED 09-APR-09
*/

import java.io.*;

public class PasswordField
{
	/**
	*@param prompt The prompt to display to the user
	*@return The password as entered by the user
	*/

	public static String readPassword (String prompt)
	{
		EraserThread et = new EraserThread(prompt);
		Thread mask = new Thread(et);
		mask.start();

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String password = "";

		try
		{
			password = in.readLine();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

		// stop masking
		et.stopMasking();
		// return the password entered by the user
		return password;
	}
}