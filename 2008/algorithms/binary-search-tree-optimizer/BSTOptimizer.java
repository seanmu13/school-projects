import java.util.ArrayList;
import java.util.Collections;

public class BSTOptimizer {

	public static int countCalls = 0;
	public static boolean MEMOIZING = false;
	public static Object[][] trees;
	public static int[][] costs;

	public static <T extends Comparable<T>> BinaryTree optimize(ArrayList<T> keys){
		KeyTable<T> table = new KeyTable<T>(keys);
		ArrayList<T> unique = table.getUnique();
		Collections.sort(unique);

		int n = table.size();

		trees = new Object[n][n];
		costs = new int[n][n];

		for(int r=0; r<n;r++){
			for(int s=0; s<n;s++){
				costs[r][s] = -1;
			}
		}

		int j = 0 ;

		for(int i=0; i < n; i++){
			costs[i][j] = table.frequency(unique.get(i));
			if(i > 0){
				costs[i][i-1] = 0;
			}

			j++;
		}


		if(MEMOIZING){
			return optimize(unique,table,true);
		}
		else{
			return optimize(unique,table,false);
		}

	}

	public static <T extends Comparable<T>> BinaryTree optimize(ArrayList<T> list, KeyTable<T> kt, boolean mem){
		int size = list.size();

		if(mem && costs[size][size] != -1){
			return (BinaryTree<T>) trees[size][size];
		}

		if(size == 1){
			BinaryTree<T> bt = new BinaryTree<T>();
			bt.add(list.get(0));
			return bt;
		}

		countCalls += 1;
		return optimize(list,kt,mem);
	}


	public static void main(String[] args) {
	}
}

/// End-of-File
