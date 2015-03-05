import java.util.ArrayList ;

public class TestBSTOptimizer {

  public static void main(String[] args) {

    ///
    /// Enter example from Sedgewick.  Keys will be sorted later.
    ///
    ArrayList<String> keys = new ArrayList<String>() ;
    keys.add("C") ;
    keys.add("G") ;
    keys.add("B") ; keys.add("B") ;
    keys.add("F") ; keys.add("F") ;
    keys.add("D") ; keys.add("D") ; keys.add("D") ;
    keys.add("A") ; keys.add("A") ; keys.add("A") ; keys.add("A") ;
    keys.add("E") ; keys.add("E") ; keys.add("E") ; keys.add("E") ; keys.add("E") ;

    ///
    /// Find optimal BST without memoization.
    ///
    BSTOptimizer.MEMOIZING = false ;
    BSTOptimizer.countCalls = 0 ;
    BinaryTree<String> firstBestBST = BSTOptimizer.optimize(keys) ;
    System.out.println( "Memoization turned OFF" ) ;
    System.out.println( "  Optimal BST:    " + firstBestBST ) ;
    System.out.println( "  Calls required: " + BSTOptimizer.countCalls ) ;

    ///
    /// Find optimal BST with memoization.
    ///
    BSTOptimizer.MEMOIZING = true ;
    BSTOptimizer.countCalls = 0 ;
    BinaryTree<String> secondBestBST = BSTOptimizer.optimize(keys) ;
    System.out.println( "Memoization turned ON" ) ;
    System.out.println( "  Optimal BST:    " + secondBestBST ) ;
    System.out.println( "  Calls required: " + BSTOptimizer.countCalls ) ;

  }

}

/// End-of-File
