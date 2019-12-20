package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.List;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;

class AoC022019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		final List<Long> program = toLongList( getFirstString( input ) );
		return itoa( computeResult( program, 12, 2 ) );
	}

	public String solveSecondPart( final Stream<String> input ) {
		final List<Long> program = toLongList( getFirstString( input ) );
		for ( int noun = 0; noun <= 99; noun++ ) {
			for ( int verb = 0; verb <= 99; verb++ ) {
				if ( computeResult( program, noun, verb ).equals( 19690720L ) ) {
					return itoa( 100 * noun + verb );
				}
			}
		}
		throw new IllegalStateException();
	}

	private Long computeResult( final List<Long> program, final long noun, final long verb ) {
		if ( program.size() > 10 ) {
			program.set( 1, noun );
			program.set( 2, verb );
		}
		final Computer2019 computer = new Computer2019( program );
		computer.run();
		return computer.memory.get( 0L );
	}

}
