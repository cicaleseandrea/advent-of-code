package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Character.isDigit;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.adventofcode.Solution;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class AoC242016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var grid = getGrid( input );
    final var locations = getLocations( grid );

    //compute shortest paths between all locations
    final var distances = locations.values().stream().collect(
        toMap( identity(), pos -> computeShortestPaths( pos, locations.values(), grid ) ) );

    final var start = locations.remove( '0' );

    return itoa( computeShortestPath( start, new HashSet<>( locations.values() ), distances,
        first ? null : start ) );
  }

  private int computeShortestPath(final Point src, final Collection<Point> destinations,
      final Map<Point, Map<Point, Integer>> distances, final Point zero) {
    if ( destinations.isEmpty() ) {
      //if zero!=null, add distance from src to zero
      return distances.get( src ).getOrDefault( zero, 0 );
    }
    int min = Integer.MAX_VALUE;
    for ( final var location : new HashSet<>( destinations ) ) {
      destinations.remove( location );

      final int distance =
          distances.get( src ).get( location ) + computeShortestPath( location, destinations,
              distances, zero );
      if ( distance < min ) {
        min = distance;
      }

      destinations.add( location );
    }
    return min;
  }

  private Map<Character, Point> getLocations(final Character[][] grid) {
    final Map<Character, Point> locations = new HashMap<>();
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        final var c = grid[i][j];
        if ( isDigit( c ) ) {
          locations.put( c, new Point( i, j ) );
        }
      }
    }
    return locations;
  }

  private Map<Point, Integer> computeShortestPaths(final Point src,
      final Collection<Point> destinations, final Character[][] grid) {
    //BFS (unweighted graph, no need for Dijkstra) to find shortest paths to destinations
    final var queue = new LinkedList<Point>();
    final var distances = new HashMap<Point, Integer>();
    //start from source
    queue.add( src );
    distances.put( src, 0 );

    while ( !queue.isEmpty() ) {
      final var curr = queue.remove();
      for ( final var neighbour : getNeighbours( grid, curr ) ) {
        if ( !distances.containsKey( neighbour ) ) {
          //add to the queue
          queue.add( neighbour );
          //add distance from source
          distances.put( neighbour, distances.get( curr ) + 1 );
        }
      }
    }

    //keep only distances to destinations
    distances.keySet().removeIf( k -> !destinations.contains( k ) );

    return distances;
  }

  private List<Point> getNeighbours(final Character[][] grid, final Point e) {
    return NEIGHBOURS_4.stream().map( n -> new Point( n.getFirst() + e.x, n.getSecond() + e.y ) )
        .filter( n -> n.x >= 0 && n.x < grid.length && n.y >= 0 && n.y < grid[0].length )
        .filter( n -> grid[n.x][n.y] != HASH ).toList();
  }

  private Character[][] getGrid(final Stream<String> input) {
    return input.map( row -> row.chars().mapToObj( c -> (char) c ).toArray( Character[]::new ) )
        .toArray( Character[][]::new );
  }

  private record Point(int x, int y) {

  }
}
