package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;

import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC022021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		long position = 0;
		long depth = 0;
		for ( final String command : input.toList() ) {
			final int digit = charToInt( command.charAt( command.length() - 1 ) );
			if ( command.startsWith( "forward" ) ) {
				position += digit;
			} else if ( command.startsWith( "down" ) ) {
				depth += digit;
			} else if ( command.startsWith( "up" ) ) {
				depth -= digit;
			}
		}
		return itoa( position * depth );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		long position = 0;
		long depth = 0;
		long aim = 0;
		for ( final String command : input.toList() ) {
			final long digit = charToInt( command.charAt( command.length() - 1 ) );
			if ( command.startsWith( "forward" ) ) {
				position += digit;
				depth += aim * digit;
			} else if ( command.startsWith( "down" ) ) {
				aim += digit;
			} else if ( command.startsWith( "up" ) ) {
				aim -= digit;
			}
		}
		return itoa( position * depth );
	}
}
