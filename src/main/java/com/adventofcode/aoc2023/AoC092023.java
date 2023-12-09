package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class AoC092023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private static String solve(final Stream<String> input, final boolean first) {
    final long sum = input.map( Utils::toLongList )
        .mapToLong( history -> extrapolateValue( history, first ) ).sum();
    return itoa( sum );
  }

  private static long extrapolateValue(List<Long> history, final boolean firstPart) {
    long value = 0;
    boolean add = true;
    while ( history.stream().anyMatch( l -> l != 0 ) ) {
      if ( firstPart ) {
        value += history.get( history.size() - 1 );
      } else {
        final long firstValue = history.get( 0 );
        value += add ? firstValue : -firstValue;
        add = !add;
      }

      final List<Long> next = new ArrayList<>();
      for ( int i = 1; i < history.size(); i++ ) {
        next.add( history.get( i ) - history.get( i - 1 ) );
      }
      history = next;
    }
    return value;
  }
}
