public class MyArrayList<T>{

	private T[] list;
	private int capacity;
	private int size;

//-------------------------
//	MyArrayList Constructor
//-------------------------

	public MyArrayList(){
		list = (T[])(new Object[5]);
		size = 0;
		capacity = 5;
	}

//------------------------------------------------------------
//	Adds an element to the array list and returns that element
//------------------------------------------------------------

	public T add(T element){
		if(size==capacity){
			capacity = (int)(Math.ceil(capacity*1.5));
			T[] newList = (T[])(new Object[capacity]);

			for(int i=0;i<size;i++){
				newList[i] = list[i];
			}

			newList[size] = element;
			size++;
			list = (T[])(new Object[capacity]);

			for(int i=0;i<size;i++){
				list[i] = newList[i];
			}
			return list[size-1];
		}
		else{
			list[size] = element;
			size++;
			return list[size-1];
		}
	}

//---------------------------------------------------------------------------------------------------------------------
//	Adds an element at the specified index and shifts everything to the right if an element already exists at the index
//---------------------------------------------------------------------------------------------------------------------

	public T add(int index, T element){
		if(index>=size || index<0)	return null;
		else if(size==capacity)		capacity = (int)(Math.ceil(capacity*1.5));

		T[] newList = (T[])(new Object[capacity]);
		newList[index] = element;
		int j = 0;

		for(int i=0;i<size;i++){
			if(i==index) j++;
			newList[j] = list[i];
			j++;
		}

		size++;
		list = (T[])(new Object[capacity]);

		for(int i=0;i<size;i++){
			list[i] = newList[i];
		}
		return element;
	}

//------------------------------------------------------------------
//	Returns the element at the specified index if the index is valid
//------------------------------------------------------------------

	public T get(int index){
		if(list[index]==null || index>=size || index<0) return null;
		else return list[index];
	}

//--------------------------------------------------------------------------------
//	Removes the element at the specified index and shifts everything left if valid
//--------------------------------------------------------------------------------

	public T remove(int index){
		if(list[index]==null || index>=size || index<0) return null;
		else{
			T removed = list[index];
			int j=0;
			T[] newList = (T[])(new Object[capacity]);

			for(int i=0;i<size;i++){
				if(i!=index){
					newList[j] = list[i];
					j++;
				}
			}

			size--;

			for(int i=0;i<size;i++){
				list[i] = newList[i];
			}
			return removed;
		}
	}

//-------------------------------------------------------
//	Returns true if the element is found in the ArrayList
//-------------------------------------------------------

	public boolean contains(T element){
		for(int i=0;i<size;i++){
			if(list[i]!=null){
				if(list[i].equals(element)) return true;
			}
		}
		return false;
	}

//-----------------------------------
//	Returns the size of the ArrayList
//-----------------------------------

	public int size(){
		return size;
	}

//--------------------------------------------
//	Returns the string value for the ArrayList
//--------------------------------------------

	public String toString(){
	   String printList = new String("");

		for(int i=0;i<size;i++){
			if(i==size-1)	printList = printList + list[i];
			else		printList = printList + list[i] + ",";
		}
	   return "\nMyArrayList:  " + "{" + printList + "}\n";
	}

	public static void main(String[] args){
	}
}