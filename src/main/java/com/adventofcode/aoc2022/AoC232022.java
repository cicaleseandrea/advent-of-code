package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.NEIGHBOURS_8;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class AoC232022 implements Solution {

  public static final List<Pair<Integer, Integer>> NEIGHBOURS_N = List.of( new Pair<>( 0, -1 ),
      new Pair<>( -1, -1 ), new Pair<>( 1, -1 ) );
  public static final List<Pair<Integer, Integer>> NEIGHBOURS_S = List.of( new Pair<>( 0, 1 ),
      new Pair<>( -1, 1 ), new Pair<>( 1, 1 ) );
  public static final List<Pair<Integer, Integer>> NEIGHBOURS_W = List.of( new Pair<>( -1, 0 ),
      new Pair<>( -1, -1 ), new Pair<>( -1, 1 ) );
  public static final List<Pair<Integer, Integer>> NEIGHBOURS_E = List.of( new Pair<>( 1, 0 ),
      new Pair<>( 1, -1 ), new Pair<>( 1, 1 ) );

  public static final List<List<Pair<Integer, Integer>>> MOVES = List.of( NEIGHBOURS_N,
      NEIGHBOURS_S, NEIGHBOURS_W, NEIGHBOURS_E );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var elves = getElves( input.toList() );

    boolean moved = false;
    int round = 0;
    while ( round < 10 || (!first && moved) ) {
      final var movements = getMovements( elves, round );
      for ( final var move : movements.entrySet() ) {
        //remove old position
        elves.remove( move.getKey() );
        //add new position
        elves.add( move.getValue() );
      }
      moved = !movements.isEmpty();
      round++;
    }
    return itoa( first ? countEmptyTiles( elves ) : round );
  }

  private Map<Pair<Integer, Integer>, Pair<Integer, Integer>> getMovements(
      final Set<Pair<Integer, Integer>> elves, final int firstMove) {
    final Map<Pair<Integer, Integer>, Pair<Integer, Integer>> movements = new HashMap<>();
    elves.stream().filter( elf -> hasNeighbour( elf, elves, NEIGHBOURS_8 ) ).forEach( elf -> {
      final var nextPos = computeNextPosition( elf, elves, firstMove );
      if ( nextPos != null ) {
        movements.put( elf, nextPos );
      }
    } );

    //keep only movements to unique positions
    final var nextPositions = movements.values();
    final var nextPosFrequencies = nextPositions.stream()
        .collect( groupingBy( identity(), counting() ) );
    nextPositions.removeIf( nextPos -> nextPosFrequencies.get( nextPos ) > 1 );

    return movements;
  }

  private Pair<Integer, Integer> computeNextPosition(final Pair<Integer, Integer> elf,
      final Set<Pair<Integer, Integer>> elves, final int firstMove) {
    int move = firstMove;
    Pair<Integer, Integer> nextPos;
    do {
      nextPos = move( elf, elves, MOVES.get( move % MOVES.size() ) );
      move++;
    } while ( nextPos == null && move < firstMove + MOVES.size() );
    return nextPos;
  }

  private Pair<Integer, Integer> move(final Pair<Integer, Integer> elf,
      final Set<Pair<Integer, Integer>> elves, final List<Pair<Integer, Integer>> neighbours) {
    if ( hasNeighbour( elf, elves, neighbours ) ) {
      return null;
    }
    final var nextDirection = neighbours.get( 0 );
    return new Pair<>( elf.getFirst() + nextDirection.getFirst(),
        elf.getSecond() + nextDirection.getSecond() );
  }

  private boolean hasNeighbour(final Pair<Integer, Integer> elf,
      final Set<Pair<Integer, Integer>> elves, final List<Pair<Integer, Integer>> neighbours) {
    return neighbours.stream().map( neighbour -> new Pair<>( elf.getFirst() + neighbour.getFirst(),
        elf.getSecond() + neighbour.getSecond() ) ).anyMatch( elves::contains );
  }

  private int countEmptyTiles(final Set<Pair<Integer, Integer>> elves) {
    final var minMaxX = elves.stream().mapToInt( Pair::getFirst ).summaryStatistics();
    final var minMaxY = elves.stream().mapToInt( Pair::getSecond ).summaryStatistics();
    final var area =
        (minMaxX.getMax() - minMaxX.getMin() + 1) * (minMaxY.getMax() - minMaxY.getMin() + 1);
    return area - elves.size();
  }

  private Set<Pair<Integer, Integer>> getElves(final List<String> input) {
    final var elves = new HashSet<Pair<Integer, Integer>>();
    for ( int y = 0; y < input.size(); y++ ) {
      final var row = input.get( y );
      for ( int x = 0; x < row.length(); x++ ) {
        if ( row.charAt( x ) == HASH ) {
          elves.add( new Pair<>( x, y ) );
        }
      }
    }
    return elves;
  }
}
