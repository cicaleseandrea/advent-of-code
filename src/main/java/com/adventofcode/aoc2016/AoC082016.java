package com.adventofcode.aoc2016;

import static java.util.stream.Collectors.joining;

import static com.adventofcode.utils.Utils.clearScreen;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.shouldPrint;

import java.util.Arrays;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC082016 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final int rows = 6;
		final int columns = 50;
		final Character[][] screen = initializeScreen( rows, columns );
		input.forEach( line -> {
			final var numbers = Utils.toLongList( line );
			final int one = numbers.get( 0 ).intValue();
			final int two = numbers.get( 1 ).intValue();
			if ( line.startsWith( "rect" ) ) {
				fill( screen, two, one );
			} else {
				final boolean rotateRow = line.startsWith( "rotate row" );
				rotate( rotateRow, rotateRow ? columns : rows, screen, one, two );
			}
			if ( shouldPrint() ) {
				printScreen( screen );
			}
		} );

		if ( first ) {
			return itoa( Arrays.stream( screen )
					.flatMap( Arrays::stream )
					.filter( c -> c == '⬛' )
					.count() );
		} else {
			return Arrays.stream( screen )
					.map( row -> Arrays.stream( row ).map( Object::toString ).collect( joining() ) )
					.collect( joining( "\n" ) );
		}
	}

	private void rotate( final boolean rotateRow, final int length, final Character[][] screen,
			final int index, final int shift ) {
		final var copy = new Character[length];
		for ( int i = 0; i < length; i++ ) {
			copy[i] = rotateRow ? screen[index][i] : screen[i][index];
		}
		for ( int i = 0; i < length; i++ ) {
			final Character value = copy[Utils.decrementMod( i, shift, length )];
			if ( rotateRow ) {
				screen[index][i] = value;
			} else {
				screen[i][index] = value;
			}
		}
	}

	private void fill( final Character[][] screen, final int rows, final int columns ) {
		for ( int i = 0; i < rows; i++ ) {
			for ( int j = 0; j < columns; j++ ) {
				screen[i][j] = '⬛';
			}
		}
	}

	private Character[][] initializeScreen( final int rows, final int columns ) {
		final Character[][] screen = new Character[rows][columns];
		for ( final var row : screen ) {
			Arrays.fill( row, '⬜' );
		}
		return screen;
	}

	private void printScreen( final Character[][] screen ) {
		clearScreen();
		Utils.printMatrix( screen );
		System.out.println();
		try {
			Thread.sleep( 50 );
		} catch ( InterruptedException e ) {
			Thread.currentThread().interrupt();
		}
	}

}
