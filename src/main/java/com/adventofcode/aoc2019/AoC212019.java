package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;

class AoC212019 implements Solution {
	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );

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

		initializeCommands( in, first );

		//move robot and wait for it to stop
		Computer2019.runComputer( program, in, out, false );

		printVideoFeed( out );

		return itoa( out.removeLast() );
	}

	private void printVideoFeed( final BlockingDeque<Long> out ) {
		if ( PRINT ) {
			final Iterator<Long> iterator = out.iterator();
			while ( iterator.hasNext() ) {
				final long current = iterator.next();
				if ( iterator.hasNext() ) {
					System.out.print( (char) current );
				} else {
					System.out.print( "Damage: " + current );
				}
			}
		}
	}

	private void initializeCommands( final BlockingQueue<Long> in, final boolean first ) {
		final String commands;
		if ( first ) {
			//jump if:
			// (NOT ground in A OR NOT ground in C) //jump as soon as possible (C) or if needed (A)
			// AND ground in D //jump if you land on ground (D)
			//@formatter:off
			commands =
				"NOT A J\n" +
				"NOT C T\n" +
				"OR T J\n" +
				"AND D J\n" +
				"WALK\n";
			//@formatter:on
		} else {
			//jump if:
			// (NOT ground in A OR NOT ground in B OR NOT ground in C) //jump as soon as possible (A/B/C)
			// AND ground in D //jump if you land on ground (D)
			// AND (ground in E OR ground in H) //jump if you land on a tile from which you can walk (E) or jump (H)
			//@formatter:off
			commands =
				"NOT A J\n" +
				"NOT B T\n" +
				"OR T J\n" +
				"NOT C T\n" +
				"OR T J\n" +
				"AND D J\n" +
				"NOT D T\n" + //false
				"OR E T\n" +
				"OR H T\n" +
				"AND T J\n" +
				"RUN\n";
			//@formatter:on
		}
		//#####..#.########
		//#####.#..########
		//#####.##.##..####

		//#####.#.#..######

		commands.chars().boxed().forEach( c -> in.add( c.longValue() ) );
	}

}
