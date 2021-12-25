package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC252021 implements Solution {
	private static final char EAST = '>';
	private static final char SOUTH = 'v';

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var inputList = input.toList();
		final var rows = inputList.size();
		final var columns = inputList.get( 0 ).length();
		final var cucumbers = getCucumbers( inputList );
		var east = cucumbers.getFirst();
		var south = cucumbers.getSecond();

		boolean movedEast;
		boolean movedSouth;
		int i = 0;
		do {
			i++;

			final Set<Point> nextEast = new HashSet<>();
			movedEast = move( east, south, nextEast,
					e -> new Point( e.x, incrementMod( e.y, columns ) ) );
			east = nextEast;

			final Set<Point> nextSouth = new HashSet<>();
			movedSouth = move( south, east, nextSouth,
					e -> new Point( incrementMod( e.x, rows ), e.y ) );
			south = nextSouth;
		} while ( movedEast || movedSouth );

		return itoa( i );
	}

	private boolean move( final Set<Point> moving, final Set<Point> still, final Set<Point> next,
			final UnaryOperator<Point> move ) {
		var moved = false;
		for ( final var cucumber : moving ) {
			final var movedCucumber = move.apply( cucumber );
			if ( moving.contains( movedCucumber ) || still.contains( movedCucumber ) ) {
				next.add( cucumber );
			} else {
				next.add( movedCucumber );
				moved = true;
			}
		}
		return moved;
	}

	private Pair<Set<Point>, Set<Point>> getCucumbers( final List<String> input ) {
		final Set<Point> east = new HashSet<>();
		final Set<Point> south = new HashSet<>();
		for ( int i = 0; i < input.size(); i++ ) {
			final var line = input.get( i ).toCharArray();
			for ( int j = 0; j < line.length; j++ ) {
				final var c = line[j];
				if ( c == EAST ) {
					east.add( new Point( i, j ) );
				} else if ( c == SOUTH ) {
					south.add( new Point( i, j ) );
				}
			}
		}
		return new Pair<>( east, south );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return MERRY_CHRISTMAS;
	}

	private record Point(int x, int y) {}
}
