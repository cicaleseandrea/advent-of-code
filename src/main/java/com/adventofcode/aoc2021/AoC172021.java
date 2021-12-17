package com.adventofcode.aoc2021;

import static java.lang.Math.max;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC172021 implements Solution {
	private static final Pattern TARGET_AREA_REGEX = Pattern.compile(
			"target area: x=(\\d+)..(\\d+), y=(-?\\d+)..(-?\\d+)" );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var targetArea = getTargetArea( input.findFirst().orElseThrow() );

		int highestY = 0;
		int count = 0;
		for ( int velocityX = 0; velocityX < 1000; velocityX++ ) {
			for ( int velocityY = -1000; velocityY < 1000; velocityY++ ) {
				final var hit = launchProbe( new Pair<>( velocityX, velocityY ), targetArea );
				if ( hit.isPresent() ) {
					highestY = max( highestY, hit.get() );
					count++;
				}
			}
		}
		return itoa( first ? highestY : count );
	}

	private Optional<Integer> launchProbe( final Pair<Integer, Integer> velocities,
			final Pair<Point, Point> targetArea ) {
		int velocityX = velocities.getFirst();
		int velocityY = velocities.getSecond();
		final Point topLeft = targetArea.getFirst();
		final Point bottomRight = targetArea.getSecond();
		int x = 0;
		int y = 0;
		int highestY = 0;
		while ( x <= bottomRight.x && y >= bottomRight.y ) {
			if ( topLeft.x <= x && y <= topLeft.y ) {
				return Optional.of( highestY );
			}
			x += velocityX;
			y += velocityY;
			highestY = max( highestY, y );
			velocityX -= velocityX > 0 ? 1 : 0;
			velocityY -= 1;
		}
		return Optional.empty();
	}

	private Pair<Point, Point> getTargetArea( final String input ) {
		final var matcher = TARGET_AREA_REGEX.matcher( input );
		if ( !matcher.matches() ) {
			throw new IllegalArgumentException();
		}
		final var topLeft = new Point( atoi( matcher.group( 1 ) ), atoi( matcher.group( 4 ) ) );
		final var bottomRight = new Point( atoi( matcher.group( 2 ) ), atoi( matcher.group( 3 ) ) );
		return new Pair<>( topLeft, bottomRight );
	}

	private record Point(int x, int y) {}
}
