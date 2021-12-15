package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.itoa;

import java.util.Arrays;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC202015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 10, Integer.MAX_VALUE );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 11, 50 );
	}

	private String solve( final Stream<String> input, final int numberOfPresents,
			final int maxHouses ) {
		final int presentsTarget = input.findFirst().map( Utils::atoi ).orElseThrow();
		final int[] presents = new int[presentsTarget / 10];
		Arrays.fill( presents, 10 );

		for ( int elf = 2; true; elf++ ) {
			for ( int house = elf, i = 0; house < presents.length && i < maxHouses; house += elf, i++ ) {
				presents[house] += elf * numberOfPresents;
			}
			if ( presents[elf] >= presentsTarget ) {
				return itoa( elf );
			}
		}
	}

}
