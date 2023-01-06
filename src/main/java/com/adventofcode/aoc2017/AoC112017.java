package com.adventofcode.aoc2017;

import static com.adventofcode.aoc2017.AoC112017.Direction.N;
import static com.adventofcode.aoc2017.AoC112017.Direction.NE;
import static com.adventofcode.aoc2017.AoC112017.Direction.NW;
import static com.adventofcode.aoc2017.AoC112017.Direction.S;
import static com.adventofcode.aoc2017.AoC112017.Direction.SE;
import static com.adventofcode.aoc2017.AoC112017.Direction.SW;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnRegex;
import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.Map;
import java.util.stream.Stream;

class AoC112017 implements Solution {

  private static final Map<Direction, Pair<Integer, Integer>> NEIGHBOURS = Map.of( N,
      new Pair<>( 0, -2 ), NE, new Pair<>( +1, -1 ), SE, new Pair<>( +1, +1 ), S,
      new Pair<>( 0, +2 ), SW, new Pair<>( -1, +1 ), NW, new Pair<>( -1, -1 ) );

  private static final Pair<Integer, Integer> ZERO = new Pair<>( 0, 0 );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    //using "doubled coordinates"
    var position = ZERO;
    var maxDistance = 0;
    final var directions = splitOnRegex( getFirstString( input ), "," ).map( String::toUpperCase )
        .map( Direction::valueOf ).map( NEIGHBOURS::get ).toList();
    for ( final var direction : directions ) {
      position = addTiles( position, direction );
      maxDistance = max( maxDistance, getDistance( ZERO, position ) );
    }

    return itoa( first ? getDistance( ZERO, position ) : maxDistance );
  }

  private Pair<Integer, Integer> addTiles(final Pair<Integer, Integer> tileA,
      final Pair<Integer, Integer> tileB) {
    return new Pair<>( tileA.getFirst() + tileB.getFirst(), tileA.getSecond() + tileB.getSecond() );
  }

  private int getDistance(final Pair<Integer, Integer> tileA, final Pair<Integer, Integer> tileB) {
    final int dCol = abs( tileA.getFirst() - tileB.getFirst() );
    final int dRow = abs( tileA.getSecond() - tileB.getSecond() );
    return dCol + max( 0, (dRow - dCol) / 2 );
  }

  enum Direction {
    N, NE, SE, S, SW, NW,
  }
}
