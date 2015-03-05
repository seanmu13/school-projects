import java.io.*;

public class Grammar
{
	// --------------------------------------------------------------------------------
	// These are the commands as given by grammar description in the Homework 3 handout
	// --------------------------------------------------------------------------------

  	private static String commands[][] =
  	{
		{"EXPRESSION", "->", "NUMBER"},
		{"EXPRESSION", "->", "VARIABLE"},
		{"EXPRESSION", "->", "(", "EXPRESSION", "OPERATOR", "EXPRESSION", ")"},
		{"COMMAND", "->", "fill", "EXPRESSION", "EXPRESSION", ";"},
		{"COMMAND", "->", "set", "EXPRESSION", "EXPRESSION", ";"},
		{"COMMAND", "->", "if", "EXPRESSION", "then", "BLOCK", ";"},
		{"COMMAND", "->", "while", "EXPRESSION", "do", "BLOCK", ";"},
		{"COMMAND", "->", "for", "EXPRESSION", "EXPRESSION", "EXPRESSION", "do", "BLOCK", ";"},
        {"BLOCK", "->", "end"},
        {"BLOCK", "->", "COMMAND", "BLOCK"}
    };

	// ------------------------------------------------------------------------------------------------------
	// More or less similiar to some java keywords, these must be recognized as different then variable names
	// ------------------------------------------------------------------------------------------------------

    private static String keywords[] = {"(", ")", "if", "fill", "then", "set", "do", "if", "while", "for", ";", "end"};

	//------------------------------------- -------------------------------------
	// Similiar to common java operators that can be parsed by the parser program
	// --------------------------------------------------------------------------

    public static String operators[] = {"+", "-", "*", "/", ">", ">=", "<", "<=", "=="};

	// --------------------------------------------------
	// Returns if the string is a defined operator or not
	// --------------------------------------------------

    public static boolean isGrammarOperator(String str)
    {
        for(String s: operators)
        {
            if( str.equals(s) )
            {
                return true;
			}
		}
        return false;
    }

	// --------------------------------------------------
	// Returns if the string is a defined keyword or not
	// --------------------------------------------------

    public static boolean isGrammarKeyword(String str)
    {
        for(String s: keywords)
        {
            if( str.equals(s) )
            {
                return true;
			}
		}
        return false;
    }

	// -------------------------------------------------------
	// Returns if the string is just a number (integer) or not
	// -------------------------------------------------------

    public static boolean isGrammarNumber(String str)
    {
        for( char c: str.toCharArray() )
        {
			// -----------------------------------------------------------------------
			// The character class is used to tell if the given char is a digit or not.
			// If even one of the characters is a digit, the method returns false
			// -----------------------------------------------------------------------

            if( ! Character.isDigit(c) )
            {
                return false;
			}
		}
        return true;
    }

	// -----------------------------------------------------------
	// Returns whether or not the string is a programming variable
	// First you must check whether or not the the string is a keyword or operator
	// If that test passes, you must verify that each character is not a digit
	// -----------------------------------------------------------

    public static boolean isGrammarVariable(String str)
    {
        if( isGrammarKeyword(str) || isGrammarOperator(str) )
        {
            return false;
		}

        for( char c: str.toCharArray() )
        {
            if( Character.isDigit(c) )
            {
                return false;
			}
		}
        return true;
    }

	// ------------------------------------------------------------------------------
	// Returns the type of structure that is defined at the row in the commands array
	// ------------------------------------------------------------------------------

    public static String typeOfStructure(int row)
    {
        return commands[row][0];
    }

	// ---------------------------------------------
	// Returns the description of grammar expression
	// ---------------------------------------------

    public static String descriptionOfStructure(int row, int col)
    {
        return commands[row][col + 2];
    }

	// ------------------------------------------------
	// Returns the number of arguements in the language
	// ------------------------------------------------

    public static int numberOfArgs(int row)
    {
        return commands[row].length - 2;
    }

	// -------------------------------------------------
	// Returns the size of the command array (# of rows)
	// -------------------------------------------------

    public static int numberOfCommands()
    {
        return commands.length;
    }

	// -----------------------------------------
	// Prints out all of the static arrays above
	// -----------------------------------------

	/*
    public static void print()
    {
        System.out.println("Commands:\n");

        for(int i = 0; i < numberOfCommands(); i++)
        {
            System.out.print("\t");

            for(int j = 0; j < commands[i].length; j++)
            {
                System.out.print(commands[i][j] + " ");
			}
            System.out.print("\n");
        }

        System.out.print("Key Words:\n\t");

        for(String s: keywords)
        {
            System.out.print(s + " ");
		}

        System.out.println("\nOperators:\n\t");

        for(String s: operators)
        {
            System.out.print(s + "");
		}

        System.out.println();
    }
    */
}