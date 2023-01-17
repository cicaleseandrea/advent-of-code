package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toPositiveLongList;
import static java.util.Comparator.comparingInt;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AoC222016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var inputList = input.toList();
    final var nodes = getNodes( inputList.subList( 2, inputList.size() ) );
    if ( first ) {
      return itoa( getViable( nodes ) );
    } else {
      //remove nodes with too much data
      nodes.values().removeIf( n -> n.used() > (inputList.size() < 20 ? 10 : 100) );
      final var empty = nodes.keySet().stream().filter( n -> nodes.get( n ).used() == 0 ).findAny()
          .orElseThrow();
      final var goal = nodes.keySet().stream().filter( n -> n.y == 0 )
          .max( comparingInt( n -> n.x ) ).orElseThrow();
      return itoa( computeShortestPath( new State( empty, goal ), nodes.keySet() ) );
    }
  }

  private int computeShortestPath(final State src, final Set<Point> nodes) {
    //BFS (unweighted graph, no need for Dijkstra) to find shortest path to destination
    final var queue = new LinkedList<State>();
    final var distances = new HashMap<State, Integer>();

    queue.add( src );
    distances.put( src, 0 );

    final var zero = new Point( 0, 0 );
    while ( !queue.isEmpty() ) {
      final var curr = queue.remove();
      if ( curr.goal.equals( zero ) ) {
        return distances.get( curr );
      }
      for ( final var neighbour : getNeighbours( curr, nodes ) ) {
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

  private List<State> getNeighbours(final State curr, final Set<Point> nodes) {
    final var empty = curr.empty;
    final var goal = curr.goal;
    return NEIGHBOURS_4.stream().map(
            neighbour -> new Point( empty.x + neighbour.getFirst(), empty.y + neighbour.getSecond() ) )
        .filter( nodes::contains ).map( nextEmpty -> {
          final var nextGoal = nextEmpty.equals( goal ) ? empty : goal;
          return new State( nextEmpty, nextGoal );
        } ).toList();
  }

  private int getViable(final Map<Point, Node> nodes) {
    int viable = 0;
    for ( final var pair : Sets.combinations( nodes.keySet(), 2 ) ) {
      final var itr = pair.iterator();
      final var a = nodes.get( itr.next() );
      final var b = nodes.get( itr.next() );
      if ( a.canMoveTo( b ) ) {
        viable++;
      }
      if ( b.canMoveTo( a ) ) {
        viable++;
      }
    }
    return viable;
  }

  private Map<Point, Node> getNodes(final List<String> input) {
    return input.stream().map( Utils::splitOnTabOrSpace ).map( tokens -> {
      final var numbers = toPositiveLongList( tokens.get( 0 ) );
      final int x = numbers.get( 0 ).intValue();
      final int y = numbers.get( 1 ).intValue();

      return new Pair<>( new Point( x, y ), new Node( extractIntegerFromString( tokens.get( 2 ) ),
          extractIntegerFromString( tokens.get( 3 ) ) ) );
    } ).collect( Collectors.toMap( Pair::getFirst, Pair::getSecond ) );
  }

  private record Node(int used, int avail) {

    boolean canMoveTo(final Node other) {
      return used != 0 && other != this && used <= other.avail;
    }
  }

  private record State(Point empty, Point goal) {

  }

  private record Point(int x, int y) {

  }
}
