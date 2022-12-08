package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.IntStream.range;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import java.util.function.LongFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC082022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var grid = input.map( String::chars ).map( s -> s.map( Utils::charToInt ) )
        .map( IntStream::toArray ).toArray( int[][]::new );
    final var indexes = range( 0, grid.length ).boxed()
        .flatMap( i -> range( 0, grid[0].length ).mapToObj( j -> new Pair<>( i, j ) ) );

    final long result;
    if ( first ) {
      result = indexes.filter( p -> isVisible( grid, p.getFirst(), p.getSecond() ) ).count();
    } else {
      result = indexes.mapToLong( p -> computeScore( grid, p.getFirst(), p.getSecond() ) ).max()
          .orElseThrow();
    }
    return itoa( result );
  }

  private long computeScore(final int[][] grid, final int i, final int j) {
    final var right = computeScore( grid, i, j, j + 1, grid[0].length, true, false );
    final var left = computeScore( grid, i, j, 0, j, true, true );
    final var down = computeScore( grid, i, j, i + 1, grid.length, false, false );
    final var up = computeScore( grid, i, j, 0, i, false, true );
    return right * left * down * up;
  }

  private long computeScore(final int[][] grid, final int i, final int j, final int start,
      final int end, final boolean row, final boolean decrease) {
    return computeVisibility( grid, i, j, start, end, row, decrease, n -> n, n -> n + 1 );
  }

  private boolean isVisible(final int[][] grid, final int i, final int j) {
    var visible = isVisible( grid, i, j, j + 1, grid[0].length, true, false ); //right
    visible = visible || isVisible( grid, i, j, 0, j, true, true ); //left
    visible = visible || isVisible( grid, i, j, i + 1, grid.length, false, false ); //down
    visible = visible || isVisible( grid, i, j, 0, i, false, true ); //up
    return visible;
  }

  private boolean isVisible(final int[][] grid, final int i, final int j, final int start,
      final int end, final boolean row, final boolean decrease) {
    return computeVisibility( grid, i, j, start, end, row, decrease, n -> true, n -> false );
  }

  private <K> K computeVisibility(final int[][] grid, final int i, final int j, final int start,
      final int end, final boolean row, final boolean decrease, final LongFunction<K> visible,
      final LongFunction<K> notVisible) {
    var range = range( start, end ).boxed();
    if ( decrease ) {
      range = range.sorted( reverseOrder() );
    }

    final var tree = grid[i][j];
    final var count = range.takeWhile( k -> (row ? grid[i][k] : grid[k][j]) < tree ).count();

    if ( count == end - start ) {
      return visible.apply( count );
    } else {
      return notVisible.apply( count );
    }
  }
}
