public class DoublyLinkedList<T> {

		private class Node {
        	private T contents ;
        	private Node previous ;
        	private Node next ;
      	}

		private Node head ;
		private Node tail ;
		private int length ;

		public DoublyLinkedList() {
			head = new Node() ;
			tail = new Node() ;
			head.next = tail ;
			tail.previous = head ;
			length = 0 ;
		}

		public void add(T entry){
			Node current = new Node();
			current.contents = entry;
			current.previous = head;
			current.next = head.next;
			current.next.previous = current;
			head.next = current;
			length++;
		}

		public void add(int index, T entry){
			Node nodeBefore = head;

			for(int i=0;i<index;i++){
				nodeBefore = nodeBefore.next;
			}

			Node current = new Node();
			current.next = nodeBefore.next;
			nodeBefore.next = current;
			current.contents = entry;
			current.previous = nodeBefore;
			current.next.previous = current;
			length++;
		}

		public T get(int index){
			Node current = head.next;

			for(int i=0;i<index;i++){
				current = current.next;
			}

			return current.contents;
		}

		public boolean contains(T entry){
			Node current = head.next;

			for(int i=0;i<length;i++){
				if(current.contents.equals(entry))	return true;
				current = current.next;
			}

			return false;
		}

		public T remove(int index){
			if(length==0 || index>length-1) return null;

			T result;
			Node nodeBefore = head;

			for(int i=0;i<index;i++){
				nodeBefore = nodeBefore.next;
			}

			result = nodeBefore.next.contents;
			nodeBefore.next = nodeBefore.next.next;
			length--;
			return result;
		}

		public int size(){
			return length;
		}

		public boolean isEmpty(){
			return length==0;
		}

		public String toString(){
			String s = new String("");
			Node current = head.next;

			for(int i=0;i<length;i++){
				if(current.next==tail) 	s = s + "<-[ " + current.contents + " ]";
				else if(i==0)	s = s + "[ " + current.contents + " ]->";
				else	s = s + "<-[ " + current.contents + " ]->";
				current = current.next;
			}

			return s;
		}

		public static void main(String[] args){
			DoublyLinkedList<String> L = new DoublyLinkedList<String>();
			L.add("0");
			L.add("1");
			L.add("2");
			L.add(3,"0ish");

			System.out.println("\nList:  " + L + "   Length: " + L.size());
			L.remove(0);
			System.out.println("\nList:  " + L);
			L.remove(1);
			System.out.println("\nList:  " + L);
			L.remove(1);
			System.out.println("\nList:  " + L);
			L.remove(0);
			System.out.println("\nList:  " + L);
			System.out.println("Empty? " + L.isEmpty() + "  contains 1? " + L.contains("1"));

			System.out.println("");
		}
}