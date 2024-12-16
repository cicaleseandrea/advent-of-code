package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.comparingInt;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Point;
import com.google.common.collect.HashMultimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

class AoC162024 implements Solution {

  private static final char START = 'S';
  private static final char END = 'E';

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
    State start = new State( getPosition( grid, START ), RIGHT );
    Point end = getPosition( grid, END );
    var result = getResult( start, end, grid, first );
    if ( first ) {
      return itoa( result.getFirst() );
    }
    return itoa( getPointsOnAnyShortestPath( result.getSecond(), end ).size() );
  }

  private Pair<Integer, HashMultimap<State, State>> getResult(final State start, final Point end,
      final char[][] grid,
      final boolean first) {
    Map<State, Integer> distances = new HashMap<>();
    distances.put( start, 0 );
    Queue<State> queue;
    //weighted graph: Dijkstra
    queue = new PriorityQueue<>( comparingInt( distances::get ) );
    queue.add( start );
    HashMultimap<State, State> stateToPrevious = HashMultimap.create();
    while ( true ) {
      State curr = queue.remove();
      if ( curr.position.equals( end ) ) {
        return new Pair<>( distances.get( curr ), first ? HashMultimap.create() : stateToPrevious );
      }
      for ( Pair<State, Integer> neighbour : getNeighbours( curr, grid ) ) {
        State neighbourState = neighbour.getFirst();
        int neighbourDistance = neighbour.getSecond();
        int oldDistance = distances.getOrDefault( neighbourState, Integer.MAX_VALUE );
        int newDistance = distances.get( curr ) + neighbourDistance;
        if ( newDistance < oldDistance || (!first && newDistance == oldDistance) ) {
          //update distance
          distances.put( neighbourState, newDistance );
          //add to the queue
          queue.add( neighbourState );
          if ( newDistance < oldDistance ) {
            //reset path to neighbour
            stateToPrevious.get( neighbourState ).clear();
          }
          //update path to neighbour
          stateToPrevious.put( neighbourState, curr );
        }
      }
    }
  }

  private Set<Point> getPointsOnAnyShortestPath(final HashMultimap<State, State> stateToPrevious,
      final Point end) {
    Deque<State> queue = new LinkedList<>();
    Stream.of( Direction.values() ).map( d -> new State( end, d ) ).forEach( queue::add );
    Set<Point> points = new HashSet<>();
    while ( !queue.isEmpty() ) {
      State curr = queue.remove();
      points.add( curr.position );
      queue.addAll( stateToPrevious.get( curr ) );
    }
    return points;
  }

  private Collection<Pair<State, Integer>> getNeighbours(final State curr, final char[][] grid) {
    List<Pair<State, Integer>> neighbours = new ArrayList<>();
    neighbours.add(
        new Pair<>( new State( curr.position, curr.direction.rotateClockwise() ), 1000 ) );
    neighbours.add(
        new Pair<>( new State( curr.position, curr.direction.rotateCounterClockwise() ), 1000 ) );
    Point next = curr.position.move( curr.direction );
    if ( grid[next.i()][next.j()] != HASH ) {
      neighbours.add( new Pair<>( new State( next, curr.direction ), 1 ) );
    }
    return neighbours;
  }

  private Point getPosition(final char[][] grid, final char c) {
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        if ( grid[i][j] == c ) {
          return new Point( i, j );
        }
      }
    }
    throw new IllegalArgumentException( "No start found" );
  }

  private record State(Point position, Direction direction) {

  }
}
