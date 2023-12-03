package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.comparingInt;

import com.adventofcode.Solution;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

class AoC012023 implements Solution {

  private static final Map<String, Integer> DIGIT_TO_DIGIT = Map.of( "1", 1, "2", 2, "3", 3, "4", 4,
      "5", 5, "6", 6, "7", 7, "8", 8, "9", 9 );
  private static final Map<String, Integer> LETTERS_TO_DIGIT = Map.of( "one", 1, "two", 2, "three",
      3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8, "nine", 9 );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, DIGIT_TO_DIGIT );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    final HashMap<String, Integer> digitsMap = new HashMap<>();
    digitsMap.putAll( DIGIT_TO_DIGIT );
    digitsMap.putAll( LETTERS_TO_DIGIT );
    return solve( input, digitsMap );
  }

  private String solve(final Stream<String> input, final Map<String, Integer> digitsMap) {
    return itoa( input.mapToInt( str -> getCalibrationValue( str, digitsMap ) ).sum() );
  }

  private static int getCalibrationValue(String str, final Map<String, Integer> digitsMap) {
    final int first = getDigit( str, digitsMap, true );
    final int last = getDigit( str, digitsMap, false );
    return first * 10 + last;
  }

  private static int getDigit(final String str, final Map<String, Integer> digitsMap,
      boolean first) {
    final BiFunction<String, String, Integer> indexOf =
        first ? String::indexOf : String::lastIndexOf;
    final Comparator<Digit> comparator =
        first ? comparingInt( Digit::index ) : comparingInt( Digit::index ).reversed();

    return digitsMap.entrySet().stream()
        .map( e -> new Digit( e.getValue(), indexOf.apply( str, e.getKey() ) ) )
        .filter( digit -> digit.index() >= 0 ).min( comparator ).map( Digit::value ).orElseThrow();
  }

  private record Digit(int value, int index) {

  }
}
