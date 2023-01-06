package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.stream.Stream;

class AoC152017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var numbers = input.map( Utils::extractIntegerFromString ).toList();
    long a = numbers.get( 0 );
    long b = numbers.get( 1 );
    int res = 0;
    for ( int i = 0; i < (first ? 40_000_000 : 5_000_000); i++ ) {
      a = getNextNumber( a, 16807, first ? 1 : 4 );
      b = getNextNumber( b, 48271, first ? 1 : 8 );
      if ( a % 65536 == b % 65536 ) {
        res++;
      }
    }
    return itoa( res );
  }

  private long getNextNumber(long n, final long mul, final long check) {
    do {
      n = (n * mul) % 2147483647;
    } while ( n % check != 0 );
    return n;
  }
}
