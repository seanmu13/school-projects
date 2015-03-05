import java.util.*;
import java.io.*;

public class Wordgram{

	public static void main(String[] args) throws IOException{

		Scanner keyboard = new Scanner(System.in);
		BufferedReader BR;
		String filename = new String(""),wordgram;
		ArrayList<String> list = new ArrayList<String>();

		boolean cont1 = true,cont2 = true;

		System.out.println("\n------Welcome to the Wordgram program.  Exit with EXIT------\n");

		//---------------------------------------------------------------------
		//	Gets the data from the words.txt file and puts them in an arraylist
		//---------------------------------------------------------------------

		while(cont1){
			try{
				System.out.print("Enter the name of the Words File: ");

				filename = new String("words.txt");
				//= keyboard.nextLine();

				if(filename.equals("EXIT") || filename.equals("exit")){
					cont1 = false;
					cont2 = false;
				}

				list = new ArrayList<String>();
				BR = new BufferedReader(new FileReader(filename));

				list.add(BR.readLine());

				int i=0;

				while(list.get(i) != null){
					list.add(BR.readLine());
					i++;
				}

				list.remove(list.size()-1);

				BR.close();
				cont1 = false;
			}

			catch(FileNotFoundException e){
				System.out.println("\n***File Not Found Error***\n");
			}
			catch(IOException e){
				System.out.println("\n***Error Reading Data From " + filename + "***\n");
			}
		}

		//------------------------------------------------------------------------------------
		//	Builds a MyHashTable containing prefixes and the words associated with that prefix
		//------------------------------------------------------------------------------------

		MyHashTable prefixes = new MyHashTable();
		String putIn;

		for(int i=0;i<list.size();i++){
			if(list.get(i).length() >= 6)	putIn = list.get(i).substring(0,5);

			else	putIn = list.get(i);
			prefixes.put(putIn,list.get(i));
		}

		//----------------------------------------------------------------------------------------
		//	Gets wordgram from keyboard, generates permutations, and finds matches of the wordgram
		//----------------------------------------------------------------------------------------

		while(cont2){
			ArrayList<String> matches = new ArrayList<String>();

			//-------------------------
			//	User enter the wordgram
			//-------------------------

			System.out.print("\nEnter Wordgram: ");
			wordgram = keyboard.nextLine();

			if(wordgram.equals("EXIT") || wordgram.equals("exit")) break;

			//--------------------------------
			//	Gets permutations for wordgram
			//--------------------------------

			PermutationGenerator generator = new PermutationGenerator(wordgram);
			ArrayList<String> perm = generator.getPermutations();
			ArrayList<String> newPerm = new ArrayList<String>();

			for(int z=0;z<perm.size();z++){
				if(perm.get(z).length()>=6){
					if(prefixes.get(perm.get(z).substring(0,5))!=null){
						newPerm.add(perm.get(z));
					}
				}
				else if(prefixes.get(perm.get(z))!=null){
					newPerm.add(perm.get(z));
				}
			}

			perm = newPerm;

			//----------------------------------------
			//	Tries to find a match for the wordgram
			//----------------------------------------

			for(int j=0;j<list.size();j++){
				for(int k=0;k<perm.size();k++){
					if(perm.get(k).equals(list.get(j))  && !matches.contains(perm.get(k))){
						matches.add(perm.get(k));
					}
				}
			}

			System.out.println("Matches: " + matches);
		}

		System.out.println("\n-----You have exited the program-----\n");
	}
}