package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.EMPTY;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.toLongList;
import static java.util.Collections.emptyIterator;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.BitSet;
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

class AoC252019 implements Solution {

	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );
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
				if ( print() ) {
					System.out.println( "THE END" );
				}
				out.add( (long) ' ' );
			}
		} ).start();
	}

	private static List<String> getSteps() {
		//TODO this only works for my input...
		return Stream.of("east", "east", "east", "take shell", "west", "south",
				"take monolith", "north", "west", "north", "north", "take planetoid",
				"east", "take cake", "north", "south", "south", "west", "west",
				"east", "north", "take astrolabe", "west", "east", "south", "east",
				"north", "west", "west", "take ornament", "west", "east", "south",
				"south", "take fuel cell", "north", "north", "east", "south", "west",
				"take bowl of rice", "east", "north", "east", "south", "west",
				"north", "west").toList();
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
					sendInstruction( in, instruction );
				}
			}
		} ).start();
	}

	private void sendInstruction( final BlockingQueue<Long> in, final String instruction ) {
		instruction.chars().boxed().forEach( c -> in.add( c.longValue() ) );
		in.add((long) '\n');
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
				if ( print() ) {
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

	private boolean collectItem( final boolean collect, final String lineStr,
			final Inventory inventory, final Iterator<String> steps ) {
		if ( collect && lineStr.startsWith( "-" ) ) {
			final String item = lineStr.replaceFirst( "- ", "" );
			if ( !BLACKLIST.contains( item ) ) {
				inventory.add( item );
			}
			return true;
		} else {
			return lineStr.equals( "Items here:" ) && steps.hasNext();
		}
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
		if ( print() ) {
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
				private final BitSet ITEMS = new BitSet( ALL_ITEMS.size() );

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
					} else {
						move = !move;
						if ( move ) {
							return "north";
						} else {
							i++;
						}
					}

					//Using Gray Code to cycle through states by only dropping or taking one item at a time
					//https://en.wikipedia.org/wiki/Gray_code#Constructing_an_n-bit_Gray_code
					//Using https://oeis.org/A007814 (http://mathworld.wolfram.com/BinaryCarrySequence.html) to find which bit to flip next
					final int position = Integer.numberOfTrailingZeros( i );
					final String item = ALL_ITEMS.get( position );
					ITEMS.flip( position );
					final String instruction;
					if ( ITEMS.get( position ) ) {
						instruction = itemInstruction( "take", item );
					} else {
						instruction = itemInstruction( "drop", item );
					}
					return instruction;
				}

				private String itemInstruction( final String action, final String item ) {
					return action + " " + item;
				}
			};
		}
	}

	public static boolean print() {return PRINT || INTERACTIVE;}

	public static void main( String[] args ) throws IOException {
		INTERACTIVE = true;
		SOLVE = false;
		new AoC252019().solveFirstPart( Files.lines(
				Path.of( "src", "test", "resources", "com.adventofcode.aoc2019",
						"AoC252019.txt" ) ) );
		Computer2019.shutdownAll();
	}
}
