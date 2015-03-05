//--------------------------------------------------------------------------------------------------------------------
//-----------------------------------------Assignment 2 - NInALine----------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------

import java.util.*;

public class NInALine
{

	public static void main(String[] args)
	{
		Scanner kb = new Scanner(System.in);

		int Nval,col, height, width;
		char player = 'X';
		boolean anotherMove = true;

		System.out.println("");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("---------------------Welcome to the N-In-A-Line Program---------------------");
		System.out.println("----------------------------------------------------------------------------");

		//----------------------------------------------------------
		//User enters N, the width, and the height of the game board
		//----------------------------------------------------------

		do
		{
			System.out.println("");
			System.out.print("Please enter the value of N (between 4 and 15 inclusive):  ");
			Nval = kb.nextInt();

		}while(Nval<4 || Nval>15);

		do
		{
			System.out.println("\nPlease enter the height and width of the");
			System.out.print("board (between " + Nval + " and 15 inclusive):  ");
			height = kb.nextInt();
			width = kb.nextInt();

		}while(height<Nval || height>15 || width<Nval || width>15);

		//----------------------------------------------
		//Creates new board object using N, height,width
		//----------------------------------------------

		Board myBoard = new Board(Nval,height,width);

		//----------------
		//Prints the board
		//----------------

		myBoard.print();

		do
		{
			//----------------------------------
			//Checks to see if the board is full
			//----------------------------------

			if(myBoard.isFull())
			{
				System.out.println("\n\nThe Board Is Full: Draw");
				break;
			}

			//-----------------------------------------
			//Asks user to pick a column for their move
			//-----------------------------------------

			do
			{
				System.out.print("\n\nChoose a column for your move(0-" + (width-1) + ") player " + player + ":  ");
				col = kb.nextInt();

			}while(col<0 || col>=width || !myBoard.allowsMove(col));

			//---------------------------------------------
			//Adds the users move to board if it is allowed
			//---------------------------------------------

			myBoard.addMove(col,player);

			//----------------
			//Prints the board
			//----------------

			myBoard.print();

			//---------------------------------------------------------------
			//Checks to see if user wins, and if a win occurs, exits the loop
			//---------------------------------------------------------------

			if(myBoard.winsFor(player))
			{
				System.out.println("\n\nCongratulations, " + player + " has won the game!");
				break;
			}

			if(player == 'X')
				player = 'O';
			else
				player = 'X';

		//----------------------------------------
		//Runs the loop if another move is allowed
		//----------------------------------------

		}while(anotherMove);

		System.out.println("\n");
	}

}