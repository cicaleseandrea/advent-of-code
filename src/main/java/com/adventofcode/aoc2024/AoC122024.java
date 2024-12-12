package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.GraphUtils;
import com.adventofcode.utils.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

class AoC122024 implements Solution {

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
    long price = 0;
    Set<Point> visited = new HashSet<>();
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        Point start = new Point( i, j );
        if ( visited.contains( start ) ) {
          continue;
        }
        Set<Point> region = GraphUtils.fill( start, curr -> getNeighbours( curr, grid ) );
        visited.addAll( region );
        price += region.size() * (first ? getPerimeter( region ) : getSides( region ));
      }
    }
    return itoa( price );
  }

  private Collection<Point> getNeighbours(final Point curr, final char[][] grid) {
    char c = grid[curr.i()][curr.j()];
    return Stream.of( Direction.values() )
        .map( curr::move )
        .filter( n -> 0 <= n.i() && n.i() < grid.length && 0 <= n.j() && n.j() < grid[0].length )
        .filter( n -> grid[n.i()][n.j()] == c )
        .toList();
  }

  private long getPerimeter(final Set<Point> region) {
    long perimeter = 0;
    for ( final Point curr : region ) {
      perimeter += Stream.of( Direction.values() )
          .filter( direction -> isPerimeter( curr, region, direction ) )
          .count();
    }
    return perimeter;
  }

  private long getSides(final Set<Point> region) {
    long sides = 0;
    for ( final Point curr : region ) {
      sides += Stream.of( Direction.values() )
          .filter( direction -> isCorner( curr, region, direction ) )
          .count();
    }
    return sides;
  }

  private boolean isPerimeter(final Point point, final Set<Point> region,
      final Direction sideToCheck) {
    return region.contains( point ) && !region.contains( point.move( sideToCheck ) );
  }

  private boolean isCorner(final Point point, final Set<Point> region,
      final Direction sideToCheck) {
    boolean isInRegionA = region.contains( point.move( sideToCheck ) );
    boolean isInRegionB = region.contains( point.move( sideToCheck.rotateCounterClockwise() ) );
    boolean isInRegionC =
        region.contains( point.move( sideToCheck ).move( sideToCheck.rotateCounterClockwise() ) );
    boolean convex = !isInRegionA && !isInRegionB;
    boolean concave = isInRegionA && isInRegionB && !isInRegionC;
    return convex || concave;
  }
}
