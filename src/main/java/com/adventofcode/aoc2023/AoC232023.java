package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.comparing;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.GraphUtils;
import com.adventofcode.utils.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC232023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private static String solve(final Stream<String> input, final boolean first) {
    final char[][] grid = input.map( String::toCharArray ).toArray( char[][]::new );
    final Point start = getPathPosition( grid, 0 );
    final Point end = getPathPosition( grid, grid.length - 1 );
    //compress grid only keeping start, end and intersections nodes
    final Map<Point, Set<Edge>> edges = getEdges( grid, start, end, first );
    return itoa( getLongestPath( start, end, edges ) );
  }

  private static long getLongestPath(final Point start, final Point end,
      final Map<Point, Set<Edge>> edges) {
    final Map<Point, Long> longestPathDistances = new HashMap<>();
    computeLongestPaths( new HashSet<>(), 0, start, longestPathDistances, edges );
    return longestPathDistances.get( end );
  }

  private static void computeLongestPaths(final Set<Point> currPath, final long currPathLength,
      final Point currNode, final Map<Point, Long> longestPathDistances,
      final Map<Point, Set<Edge>> edges) {
    if ( longestPathDistances.getOrDefault( currNode, 0L ) < currPathLength ) {
      //update longest distance to current node
      longestPathDistances.put( currNode, currPathLength );
    }

    //current path goes through current node
    currPath.add( currNode );
    for ( final Edge edge : edges.get( currNode ) ) {
      if ( !currPath.contains( edge.point ) ) {
        //continue current path through neighbour only if not seen before
        computeLongestPaths( currPath, currPathLength + edge.distance, edge.point,
            longestPathDistances, edges );
      }
    }
    //current path does not go through current node
    currPath.remove( currNode );
  }

  private static Map<Point, Set<Edge>> getEdges(final char[][] grid, final Point start,
      final Point end, final boolean first) {
    final Map<Point, Set<Edge>> edges = new HashMap<>();
    final Queue<Point> queue = new LinkedList<>();
    queue.add( start );

    while ( !queue.isEmpty() ) {
      final Point curr = queue.remove();
      if ( curr.equals( end ) ) {
        //part 1: directed graph
        edges.put( end, new HashSet<>() );
        if ( !first ) {
          //part 2: undirected graph
          addReverseEdges( edges );
        }
        return edges;
      }

      for ( Direction direction : Direction.values() ) {
        //move by one step
        final Point shifted = curr.move( direction );
        if ( isInGrid( shifted, grid ) ) {
          final char c = grid[shifted.i()][shifted.j()];
          if ( c != HASH && !direction.reverse().equals( Direction.fromSymbol( c ) ) ) {
            //this direction is acceptable: BFS to next intersection/end
            final Map<Point, Long> distances = GraphUtils.computeShortestPaths( shifted,
                (Point n) -> getNeighbours( n, grid, false ).size() >= 3,
                n -> getNeighbours( n, grid, true ) );
            distances.keySet().stream().max( comparing( distances::get ) ).ifPresent(
                //save edge to farthest point (i.e. intersection/end)
                far -> {
                  edges.computeIfAbsent( curr, k -> new HashSet<>() )
                      .add( new Edge( far, distances.get( far ) + 1 ) );
                  if ( !edges.containsKey( far ) ) {
                    //add to queue if new node
                    queue.add( far );
                  }
                } );
          }
        }
      }
    }

    return edges;
  }

  private static List<Point> getNeighbours(final Point curr, final char[][] grid,
      final boolean checkDirection) {
    final Direction direction = Direction.fromSymbol( grid[curr.i()][curr.j()] );
    if ( checkDirection && direction != null ) {
      return List.of( curr.move( direction ) );
    }

    return Stream.of( Direction.values() ).map( curr::move ).filter( p -> isInGrid( p, grid ) )
        .filter( p -> grid[p.i()][p.j()] != HASH ).toList();
  }

  private static void addReverseEdges(final Map<Point, Set<Edge>> edges) {
    edges.keySet().forEach( source -> edges.get( source )
        .forEach( destination -> edges.get( destination.point )
            .add( new Edge( source, destination.distance ) ) ) );
  }

  private static boolean isInGrid(final Point p, final char[][] grid) {
    return p.i() >= 0 && p.j() >= 0 && p.i() < grid.length && p.j() < grid[0].length;
  }

  private static Point getPathPosition(final char[][] grid, final int row) {
    return IntStream.range( 0, grid[0].length ).filter( j -> grid[row][j] == DOT )
        .mapToObj( j -> new Point( row, j ) ).findAny().orElseThrow();
  }

  private record Edge(Point point, long distance) {

  }
}
