package com.adventofcode.aoc2019;

import static java.lang.Math.abs;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Triplet;
import com.google.common.collect.Sets;

class AoC122019 implements Solution {
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	public String solve( final Stream<String> input, final boolean first ) {
		final List<Pair<Triplet<Long, Long, Long>, Triplet<Long, Long, Long>>> moons = new ArrayList<>();

		input.forEach( line -> moons.add( getMoon( line ) ) );

		final int steps = getStepsFirstPart( moons.get( 0 ).getFirst().getFirst() );

		final BigInteger stepsX = BigInteger.valueOf(
				cycle( first, moons, steps, Triplet::setFirst, Triplet::getFirst ) );
		final BigInteger stepsY = BigInteger.valueOf(
				cycle( first, moons, steps, Triplet::setSecond, Triplet::getSecond ) );
		final BigInteger stepsZ = BigInteger.valueOf(
				cycle( first, moons, steps, Triplet::setThird, Triplet::getThird ) );

		if ( first ) {
			return itoa( moons.stream().mapToLong( this::totalEnergy ).sum() );
		} else {
			final BigInteger lcmTwoAxis = stepsX.multiply( stepsY ).divide( stepsX.gcd( stepsY ) );
			final BigInteger lcmThreeAxis = lcmTwoAxis.multiply( stepsZ )
					.divide( lcmTwoAxis.gcd( stepsZ ) );
			return lcmThreeAxis.toString();
		}
	}

	private long cycle( final boolean first,
			final List<Pair<Triplet<Long, Long, Long>, Triplet<Long, Long, Long>>> moons,
			final int steps, final BiConsumer<Triplet<Long, Long, Long>, Long> setter,
			final Function<Triplet<Long, Long, Long>, Long> getter ) {
		int i = 0;
		final Set<List<Pair<Long, Long>>> seen = new HashSet<>();
		List<Pair<Long, Long>> config;
		do {
			config = new ArrayList<>();
			computeOneAxis( moons, setter, getter );
			for ( Pair<Triplet<Long, Long, Long>, Triplet<Long, Long, Long>> m : moons ) {
				final Triplet<Long, Long, Long> pos = m.getFirst();
				final Triplet<Long, Long, Long> vel = m.getSecond();
				config.add( new Pair<>( getter.apply( pos ), getter.apply( vel ) ) );
			}
			i++;
		} while ( ( first && i < steps ) || ( !first && seen.add( deepCopy( config ) ) ) );

		return i - 1;
	}

	private List<Pair<Long, Long>> deepCopy( final List<Pair<Long, Long>> config ) {
		final List<Pair<Long, Long>> copy = new ArrayList<>();
		for ( final Pair<Long, Long> pair : config ) {
			copy.add( new Pair<>( pair ) );
		}
		return copy;
	}

	private void computeOneAxis(
			final List<Pair<Triplet<Long, Long, Long>, Triplet<Long, Long, Long>>> moons,
			final BiConsumer<Triplet<Long, Long, Long>, Long> setter,
			final Function<Triplet<Long, Long, Long>, Long> getter ) {
		final var pairs = Sets.combinations( Set.copyOf( moons ), 2 );

		for ( final var pair : pairs ) {
			final var it = pair.iterator();
			final var moon1 = it.next();
			final var moon2 = it.next();

			final var position = moon1.getFirst();
			final var position2 = moon2.getFirst();

			final var velocity = moon1.getSecond();
			final var velocity2 = moon2.getSecond();

			applyGravity( position, position2, velocity, velocity2, setter, getter );
		}

		moons.forEach( moon -> {
			final var position = moon.getFirst();
			final var velocity = moon.getSecond();
			applyVelocity( position, velocity, setter, getter );
		} );
	}

	private int getStepsFirstPart( final Long first ) {
		return switch ( first.intValue() ) {
			case -1 -> 10;
			case -8 -> 100;
			default -> 1000;
		};
	}

	private void applyVelocity( final Triplet<Long, Long, Long> position,
			final Triplet<Long, Long, Long> velocity,
			final BiConsumer<Triplet<Long, Long, Long>, Long> setter,
			final Function<Triplet<Long, Long, Long>, Long> getter ) {
		setter.accept( position, getter.apply( position ) + getter.apply( velocity ) );
	}

	private void applyGravity( final Triplet<Long, Long, Long> position,
			final Triplet<Long, Long, Long> position2, final Triplet<Long, Long, Long> velocity,
			final Triplet<Long, Long, Long> velocity2,
			final BiConsumer<Triplet<Long, Long, Long>, Long> setter,
			final Function<Triplet<Long, Long, Long>, Long> getter ) {

		final long gravity = Long.compare( getter.apply( position ), getter.apply( position2 ) );

		setter.accept( velocity, getter.apply( velocity ) - gravity );
		setter.accept( velocity2, getter.apply( velocity2 ) + gravity );
	}

	private Pair<Triplet<Long, Long, Long>, Triplet<Long, Long, Long>> getMoon(
			final String line ) {
		int i = 0;
		final List<Long> numbers = toLongList( line );
		final var position = new Triplet<>( numbers.get( i++ ), numbers.get( i++ ),
				numbers.get( i++ ) );
		final var velocity = new Triplet<>( 0L, 0L, 0L );

		return new Pair<>( position, velocity );
	}

	private long totalEnergy(
			final Pair<Triplet<Long, Long, Long>, Triplet<Long, Long, Long>> moon ) {
		final var position = moon.getFirst();
		final var velocity = moon.getSecond();
		final long potential = getEnergy( position );
		final long kinetic = getEnergy( velocity );
		return potential * kinetic;
	}

	private long getEnergy( final Triplet<Long, Long, Long> position ) {
		return abs( position.getFirst() ) + abs( position.getSecond() ) + abs(
				position.getThird() );
	}
}
