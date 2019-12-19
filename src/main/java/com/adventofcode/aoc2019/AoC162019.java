package com.adventofcode.aoc2019;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.joining;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.incrementMod;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC162019 implements Solution {
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final String inputStr = getFirstString( input );
		int[] inputSignal = inputStr.chars().map( Utils::charToInt ).toArray();
		int[] signal;

		if ( first ) {
			signal = inputSignal;
			final int[] pattern = new int[] { 0, 1, 0, -1 };
			final int phases = inputSignal.length < 10 ? 4 : 100;
			for ( int i = 0; i < phases; i++ ) {
				//each phase
				final int[] previousSignal = signal;
				signal = IntStream.range( 0, inputSignal.length )
						.parallel().map( j -> computeDigit( pattern, previousSignal, j ) )
						.toArray();
			}
		} else {
			final int offset = atoi( inputStr.substring( 0, 7 ) );
			signal = getSignal( inputSignal, offset );
			//optimization possible only because the offset is always greater than half the size of the signal
			for ( int i = 0; i < 100; i++ ) {
				for ( int j = signal.length - 2; j >= 0; j-- ) {
					signal[j] += signal[j + 1];
					signal[j] %= 10;
				}
			}
		}

		return Arrays.stream( signal ).mapToObj( Utils::itoa ).limit( 8 ).collect( joining() );
	}

	private int computeDigit( final int[] pattern, final int[] signal, final int j ) {
		int pos = ( j == 0 ) ? 1 : 0;
		int res = 0;
		for ( int k = 0; k < signal.length; k++ ) {
			res += signal[k] * pattern[pos];

			//don't move until you use first pattern digit enough times
			//move to next pattern digit every j+1 steps
			if ( ( k + 2 ) % ( j + 1 ) == 0 ) {
				pos = incrementMod( pos, pattern.length );
			}
		}
		return abs( res ) % 10;
	}

	private int[] getSignal( final int[] inputSignal, final int offset ) {
		final int start = offset % inputSignal.length;
		final int[] biggerSignal = new int[inputSignal.length * 10000 - offset];

		System.arraycopy( inputSignal, start, biggerSignal, 0, inputSignal.length - start );

		int dest = inputSignal.length - start;
		while ( dest < biggerSignal.length ) {
			System.arraycopy( inputSignal, 0, biggerSignal, dest,
					Math.min( inputSignal.length, biggerSignal.length - dest ) );
			dest += inputSignal.length;
		}
		return biggerSignal;
	}
}
