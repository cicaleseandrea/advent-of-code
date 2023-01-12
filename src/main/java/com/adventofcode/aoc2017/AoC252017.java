package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Triplet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Stream;

class AoC252017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    final var inputList = input.toList();
    var state = inputList.get( 0 ).charAt( 15 );
    final int steps = extractIntegerFromString( inputList.get( 1 ) );
    final Map<Character, BiFunction<Set<Integer>, AtomicInteger, Character>> commands = getCommands(
        inputList.subList( 3, inputList.size() ) );
    final Set<Integer> tape = new HashSet<>();
    final var position = new AtomicInteger();
    for ( int i = 0; i < steps; i++ ) {
      state = commands.get( state ).apply( tape, position );
    }
    return itoa( tape.size() );
  }

  private Map<Character, BiFunction<Set<Integer>, AtomicInteger, Character>> getCommands(
      final List<String> input) {
    final Map<Character, BiFunction<Set<Integer>, AtomicInteger, Character>> commands = new HashMap<>();
    final int commandLength = 10;
    for ( int i = 0; i < input.size(); i += commandLength ) {
      final var state = input.get( i ).charAt( 9 );
      commands.put( state, getStateCommands( input.subList( i + 1, i + commandLength - 1 ) ) );
    }
    return commands;
  }

  private BiFunction<Set<Integer>, AtomicInteger, Character> getStateCommands(
      final List<String> input) {
    final var zero = getCommand( input.subList( 1, 4 ) );
    final var one = getCommand( input.subList( 5, 8 ) );
    return (tape, position) -> {
      if ( !tape.contains( position.intValue() ) ) {
        return compute( tape, position, zero.getFirst(), zero.getSecond(), zero.getThird() );
      } else {
        return compute( tape, position, one.getFirst(), one.getSecond(), one.getThird() );
      }
    };
  }

  private Triplet<Boolean, Boolean, Character> getCommand(final List<String> input) {
    final var writeOne = input.get( 0 ).charAt( 22 ) == '1';
    final var moveRight = input.get( 1 ).charAt( 27 ) == 'r';
    final var nextState = input.get( 2 ).charAt( 26 );
    return new Triplet<>( writeOne, moveRight, nextState );
  }

  private char compute(final Set<Integer> tape, final AtomicInteger position,
      final boolean writeOne, final boolean moveRight, final char nextState) {
    final int intPosition = position.intValue();
    if ( writeOne ) {
      tape.add( intPosition );
    } else {
      tape.remove( intPosition );
    }
    if ( moveRight ) {
      position.incrementAndGet();
    } else {
      position.decrementAndGet();
    }
    return nextState;
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return MERRY_CHRISTMAS;
  }
}
