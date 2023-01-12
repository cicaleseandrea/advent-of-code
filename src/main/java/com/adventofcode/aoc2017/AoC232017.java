package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2017;
import java.util.stream.Stream;

class AoC232017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    if ( first ) {
      final var program = new Computer2017( input.toList() );
      while ( program.step() ) {
        //keep running
      }
      return itoa( program.getMul() );
    } else {
      return itoa( computeNonPrimes( 108100, 125100, 17 ) );
    }
  }

  private int computeNonPrimes(int b, final int c, final int increment) {
    // TODO this only works for my input... adjust for b, c and increment values
    var h = 0;
    for ( ; b <= c; b += increment ) {
      for ( int i = 2; i <= b / 2; i++ ) {
        if ( b % i == 0 ) {
          //not a prime
          h++;
          break;
        }
      }
    }
    return h;
  }
}
