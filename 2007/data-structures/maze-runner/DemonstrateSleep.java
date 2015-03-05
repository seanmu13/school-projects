public class DemonstrateSleep {

  ///
  /// Sleep one second.
  ///
  public static void main(String[] args) {
      System.out.println("START") ;
      int pause = 1000 ;
      try { Thread.sleep(pause) ; } catch (Exception e) { } ;
      System.out.println("FINISH") ;
  }
}