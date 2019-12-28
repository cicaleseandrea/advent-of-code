package com.adventofcode.aoc2015;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Map;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC012015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final Map<Character, Long> frequencies = getFirstString( input ).chars()
				.mapToObj( c -> (char) c )
				.collect( groupingBy( identity(), counting() ) );
		return itoa( frequencies.get( '(' ) - frequencies.get( ')' ) );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final char[] charArray = getFirstString( input ).toCharArray();
        int floor = 0;
        int i = 0;
		while ( floor != -1 ) {
			floor += ( charArray[i++] == '(' ) ? 1 : -1;
		}
		return itoa( i );
	}
}
