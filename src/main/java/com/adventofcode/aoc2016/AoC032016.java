package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC032016 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return itoa( input.map( Utils::toLongList ).filter( AoC032016::isTriangle ).count() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var lines = input.toList();
		long count = IntStream.iterate( 0, i -> i < lines.size(), i -> i + 3 )
				.boxed()
				.flatMap( i -> IntStream.range( 0, 3 )
						.mapToObj( j -> List.of( Utils.toLongList( lines.get( i ) ).get( j ),
								Utils.toLongList( lines.get( i + 1 ) ).get( j ),
								Utils.toLongList( lines.get( i + 2 ) ).get( j ) ) ) )
				.filter( AoC032016::isTriangle )
				.count();
		return itoa( count );
	}

	private static boolean isTriangle( final List<Long> numbers ) {
		final long x = numbers.get( 0 );
		final long y = numbers.get( 1 );
		final long z = numbers.get( 2 );
		return x + y > z && y + z > x && x + z > y;
	}

}
