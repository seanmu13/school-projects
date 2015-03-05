public class TestNetwork {

  public static void main(String[] args) {
    Network network = new Network("A B C D E F") ;
    network.setCapacity("A","B",6) ;
    network.setCapacity("A","C",8) ;
    network.setCapacity("B","D",6) ;
    network.setCapacity("B","E",3) ;
    network.setCapacity("C","D",3) ;
    network.setCapacity("C","E",3) ;
    network.setCapacity("D","F",8) ;
    network.setCapacity("E","F",6) ;
    System.out.println("Original network:") ;
    network.print() ;
    network.maximizeFlow("A","F") ;
    System.out.println("Network with flow values:") ;
    network.print() ;
  }
}

/// End-of-File
