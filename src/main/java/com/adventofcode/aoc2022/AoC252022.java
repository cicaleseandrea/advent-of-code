package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;

import com.adventofcode.Solution;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.stream.Stream;

class AoC252022 implements Solution {

  private static final BiMap<Character, Integer> DIGITS = ImmutableBiMap.of( '=', -2, '-', -1, '0',
      0, '1', 1, '2', 2 );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return toSnafu( input.mapToLong( this::toDecimal ).sum() );
  }

  private String toSnafu(final long decimal) {
    final var snafu = new StringBuilder();
    long division = decimal;
    while ( division != 0 ) {
      int remainder = (int) (division % 5);
      if ( remainder > 2 ) {
        remainder -= 5;
      }
      division -= remainder;
      snafu.append( DIGITS.inverse().get( remainder ) );
      division /= 5;
    }
    return snafu.reverse().toString();
  }

  private long toDecimal(final String snafu) {
    long decimal = 0L;
    for ( final var c : snafu.toCharArray() ) {
      decimal *= 5;
      decimal += DIGITS.get( c );
    }
    return decimal;
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return MERRY_CHRISTMAS;
  }
}
