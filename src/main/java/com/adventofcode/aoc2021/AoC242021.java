package com.adventofcode.aoc2021;

import static java.util.stream.Collectors.toCollection;

import static com.adventofcode.utils.Utils.shouldPrint;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC242021 implements Solution {
	private static final Pattern INSTRUCTION_REGEX = Pattern.compile( "(\\w+) (\\w) ?(-?\\w*)" );

	// TODO this only works for my input...
	// Program decoded manually

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var modelNumber = "94992994195998";
		checkModelNumber( input, getModelNumberList( modelNumber ) );
		return modelNumber;
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var modelNumber = "21191861151161";
		checkModelNumber( input, getModelNumberList( modelNumber ) );
		return modelNumber;
	}

	private void checkModelNumber( final Stream<String> input, final List<Integer> modelNumber ) {
		final var program = input.toList();
		final var variables = new HashMap<>( Map.of( "w", 0, "x", 0, "y", 0, "z", 0 ) );

		for ( int step = 0; step < program.size(); step++ ) {
			final var instruction = program.get( step );
			runInstruction( instruction, modelNumber, variables, step );
		}

		if ( ( variables.get( "z" ) != 0 ) ) {
			throw new IllegalStateException( "Model number is invalid" );
		}
	}

	private List<Integer> getModelNumberList( final String modelNumber ) {
		return modelNumber.chars()
				.mapToObj( Utils::charToInt )
				.collect( toCollection( LinkedList::new ) );
	}

	private void runInstruction( final String input, final List<Integer> modelNumber,
			final Map<String, Integer> variables, final int step ) {
		final var matcher = INSTRUCTION_REGEX.matcher( input );
		if ( !matcher.matches() ) {
			throw new IllegalArgumentException();
		}

		final var instruction = Instruction.getInstruction( matcher.group( 1 ) );

		final var a = matcher.group( 2 );
		final var b = matcher.group( 3 );
		final var aValue = instruction == Instruction.INP ? modelNumber.remove( 0 ) : variables.get(
				a );
		final var bValue = b.isEmpty() || variables.containsKey( b ) ? variables.get(
				b ) : Integer.valueOf( b );

		final var res = instruction.compute( aValue, bValue );
		variables.put( a, res );

		if ( shouldPrint() ) {
			System.out.println( instruction.toString( a, b, step ) );
			System.out.println( variables );
		}
	}

	private enum Instruction {
		INP( ( a, b ) -> a, ( a, b ) -> a + "=MODEL NUMBER" ),
		ADD( ( a, b ) -> a + b, ( a, b ) -> a + "=" + a + "+" + b ),
		MUL( ( a, b ) -> a * b, ( a, b ) -> a + "=" + a + "*" + b ),
		DIV( ( a, b ) -> {
			if ( b == 0 ) {
				throw new IllegalStateException( "Wrong arguments for DIV" );
			}
			return a / b;
		}, ( a, b ) -> a + "=" + a + "/" + b ),
		MOD( ( a, b ) -> {
			if ( a < 0 || b <= 0 ) {
				throw new IllegalStateException( "Wrong arguments for MOD" );
			}
			return a % b;
		}, ( a, b ) -> a + "=" + a + "%" + b ),
		EQL( ( a, b ) -> Objects.equals( a, b ) ? 1 : 0,
				( a, b ) -> a + "=(" + a + "==" + b + ") ? 1 : 0" );

		private final BinaryOperator<Integer> computation;
		private final BinaryOperator<String> stringRepresentation;

		Instruction( final BinaryOperator<Integer> compute,
				final BinaryOperator<String> stringRepresentation ) {
			this.stringRepresentation = stringRepresentation;
			this.computation = compute;
		}

		static Instruction getInstruction( final String instruction ) {
			return switch ( instruction ) {
				case "inp" -> INP;
				case "add" -> ADD;
				case "mul" -> MUL;
				case "div" -> DIV;
				case "mod" -> MOD;
				case "eql" -> EQL;
				default -> throw new IllegalStateException( "Unexpected value: " + instruction );
			};
		}

		int compute( final Integer a, final Integer b ) {
			return computation.apply( a, b );
		}

		String toString( final String a, final String b, final int step ) {
			return step + ": " + stringRepresentation.apply( a, b );
		}
	}

}
