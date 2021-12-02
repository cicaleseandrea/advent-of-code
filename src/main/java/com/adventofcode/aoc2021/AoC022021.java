package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;

import java.util.function.LongBinaryOperator;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC022021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, ( digit, aim ) -> 0, ( digit, aim ) -> digit );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, ( digit, aim ) -> digit * aim, ( digit, aim ) -> 0 );
	}

	private String solve( final Stream<String> input, final LongBinaryOperator computeDepthForward,
			final LongBinaryOperator computeDepthUpDown ) {
		long position = 0;
		long depth = 0;
		long aim = 0;
		for ( final String command : input.toList() ) {
			final long digit = charToInt( command.charAt( command.length() - 1 ) );
			if ( command.startsWith( "forward" ) ) {
				position += digit;
				depth += computeDepthForward.applyAsLong( digit, aim );
			} else if ( command.startsWith( "down" ) ) {
				aim += digit;
				depth += computeDepthUpDown.applyAsLong( digit, aim );
			} else if ( command.startsWith( "up" ) ) {
				aim -= digit;
				depth -= computeDepthUpDown.applyAsLong( digit, aim );
			}
		}
		return itoa( position * depth );
	}
}
