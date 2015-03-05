//--------------------------------------------------------------------------------------------------------------------
//-----------------------------------------Assignment 1 - Verification of a VIN---------------------------------------
//--------------------------------------------------------------------------------------------------------------------

public class VIN
	{
	public static void main (String[] args)
		{
			//---------------------------------------------------------------------------------------------
			//Variable declarations, array for number weights, and array for character values
			//---------------------------------------------------------------------------------------------

			String number = args[0], temp = "", country, manu, year;
			int[] weight = {8,7,6,5,4,3,2,10,0,9,8,7,6,5,4,3,2};
			int[] values = {1,2,3,4,5,6,7,8,0,1,2,3,4,5,0,7,0,9,2,3,4,5,6,7,8,9};
			int sum = 0, iNum;

			//---------------------------------------------------
			//Converts all the characters of the VIN to uppercase
			//---------------------------------------------------

			number = number.toUpperCase();

			System.out.println("\nIs " + number + " a valid VIN?\n");

			//----------------------------------------------
			//Error: illegal characters were used in the VIN
			//----------------------------------------------

			if(number.indexOf('I') != -1 || number.indexOf('O') != -1 || number.indexOf('Q') != -1)
				throw new RuntimeException("You Have Entered An Illegal Character(I,O,Q)");

			//----------------------------------------------
			//Error: NOT a 17 character VIN
			//----------------------------------------------

			if(number.length() != 17 && number.length() != 20)
				throw new RuntimeException("Your VIN Does NOT Contain 17 Characters");

			//-----------------------------------------------------------------------------------------------------------------
			//If the VIN possibly contains dashes, i.e. more than 17 characters, the if statement checks for appropriate dashes
			//-----------------------------------------------------------------------------------------------------------------

			if(number.length() == 20)
				{

					//----------------------------------------------
					//Error: NOT a 17 character VIN
					//----------------------------------------------

					if(number.indexOf('-') == -1)
						throw new RuntimeException("Your VIN Does NOT Contain 17 Characters");

					//---------------------------------------------------------------------------------
					//Error: Checks to see if the dashes are placed in the 4th,10th,and 12th characters
					//---------------------------------------------------------------------------------

					else if(number.charAt(3) != '-' || number.charAt(9) != '-' || number.charAt(11) != '-')
						throw new RuntimeException("Your VIN Format Is Incorrect, please enter VIN as xxx-xxxxx-x-xxxxxxxx");

					//---------------------------------------------------------------
					//Error: Checks to see if there are dashes in other places in VIN
					//---------------------------------------------------------------

					for(int i = 0; i<number.length(); i++)
						{
							if(i == 3 || i == 9 || i == 11)
								continue;

							else if(number.charAt(i) == '-')
								throw new RuntimeException("Your VIN Format Is Incorrect, please enter VIN as xxx-xxxxx-x-xxxxxxxx");
						}

					//----------------------------------------------------------------
					//Removes dashes from VIN so it becomes only a 17 character string
					//----------------------------------------------------------------

					for(int j = 0; j<number.length(); j++ )
						{
							if(number.charAt(j) != '-')
								{
									temp = temp + number.charAt(j);
								}
						}

					number = temp;
				}

			//------------------------------------------------------------------
			//If the original VIN contains 17 characters, this checks for dashes
			//------------------------------------------------------------------

			else
				if(number.indexOf('-') != -1)
					throw new RuntimeException("Your VIN Does NOT Contain 17 Characters");

			//-------------------------------------
			//Caclulates weighted sum of the values
			//-------------------------------------

			for(int k = 0; k<number.length(); k++)
				{
					//--------------------------------------------------------
					//Converts the character to an integer based on ASCII code
					//--------------------------------------------------------

					iNum = (int)number.charAt(k);

					//------------------------------------------------------------------------------------------------
					//If the iNum is an alphabetic character,it adds to the sum the weight * the value from the arrays
					//------------------------------------------------------------------------------------------------

					if(iNum >= 65)
						sum = sum + weight[k]*values[iNum-65];

					//--------------------------------------------------------------------------------------------------------
					//If the iNum is an decimal integer, it adds to the sum the weight of the digit * the actual decimal value
					//--------------------------------------------------------------------------------------------------------

					else
						sum = sum + weight[k]*(iNum-48);
				}

			//--------------------------------------------------------------------------------------------------
			//Checks to see whether the weighted sum mod 11 is 10 and if the symbol X is used as the check digit
			//--------------------------------------------------------------------------------------------------

			if(sum%11 != 10 || number.charAt(8) != 'X')
				throw new RuntimeException("Illegal Check Digit: " + number.charAt(8));

			//------------------------------------------------------------
			//Identifies the country in which the vehicle was manufactured
			//------------------------------------------------------------

			if(number.charAt(0) == '1' || number.charAt(0) == '4')
				country = "USA";
			else if (number.charAt(0) == '2')
				country = "Canada";
			else if (number.charAt(0) == '3')
				country = "Mexico";
			else if (number.charAt(0) == 'J')
				country = "Japan";
			else if (number.charAt(0) == 'K')
				country = "Korea";
			else if (number.charAt(0) == 'S')
				country = "England";
			else if (number.charAt(0) == 'W')
				country = "Germany";
			else if (number.charAt(0) == 'Z')
				country = "Italy";
			else
				country = "Country Not Listed";

			//------------------------------------------
			//Identifies the manufacturer of the vehicle
			//------------------------------------------

			if(number.charAt(1) == 'A')
				manu = "Audi or Jaguar";
			else if (number.charAt(1) == 'B')
				manu = "BMW or Dodge";
			else if (number.charAt(1) == '4')
				manu = "Buick";
			else if (number.charAt(1) == '6')
				manu = "Cadillac";
			else if (number.charAt(1) == '1')
				manu = "Chevrolet";
			else if (number.charAt(1) == 'C')
				manu = "Chrysler";
			else if (number.charAt(1) == 'F')
				manu = "Ford";
			else if (number.charAt(1) == '7')
				manu = "GM Canada";
			else if (number.charAt(1) == 'G')
				manu = "General Motors";
			else if (number.charAt(1) == 'H')
				manu = "Honda";
			else if (number.charAt(1) == 'L')
				manu = "Lincoln";
			else if (number.charAt(1) == 'D')
				manu = "Mercedes Benz";
			else if (number.charAt(1) == 'M')
				manu = "Mercury";
			else if (number.charAt(1) == 'N')
				manu = "Nissan";
			else if (number.charAt(1) == '3')
				manu = "Oldsmobile";
			else if (number.charAt(1) == '2' || number.charAt(0) == '5')
				manu = "Pontiac";
			else if (number.charAt(1) == 'P')
				manu = "Plymouth";
			else if (number.charAt(1) == '8')
				manu = "Saturn";
			else if (number.charAt(1) == 'T')
				manu = "Toyota";
			else if (number.charAt(1) == 'V')
				manu = "VW or Volvo";
			else
				manu = "Manufacturer Not Listed";

			//----------------------------------------
			//Identifies the model year of the vehicle
			//----------------------------------------

			if(number.charAt(9) == 'J')
				year = "1988";
			else if (number.charAt(9) == 'K')
				year = "1989";
			else if (number.charAt(9) == 'L')
				year = "1990";
			else if (number.charAt(9) == 'M')
				year = "1991";
			else if (number.charAt(9) == 'N')
				year = "1992";
			else if (number.charAt(9) == 'P')
				year = "1993";
			else if (number.charAt(9) == 'R')
				year = "1994";
			else if (number.charAt(9) == 'S')
				year = "1995";
			else if (number.charAt(9) == 'T')
				year = "1996";
			else if (number.charAt(9) == 'V')
				year = "1997";
			else if (number.charAt(9) == 'W')
				year = "1998";
			else if (number.charAt(9) == 'X')
				year = "1999";
			else if (number.charAt(9) == 'Y')
				year = "2000";
			else if (number.charAt(9) == '1')
				year = "2001";
			else if (number.charAt(9) == '2')
				year = "2002";
			else if (number.charAt(9) == '3')
				year = "2003";
			else if (number.charAt(9) == '4')
				year = "2004";
			else if (number.charAt(9) == '5')
				year = "2005";
			else if (number.charAt(9) == '6')
				year = "2006";
			else if (number.charAt(9) == '7')
				year = "2007";
			else
				year = "Year Not Listed";

			//---------------------------------------------------------------------------------------------------------
			//Displays the weighted sum, the weighted sum mod 11, the manufacturing country/company, and the model year
			//---------------------------------------------------------------------------------------------------------

			System.out.println("Weighted sum = " + sum);
			System.out.println("Weighted sum modulo 11 = " + (sum%11) + "\n");
			System.out.println("Manufactured In: " + country);
			System.out.println("Manufactured By: " + manu);
			System.out.println("Model Year: " + year + "\n");


		}
	}