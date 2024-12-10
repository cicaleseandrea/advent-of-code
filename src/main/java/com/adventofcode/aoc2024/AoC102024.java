package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.GraphUtils;
import com.adventofcode.utils.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class AoC102024 implements Solution {

  private static final char START = '0';
  private static final char END = '9';

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    char[][] grid = input.map( String::toCharArray ).toArray( char[][]::new );
    Collection<List<Point>> startingPaths = getStartingPaths( grid );
    long result = startingPaths.stream()
        .map( startingPath -> GraphUtils.fill( startingPath, curr -> getNextPaths( curr, grid ) ) )
        .mapToLong( allPaths -> getScore( allPaths, grid, first ) )
        .sum();
    return itoa( result );
  }

  private Collection<List<Point>> getStartingPaths(final char[][] grid) {
    Collection<List<Point>> startingPaths = new ArrayList<>();
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        if ( grid[i][j] == START ) {
          startingPaths.add( List.of( new Point( i, j ) ) );
        }
      }
    }
    return startingPaths;
  }

  private Collection<List<Point>> getNextPaths(final List<Point> currPath, final char[][] grid) {
    Point currPosition = currPath.get( currPath.size() - 1 );
    Collection<Point> nextPositions = Stream.of( Direction.values() )
        .map( currPosition::move )
        .filter( n -> 0 <= n.i() && n.i() < grid.length && 0 <= n.j() && n.j() < grid[0].length )
        //increasing height
        .filter( n -> grid[n.i()][n.j()] == grid[currPosition.i()][currPosition.j()] + 1 )
        .toList();
    Collection<List<Point>> nextPaths = new ArrayList<>();
    for ( Point nextPosition : nextPositions ) {
      List<Point> nextPath = new ArrayList<>( currPath );
      nextPath.add( nextPosition );
      nextPaths.add( nextPath );
    }
    return nextPaths;
  }

  private long getScore(final Collection<List<Point>> allPaths, final char[][] grid,
      final boolean first) {
    Collection<Point> pathEnds = allPaths.stream()
        .map( path -> path.get( path.size() - 1 ) )
        .filter( pathEnd -> grid[pathEnd.i()][pathEnd.j()] == END )
        .toList();
    if ( first ) {
      //consider distinct positions instead of distinct paths
      pathEnds = Set.copyOf( pathEnds );
    }
    return pathEnds.size();
  }
}
