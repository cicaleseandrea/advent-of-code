package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

class AoC122022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var map = input.map( String::toCharArray ).toArray( char[][]::new );
    final var srcDst = getSourceAndDestination( map );
    final var src = srcDst.getFirst();
    final var dst = srcDst.getSecond();

    final List<Integer> distances;
    if ( first ) {
      distances = computeDistances( map, src, List.of( dst ), diff -> diff <= 1 );
    } else {
      final var lowPoints = range( 0, map.length ).boxed().flatMap(
          i -> range( 0, map[0].length ).filter( j -> map[i][j] == 'a' )
              .mapToObj( j -> new Pair<>( i, j ) ) ).collect( toSet() );
      distances = computeDistances( map, dst, lowPoints, diff -> diff >= -1 );
    }
    return itoa( Collections.min( distances ) );
  }

  private List<Integer> computeDistances(final char[][] map, final Pair<Integer, Integer> src,
      final Collection<Pair<Integer, Integer>> destinations, final IntPredicate climbable) {
    final var result = new ArrayList<Integer>();

    //BFS to find shortest path (unweighted graph, no need for Dijkstra)
    final var queue = new LinkedList<Pair<Integer, Integer>>();
    final var distances = new HashMap<Pair<Integer, Integer>, Integer>();
    //start from source
    queue.add( src );
    distances.put( src, 0 );

    while ( !queue.isEmpty() ) {
      final var curr = queue.remove();
      if ( destinations.contains( curr ) ) {
        result.add( distances.get( curr ) );
      }

      for ( final var neighbour : findNeighbours( curr, map, climbable ) ) {
        if ( !distances.containsKey( neighbour ) ) {
          //add to the queue
          queue.add( neighbour );
          //add distance from source
          distances.put( neighbour, distances.get( curr ) + 1 );
        }
      }
    }

    return result;
  }

  private Collection<Pair<Integer, Integer>> findNeighbours(final Pair<Integer, Integer> point,
      final char[][] map, final IntPredicate climbable) {
    final var height = map[point.getFirst()][point.getSecond()];
    final var y = point.getFirst();
    final var x = point.getSecond();

    return NEIGHBOURS_4.stream()
        .map( neighbour -> new Pair<>( y + neighbour.getFirst(), x + neighbour.getSecond() ) )
        // add only cells that can be reached
        .filter( neighbour -> neighbour.getFirst() >= 0 && neighbour.getSecond() >= 0
            && neighbour.getFirst() < map.length && neighbour.getSecond() < map[0].length ).filter(
            neighbour -> climbable.test(
                map[neighbour.getFirst()][neighbour.getSecond()] - height ) ).toList();
  }

  private Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> getSourceAndDestination(
      final char[][] map) {
    Pair<Integer, Integer> src = null;
    Pair<Integer, Integer> dst = null;

    for ( int i = 0; i < map.length; i++ ) {
      for ( int j = 0; j < map[0].length; j++ ) {
        final char c = map[i][j];
        if ( c == 'S' ) {
          src = new Pair<>( i, j );
          map[i][j] = 'a';
        } else if ( c == 'E' ) {
          dst = new Pair<>( i, j );
          map[i][j] = 'z';
        }
      }
    }

    return new Pair<>( src, dst );
  }
}
