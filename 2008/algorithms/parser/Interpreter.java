public class Interpreter
{
    public static int evaluate(ParseTree parsetree, Variables vars)
    {
        if(parsetree.listOfTrees.size() == 1 && parsetree.getTree(0).value.equals("NUMBER"))
        {
            return Integer.parseInt(parsetree.getTree(0).getTree(0).value);
		}

        if(parsetree.listOfTrees.size() == 1 && parsetree.getTree(0).value.equals("VARIABLE"))
        {
            return vars.getStoreValue(parsetree.getTree(0).getTree(0).value);
		}

        if(parsetree.listOfTrees.size() == 5 && parsetree.getTree(2).value.equals("OPERATOR"))
        {
            int operand1 = evaluate(parsetree.getTree(1), vars);
            int operand2 = evaluate(parsetree.getTree(3), vars);
            String s = parsetree.getTree(2).getTree(0).value;

            if(s.equals("+"))
            {
                return operand1 + operand2;
			}

            if(s.equals("-"))
            {
                return operand1 - operand2;
			}

            if(s.equals("*"))
            {
                return operand1 * operand2;
			}

            if(s.equals("/"))
            {
                return operand1 / operand2;
			}

            if(s.equals("=="))
            {
				if( operand1 == operand2)
				{
					return 1;
				}

				return 0;
			}

            if(s.equals("<"))
            {
				if( operand1 < operand2)
				{
					return 1;
				}

				return 0;
			}

            if(s.equals("<="))
            {
				if( operand1 <= operand2)
				{
					return 1;
				}

				return 0;
			}

            if(s.equals(">"))
            {
				if( operand1 > operand2)
				{
					return 1;
				}

				return 0;
			}

            if(s.equals(">="))
            {
				if( operand1 >= operand2)
				{
					return 1;
				}

				return 0;
			}
            else
            {
                return 0;
			}
        }
        else
        {
            return 0;
        }
    }

    public static void executeBlock(ParseTree parsetree, Variables variables, Sketch sketch)
    {
        if(parsetree.listOfTrees.size() == 1)
        {
            return;
        }
        else
        {
            execute(parsetree.getTree(0), variables, sketch);
            executeBlock(parsetree.getTree(1), variables, sketch);
            return;
        }
    }

    public static void execute(ParseTree parsetree, Variables variables, Sketch sketch)
    {
        if(parsetree.getTree(0).value.equals("fill"))
        {
            sketch.fill(evaluate(parsetree.getTree(1), variables), evaluate(parsetree.getTree(2), variables));
		}
        else
        {
			if(parsetree.getTree(0).value.equals("set"))
			{
				variables.setStoreValue(parsetree.getTree(1).getTree(0).getTree(0).value, evaluate(parsetree.getTree(2), variables));
			}
			else
			{
				if(parsetree.getTree(0).value.equals("if"))
				{
					if(evaluate(parsetree.getTree(1), variables) != 0)
					{
						executeBlock(parsetree.getTree(3), variables, sketch);
					}
				}
				else
				{
					if(parsetree.getTree(0).value.equals("while"))
					{
						for(; evaluate(parsetree.getTree(1), variables) != 0; executeBlock(parsetree.getTree(3), variables, sketch));
					}
					else
					{
						if(parsetree.getTree(0).value.equals("for"))
						{
							variables.setStoreValue(parsetree.getTree(1).getTree(0).getTree(0).value, evaluate(parsetree.getTree(2), variables));
							for(; evaluate(parsetree.getTree(1), variables) < evaluate(parsetree.getTree(3), variables); variables.increasePlusPlus(parsetree.getTree(1).getTree(0).getTree(0).value))
							{
								executeBlock(parsetree.getTree(5), variables, sketch);
							}

						}
					}
				}
			}
		}
    }
}