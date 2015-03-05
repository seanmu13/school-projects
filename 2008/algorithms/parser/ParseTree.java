import java.util.*;

public class ParseTree
{
	// ------------------------------------------------------------------------
	// Maintains an ArrayList of ParseTrees to keep tracks of the various trees
	// ------------------------------------------------------------------------

    public String value;
    public ArrayList<ParseTree> listOfTrees;

    public ParseTree(String str)
    {
        value = str;
        listOfTrees = new ArrayList<ParseTree>();
    }

	// ------------------------------------------
	// Adds a parseTree to the ArrayList of trees
	// ------------------------------------------

    public void addTree(ParseTree parsetree)
    {
        listOfTrees.add(parsetree);
    }

	// ------------------------------------
	// Gets the tree specified by the index
	// ------------------------------------

    public ParseTree getTree(int index)
    {
        return listOfTrees.get(index);
    }

	/*
    public String toString()
    {
        String s = value + "\n";

        if( listOfTrees.isEmpty() )
        {
        }
        else
        {
			for(ParseTree tree : listOfTrees)
			{
				s += " " + tree;
			}
        }
        return s + "\n";
    }
    */
}
