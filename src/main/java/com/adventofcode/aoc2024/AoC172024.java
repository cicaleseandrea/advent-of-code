package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Computer2024.EIGHT;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2024;
import com.adventofcode.utils.Utils;
import com.google.common.base.Preconditions;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
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
    List<Integer> instructions = numbers.get( 4 ).stream().map( Number::intValue ).toList();
    BigInteger a = BigInteger.valueOf( numbers.get( 0 ).get( 0 ) );
    if ( first ) {
      List<Integer> output = runComputer( instructions, a );
      return output.stream().map( Object::toString ).collect( Collectors.joining( "," ) );
    }
    BigInteger result = ZERO;
    BigInteger mul = EIGHT.pow( instructions.size() - 1 );
    for ( int i = instructions.size() - 1; i >= 0; i-- ) {
      a = result.divide( mul );
      int digitWanted = instructions.get( i );
      BigInteger offset = ZERO;
      while ( runComputer( instructions, a ).get( 0 ) != digitWanted ) {
        a = a.add( ONE );
        offset = offset.add( ONE );
      }
      result = result.add( offset.multiply( mul ) );
      if ( offset.compareTo( EIGHT ) < 0 ) {
        mul = mul.divide( EIGHT );
      } else {
        //crossed the boundary. go back to previous digit
        i += 2;
        mul = mul.multiply( EIGHT );
      }
    }
    Preconditions.checkArgument( runComputer( instructions, result ).equals( instructions ),
        "result: " + result + " instructions: " + instructions );
    return result.toString();
  }

  private List<Integer> runComputer(final List<Integer> instructions, final BigInteger a) {
    var computer = new Computer2024( instructions, a );
    while ( computer.step() ) {
      //keep running
    }
    return computer.getOutput();
  }
}
