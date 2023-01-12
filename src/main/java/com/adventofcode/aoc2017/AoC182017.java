package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2017;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;

class AoC182017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var instructions = input.toList();
    final Deque<Long> queueA = new LinkedList<>();
    final Deque<Long> queueB = new LinkedList<>();
    final var programA = new Computer2017( instructions, queueA, queueB, 0 );
    final var programB = new Computer2017( instructions, queueB, queueA, 1 );
    while ( programA.step() || (!first && programB.step()) ) {
      //keep running
    }

    return itoa( first ? queueB.peekLast() : programB.getSnd() );
  }
}
