package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static com.google.common.collect.Lists.reverse;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Iterables;
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
        .map( history -> first ? history : reverse( history ) )
        .mapToLong( AoC092023::extrapolateValue ).sum();
    return itoa( sum );
  }

  private static long extrapolateValue(List<Long> history) {
    long value = 0;
    while ( history.stream().anyMatch( n -> n != 0 ) ) {
      value += Iterables.getLast( history );
      final List<Long> next = new ArrayList<>();
      for ( int i = 1; i < history.size(); i++ ) {
        next.add( history.get( i ) - history.get( i - 1 ) );
      }
      history = next;
    }
    return value;
  }
}
