//////////////////////////////////////////////////////////////////////
///
/// Contents: Demonstrate Homework-3 Code
/// Author:   John Aronis
/// Date:     January 2007
///
//////////////////////////////////////////////////////////////////////

public class DemonstrateTSP {

  public static void main(String[] args) {

    //
    // Demonstrate MyArrayList<T> class.
    //
    System.out.println("TESTING MYARRAYLIST CLASS") ;
    System.out.println("Create a MyArrayList of Strings...") ;
    MyArrayList<String> L1 = new MyArrayList<String>() ;
    System.out.println("Add strings a, b, c, d...") ;
    L1.add("a") ; L1.add("b") ; L1.add("c") ; L1.add("d") ;
    System.out.println("Add string foo to position 2...") ;
    L1.add(2,"foo") ;
    System.out.println("Remove element at index 1...") ;
    L1.remove(1) ;
    System.out.println("L1 should now contain a foo c d...") ;
    System.out.println(L1) ;
    System.out.println("Get element at index 1...") ;
    System.out.println(L1.get(1)) ;
    System.out.println("Check if L1 contains foo...") ;
    System.out.println(L1.contains("foo")) ;
    System.out.println("Check if L1 contains hello...") ;
    System.out.println(L1.contains("hello")) ;
    System.out.println("Find length of L1...") ;
    System.out.println(L1.size()) ;
    System.out.println("Create a MyArrayList of Integers...") ;
    MyArrayList<Integer> L2 = new MyArrayList<Integer>() ;
    System.out.println("Fill it to force resizing...") ;
    for (int i=0 ; i<100 ; i++) L2.add(new Integer(i)) ;
    System.out.println(L2) ;

    //
    // Demonstrate TSP code.
    //


    System.out.println("TESTING TRAVELINGSALESMAN CLASS") ;
    TravelingSalesman TS = new TravelingSalesman() ;
    TS.addCity("a",2,1) ;
    TS.addCity("b",5,2) ;
    TS.addCity("c",1,3) ;
    TS.addCity("d",4,3) ;
    TS.addCity("e",6,3) ;
    TS.addCity("f",2,4) ;
    TS.addCity("g",5,5) ;
    System.out.println("This should find the tour a c f d b e g a.") ;
    MyArrayList<City> tour = TS.tour("a") ;
    System.out.println(tour) ;

  }

}

/// End-of-File
