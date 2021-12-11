package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.itoa;

import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC112021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 1 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 3 );
	}

	private String solve( final Stream<String> input, final int windowSize ) {
		int result = 0;
		return itoa( result );
	}
}
