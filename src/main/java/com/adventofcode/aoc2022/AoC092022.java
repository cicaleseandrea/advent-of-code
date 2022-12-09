package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Integer.signum;
import static java.lang.Math.abs;
import static java.util.stream.Stream.generate;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

class AoC092022 implements Solution {

  private static final Map<Character, Consumer<Pair<Integer, Integer>>> MOVES = Map.of( 'R',
      knot -> knot.setFirst( knot.getFirst() + 1 ), 'L',
      knot -> knot.setFirst( knot.getFirst() - 1 ), 'U',
      knot -> knot.setSecond( knot.getSecond() + 1 ), 'D',
      knot -> knot.setSecond( knot.getSecond() - 1 ) );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 2 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 10 );
  }

  private String solve(final Stream<String> input, final int nKnots) {
    final var knots = generate( () -> new Pair<>( 0, 0 ) ).limit( nKnots ).toList();
    final var visited = new HashSet<Pair<Integer, Integer>>();

    input.forEach( direction -> {
      for ( int i = 0; i < extractIntegerFromString( direction ); i++ ) {
        MOVES.get( direction.charAt( 0 ) ).accept( knots.get( 0 ) );
        for ( int j = 1; j < knots.size(); j++ ) {
          follow( knots.get( j - 1 ), knots.get( j ) );
        }
        visited.add( new Pair<>( knots.get( knots.size() - 1 ) ) );
      }
    } );

    return itoa( visited.size() );
  }

  private void follow(final Pair<Integer, Integer> goal, final Pair<Integer, Integer> knot) {
    final var diffX = goal.getFirst() - knot.getFirst();
    final var diffY = goal.getSecond() - knot.getSecond();
    if ( abs( diffX ) == 2 || abs( diffY ) == 2 ) {
      knot.setFirst( knot.getFirst() + signum( diffX ) );
      knot.setSecond( knot.getSecond() + signum( diffY ) );
    }
  }

}
