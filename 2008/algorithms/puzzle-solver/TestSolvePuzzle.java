/// This program should print:
///
///     2 5 3
///     1 _ 6
///     4 7 8
///
///     2 _ 3
///     1 5 6
///     4 7 8
///
///     _ 2 3
///     1 5 6
///     4 7 8
///
///     1 2 3
///     _ 5 6
///     4 7 8
///
///     1 2 3
///     4 5 6
///     _ 7 8
///
///     1 2 3
///     4 5 6
///     7 _ 8
///
///     1 2 3
///     4 5 6
///     7 8 _

public class TestSolvePuzzle {

  public static void main(String[] args) {
    Puzzle scrambled = new Puzzle("2 5 3 1 _ 6 4 7 8") ;
    //Puzzle scrambled = new Puzzle("1 5 _ 2 4 7 8 3 6") ;
    Puzzle solved = new Puzzle("1 2 3 4 5 6 7 8 _") ;

    System.out.println(scrambled);
    System.out.println(solved);

    SolvePuzzle.solve(scrambled,solved) ;
  }

}

/// End-of-File
