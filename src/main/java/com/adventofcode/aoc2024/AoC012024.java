package com.adventofcode.aoc2024;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class AoC012024 implements Solution {


  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    List<Integer> firstList = new ArrayList<>();
    List<Integer> secondList = new ArrayList<>();
    Map<Integer, Integer> secondListFrequencies = new HashMap<>();
    input.map( Utils::splitOnTabOrSpace )
        .forEach( pair -> {
          int a = Integer.parseInt( pair.get( 0 ) );
          int b = Integer.parseInt( pair.get( 1 ) );
          firstList.add( a );
          secondList.add( b );
          secondListFrequencies.merge( b, 1, Integer::sum );
        } );
    Collections.sort( firstList );
    Collections.sort( secondList );

    int result = 0;
    for ( int i = 0; i < firstList.size(); i++ ) {
      int firstNumber = firstList.get( i );
      int secondNumber = secondList.get( i );
      int distance = Math.abs( firstNumber - secondNumber );
      int score = firstNumber * secondListFrequencies.getOrDefault( firstNumber, 0 );
      result += first ? distance : score;
    }

    return Utils.itoa( result );
  }

}
