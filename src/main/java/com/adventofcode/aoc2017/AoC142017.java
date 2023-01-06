package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

import com.adventofcode.Solution;
import com.adventofcode.utils.DisjointSet;
import com.adventofcode.utils.Pair;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC142017 implements Solution {

  private static final Solution AOC_10_2017 = new AoC102017();
  private static final UnaryOperator<String> KNOT_HASH = s -> AOC_10_2017.solveSecondPart(
      Stream.of( s ) );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var grid = getGrid( getFirstString( input ) );
    final var usedPositions = getUsedPositions( grid );

    final var regions = new DisjointSet<>();
    for ( int i = 0; i < grid.size(); i++ ) {
      for ( int j = 0; j < grid.get( 0 ).length(); j++ ) {
        final var pos = new Pair<>( i, j );
        if ( usedPositions.contains( pos ) ) {
          regions.makeSet( pos );
          NEIGHBOURS_4.stream().map(
                  n -> new Pair<>( pos.getFirst() + n.getFirst(), pos.getSecond() + n.getSecond() ) )
              .filter( usedPositions::contains ).forEach( n -> {
                regions.makeSet( n );
                regions.union( pos, n );
              } );
        }
      }
    }

    return itoa( first ? usedPositions.size() : regions.getSize() );
  }

  private Set<Pair<Integer, Integer>> getUsedPositions(final List<String> grid) {
    return IntStream.range( 0, grid.size() ).boxed().flatMap(
        i -> range( 0, grid.get( 0 ).length() ).filter( j -> grid.get( i ).charAt( j ) == '1' )
            .mapToObj( j -> new Pair<>( i, j ) ) ).collect( toSet() );
  }

  private List<String> getGrid(final String str) {
    return IntStream.range( 0, 128 ).mapToObj( i -> str + '-' + i ).map( KNOT_HASH )
        .map( this::hexToBinary ).toList();
  }

  private String hexToBinary(String hex) {
    hex = hex.replace( "0", "0000" );
    hex = hex.replace( "1", "0001" );
    hex = hex.replace( "2", "0010" );
    hex = hex.replace( "3", "0011" );
    hex = hex.replace( "4", "0100" );
    hex = hex.replace( "5", "0101" );
    hex = hex.replace( "6", "0110" );
    hex = hex.replace( "7", "0111" );
    hex = hex.replace( "8", "1000" );
    hex = hex.replace( "9", "1001" );
    hex = hex.replace( "a", "1010" );
    hex = hex.replace( "b", "1011" );
    hex = hex.replace( "c", "1100" );
    hex = hex.replace( "d", "1101" );
    hex = hex.replace( "e", "1110" );
    hex = hex.replace( "f", "1111" );
    return hex;
  }
}
