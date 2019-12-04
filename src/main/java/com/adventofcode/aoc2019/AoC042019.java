package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toDigitsArray;
import static com.adventofcode.utils.Utils.toPositiveLongList;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC042019 implements Solution {
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, groupSize -> ( groupSize >= 2 ) );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, groupSize -> ( groupSize == 2 ) );
	}

	public String solve( final Stream<String> input, final IntPredicate checkSize ) {
		long res = 0;
		final List<Long> range = toPositiveLongList( getFirstString( input ) );
		for ( long i = range.get( 0 ); i < range.get( 1 ); i++ ) {
			if ( acceptable( toDigitsArray( i ), checkSize ) ) {
				res++;
			}
		}
		return itoa( res );
	}

	private boolean acceptable( final int[] arr, final IntPredicate checkSize ) {
		boolean acceptable = false;
		int groupSize = 1;
		for ( int j = 1; j <= arr.length; j++ ) {
			if ( j == arr.length || arr[j] > arr[j - 1] ) {
				//no more digits or digit increasing: check latest group size
				if ( checkSize.test( groupSize ) ) {
					acceptable = true;
				}
				groupSize = 1;
			} else if ( arr[j] == arr[j - 1] ) {
				//same digit, increase current group size
				groupSize++;
			} else {
				//digit decreasing: unacceptable
				acceptable = false;
				break;
			}
		}
		return acceptable;
	}
}
