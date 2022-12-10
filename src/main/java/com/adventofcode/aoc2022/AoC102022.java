package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.BLACK;
import static com.adventofcode.utils.Utils.WHITE;
import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.getIterable;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.shouldPrint;
import static java.lang.Math.abs;

import com.adventofcode.Solution;
import java.util.stream.Stream;

class AoC102022 implements Solution {

  private static final int SCREEN_WIDTH = 40;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    var cycle = 1;
    var register = 1;
    var strength = 0;
    var check = SCREEN_WIDTH / 2;
    final var image = new StringBuilder();

    for ( final var line : getIterable( input ) ) {
      final var instruction = line.split( " " );
      for ( int i = 0; i < instruction.length; i++ ) {
        // cycle start
        if ( cycle == check ) {
          strength += cycle * register;
          check += SCREEN_WIDTH;
        }
        drawCharacter( cycle, register, image );
        // cycle end
        cycle++;
        if ( i == 1 ) {
          // addx
          register += atol( instruction[1] );
        }
      }
    }

    if ( first ) {
      return itoa( strength );
    } else {
      if ( shouldPrint() ) {
        System.out.println( image );
      }
      return image.toString();
    }
  }

  private void drawCharacter(final int cycle, final int register, final StringBuilder image) {
    final var position = (cycle - 1) % SCREEN_WIDTH;
    if ( abs( position - register ) <= 1 ) {
      image.append( WHITE );
    } else {
      image.append( BLACK );
    }

    if ( position == SCREEN_WIDTH - 1 ) {
      image.append( '\n' );
    }
  }
}
