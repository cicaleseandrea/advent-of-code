package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Character.isDigit;

import com.adventofcode.Solution;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

class AoC212022 implements Solution {

  private static final String ROOT = "root";
  private static final String HUMAN = "humn";

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var monkeys = getMonkeys( input );
    return computeValue( monkeys.get( ROOT ), monkeys, first );
  }

  private String computeValue(final Monkey monkey, Map<String, Monkey> monkeys,
      final boolean first) {
    final var monkeyName = monkey.name;
    if ( !first && monkeyName.equals( HUMAN ) ) {
      return "x";
    } else if ( monkey.value != null ) {
      return monkey.value;
    }

    final var left = computeValue( monkeys.get( monkey.first ), monkeys, first );
    final var right = computeValue( monkeys.get( monkey.second ), monkeys, first );

    if ( isNumber( left ) && isNumber( right ) ) {
      //part 1 will always enter this branch
      return itoa( getOperation( monkey.op ).apply( atol( left ), atol( right ) ) );
    } else if ( monkeyName.equals( ROOT ) ) {
      return computeHumn( left, right );
    } else {
      final var number = isNumber( left ) ? left : right;
      final var string = isNumber( left ) ? right : left;
      return getInvertedOperations( monkey.op, number, string, !isNumber( left ) );
    }
  }

  private String getInvertedOperations(final String op, final String n, final String s,
      final boolean invertMinus) {
    return switch ( op ) {
      case "+" -> "- " + n + " " + s;
      case "-" -> invertMinus ? "+ " + n + " " + s : "- " + n + " * -1 " + s;
      case "*" -> "/ " + n + " " + s;
      case "/" -> "* " + n + " " + s;
      default -> throw new IllegalStateException( "Unexpected value: " + op );
    };
  }

  private String computeHumn(final String left, final String right) {
    var result = atol( isNumber( left ) ? left : right );
    final var tokens = (isNumber( left ) ? right : left).split( " " );
    for ( int i = 0; i < tokens.length - 1; i += 2 ) {
      final var op = tokens[i];
      final var number = atol( tokens[i + 1] );
      result = getOperation( op ).apply( result, number );
    }
    return itoa( result );
  }

  private Map<String, Monkey> getMonkeys(final Stream<String> input) {
    final Map<String, Monkey> monkeys = new HashMap<>();
    input.map( line -> line.split( " " ) ).forEach( tokens -> {
      final var name = tokens[0].replace( ":", "" );
      final var isNumber = isNumber( tokens[1] );
      if ( isNumber ) {
        monkeys.put( name, new Monkey( name, tokens[1], null, null, null ) );
      } else {
        monkeys.put( name, new Monkey( name, null, tokens[1], tokens[2], tokens[3] ) );
      }
    } );
    return monkeys;
  }

  private BinaryOperator<Long> getOperation(final String op) {
    return switch ( op ) {
      case "+" -> (a, b) -> a + b;
      case "-" -> (a, b) -> a - b;
      case "*" -> (a, b) -> a * b;
      case "/" -> (a, b) -> a / b;
      default -> throw new IllegalStateException( "Unexpected value: " + op );
    };
  }

  private boolean isNumber(final String s) {
    return isDigit( s.charAt( 0 ) );
  }

  private record Monkey(String name, String value, String first, String op, String second) {

  }
}
