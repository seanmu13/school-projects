import java.util.*;
import java.io.*;

public class PermutationGenerator{
	public String word;

    public PermutationGenerator(String aWord){
       word = aWord;
    }

	//------------------------------------
	//	Generates Permuations of the word
	//------------------------------------

    public ArrayList<String> getPermutations(){

		ArrayList<String> result = new ArrayList<String>();

		//-----------------------------------------------------
		//	If string is empty, simply returns the empty string
		//-----------------------------------------------------

		if(word.length() == 0){
			result.add(word);
			return result;
		}

 		//--------------------------------
 		//	Iterates through each character
 		//--------------------------------

		for(int i = 0; i < word.length(); i++){

			//------------------------
			//	Makes a smaller word
			//------------------------

			String shorterWord = word.substring(0, i) + word.substring(i + 1);
			//----------------------------------------------
			// Generate all permutations of the simpler word
			//----------------------------------------------

			PermutationGenerator shorterPermGenerator = new PermutationGenerator(shorterWord);
			ArrayList<String> shorterWordsPerm = shorterPermGenerator.getPermutations();

			//------------------------------------------------
			// Adds removed character to the beginning of perm
			//------------------------------------------------

			for(int k=0;k<shorterWordsPerm.size();k++){
				result.add(word.charAt(i) + shorterWordsPerm.get(k));
			}
		}

		//------------------------
		// Return permutations
		//------------------------

       return result;
	}

	public static void main(String[] args){

	}
 }