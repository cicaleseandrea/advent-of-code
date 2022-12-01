package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.matrixToLongStream;
import static com.adventofcode.utils.Utils.toLongList;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

class AoC192019 implements Solution {

	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );

	public String solveFirstPart( final Stream<String> input ) {
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		final List<Long> program = toLongList( getFirstString( input ) );
		final int size = 50;
		final long[][] matrix = new long[size][size];
		for ( int y = 0; y < size; y++ ) {
			for ( int x = 0; x < size; x++ ) {
				matrix[y][x] = getNumber( program, in, out, x, y );
			}
		}
		if ( PRINT ) {
			printMatrix( matrix );
		}

		return itoa( matrixToLongStream( matrix ).filter( e -> e == 1L ).count() );
	}

	public String solveSecondPart( final Stream<String> input ) {
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		final List<Long> program = toLongList( getFirstString( input ) );
		final int size = 100;
		int y = size;
		int x = 1;
		while ( true ) {
			//find the left-most part of the beam on this row
			long number;
			do {
				number = getNumber( program, in, out, x, y );
				if ( number == 0 ) {
					/* move to the right only if number is 0. if 1, will retry the same x on the next row */
					x++;
				}
			} while ( number == 0 );

			//if top right corner of the square is inside the beam
			if ( getNumber( program, in, out, x + ( size - 1 ), y - ( size - 1 ) ) == 1L ) {
				//find the top left corner of the square
				y -= ( size - 1 );
				return itoa( x * 10000L + y );
			}

			//move to next row
			y++;
		}
	}

	private void printMatrix( final long[][] matrix ) {
		for ( final var row : matrix ) {
			for ( final var e : row ) {
				System.out.print( e );
			}
			System.out.println();
		}
	}

	private long getNumber( final List<Long> program, final BlockingQueue<Long> in,
			final BlockingDeque<Long> out, final int x, final int y ) {
		in.add( (long) x );
		in.add( (long) y );
		try {
			Computer2019.runComputer( program, in, out, false );
			return out.take();
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}

		throw new IllegalArgumentException();
	}
}
