package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.stream.Stream;

class AoC042022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, this::totalOverlap );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, this::partialOverlap );
  }

  private String solve(final Stream<String> input,
      final Function4<Long, Long, Long, Long, Boolean> overlaps) {
    final var count = input.map( Utils::toPositiveLongList ).filter(
        numbers -> overlaps.apply( numbers.get( 0 ), numbers.get( 1 ), numbers.get( 2 ),
            numbers.get( 3 ) ) ).count();
    return itoa( count );
  }

  private boolean totalOverlap(final long a1, final long b1, final long a2, final long b2) {
    return (a1 <= a2 && b1 >= b2) || (a1 >= a2 && b1 <= b2);
  }

  private boolean partialOverlap(final long a1, final long b1, final long a2, final long b2) {
    return !(a1 > b2 || a2 > b1);
  }

  @FunctionalInterface
  private interface Function4<T1, T2, T3, T4, R> {

    R apply(T1 t1, T2 t2, T3 t3, T4 t4);
  }

}
