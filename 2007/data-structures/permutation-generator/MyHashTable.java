import java.util.ArrayList;

public class MyHashTable{

	public int M = 48571;
	public ArrayList[] table = new ArrayList[M];

	public MyHashTable(){
	}

	//--------------------------------------
	//	Places key,value pairs in hash table
	//--------------------------------------

	public void put(String key,String val){

		ArrayList<String> values = new ArrayList<String>();
		int hash=1;

		for(int i=0;i<key.length();i++){
			hash = (hash * 32 + key.charAt(i)-97)%M;
		}

		//----------------------------------------------------------------------------
		//	If there is already a value at the given key, it adds it to the same place
		//----------------------------------------------------------------------------

		if(table[hash]!=null){
			for(int j=0;j<table[hash].size();j++){
				values.add((String)table[hash].get(j));
			}
		}

		values.add(val);
		table[hash] = values;
	}

	//-------------------------------------
	//	Gets the value at the specified key
	//-------------------------------------

	public ArrayList get(String key){

		char c;
		int hash=1;

		for(int i=0;i<key.length();i++){
			c = key.charAt(i);
			hash = (hash*32+c-97)%M;
		}

		return table[hash];
	}

	public static void main(String[] args){
	}
}