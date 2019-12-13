package com.adventofcode.aoc2019;

import static java.util.stream.Collectors.joining;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import com.adventofcode.utils.Utils;

class AoC092019 implements Solution {
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 1 );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 2 );
	}

	private String solve( final Stream<String> input, final long inputNumber ) {
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		in.add( inputNumber );
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		final Computer2019 computer = new Computer2019( in, out );
		final List<Long> program = toLongList( getFirstString( input ) );
		computer.loadProgram( program );
		computer.run();

		return out.stream().map( Utils::itoa ).collect( joining( "\n" ) );
	}

}