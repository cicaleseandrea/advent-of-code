package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toCollection;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.GraphUtils;
import com.adventofcode.utils.Point;
import com.adventofcode.utils.Utils;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class AoC182024 implements Solution {

  private static final int EXAMPLE_SIZE = 7;
  private static final int INPUT_SIZE = 71;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    List<String> lines = input.toList();
    boolean isExample = lines.size() < INPUT_SIZE;
    int maxCorruption = isExample ? 12 : 1024;
    int size = isExample ? EXAMPLE_SIZE : INPUT_SIZE;
    Point start = new Point( 0, 0 );
    Point end = new Point( size - 1, size - 1 );
    Set<Point> corruptedPoints = lines.stream()
        .map( this::getCorrupted )
        .collect( toCollection( HashSet::new ) );
    //iterate in reverse order and remove points to speed up part 2
    for ( int i = lines.size() - 1; i >= maxCorruption; i-- ) {
      Point corrupted = getCorrupted( lines.get( i ) );
      corruptedPoints.remove( corrupted );
      if ( !first || i == maxCorruption ) {
        Map<Point, Long> distances = GraphUtils.computeShortestPaths( start, n -> n.equals( end ),
            (Point n) -> getNeighbours( n, corruptedPoints, size ) );
        if ( first ) {
          return itoa( distances.get( end ) );
        } else if ( distances.containsKey( end ) ) {
          //this block was preventing the exit from being reachable
          return corrupted.i() + "," + corrupted.j();
        }
      }
    }
    throw new IllegalArgumentException();
  }

  private Point getCorrupted(final String line) {
    List<Long> numbers = Utils.toLongList( line );
    return new Point( numbers.get( 0 ).intValue(), numbers.get( 1 ).intValue() );
  }

  private Collection<Point> getNeighbours(final Point curr, final Set<Point> corruptedPoints,
      final int size) {
    return Stream.of( Direction.values() )
        .map( curr::move )
        .filter( n -> 0 <= n.i() && n.i() < size && 0 <= n.j() && n.j() < size )
        .filter( n -> !corruptedPoints.contains( n ) )
        .toList();
  }
}
