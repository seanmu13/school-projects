// CS 0401 Fall 2007-01
// First test program for Assignment 5.  This program tests:
//	  1) The required class hierarchy of the Recording superclass as specified
//		 in the assignment sheet
//	  2) The MyBag interface and MyDB implementation of the interface
// To get full credit for this assignment this program must run without
// any alterations.  If you must alter this file to get your program to run,
// clearly specify any changes in your Assignment Information Sheet.  You will
// lose some credit, but you can still get partial credit for the parts that
// you have correct.
//
// Read this program VERY CAREFULLY to see the methods required in each of the
// classes in your Recording hierarchy.  Note especially:
//		-- The constructors and their parameters
//		-- Other methods and how they are called
//		-- How references are used to access the objects and how they are cast
//		   when necessary
// See additional comments below for more information and help.

import java.io.*;  // need for keyboard input

public class Assig5a
{
	public static void main(String [] args) throws IOException
	{
		// A BufferedReader is a common way of reading text data from
		// the standard input stream.  We will discuss this more in lecture.
		BufferedReader indata =
			new BufferedReader(new InputStreamReader(System.in));
		String sopt;
		int nopt;

		MyBag stuff = new MyDB(10);
		Recording newItem;

		initData(stuff);

		boolean done = false;
		while (!done)
		{
			String options = new String(
				"\nPlease choose an option:\n" +
				"1) Print Out The Data\n" +
				"2) Sort The Data\n" +
				"3) Add a New Item\n" +
				"4) Delete an Item\n" +
				"5) Find and Print an Item\n" +
				"6) Print All Items of a Certain Type\n" +
				"7) (or any other number) Quit\n");
			System.out.println(options);
			sopt = indata.readLine();
			nopt = Integer.parseInt(sopt);
			switch (nopt)
			{
				// toString method needed for MyDB class.  Note: Even though
				// toString() is not formally listed as part of the
				// MyBag interface, it will still work here since it is
				// defined originally in class Object
				case 1:
					{
					System.out.println(stuff);  break;
				}
				// sortTheData() is NOT part of MyBag or Object, so we must
				// cast to MyDB to call it here
				case 2:
					{
					((MyDB)stuff).sortTheData();  break;
				}
				case 3:
					{
					newItem = readNewObject(indata);
					stuff.addElement(newItem);
					System.out.println(newItem + "was ADDED to the DB");
					break;
				}
				case 4:
					{
					newItem = getRecording(indata);

					Recording oldItem =
						(Recording) stuff.findElement(newItem);
					if (oldItem != null)
					{
						stuff.removeElement(oldItem);
						System.out.println(oldItem + " HAS BEEN DELETED");
					}
					else
						System.out.println(newItem + " was NOT FOUND");
					break;
				}
				case 5:
					{
					newItem = getRecording(indata);
					Recording oldItem =
						(Recording) stuff.findElement(newItem);
					if (oldItem != null)
					{
						System.out.println(oldItem + "IS PRESENT");
					}
					else
						System.out.println(newItem + " was NOT FOUND");
					break;
				}
				case 6:
					{
					System.out.println("Enter Class Type:");
					sopt = indata.readLine();
					((MyDB)stuff).showThisType(sopt);
					break;
				}
				default:
					{
					System.out.println("GoodBye!");
					done = true;  break;
				}
			}
		}

	}

	public static Recording getRecording(BufferedReader BR)
		throws IOException
	{
		Recording R;
		String choice, subch;

		System.out.println("Title?");
		String title = BR.readLine();
		System.out.println("Please select:\n1)Audio\n2)Video\n");
		choice = BR.readLine();
		if (choice.equals("1"))
		{
			System.out.println("Artist?");
			String art = BR.readLine();
			System.out.println("Please select:\na)CD\nb)Tape\n");
			subch = BR.readLine();
			if (subch.equals("a"))
				R = new CD(title, 0, 0);
			else
				R = new AudioTape(title, 0, 0);
			((Audio)R).setArtist(art);
		}
		else
		{
			System.out.println("Director?");
			String dir = BR.readLine();
			System.out.println("Please select:\na)DVD\nb)Tape\n");
			subch = BR.readLine();
			if (subch.equals("a"))
				R = new DVD(title, 0, 0);
			else
				R = new VideoTape(title, 0, 0);
			((Video)R).setDirector(dir);
		}
		return R;
	}


	// Carefully note how each of these objects is created.  Note especially
	// the constructors and mutators used here, so you can provide them in
	// your classes.  Also note how the reference is cast before some of the
	// method calls -- this indicates that the method called is not part of
	// the superclass and thus the reference must be cast into a subclass.
	public static void initData(MyBag stuff)
	{
		Recording currItem =
			new CD("Little Earthquakes", 57, 7);
		((Audio)currItem).setArtist("Tori Amos");
		stuff.addElement(currItem);
		currItem =
			new DVD("Princess Bride, The", 98, 0);
		((Video)currItem).setDirector("Rob Reiner");
		stuff.addElement(currItem);
		currItem =
			new CD("No Need to Argue", 51, 23);
		((Audio)currItem).setArtist("The Cranberries");
		stuff.addElement(currItem);
		currItem =
			new VideoTape("King Kong", 103, 0);
		((Video)currItem).setDirector("Merian Cooper");
		stuff.addElement(currItem);
		currItem =
			new AudioTape("Los Angeles", 37, 45);
		((Audio)currItem).setArtist("X");
		stuff.addElement(currItem);

		// Note the example below and look at the sample output.
		// Clearly, the two movies are not considered to be
		// equal, even though they have the same title.  Thus,
		// the equals() method for the Video class should take into
		// account both the title and the director.  Similarly, the
		// equals() method for the Audio class should take into
		// account both the title and the artist.
		currItem =
			new VideoTape("King Kong", 134, 0);
		((Video)currItem).setDirector("John Guillermin");
		if (stuff.containsElement(currItem))
			System.out.println(currItem + " is found");
		else
			System.out.println(currItem + " is not found");
		stuff.addElement(currItem);
		if (stuff.containsElement(currItem))
			System.out.println(currItem + " is found");
		else
			System.out.println(currItem + " is not found");
	}

	// Again note how the objects are being created and mutated
	// here so you can create the required classes correctly.
	public static Recording readNewObject(BufferedReader BR)
		throws IOException
	{
		Recording R;
		String choice, subch;

		System.out.println("Title of Recording?");
		String title = BR.readLine();
		System.out.println("Length in Minutes?");
		int min = Integer.parseInt(BR.readLine());
		System.out.println("Extra Seconds?");
		double sec = Double.parseDouble(BR.readLine());
		System.out.println("Please select:\n1)Audio\n2)Video\n");
		choice = BR.readLine();
		if (choice.equals("1"))
		{
			System.out.println("Artist?");
			String art = BR.readLine();
			System.out.println("Please select:\na)CD\nb)Tape\n");
			subch = BR.readLine();
			if (subch.equals("a"))
				R = new CD(title, min, sec);
			else
				R = new AudioTape(title, min, sec);
			((Audio)R).setArtist(art);
		}
		else
		{
			System.out.println("Director?");
			String dir = BR.readLine();
			System.out.println("Please select:\na)DVD\nb)Tape\n");
			subch = BR.readLine();
			if (subch.equals("a"))
				R = new DVD(title, min, sec);
			else
				R = new VideoTape(title, min, sec);
			((Video)R).setDirector(dir);
		}
		return R;
	}

}
