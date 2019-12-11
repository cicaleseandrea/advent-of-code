package com.adventofcode.aoc2019;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.BLACK;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.WHITE;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;

class AoC112019 implements Solution {
	Map<Character, Long> PANEL = Map.of( DOT, 0L, HASH, 1L );
	Map<Long, Character> INVERTED_PANEL = Map.of( 0L, DOT, 1L, HASH );
	Map<Long, Character> PAINT = Map.of( 0L, DOT, 1L, HASH );

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private Pair<Long, Long> getcopy( final Pair<Long, Long> pos ) {
		return new Pair<>( pos.getFirst(), pos.getSecond() );
	}

	public String solve( final Stream<String> input, final boolean first ) {
		Direction direction = UP;
		Pair<Long, Long> pos = new Pair<>( 0L, 0L );
		Map<Pair<Long, Long>, Character> grid = new HashMap<>();
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		final Computer2019 computer = new Computer2019( in, out );
		final List<Long> program = toLongList( getFirstString( input ) );
		computer.loadProgram( program );
		Future<?> future = computer.runAsync();
		try {
			do {
				//in
				in.add( PANEL.get( grid.getOrDefault( pos, first ? DOT : HASH ) ) );
				//out
				Long outNumber = null;
				do {
					outNumber = out.poll( 10, MILLISECONDS );
				} while ( outNumber == null && !future.isDone() );
				if ( future.isDone() ) {
					//program halted
					break;
				}
				grid.put( getcopy( pos ), INVERTED_PANEL.get( outNumber ) );

				outNumber = out.take();
				if ( outNumber == 1L ) {
					direction = direction.rotateClockwise();
				} else {
					direction = direction.rotateCounterClockwise();
				}

				switch ( direction ) {
				case UP -> pos.setSecond( pos.getSecond() - 1 );
				case DOWN -> pos.setSecond( pos.getSecond() + 1 );
				case LEFT -> pos.setFirst( pos.getFirst() - 1 );
				case RIGHT -> pos.setFirst( pos.getFirst() + 1 );
				}

			} while ( true );
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		if ( first ) {
			return itoa( grid.size() );
		} else {
			final long[][] matrix = new long[43][6];
			for ( final Pair<Long, Long> point : grid.keySet() ) {
				matrix[point.getFirst().intValue()][point.getSecond()
						.intValue()] = grid.getOrDefault( point, HASH ) == HASH ? 1L : 0L;
			}

			final StringBuilder res = new StringBuilder();
			for ( int j = 0; j < matrix[0].length; j++ ) {
				for ( long[] longs : matrix ) {
					Character character = 1L == longs[j] ? WHITE : BLACK;
					res.append( character );
				}
				res.append( "\n" );
			}

			return res.toString();
		}
	}
}
