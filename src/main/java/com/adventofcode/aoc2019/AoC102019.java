package com.adventofcode.aoc2019;

import static java.lang.Math.abs;
import static java.util.Comparator.comparingDouble;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.getIterable;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.math.LongMath;

class AoC102019 implements Solution {
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final int columns;
		final int rows;
		int x = 0;
		int y = 0;
		final HashSet<Pair<Long, Long>> asteroids = new HashSet<>();
		for ( final String line : getIterable( input ) ) {
			x = 0;
			for ( final char c : line.toCharArray() ) {
				if ( c == HASH ) {
					asteroids.add( new Pair<>( (long) x, (long) y ) );
				}
				x++;
			}
			y++;
		}
		columns = x;
		rows = y;

		long max = 0;
		Pair<Long, Long> station = null;
		for ( final Pair<Long, Long> asteroid : asteroids ) {
			long res = computeAsteroidsDetected( asteroid, asteroids, columns, rows );
			if ( res > max ) {
				max = res;
				station = asteroid;
			}
		}

		if ( first ) {
			return itoa( max );
		} else {
			final Pair<Long, Long> bet = vaporizeAsteroids( station, asteroids, columns, rows );
			return itoa( bet.getFirst() * 100 + bet.getSecond() );
		}
	}

	private long computeAsteroidsDetected( final Pair<Long, Long> station,
			final HashSet<Pair<Long, Long>> asteroids, final int columns, final int rows ) {
		final Set<Pair<Long, Long>> visible = new HashSet<>( asteroids );
		visible.remove( station );
		long count = 0;
		while ( !visible.isEmpty() ) {
			vaporizeAsteroids( station, visible.stream().findAny().orElseThrow(), visible, columns,
					rows, true );
			count++;
		}
		return count;
	}

	private Pair<Long, Long> vaporizeAsteroids( final Pair<Long, Long> station,
			final Pair<Long, Long> direction, final Set<Pair<Long, Long>> asteroids,
			final int columns, final int rows, final boolean all ) {
		long xDiff = direction.getFirst() - station.getFirst();
		long yDiff = direction.getSecond() - station.getSecond();
		long gcd = LongMath.gcd( abs( xDiff ), abs( yDiff ) );
		if ( gcd != 0 ) {
			xDiff /= gcd;
			yDiff /= gcd;
		}
		long x = station.getFirst() + xDiff;
		long y = station.getSecond() + yDiff;
		boolean vaporized;
		Pair<Long, Long> vaporize;
		do {
			vaporize = new Pair<>( x, y );
			vaporized = asteroids.remove( vaporize );
			x += xDiff;
			y += yDiff;
		} while ( ( !vaporized || all ) && 0 <= x && x < columns && 0 <= y && y < rows );

		return vaporized ? vaporize : null;
	}

	private Pair<Long, Long> vaporizeAsteroids( final Pair<Long, Long> station,
			final Set<Pair<Long, Long>> asteroids, final int columns, final int rows ) {

		final NavigableSet<Pair<Long, Long>> angles = new TreeSet<>( comparingDouble(
				it -> -Math.atan2( it.getFirst() - station.getFirst(),
						it.getSecond() - station.getSecond() ) ) );

		angles.addAll( asteroids );

		long count = 0;
		Pair<Long, Long> res = null;
		Iterator<Pair<Long, Long>> iterator = angles.iterator();
		while ( count < 200 ) {
			final Pair<Long, Long> direction = iterator.next();
			res = vaporizeAsteroids( station, direction, asteroids, columns, rows, false );
			if ( res != null ) {
				count++;
			}
			if ( !iterator.hasNext() ) {
				iterator = angles.iterator();
			}
		}
		return res;
	}

}