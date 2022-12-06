package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;
import static java.util.stream.Stream.concat;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.Comparator;
import java.util.stream.Stream;

class AoC152016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve(
        concat( input, Stream.of( "Disc #7 has 11 positions; at time=0, it is at position 0." ) ) );
  }

  private String solve(final Stream<String> input) {
    final var discs = input.map( this::getPair )
        .sorted( Comparator.<Pair<Long, Long>>comparingLong( Pair::getFirst ).reversed() ).toList();

    long increment = discs.get( 0 ).getFirst();
    long res = discs.get( 0 ).getSecond();
    for ( final var pair : discs.subList( 1, discs.size() ) ) {
      final long mod = pair.getFirst();
      final long value = pair.getSecond();
      while ( res % mod != value ) {
        res += increment;
      }
      increment *= mod;
    }
    return itoa( res );
  }

  private Pair<Long, Long> getPair(final String input) {
    final var numbers = toLongList( input );
    final var mod = numbers.get( 1 );
    final var value = Math.floorMod( -(numbers.get( 0 ) + numbers.get( 3 )), mod );
    return new Pair<>( mod, value );
  }
}
