package com.adventofcode.utils;

import static com.adventofcode.utils.Utils.atoi;
import static java.lang.Character.isAlphabetic;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Computer2016 {

  private final List<List<String>> instructions;
  private final int[] registers;
  private final Queue<Integer> signal;
  private int op;

  public Computer2016(final List<String> instructions) {
    this( instructions, 0, 0, 0, 0 );
  }

  public Computer2016(final List<String> instructions, final int a, final int b, final int c,
      final int d) {
    this.instructions = instructions.stream().map( Utils::splitOnTabOrSpace ).map( ArrayList::new )
        .collect( toCollection( ArrayList::new ) );
    this.registers = new int[]{a, b, c, d};
    this.signal = new LinkedList<>();
    this.op = 0;
  }

  public boolean step() {
    if ( op >= instructions.size() ) {
      return false;
    }
    final var line = instructions.get( op );
    final var instruction = line.get( 0 );
    switch ( instruction ) {
      case "cpy" -> {
        final char register = line.get( 2 ).charAt( 0 );
        if ( isAlphabetic( register ) ) {
          final var x = getValue( line.get( 1 ) );
          final var y = getRegisterPosition( register );
          registers[y] = x;
        }
      }
      case "inc" -> {
        final char register = line.get( 1 ).charAt( 0 );
        if ( isAlphabetic( register ) ) {
          registers[getRegisterPosition( register )]++;
        }
      }
      case "dec" -> {
        final char register = line.get( 1 ).charAt( 0 );
        if ( isAlphabetic( register ) ) {
          registers[getRegisterPosition( register )]--;
        }
      }
      case "jnz" -> {
        final var x = getValue( line.get( 1 ) );
        final var y = getValue( line.get( 2 ) );
        if ( x != 0 ) {
          op += y - 1;
        }
      }
      case "out" -> signal.add( getValue( line.get( 1 ) ) );
      case "tgl" -> {
        final int tglOp = getValue( line.get( 1 ) ) + op;
        if ( tglOp >= 0 && tglOp < instructions.size() ) {
          final var tglLine = instructions.get( tglOp );
          final var tglInstruction = tglLine.get( 0 );
          final int tglArgs = tglLine.size() - 1;
          if ( tglArgs == 1 ) {
            tglLine.set( 0, tglInstruction.equals( "inc" ) ? "dec" : "inc" );
          } else if ( tglArgs == 2 ) {
            tglLine.set( 0, tglInstruction.equals( "jnz" ) ? "cpy" : "jnz" );
          } else {
            throw new IllegalArgumentException();
          }
        }
      }
      default -> throw new IllegalStateException( "Unexpected value: " + instruction );
    }
    op++;
    return true;
  }

  private int getValue(final String s) {
    final var c = s.charAt( 0 );
    if ( isAlphabetic( c ) ) {
      return registers[getRegisterPosition( c )];
    } else {
      return atoi( s );
    }
  }

  private int getRegisterPosition(final char c) {
    return c - 'a';
  }

  public int getRegister(final int n) {
    return registers[n];
  }

  public Integer getSignal() {
    return signal.poll();
  }
}
