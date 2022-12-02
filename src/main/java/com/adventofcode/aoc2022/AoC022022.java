package com.adventofcode.aoc2022;

import static com.adventofcode.aoc2022.AoC022022.Outcome.DRAW;
import static com.adventofcode.aoc2022.AoC022022.Outcome.LOSS;
import static com.adventofcode.aoc2022.AoC022022.Outcome.VICTORY;
import static com.adventofcode.aoc2022.AoC022022.Shape.PAPER;
import static com.adventofcode.aoc2022.AoC022022.Shape.ROCK;
import static com.adventofcode.aoc2022.AoC022022.Shape.SCISSOR;
import static com.adventofcode.utils.Utils.decrementMod;
import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

class AoC022022 implements Solution {

  private static final Map<Character, Shape> CHARACTER_TO_SHAPE = Map.of( 'A', ROCK, 'B', PAPER,
      'C', SCISSOR, 'X', ROCK, 'Y', PAPER, 'Z', SCISSOR );
  private static final Map<Character, Outcome> CHARACTER_TO_OUTCOME = Map.of( 'X', LOSS, 'Y', DRAW,
      'Z', VICTORY );
  private static final Map<Shape, Integer> SHAPE_TO_SCORE = Map.of( ROCK, 1, PAPER, 2, SCISSOR, 3 );
  private static final Map<Outcome, Integer> OUTCOME_TO_SCORE = Map.of( LOSS, 0, DRAW, 3, VICTORY,
      6 );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, this::chooseShapesFirst );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, this::chooseShapesSecond );
  }

  private String solve(final Stream<String> input,
      final Function<Pair<Character, Character>, Pair<Shape, Shape>> chooseShapes) {
    return itoa( input.map( s -> new Pair<>( s.charAt( 0 ), s.charAt( 2 ) ) ).map( chooseShapes )
        .mapToInt( this::score ).sum() );
  }

  private Pair<Shape, Shape> chooseShapesFirst(final Pair<Character, Character> chars) {
    final var opponentShape = CHARACTER_TO_SHAPE.get( chars.getFirst() );
    final var myShape = CHARACTER_TO_SHAPE.get( chars.getSecond() );
    return new Pair<>( opponentShape, myShape );
  }

  private Pair<Shape, Shape> chooseShapesSecond(final Pair<Character, Character> chars) {
    final var opponentShape = CHARACTER_TO_SHAPE.get( chars.getFirst() );
    final var outcome = CHARACTER_TO_OUTCOME.get( chars.getSecond() );
    final var myShape = opponentShape.getShape( outcome );
    return new Pair<>( opponentShape, myShape );
  }

  private int score(final Pair<Shape, Shape> shapes) {
    final var opponentShape = shapes.getFirst();
    final var myShape = shapes.getSecond();
    final var outcome = myShape.getOutcome( opponentShape );
    return SHAPE_TO_SCORE.get( myShape ) + OUTCOME_TO_SCORE.get( outcome );
  }

  enum Outcome {
    LOSS, DRAW, VICTORY
  }

  enum Shape {
    ROCK( 0 ), PAPER( 1 ), SCISSOR( 2 );

    private static final Map<Integer, Shape> BY_NUMBER = Arrays.stream( values() )
        .collect( toMap( s -> s.number, identity() ) );

    private static final BiMap<Integer, Outcome> DIFFERENCE_TO_OUTCOME = HashBiMap.create(
        Map.of( 0, DRAW, 1, VICTORY, 2, LOSS ) );
    private final int number;

    Shape(final int number) {
      this.number = number;
    }

    Outcome getOutcome(final Shape opponent) {
      final var difference = decrementMod( this.number, opponent.number, 3 );
      return DIFFERENCE_TO_OUTCOME.get( difference );
    }

    Shape getShape(final Outcome outcome) {
      final var difference = DIFFERENCE_TO_OUTCOME.inverse().get( outcome );
      final var shape = incrementMod( this.number, difference, 3 );
      return BY_NUMBER.get( shape );
    }
  }
}
