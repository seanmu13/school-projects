import java.util.*;

public class Parser
{
    public static ArrayList tokenize(String str)
    {
        ArrayList<ParseTree> list = new ArrayList<ParseTree>();
        int length = 0;

topOfOuterForLoop:

        for(; str.length() != 0; str = str.substring(length))
        {
            for(; str.charAt(0) == ' '; str = str.substring(1));
            length = str.length();

            while(true)
            {
                String partOfString = str.substring(0, length);

                if(Grammar.isGrammarNumber(partOfString))
                {
                    ParseTree tree = new ParseTree("NUMBER");
                    tree.addTree(new ParseTree(partOfString));
                    list.add(tree);
                    continue topOfOuterForLoop;
                }

                if(Grammar.isGrammarVariable(partOfString))
                {
                    ParseTree parsetree1 = new ParseTree("VARIABLE");
                    parsetree1.addTree(new ParseTree(partOfString));
                    list.add(parsetree1);
                    continue topOfOuterForLoop;
                }

                if(Grammar.isGrammarOperator(partOfString))
                {
                    ParseTree parsetree2 = new ParseTree("OPERATOR");
                    parsetree2.addTree(new ParseTree(partOfString));
                    list.add(parsetree2);
                    continue topOfOuterForLoop;
                }

                if(Grammar.isGrammarKeyword(partOfString))
                {
                    ParseTree parsetree3 = new ParseTree(partOfString);
                    list.add(parsetree3);
                    continue topOfOuterForLoop;
                }
                length--;
            }
        }

        return list;
    }

    public static boolean canApplyRule(int i, int j, ArrayList<ParseTree> list)
    {
        if( j + Grammar.numberOfArgs(i) > list.size() )
        {
            return false;
		}

        for(int k = 0; k < Grammar.numberOfArgs(i); k++)
        {
            if( ! Grammar.descriptionOfStructure(i, k).equals(list.get(j + k).value) )
            {
                return false;
			}
		}
        return true;
    }

    public static ArrayList applyRule(int i, int j, ArrayList<ParseTree> list)
    {
        ArrayList<ParseTree> anotherList = new ArrayList<ParseTree>();

        for(int k = 0; k < j; k++)
        {
            anotherList.add(list.get(k));
		}

        ParseTree tree = new ParseTree(Grammar.typeOfStructure(i));

        for(int l = 0; l < Grammar.numberOfArgs(i); l++)
        {
            tree.addTree(list.get(j + l));
		}

        anotherList.add(tree);

        for(int i1 = j + Grammar.numberOfArgs(i); i1 < list.size(); i1++)
        {
            anotherList.add(list.get(i1));
		}

        return anotherList;
    }

    public static boolean isParsed(ArrayList<ParseTree> list, String str)
    {
		if( list.size() == 1 && list.get(0).value.equals(str) )
		{
			return true;
		}

		return false;
    }

    public static ParseTree parse(ArrayList<ParseTree> list, String str)
    {
        Stack stack = new Stack();
        stack.push(list);

        while( ! stack.isEmpty() )
        {
            list = stack.pop();

            if(isParsed(list, str))
            {
                return list.get(0);
			}

            int i = 0;

            while( i < Grammar.numberOfCommands() )
            {
                for(int j = 0; j < list.size(); j++)
                {
                    if(canApplyRule(i, j, list))
                    {
                        stack.push(applyRule(i, j, list));
					}
				}
                i++;
            }
        }
        return null;
    }

    public static ArrayList<ParseTree> applyOneRule(ArrayList<ParseTree> list)
    {
        for(int i = 0; i < Grammar.numberOfCommands(); i++)
        {
            for(int j = 0; j < list.size(); j++)
            {
                if(canApplyRule(i, j, list))
                {
                    return applyRule(i, j, list);
				}
			}
        }

        return null;
    }

    public static ParseTree fastParse(ArrayList<ParseTree> list, String str)
    {
        for(; ! isParsed(list, str); list = applyOneRule(list));
        return list.get(0);
    }
}