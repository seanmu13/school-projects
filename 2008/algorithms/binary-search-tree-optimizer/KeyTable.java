import java.util.ArrayList;

public class KeyTable<T extends Comparable<T>>{

	private ArrayList<T> list;

	public KeyTable(ArrayList<T> keys){
		list = keys;
	}

	public T get(int i){
		return list.get(i);
	}

	public int frequency(T key){
		int freq = 0;

		for(int i = 0; i < list.size(); i++){
			if(key == list.get(i)){
				freq++;
			}
		}

		return freq;
	}

	public int size(){
		ArrayList unique = new ArrayList();
		unique.add(list.get(0));

		for(int i = 0; i < list.size(); i++){
			if(! unique.contains(list.get(i))){
				unique.add(list.get(i));
			}
		}

		return unique.size();
	}

	public ArrayList<T> getUnique(){
		ArrayList<T> unique2 = new ArrayList<T>();
		unique2.add(list.get(0));

		for(int i = 0; i < list.size(); i++){
			if(! unique2.contains(list.get(i))){
				unique2.add(list.get(i));
			}
		}

		return unique2;
	}

	public static void main(String[] args){
		ArrayList<String> keys = new ArrayList<String>() ;
		keys.add("C") ;
		keys.add("G") ;
		keys.add("B") ; keys.add("B") ;
		keys.add("F") ; keys.add("F") ;
		keys.add("D") ; keys.add("D") ; keys.add("D") ;
		keys.add("A") ; keys.add("A") ; keys.add("A") ; keys.add("A") ;
		keys.add("E") ; keys.add("E") ; keys.add("E") ; keys.add("E") ; keys.add("E") ;

		KeyTable<String> table = new KeyTable<String>(keys);
		System.out.println("Get 1: " + table.get(1));
		System.out.println("Freq D: " + table.frequency("D"));
		System.out.println("Size: " + table.size());
	}
}