package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.getFirstString;

import com.adventofcode.Solution;
import java.util.stream.Stream;

class AoC162016 implements Solution {

  private static final char ZERO = '0';
  private static final char ONE = '1';

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 272 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 35651584 );
  }

  private String solve(final Stream<String> input, final int inputLength) {
    final var data = new StringBuilder( getFirstString( input ) );
    final var diskLength = data.length() > 10 ? inputLength : 20;

    while ( data.length() < diskLength ) {
      expandData( data );
    }

    return computeChecksum( data.substring( 0, diskLength ) );
  }

  private String computeChecksum(final String data) {
    var checksum = data;
    do {
      final var tmp = new StringBuilder();
      for ( int i = 1; i < checksum.length(); i += 2 ) {
        tmp.append( checksum.charAt( i - 1 ) == checksum.charAt( i ) ? ONE : ZERO );
      }
      checksum = tmp.toString();
    } while ( checksum.length() % 2 == 0 );
    return checksum;
  }

  private void expandData(final StringBuilder data) {
    final var length = data.length();
    data.append( ZERO );
    for ( int i = length - 1; i >= 0; i-- ) {
      final var c = data.charAt( i );
      data.append( c == ZERO ? ONE : ZERO );
    }
  }
}
