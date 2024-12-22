package com.adventofcode.aoc2024;


import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.Stream;

class AoC222024 implements Solution {

  private static final Map<State, Integer> SEQ_TO_BANANAS = new ConcurrentHashMap<>();
  private static final LongAccumulator MAX = new LongAccumulator( Long::max, 0 );
  private static final int B16777216 = 0b111111111111111111111111;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    SEQ_TO_BANANAS.clear();
    MAX.reset();
    long sum = input
        .parallel()
        .map( Utils::extractIntegerFromString )
        .mapToLong( secret -> getNumber( secret, first ) )
        .sum();
    if ( first ) {
      return itoa( sum );
    } else {
      return itoa( MAX.get() );
    }
  }

  private long getNumber(final long secret, final boolean first) {
    Set<State> seen = new HashSet<>();
    long a = getNextNumber( secret );
    long b = getNextNumber( a );
    long c = getNextNumber( b );
    long d = getNextNumber( c );
    for ( int i = 5; i <= 2000; i++ ) {
      long e = getNextNumber( d );
      State state = new State(
          (int) (b % 10 - a % 10),
          (int) (c % 10 - b % 10),
          (int) (d % 10 - c % 10),
          (int) (e % 10 - d % 10) );
      if ( !first && seen.add( state ) ) {
        long sum = SEQ_TO_BANANAS.merge( state, (int) (e % 10), Integer::sum );
        MAX.accumulate( sum );
      }
      a = b;
      b = c;
      c = d;
      d = e;
    }
    return d;
  }

  private long getNextNumber(long secret) {
    secret = ((secret << 6) ^ secret) & B16777216;
    secret = ((secret >> 5) ^ secret) & B16777216;
    secret = ((secret << 11) ^ secret) & B16777216;
    return secret;
  }

  private record State(int a, int b, int c, int d) {

  }
}
