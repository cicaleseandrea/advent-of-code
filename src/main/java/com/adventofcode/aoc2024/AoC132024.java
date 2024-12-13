package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import java.util.List;
import java.util.stream.Stream;

class AoC132024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    List<String> lines = input.toList();
    long tokens = 0;
    for ( int i = 0; i < lines.size(); i++ ) {
      List<Long> a = Utils.toLongList( lines.get( i++ ) );
      List<Long> b = Utils.toLongList( lines.get( i++ ) );
      List<Long> prize = Utils.toLongList( lines.get( i++ ) );
      long prizeAdd = first ? 0 : 10000000000000L;
      Machine machine = new Machine(
          new Pair<>( a.get( 0 ), a.get( 1 ) ),
          new Pair<>( b.get( 0 ), b.get( 1 ) ),
          new Pair<>( prize.get( 0 ) + prizeAdd, prize.get( 1 ) + prizeAdd )
      );
      tokens += countTokens( machine );
    }
    return itoa( tokens );
  }

  private long countTokens(final Machine machine) {
    long div = machine.a.getFirst() * machine.b.getSecond()
        - machine.a.getSecond() * machine.b.getFirst();
    long x = (machine.b.getSecond() * machine.prize.getFirst()
        - machine.b.getFirst() * machine.prize.getSecond()) / div;
    long y = (machine.a.getFirst() * machine.prize.getSecond()
        - machine.a.getSecond() * machine.prize.getFirst()) / div;
    if ( x * machine.a.getFirst() + y * machine.b.getFirst() == machine.prize.getFirst()
        && x * machine.a.getSecond() + y * machine.b.getSecond() == machine.prize.getSecond() ) {
      return x * 3 + y;
    } else {
      return 0;
    }
  }

  private record Machine(Pair<Long, Long> a, Pair<Long, Long> b, Pair<Long, Long> prize) {

  }
}
