package com.adventofcode.utils;

import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class GraphUtils {

  private GraphUtils() {
  }

  private static <T> Map<T, Long> computeShortestPaths(final T start, final Optional<T> end,
      final Function<T, Collection<T>> getNeighbours) {
    //BFS
    final Deque<T> queue = new LinkedList<>();
    final Map<T, Long> distances = new HashMap<>();

    queue.add( start );
    distances.put( start, 0L );

    while ( !queue.isEmpty() ) {
      final T curr = queue.remove();
      if ( end.isPresent() && curr.equals( end.get() ) ) {
        return distances;
      }
      for ( final T neighbour : getNeighbours.apply( curr ) ) {
        if ( !distances.containsKey( neighbour ) ) {
          //add to the queue
          queue.add( neighbour );
          //add distance from source
          distances.put( neighbour, distances.get( curr ) + 1 );
        }
      }
    }

    return distances;
  }

  public static <T> Map<T, Long> computeShortestPaths(final T start,
      final Function<T, Collection<T>> getNeighbours) {
    return computeShortestPaths( start, Optional.empty(), getNeighbours );
  }

  public static <T> long computeShortestPath(final T start, final T end,
      final Function<T, Collection<T>> getNeighbours) {
    return computeShortestPaths( start, Optional.of( end ), getNeighbours ).get( end );
  }

  public static <T> Set<T> fill(final T start, final Function<T, Collection<T>> getNeighbours) {
    return computeShortestPaths( start, Optional.empty(), getNeighbours ).keySet();
  }
}
