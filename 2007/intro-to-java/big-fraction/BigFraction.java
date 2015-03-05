//-----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------Assignment 4 - BigFraction--------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------

import java.math.*;

public class BigFraction implements Comparable{

	//--------------------------------
	//Declaration of private variables
	//--------------------------------

	private BigInteger numerator;
	private BigInteger denominator;

	//---------------------------
	//Constructor for BigFraction
	//---------------------------

	public BigFraction(int n, int d){

			BigInteger n1 = new BigInteger("" + n);
			BigInteger d1 = new BigInteger("" + d);
			numerator = n1;
			denominator = d1;
	}

	//---------------------------
	//Constructor for BigFraction
	//---------------------------

	public BigFraction(String n, String d){

		BigInteger n1 = new BigInteger(n);
		BigInteger d1 = new BigInteger(d);

		numerator = n1;
		denominator = d1;
	}

	//---------------------------
	//Constructor for BigFraction
	//---------------------------

	public BigFraction(BigInteger n, BigInteger d){

		numerator = n;
		denominator = d;
	}

	//-------------------------------------
	//Accessor Method to retrieve numerator
	//-------------------------------------

	public BigInteger numerator(){

		return numerator;
	}

	//---------------------------------------
	//Accessor Method to retrieve denominator
	//---------------------------------------

	public BigInteger denominator(){

		return denominator;
	}

	//-----------------------------------------------------------
	//Reduce method - reduces the BigFraction to its lowest terms
	//-----------------------------------------------------------

	private void reduce(){

		BigInteger neg1 = new BigInteger("" + -1);

		if(this.numerator.doubleValue()<0 && this.denominator.doubleValue()<0){
			this.numerator = this.numerator.multiply(neg1);
			this.denominator = this.denominator.multiply(neg1);
		}
		else if(this.numerator.doubleValue()==0 && this.denominator.doubleValue()!=0){
			this.numerator = new BigInteger("" + 0);
			this.denominator = new BigInteger("" + 1);
		}

		BigInteger dv = this.numerator.gcd(denominator);

		this.numerator = this.numerator.divide(dv);
		this.denominator = this.denominator.divide(dv);
	}

	//------------------------------
	//Adds two BigFractions together
	//------------------------------

	public BigFraction add(BigFraction other){

		BigFraction result;

		this.reduce();
		other.reduce();

		result = new BigFraction(this.numerator.multiply(other.denominator).add(other.numerator.multiply(this.denominator)),this.denominator.multiply(other.denominator));

		result.reduce();

		return result;
	}

	//--------------------------
	//Subtracts two BigFractions
	//--------------------------

	public BigFraction subtract(BigFraction other){

		BigFraction result;

		this.reduce();
		other.reduce();

		result = new BigFraction(this.numerator.multiply(other.denominator).subtract(other.numerator.multiply(this.denominator)),this.denominator.multiply(other.denominator));

		result.reduce();

		return result;
	}

	//------------------------------------
	//Multiplies two BigFractions together
	//------------------------------------

	public BigFraction multiply(BigFraction other){

		BigFraction result;

		this.reduce();
		other.reduce();

		result = new BigFraction(this.numerator.multiply(other.numerator),this.denominator.multiply(other.denominator));

		result.reduce();

		return result;
	}

	//-------------------------------------------------
	//Divides this BigFraction by the other BigFraction
	//-------------------------------------------------

	public BigFraction divide(BigFraction other){

		BigFraction result;

		this.reduce();
		other.reduce();

		result = new BigFraction(this.numerator.multiply(other.denominator),this.denominator.multiply(other.numerator));

		result.reduce();

		return result;
	}

	//---------------------------------------------
	//Raises the BigFraction to the power specified
	//---------------------------------------------

	public BigFraction pow(int exponent){

		BigFraction result;

		this.reduce();

		BigInteger num = this.numerator,den = this.denominator,orignum = this.numerator,origden = this.denominator;

		if(exponent==0){

			num = new BigInteger("" + 1);
			den = new BigInteger("" + 1);

			result = new BigFraction(num,den);

			return result;
		}
		else if(exponent<0){


			num = this.denominator;
			den = this.numerator;
			orignum = this.denominator;
			origden = this.numerator;
		}

		for(int i=1;i<Math.abs(exponent);i++){
			num = orignum.multiply(num);
			den = origden.multiply(den);
		}

		result = new BigFraction(num,den);

		result.reduce();

		return result;
	}

	//-----------------------------------------
	//Gives the double value of the BigFraction
	//-----------------------------------------

	public double doubleValue(){

		return this.numerator.doubleValue()/this.denominator.doubleValue();
	}

	//--------------------------------------------------------
	//Defines what a BigFraction is when it is to be displayed
	//--------------------------------------------------------

	public String toString(){

		String num,den, result = new String(""), digString = new String("");
		int digits;

		this.reduce();

		num = new String(this.numerator + "");
		den = new String(this.denominator + "");

		if(this.denominator.doubleValue()==0) return "" + this.doubleValue();

		if(num.length()>=den.length())
			digits = num.length();
		else
			digits = den.length();

		for(int i=0;i<digits;i++){
			digString = digString + "- ";
		}

		result = "\n" + num + "\n" + digString + "\n" + den + "\n";

		return result;
	}

	//--------------------------------------------------------------------------
	//Finds the quotient for this numerator/denominator as well as the remainder
	//--------------------------------------------------------------------------

	public BigInteger[] divideAndRemainder(){

		this.reduce();

		BigInteger num = this.numerator;
		BigInteger den = this.denominator;

		BigInteger[] result = new BigInteger[2];

		try{
			result = num.divideAndRemainder(den);
		}
		catch(ArithmeticException e)
		{
			System.out.println("\nCannot Divide By Zero: " + this.numerator + "/" + this.denominator);
		}

		return result;
	}

	//---------------------------------------------------------------
	//Implements the comparable interface to compare two BigFractions
	//---------------------------------------------------------------

	public int compareTo(Object other){

		double first = this.doubleValue();
		double second = ((BigFraction) other).doubleValue();

		if(second>first) return -1;
		if(first==second) return 0;

		return 1;
	}

	//--------------------------------------------------------
	//Main function used as driver to test BigFraction methods
	//--------------------------------------------------------

	public static void main (String[] args){

		BigFraction fir = new BigFraction(-123,-8);
		BigFraction sec = new BigFraction(195,133446);

		BigInteger[] low1 = new BigInteger[2];
		low1 = fir.divideAndRemainder();

		BigInteger[] low2 = new BigInteger[2];
		low2 = sec.divideAndRemainder();

		System.out.println("\n1st Fraction:  " + fir);
		System.out.println("2nd Fraction:  " + sec);
		System.out.println("1st + 2nd:  " + fir.add(sec));
		System.out.println("1st - 2nd:  " + fir.subtract(sec));
		System.out.println("1st * 2nd:  " + fir.multiply(sec));
		System.out.println("1st / 2nd:  " + fir.divide(sec));
		System.out.println("DoubleVal 1:  " + fir.doubleValue());
		System.out.println("DoubleVal 2:  " + sec.doubleValue());
		System.out.println("Quotient 1:  " + low1[0]);
		System.out.println("Remainder 1: " + low1[1]);
		System.out.println("Quotient 2:  " + low2[0]);
		System.out.println("Remainder 2: " + low2[1]);

		if(fir.compareTo(sec)==1) System.out.println("First > Second" + "\n");
		else if(fir.compareTo(sec)==-1) System.out.println("First < Second" + "\n");
		else System.out.println("First = Second" + "\n");
	}

}