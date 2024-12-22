package com.adventofcode.aoc2024;


import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class AoC222024 implements Solution {

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
    var results = input.map( Utils::extractIntegerFromString ).map( this::getResults );
    if ( first ) {
      return itoa( results.mapToLong( Pair::getFirst ).sum() );
    } else {
      List<Map<State, Integer>> seqToDigitList = results.map( Pair::getSecond ).toList();
      int max = seqToDigitList.stream()
          .map( Map::keySet )
          .flatMap( Set::stream )
          .distinct()
          .parallel() //TODO speedup
          .mapToInt( seq ->
              seqToDigitList.stream()
                  .mapToInt( seqToDigit -> seqToDigit.getOrDefault( seq, 0 ) )
                  .sum()
          ).max().getAsInt();
      return itoa( max );
    }
  }

  private Pair<Long, Map<State, Integer>> getResults(final long secret) {
    Map<State, Integer> seqToDigit = new HashMap<>();
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
      seqToDigit.putIfAbsent( state, (int) (e % 10) );
      a = b;
      b = c;
      c = d;
      d = e;
    }
    return new Pair<>( d, seqToDigit );
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
