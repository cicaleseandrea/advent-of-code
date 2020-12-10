package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.itoa;

import java.util.HashSet;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;

class AoC012016 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		var position = new Pair<>( 0L, 0L );
		var direction = Direction.UP;
		final var seen = new HashSet<>();
		for ( final String turn : Utils.getFirstString( input ).split( ", " ) ) {
			if ( turn.charAt( 0 ) == 'R' ) {
				direction = direction.rotateClockwise();
			} else {
				direction = direction.rotateCounterClockwise();
			}
			final int steps = Integer.parseInt( turn.substring( 1 ) );
			for ( int i = 0; i < steps; i++ ) {
				if ( !first && !seen.add( position ) ) {
					return itoa( Utils.manhattanDistance( Pair.ZERO, position ) );
				}
				final long x = switch ( direction ) {
					case DOWN, UP -> 0;
					case RIGHT -> 1;
					case LEFT -> -1;
				};
				final long y = switch ( direction ) {
					case LEFT, RIGHT -> 0;
					case UP -> 1;
					case DOWN -> -1;
				};
				position = new Pair<>( position.getFirst() + x, position.getSecond() + y );

			}
		}
		return itoa( Utils.manhattanDistance( Pair.ZERO, position ) );
	}

}
