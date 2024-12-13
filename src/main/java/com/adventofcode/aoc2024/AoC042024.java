package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Point;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

class AoC042024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, grid -> p -> searchAll( grid, p ) );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, grid -> p -> searchX( grid, p ) );
  }

  private String solve(final Stream<String> input,
      final Function<char[][], ToIntFunction<Point>> search) {
    char[][] grid = input.map( String::toCharArray ).toArray( char[][]::new );
    int result = 0;
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        result += search.apply( grid ).applyAsInt( new Point( i, j ) );
      }
    }
    return itoa( result );
  }

  private int searchAll(final char[][] grid, final Point start) {
    ToIntFunction<UnaryOperator<Point>> search = move -> searchWord( "XMAS", grid, start, move );
    int result = 0;
    //horizontal and vertical
    result += Stream.of( Direction.values() )
        .mapToInt( direction -> search.applyAsInt( p -> p.move( direction ) ) )
        .sum();
    //diagonals
    result += Stream.of( Direction.values() )
        .mapToInt( direction ->
            search.applyAsInt( p -> p.move( direction ).move( direction.rotateClockwise() ) )
        ).sum();
    return result;
  }

  private int searchX(final char[][] grid, final Point start) {
    Function<Point, ToIntFunction<UnaryOperator<Point>>> search =
        p -> move ->
            searchWord( "MAS", grid, p, move ) | searchWord( "SAM", grid, p, move );
    int diagonalDown = search.apply( start ).applyAsInt( p -> p.move( RIGHT ).move( DOWN ) );
    int diagonalUp = search.apply( start.move( DOWN ).move( DOWN ) )
        .applyAsInt( p -> p.move( RIGHT ).move( UP ) );
    return diagonalDown & diagonalUp;
  }

  private int searchWord(final String word, final char[][] grid, Point position,
      UnaryOperator<Point> move) {
    int rows = grid.length;
    int columns = grid[0].length;
    for ( final char c : word.toCharArray() ) {
      if ( position.i() < 0 || rows <= position.i() || position.j() < 0 || columns <= position.j()
          || grid[position.i()][position.j()] != c ) {
        return 0;
      }
      position = move.apply( position );
    }
    return 1;
  }
}
