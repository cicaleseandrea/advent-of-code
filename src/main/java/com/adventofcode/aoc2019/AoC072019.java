package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Collections2;

class AoC072019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, Set.of( 0, 1, 2, 3, 4 ) );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, Set.of( 5, 6, 7, 8, 9 ) );
	}

	public String solve( final Stream<String> input, final Collection<Integer> availablePhases ) {
		final List<Long> program = toLongList( getFirstString( input ) );
		return Collections2.permutations( availablePhases )
				.stream()
				.map( settings -> runComputers( program, settings.iterator() ) )
				.max( Long::compareTo )
				.map( Utils::itoa )
				.orElseThrow();
	}

	private long runComputers( final List<Long> program, final Iterator<Integer> settings ) {
		BlockingQueue<Long> firstConnection = new LinkedBlockingQueue<>();
		BlockingQueue<Long> previousConnection = firstConnection;
		final Set<Future<?>> computers = new HashSet<>();
		while ( settings.hasNext() ) {
			//configure phase settings
			previousConnection.add( settings.next().longValue() );
			//connect computers to each other. loop back last computer into first
			final BlockingQueue<Long> nextConnection = settings.hasNext() ? new LinkedBlockingQueue<>() : firstConnection;
			computers.add( runComputer( program, previousConnection, nextConnection ) );
			previousConnection = nextConnection;
		}

		try {
			//start computing
			firstConnection.add( 0L );
			for ( final var computer : computers ) {
				//wait for completion
				computer.get();
			}
			return previousConnection.remove();
		} catch ( InterruptedException | ExecutionException e ) {
			throw new RuntimeException( e );
		}
	}

	private Future<?> runComputer( final List<Long> program, final BlockingQueue<Long> previous,
			final BlockingQueue<Long> next ) {
		final Computer2019 computer = new Computer2019( previous, next );
		computer.loadProgram( program );
		return computer.runAsync();
	}

}
