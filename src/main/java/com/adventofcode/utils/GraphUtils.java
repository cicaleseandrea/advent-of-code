package com.adventofcode.utils;

import static java.util.Comparator.comparingLong;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

public class GraphUtils {

  private GraphUtils() {
  }

  private static <T> Map<T, Long> computeShortestPaths(final T start, final Predicate<T> end,
      final Function<T, Collection<T>> getNeighbours) {
    //unweighted graph
    return computeShortestPathsInternal( start, end, getNeighbours, null );
  }

  public static <T> Map<T, Long> computeShortestPaths(final T start, final Predicate<T> end,
      final Function<T, Collection<T>> getNeighbours,
      final ToLongFunction<T> getNeighbourDistance) {
    //weighted graph
    Preconditions.checkNotNull( getNeighbourDistance );
    return computeShortestPathsInternal( start, end, getNeighbours, getNeighbourDistance );
  }

  private static <T> Map<T, Long> computeShortestPathsInternal(final T start,
      final Predicate<T> end, final Function<T, Collection<T>> getNeighbours,
      ToLongFunction<T> getNeighbourDistance) {
    final Map<T, Long> distances = new HashMap<>();
    distances.put( start, 0L );
    final Queue<T> queue;
    if ( getNeighbourDistance == null ) {
      //unweighted graph: BFS
      queue = new LinkedList<>();
      getNeighbourDistance = n -> 1L;
    } else {
      //weighted graph: Dijkstra
      queue = new PriorityQueue<>( comparingLong( distances::get ) );
    }
    queue.add( start );

    while ( !queue.isEmpty() ) {
      final T curr = queue.remove();
      if ( end.test( curr ) ) {
        return distances;
      }
      for ( final T neighbour : getNeighbours.apply( curr ) ) {
        final long neighbourDistance = getNeighbourDistance.applyAsLong( neighbour );
        final long newDistance = distances.get( curr ) + neighbourDistance;
        if ( newDistance < distances.getOrDefault( neighbour, Long.MAX_VALUE ) ) {
          //update distance
          distances.put( neighbour, newDistance );
          //add to the queue
          //queue.remove( neighbour );
          queue.add( neighbour );
        }
      }
    }

    return distances;
  }

  public static <T> Map<T, Long> computeShortestPaths(final T start,
      final Function<T, Collection<T>> getNeighbours) {
    return computeShortestPaths( start, end -> false, getNeighbours );
  }

  public static <T> Map<T, Long> computeShortestPaths(final T start,
      final Function<T, Collection<T>> getNeighbours,
      final ToLongFunction<T> getNeighbourDistance) {
    return computeShortestPaths( start, end -> false, getNeighbours, getNeighbourDistance );
  }

  public static <T> long computeShortestPath(final T start, final Predicate<T> end,
      final Function<T, Collection<T>> getNeighbours) {
    final Map<T, Long> distances = computeShortestPaths( start, end, getNeighbours );
    return getShortest( distances, end );
  }

  public static <T> long computeShortestPath(final T start, final Predicate<T> end,
      final Function<T, Collection<T>> getNeighbours,
      final ToLongFunction<T> getNeighbourDistance) {
    final Map<T, Long> distances = computeShortestPaths( start, end, getNeighbours,
        getNeighbourDistance );
    return getShortest( distances, end );
  }

  public static <T> Set<T> fill(final T start, final Function<T, Collection<T>> getNeighbours) {
    return computeShortestPaths( start, end -> false, getNeighbours ).keySet();
  }

  public static <T> Set<T> fill(final T start, final Function<T, Collection<T>> getNeighbours,
      final ToLongFunction<T> getNeighbourDistance) {
    return computeShortestPaths( start, end -> false, getNeighbours,
        getNeighbourDistance ).keySet();
  }

  private static <T> long getShortest(final Map<T, Long> distances, final Predicate<T> end) {
    return distances.keySet().stream().filter( end ).mapToLong( distances::get ).min()
        .orElseThrow( () -> new IllegalArgumentException( "Could not reach the end" ) );
  }

}
