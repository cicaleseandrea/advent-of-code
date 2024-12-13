package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Point;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC082024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    char[][] grid = input.map( String::toCharArray ).toArray( char[][]::new );
    var charToPositions = getCharToPositions( grid );
    Set<Point> antinodes = new HashSet<>();
    for ( char antenna : charToPositions.keySet() ) {
      var antennaPositions = charToPositions.get( antenna );
      for ( var pair : Sets.combinations( antennaPositions, 2 ) ) {
        var iterator = pair.iterator();
        antinodes.addAll( getAntinodes( iterator.next(), iterator.next(), grid, first ) );
      }
    }
    return itoa( antinodes.size() );
  }

  private List<Point> getAntinodes(final Point antennaOne, final Point antennaTwo,
      final char[][] grid, final boolean first) {
    int maxSize = Math.max( grid.length, grid[0].length );
    IntStream steps = first ? IntStream.of( 1, -2 ) : IntStream.range( -maxSize, maxSize );
    int diffI = antennaOne.i() - antennaTwo.i();
    int diffJ = antennaOne.j() - antennaTwo.j();
    return steps
        .mapToObj( n -> new Point( antennaOne.i() + n * diffI, antennaOne.j() + n * diffJ ) )
        .filter( antinode -> isInsideGrid( antinode, grid ) )
        .toList();
  }

  private boolean isInsideGrid(final Point position, final char[][] grid) {
    int i = position.i();
    int j = position.j();
    return 0 <= i && i < grid.length && 0 <= j && j < grid[0].length;
  }

  private SetMultimap<Character, Point> getCharToPositions(final char[][] grid) {
    SetMultimap<Character, Point> charToPositions = HashMultimap.create();
    for ( int i = 0; i < grid.length; i++ ) {
      for ( int j = 0; j < grid[0].length; j++ ) {
        char c = grid[i][j];
        if ( c != DOT ) {
          charToPositions.put( c, new Point( i, j ) );
        }
      }
    }
    return charToPositions;
  }
}
