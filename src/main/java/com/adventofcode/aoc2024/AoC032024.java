package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.joining;

import com.adventofcode.Solution;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class AoC032024 implements Solution {

  private static final Pattern MUL_REGEX = Pattern.compile( "mul\\((\\d+),(\\d+)\\)" );
  private static final String DO = "do()";
  private static final String DONT = "don't()";

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    String instructions = input.collect( joining() );
    int result = 0;
    int instructionEnd = instructions.length();
    int enabledStart = 0;
    while ( enabledStart != -1 ) {
      int enabledEnd = instructions.indexOf( DONT, enabledStart );
      if ( first || enabledEnd == -1 ) {
        enabledEnd = instructionEnd;
      }

      String enabledInstructions = instructions.substring( enabledStart, enabledEnd );
      result += computeMultiplications( enabledInstructions );

      enabledStart = instructions.indexOf( DO, enabledEnd );
    }

    return itoa( result );
  }

  private int computeMultiplications(final String instructions) {
    return MUL_REGEX.matcher( instructions )
        .results()
        .mapToInt( match -> {
          int a = Integer.parseInt( match.group( 1 ) );
          int b = Integer.parseInt( match.group( 2 ) );
          return a * b;
        } )
        .sum();
  }

}
