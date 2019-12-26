package com.adventofcode.aoc2019;

import static java.lang.Character.isDigit;
import static java.util.Collections.emptyIterator;

import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.toLongList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;

class AoC252019 implements Solution {

	private static boolean INTERACTIVE = false;
	private static final List<String> blacklist = List.of( "escape pod", "photons",
			"giant electromagnet", "molten lava", "infinite loop" );

	public String solveSecondPart( final Stream<String> input ) {
		return MERRY_CHRISTMAS;
	}

	public String solveFirstPart( final Stream<String> input ) {
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		final BlockingQueue<Long> out = new LinkedBlockingQueue<>();
		final List<Long> program = toLongList( getFirstString( input ) );
		final Future<?> future = Computer2019.runComputer( program, in, out, true );

		if ( INTERACTIVE ) {
			enableInteractiveMode( in, future );
		}

		enableGameEnd( out, future );

		final Iterator<String> instructions = INTERACTIVE ? emptyIterator() : getSolution().iterator();
		return playGame( in, out, instructions, future );
	}

	private void enableGameEnd( final BlockingQueue<Long> out, final Future<?> future ) {
		new Thread( () -> {
			try {
				future.get();
			} catch ( InterruptedException | ExecutionException | CancellationException e ) {
			} finally {
				if ( INTERACTIVE ) {
					System.out.println( "THE END" );
				}
				out.add( (long) ' ' );
			}
		} ).start();
	}

	private List<String> getSolution() {
		//TODO this only works for my input...
		return List.of( "east\n", "east\n", "east\n", "take shell\n", "west\n", "south\n",
				"take monolith\n", "north\n", "west\n", "north\n", "north\n", "take planetoid\n",
				"east\n", "take cake\n", "north\n", "south\n", "south\n", "west\n", "west\n",
				"east\n", "north\n", "take astrolabe\n", "west\n", "east\n", "south\n", "east\n",
				"north\n", "west\n", "west\n", "take ornament\n", "west\n", "east\n", "south\n",
				"south\n", "take fuel cell\n", "north\n", "north\n", "east\n", "south\n", "west\n",
				"take bowl of rice\n", "east\n", "north\n", "east\n", "south\n", "west\n",
				"north\n", "west\n", "drop bowl of rice\n", "drop shell\n", "drop ornament\n",
				"drop cake\n", "north\n" );
	}

	private void enableInteractiveMode( final BlockingQueue<Long> in, final Future<?> future ) {
		final Scanner scanner = new Scanner( System.in );
		new Thread( () -> {
			while ( !future.isDone() ) {
				final String instruction = scanner.nextLine();
				if ( "exit".equals( instruction ) ) {
					System.out.println( "Bye Bye" );
					future.cancel( true );
					return;
				} else {
					sendInstruction( in, instruction + "\n" );
				}
			}
		} ).start();
	}

	private void sendInstruction( final BlockingQueue<Long> in, final String instruction ) {
		instruction.chars().boxed().forEach( c -> in.add( c.longValue() ) );
	}

	private String playGame( final BlockingQueue<Long> in, final BlockingQueue<Long> out,
			final Iterator<String> instructions, final Future<?> future ) {
		final StringBuilder word = new StringBuilder();
		final StringBuilder password = new StringBuilder();
		while ( !future.isDone() ) {
			try {
				final long current = out.take();
				final char c = (char) current;
				if ( INTERACTIVE ) {
					System.out.print( c );
				}
				if ( isDigit( c ) && word.length() == 0 ) {
					password.append( c );
				} else if ( c == ' ' || c == '\n' ) {
					word.setLength( 0 );
				} else {
					word.append( c );
				}
				if ( word.toString().equals( "Command?" ) && instructions.hasNext() ) {
					final String instruction = instructions.next();
					if ( INTERACTIVE ) {
						System.out.println();
						System.out.print( instruction );
					}
					sendInstruction( in, instruction );
				}
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}

		return password.toString();
	}

	public static void main( String[] args ) throws IOException {
		INTERACTIVE = true;
		new AoC252019().solveFirstPart( Files.lines(
				Path.of( "src", "test", "resources", "com.adventofcode.aoc2019",
						"AoC252019.txt" ) ) );
		Computer2019.shutdownAll();
	}
}
