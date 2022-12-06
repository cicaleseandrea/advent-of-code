package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Character.isAlphabetic;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import java.util.List;
import java.util.stream.Stream;

class AoC122016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final int[] registers = {0, 0, first ? 0 : 1, 0};
    final var instructions = input.map( Utils::splitOnTabOrSpace ).toList();
    var curr = 0;
    while ( curr < instructions.size() ) {
      final var line = instructions.get( curr );
      final var instruction = line.get( 0 );
      switch ( instruction ) {
        case "cpy" -> {
          final var values = getValues( line, registers, false );
          registers[values.getSecond()] = values.getFirst();
        }
        case "inc" -> registers[getRegisterPosition( line.get( 1 ).charAt( 0 ) )]++;
        case "dec" -> registers[getRegisterPosition( line.get( 1 ).charAt( 0 ) )]--;
        case "jnz" -> {
          final var values = getValues( line, registers, true );
          if ( values.getFirst() != 0 ) {
            curr += values.getSecond() - 1;
          }
        }
        default -> throw new IllegalStateException( "Unexpected value: " + instruction );
      }
      curr++;
    }

    return itoa( registers[0] );
  }


  private Pair<Integer, Integer> getValues(final List<String> args, final int[] registers,
      final boolean extractRegisterValue) {
    final var first = getValue( args.get( 1 ), registers, true );
    final var second = getValue( args.get( 2 ), registers, extractRegisterValue );
    return new Pair<>( first, second );
  }

  private int getValue(final String s, final int[] registers, final boolean extractRegisterValue) {
    final var c = s.charAt( 0 );
    if ( isAlphabetic( c ) ) {
      final var registerPosition = getRegisterPosition( c );
      return extractRegisterValue ? registers[registerPosition] : registerPosition;
    } else {
      return atoi( s );
    }
  }

  private int getRegisterPosition(final char c) {
    return c - 'a';
  }

}
