package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.itoa;

import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC122015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return itoa( input.flatMap( Utils::toLongStream ).mapToLong( number -> number ).sum() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return itoa( 0 );
	}

}
