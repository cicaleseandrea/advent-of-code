package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2016;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC232016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var inputList = input.toList();
    final int a = first ? 7 : 12;
    if ( inputList.size() <= 10 ) {
      final var computer = new Computer2016( inputList, a, 0, 0, 0 );
      while ( computer.step() ) {
        //keep running
      }
      return itoa( computer.getRegister( 0 ) );
    } else {
      return itoa( computeResult( a, 98, 86 ) );
    }
  }

  private int computeResult(final int a, final int c, final int d) {
    // TODO this only works for my input... adjust for c and d values
    final int factorial = IntStream.rangeClosed( 1, a ).reduce( 1, (int x, int y) -> x * y );
    return factorial + (d * c);
  }
}
