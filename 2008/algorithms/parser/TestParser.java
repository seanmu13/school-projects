/// To parse a command, first tokenize it, then parse it into a tree.  The
/// tokenize takes the command to be tokenized.  Note how it deals with
/// variables and numbers.  The parser takes two arguments: the tokenized
/// command and the category to parse it into (generally "COMMAND").

public class TestParser
{
	public static void main(String[] args)
	{
		System.out.println( Parser.tokenize("for x 1 columns do fill x 10 ; end ;") ) ;
		System.out.println( Parser.fastParse( Parser.tokenize("for x 1 columns do fill x 10 ; end ;"), "COMMAND") ) ;
	}
}
