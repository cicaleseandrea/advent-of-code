package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.decrementMod;
import static com.adventofcode.utils.Utils.listGetOrDefault;
import static com.adventofcode.utils.Utils.splitOnTabOrSpace;
import static com.adventofcode.utils.Utils.toPositiveLongList;
import static java.lang.Math.floorMod;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC212016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, false );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, true );
  }

  private String solve(final Stream<String> input, final boolean reverse) {
    final var operations = input.collect( toCollection( ArrayList::new ) );
    if ( reverse ) {
      Collections.reverse( operations );
    }

    final Password password;
    if ( reverse ) {
      password = new Password( "fbgdceah" );
    } else {
      password = new Password( operations.size() < 10 ? 5 : 8 );
    }
    scramble( operations, password, reverse );

    return password.toString();
  }

  private void scramble(final List<String> input, final Password password, final boolean reverse) {
    for ( final var line : input ) {
      final var tokens = splitOnTabOrSpace( line );
      final var numbers = toPositiveLongList( line );
      final int a = listGetOrDefault( numbers, 0, 0L ).intValue();
      final int b = listGetOrDefault( numbers, 1, 0L ).intValue();
      final var operation = tokens.get( 0 );
      switch ( operation ) {
        case "swap" -> {
          if ( !numbers.isEmpty() ) {
            password.swap( a, b );
          } else {
            password.swap( line.charAt( 12 ), line.charAt( 26 ) );
          }
        }
        case "rotate" -> {
          if ( line.charAt( 7 ) == 'b' ) {
            password.rotate( line.charAt( 35 ), reverse );
          } else {
            final int direction = (line.charAt( 7 ) == 'r') ^ reverse ? 1 : -1;
            password.rotate( direction * numbers.get( 0 ).intValue() );
          }
        }
        case "reverse" -> password.reverse( a, b );
        case "move" -> password.move( a, b, reverse );
        default -> throw new IllegalStateException( "Unexpected value: " + operation );
      }
    }
  }

  private static class Password {

    final List<Character> programs;

    Password(final int n) {
      programs = IntStream.range( 0, n ).mapToObj( i -> (char) ('a' + i) )
          .collect( toCollection( ArrayList::new ) );
    }

    Password(final String s) {
      programs = s.chars().mapToObj( c -> (char) c ).collect( toCollection( ArrayList::new ) );
    }

    void rotate(final int n) {
      final int size = programs.size();
      for ( int i = 0; i < floorMod( n, size ); i++ ) {
        programs.add( 0, programs.remove( size - 1 ) );
      }
    }

    void rotate(final char c, final boolean reverse) {
      final var index = programs.indexOf( c );
      rotate( reverse ? getReverseRotations( index ) : getRotations( index ) );
    }

    private int getRotations(final int index) {
      return 1 + index + (index >= 4 ? 1 : 0);
    }

    private int getReverseRotations(int index) {
      final int size = programs.size();
      int rotations = 0;
      do {
        index = decrementMod( index, size );
        rotations++;
      } while ( floorMod( getRotations( index ), size ) != floorMod( rotations, size )
          || rotations == 5 );
      return -rotations;
    }

    void swap(final int a, final int b) {
      final var tmp = programs.get( a );
      programs.set( a, programs.get( b ) );
      programs.set( b, tmp );
    }

    void swap(final char a, final char b) {
      swap( programs.indexOf( a ), programs.indexOf( b ) );
    }

    void reverse(final int a, final int b) {
      for ( int i = a; i <= (b + a) / 2; i++ ) {
        swap( i, b - (i - a) );
      }
    }

    void move(final int a, final int b, final boolean reverse) {
      programs.add( reverse ? a : b, programs.remove( reverse ? b : a ) );
    }

    @Override
    public String toString() {
      return programs.stream().map( Object::toString ).collect( joining() );
    }
  }
}
