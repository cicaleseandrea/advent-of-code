package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Pair.ZERO;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;

class AoC032015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private static String solve( final Stream<String> input, final boolean first ) {
		Pair<Long, Long> santaPosition = new Pair<>( ZERO );
		Pair<Long, Long> roboSantaPosition = new Pair<>( ZERO );
		Pair<Long, Long> position;

		final Set<Pair<Long, Long>> santaPresents = new HashSet<>();
		santaPresents.add( new Pair<>( santaPosition ) );

		boolean santa = true;
		for ( final char c : getFirstString( input ).toCharArray() ) {
			position = ( first || santa ) ? santaPosition : roboSantaPosition;
			deliverPresents( c, position, santaPresents );
			santa = !santa;
		}
		return itoa( santaPresents.size() );
	}

	private static void deliverPresents( char c, Pair<Long, Long> santaPosition,
			Set<Pair<Long, Long>> santaPresents ) {
		Direction.fromSymbol( c ).move( santaPosition );
		santaPresents.add( new Pair<>( santaPosition ) );
	}
}
