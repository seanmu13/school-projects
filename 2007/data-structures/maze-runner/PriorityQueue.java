import java.util.ArrayList;

public class PriorityQueue<Point extends Comparable<Point>>{

	private ArrayList<Point> heap;

	//----------------------------
	//	PriorityQueue Contstructor
	//----------------------------

	public PriorityQueue(){
		heap = new ArrayList<Point>();
	}

	//------------------------------------
	//	Adds an item to the priority queue
	//------------------------------------

	public void add(Point item){
		Point temp;

		if(heap.isEmpty() && heap.indexOf(null)==-1){
			heap.add(item);
			return;
		}

		int index=0;

		if(heap.indexOf(null)==-1){
			heap.add(item);
			index = heap.size()-1;
		}
		else{
			index = heap.indexOf(null);
			heap.set(index,item);
		}

		while(heap.get(index).compareTo(heap.get((index-1)/2))>0){
			temp = heap.get(index);
			heap.set(index,heap.get((index-1)/2));
			heap.set((index-1)/2,temp);
			index = (index-1)/2;
			if(index==0) break;
		}

		return;
	}

//-----------------------------------------------------------------
//	Removes an item to the priority queue and returns the root item
//-----------------------------------------------------------------

	public Point remove(){
		Point temp;

		if(heap.isEmpty() || (heap.size()>0 && heap.get(0)==null)) return null;

		if(heap.size()==1 || (heap.size()>1 && heap.get(1)==null)){
			temp = heap.get(0);
			heap.set(0,null);
			return temp;
		}

		Point root = heap.get(0);
		int index = 0, end;

		if(heap.indexOf(null)==-1){
			heap.set(0,heap.get(heap.size()-1));
			heap.set(heap.size()-1,null);
			end = heap.size()-2;
		}
		else{
			int nullInd = heap.indexOf(null)-1;
			heap.set(0,heap.get(nullInd));
			heap.set(nullInd,null);
			end = nullInd-1;
		}

		boolean cont = true;

		while(cont){
			if(heap.indexOf(null)!=-1){
				int nullI = heap.indexOf(null)-1;
				if(index==nullI){
					cont = false;
					break;
				}
			}
			if(heap.get(2*index+1)==null){
				cont = false;
				break;
			}
			else if(heap.get(2*index+2)==null){
				if(heap.get(index).compareTo(heap.get(2*index+1))<0){
					temp = heap.get(index);
					heap.set(index,heap.get(2*index+1));
					heap.set(2*index+1,temp);
					index = 2*index+1;
				}
				else{
					cont = false;
				}
			}
			else{
				if(heap.get(index).compareTo(heap.get(2*index+1))<0 || heap.get(index).compareTo(heap.get(2*index+2))<0){
					if(heap.get(2*index+1).compareTo(heap.get(2*index+2))>0){
						temp = heap.get(index);
						heap.set(index,heap.get(2*index+1));
						heap.set(2*index+1,temp);
						index = 2*index+1;
					}
					else{
						temp = heap.get(index);
						heap.set(index,heap.get(2*index+2));
						heap.set(2*index+2,temp);
						index = 2*index+2;
					}
				}
				else{
					cont = false;
				}
			}

			if(index>=end || (2*index+1)>=end || (2*index+1)>=end) break;
		}

		return root;
	}

//-----------------------------------
//	Checks if priority queue is empty
//-----------------------------------

	public boolean isEmpty(){
		return heap.isEmpty() || (heap.size()>0 && heap.get(0)==null);
	}

//---------------------------
//	Prints the priority queue
//---------------------------

	public String toString(){
		if(heap.isEmpty()) return "[]";

		String result = new String("[");
		int stop = 0;
		if(heap.indexOf(null)==-1){
			stop = heap.size();
		}
		else{
			stop = heap.indexOf(null);
		}

		for(int i=0;i<stop;i++){
			if((i+1)==stop) result+= heap.get(i);
			else	result+= heap.get(i) + ", ";
		}

		return result + "]";
	}

	public static void main(String[] args){
	}
}