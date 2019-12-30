package com.adventofcode.aoc2019;

import static java.util.Collections.emptyIterator;
import static java.util.stream.Collectors.toCollection;

import static com.adventofcode.utils.Utils.EMPTY;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.toLongList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
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
	private static boolean SOLVE = true;
	private static final List<String> BLACKLIST = List.of( "escape pod", "photons",
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

		final List<String> steps = SOLVE ? getSteps() : new ArrayList<>();
		return playGame( in, out, steps.iterator(), future );
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

	private static List<String> getSteps() {
		//TODO this only works for my input...
		return Stream.of( "east\n", "east\n", "east\n", "take shell\n", "west\n", "south\n",
				"take monolith\n", "north\n", "west\n", "north\n", "north\n", "take planetoid\n",
				"east\n", "take cake\n", "north\n", "south\n", "south\n", "west\n", "west\n",
				"east\n", "north\n", "take astrolabe\n", "west\n", "east\n", "south\n", "east\n",
				"north\n", "west\n", "west\n", "take ornament\n", "west\n", "east\n", "south\n",
				"south\n", "take fuel cell\n", "north\n", "north\n", "east\n", "south\n", "west\n",
				"take bowl of rice\n", "east\n", "north\n", "east\n", "south\n", "west\n",
				"north\n", "west\n" ).collect( toCollection( ArrayList::new ) );
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
			final Iterator<String> steps, final Future<?> future ) {
		final StringBuilder line = new StringBuilder();
		final Inventory inventory = new Inventory();
		Iterator<String> items = emptyIterator();
		String password = EMPTY;
		boolean securityCheckpoint = false;
		boolean collect = false;
		while ( !future.isDone() || !out.isEmpty() ) {
			try {
				final char c = (char) out.take().longValue();
				if ( INTERACTIVE ) {
					System.out.print( c );
				}

				final String lineStr = line.toString();
				if ( c == '\n' ) {
					collect = collectItem( collect, lineStr, inventory, steps );
					final String tmp = getPassword( lineStr );
					if ( !tmp.equals( EMPTY ) ) {
						password = tmp;
					}
					line.setLength( 0 );
				} else {
					line.append( c );
				}

				if ( !securityCheckpoint && lineStr.endsWith( "identity." ) ) {
					securityCheckpoint = true;
					items = inventory.iterator();
				}

				if ( SOLVE && lineStr.endsWith(
						"Command?" ) && ( steps.hasNext() || items.hasNext() ) ) {
					sendAutomaticInstruction( in, steps, items );
				}

			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}

		return password;
	}

	private boolean collectItem( boolean collect, final String lineStr, final Inventory inventory,
			final Iterator<String> steps ) {
		if ( collect && lineStr.startsWith( "-" ) ) {
			final String item = lineStr.replaceFirst( "- ", "" );
			if ( !BLACKLIST.contains( item ) ) {
				inventory.add( item );
			}
		} else {
			collect = lineStr.equals( "Items here:" ) && steps.hasNext();
		}
		return collect;
	}

	private String getPassword( final String lineStr ) {
		String password = EMPTY;
		final List<Long> numbers = toLongList( lineStr );
		if ( !numbers.isEmpty() ) {
			password = numbers.get( 0 ).toString();
		}
		return password;
	}

	private void sendAutomaticInstruction( final BlockingQueue<Long> in,
			final Iterator<String> steps, final Iterator<String> inventory ) {
		final String instruction;
		if ( steps.hasNext() ) {
			instruction = steps.next();
		} else {
			instruction = inventory.next();
		}
		if ( INTERACTIVE ) {
			System.out.println();
			System.out.print( instruction );
		}
		sendInstruction( in, instruction );
	}

	private static class Inventory implements Iterable<String> {

		private final List<String> ALL_ITEMS = new ArrayList<>();

		private void add( final String item ) {
			ALL_ITEMS.add( item );
		}

		@Override
		public Iterator<String> iterator() {
			return new Iterator<>() {
				private int i = -1;
				private boolean move = true;
				private boolean initialize = true;

				@Override
				public boolean hasNext() {
					return i < Math.pow( 2, ALL_ITEMS.size() );
				}

				@Override
				public String next() {
					if ( !hasNext() ) {
						throw new NoSuchElementException();
					} else if ( initialize ) {
						i++;
						if ( i < ALL_ITEMS.size() ) {
							return itemInstruction( "drop", ALL_ITEMS.get( i ) );
						} else {
							initialize = false;
							i = 1;
						}
					} else if ( move = !move ) {
						return "north\n";
					} else {
						i++;
					}

					//Using Gray Code to cycle through states by only dropping or taking one item at a time
					//https://en.wikipedia.org/wiki/Gray_code#Constructing_an_n-bit_Gray_code
					//Using https://oeis.org/A007814 (http://mathworld.wolfram.com/BinaryCarrySequence.html) to find which bit to flip next
					final int position = Integer.numberOfTrailingZeros( i );
					final String item = ALL_ITEMS.get( position );
					final int gray = i ^ ( i >> 1 );
					final int bitFlipped = 1 << position;
					final boolean drop = ( gray & bitFlipped ) == 0;
					final String instruction;
					if ( drop ) {
						instruction = itemInstruction( "drop", item );
					} else {
						instruction = itemInstruction( "take", item );
					}
					return instruction;
				}

				private String itemInstruction( final String action, final String item ) {
					return action + " " + item + "\n";
				}
			};
		}
	}

	public static void main( String[] args ) throws IOException {
		INTERACTIVE = true;
		new AoC252019().solveFirstPart( Files.lines(
				Path.of( "src", "test", "resources", "com.adventofcode.aoc2019",
						"AoC252019.txt" ) ) );
		Computer2019.shutdownAll();
	}
}
