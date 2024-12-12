package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.GraphUtils;
import com.adventofcode.utils.Pair;
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
    return Stream.of( Direction.values() )
        .mapToLong( direction -> getSides( region, direction ) )
        .sum();
  }

  private long getSides(final Set<Point> region, final Direction sideToCheck) {
    boolean moveHorizontally = sideToCheck.equals( UP ) || sideToCheck.equals( DOWN );
    var indexingValues = getIndexingValues( region, moveHorizontally );
    var firstLoop = indexingValues.getFirst();
    var secondLoop = indexingValues.getSecond();
    int sides = 0;
    //scan the grid either horizontally or vertically depending on the side we are checking
    for ( int a = firstLoop.getFirst(); a <= firstLoop.getSecond(); a++ ) {
      boolean wasPerimeter = false;
      for ( int b = secondLoop.getFirst(); b <= secondLoop.getSecond(); b++ ) {
        Point curr = new Point( moveHorizontally ? a : b, moveHorizontally ? b : a );
        boolean isPerimeter = isPerimeter( curr, region, sideToCheck );
        //when perimeter stops, we count a side
        sides += wasPerimeter && !isPerimeter ? 1 : 0;
        wasPerimeter = isPerimeter;
      }
      sides += wasPerimeter ? 1 : 0;
    }
    return sides;
  }

  private boolean isPerimeter(final Point point, final Set<Point> region,
      final Direction sideToCheck) {
    return region.contains( point ) && !region.contains( point.move( sideToCheck ) );
  }

  private Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> getIndexingValues(
      final Set<Point> region, final boolean moveHorizontally) {
    int minI = region.stream().mapToInt( Point::i ).min().getAsInt();
    int minJ = region.stream().mapToInt( Point::j ).min().getAsInt();
    int maxI = region.stream().mapToInt( Point::i ).max().getAsInt();
    int maxJ = region.stream().mapToInt( Point::j ).max().getAsInt();
    var firstLoop = moveHorizontally ? new Pair<>( minI, maxI ) : new Pair<>( minJ, maxJ );
    var secondLoop = moveHorizontally ? new Pair<>( minJ, maxJ ) : new Pair<>( minI, maxI );
    return new Pair<>( firstLoop, secondLoop );
  }
}
