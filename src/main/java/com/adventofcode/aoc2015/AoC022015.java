package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.itoa;

import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Sets;

class AoC022015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, Math::multiplyExact, ( side ) -> 2 * side,
				( sides, partial ) -> partial );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, ( a, b ) -> 2 * ( a + b ), ( side ) -> 0L,
				( sides, partial ) -> sides.get( 0 ) * sides.get( 1 ) * sides.get( 2 ) );
	}

	private static String solve( final Stream<String> input, final BinaryOperator<Long> computeSide,
			final UnaryOperator<Long> computePartialResult,
			final BiFunction<List<Long>, Long, Long> computeFinalResult ) {
		return itoa( input.map( Utils::toLongList ).mapToLong( sides -> {
			long min = Long.MAX_VALUE;
			long partialRes = 0;
			for ( final var pair : Sets.combinations( Set.of( 0, 1, 2 ), 2 ) ) {
				var it = pair.iterator();
				final long side = computeSide.apply( sides.get( it.next() ),
						sides.get( it.next() ) );
				if ( side < min ) {
					min = side;
				}
				partialRes += computePartialResult.apply( side );
			}
			return min + computeFinalResult.apply( sides, partialRes );
		} ).sum() );
	}
}
