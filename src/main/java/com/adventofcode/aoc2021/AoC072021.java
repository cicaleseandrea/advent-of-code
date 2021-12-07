package com.adventofcode.aoc2021;

import static java.lang.Math.abs;
import static java.util.Collections.max;
import static java.util.Collections.min;
import static java.util.function.LongUnaryOperator.identity;

import static com.adventofcode.utils.Utils.itoa;

import java.util.List;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC072021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, identity() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, this::triangularNumber );
	}

	private String solve( final Stream<String> input, final LongUnaryOperator computeFuel ) {
		final var positions = input.findFirst().map( Utils::toLongStream ).orElseThrow().toList();
		return itoa( minimizeFuel( positions, computeFuel ) );
	}

	private long minimizeFuel( final List<Long> positions, final LongUnaryOperator computeFuel ) {
		return LongStream.rangeClosed( min( positions ), max( positions ) )
				.map( point -> computeTotalFuel( point, positions, computeFuel ) )
				.min()
				.orElseThrow();
	}

	private long computeTotalFuel( final long point, final List<Long> positions,
			final LongUnaryOperator computeFuel ) {
		return positions.stream().mapToLong( n -> abs( n - point ) ).map( computeFuel ).sum();
	}

	private long triangularNumber( final long distance ) {
		// 1 + 2 + 3 + ... + distance
		return distance * ( distance + 1 ) / 2;
	}
}
