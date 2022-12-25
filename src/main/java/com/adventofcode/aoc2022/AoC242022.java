package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Stream.concat;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class AoC242022 implements Solution {

  private static final List<Pair<Integer, Integer>> NEIGHBOURS = concat(
      Stream.of( new Pair<>( 0, 0 ) ), NEIGHBOURS_4.stream() ).toList();

  private static final Map<Character, Pair<Integer, Integer>> MOVES = Map.of( '>',
      new Pair<>( 1, 0 ), '<', new Pair<>( -1, 0 ), '^', new Pair<>( 0, -1 ), 'v',
      new Pair<>( 0, 1 ) );

  private Pair<Integer, Integer> start;
  private Pair<Integer, Integer> end;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    //precompute all possible blizzards combinations
    final var valleys = getValleys( input.toList() );

    final var firstTrip = computeTime( start, end, valleys, 0 );
    if ( first ) {
      return itoa( firstTrip );
    } else {
      return itoa(
          computeTime( start, end, valleys, computeTime( end, start, valleys, firstTrip ) ) );
    }
  }

  private int computeTime(final Pair<Integer, Integer> source,
      final Pair<Integer, Integer> destination, List<Valley> valleys, final int startTime) {
    //BFS to find shortest path (unweighted graph, no need for Dijkstra)
    final var queue = new LinkedList<State>();
    final var seen = new HashSet<>();

    //start from source
    final var startState = new State( source, startTime );
    queue.add( startState );
    seen.add( startState );

    while ( !queue.isEmpty() ) {
      final var currState = queue.remove();
      if ( destination.equals( currState.position ) ) {
        return currState.time;
      }

      final var nextTime = currState.time + 1;
      final var nextValley = valleys.get( nextTime % valleys.size() );
      for ( final var nextPosition : getNextPositions( currState.position, nextValley ) ) {
        final var nextState = new State( nextPosition, nextTime );
        if ( seen.add( nextState ) ) {
          //add state never seen before to the queue
          queue.add( nextState );
        }
      }
    }

    throw new IllegalStateException();
  }

  private List<Pair<Integer, Integer>> getNextPositions(final Pair<Integer, Integer> currPosition,
      final Valley valley) {
    return NEIGHBOURS.stream().map( n -> new Pair<>( currPosition.getFirst() + n.getFirst(),
            currPosition.getSecond() + n.getSecond() ) )
        //inside valley
        .filter( n -> 0 < n.getFirst() && n.getFirst() < end.getFirst() + 1 && 0 < n.getSecond()
            && n.getSecond() < end.getSecond() || n.equals( start ) || n.equals( end ) )
        //no blizzard
        .filter( nextPosition -> !valley.blizzards.contains( nextPosition ) ).toList();
  }

  private Map<Pair<Integer, Integer>, Set<Character>> computeNextValley(
      final Map<Pair<Integer, Integer>, Set<Character>> valley) {
    final Map<Pair<Integer, Integer>, Set<Character>> nextValley = new HashMap<>();
    valley.keySet().forEach( position -> valley.get( position ).forEach( c -> {
      final var move = MOVES.get( c );
      final var x = getNextBlizzardPosition( position.getFirst() + move.getFirst(),
          end.getFirst() + 1 );
      final var y = getNextBlizzardPosition( position.getSecond() + move.getSecond(),
          end.getSecond() );
      nextValley.computeIfAbsent( new Pair<>( x, y ), k -> new HashSet<>() ).add( c );
    } ) );
    return nextValley;
  }

  private int getNextBlizzardPosition(final int i, final int max) {
    //wrap around edges
    return Math.floorMod( i - 1, max - 1 ) + 1;
  }

  private List<Valley> getValleys(final List<String> input) {
    var valley = getInitialValley( input );
    final var max = valley.keySet().stream()
        .max( comparingInt( Pair<Integer, Integer>::getFirst ).thenComparingInt( Pair::getSecond ) )
        .map( p -> new Pair<>( p.getFirst() + 1, p.getSecond() + 1 ) ).orElseThrow();
    start = new Pair<>( 1, 0 );
    end = new Pair<>( max.getFirst() - 1, max.getSecond() );

    //precompute all possible blizzards combinations
    final List<Valley> valleys = new ArrayList<>();
    while ( !valleys.contains( new Valley( valley.keySet() ) ) ) {
      valleys.add( new Valley( valley.keySet() ) );
      valley = computeNextValley( valley );
    }
    return valleys;
  }

  private Map<Pair<Integer, Integer>, Set<Character>> getInitialValley(final List<String> input) {
    final Map<Pair<Integer, Integer>, Set<Character>> blizzards = new HashMap<>();
    for ( int y = 0; y < input.size(); y++ ) {
      final var row = input.get( y );
      for ( int x = 0; x < row.length(); x++ ) {
        final var c = row.charAt( x );
        if ( MOVES.containsKey( c ) ) {
          blizzards.computeIfAbsent( new Pair<>( x, y ), k -> new HashSet<>() ).add( c );
        }
      }
    }
    return blizzards;
  }

  private record State(Pair<Integer, Integer> position, int time) {

  }

  private record Valley(Set<Pair<Integer, Integer>> blizzards) {

  }
}
