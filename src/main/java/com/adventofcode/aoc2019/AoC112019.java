package com.adventofcode.aoc2019;

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
import static com.adventofcode.utils.Utils.readOutput;
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
import java.util.function.Function;
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
		return solve( input, DOT, grid -> itoa( grid.size() ) );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, HASH, this::computeImage );
	}

	private String solve( final Stream<String> input, final Character start,
			final Function<Map<Pair<Long, Long>, Character>, String> computeResult ) {
		final Pair<Long, Long> pos = new Pair<>( 0L, 0L );
		final Map<Pair<Long, Long>, Character> grid = new HashMap<>();
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		final Future<?> future = startComputer( input, in, out );

		Direction direction = UP;
		try {
			do {
				//in
				in.add( PANEL_IN.get( grid.getOrDefault( pos, start ) ) );

				//out
				final Long paint = readOutput( out, future );
				if ( paint == null ) {
					//program halted
					break;
				}

				//paint
				grid.put( new Pair<>( pos ), PANEL_OUT.get( paint ) );

				//move
				direction = ROTATE.get( out.take() ).apply( direction );
				MOVE.get( direction ).accept( pos );

			} while ( true );
		} catch ( InterruptedException e ) {
			throw new RuntimeException( e );
		}

		return computeResult.apply( grid );
	}

	private String computeImage( final Map<Pair<Long, Long>, Character> grid ) {
		int maxX = 0;
		int maxY = 0;
		for ( final var p : grid.keySet() ) {
			maxX = Math.max( p.getFirst().intValue(), maxX );
			maxY = Math.max( p.getSecond().intValue(), maxY );
		}
		final long[][] matrix = new long[maxY + 1][maxX + 1];

		//flip image
		grid.keySet()
				.forEach( point -> matrix[point.getSecond().intValue()][point.getFirst()
						.intValue()] = PANEL_IN.get( grid.getOrDefault( point, HASH ) ) );

		final StringBuilder res = new StringBuilder();
		for ( long[] row : matrix ) {
			for ( long e : row ) {
				res.append( PAINT.get( e ) );
			}
			res.append( "\n" );
		}

		return res.toString();
	}

	private Future<?> startComputer( final Stream<String> input, final BlockingQueue<Long> in,
			final BlockingDeque<Long> out ) {
		final List<Long> program = toLongList( getFirstString( input ) );
		final Computer2019 computer = new Computer2019( in, out );
		computer.loadProgram( program );
		return computer.runAsync();
	}

}
