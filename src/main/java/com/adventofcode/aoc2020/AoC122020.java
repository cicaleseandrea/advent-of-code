package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

class AoC122020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private static String solve( final Stream<String> input, final boolean first ) {
		final Pair<Integer, Integer> ship = new Pair<>( 0, 0 );
		var direction = Direction.RIGHT;
		final Pair<Integer, Integer> waypoint = new Pair<>( 10, 1 );
		for ( final String line : input.toList() ) {
			final char action = line.charAt( 0 );
			final int value = atoi( line.substring( 1 ) );
			final Pair<Integer, Integer> move = new Pair<>( 0, 0 );
			switch ( action ) {
			case 'R' -> direction = rotate( Direction::rotateClockwise, 1, value, direction,
					waypoint );
			case 'L' -> direction = rotate( Direction::rotateCounterClockwise, -1, value, direction,
					waypoint );
			case 'F' -> {
				final Pair<Integer, Integer> forward = getForward( first, direction, waypoint );
				ship.setFirst( ship.getFirst() + value * forward.getFirst() );
				ship.setSecond( ship.getSecond() + value * forward.getSecond() );
			}
			case 'E' -> move.setFirst( value );
			case 'W' -> move.setFirst( -value );
			case 'N' -> move.setSecond( value );
			case 'S' -> move.setSecond( -value );
			default -> throw new IllegalStateException( "Unexpected value: " + action );
			}
			final Pair<Integer, Integer> pointToMove = first ? ship : waypoint;
			pointToMove.setFirst( pointToMove.getFirst() + move.getFirst() );
			pointToMove.setSecond( pointToMove.getSecond() + move.getSecond() );
		}
		return itoa( Math.abs( ship.getFirst() ) + Math.abs( ship.getSecond() ) );
	}

	private static Pair<Integer, Integer> getForward( final boolean first,
			final Direction direction, final Pair<Integer, Integer> waypoint ) {
		if ( first ) {
			return switch ( direction ) {
				case RIGHT -> new Pair<>( 1, 0 );
				case LEFT -> new Pair<>( -1, 0 );
				case UP -> new Pair<>( 0, 1 );
				case DOWN -> new Pair<>( 0, -1 );
			};
		} else {
			return waypoint;
		}
	}

	private static Direction rotate( final UnaryOperator<Direction> rotateDirection,
			final int flipX, final int degrees, final Direction direction,
			final Pair<Integer, Integer> waypoint ) {
		Direction newDirection = direction;
		for ( int i = 0; i < degrees / 90; i++ ) {
			newDirection = rotateDirection.apply( newDirection );
			final int newWaypointX = waypoint.getSecond() * flipX;
			final int newWaypointY = waypoint.getFirst() * -flipX;
			waypoint.setFirst( newWaypointX );
			waypoint.setSecond( newWaypointY );
		}
		return newDirection;
	}

}
