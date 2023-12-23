package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.pow;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.GraphUtils;
import com.adventofcode.utils.Point;
import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

class AoC212023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    final List<String> inputList = input.toList();
    return solve( inputList, inputList.size() < 20 ? 6 : 64 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    final List<String> inputList = input.toList();
    return solve( inputList, 26501365 );
  }

  private static String solve(final List<String> input, final int steps) {
    final char[][] grid = input.stream().map( String::toCharArray ).toArray( char[][]::new );
    final Point start = getStart( grid );
    final Map<Point, Long> distances = GraphUtils.computeShortestPaths( start,
        curr -> getNeighbours( curr, grid ) );
    final int halfGrid = grid.length / 2;
    if ( steps <= halfGrid + 1 ) {
      final long less = count( distances, v -> v <= steps && v % 2 == steps % 2 );
      return itoa( less );
    }
    Preconditions.checkArgument( steps % grid.length == halfGrid ); //only works in this scenario
    final long moreOdd = count( distances, v -> v > halfGrid && v % 2 != 0 );
    final long moreEven = count( distances, v -> v > halfGrid && v % 2 == 0 );
    final long allOdd = count( distances, v -> v % 2 != 0 );
    final long allEven = count( distances, v -> v % 2 == 0 );
    final long nFullGrids = steps / grid.length;
    return itoa(
        pow( nFullGrids, 2 ) * allEven
            + nFullGrids * moreEven
            + pow( nFullGrids + 1, 2 ) * allOdd
            - (nFullGrids + 1) * moreOdd
    );
  }

  private static long count(final Map<Point, Long> distances, Predicate<Long> count) {
    return distances.values().stream().filter( count ).count();
  }

  private static Point getStart(final char[][] grid) {
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        if ( grid[i][j] == 'S' ) {
          grid[i][j] = DOT;
          return new Point( i, j );
        }
      }
    }
    throw new IllegalArgumentException();
  }

  private static Collection<Point> getNeighbours(final Point curr, final char[][] grid) {
    return Stream.of( Direction.values() ).map( curr::move )
        .filter( p -> p.i() >= 0 && p.j() >= 0 && p.i() < grid.length && p.j() < grid[0].length )
        .filter( p -> grid[p.i()][p.j()] == DOT ).toList();
  }

}
