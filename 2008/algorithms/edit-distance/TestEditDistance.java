public class TestEditDistance {

  public static void main(String[] args) {
	System.out.println("\n========================") ;
	System.out.println("-- Test Edit Distance --") ;
	System.out.println("========================\n") ;
    System.out.println("=======================================================") ;
    System.out.println( EditDistance.distance("abc","abc",true) ) ;
    System.out.println("=======================================================") ;
    System.out.println( EditDistance.distance("abc","abxcd",true) ) ;
    System.out.println("=======================================================") ;
    System.out.println( EditDistance.distance("shoes","socks",true) ) ;
    System.out.println("=======================================================") ;
    System.out.println( EditDistance.distance("dogs","hounds",false) ) ;
    System.out.println("=======================================================\n") ;
  }

}

/// End-of-File


