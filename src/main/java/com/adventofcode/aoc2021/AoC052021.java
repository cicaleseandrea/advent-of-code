package com.adventofcode.aoc2021;

import static java.lang.Long.max;
import static java.lang.Long.signum;
import static java.lang.Math.abs;

import static com.adventofcode.utils.Utils.incrementMapElement;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC052021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, this::notDiagonal );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, line -> true );
	}

	private String solve( final Stream<String> input, final Predicate<Line> considerLine ) {
		final var lines = getLines( input, considerLine );
		final var map = fillMap( lines );
		final long overlappingPoints = map.values()
				.stream()
				.filter( numberOfLines -> numberOfLines > 1 )
				.count();
		return itoa( overlappingPoints );
	}

	private Set<Line> getLines( final Stream<String> input, final Predicate<Line> considerLine ) {
		return input.map( Utils::toLongList )
				.map( this::getLine )
				.filter( considerLine )
				.collect( Collectors.toSet() );
	}

	private Line getLine( final List<Long> lineNumbers ) {
		return new Line( new Point( lineNumbers.get( 0 ), lineNumbers.get( 1 ) ),
				new Point( lineNumbers.get( 2 ), lineNumbers.get( 3 ) ) );
	}

	private Map<Point, Long> fillMap( final Set<Line> lines ) {
		final Map<Point, Long> map = new HashMap<>();
		for ( final var line : lines ) {
			final long xDifference = line.end.x - line.start.x;
			final long yDifference = line.end.y - line.start.y;
			final long steps = max( abs( xDifference ), abs( yDifference ) );
			for ( long i = 0; i <= steps; i++ ) {
				final var point = new Point( line.start.x + signum( xDifference ) * i,
						line.start.y + signum( yDifference ) * i );
				incrementMapElement( map, point );
			}
		}
		return map;
	}

	private boolean notDiagonal( final Line line ) {
		return line.start.x == line.end.x || line.start.y == line.end.y;
	}

	private record Line(Point start, Point end) {}

	private record Point(long x, long y) {}
}
