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
		int[] signal = inputStr.chars().map( Utils::charToInt ).toArray();

		if ( !first ) {
			int[] biggerSignal = new int[signal.length * 10000];
			for ( int i = 0; i < 10000; i++ ) {
				System.arraycopy( signal, 0, biggerSignal, signal.length * i, signal.length );
			}
			signal = biggerSignal;
		}

		final int[] pattern = new int[] { 0, 1, 0, -1 };
		final int phases = signal.length < 10 ? 4 : 100;
		for ( int i = 0; i < phases; i++ ) {
			//each phase
			final int[] finalSignal = signal;
			signal = IntStream.range( 0, signal.length )
					.parallel()
					.map( j -> computeDigit( pattern, finalSignal, j ) )
					.toArray();

		}
		if ( first ) {
			return Arrays.stream( signal ).mapToObj( Utils::itoa ).limit( 8 ).collect( joining() );
		} else {
			final long offset = atoi( inputStr.substring( 0, 7 ) );
			System.out.println( offset );
			return Arrays.stream( signal )
					.mapToObj( Utils::itoa )
					.skip( offset )
					.limit( 8 )
					.collect( joining() );
		}
	}

	private int computeDigit( final int[] pattern, final int[] signal, final int j ) {
		int pos = ( j == 0 ) ? 1 : 0;
		int tmp = 0;
		for ( int k = 0; k < signal.length; k++ ) {
			tmp += signal[k] * pattern[pos];

			//don't move until you use first pattern digit enough times
			//move to next pattern digit every j+1 steps
			if ( ( k + 2 ) % ( j + 1 ) == 0 ) {
				pos = incrementMod( pos, pattern.length );
			}
		}
		return simplifyDigit( tmp );
	}

	private int simplifyDigit( final int number ) {
		return abs( number ) % 10;
	}
}
