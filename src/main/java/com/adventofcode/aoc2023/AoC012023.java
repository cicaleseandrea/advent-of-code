package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.comparingInt;

import com.adventofcode.Solution;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

class AoC012023 implements Solution {

  private static final Map<String, Integer> DIGIT_TO_DIGIT = Map.of( "1", 1, "2", 2, "3", 3, "4", 4,
      "5", 5, "6", 6, "7", 7, "8", 8, "9", 9 );
  private static final Map<String, Integer> LETTER_TO_DIGIT = Map.of( "one", 1, "two", 2, "three",
      3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8, "nine", 9 );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, DIGIT_TO_DIGIT );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    final HashMap<String, Integer> digitsMap = new HashMap<>();
    digitsMap.putAll( DIGIT_TO_DIGIT );
    digitsMap.putAll( LETTER_TO_DIGIT );
    return solve( input, digitsMap );
  }

  private String solve(final Stream<String> input, final Map<String, Integer> digitsMap) {
    final long sum = input.mapToLong( str -> getCalibrationValue( str, digitsMap ) ).sum();
    return itoa( sum );
  }

  private static int getCalibrationValue(String str, final Map<String, Integer> digitsMap) {
    final int first = getDigit( str, digitsMap, String::indexOf, true );
    final int last = getDigit( str, digitsMap, String::lastIndexOf, false );
    return first * 10 + last;
  }

  private static int getDigit(final String str, final Map<String, Integer> digitsMap,
      BiFunction<String, String, Integer> indexOf, boolean first) {
    final List<Integer> digitsSorted = digitsMap.entrySet().stream()
        .map( e -> new Digit( indexOf.apply( str, e.getKey() ), e.getValue() ) )
        .filter( digit -> digit.index() >= 0 ).sorted( comparingInt( Digit::index ) )
        .map( Digit::value ).toList();

    return first ? digitsSorted.get( 0 ) : digitsSorted.get( digitsSorted.size() - 1 );
  }

  private record Digit(int index, int value) {

  }
}
