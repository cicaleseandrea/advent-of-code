package com.adventofcode.aoc2019;

import static java.lang.Math.min;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.LEFT;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.clearScreen;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.printMatrix;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;

class AoC152019 implements Solution {

	private static final long STUCK = 0L;
	private static final long MOVED = 1L;
	private static final long FOUND = 2L;
	private static final char ROOM = '░';
	private static final char WALL = '▓';
	private static final char UNKNOWN = '▒';
	private static final char OXYGEN = 'X';
	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );

	private static final Map<Long, Character> STATUS = Map.of( STUCK, WALL, MOVED, ROOM, FOUND,
			OXYGEN );

	private static final Map<Direction, Long> MOVE_COMMAND = Map.of( UP, 1L, DOWN, 2L, LEFT, 3L,
			RIGHT, 4L );

	private static final Map<Long, UnaryOperator<Direction>> ROTATE = Map.of( STUCK,
			Direction::rotateCounterClockwise, MOVED, Direction::rotateClockwise, FOUND,
			Direction::rotateClockwise );

	//@formatter:off
	private static final Map<Direction, UnaryOperator<Pair<Long, Long>>> MOVE_POSITION = Map.of(
			UP, pos -> new Pair<>( pos.getFirst(), pos.getSecond() - 1 ),
			DOWN, pos -> new Pair<>( pos.getFirst(), pos.getSecond() + 1 ),
			LEFT, pos -> new Pair<>( pos.getFirst() - 1, pos.getSecond() ),
			RIGHT, pos -> new Pair<>( pos.getFirst() + 1, pos.getSecond() ) );
	//@formatter:on

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		startComputer( input, in, out );

		final Map<Pair<Long, Long>, Pair<Character, Long>> maze = new HashMap<>();
		final Pair<Long, Long> droidPosition = exploreMaze( in, out, maze, new Pair<>( 0L, 0L ) );

		if ( first ) {
			return itoa( maze.get( droidPosition ).getSecond() );
		}

		final Map<Pair<Long, Long>, Pair<Character, Long>> invertedMaze = getInvertedMaze( maze );

		exploreMaze( in, out, invertedMaze, droidPosition );

		return invertedMaze.values()
				.stream()
				.map( Pair::getSecond )
				.max( Long::compareTo )
				.map( Utils::itoa )
				.orElseThrow();
	}

	private Pair<Long, Long> exploreMaze( final BlockingQueue<Long> in,
			final BlockingDeque<Long> out, final Map<Pair<Long, Long>, Pair<Character, Long>> maze,
			final Pair<Long, Long> start ) {
		Pair<Long, Long> droidPosition = start;
		maze.put( droidPosition, new Pair<>( ROOM, 0L ) );
		Long status;
		long steps = 0;
		Direction direction = RIGHT;
		try {
			//the maze is simply connected: explore it using wall follower right-hand rule
			//(no need for BFS, Dijkstra, spawning of multiple copies of the computer)
			//keep track of distances
			do {
				//move
				final Long movement = MOVE_COMMAND.get( direction );
				in.add( movement );

				//status
				status = out.take();

				//update maze position
				final Pair<Long, Long> mazePosition = MOVE_POSITION.get( direction )
						.apply( droidPosition );

				//update droid position
				if ( status != STUCK ) {
					droidPosition = mazePosition;

					steps++;
					final Pair<Character, Long> old;
					if ( ( old = maze.get( droidPosition ) ) != null ) {
						//update room distance
						old.setSecond( min( steps, old.getSecond() ) );
					} else {
						//add room position
						maze.put( droidPosition, new Pair<>( UNKNOWN, steps ) );
					}
					steps = maze.get( droidPosition ).getSecond();
				}

				//add maze position
				maze.put( mazePosition, new Pair<>( STATUS.get( status ), steps ) );

				//change direction
				direction = ROTATE.get( status ).apply( direction );

				if ( PRINT ) {
					printMaze( maze );
				}

			} while ( status != FOUND );
		} catch ( InterruptedException e ) {
			throw new RuntimeException( e );
		}
		return droidPosition;
	}

	private void printMaze( final Map<Pair<Long, Long>, Pair<Character, Long>> maze )
			throws InterruptedException {
		clearScreen();
		final int offset = 21;
		final int size = 41;
		final Character[][] matrix = new Character[size][size];
		for ( final var row : matrix ) {
			Arrays.fill( row, UNKNOWN );
		}
		maze.keySet()
				.forEach( point -> matrix[point.getSecond().intValue() + offset][point.getFirst()
						.intValue() + offset] = maze.getOrDefault( point,
						new Pair<>( UNKNOWN, 0L ) ).getFirst() );
		System.out.println();
		printMatrix( matrix );
		System.out.println();
		Thread.sleep( 20 );
	}

	private Map<Pair<Long, Long>, Pair<Character, Long>> getInvertedMaze(
			final Map<Pair<Long, Long>, Pair<Character, Long>> maze ) {
		if ( !PRINT ) {
			return new HashMap<>();
		} else {
			for ( final var cell : maze.entrySet() ) {
				cell.setValue( new Pair<>( cell.getValue().getFirst(), Long.MAX_VALUE ) );
			}
			return maze;
		}
	}

	private Future<?> startComputer( final Stream<String> input, final BlockingQueue<Long> in,
			final BlockingDeque<Long> out ) {
		final List<Long> program = toLongList( getFirstString( input ) );
		final Computer2019 computer = new Computer2019( in, out );
		computer.loadProgram( program );
		return computer.runAsync();
	}
}
