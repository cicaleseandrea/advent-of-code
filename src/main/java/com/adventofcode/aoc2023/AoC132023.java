package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.min;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

class AoC132023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, AoC132023::findReflection );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, AoC132023::findReflectionSmudge );
  }

  private static String solve(final Stream<String> input,
      final Function<char[][], Reflection> findReflection) {
    final int summary = getPatterns( input ).stream().map( findReflection )
        .mapToInt( Reflection::getSummary ).sum();
    return itoa( summary );
  }

  private static boolean isReflection(final char[][] grid, final int i) {
    if ( i <= 0 || i >= grid.length ) {
      return false;
    }
    for ( int j = 0; j < min( i, grid.length - i ); j++ ) {
      if ( !Arrays.equals( grid[i - 1 - j], grid[i + j] ) ) {
        return false;
      }
    }
    return true;
  }

  private static List<Reflection> findReflections(final char[][] grid) {
    final List<Reflection> reflections = new ArrayList<>();
    reflections.addAll( findReflections( grid, false ) );
    reflections.addAll( findReflections( grid, true ) );
    return reflections;
  }

  private static List<Reflection> findReflections(char[][] grid, boolean vertical) {
    if ( vertical ) {
      grid = transpose( grid );
    }
    final List<Reflection> reflections = new ArrayList<>();
    for ( int i = 0; i < grid.length; i++ ) {
      if ( isReflection( grid, i ) ) {
        reflections.add( new Reflection( vertical ? i : 0, vertical ? 0 : i ) );
      }
    }
    return reflections;
  }

  private static Reflection findReflection(final char[][] grid) {
    return findReflections( grid ).get( 0 );
  }

  private static Reflection findReflectionSmudge(final char[][] grid) {
    final Reflection oldReflection = findReflection( grid );
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        grid[i][j] = (grid[i][j] == DOT) ? HASH : DOT;
        final List<Reflection> reflections = findReflections( grid );
        grid[i][j] = (grid[i][j] == DOT) ? HASH : DOT;
        final Optional<Reflection> newReflection = reflections.stream()
            .filter( reflection -> !reflection.equals( oldReflection ) ).findAny();
        if ( newReflection.isPresent() ) {
          return newReflection.get();
        }
      }
    }
    throw new IllegalArgumentException( "No reflection found with smudge" );
  }

  private static List<char[][]> getPatterns(final Stream<String> input) {
    final List<char[][]> patterns = new ArrayList<>();
    final Iterator<String> inputIterator = input.iterator();
    while ( inputIterator.hasNext() ) {
      final List<char[]> list = new ArrayList<>();
      String line;
      while ( inputIterator.hasNext() && !(line = inputIterator.next()).isEmpty() ) {
        list.add( line.toCharArray() );
      }
      patterns.add( list.toArray( char[][]::new ) );
    }
    return patterns;
  }

  private static char[][] transpose(final char[][] grid) {
    final int rows = grid.length;
    final int cols = grid[0].length;
    final char[][] transposed = new char[cols][rows];
    for ( int i = 0; i < rows; i++ ) {
      for ( int j = 0; j < cols; j++ ) {
        transposed[j][i] = grid[i][j];
      }
    }
    return transposed;
  }

  private record Reflection(int verticalIndex, int horizontalIndex) {

    int getSummary() {
      return verticalIndex + (100 * horizontalIndex);
    }
  }
}
