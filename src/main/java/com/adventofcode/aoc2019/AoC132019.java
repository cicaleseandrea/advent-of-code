package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.AT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.PLUS;
import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.TILDE;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.printMatrix;
import static com.adventofcode.utils.Utils.readOutput;
import static com.adventofcode.utils.Utils.toLongList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import com.adventofcode.utils.Pair;

class AoC132019 implements Solution {
	private static final int COLUMNS = 37;
	private static final char ROWS = 26;
	private static final char EMPTY = SPACE;
	private static final char WALL = HASH;
	private static final char BLOCK = TILDE;
	private static final char PADDLE = PLUS;
	private static final char BALL = AT;
	private static final Map<Long, Character> TILES = Map.of( 0L, EMPTY, 1L, WALL, 2L, BLOCK,
			3L, PADDLE, 4L, BALL );

	private static final boolean DRAW = false;
	private static boolean INTERACTIVE = false;

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		final Future<?> future = startComputer( input, first, in, out );

		final Map<Pair<Long, Long>, Character> grid = new HashMap<>();
		Long score = 0L;
		Long paddlePosition = 0L;

		if ( INTERACTIVE ) {
			enableInteractiveMode( in, future );
		}

		try {
			do {
				//out
				final Long x = readOutput( out, future );
				if ( x == null ) {
					//program halted
					break;
				}

				final Long y = out.take();
				final Long tile = out.take();

				if ( !first && x == -1 && y == 0 ) {
					//update score
					score = tile;
				} else {
					//draw
					final Character symbol = TILES.get( tile );
					grid.put( new Pair<>( y, x ), symbol );
					paddlePosition = ( symbol == PADDLE ) ? x : paddlePosition;
					if ( !INTERACTIVE && symbol == BALL ) {
						movePaddle( paddlePosition, in, x );
					}
					if ( ( DRAW || INTERACTIVE ) && ( x == COLUMNS - 1 || Objects.equals( symbol,
							BALL ) ) ) {
						printGame( grid, score );
						Thread.sleep( 400 );
					}
				}

			} while ( true );
		} catch ( InterruptedException e ) {
			throw new RuntimeException( e );
		}

		if ( first ) {
			return itoa( grid.values().stream().filter( c -> c == BLOCK ).count() );
		} else {
			printResult( grid );
			return itoa( score );
		}
	}

	private Future<?> startComputer( final Stream<String> input, final boolean first,
			final BlockingQueue<Long> in, final BlockingDeque<Long> out ) {
		final List<Long> program = toLongList( getFirstString( input ) );
		if ( !first ) {
			//play
			program.set( 0, 2L );
		}

		final Computer2019 computer = new Computer2019( in, out );
		computer.loadProgram( program );
		return computer.runAsync();
	}

	private void movePaddle( final Long paddlePosition, final BlockingQueue<Long> in,
			final Long x ) {
		in.add( ( (long) Long.compare( x, paddlePosition ) ) );
	}

	private void enableInteractiveMode( final BlockingQueue<Long> in, final Future<?> future ) {
		final Scanner scanner = new Scanner( System.in );
		new Thread( () -> {
			while ( !future.isDone() ) {
				final String joystickInput = scanner.nextLine();
				final Long direction = switch ( joystickInput.toLowerCase() ) {
					case "a", "l" -> -1L;
					case "s", " " -> 0L;
					case "d", "r" -> 1L;
					default -> null;
				};
				if ( direction != null ) {
					in.add( direction );
				} else {
					System.out.println(
							"Only 'l/a', ' /s', 'r/d' are allowed. You wrote: " + joystickInput );
				}
			}
		} ).start();
	}

	private void printGame( final Map<Pair<Long, Long>, Character> grid, final Long score ) {
		final Character[][] matrix = new Character[ROWS][COLUMNS];
		for ( final Character[] row : matrix ) {
			Arrays.fill( row, EMPTY );
		}
		grid.keySet()
				.forEach( point -> matrix[point.getFirst().intValue()][point.getSecond()
						.intValue()] = grid.getOrDefault( point, EMPTY ) );
		printMatrix( matrix );
		System.out.println( "SCORE: " + score );
	}

	private void printResult( final Map<Pair<Long, Long>, Character> grid ) {
		if ( !grid.containsValue( BLOCK ) ) {
			System.out.println( "✅✅✅✅✅" );
			System.out.println( "YOU WON!!" );
			System.out.println( "✅✅✅✅✅" );
		} else {
			System.out.println( "❌❌❌❌❌" );
			System.out.println( "GAME OVER" );
			System.out.println( "❌❌❌❌❌" );
		}
	}

	public static void main( String[] args ) throws IOException {
		INTERACTIVE = true;
		new AoC132019().solveSecondPart( Files.lines(
				Path.of( "src", "test", "resources", "com.adventofcode.aoc2019",
						"AoC132019.txt" ) ) );
		Computer2019.shutdownAll();
	}
}
