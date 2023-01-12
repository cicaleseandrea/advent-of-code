package com.adventofcode.utils;

import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.splitOnTabOrSpace;
import static java.lang.Character.isAlphabetic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Computer2017 {

  private final List<String> instructions;
  private final Queue<Long> in;
  private final Queue<Long> out;
  private final Map<String, Long> registers = new HashMap<>();
  private int op = 0;
  private int snd = 0;
  private int mul = 0;

  public Computer2017(final List<String> instructions) {
    this( instructions, null, null, 0 );
  }

  public Computer2017(final List<String> instructions, final Queue<Long> in, final Queue<Long> out,
      final long pValue) {
    this.instructions = instructions;
    this.in = in;
    this.out = out;
    registers.put( "p", pValue );
  }

  public boolean step() {
    if ( op >= instructions.size() ) {
      return false;
    }
    final var instruction = splitOnTabOrSpace( instructions.get( op ) );
    switch ( instruction.get( 0 ) ) {
      case "set" ->
          registers.put( instruction.get( 1 ), getValue( instruction.get( 2 ), registers ) );
      case "add" ->
          registers.merge( instruction.get( 1 ), getValue( instruction.get( 2 ), registers ),
              Long::sum );
      case "sub" ->
          registers.merge( instruction.get( 1 ), -getValue( instruction.get( 2 ), registers ),
              Long::sum );
      case "mul" -> {
        registers.merge( instruction.get( 1 ), 0L,
            (old, b) -> old * getValue( instruction.get( 2 ), registers ) );
        mul++;
      }
      case "mod" -> registers.merge( instruction.get( 1 ), 0L,
          (old, b) -> old % getValue( instruction.get( 2 ), registers ) );
      case "snd" -> {
        out.add( getValue( instruction.get( 1 ), registers ) );
        snd++;
      }
      case "rcv" -> {
        if ( in.isEmpty() ) {
          return false;
        } else {
          registers.put( instruction.get( 1 ), in.poll() );
        }
      }
      case "jgz" -> {
        if ( getValue( instruction.get( 1 ), registers ) > 0 ) {
          op += getValue( instruction.get( 2 ), registers ) - 1;
        }
      }
      case "jnz" -> {
        if ( getValue( instruction.get( 1 ), registers ) != 0 ) {
          op += getValue( instruction.get( 2 ), registers ) - 1;
        }
      }
      default -> throw new IllegalStateException( "Unexpected value: " + instruction );
    }
    op++;
    return true;
  }

  private long getValue(final String x, final Map<String, Long> registers) {
    return isAlphabetic( x.charAt( 0 ) ) ? registers.getOrDefault( x, 0L ) : atol( x );
  }

  public int getSnd() {
    return snd;
  }

  public int getMul() {
    return mul;
  }
}
