package com.adventofcode.aoc2024;


import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

class AoC252024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    if ( !first ) {
      return MERRY_CHRISTMAS;
    }
    List<int[]> locks = new ArrayList<>();
    List<int[]> keys = new ArrayList<>();
    var itr = input.iterator();
    while ( itr.hasNext() ) {
      String firstLine = itr.next();
      if ( firstLine.isEmpty() ) {
        continue;
      }
      if ( firstLine.charAt( 0 ) == HASH ) {
        locks.add( getPins( itr, false ) );
      } else {
        keys.add( getPins( itr, true ) );
      }
    }
    return itoa( keys.stream()
        .mapToLong( key -> locks.stream().filter( lock -> fits( key, lock ) ).count() )
        .sum() );
  }

  private int[] getPins(final Iterator<String> itr, final boolean isKey) {
    int[] pins = new int[5];
    for ( int i = 0; i < 6; i++ ) {
      String line = itr.next();
      for ( int j = 0; !(isKey && i == 5) && (j < pins.length); j++ ) {
        if ( line.charAt( j ) == HASH ) {
          pins[j]++;
        }
      }
    }
    return pins;
  }

  private boolean fits(int[] key, int[] lock) {
    for ( int i = 0; i < lock.length; i++ ) {
      if ( lock[i] + key[i] > 5 ) {
        return false;
      }
    }
    return true;
  }
}
