package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.function.LongBinaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC022021 implements Solution {

	private static final Pattern COMMAND_REGEX = Pattern.compile( "(\\D+) (\\d)" );

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
		for ( final String line : input.toList() ) {
			final var matcher = COMMAND_REGEX.matcher( line );
			if ( !matcher.matches() ) {
				throw new IllegalArgumentException();
			}
			final var command = matcher.group( 1 );
			final long digit = atoi( matcher.group( 2 ) );
			switch ( command ) {
			case "forward" -> {
				position += digit;
				depth += computeDepthForward.applyAsLong( digit, aim );
			}
			case "down" -> {
				aim += digit;
				depth += computeDepthUpDown.applyAsLong( digit, aim );
			}
			case "up" -> {
				aim -= digit;
				depth -= computeDepthUpDown.applyAsLong( digit, aim );
			}
			default -> throw new IllegalStateException( "Unexpected value: " + command );
			}
		}
		return itoa( position * depth );
	}
}
