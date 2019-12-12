package com.adventofcode.aoc2019;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.LEFT;
import static com.adventofcode.utils.Direction.RIGHT;
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
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;

class AoC112019 implements Solution {
	private static final Map<Character, Long> PANEL_IN = Map.of( DOT, 0L, HASH, 1L );
	private static final Map<Long, Character> PANEL_OUT = Map.of( 0L, DOT, 1L, HASH );
	private static final Map<Long, Character> PAINT = Map.of( 0L, BLACK, 1L, WHITE );
	private static final Map<Long, UnaryOperator<Direction>> ROTATE = Map.of( 0L,
			Direction::rotateCounterClockwise, 1L, Direction::rotateClockwise );
	//@formatter:off
	private static final Map<Direction, Consumer<Pair<Long, Long>>> MOVE = Map.of(
			UP, pos -> pos.setSecond( pos.getSecond() - 1 ),
			DOWN, pos -> pos.setSecond( pos.getSecond() + 1 ),
			LEFT, pos -> pos.setFirst( pos.getFirst() - 1 ),
			RIGHT, pos -> pos.setFirst( pos.getFirst() + 1 )
	);
	//@formatter:on

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	public String solve( final Stream<String> input, final boolean first ) {
		final Pair<Long, Long> pos = new Pair<>( 0L, 0L );
		final Map<Pair<Long, Long>, Character> grid = new HashMap<>();
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		final List<Long> program = toLongList( getFirstString( input ) );

		final Computer2019 computer = new Computer2019( in, out );
		computer.loadProgram( program );
		final Future<?> future = computer.runAsync();

		Direction direction = UP;
		try {
			do {
				//in
				in.add( PANEL_IN.get( grid.getOrDefault( pos, first ? DOT : HASH ) ) );

				//out
				Long paint;
				do {
					paint = out.poll( 50, MILLISECONDS );
				} while ( paint == null && !future.isDone() );
				if ( future.isDone() ) {
					//program halted
					break;
				}

				//paint
				grid.put( new Pair<>( pos ), PANEL_OUT.get( paint ) );

				//move
				direction = ROTATE.get( out.take() ).apply( direction );
				MOVE.get( direction ).accept( pos );

			} while ( true );
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		if ( first ) {
			return itoa( grid.size() );
		} else {

			//TODO find boundaries programmatically
			final long[][] matrix = new long[43][6];
			for ( final Pair<Long, Long> point : grid.keySet() ) {
				matrix[point.getFirst().intValue()][point.getSecond().intValue()] = PANEL_IN.get(
						grid.getOrDefault( point, HASH ) );
			}

			//flip image
			final StringBuilder res = new StringBuilder();
			for ( int j = 0; j < matrix[0].length; j++ ) {
				for ( long[] longs : matrix ) {
					res.append( PAINT.get( longs[j] ) );
				}
				res.append( "\n" );
			}

			return res.toString();
		}
	}

}
