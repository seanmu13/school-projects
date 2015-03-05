// CS 0401 Fall 2007-01
// Partial Implementation of MyDB class
// You must complete this class so that test programs Assig4a.java
// and Assig4b.java work as written.  You may not change any of the
// code already provided here, but you may certainly add code as
// necessary.  In particular, you need to implement all of the methods
// of the MyBag interface, plus the toString() method and the
// sortTheData() method.  I recommend trying these methods one at a
// time with a simple test program as you develop your solution.  Only
// try the Assig5a.java and Assig5b.java programs once you are confident
// that your class is correct.

//----------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------Assignment 5----------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------


import java.util.*;

public class MyDB implements MyBag
{
       private Object[] theData;
       private int numItems;

//--------------------
//Constructor for MyDB
//--------------------

       public MyDB(int size)
       {
           numItems = 0;
           theData = new Object[size];
       }

//------------------------------
//Returns false if MyDB is empty
//------------------------------

       public boolean isEmpty()
       {
		   for(int i=0;i<theData.length;i++)
				if(theData[i]!=null)
					return false;

		   return true;
	   }

//------------------------------
//Returns the # of items in MyDB
//------------------------------

	   public int size()
	   {
		   int sz=0;

		   for(int i=0;i<theData.length;i++)
				if(theData[i]!=null)
					sz = sz + 1;

		   return sz;
	   }

//-------------------------------------------------
//Adds an element to already existing items in MyDB
//-------------------------------------------------

	   public void addElement(Object value)
	   {
		   for(int i=0;i<theData.length;i++)
		   {
				if(theData[i]==null)
				{
					theData[i] = value;
					numItems = numItems+1;
					break;
				}
			}

	   }

//-----------------------------------------------
//Returns true if the object is found in the MyDB
//-----------------------------------------------

	   public boolean containsElement(Object value)
	   {

		  	for(int i=0;i<theData.length;i++)
		  	{
		  		if(theData[i]!=null)
		  		{
		  			if(theData[i].equals(value))
		  			{
		  				return true;
					}
				}
			}

		   	return false;
	   }

//-------------------------------------------------------------------------
//If the object is foumd, findElement returns that value back to the caller
//-------------------------------------------------------------------------

	   public Object findElement(Object value)
	   {System.out.println("Valu  valu value" + value);
		  	for(int i=0;i<theData.length;i++)
		  	{
		  		if(theData[i]!=null)
		  		{System.out.println("data data data" + theData[i]);
		  			if(theData[i].equals(value))
		  			{
		  				return theData[i];
					}
				}
			}

		   return null;
	   }

//---------------------------------------
//Removes the specified object from MyDB
//---------------------------------------

	   public void removeElement(Object value)
	   {
		  	for(int i=0;i<theData.length;i++)
		  	{
		  		if(theData[i]!=null)
		  		{
		  			if(theData[i].equals(value))
		  			{
		  			theData[i] = null;
		  			numItems = numItems-1;
					}
				}
			}

		  	return;
	   }

//-----------------------------
//Converts the MyDB to a string
//-----------------------------

	   public String toString()
	   {
		   String bag = new String("");

		  	for(int i=0;i<theData.length;i++)
		  		if(theData[i]!=null)
					bag = bag + theData[i] + "\n";

		   return bag;
	   }

//------------------------------------
//Sorts the MyDB by alphabetical order
//------------------------------------

	   public void sortTheData()
	   {
		   Object[] smList = new Object[numItems];
		   int j = 0;

		   for(int i=0;i<theData.length;i++)
		   		if(theData[i]!=null)
		   		{
		   			smList[j] = theData[i];
		   			j++;
				}

		   Arrays.sort(smList);

		   for(int k=0;k<theData.length;k++)
				{
					if(k<smList.length)
						theData[k] = smList[k];
					else
						theData[k] = null;
				}
	   }


	   // Fill in necessary missing methods here

	   // NOTE: For methods that add to or remove from the MyDB object,
	   // your code should NOT CRASH even if a user attempts to add to
	   // a full array or remove from an empty one.  In these cases you
	   // should respond in a logical way (ex: do not add if MyDB is
	   // full and return null or false if MyDB is empty)

	   // I have provided this method for you, since its implementation
	   // is a bit non-traditional
       public void showThisType(String className)
       {
              try
              {
                   Class C1 = Class.forName(className);
                   for (int i = 0; i < numItems; i++)
                   {
                        if (C1.isInstance(theData[i]))
                        {
                            System.out.println(theData[i]);
                        }
                   }
              }
              catch (Exception e) {}
       }

       public static void main(String[] args)
       {
	   }

}
