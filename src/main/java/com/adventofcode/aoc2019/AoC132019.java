package com.adventofcode.aoc2019;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import static com.adventofcode.utils.Utils.AT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.TILDE;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.printMatrix;
import static com.adventofcode.utils.Utils.toLongList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
	private static final char EMPTY = SPACE;
	private static final char WALL = HASH;
	private static final char BLOCK = TILDE;
	private static final char PADDLE = '+';
	private static final char BALL = AT;
	private static final boolean DRAW = false;
	private static boolean INTERACTIVE = false;

	private static final Map<Long, Character> PANEL_OUT = Map.of( 0L, EMPTY, 1L, WALL, 2L, BLOCK,
			3L, PADDLE, 4L, BALL );

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

		if ( INTERACTIVE ) {
			enableInteractiveMode( in, future );
		}

		try {
			do {
				//out
				Long x;
				do {
					x = out.poll( 100, MILLISECONDS );
				} while ( x == null && !future.isDone() );
				if ( future.isDone() ) {
					//program halted
					break;
				}

				final Long y = out.take();
				final Long tile = out.take();
				Character symbol = null;

				if ( !first && x == -1 && y == 0 ) {
					score = tile;
				} else {
					//draw
					symbol = PANEL_OUT.get( tile );
					grid.put( new Pair<>( y, x ), symbol );
					if ( !INTERACTIVE ) {
						move( grid, in, x, symbol );
					}
				}

				if ( !first && ( DRAW || INTERACTIVE ) ) {
					printGame( grid, score );
					if ( Objects.equals( symbol, BALL ) ) {
						Thread.sleep( 400 );
					}
				}

			} while ( !future.isDone() );
		} catch ( Exception e ) {
			e.printStackTrace();
		}

		if ( first ) {
			return itoa( grid.values().stream().filter( c -> c == BLOCK ).count() );
		} else {
			if ( !grid.containsValue( BLOCK ) ) {
				System.err.println( "✅✅✅✅✅" );
				System.err.println( "YOU WON!!" );
				System.err.println( "✅✅✅✅✅" );
			}
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

	private void move( final Map<Pair<Long, Long>, Character> grid, final BlockingQueue<Long> in,
			final Long x, final Character symbol ) {
		if ( symbol == BALL ) {
			final Long paddlePos = grid.entrySet()
					.stream()
					.filter( e -> e.getValue() == PADDLE )
					.map( Map.Entry::getKey )
					.findFirst()
					.orElse( Pair.ZERO )
					.getSecond();
			in.add( ( (long) Long.compare( x, paddlePos ) ) );
		}
	}

	private void enableInteractiveMode( final BlockingQueue<Long> in, final Future<?> future ) {
		final Scanner scanner = new Scanner( System.in );
		new Thread( () -> {
			while ( !future.isDone() ) {
				final String command = scanner.nextLine();
				Long joystick = switch ( command.toLowerCase() ) {
					case "a", "l" -> -1L;
					case "s", " " -> 0L;
					case "d", "r" -> 1L;
					default -> null;
				};
				if ( joystick != null ) {
					in.add( joystick );
				} else {
					System.out.println(
							"Only 'l/a', ' /s', 'r/d' are allowed. You wrote: " + command );
				}
			}
			System.err.println( "❌❌❌❌❌" );
			System.err.println( "GAME OVER" );
			System.err.println( "❌❌❌❌❌" );
			Computer2019.shutdownAll();
		} ).start();
	}

	private void printGame( final Map<Pair<Long, Long>, Character> grid, final Long score ) {
		final Character[][] matrix = new Character[26][37];
		for ( final Pair<Long, Long> point : grid.keySet() ) {
			matrix[point.getFirst().intValue()][point.getSecond().intValue()] = grid.getOrDefault(
					point, EMPTY );
		}
		printMatrix( matrix );
		System.out.println( "SCORE: " + score );
	}

	public static void main( String[] args ) {
		INTERACTIVE = true;
		try {
			new AoC132019().solveSecondPart( Files.lines(
					Path.of( "src", "test", "resources", "com.adventofcode.aoc2019",
							"AoC132019.txt" ) ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}
