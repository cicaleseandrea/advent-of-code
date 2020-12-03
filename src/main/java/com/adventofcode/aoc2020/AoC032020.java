package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.getCharMatrix;
import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.printMatrix;
import static com.adventofcode.utils.Utils.shouldPrint;

import java.util.List;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC032020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var matrix = getCharMatrix( input );
		if ( shouldPrint() ) {
			printMatrix( matrix );
		}
		return itoa( countTrees( matrix, 3, 1 ) );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var matrix = getCharMatrix( input );
		return itoa(
				countTrees( matrix, 1, 1 ) * countTrees( matrix, 3, 1 ) * countTrees( matrix, 5,
						1 ) * countTrees( matrix, 7, 1 ) * countTrees( matrix, 1, 2 ) );
	}

	private static long countTrees( final List<List<Character>> matrix, final int x, final int y ) {
		final int rows = matrix.size();
		final int columns = matrix.get( 0 ).size();
		int i = 0;
		int j = 0;
		long result = 0;
		while ( i < rows ) {
			final char c = matrix.get( i ).get( j );
			if ( c == HASH ) {
				result++;
			}
			j = incrementMod( j, x, columns );
			i += y;
		}
		return result;
	}

}
