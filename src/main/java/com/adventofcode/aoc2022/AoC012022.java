package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.Arrays.stream;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.joining;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.stream.Stream;

class AoC012022 implements Solution {

  private static final String DELIMITER = "~";

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 1 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 3 );
  }

  private String solve(final Stream<String> input, final int top) {
    final var oneLine = input.collect( joining( DELIMITER ) );
    final var elves = stream( oneLine.split( DELIMITER + DELIMITER ) );
    final var elvesSums = elves.map(
        calories -> stream( calories.split( DELIMITER ) ).mapToLong( Utils::atol ).sum() );
    final var topValues = elvesSums.sorted( reverseOrder() ).limit( top );
    return itoa( topValues.mapToLong( Long::longValue ).sum() );
  }
}
