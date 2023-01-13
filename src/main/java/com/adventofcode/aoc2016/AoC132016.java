package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

class AoC132016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final int favourite = extractIntegerFromString( getFirstString( input ) );
    return itoa( computePaths( new Pair<>( 1, 1 ),
        new Pair<>( favourite <= 10 ? 7 : 31, favourite <= 10 ? 4 : 39 ), favourite, 50, first ) );
  }

  private int computePaths(final Pair<Integer, Integer> src, final Pair<Integer, Integer> dst,
      final int favourite, final int maxDistance, final boolean first) {
    //BFS (unweighted graph, no need for Dijkstra) to find:
    //shortest path to destination
    //all paths with max distance
    final var queue = new LinkedList<Pair<Integer, Integer>>();
    final var distances = new HashMap<Pair<Integer, Integer>, Integer>();
    //start from source
    queue.add( src );
    distances.put( src, 0 );

    while ( !queue.isEmpty() ) {
      final var curr = queue.remove();
      if ( first && curr.equals( dst ) ) {
        return distances.get( curr );
      } else if ( !first && distances.get( curr ) == maxDistance ) {
        return distances.size();
      }
      for ( final var neighbour : getNeighbours( curr, favourite ) ) {
        if ( !distances.containsKey( neighbour ) ) {
          //add to the queue
          queue.add( neighbour );
          //add distance from source
          distances.put( neighbour, distances.get( curr ) + 1 );
        }
      }
    }

    throw new IllegalStateException();
  }

  private List<Pair<Integer, Integer>> getNeighbours(final Pair<Integer, Integer> e,
      final int favourite) {
    return NEIGHBOURS_4.stream()
        .map( n -> new Pair<>( n.getFirst() + e.getFirst(), n.getSecond() + e.getSecond() ) )
        .filter( n -> n.getFirst() >= 0 && n.getSecond() >= 0 )
        .filter( n -> isOpen( n.getFirst(), n.getSecond(), favourite ) ).toList();
  }

  private boolean isOpen(final int x, final int y, final int favourite) {
    return Integer.bitCount( x * x + 3 * x + 2 * x * y + y + y * y + favourite ) % 2 == 0;
  }
}
