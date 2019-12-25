package com.adventofcode.aoc2019;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.decrementMod;
import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;

import java.util.List;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC222019 implements Solution {

	private static boolean PRINT = false;

	private static int deckSize;
	private static int posRes;
	private static int posHead;
	private static boolean inverted;
	private static int steps;

	private static void deal( final Integer i ) {
		posRes = ( posRes * i ) % deckSize;

		//TODO?
		steps = ( steps * -i );
		print();
		//steps=i then invert then cut -1
		//		steps = i;
		//		invert();
		//		cut( -1 );
		//deal 7
		//0 3 6 9 2 5 8 1 4 7
		//deal 9
		//0 7 4 1 8 5 2 9 6 3
		//my error
		//0, 3, 6, 9, 2, 5, 8, 1, 4, 7,

		//deal 9
		//0, 9, 8, 7, 6, 5, 4, 3, 2, 1

	}

	private static void cut( final Integer i ) {
		posRes = decrementMod( posRes, i, deckSize );
		if ( !inverted ) {
			posHead = decrementMod( posHead, -i * steps, deckSize );
		} else {
			posHead = decrementMod( posHead, i * steps, deckSize );
		}
		print();
	}

	private static void invert() {
		posRes = ( deckSize - 1 ) - posRes;
		inverted = !inverted;
		if ( !inverted ) {
			posHead = decrementMod( posHead, -steps, deckSize );
		} else {
			posHead = decrementMod( posHead, steps, deckSize );
		}
		print();
	}

	private static void print() {
		if ( PRINT ) {
			int e = posHead;
			System.out.println( "RES: " + posRes );
			System.out.println( "steps: " + steps + " inverted: " + inverted );
			for ( int i = 0; i < deckSize; i++ ) {
				System.out.print( e + ", " );
				if ( !inverted ) {
					e = decrementMod( e, -steps, deckSize );
				} else {
					e = decrementMod( e, steps, deckSize );
				}
			}
			System.out.println();
		}
	}

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final List<String> lines = input.collect( toList() );
		deckSize = lines.size() < 20 ? 10 : 10007;
		posRes = lines.size() < 20 ? 9 : 2019;
		posHead = 0;
		inverted = false;
		steps = 1;
		for ( final String line : lines ) {
			if ( line.startsWith( "cut" ) ) {
				cut( extractIntegerFromString( line ) );
			} else if ( line.startsWith( "deal into" ) ) {
				invert();
			} else {
				deal( extractIntegerFromString( line ) );
			}
		}


		return itoa( posRes );
	}

}
