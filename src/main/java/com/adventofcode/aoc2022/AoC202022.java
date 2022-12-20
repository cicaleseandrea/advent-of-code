package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

class AoC202022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var original = input.map( Utils::atol ).map( n -> n * (first ? 1L : 811589153L) )
        .map( MyNumber::new ).toList();
    final var list = new ArrayList<>( original );
    final var size = original.size();
    for ( int i = 0; i < (first ? 1 : 10); i++ ) {
      for ( final var n : original ) {
        final var currentIndex = list.indexOf( n );
        final var nextIndex = Math.floorMod( currentIndex + n.value, size - 1 );
        list.remove( currentIndex );
        list.add( nextIndex, n );
      }
    }

    final var zeroIndex = list.indexOf( new MyNumber( 0L ) );
    final var valueOne = list.get( (zeroIndex + 1000) % size );
    final var valueTwo = list.get( (zeroIndex + 2000) % size );
    final var valueThree = list.get( (zeroIndex + 3000) % size );
    return itoa( valueOne.value + valueTwo.value + valueThree.value );
  }

  //the input list has duplicate numbers! 🤬🤬🤬
  //so we need to redefine equality to find their index in the list
  private record MyNumber(long value) {

    @Override
    public boolean equals(final Object o) {
      final MyNumber other = (MyNumber) o;
      //equal if they are the same object, or if their value is 0 (there can only be one 0 in the input list)
      return this == other || (value == 0L && other.value == 0L);
    }

    @Override
    public int hashCode() {
      return Objects.hash( value );
    }
  }
}
