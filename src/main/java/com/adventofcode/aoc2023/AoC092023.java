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

  private static long extrapolateValue(List<Long> history, final boolean first) {
    if ( history.stream().allMatch( l -> l == 0 ) ) {
      return 0;
    }
    final List<Long> next = new ArrayList<>();
    for ( int i = 1; i < history.size(); i++ ) {
      next.add( history.get( i ) - history.get( i - 1 ) );
    }
    final long nextValue = extrapolateValue( next, first );
    return first ? history.get( history.size() - 1 ) + nextValue : history.get( 0 ) - nextValue;
  }
}
