package com.adventofcode.aoc2016;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.DOT;

import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC022016 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		//@formatter:off
		final char[][] keypad = { { '1', '2', '3' },
								  { '4', '5', '6' },
								  { '7', '8', '9' } };
		//@formatter:on
		return solve( input, keypad, 1, 1 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final char[][] keypad = { { DOT, DOT, '1', DOT, DOT },
								  { DOT, '2', '3', '4', DOT },
								  { '5', '6', '7', '8', '9' },
								  { DOT, 'A', 'B', 'C', DOT },
								  { DOT, DOT, 'D', DOT, DOT }, };
		return solve( input, keypad, 0, 2 );
	}

	private String solve( final Stream<String> input, final char[][] keypad, int x, int y ) {
		final var code = new StringBuilder();
		for ( final String line : input.collect( toList() ) ) {
			for ( final char c : line.toCharArray() ) {
				if ( c == 'D' && y < keypad.length - 1 && keypad[y + 1][x] != DOT ) {
					y += 1;
				} else if ( c == 'U' && y > 0 && keypad[y - 1][x] != DOT ) {
					y -= 1;
				} else if ( c == 'R' && x < keypad.length - 1 && keypad[y][x + 1] != DOT ) {
					x += 1;
				} else if ( c == 'L' && x > 0 && keypad[y][x - 1] != DOT ) {
					x -= 1;
				}
			}
			code.append( keypad[y][x] );
		}
		return code.toString();
	}

}
