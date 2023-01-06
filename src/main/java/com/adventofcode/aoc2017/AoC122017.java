package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.DisjointSet;
import com.adventofcode.utils.Utils;
import java.util.stream.Stream;

class AoC122017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final DisjointSet<Long> forest = new DisjointSet<>();
    input.map( Utils::toPositiveLongList ).forEach( numbers -> {
      numbers.forEach( forest::makeSet );
      for ( int i = 1; i < numbers.size(); i++ ) {
        forest.union( numbers.get( 0 ), numbers.get( i ) );
      }
    } );

    return itoa( first ? forest.getSize( 0L ) : forest.getSize() );
  }

}
