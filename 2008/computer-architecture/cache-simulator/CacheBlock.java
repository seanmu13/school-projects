public class CacheBlock implements Comparable{
	public boolean dirty = false;;
	public int tag;
	public int block;
	public int index;

	// Creates a new CacheBlock
	public CacheBlock(int indx, int blk, int tg){
		tag = tg;
		block = blk;
		index = indx;
	}

	// If the index and tags are the same, the cache blocks are equal
	public int compareTo(Object obj){
		CacheBlock cb = (CacheBlock)obj;

		if(cb == null){
			return 1;
		}

		if(this.index == cb.index && this.tag == cb.tag){
			return 0;
		}
		else{
			return 1;
		}
	}
}