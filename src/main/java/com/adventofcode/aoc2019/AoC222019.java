package com.adventofcode.aoc2019;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC222019 implements Solution {
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final List<String> lines = input.collect( toList() );
		final int deckSize = lines.size() < 20 ? 10 : 10007;
		final int posRes = lines.size() < 20 ? 9 : 2019;


		return null;
	}
}
