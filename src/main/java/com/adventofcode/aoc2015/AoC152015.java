package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongStream;
import static java.lang.Math.max;

import com.adventofcode.Solution;
import com.google.common.base.Preconditions;
import java.util.stream.Stream;

class AoC152015 implements Solution {


	@Override
	public String solveFirstPart(final Stream<String> input) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart(final Stream<String> input) {
		return solve( input, false );
	}

	private String solve(final Stream<String> input, final boolean first) {
		return itoa( getMaxScore( getIngredients( input ), first ) );
	}

	private int getMaxScore(final int[][] ingredients, final boolean first) {
		Preconditions.checkArgument( ingredients.length <= 4 );
		final int totalQuantity = 100;
		int max = 0;
		for ( int k = 0; k <= (ingredients.length == 4 ? totalQuantity : 0); k++ ) {
			for ( int j = 0; j + k <= (ingredients.length >= 3 ? totalQuantity : 0); j++ ) {
				for ( int i = 0; i + j + k <= (ingredients.length >= 2 ? totalQuantity : 0); i++ ) {
					final int[] quantities = {totalQuantity - i - j - k, i, j, k,};
					final int score = getCookieScore( ingredients, quantities, first );
					if ( score > max ) {
						max = score;
					}
				}
			}
		}
		return max;
	}

	private int getCookieScore(final int[][] ingredients, final int[] quantities,
			final boolean first) {
		if ( !first && getPropertyScore( ingredients, quantities, 4 ) != 500 ) {
			//wrong calories score
			return 0;
		}
		int score = 1;
		for ( int property = 0; property < 4; property++ ) {
			score *= max( 0, getPropertyScore( ingredients, quantities, property ) );
		}
		return score;
	}

	private int getPropertyScore(final int[][] ingredients, final int[] quantities,
			final int propertyIndex) {
		int score = 0;
		for ( int ingredient = 0; ingredient < ingredients.length; ingredient++ ) {
			score += ingredients[ingredient][propertyIndex] * quantities[ingredient];
		}
		return score;
	}

	private int[][] getIngredients(final Stream<String> input) {
		return input.map( line -> toLongStream( line ).mapToInt( Long::intValue ).toArray() )
				.toArray( int[][]::new );
	}
}
