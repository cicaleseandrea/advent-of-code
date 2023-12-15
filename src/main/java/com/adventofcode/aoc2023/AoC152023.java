package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.primitives.Ints;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class AoC152023 implements Solution {

  private static final Pattern STEP_REGEX = Pattern.compile( "([a-zA-Z]+)[=\\-](\\d*)" );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private static String solve(final Stream<String> input, final boolean first) {
    final Stream<String> sequence = input.flatMap( line -> Utils.splitOnRegex( line, "," ) );
    if ( first ) {
      final int sum = sequence.mapToInt( AoC152023::computeHash ).sum();
      return itoa( sum );
    }

    final List<Step> steps = sequence.map( AoC152023::toStep ).toList();
    final Map<Integer, LinkedHashMap<String, Integer>> boxes = fillBoxes( steps );
    return itoa( computeFocusingPower( boxes ) );
  }

  private static Map<Integer, LinkedHashMap<String, Integer>> fillBoxes(final List<Step> steps) {
    final Map<Integer, LinkedHashMap<String, Integer>> boxes = new HashMap<>();
    for ( Step step : steps ) {
      final var box = boxes.computeIfAbsent( step.computeHash(), k -> new LinkedHashMap<>() );
      final String label = step.label;
      step.focalLength.ifPresentOrElse( v -> box.put( label, v ), () -> box.remove( label ) );
    }
    return boxes;
  }

  private static int computeFocusingPower(
      final Map<Integer, LinkedHashMap<String, Integer>> boxes) {
    int sum = 0;
    for ( int boxNumber : boxes.keySet() ) {
      int slotNumber = 1;
      for ( int focalLength : boxes.get( boxNumber ).values() ) {
        sum += (boxNumber + 1) * slotNumber * focalLength;
        slotNumber++;
      }
    }
    return sum;
  }

  private static int computeHash(final String str) {
    int hash = 0;
    for ( final char c : str.toCharArray() ) {
      hash = ((hash + c) * 17) % 256;
    }
    return hash;
  }

  private static Step toStep(final String str) {
    final var matcher = STEP_REGEX.matcher( str );
    if ( !matcher.matches() ) {
      throw new IllegalArgumentException();
    }
    final String label = matcher.group( 1 );
    final Optional<Integer> focalLength = Optional.ofNullable(
        Ints.tryParse( matcher.group( 2 ) ) );
    return new Step( label, focalLength );
  }

  private record Step(String label, Optional<Integer> focalLength) {

    int computeHash() {
      return AoC152023.computeHash( label );
    }
  }
}
