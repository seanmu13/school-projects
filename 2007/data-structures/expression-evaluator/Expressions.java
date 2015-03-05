import java.math.*;
import java.util.*;

public class Expressions{

	public static void main(String[] args){
	}

//---------------
//Evaluate Method
//---------------

	public static float evaluate(String exp, float xValue){

		int depth = 0;

//--------------------------------------------------------------------
//First checks to see if the expression is a single variable or number
//--------------------------------------------------------------------

		if(exp.equals("x")) return xValue;
		else if(isNumber(exp)) return Float.parseFloat(exp);
		else{

			for(int i=0;i<exp.length();i++){

				if(exp.charAt(i)=='(')	depth++;
				else if(exp.charAt(i)==')')	depth--;

//-----------------------------------------------------------------------------------------------------------------------------
//Finds top most operator if it comes after the one deep parenthesis & evalutates both expressions on each side of the operator
//-----------------------------------------------------------------------------------------------------------------------------

				if(depth==1 && exp.charAt(i)==')' && exp.charAt(i+1)!=')' && !isNumber("" + exp.charAt(i+1)) ){

					switch(exp.charAt(i+1)){
						case '*': return evaluate(exp.substring(1,i+1),xValue) * evaluate(exp.substring(i+2,exp.length()-1),xValue);
						case '/': return evaluate(exp.substring(1,i+1),xValue) / evaluate(exp.substring(i+2,exp.length()-1),xValue);
						case '+': return evaluate(exp.substring(1,i+1),xValue) + evaluate(exp.substring(i+2,exp.length()-1),xValue);
						case '-': return evaluate(exp.substring(1,i+1),xValue) - evaluate(exp.substring(i+2,exp.length()-1),xValue);
						case '^': return (float)Math.pow(evaluate(exp.substring(1,i+1),xValue),evaluate(exp.substring(i+2,exp.length()-1),xValue));
					}
				}

//------------------------------------------------------------------------------------------------------------------------------
//Finds top most operator if it comes before the two deep parenthesis & evalutates both expressions on each side of the operator
//------------------------------------------------------------------------------------------------------------------------------

				else if(depth==2 && exp.charAt(i)==')' && exp.charAt(i-1)!=')' && !isNumber("" + exp.charAt(i-1)) ){

					switch(exp.charAt(i-1)){
						case '*': return evaluate(exp.substring(1,i-1),xValue) * evaluate(exp.substring(i,exp.length()-1),xValue);
						case '/': return evaluate(exp.substring(1,i-1),xValue) / evaluate(exp.substring(i,exp.length()-1),xValue);
						case '+': return evaluate(exp.substring(1,i-1),xValue) + evaluate(exp.substring(i,exp.length()-1),xValue);
						case '-': return evaluate(exp.substring(1,i-1),xValue) - evaluate(exp.substring(i,exp.length()-1),xValue);
						case '^': return (float)Math.pow(evaluate(exp.substring(1,i-1),xValue),evaluate(exp.substring(i,exp.length()-1),xValue));
					}
				}
			}

//------------------------------------------------------------
//Evaluates the expression if it is of the form (a operator b)
//------------------------------------------------------------

			for(int i=0;i<exp.length();i++){

				switch(exp.charAt(i)){
					case '*': return evaluate(exp.substring(1,i),xValue) * evaluate(exp.substring(i+1,exp.length()-1),xValue);
					case '/': return evaluate(exp.substring(1,i),xValue) / evaluate(exp.substring(i+1,exp.length()-1),xValue);
					case '+': return evaluate(exp.substring(1,i),xValue) + evaluate(exp.substring(i+1,exp.length()-1),xValue);
					case '-': return evaluate(exp.substring(1,i),xValue) - evaluate(exp.substring(i+1,exp.length()-1),xValue);
					case '^': return (float)Math.pow(evaluate(exp.substring(1,i),xValue),evaluate(exp.substring(i+1,exp.length()-1),xValue));
				}
			}

			return 0F;
		}
	}

//-------------------------------------------------------
//isNumber method checks to see if expression is a number
//-------------------------------------------------------

	public static boolean isNumber(String expression){
		try { Float.parseFloat(expression) ; return true ; }
	    catch (Exception e) { return false ; }
  }
}