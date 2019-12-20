package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;

class AoC052019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 1 );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 5 );
	}

	private String solve( final Stream<String> input, final long inputNumber ) {
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		in.add( inputNumber );
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		final List<Long> program = toLongList( getFirstString( input ) );
		Computer2019.runComputer( program, in, out, false );

		return itoa( out.removeLast() );
	}

}
