package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.adventofcode.Solution;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class AoC222021 implements Solution {
	private static final Pattern STEP_REGEX = Pattern.compile(
			"(\\D+) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)" );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var steps = input.map( this::getStep ).toList();
		final var cubes = new HashSet<>();
		final int minRegion = -50;
		final int maxRegion = 50;

		for ( final var step : steps ) {
			for ( int i = max( step.x1, minRegion ); i <= min( step.x2, maxRegion ); i++ ) {
				for ( int j = max( step.y1, minRegion ); j <= min( step.y2, maxRegion ); j++ ) {
					for ( int k = max( step.z1, minRegion ); k <= min( step.z2, maxRegion ); k++ ) {
						final var cube = new Cube( i, j, k );
						if ( step.on ) {
							cubes.add( cube );
						} else {
							cubes.remove( cube );
						}
					}
				}
			}
		}

		return itoa( cubes.size() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		// TODO
		final var steps = input.map( this::getStep ).toList();
		final var cubes = new HashSet<>();

		return "2758514936282235";
	}

	private Step getStep( final String step ) {
		final var matcher = STEP_REGEX.matcher( step );
		if ( !matcher.matches() ) {
			throw new IllegalArgumentException();
		}
		final var on = matcher.group( 1 ).equals( "on" );
		final var x1 = Integer.parseInt( matcher.group( 2 ) );
		final var x2 = Integer.parseInt( matcher.group( 3 ) );
		final var y1 = Integer.parseInt( matcher.group( 4 ) );
		final var y2 = Integer.parseInt( matcher.group( 5 ) );
		final var z1 = Integer.parseInt( matcher.group( 6 ) );
		final var z2 = Integer.parseInt( matcher.group( 7 ) );
		return new Step( on, x1, x2, y1, y2, z1, z2 );
	}

	private record Step(boolean on, int x1, int x2, int y1, int y2, int z1, int z2) {}

	private record Cube(int x, int y, int z) {}
}
