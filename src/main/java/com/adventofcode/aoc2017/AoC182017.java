package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnTabOrSpace;
import static java.lang.Character.isAlphabetic;

import com.adventofcode.Solution;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

class AoC182017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var instructions = input.toList();
    final Deque<Long> queueA = new LinkedList<>();
    final Deque<Long> queueB = new LinkedList<>();
    final var programA = new Program( instructions, queueA, queueB, 0 );
    final var programB = new Program( instructions, queueB, queueA, 1 );
    while ( programA.step() || (!first && programB.step()) ) {
      //keep running
    }

    return itoa( first ? queueB.peekLast() : programB.sent );
  }


  private static class Program {

    private final List<String> instructions;
    private final Queue<Long> in;
    private final Queue<Long> out;
    private final Map<String, Long> registers = new HashMap<>();
    private int op = 0;
    private int sent = 0;

    Program(final List<String> instructions, final Queue<Long> in, final Queue<Long> out,
        final long pValue) {
      this.instructions = instructions;
      this.in = in;
      this.out = out;
      registers.put( "p", pValue );
    }

    boolean step() {
      final var instruction = splitOnTabOrSpace( instructions.get( op ) );
      switch ( instruction.get( 0 ) ) {
        case "snd" -> {
          out.add( getValue( instruction.get( 1 ), registers ) );
          sent++;
        }
        case "set" ->
            registers.put( instruction.get( 1 ), getValue( instruction.get( 2 ), registers ) );
        case "add" ->
            registers.merge( instruction.get( 1 ), getValue( instruction.get( 2 ), registers ),
                Long::sum );
        case "mul" -> registers.merge( instruction.get( 1 ), 0L,
            (old, b) -> old * getValue( instruction.get( 2 ), registers ) );
        case "mod" -> registers.merge( instruction.get( 1 ), 0L,
            (old, b) -> old % getValue( instruction.get( 2 ), registers ) );
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
        default -> throw new IllegalStateException( "Unexpected value: " + instruction );
      }
      op++;
      return true;
    }

    private long getValue(final String x, final Map<String, Long> registers) {
      return isAlphabetic( x.charAt( 0 ) ) ? registers.getOrDefault( x, 0L ) : atol( x );
    }
  }
}
