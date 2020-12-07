package com.adventofcode.aoc2015;

import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC112015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( Utils.getFirstString( input ) );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( solve( Utils.getFirstString( input ) ) );
	}

	private String solve( final String input ) {
		// originally solved with pen and paper
		// simplified algorithm that works on my input
		final var password = input.toCharArray();
		if ( password[3] >= 'x' && password[4] >= 'x' ) {
			password[2]++;
			password[3] = 'a';
		}
		password[4] = password[3];
		password[5] = (char) ( password[4] + 1 );
		password[6] = (char) ( password[5] + 1 );
		password[7] = password[6];
		return new String( password );
	}

}
