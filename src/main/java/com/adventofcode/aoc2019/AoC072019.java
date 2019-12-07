package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

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
import com.google.common.collect.Collections2;

class AoC072019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, List.of( 0, 1, 2, 3, 4 ) );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, List.of( 5, 6, 7, 8, 9 ) );
	}

	public String solve( final Stream<String> input, final List<Integer> elements ) {
		final List<Long> program = toLongList( getFirstString( input ) );
		long res = 0;
		for ( final List<Integer> settings : Collections2.permutations( elements ) ) {
			try {
				res = Math.max( res, runAmplifiers( program, settings ) );
			} catch ( InterruptedException | ExecutionException e ) {
				e.printStackTrace();
			}
		}
		return itoa( res );
	}

	private long runAmplifiers( final List<Long> program, final Iterable<Integer> settings )
			throws InterruptedException, ExecutionException {
		BlockingQueue<Long> first = new LinkedBlockingQueue<>();
		BlockingQueue<Long> previous = first;
		Iterator<Integer> iterator = settings.iterator();
		final Set<Future<?>> futures = new HashSet<>();
		while ( iterator.hasNext() ) {
			final long phase = iterator.next();
			previous.add( phase );
			final BlockingQueue<Long> next = iterator.hasNext() ? new LinkedBlockingQueue<>() : first;
			futures.add( createAmplifier( program, previous, next ).runAsync() );
			previous = next;
		}
		first.put( 0L );
		for ( final Future<?> f : futures ) {
			f.get();
		}
		return previous.take();
	}

	private Computer2019 createAmplifier( final List<Long> program, final BlockingQueue<Long> in,
			final BlockingQueue<Long> out ) {
		final Computer2019 computer = new Computer2019( true, in, out );
		computer.loadProgram( program );
		return computer;
	}

}
