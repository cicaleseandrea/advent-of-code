package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import com.adventofcode.utils.Pair;

class AoC192019 implements Solution {
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		final List<Long> program = toLongList( getFirstString( input ) );
		final Map<Pair<Long, Long>, Character> grid = new HashMap<>();
		for ( int i = 0; i < 50; i++ ) {
			for ( int j = 0; j < 50; j++ ) {
				grid.put( new Pair<>( (long) i, (long) j ), SPACE );
				in.add( (long) i );
				in.add( (long) j );

				try {
					startComputer( program, true, in, out ).get();
				} catch ( InterruptedException | ExecutionException e ) {
					e.printStackTrace();
				}
			}
		}

		System.out.println( grid.keySet() );

		System.out.println( in );

		System.out.println( out );

		final long res = out.stream().filter( e -> e == 1L ).count();

		return itoa( res );
	}

	private Future<?> startComputer( final List<Long> program, final boolean first,
			final BlockingQueue<Long> in, final BlockingDeque<Long> out ) {
		final Computer2019 computer = new Computer2019( in, out );
		computer.loadProgram( program );
		//		computer.printProgram();
		return computer.runAsync();

	}
}
