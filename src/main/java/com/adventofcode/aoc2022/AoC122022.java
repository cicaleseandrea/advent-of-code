package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.IntStream.range;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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

    final Stream<Pair<Integer, Integer>> sources;
    if ( first ) {
      sources = Stream.of( src );
    } else {
      sources = range( 0, map.length ).boxed().flatMap(
          i -> range( 0, map[0].length ).filter( j -> map[i][j] == 'a' )
              .mapToObj( j -> new Pair<>( i, j ) ) );
    }

    final var steps = sources.mapToInt( s -> computeDistance( map, s, dst ) ).min().orElseThrow();
    return itoa( steps );
  }

  private int computeDistance(final char[][] map, final Pair<Integer, Integer> src,
      final Pair<Integer, Integer> dst) {
    //BFS to find shortest path (unweighted graph, no need for Dijkstra)
    final var queue = new LinkedList<Pair<Integer, Integer>>();
    final var distances = new HashMap<Pair<Integer, Integer>, Integer>();
    //start from source
    queue.add( src );
    distances.put( src, 0 );

    while ( !queue.isEmpty() ) {
      final var curr = queue.remove();
      if ( curr.equals( dst ) ) {
        return distances.get( dst );
      }

      for ( final var neighbour : findNeighbours( curr, map ) ) {
        if ( !distances.containsKey( neighbour ) ) {
          //add to the queue
          queue.add( neighbour );
          //add distance from source
          distances.put( neighbour, distances.get( curr ) + 1 );
        }
      }
    }

    return Integer.MAX_VALUE;
  }

  private Collection<Pair<Integer, Integer>> findNeighbours(final Pair<Integer, Integer> point,
      final char[][] map) {
    final var height = map[point.getFirst()][point.getSecond()];
    final var y = point.getFirst();
    final var x = point.getSecond();

    return NEIGHBOURS_4.stream()
        .map( neighbour -> new Pair<>( y + neighbour.getFirst(), x + neighbour.getSecond() ) )
        // add only cells that can be reached
        .filter( neighbour -> neighbour.getFirst() >= 0 && neighbour.getSecond() >= 0
            && neighbour.getFirst() < map.length && neighbour.getSecond() < map[0].length )
        .filter( neighbour -> map[neighbour.getFirst()][neighbour.getSecond()] - height <= 1 )
        .toList();
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
