package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

class AoC022024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, this::isStrictlySafe );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, this::isSafe );
  }

  private String solve(final Stream<String> input, final Predicate<List<Long>> safetyCheck) {
    return itoa( input.map( Utils::toLongList ).filter( safetyCheck ).count() );
  }

  private boolean isStrictlySafe(final List<Long> list) {
    boolean ascending = list.get( 0 ) < list.get( 1 );
    for ( int i = 1; i < list.size(); i++ ) {
      long prev = list.get( i - 1 );
      long next = list.get( i );
      //check ordering
      if ( ascending ? prev >= next : prev <= next ) {
        return false;
      }
      //check difference
      long diff = Math.abs( prev - next );
      if ( diff < 1 || 3 < diff ) {
        return false;
      }
    }
    return true;
  }

  private boolean isSafe(final List<Long> list) {
    for ( int i = 0; i < list.size(); i++ ) {
      List<Long> smallerList = new ArrayList<>( list );
      smallerList.remove( i );
      if ( isStrictlySafe( smallerList ) ) {
        return true;
      }
    }
    return false;
  }

}
