package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.decrementMod;
import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.toPositiveLongList;
import static java.util.stream.Collectors.joining;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC162017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var moves = getFirstString( input ).split( "," );
    final var dance = new Dance( moves.length < 5 ? 5 : 16 );
    final var totalDances = first ? 1 : 1_000_000_000;

    final var orders = new HashSet<>();
    var cycleFound = false;
    for ( int i = 0; i < totalDances; i++ ) {
      for ( final var move : moves ) {
        final var c = move.charAt( 0 );
        switch ( c ) {
          case 's' -> dance.spin( extractIntegerFromString( move ) );
          case 'p' -> dance.partner( move.charAt( 1 ), move.charAt( 3 ) );
          case 'x' -> {
            final var positions = toPositiveLongList( move );
            dance.exchange( positions.get( 0 ).intValue(), positions.get( 1 ).intValue() );
          }
          default -> throw new IllegalStateException( "Unexpected value: " + c );
        }
      }
      if ( !cycleFound && !orders.add( dance.toString() ) ) {
        cycleFound = true;
        //skip dances
        i = totalDances - (totalDances % i);
      }
    }

    return dance.toString();
  }


  private static class Dance {

    final List<Character> programs;
    int head = 0;

    Dance(final int n) {
      programs = IntStream.range( 0, n ).mapToObj( i -> (char) ('a' + i) )
          .collect( Collectors.toCollection( ArrayList::new ) );
    }

    void spin(final int n) {
      head = decrementMod( head, n, programs.size() );
    }

    void exchange(int a, int b) {
      a = incrementMod( a, head, programs.size() );
      b = incrementMod( b, head, programs.size() );
      final var tmp = programs.get( a );
      programs.set( a, programs.get( b ) );
      programs.set( b, tmp );
    }

    void partner(final char a, final char b) {
      final var indexA = programs.indexOf( a );
      final var indexB = programs.indexOf( b );
      exchange( decrementMod( indexA, head, programs.size() ),
          decrementMod( indexB, head, programs.size() ) );
    }

    @Override
    public String toString() {
      return IntStream.range( head, head + programs.size() ).map( i -> i % programs.size() )
          .mapToObj( i -> programs.get( i ).toString() ).collect( joining() );
    }
  }
}
