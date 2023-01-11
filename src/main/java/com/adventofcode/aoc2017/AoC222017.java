package com.adventofcode.aoc2017;

import static com.adventofcode.aoc2017.AoC222017.State.CLEAN;
import static com.adventofcode.aoc2017.AoC222017.State.FLAGGED;
import static com.adventofcode.aoc2017.AoC222017.State.INFECTED;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.comparingInt;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class AoC222017 implements Solution {

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
    final var nodes = getNodes( inputList );
    var curr = new Pair<>( inputList.size() / 2L, inputList.size() / 2L );
    var direction = Direction.UP;
    var infected = 0;
    for ( int i = 0; i < (first ? 10_000 : 10_000_000); i++ ) {
      final var state = nodes.getOrDefault( curr, CLEAN );
      if ( state == INFECTED ) {
        direction = direction.rotateClockwise();
      } else if ( state == CLEAN ) {
        direction = direction.rotateCounterClockwise();
      } else if ( state == FLAGGED ) {
        direction = direction.reverse();
      }

      final var nextState = state.next( first );
      nodes.put( curr, nextState );
      if ( nextState == INFECTED ) {
        infected++;
      }

      curr = new Pair<>( curr );
      direction.move( curr );
    }
    return itoa( infected );
  }

  private Map<Pair<Long, Long>, State> getNodes(final List<String> input) {
    final Map<Pair<Long, Long>, State> infected = new HashMap<>();
    for ( int i = 0; i < input.size(); i++ ) {
      final var line = input.get( i );
      for ( int j = 0; j < line.length(); j++ ) {
        if ( line.charAt( j ) == HASH ) {
          infected.put( new Pair<>( (long) i, (long) j ), INFECTED );
        }
      }
    }
    return infected;
  }

  enum State {
    CLEAN( 0 ), WEAKENED( 1 ), INFECTED( 2 ), FLAGGED( 3 );

    static final List<State> BY_VALUE = Arrays.stream( values() )
        .sorted( comparingInt( State::getValue ) ).toList();
    final int value;

    State(final int value) {
      this.value = value;
    }

    public State next(final boolean first) {
      return BY_VALUE.get( incrementMod( value, first ? 2 : 1, BY_VALUE.size() ) );
    }

    private int getValue() {
      return value;
    }
  }
}
