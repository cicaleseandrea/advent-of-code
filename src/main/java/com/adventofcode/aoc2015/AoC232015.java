package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.stream.Stream;

class AoC232015 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final int[] registers = {first ? 0 : 1, 0};
    var curr = 0;
    final var instructions = input.toList();
    while ( curr < instructions.size() ) {
      final var line = instructions.get( curr );
      final var instruction = line.substring( 0, 3 );
      switch ( instruction ) {
        case "hlf" -> {
          registers[getRegisterNumber( line )] /= 2;
          curr++;
        }
        case "tpl" -> {
          registers[getRegisterNumber( line )] *= 3;
          curr++;
        }
        case "inc" -> {
          registers[getRegisterNumber( line )] += 1;
          curr++;
        }
        case "jmp" -> curr += getJump( line );
        case "jie" -> curr += registers[getRegisterNumber( line )] % 2 == 0 ? getJump( line ) : 1;
        case "jio" -> curr += registers[getRegisterNumber( line )] == 1 ? getJump( line ) : 1;
        default -> throw new IllegalStateException( "Unexpected value: " + instruction );
      }
    }
    return itoa( registers[1] );
  }

  private int getJump(final String line) {
    return extractIntegerFromString( line );
  }

  private int getRegisterNumber(final String line) {
    return line.charAt( 4 ) - 'a';
  }

}
