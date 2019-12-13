package com.adventofcode.aoc2019;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

	private String solve( final Stream<String> input, final boolean first ) {
		final List<Triplet<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>> moons = new ArrayList<>();

		input.forEach( line -> moons.add( getMoon( line ) ) );

		final int steps = getStepsFirstPart( moons.get( 0 ).getFirst().getFirst() );

		final BigInteger stepsX = BigInteger.valueOf(
				cycle( first, moons, steps, Triplet::getFirst ) );
		final BigInteger stepsY = BigInteger.valueOf(
				cycle( first, moons, steps, Triplet::getSecond ) );
		final BigInteger stepsZ = BigInteger.valueOf(
				cycle( first, moons, steps, Triplet::getThird ) );

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
			final List<Triplet<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>> moons,
			final int steps,
			final Function<Triplet<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>, Pair<Long, Long>> axisGetter ) {
		int i = 0;
		final var initialState = deepCopy( moons, axisGetter );
		List<Pair<Long, Long>> state;
		do {
			computeOneStep( moons, axisGetter );
			state = moons.stream().map( axisGetter ).collect( toList() );
			i++;
		} while ( ( first && i < steps ) || ( !first && !initialState.equals( state ) ) );
		return i;
	}

	private List<Pair<Long, Long>> deepCopy(
			final List<Triplet<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>> moons,
			final Function<Triplet<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>, Pair<Long, Long>> axisGetter ) {
		return moons.stream().map( axisGetter ).map( Pair::new ).collect( toList() );
	}

	private void computeOneStep(
			final List<Triplet<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>> moons,
			final Function<Triplet<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>>, Pair<Long, Long>> axisGetter ) {
		final var pairs = Sets.combinations( Set.copyOf( moons ), 2 );

		for ( final var pair : pairs ) {
			final var it = pair.iterator();
			final var moon1 = it.next();
			final var moon2 = it.next();

			applyGravity( axisGetter.apply( moon1 ), axisGetter.apply( moon2 ) );
		}

		moons.stream().map( axisGetter ).forEach( this::applyVelocity );
	}

	private int getStepsFirstPart( final Long first ) {
		return switch ( first.intValue() ) {
			case -1 -> 10;
			case -8 -> 100;
			default -> 1000;
		};
	}

	private void applyVelocity( final Pair<Long, Long> axis ) {
		//position = position + velocity
		axis.setFirst( axis.getFirst() + axis.getSecond() );
	}

	private void applyGravity( final Pair<Long, Long> axis, final Pair<Long, Long> axis2 ) {
		//compute gravity based on positions
		final long gravity = Long.compare( axis.getFirst(), axis2.getFirst() );
		//update velocities based on gravity
		axis.setSecond( axis.getSecond() - gravity );
		axis2.setSecond( axis2.getSecond() + gravity );
	}

	private Triplet<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>> getMoon(
			final String line ) {
		int i = 0;
		final List<Long> numbers = toLongList( line );
		final var x = new Pair<>( numbers.get( i++ ), 0L );
		final var y = new Pair<>( numbers.get( i++ ), 0L );
		final var z = new Pair<>( numbers.get( i++ ), 0L );

		return new Triplet<>( x, y, z );
	}

	private long totalEnergy(
			final Triplet<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>> moon ) {
		final long potential = getEnergy( moon, Pair::getFirst );
		final long kinetic = getEnergy( moon, Pair::getSecond );
		return potential * kinetic;
	}

	private long getEnergy(
			final Triplet<Pair<Long, Long>, Pair<Long, Long>, Pair<Long, Long>> moon,
			final Function<Pair<Long, Long>, Long> getter ) {
		return abs( getter.apply( moon.getFirst() ) ) + abs(
				getter.apply( moon.getSecond() ) ) + abs( getter.apply( moon.getThird() ) );
	}
}
