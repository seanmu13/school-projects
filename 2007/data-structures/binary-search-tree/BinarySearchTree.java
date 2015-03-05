public class BinarySearchTree{

	private class Node{
		private Node left,right,parent;
		private Object contents;
	}

	private int size = 0;
	public Node root;

	public BinarySearchTree(){
		root = new Node();
		root.left = null;
		root.right = null;
	}

	public void add(Comparable c){
		if(size==0){
			root.contents = c;
			size++;
		}
		else{
			Node current = root;
			boolean cont = true;

			while(cont){
				if(c.compareTo(current.contents)<0){
					if(current.left==null){
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
					if(current.right==null){
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
	}

	public boolean contains(Comparable c){
		Node current = root;

		while(current!=null){
			if(c.compareTo(current.contents)<0) current=current.left;
			else if(c.compareTo(current.contents)>0) current=current.right;
			else return true;
		}
		return false;
	}

	public void remove(Comparable c){
		if(size==0) return;
		if(this.contains(c)==false) return;
		else{
			Node current = root;
			current.parent = null;
			Node removed = null;
			boolean notFound = true;
			Node temp;

			while(notFound){
				if(c.compareTo(current.contents)<0 && current.left!=null){
					current.left.parent = current;
					current=current.left;
				}
				else if(c.compareTo(current.contents)>0 && current.right!=null){
					current.right.parent = current;
					current=current.right;
				}
				else{
					if(current.left == null && current.right == null){
						current.contents = null;
						return;
					}
					else if(current.left == null){
						current = current.right;
					}
					else if(current.right == null){
						removed = current;
						current = current.left;
						current.parent = removed;
						removed.contents = current.contents;
						while(current.right!=null){
							temp = current;
							removed.contents = current.contents;
							current = current.right;
							current.parent = temp;
						}
						if(current.left!=null){
							current = current.left;
							return;
						}

						current.contents = null;
					}
					else{
						removed = current;
						current = current.left;
						current.parent = removed;

						while(current.right!=null){
							temp = current;
							removed.contents = current.contents;
							current = current.right;
							current.parent = temp;
						}
						if(current.left!=null){
							current = current.left;
							return;
						}

						current.contents = null;
					}
				}
			}
		}
	}

	public String toString(){
		result = "";

		if(size==0) return "";

		inOrder(root);

		return result;
	}

	public void inOrder(Node curr){
		if(curr.left != null) inOrder(curr.left);
		result += curr.contents + " ";
		if(curr.right != null) inOrder(curr.right);
	}

	public String result;

	public static void main(String[] args){
		BinarySearchTree bst = new BinarySearchTree();
		bst.add("d");
		System.out.println(bst);
		bst.add("f");
		System.out.println(bst);
		bst.add("b");
		System.out.println(bst);
		bst.add("c");
		System.out.println(bst);
		bst.add("a");
		System.out.println(bst);
		bst.add("e");
		System.out.println(bst);
		bst.add("g");
		System.out.println(bst);
		bst.remove("d");
		System.out.println(bst);
		bst.remove("a");
		System.out.println(bst);
		bst.remove("f");
		System.out.println(bst.contains("g"));
		System.out.println(bst.contains("a"));
	}
}