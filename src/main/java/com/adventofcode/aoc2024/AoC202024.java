package com.adventofcode.aoc2024;


import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.manhattanDistance;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.GraphUtils;
import com.adventofcode.utils.Point;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class AoC202024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 2 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 20 );
  }

  private String solve(final Stream<String> input, final int maxCheat) {
    char[][] grid = input.map( String::toCharArray ).toArray( char[][]::new );
    Map<Point, Long> pointToDistance = GraphUtils.computeShortestPaths( getStart( grid ),
        n -> getNeighbours( n, grid ) );
    List<Point> pointsSortedByDistance = pointToDistance.keySet().stream()
        .sorted( Comparator.comparingLong( pointToDistance::get ) )
        .toList();
    int minSave = grid.length < 20 ? 50 : 100;
    long result = 0;
    for ( int i = 0; i < pointsSortedByDistance.size(); i++ ) {
      for ( int j = i + 1; j < pointsSortedByDistance.size(); j++ ) {
        Point start = pointsSortedByDistance.get( i );
        Point end = pointsSortedByDistance.get( j );
        long shortcut = manhattanDistance( start.i(), start.j(), end.i(), end.j() );
        if ( shortcut <= maxCheat //shortcut is allowed
            && (pointToDistance.get( end ) - pointToDistance.get( start )) - shortcut
            >= minSave ) { //shortcut is useful
          result++;
        }
      }
    }
    return itoa( result );
  }

  private Point getStart(final char[][] grid) {
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        if ( grid[i][j] == 'S' ) {
          return new Point( i, j );
        }
      }
    }
    throw new IllegalArgumentException();
  }

  private Collection<Point> getNeighbours(final Point curr, final char[][] grid) {
    return Stream.of( Direction.values() )
        .map( curr::move )
        .filter( n -> 0 <= n.i() && n.i() < grid.length && 0 <= n.j() && n.j() < grid[0].length )
        .filter( n -> grid[n.i()][n.j()] != HASH )
        .toList();
  }
}
