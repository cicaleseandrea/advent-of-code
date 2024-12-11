package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class AoC112024 implements Solution {

  //used for memoization
  private static final Map<State, Long> CACHE = new HashMap<>();

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 25 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 75 );
  }

  private String solve(final Stream<String> input, final int steps) {
    List<Long> numbers = Utils.toLongList( Utils.getFirstString( input ) );
    long stones = numbers.stream().mapToLong( number -> countStones( number, steps ) ).sum();
    return itoa( stones );
  }

  private long countStones(final long number, int steps) {
    State state = new State( number, steps );
    if ( CACHE.containsKey( state ) ) {
      return CACHE.get( state );
    }

    int nDigits = (int) Math.log10( number ) + 1;
    long result;
    if ( steps == 0 ) {
      result = 1;
    } else if ( nDigits % 2 == 0 ) {
      long div = (long) Math.pow( 10, nDigits / 2.0 );
      long left = number / div;
      long right = number % div;
      result = countStones( left, steps - 1 ) + countStones( right, steps - 1 );
    } else {
      result = countStones( number == 0 ? 1 : number * 2024, steps - 1 );
    }

    CACHE.put( state, result );
    return result;
  }

  private record State(long number, int steps) {

  }
}
