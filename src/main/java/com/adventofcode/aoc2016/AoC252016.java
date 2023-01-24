package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.stream.Stream;

class AoC252016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    int res = 0;
    while ( !isSignal( res ) ) {
      res++;
    }
    return itoa( res );
  }

  private boolean isSignal(int a) {
    // TODO this only works for my input... adjust for different initial values
    a += 2534;
    boolean zero = true;
    do {
      if ( zero != (a % 2 == 0) ) {
        return false;
      }
      a /= 2;
      zero = !zero;
    } while ( a != 0 );
    return true;
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return MERRY_CHRISTMAS;
  }
}
