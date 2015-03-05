import java.util.ArrayList;

public class Cache{
	public int size;
	public int associativity;
	public int blockSize;
	public int latency;
	public int blocks;
	public int sets;
	public int wordsPerBlock;
	public int indexSetBits;
	public int blockOffsetBits;
	public int tagBits;
	public static boolean writeBack = false;
	public CacheBlock[][] list;

	// Creates a new Cache
	public Cache(int s, int a, int b, int l){
		size = s;
		associativity = a;
		blockSize = b;
		latency = l;

		// Determines the # of blocks, sets, and words per block
		blocks = size / blockSize;
		sets = blocks / associativity;
		wordsPerBlock = blockSize / 4;

		// Determines the # of bits in the address required for the index, tag, and block offset
		indexSetBits = (int) Math.ceil(Math.log(sets)/Math.log(2));
		blockOffsetBits = (int) Math.ceil(Math.log(wordsPerBlock)/Math.log(2));
		tagBits = 30 - indexSetBits - blockOffsetBits;

		// Creates a 2-dimensional array with the rows being the number of sets and the columns being the number of blocks in each set
		list = new CacheBlock[sets][associativity];
	}

	// Reads an address
	public String read(String readAddress){
		// Converts address to binary then gets the block, index, and tag numbers in binary
		String binAddr = cachesim.hexToBin(readAddress);
		String blockNumBin = binAddr.substring(binAddr.length() - 2 - blockOffsetBits,binAddr.length() - 2);
		String indexNumBin = binAddr.substring(tagBits,binAddr.length() - 2 - blockOffsetBits);
		String tagNumBin = binAddr.substring(0, tagBits);

		// Converts the binary block,index,and tag to decimal
		int blockNum = Integer.parseInt(blockNumBin,2);
		int indexNum = Integer.parseInt(indexNumBin,2);
		int tagNum = Integer.parseInt(tagNumBin,2);

		// Makes a new cache block with the given index, block, and tag
		CacheBlock current = new CacheBlock(indexNum, blockNum, tagNum);

		// Looks in the set for the given tag to see if it already exists (hit)
		for(int i = 0; i < list[indexNum].length; i++){
			if(current.compareTo(list[indexNum][i]) == 0){
				// If it is found, shifts the blocks in the set down, and puts the current one at the end of the array to keep LRU in order
				this.shift(list[indexNum], i);
				return "hit";
			}
		}

		// If there is a miss, we search for an open spot in the set and create the block there
		for(int i = 0; i < list[indexNum].length; i++){
			if(list[indexNum][i] == null){
				list[indexNum][i] = current;
				return "miss";
			}
		}

		// If there is no open block, then we must replace the LRU block with the current one
		this.replaceAndShift(list[indexNum], current);
		return "miss";
	}

	// Writes to the cache
	public String write(String writeAddress){
		String binAddr = cachesim.hexToBin(writeAddress);
		String blockNumBin = binAddr.substring(binAddr.length() - 2 - blockOffsetBits,binAddr.length() - 2);
		String indexNumBin = binAddr.substring(tagBits,binAddr.length() - 2 - blockOffsetBits);
		String tagNumBin = binAddr.substring(0, tagBits);

		int blockNum = Integer.parseInt(blockNumBin,2);
		int indexNum = Integer.parseInt(indexNumBin,2);
		int tagNum = Integer.parseInt(tagNumBin,2);

		CacheBlock current = new CacheBlock(indexNum, blockNum, tagNum);

		// Sets the current cache block dirty bit
		current.dirty = true;

		// Searches through the set to see if there is a cache hit
		for(int i = 0; i < list[indexNum].length; i++){
			if(current.compareTo(list[indexNum][i]) == 0){
				// If there is a hit and the block already there is dirty, there is going to be a writeback
				if(list[indexNum][i].dirty){
					writeBack = true;
				}
				else{
					writeBack = false;
				}

				// Shift the blocks in the set to maintain the LRU block
				this.shift(list[indexNum], i);
				return "hit";
			}
		}

		// If there is a miss, find an open block (non-valid) and replace it with the current block
		for(int i = 0; i < list[indexNum].length; i++){
			if(list[indexNum][i] == null){
				list[indexNum][i] = current;
				return "miss";
			}
		}

		// If there is no open block, we must shift the LRU out
		this.replaceAndShift(list[indexNum], current);
		return "miss";
	}

	// If the set contains all valid blocks, we must replace the LRU block
	public void replaceAndShift(CacheBlock[] cb, CacheBlock newBlock){
		// The first entry in the set is the LRU
		boolean dirty = cb[0].dirty;

		if(dirty){
			writeBack = true;
		}
		else{
			writeBack = false;
		}

		// In the set, we will shift everything left to maintain LRU
		for(int i = 0; i < cb.length - 1; i++){
			cb[i] = cb[i+1];
		}

		// The last entry in the set gets the newBlock
		cb[cb.length - 1] = newBlock;
		return;
	}

	// Performs a shift when a hit occurs to maintain LRU
	public void shift(CacheBlock[] cb, int index){
		int indexOfLastEntry = 0;

		// Store this because this is the block currently being accessed
		CacheBlock temp = cb[index];

		// Finds the location of the last index that is not null
		for(int i = 0; i < cb.length; i++){
			if(cb[i] != null){
				indexOfLastEntry = i;
			}
		}

		// Shift left
		for(int i = index; i < indexOfLastEntry; i++){
			cb[i] = cb[i+1];
		}

		// The last non null entry in the set will be the current address
		cb[indexOfLastEntry] = temp;
		return;
	}

	// Finds the tag of the address
	public int getTag(String address){
		String binAddr = cachesim.hexToBin(address);
		String tagNumBin = binAddr.substring(0, tagBits);
		return Integer.parseInt(tagNumBin,2);
	}

	// Finds the set of the address
	public int getSet(String address){
		String binAddr = cachesim.hexToBin(address);
		String indexNumBin = binAddr.substring(tagBits,binAddr.length() - 2 - blockOffsetBits);
		return Integer.parseInt(indexNumBin,2);
	}

	// Finds the block of the address
	public int getBlock(String address){
		String binAddr = cachesim.hexToBin(address);
		String blockNumBin = binAddr.substring(binAddr.length() - 2 - blockOffsetBits,binAddr.length() - 2);
		return Integer.parseInt(blockNumBin,2);
	}

	public static void main(String[] args){
	}
}