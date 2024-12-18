package com.adventofcode.aoc2024;


import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.joining;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2024;
import com.adventofcode.utils.Utils;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.stream.Stream;

class AoC172024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    List<List<Long>> numbers = input.map( Utils::toLongList ).toList();
    List<Integer> instructions = numbers.get( 4 ).stream().map( Long::intValue ).toList();
    long a = first ? numbers.get( 0 ).get( 0 ) : findQuine( instructions );
    List<Integer> output = runComputer( instructions, a );
    Preconditions.checkArgument( first || output.equals( instructions ),
        "output for a=" + a + ": " + output + " instructions: " + instructions );
    return first ? output.stream().map( Object::toString ).collect( joining( "," ) ) : itoa( a );
  }

  private long findQuine(final List<Integer> instructions) {
    long a = 0;
    boolean backtrack = false;
    for ( int i = instructions.size() - 1; i >= 0; i-- ) {
      a = backtrack ? a / 8 : a * 8;
      int digitWanted = instructions.get( i );
      int offset = 0;
      while ( runComputer( instructions, a ).get( 0 ) != digitWanted ) {
        a++;
        offset++;
      }
      if ( offset >= 8 ) {
        //crossed the boundary. backtrack to the previous digit
        i += 2;
        backtrack = true;
      } else {
        backtrack = false;
      }
    }
    return a;
  }

  private List<Integer> runComputer(final List<Integer> instructions, final long a) {
    var computer = new Computer2024( instructions, a );
    while ( computer.step() ) {
      //keep running
    }
    return computer.getOutput();
  }
}
