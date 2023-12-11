package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC112023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 2 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 1_000_000 );
  }

  private static String solve(final Stream<String> input, final int expansionFactor) {
    final List<List<Character>> grid = Utils.getCharMatrix( input );
    final EmptySpace emptySpace = getEmptySpace( grid );
    final Set<Galaxy> galaxies = getGalaxies( grid, emptySpace.rows, emptySpace.columns,
        expansionFactor );

    final long sum = Sets.combinations( galaxies, 2 ).stream()
        .mapToLong( AoC112023::computeDistance ).sum();
    return itoa( sum );
  }

  private static long computeDistance(final Set<Galaxy> pair) {
    final var itr = pair.iterator();
    final Galaxy a = itr.next();
    final Galaxy b = itr.next();
    return Utils.manhattanDistance( a.i, a.j, b.i, b.j );
  }

  private static Set<Galaxy> getGalaxies(final List<List<Character>> grid,
      final Set<Integer> emptyRows, final Set<Integer> emptyColumns, final int expansion) {
    final Set<Galaxy> galaxies = new HashSet<>();
    long rowOffset = 0;
    for ( int i = 0; i < grid.size(); i++ ) {
      final List<Character> row = grid.get( i );
      rowOffset += emptyRows.contains( i ) ? expansion - 1 : 0;
      long columnOffset = 0;
      for ( int j = 0; j < row.size(); j++ ) {
        columnOffset += emptyColumns.contains( j ) ? expansion - 1 : 0;
        final char c = row.get( j );
        if ( c == HASH ) {
          galaxies.add( new Galaxy( i + rowOffset, j + columnOffset ) );
        }
      }
    }
    return galaxies;
  }

  private static EmptySpace getEmptySpace(final List<List<Character>> grid) {
    final Set<Integer> emptyRows = IntStream.range( 0, grid.size() ).boxed()
        .collect( Collectors.toSet() );
    final Set<Integer> emptyColumns = IntStream.range( 0, grid.get( 0 ).size() ).boxed()
        .collect( Collectors.toSet() );
    for ( int i = 0; i < grid.size(); i++ ) {
      final List<Character> row = grid.get( i );
      for ( int j = 0; j < row.size(); j++ ) {
        final char c = row.get( j );
        if ( c == HASH ) {
          emptyRows.remove( i );
          emptyColumns.remove( j );
        }
      }
    }
    return new EmptySpace( emptyRows, emptyColumns );
  }

  private record Galaxy(long i, long j) {

  }

  private record EmptySpace(Set<Integer> rows, Set<Integer> columns) {

  }
}
