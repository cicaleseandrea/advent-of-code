package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.decrementMapElement;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.incrementMapElement;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.HashMap;
import java.util.stream.Stream;

class AoC062022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 4 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 14 );
  }

  private String solve(final Stream<String> input, final int size) {
    final var signal = getFirstString( input );
    final var frequencies = new HashMap<Character, Long>();
    for ( int i = 0; i < signal.length(); i++ ) {
      if ( i >= size ) {
        final var first = signal.charAt( i - size );
        decrementMapElement( frequencies, first );
      }
      final var last = signal.charAt( i );
      incrementMapElement( frequencies, last );
      if ( frequencies.size() == size ) {
        return itoa( i + 1L );
      }
    }
    throw new IllegalStateException();
  }
}
