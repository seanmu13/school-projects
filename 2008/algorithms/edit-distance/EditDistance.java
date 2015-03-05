import java.util.Arrays;

public class EditDistance{

	//	distance() computes the distance between two strings
	//	if verbose is true, the matrix with the individual distances for each char will printed to the screen

	public static int distance(String string1, String string2, boolean verbose){

		//	Initialization of rows and columns
		//	Rows & columns must be the string size + 1 to account for the empty string at the beginning of each string
		int rows = string1.length() + 1;
		int cols = string2.length() + 1;
		int[][] a = new int[rows][cols];
		int cost = 0;

		// Initalization of the 0th row
		for(int i = 0; i < cols; i++){
			a[0][i] = i;
		}

		// Initalization of the 0th column
		for(int j = 0; j < rows; j++){
			a[j][0] = j;
		}

		//	Finds cost and distance for all character permutations
		for(int m = 1; m < rows; m++){
			for(int n = 1; n < cols; n++){
				cost = cost(string2.charAt(n-1),string1.charAt(m-1));
				a[m][n] = min(a[m-1][n]+1, a[m][n-1]+1, a[m-1][n-1] + cost);
			}
		}

		//	If verbose is true, will print matrix
		if(verbose){
			print(a,string2,string1);
		}

		//	Returns edit distance of string1 and string2
		return a[rows - 1][cols - 1];
	}

	//	Finds and returns the minimum value of a,b,c
	public static int min(int a, int b, int c){
		int[] m = {a,b,c};
		Arrays.sort(m);
		return m[0];
	}

	//	Computes and returns the cost between two characters
	public static int cost(char ch1, char ch2){
		if(ch1 == ch2){
			return 0;
		}

		return 1;
	}

	//	Prints the matrix of the two strings with individual edit distances
	public static void print(int[][] matrix, String s1, String s2){
		System.out.print("    ");

		//	Prints out one of the strings at the top of the matrix aligned with column 1
		for(int i = 0; i < s1.length(); i++){

			if(i == s1.length() - 1){
				System.out.println(s1.charAt(i) + " ");
			}
			else{
				System.out.print(s1.charAt(i) + " ");
			}

		}

		//	Prints out individual eidt distances and the other strings along the left side of the matrix
		for(int k = 0; k < matrix.length; k++){

			if(k == 0){
				System.out.print("  ");
			}

			for(int m = 0; m < matrix[0].length; m++){

				// If the column is 0 and the row is greater than 0 we print a character from the string first
				if(m == 0 && k > 0){
					System.out.print(s2.charAt(k-1) + " ");
				}

				System.out.print(matrix[k][m] + " ");
			}
			System.out.println();
		}

		System.out.println();
		return;
	}
}