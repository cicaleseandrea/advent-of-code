package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongStream;

import java.util.Arrays;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC062021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 80 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 256 );
	}

	private String solve( final Stream<String> input, final int days ) {
		final long[] ages = new long[9];
		toLongStream( input.findFirst().orElseThrow() ).forEach( age -> ages[age.intValue()]++ );

		for ( int i = 0; i < days; i++ ) {
			final long spawn = ages[0];
			for ( int j = 0; j < 8; j++ ) {
				// age decreases
				ages[j] = ages[j + 1];
			}
			ages[6] += spawn;
			ages[8] = spawn;
		}
		return itoa( Arrays.stream( ages ).sum() );
	}
}
