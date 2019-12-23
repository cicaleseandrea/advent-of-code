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
		final int rows;
		final int columns;
		int i = 0;
		int j = 0;
		final HashSet<Pair<Long, Long>> asteroids = new HashSet<>();
		for ( final String line : getIterable( input ) ) {
			final char[] charArray = line.toCharArray();
			j = 0;
			while ( j < charArray.length ) {
				if ( charArray[j] == HASH ) {
					asteroids.add( new Pair<>( (long) i, (long) j ) );
				}
				j++;
			}
			i++;
		}
		rows = i;
		columns = j;

		long max = 0;
		Pair<Long, Long> station = null;
		for ( final Pair<Long, Long> asteroid : asteroids ) {
			long res = computeAsteroidsDetected( asteroid, asteroids, rows, columns );
			if ( res > max ) {
				max = res;
				station = asteroid;
			}
		}

		if ( first ) {
			return itoa( max );
		} else {
			final Pair<Long, Long> bet = vaporizeAsteroids( station, asteroids, rows, columns );
			return itoa( bet.getSecond() * 100 + bet.getFirst() );
		}
	}

	private Pair<Long, Long> vaporizeAsteroids( final Pair<Long, Long> station,
			final Set<Pair<Long, Long>> asteroids, final int rows, final int columns ) {

		final NavigableSet<Pair<Long, Long>> angles = new TreeSet<>( comparingDouble(
				it -> -Math.atan2( it.getSecond() - station.getSecond(),
						it.getFirst() - station.getFirst() ) ) );

		//TODO check

		angles.addAll( asteroids );

		long count = 0;
		Pair<Long, Long> res = null;
		Iterator<Pair<Long, Long>> iterator = angles.iterator();
		while ( count < 200 ) {
			final Pair<Long, Long> direction = iterator.next();
			res = vaporizeAsteroids( station, direction, asteroids, rows, columns, false );
			if ( res != null ) {
				count++;
			}
			if ( !iterator.hasNext() ) {
				iterator = angles.iterator();
			}
		}
		return res;
	}

	private long computeAsteroidsDetected( final Pair<Long, Long> station,
			final HashSet<Pair<Long, Long>> asteroids, final int rows, final int columns ) {
		final Set<Pair<Long, Long>> visible = new HashSet<>( asteroids );
		visible.remove( station );
		long count = 0;
		while ( !visible.isEmpty() ) {
			vaporizeAsteroids( station, visible.stream().findAny().orElseThrow(), visible, rows,
					columns,
					true );
			count++;
		}
		return count;
	}

	private Pair<Long, Long> vaporizeAsteroids( final Pair<Long, Long> station,
			final Pair<Long, Long> direction, final Set<Pair<Long, Long>> asteroids, final int rows,
			final int columns, final boolean all ) {
		long iDiff = direction.getFirst() - station.getFirst();
		long jDiff = direction.getSecond() - station.getSecond();
		long gcd = LongMath.gcd( abs( iDiff ), abs( jDiff ) );
		if ( gcd != 0 ) {
			iDiff /= gcd;
			jDiff /= gcd;
		}
		long i = station.getFirst() + iDiff;
		long j = station.getSecond() + jDiff;
		boolean vaporized;
		Pair<Long, Long> vaporize;
		do {
			vaporize = new Pair<>( i, j );
			vaporized = asteroids.remove( vaporize );
			i += iDiff;
			j += jDiff;
		} while ( ( !vaporized || all ) && 0 <= i && i < rows && 0 <= j && j < columns );

		return vaporized ? vaporize : null;
	}

}