package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
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
    List<List<Character>> grid = Utils.getCharMatrix( input );
    var charToPositions = getCharToPositions( grid );
    HashSet<Pair<Integer, Integer>> antinodes = new HashSet<>();
    for ( char antenna : charToPositions.keySet() ) {
      var antennaPositions = charToPositions.get( antenna );
      for ( var pair : Sets.combinations( antennaPositions, 2 ) ) {
        var iterator = pair.iterator();
        antinodes.addAll( getAntinodes( iterator.next(), iterator.next(), grid, first ) );
      }
    }
    return itoa( antinodes.size() );
  }

  private List<Pair<Integer, Integer>> getAntinodes(final Pair<Integer, Integer> antennaOne,
      final Pair<Integer, Integer> antennaTwo, final List<List<Character>> grid,
      final boolean first) {
    int rows = grid.size();
    int columns = grid.get( 0 ).size();
    IntStream steps = first ? IntStream.of( 1, -2 )
        : IntStream.range( -Math.max( rows, columns ), Math.max( rows, columns ) );

    int diffI = antennaOne.getFirst() - antennaTwo.getFirst();
    int diffJ = antennaOne.getSecond() - antennaTwo.getSecond();
    return steps.mapToObj(
            n -> new Pair<>( antennaOne.getFirst() + n * diffI, antennaOne.getSecond() + n * diffJ ) )
        .filter( antinode -> isInsideGrid( antinode, grid ) ).toList();
  }

  private boolean isInsideGrid(final Pair<Integer, Integer> position,
      final List<List<Character>> grid) {
    int rows = grid.size();
    int columns = grid.get( 0 ).size();
    int i = position.getFirst();
    int j = position.getSecond();
    return 0 <= i && i < rows && 0 <= j && j < columns;
  }

  private HashMultimap<Character, Pair<Integer, Integer>> getCharToPositions(
      final List<List<Character>> grid) {
    HashMultimap<Character, Pair<Integer, Integer>> charToPosition = HashMultimap.create();
    for ( int i = 0; i < grid.size(); i++ ) {
      for ( int j = 0; j < grid.get( 0 ).size(); j++ ) {
        char c = grid.get( i ).get( j );
        if ( c != DOT ) {
          charToPosition.put( c, new Pair<>( i, j ) );
        }
      }
    }
    return charToPosition;
  }
}