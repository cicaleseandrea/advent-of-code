package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toPositiveLongList;

import com.adventofcode.Solution;
import java.math.BigInteger;
import java.util.stream.Stream;

class AoC252015 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    final var numbers = toPositiveLongList( getFirstString( input ) );
    final long row = numbers.get( 0 );
    final long column = numbers.get( 1 );
    final long position = getPosition( row, column );

    BigInteger result = BigInteger.valueOf( 20151125 );
    final BigInteger mul = BigInteger.valueOf( 252533 );
    final BigInteger mod = BigInteger.valueOf( 33554393 );
    for ( int i = 1; i < position; i++ ) {
      result = result.multiply( mul );
      result = result.mod( mod );
    }

    return itoa( result );
  }

  private long getPosition(final long row, final long column) {
    return 1 + ((row - 1) * row) / 2 + ((column - 1) * column) / 2 + ((column - 1) * row);
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return MERRY_CHRISTMAS;
  }

}
