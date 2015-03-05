import java.util.ArrayList;

public class BinaryTree<T extends Comparable<T>>{
	private class Node{
		private Node left,right,parent;
		private T contents;
	}

	private int size = 0;
	private String result;
	public Node root;

	public BinaryTree(){
		root = new Node();
		root.left = null;
		root.right = null;
	}

	public void add(T c){
		if(size == 0){
			root.contents = c;
			size++;
			return;
		}

		Node current = root;

		while(true){
			if(c.compareTo(current.contents) == 0){
				return;
			}

			if(c.compareTo(current.contents) < 0){
				if(current.left == null){
					current.left = new Node();
					current.left.contents = c;
					size++;
					break;
				}
				else{
					current = current.left;
				}
			}
			else{
				if(current.right == null){
					current.right = new Node();
					current.right.contents = c;
					size++;
					break;
				}
				else{
					current = current.right;
				}
			}
		}
	}

	public boolean contains(T c){
		Node current = root;

		while(current!= null){
			if(c.compareTo(current.contents) < 0) current = current.left;
			else if(c.compareTo(current.contents) > 0) current = current.right;
			else return true;
		}

		return false;
	}

	public int cost(KeyTable<T> keys){
		int treeCost = 0;
		int freq = 1;
		int depth = 1;
		T currentKey;

		ArrayList<T> unique = keys.getUnique();

		for(int i = 0; i < unique.size(); i++){
			currentKey = unique.get(i);
			freq = keys.frequency(currentKey);
			depth = this.depth(currentKey);
			treeCost += freq * depth;
		}

		return treeCost;
	}

	public int depth(T key){
		if( !this.contains(key)) return 0;

		int depth = 1;

		Node current = root;

		while(current!= null){
			if(key.compareTo(current.contents) < 0){
				current = current.left;
				depth++;
			}
			else if(key.compareTo(current.contents) > 0){
				current = current.right;
				depth++;
			}
			else{
				break;
			}
		}

		return depth;
	}

	public String toString(){

		System.out.println("\nBinary Tree printed using In-Order Traversal:\n");

		result = "";

		if(size==0) return "";

		inOrder(root);

		return result;
	}

	public void inOrder(Node current){
		if(current.left != null) inOrder(current.left);
		result += current.contents + " ";
		if(current.right != null) inOrder(current.right);
	}

	public static void main(String[] args){
		ArrayList<String> keys = new ArrayList<String>() ;
		keys.add("C") ;
		keys.add("G") ;
		keys.add("B") ; keys.add("B") ;
		keys.add("F") ; keys.add("F") ; keys.add("F") ;
		keys.add("D") ; keys.add("D") ; keys.add("D") ;
		keys.add("A") ; keys.add("A") ; keys.add("A") ; keys.add("A") ;
		keys.add("E") ; keys.add("E") ; keys.add("E") ; keys.add("E") ; keys.add("E") ;

		BinaryTree<String> bt = new BinaryTree<String>();
		bt.add("C") ;
		bt.add("G") ;
		bt.add("B") ;
		bt.add("F") ;
		bt.add("D") ;
		bt.add("A") ;
		bt.add("E") ;

		System.out.println(bt);
		System.out.println("\nDepth C: " + bt.depth("C"));
		System.out.println("Depth G: " + bt.depth("G"));
		System.out.println("Depth B: " + bt.depth("B"));
		System.out.println("Depth F: " + bt.depth("F"));
		System.out.println("Depth A: " + bt.depth("A"));
		System.out.println("Depth D: " + bt.depth("D"));
		System.out.println("Depth E: " + bt.depth("E"));

		KeyTable<String> table = new KeyTable<String>(keys);
		System.out.println("Cost: " + bt.cost(table) + "\n");
	}
}