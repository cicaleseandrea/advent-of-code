package com.adventofcode.aoc2024;


import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class AoC252024 implements Solution {

  private static final int MAX_HEIGHT = 7;

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
    List<String> inputList = input.toList();
    for ( int i = 0; i < inputList.size(); i += MAX_HEIGHT + 1 ) {
      int[] pins = getPins( inputList, i );
      if ( inputList.get( i ).charAt( 0 ) == HASH ) {
        locks.add( pins );
      } else {
        keys.add( pins );
      }
    }
    return itoa( keys.stream()
        .mapToLong( key -> locks.stream().filter( lock -> fits( key, lock ) ).count() )
        .sum() );
  }

  private int[] getPins(final List<String> inputList, final int index) {
    int[] pins = new int[5];
    for ( int i = 0; i < MAX_HEIGHT; i++ ) {
      String line = inputList.get( index + i );
      for ( int j = 0; j < pins.length; j++ ) {
        if ( line.charAt( j ) == HASH ) {
          pins[j]++;
        }
      }
    }
    return pins;
  }

  private boolean fits(int[] key, int[] lock) {
    for ( int i = 0; i < lock.length; i++ ) {
      if ( lock[i] + key[i] > MAX_HEIGHT ) {
        return false;
      }
    }
    return true;
  }
}
