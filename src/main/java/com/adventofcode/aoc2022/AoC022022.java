package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.decrementMod;
import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

class AoC022022 implements Solution {

  private static final Map<Character, Integer> SHAPES = Map.of( 'A', 0, 'B', 1, 'C', 2, 'X', 0, 'Y',
      1, 'Z', 2 );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, UnaryOperator.identity() );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, this::chooseShape );
  }

  private String solve(final Stream<String> input,
      final UnaryOperator<Pair<Integer, Integer>> chooseShapes) {
    return itoa( input.map( this::toPair ).map( chooseShapes ).mapToInt( this::score ).sum() );
  }

  private Pair<Integer, Integer> toPair(final String round) {
    final var first = SHAPES.get( round.charAt( 0 ) );
    final var second = SHAPES.get( round.charAt( 2 ) );
    return new Pair<>( first, second );
  }

  private Pair<Integer, Integer> chooseShape(final Pair<Integer, Integer> shapes) {
    final var opponentShape = shapes.getFirst();
    final var outcome = shapes.getSecond();

    final var myShape = switch ( outcome ) {
      case 1 -> opponentShape; //draw
      case 2 -> incrementMod( opponentShape, 1, 3 ); //victory
      case 0 -> incrementMod( opponentShape, 2, 3 ); //loss
      default -> throw new IllegalStateException( "Unexpected value: " + outcome );
    };
    return new Pair<>( opponentShape, myShape );
  }

  private int score(final Pair<Integer, Integer> shapes) {
    final var opponentShape = shapes.getFirst();
    final var myShape = shapes.getSecond();
    final var difference = decrementMod( myShape, opponentShape, 3 );
    final var outcome = switch ( difference ) {
      case 0 -> 3; //draw
      case 1 -> 6; //victory
      case 2 -> 0; //loss
      default -> throw new IllegalStateException( "Unexpected value: " + difference );
    };
    return (myShape + 1) + outcome;
  }
}
