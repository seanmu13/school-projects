//////////////////////////////////////////////////////////////////////
///
/// Contents: Demonstrate some capabilities of a generic ArrayList
/// Author:   John Aronis
/// Date:     January 2007
///
//////////////////////////////////////////////////////////////////////

/// Java's ArrayList is a flexible, fast, and extremely useful data
/// structure.  It comes in two forms: generic and vanilla.  The
/// generic form is illustrated below.  A few of the most useful
/// methods are illustrated; see a good manual (such as the O'Reilly
/// "Java in a Nutshell") for many more.

import java.util.ArrayList ;
import java.util.Collections ;

public class DemonstrateArrayList {

  public static void main(String[] args) {

    ///
    /// Create an ArrayList of Strings and fill it:
    ///
    ArrayList<String> A = new ArrayList<String>() ;
    A.add("d") ;
    A.add("b") ;
    A.add("e") ;
    A.add("a") ;
    A.add("h") ;
    A.add("f") ;
    A.add("c") ;
    A.add("g") ;
    System.out.println(A) ;

    ///
    /// Zip through it quickly:
    ///
    for (int i=0 ; i<A.size() ; i++) { System.out.print( A.get(i) + " " ) ; }
    System.out.println() ;

    ///
    /// String implements Comparable, so sort it with Java's Quicksort:
    ///
    Collections.sort(A) ;
    System.out.println(A) ;

    ///
    /// Search it with Java's binary search:
    ///
    System.out.println( Collections.binarySearch(A,"c") ) ;
    System.out.println( Collections.binarySearch(A,"x") ) ;

    ///
    /// Demonstrate a few other important methods:
    ///
    A.add(2,"foo") ;
    A.remove(4) ;
    A.set(1,"hello") ;
    System.out.println(A) ;

  }

}

/// End-of-File
