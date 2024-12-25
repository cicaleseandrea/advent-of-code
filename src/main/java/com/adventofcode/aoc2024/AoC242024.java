package com.adventofcode.aoc2024;


import static com.adventofcode.utils.Utils.charToInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import com.adventofcode.Solution;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

class AoC242024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    List<String> lines = input.toList();
    Map<String, Integer> wireToValue = lines.stream()
        .takeWhile( Predicate.not( String::isEmpty ) )
        .collect( toMap( line -> line.substring( 0, 3 ), line -> charToInt( line.charAt( 5 ) ) ) );
    Map<String, Gate> outputToGate = lines.stream()
        .dropWhile( Predicate.not( String::isEmpty ) )
        .filter( Predicate.not( String::isEmpty ) )
        .map( line -> {
          int offset = line.contains( " OR" ) ? 0 : 1;
          String inputA = line.substring( 0, 3 );
          String op = line.substring( 4, 6 + offset );
          String inputB = line.substring( 7 + offset, 10 + offset );
          String output = line.substring( line.length() - 3 );
          return new Gate( inputA, op, inputB, output );
        } ).collect( toMap( gate -> gate.output, identity() ) );
    if ( first ) {
      return Long.valueOf( getBinary( outputToGate, wireToValue ), 2 ).toString();
    } else {
      return getWrongWires( outputToGate ).stream().sorted().collect( joining( "," ) );
    }
  }

  private Set<String> getWrongWires(final Map<String, Gate> outputToGate) {
    Set<String> wrongWires = new HashSet<>();
    for ( Gate gate : outputToGate.values() ) {
      Optional<Gate> gateInputA = Optional.ofNullable( outputToGate.get( gate.inputA ) );
      Optional<Gate> gateInputB = Optional.ofNullable( outputToGate.get( gate.inputB ) );
      if ( isOutputNumber( gate.output ) ) {
        //output wires should be connected to an XOR gate (except the last bit which is a carry bit)
        if ( !gate.output.equals( "z45" ) && !gate.op.equals( "XOR" )
            || gate.output.equals( "z45" ) && !gate.op.equals( "OR" ) ) {
          wrongWires.add( gate.output );
        }
      } else if ( gate.op.equals( "OR" ) ) {
        //inputs to OR gates should be AND gates
        if ( !hasOp( gateInputA, "AND" ) ) {
          wrongWires.add( gate.inputA );
        }
        if ( !hasOp( gateInputB, "AND" ) ) {
          wrongWires.add( gate.inputB );
        }
      } else if ( gate.op.equals( "AND" ) ) {
        if ( hasOp( gateInputA, "XOR" ) && hasOp( gateInputB, "XOR" ) ) {
          //maximum one input to an AND gate should be an XOR gate, and its inputs should be input numbers
          if ( !isInputNumber( gateInputA.map( Gate::inputA ) )
              || !isInputNumber( gateInputA.map( Gate::inputB ) ) ) {
            wrongWires.add( gate.inputA );
          }
          if ( !isInputNumber( gateInputB.map( Gate::inputA ) )
              || !isInputNumber( gateInputB.map( Gate::inputB ) ) ) {
            wrongWires.add( gate.inputB );
          }
        } else {
          //an input to an AND gate can be another AND gate only if it's the carry bit from the half-adder
          if ( hasOp( gateInputA, "AND" )
              && !isFirstInputNumber( gateInputA.map( Gate::inputA ) )
              && !isFirstInputNumber( gateInputA.map( Gate::inputB ) ) ) {
            wrongWires.add( gate.inputA );
          }
          if ( hasOp( gateInputB, "AND" )
              && !isFirstInputNumber( gateInputB.map( Gate::inputA ) )
              && !isFirstInputNumber( gateInputB.map( Gate::inputB ) ) ) {
            wrongWires.add( gate.inputB );
          }
        }
      }
    }
    return wrongWires;
  }

  private String getBinary(final Map<String, Gate> outputToGate,
      final Map<String, Integer> wireToValue) {
    return outputToGate.keySet()
        .stream()
        .filter( this::isOutputNumber )
        .sorted( Comparator.reverseOrder() )
        .map( output -> computeValues( output, outputToGate, wireToValue ) )
        .map( String::valueOf )
        .collect( joining() );
  }

  private int computeValues(final String output, final Map<String, Gate> outputToGate,
      final Map<String, Integer> wireToValue) {
    if ( wireToValue.containsKey( output ) ) {
      return wireToValue.get( output );
    }
    Gate gate = outputToGate.get( output );
    int inputA = computeValues( gate.inputA, outputToGate, wireToValue );
    int inputB = computeValues( gate.inputB, outputToGate, wireToValue );
    int value = switch ( gate.op ) {
      case "AND" -> inputA & inputB;
      case "OR" -> inputA | inputB;
      case "XOR" -> inputA ^ inputB;
      default -> throw new IllegalStateException( "Unexpected value: " + gate.op );
    };
    wireToValue.put( output, value );
    return value;
  }

  private boolean isOutputNumber(final String wire) {
    return wire.startsWith( "z" );
  }

  private boolean isInputNumber(final Optional<String> wire) {
    return wire.map( s -> s.startsWith( "x" ) || s.startsWith( "y" ) ).orElse( false );
  }

  private boolean isFirstInputNumber(final Optional<String> wire) {
    return wire.map( s -> s.startsWith( "x00" ) || s.startsWith( "y00" ) ).orElse( false );
  }

  private boolean hasOp(final Optional<Gate> gate, final String op) {
    return gate.map( Gate::op ).map( gateOp -> gateOp.equals( op ) ).orElse( false );
  }

  private record Gate(String inputA, String op, String inputB, String output) {

  }
}
