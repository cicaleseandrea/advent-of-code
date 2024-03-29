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

		final String commands = initializeCommands( in, first );

		//move robot and wait for it to stop
		Computer2019.runComputer( program, in, out, false );

		printVideoFeed( out, commands );

		return itoa( out.removeLast() );
	}

	private void printVideoFeed( final BlockingQueue<Long> out, final String commands ) {
		if ( PRINT ) {
			final Iterator<Long> iterator = out.iterator();
			while ( iterator.hasNext() ) {
				final long current = iterator.next();
				if ( iterator.hasNext() ) {
					System.out.print( (char) current );
				} else {
					System.out.print( "Damage: " + current );
				}
				if ( current == ':' ) {
					System.out.println();
					System.out.print( commands );
				}
			}
		}
	}

	private String initializeCommands( final BlockingQueue<Long> in, final boolean first ) {
		final String commands;
		if ( first ) {
			//jump if:
			// (NOT ground in A OR NOT ground in C) //jump as soon as possible (C) or if needed (A)
			// AND ground in D //jump if you land on ground (D)
			//@formatter:off
			//!(A && C) = (!A || !C)
			commands =
			"""
			OR A J
			AND C J
			NOT J J
			AND D J
			WALK
			""";
			//@formatter:on
		} else {
			//jump if:
			// (NOT ground in A OR NOT ground in B OR NOT ground in C) //jump as soon as possible (A/B/C)
			// AND ground in D //jump if you land on ground (D)
			// AND (ground in E OR ground in H) //jump if you land on a tile from which you can walk (E) or jump (H)
			//@formatter:off
			//!(A && B && C) = (!A || !B || !C)
			commands =
			"""
			OR A J
			AND B J
			AND C J
			NOT J J
			AND D J
			OR E T
			OR H T
			AND T J
			RUN
			""";
			//@formatter:on
		}
		commands.chars().boxed().forEach( c -> in.add( c.longValue() ) );

		return commands;
	}

}
