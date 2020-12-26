package com.adventofcode.aoc2015;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.aoc2015.AoC062015.Instruction.OFF;
import static com.adventofcode.aoc2015.AoC062015.Instruction.ON;
import static com.adventofcode.aoc2015.AoC062015.Instruction.TOGGLE;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Arrays;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.regex.MatchResult;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC062015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input,
				Map.of( ON, light -> 1, OFF, light -> 0, TOGGLE, light -> light ^ 1 ) );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input,
				Map.of( ON, light -> light + 1, OFF, light -> Math.max( light - 1, 0 ), TOGGLE,
						light -> light + 2 ) );
	}

	private String solve( final Stream<String> input,
			final Map<Instruction, IntUnaryOperator> instructions ) {
		final int[][] lights = new int[1000][1000];
		input.forEach( line -> {
			final var numbers = Utils.LONG_PATTERN.matcher( line )
					.results()
					.map( MatchResult::group )
					.map( Utils::atoi )
					.collect( toList() );
			final var instruction = getInstruction( line, instructions );
			for ( int y = numbers.get( 1 ); y <= numbers.get( 3 ); y++ ) {
				for ( int x = numbers.get( 0 ); x <= numbers.get( 2 ); x++ ) {
					lights[x][y] = instruction.applyAsInt( lights[x][y] );
				}
			}
		} );
		return itoa( Arrays.stream( lights ).flatMapToInt( Arrays::stream ).sum() );
	}

	private IntUnaryOperator getInstruction( final String line,
			final Map<Instruction, IntUnaryOperator> instructions ) {
		if ( line.startsWith( "toggle" ) ) {
			return instructions.get( TOGGLE );
		} else if ( line.contains( "off" ) ) {
			return instructions.get( OFF );
		} else {
			return instructions.get( ON );
		}
	}

	enum Instruction {
		TOGGLE,
		ON,
		OFF
	}

}
