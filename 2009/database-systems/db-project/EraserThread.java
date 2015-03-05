/*
Purpose:  Facilitates the secure entry of passwords by not echoing user entry through an erasing method.

NOTE:

This code has been acquired from the java.sun.com tutorials for masking passwords.
The link to the code can be found below:

http://java.sun.com/developer/technicalArticles/Security/pwordmask/

ACCESSED 09-APR-09
*/

import java.io.*;

public class EraserThread implements Runnable
{
	private boolean stop;

	/**
	*@param The prompt displayed to the user
	*/
	public EraserThread(String prompt)
	{
		System.out.print(prompt);
	}

	/**
	* Begin masking...display asterisks (*)
	*/
	public void run ()
	{
		stop = true;
		while (stop)
		{
			System.out.print("\010\040");
		}
		try
		{
			Thread.currentThread().sleep(1);
		}
		catch(InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}

	/**
	* Instruct the thread to stop masking
	*/
	public void stopMasking()
	{
		this.stop = false;
	}
}