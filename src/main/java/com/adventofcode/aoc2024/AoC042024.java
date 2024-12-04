package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.stream.Stream;

class AoC042024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, grid -> (i, j) -> searchAll( grid, i, j ) );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, grid -> (i, j) -> searchX( grid, i, j ) );
  }

  private String solve(final Stream<String> input,
      final Function<List<List<Character>>, IntBinaryOperator> search) {
    List<List<Character>> grid = Utils.getCharMatrix( input );
    int rows = grid.size();
    int columns = grid.get( 0 ).size();
    int result = 0;
    for ( int i = 0; i < rows; i++ ) {
      for ( int j = 0; j < columns; j++ ) {
        result += search.apply( grid ).applyAsInt( i, j );
      }
    }
    return itoa( result );
  }

  private int searchWord(final String word, final List<List<Character>> grid, final int i,
      final int j, IntBinaryOperator iMove, IntBinaryOperator jMove) {
    int rows = grid.size();
    int columns = grid.get( 0 ).size();
    for ( int k = 0; k < word.length(); k++ ) {
      char c = word.charAt( k );
      int nextI = iMove.applyAsInt( i, k );
      int nextJ = jMove.applyAsInt( j, k );
      if ( nextI < 0 || rows <= nextI || nextJ < 0 || columns <= nextJ
          || grid.get( nextI ).get( nextJ ) != c ) {
        return 0;
      }
    }
    return 1;
  }

  private int searchAll(final List<List<Character>> grid, final int i, final int j) {
    int result = 0;
    //horizontal
    result += searchWord( "XMAS", grid, i, j, (a, b) -> a, (a, b) -> a + b );
    result += searchWord( "XMAS", grid, i, j, (a, b) -> a, (a, b) -> a - b );
    //vertical
    result += searchWord( "XMAS", grid, i, j, (a, b) -> a + b, (a, b) -> a );
    result += searchWord( "XMAS", grid, i, j, (a, b) -> a - b, (a, b) -> a );
    //diagonal right
    result += searchWord( "XMAS", grid, i, j, (a, b) -> a + b, (a, b) -> a + b );
    result += searchWord( "XMAS", grid, i, j, (a, b) -> a - b, (a, b) -> a + b );
    //diagonal left
    result += searchWord( "XMAS", grid, i, j, (a, b) -> a + b, (a, b) -> a - b );
    result += searchWord( "XMAS", grid, i, j, (a, b) -> a - b, (a, b) -> a - b );
    return result;
  }

  private int searchX(final List<List<Character>> grid, final int i, final int j) {
    final boolean diagonalDown = Stream.of( "MAS", "SAM" )
        .anyMatch( word -> searchWord( word, grid, i, j, (a, b) -> a + b, (a, b) -> a + b ) == 1 );
    final boolean diagonalUp = Stream.of( "MAS", "SAM" ).anyMatch(
        word -> searchWord( word, grid, i + 2, j, (a, b) -> a - b, (a, b) -> a + b ) == 1 );
    return diagonalDown && diagonalUp ? 1 : 0;
  }
}
