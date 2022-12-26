package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;

import com.adventofcode.Solution;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class AoC252022 implements Solution {

  private static final Map<Character, Integer> VALUES = Map.of( '0', 0, '1', 1, '2', 2, '=', -2,
      '-', -1 );
  private static final List<Character> DIGITS = List.of( '0', '1', '2', '=', '-' );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return toSnafu( input.mapToLong( this::toDecimal ).sum() );
  }

  private String toSnafu(long decimal) {
    final var snafu = new StringBuilder();
    while ( decimal != 0 ) {
      final int remainder = (int) (decimal % 5);
      final char digit = DIGITS.get( remainder );
      decimal -= VALUES.get( digit );
      decimal /= 5;
      snafu.append( digit );
    }
    return snafu.reverse().toString();
  }

  private long toDecimal(final String snafu) {
    long decimal = 0L;
    for ( final char c : snafu.toCharArray() ) {
      decimal *= 5;
      decimal += VALUES.get( c );
    }
    return decimal;
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return MERRY_CHRISTMAS;
  }
}
