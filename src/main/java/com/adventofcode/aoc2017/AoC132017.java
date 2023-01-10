package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.stream.Stream;

class AoC132017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var layers = input.map( this::getPair ).toList();

    if ( first ) {
      final var res = layers.stream().filter( p -> p.getSecond() % p.getFirst() == 0 )
          .mapToLong( p -> p.getSecond() * getRange( p.getFirst() ) ).sum();
      return itoa( res );
    }

    boolean caught;
    var delay = 0;
    do {
      delay++;
      final var finalDelay = delay;
      caught = layers.stream().anyMatch( p -> (p.getSecond() + finalDelay) % p.getFirst() == 0 );
    } while ( caught );
    return itoa( delay );
  }

  private Pair<Long, Long> getPair(final String input) {
    final var numbers = toLongList( input );
    final var mod = getMod( numbers.get( 1 ) );
    final var value = numbers.get( 0 );
    return new Pair<>( mod, value );
  }

  private long getMod(final long n) {
    return n * 2 - 2;
  }

  private long getRange(final long n) {
    return (n + 2) / 2;
  }
}
