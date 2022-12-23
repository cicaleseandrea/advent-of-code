package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class AoC172022 implements Solution {

  private static final List<List<Pair<Long, Long>>> ROCKS = List.of(
      List.of( new Pair<>( 2L, 0L ), new Pair<>( 3L, 0L ), new Pair<>( 4L, 0L ),
          new Pair<>( 5L, 0L ) ),
      List.of( new Pair<>( 3L, 0L ), new Pair<>( 2L, 1L ), new Pair<>( 4L, 1L ),
          new Pair<>( 3L, 2L ) ),
      List.of( new Pair<>( 2L, 0L ), new Pair<>( 3L, 0L ), new Pair<>( 4L, 0L ),
          new Pair<>( 4L, 1L ), new Pair<>( 4L, 2L ) ),
      List.of( new Pair<>( 2L, 0L ), new Pair<>( 2L, 1L ), new Pair<>( 2L, 2L ),
          new Pair<>( 2L, 3L ) ),
      List.of( new Pair<>( 2L, 0L ), new Pair<>( 3L, 0L ), new Pair<>( 2L, 1L ),
          new Pair<>( 3L, 1L ) ) );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final Set<Pair<Long, Long>> blocks = new HashSet<>();
    final Map<State, Value> statesSeen = new HashMap<>();
    final var jetPattern = getFirstString( input );
    var jetIndex = 0;
    var maxHeight = 0L;
    var heightToAdd = 0L;
    var cycleFound = false;
    final var totalRounds = first ? 2022L : 1000000000000L;

    for ( long round = 0L; round < totalRounds; round++ ) {
      final var rockType = (int) (round % ROCKS.size());
      final var rock = getRock( rockType, maxHeight );
      //move rock
      var stopMoving = false;
      while ( !stopMoving ) {
        //move horizontally
        move( rock, blocks, jetPattern.charAt( jetIndex ) == '>' ? 1 : -1, 0 );
        jetIndex = incrementMod( jetIndex, jetPattern.length() );
        //move down
        if ( !move( rock, blocks, 0, -1 ) ) {
          stopMoving = true;
        }
      }
      //update blocks
      blocks.addAll( rock );
      //compute max height
      maxHeight = blocks.stream().mapToLong( Pair::getSecond ).max().orElseThrow();

      //compute state
      final var key = new State( rockType, getTopRows( blocks, maxHeight ) );
      //check for cycle
      if ( !cycleFound && statesSeen.containsKey( key ) ) {
        cycleFound = true;
        final var value = statesSeen.get( key );
        final var roundsPerCycle = round - value.round;
        final var roundsLeft = totalRounds - round;
        final var cyclesToSkip = roundsLeft / roundsPerCycle;
        final var roundsToSkip = cyclesToSkip * roundsPerCycle;
        //skip rounds
        round += roundsToSkip;
        //compute height to add to the final result
        final var cycleHeight = maxHeight - value.maxHeight;
        heightToAdd = cyclesToSkip * cycleHeight;
      }
      statesSeen.put( key, new Value( round, maxHeight ) );
    }
    return itoa( maxHeight + heightToAdd );
  }

  private Set<Pair<Long, Long>> getTopRows(final Set<Pair<Long, Long>> blocks,
      final long maxHeight) {
    //get top 30 rows
    return blocks.stream().filter( block -> maxHeight - block.getSecond() < 30 )
        //get positions relative to top row
        .map( block -> new Pair<>( block.getFirst(), maxHeight - block.getSecond() ) )
        .collect( toSet() );
  }

  private boolean move(final List<Pair<Long, Long>> rock, final Set<Pair<Long, Long>> blocks,
      final int moveX, final int moveY) {
    //check next positions
    final var hit = rock.stream()
        .map( block -> new Pair<>( block.getFirst() + moveX, block.getSecond() + moveY ) ).anyMatch(
            block -> block.getFirst() < 0 || block.getFirst() > 6 || block.getSecond() < 1
                || blocks.contains( block ) );
    if ( !hit ) {
      //move
      rock.forEach( block -> {
        block.setFirst( block.getFirst() + moveX );
        block.setSecond( block.getSecond() + moveY );
      } );
    }
    return !hit;
  }

  private List<Pair<Long, Long>> getRock(final int rockType, final long maxHeight) {
    return ROCKS.get( rockType ).stream()
        .map( block -> new Pair<>( block.getFirst(), block.getSecond() + maxHeight + 4 ) ).toList();
  }

  private record State(int rockType, Set<Pair<Long, Long>> topRows) {

  }

  private record Value(long round, long maxHeight) {

  }
}
