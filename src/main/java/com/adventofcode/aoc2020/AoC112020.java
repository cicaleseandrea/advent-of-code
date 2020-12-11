package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.clearScreen;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;

class AoC112020 implements Solution {

	private static final char EMPTY_SEAT = 'L';
	private static final char FLOOR = DOT;
	private static final char OCCUPIED = HASH;

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 4, seat -> false );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 5, seat -> seat == FLOOR );
	}

	private static String solve( final Stream<String> input, final int maxSeats,
			final Predicate<Character> keepChecking ) {
		List<List<Character>> grid = Utils.getCharMatrix( input );
		List<List<Character>> nextGrid = grid;
		final int rows = grid.size();
		final int columns = grid.get( 0 ).size();
		do {
			grid = nextGrid;
			print( grid );

			nextGrid = new ArrayList<>();
			for ( int i = 0; i < rows; i++ ) {
				final var nextRow = new ArrayList<Character>();
				for ( int j = 0; j < columns; j++ ) {
					nextRow.add( nextSeat( grid, i, j, maxSeats, keepChecking ) );
				}
				nextGrid.add( nextRow );
			}
		} while ( !grid.equals( nextGrid ) );

		return itoa( grid.stream()
				.flatMap( Collection::stream )
				.filter( seat -> seat == OCCUPIED )
				.count() );
	}

	private static char nextSeat( final List<List<Character>> grid, final int i, final int j,
			final int maxSeats, final Predicate<Character> keepChecking ) {
		final char curr = grid.get( i ).get( j );
		if ( curr == EMPTY_SEAT && countOccupiedSeats( grid, i, j, keepChecking ) == 0 ) {
			return OCCUPIED;
		} else if ( curr == OCCUPIED && countOccupiedSeats( grid, i, j,
				keepChecking ) >= maxSeats ) {
			return EMPTY_SEAT;
		} else {
			return curr;
		}
	}

	private static int countOccupiedSeats( final List<List<Character>> grid, final int i,
			final int j, final Predicate<Character> keepChecking ) {
		int occupied = 0;
		final var directions = List.of( new Pair<>( -1, -1 ), new Pair<>( -1, 0 ),
				new Pair<>( -1, 1 ), new Pair<>( 0, -1 ), new Pair<>( 0, 1 ), new Pair<>( 1, -1 ),
				new Pair<>( 1, 0 ), new Pair<>( 1, 1 ) );
		for ( final var direction : directions ) {
			int k = i;
			int l = j;
			char seat;
			do {
				k += direction.getFirst();
				l += direction.getSecond();
				seat = getCell( grid, k, l );
				if ( seat == OCCUPIED ) {
					occupied++;
				}
			} while ( keepChecking.test( seat ) );
		}
		return occupied;
	}

	public static char getCell( final List<List<Character>> grid, final int i, final int j ) {
		return ( 0 <= i && i < grid.size() && 0 <= j && j < grid.get( 0 ).size() ) ? grid.get( i )
				.get( j ) : SPACE;
	}

	private static void print( final List<List<Character>> grid ) {
		if ( Utils.shouldPrint() ) {
			clearScreen();
			Utils.printMatrix( grid );
			System.out.println();
			try {
				Thread.sleep( 200 );
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}
}
