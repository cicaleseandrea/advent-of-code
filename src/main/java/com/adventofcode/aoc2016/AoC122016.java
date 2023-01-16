package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2016;
import java.util.stream.Stream;

class AoC122016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var computer = new Computer2016( input.toList(), 0, 0, first ? 0 : 1, 0 );
    while ( computer.step() ) {
      //keep running
    }

    return itoa( computer.getRegister( 0 ) );
  }
}
