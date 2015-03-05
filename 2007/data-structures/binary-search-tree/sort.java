import java.util.*;

public class sort{
	public static void main(String[] args){
		int[] a;

		a = make(20,10);

		System.out.println("\n\nFirst");

		for(int i=0;i<a.length;i++){
			System.out.print(a[i] + ",");
		}

		quickSort(a);

		System.out.println("\n\n");

		for(int i=0;i<a.length;i++){
			System.out.print(a[i] + ",");
		}

		System.out.println("\n\n");

	}

	public static int[] make(int length, int maxElement){
		int[] result = new int[length];
		Random generator = new Random();
		int random=0;
		for(int i=0;i<length;i++){
			random = generator.nextInt(maxElement)+1;
			result[i] = random;
		}
		return result;
	}

	public static int[] selectionSort(int[] A){
		int smallest=0;

		for (int i=0;i<A.length;i++) {
			smallest=i;
			for (int j=i;j<A.length;j++)
			{if(A[j] <A[smallest]) smallest=j;}
			swap(A,i,smallest);
		}
		return A;

	}
	public static int[] insertionSort(int[] A){
		int length=A.length;

		for (int i=0;i<A.length;i++) {
			for (int j=i;j>0&&A[j-1]>A[j];j--){
				swap(A,j,j-1);}
		}
		return A;
	}

	public static int[] mergeSort(int[] whole){
		if (whole.length == 1) {
				return whole;
		}
		else {
			// Create an array to hold the left half of the whole array
			// and copy the left half of whole into the new array.
			int[] left = new int[whole.length/2];
			System.arraycopy(whole, 0, left, 0, left.length);
			// Create an array to hold the right half of the whole array
			// and copy the right half of whole into the new array.
			int[] right = new int[whole.length-left.length];
			System.arraycopy(whole, left.length, right, 0, right.length);

			// Sort the left and right halves of the array.
			left = mergeSort(left);
			right = mergeSort(right);

			// Merge the results back together.
			merge(left, right, whole);

			return whole;
		}
	}

	/**
	* Merge the two sorted arrays left and right into the
	 * array whole.
	 *
	 * @param left a sorted array.
	 * @param right a sorted array.
	 * @param whole the array to hold the merged left and right arrays.
	 */
	public static void merge(int[] left, int[] right, int[] whole) {
		int leftIndex = 0;
		int rightIndex = 0;
		int wholeIndex = 0;

		// As long as neither the left nor the right array has
		// been used up, keep taking the smaller of left[leftIndex]
		// or right[rightIndex] and adding it at both[bothIndex].
		while (leftIndex < left.length && rightIndex < right.length) {
			if (left[leftIndex] < right[rightIndex]) {
			whole[wholeIndex] = left[leftIndex];
			leftIndex++;
			}
			else {
			whole[wholeIndex] = right[rightIndex];
			rightIndex++;
			}
			wholeIndex++;
		}

		int[] rest;
		int restIndex;
		if (leftIndex >= left.length) {
		// The left array has been use up...
		rest = right;
		restIndex = rightIndex;
		}
		else {
			// The right array has been used up...
			rest = left;
			restIndex = leftIndex;
		}

		// Copy the rest of whichever array (left or right) was
		// not used up.
		for (int i=restIndex; i<rest.length; i++) {
			whole[wholeIndex] = rest[i];
			wholeIndex++;
		}
	}

	//-----------------------------------------------//

	public static void quickSort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }
    public static void quickSort(int[] a, int left, int right) {
        if (right <= left) return;
        int i = partition(a, left, right);
        quickSort(a, left, i-1);
        quickSort(a, i+1, right);
    }

    private static int partition(int[] a, int left, int right) {
        int i = left - 1;
        int j = right;
        while (true) {
            while (less(a[++i], a[right]))      // find item on left to swap
                ;                               // a[right] acts as sentinel
            while (less(a[right], a[--j]))      // find item on right to swap
                if (j == left) break;           // don't go out-of-bounds
            if (i >= j) break;                  // check if pointers cross
            swap(a, i, j);                      // swap two elements into place
        }
        swap(a, i, right);                      // swap with partition element
        return i;
    }

    // is x < y ?
    private static boolean less(int x, int y) {
        return (x < y);
    }

	//-----------------------------------------------//

	public static void swap(int[] A, int x, int y){
		int temp = A[x];
		A[x] = A[y];
		A[y] = temp;
	}
}