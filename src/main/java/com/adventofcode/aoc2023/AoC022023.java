package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.max;

import com.adventofcode.Solution;
import com.google.common.collect.Streams;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class AoC022023 implements Solution {

  private static final Pattern RED_REGEX = Pattern.compile( "(\\d+) red" );
  private static final Pattern GREEN_REGEX = Pattern.compile( "(\\d+) green" );
  private static final Pattern BLUE_REGEX = Pattern.compile( "(\\d+) blue" );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, (str, index) -> isPossible( str ) ? index + 1 : 0 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, (str, index) -> getPower( str ) );
  }

  private String solve(final Stream<String> input,
      final BiFunction<String, Long, Long> gameToNumber) {
    return itoa(
        Streams.mapWithIndex( input, gameToNumber::apply ).mapToLong( Long::longValue ).sum() );
  }

  private static boolean isPossible(final String str) {
    for ( String reveal : str.split( ";" ) ) {
      final RGB rgb = getRGB( reveal );
      if ( !(rgb.red <= 12 && rgb.green <= 13 && rgb.blue <= 14) ) {
        return false;
      }
    }
    return true;
  }

  private static long getPower(final String str) {
    long red = 0;
    long green = 0;
    long blue = 0;
    for ( String reveal : str.split( ";" ) ) {
      final RGB rgb = getRGB( reveal );
      red = max( red, rgb.red );
      green = max( green, rgb.green );
      blue = max( blue, rgb.blue );
    }
    return red * green * blue;
  }

  private static RGB getRGB(final String str) {
    return new RGB( getColor( RED_REGEX, str ), getColor( GREEN_REGEX, str ),
        getColor( BLUE_REGEX, str ) );
  }

  private static long getColor(final Pattern colorRegex, final String str) {
    final Matcher matcher = colorRegex.matcher( str );
    return matcher.find() ? atoi( matcher.group( 1 ) ) : 0;
  }

  private record RGB(long red, long green, long blue) {

  }

}
