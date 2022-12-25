package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.floorMod;
import static java.util.stream.Stream.concat;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

class AoC242022 implements Solution {

  private static final List<Pair<Integer, Integer>> NEIGHBOURS = concat(
      Stream.of( new Pair<>( 0, 0 ) ), NEIGHBOURS_4.stream() ).toList();

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var valley = input.map( String::toCharArray ).toArray( char[][]::new );
    final var start = new Pair<>( 1, 0 );
    final var end = new Pair<>( valley[0].length - 2, valley.length - 1 );

    final var firstTrip = computeTime( start, end, valley, 0 );
    if ( first ) {
      return itoa( firstTrip );
    } else {
      return itoa(
          computeTime( start, end, valley, computeTime( end, start, valley, firstTrip ) ) );
    }
  }

  private int computeTime(final Pair<Integer, Integer> source,
      final Pair<Integer, Integer> destination, char[][] valley, final int startTime) {
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
      for ( final var nextPosition : getNextPositions( currState.position, nextTime, valley ) ) {
        final var nextState = new State( nextPosition, nextTime );
        if ( seen.add( nextState ) ) {
          //add state never seen before to the queue
          queue.add( nextState );
        }
      }
    }

    throw new IllegalStateException();
  }

  private List<Pair<Integer, Integer>> getNextPositions(final Pair<Integer, Integer> position,
      final int time, final char[][] valley) {
    return NEIGHBOURS.stream().map( n -> new Pair<>( position.getFirst() + n.getFirst(),
            position.getSecond() + n.getSecond() ) )
        //inside map
        .filter( n -> 0 <= n.getFirst() && n.getFirst() < valley[0].length && 0 <= n.getSecond()
            && n.getSecond() < valley.length )
        //inside valley
        .filter( n -> valley[n.getSecond()][n.getFirst()] != HASH )
        //no blizzard
        .filter( nextPosition -> !isBlizzard( nextPosition, time, valley ) ).toList();
  }

  private boolean isBlizzard(final Pair<Integer, Integer> position, final int time,
      final char[][] valley) {
    final var x = position.getFirst();
    final var y = position.getSecond();
    final var xPlusT = getBlizzardPosition( x + time, valley[0].length - 2 );
    final var xMinusT = getBlizzardPosition( x - time, valley[0].length - 2 );
    final var yPlusT = getBlizzardPosition( y + time, valley.length - 2 );
    final var yMinusT = getBlizzardPosition( y - time, valley.length - 2 );
    return valley[y][xPlusT] == '<' || valley[y][xMinusT] == '>' || valley[yPlusT][x] == '^'
        || valley[yMinusT][x] == 'v';
  }

  private int getBlizzardPosition(final int i, final int size) {
    //wrap around edges
    return floorMod( i - 1, size ) + 1;
  }

  private record State(Pair<Integer, Integer> position, int time) {

  }
}
