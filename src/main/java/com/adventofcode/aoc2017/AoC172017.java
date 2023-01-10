package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.stream.Stream;

class AoC172017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final int steps = extractIntegerFromString( getFirstString( input ) );
    final ArrayList<Integer> buffer = new ArrayList<>();
    buffer.add( 0 );

    var pos = 0;
    var res = 0;
    for ( int i = 1; i <= (first ? 2017 : 50_000_000); i++ ) {
      pos = getNextPos( pos, steps, i );
      if ( first ) {
        buffer.add( pos, i );
      } else if ( pos == 1 ) {
        res = i;
      }
    }

    return itoa( first ? buffer.get( pos + 1 ) : res );
  }

  private static int getNextPos(final int pos, final int steps, final int size) {
    return incrementMod( pos, steps, size ) + 1;
  }

}
