// CS 401 Fall 2007-01
// Second test program for Assignment 5.  This program is
// another test of the MyDB class to ensure that it works
// with any Comparable types.  For full credit, this program must
// run correctly exactly as is and produce output similar to that
// shown in the test run.

import java.io.*;  // need for keyboard input

public class Assig5b
{
	public static void main(String [] args) throws IOException
	{
		BufferedReader indata =
			new BufferedReader(new InputStreamReader(System.in));
		String sopt;
		int nopt;

		MyBag stuff = new MyDB(15);

		String item;

		item = new String("Steelers"); stuff.addElement(item);
		item = new String("Eagles"); stuff.addElement(item);
		item = new String("Browns"); stuff.addElement(item);
		item = new String("Bengals"); stuff.addElement(item);
		item = new String("Patriots"); stuff.addElement(item);
		item = new String("Ravens"); stuff.addElement(item);

		System.out.println(stuff);
		((MyDB)stuff).sortTheData();  // Since sortTheData is not part of the
		                              // MyBag interface, we must case the
		                              // reference to MyDB before calling it.
		System.out.println(stuff);

		item = new String("Eagles");
		stuff.removeElement(item);

		System.out.println(stuff);

		item = (String) stuff.findElement("Vikings");
		if (item != null)
			System.out.println(item + " was found");
		else
			System.out.println("Vikings was not found");

		item = (String) stuff.findElement("Steelers");
		if (item != null)
			System.out.println(item + " was found");
		else
			System.out.println("Steelers was not found");
		System.out.println();

		String [] tests = {"Colts", "Panthers", "Steelers", "Bengals", "Rams"};
		for (int i = 0; i < tests.length; i++)
		{
			if (stuff.containsElement(tests[i]))
				System.out.println(tests[i] + " was found");
			else
				System.out.println(tests[i] + " was not found");
		}
		System.out.println();

		int num = stuff.size();
		System.out.println("The list has " + num + " items");

		boolean empty = stuff.isEmpty();
		if (empty)
			System.out.println("The list is empty");
		else
			System.out.println("The list is not empty");
	}
}
