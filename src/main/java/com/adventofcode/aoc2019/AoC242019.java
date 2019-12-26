package com.adventofcode.aoc2019;

import static java.util.stream.Collectors.toUnmodifiableList;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.clearScreen;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC242019 implements Solution {

	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	//TODO
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private static String solve( final Stream<String> input, final boolean first ) {
		var curr = getInitialState( input );
		final Set<List<List<Character>>> seen = new HashSet<>();
		boolean cycle = false;
		//stop when you have a cycle
		while ( !cycle ) {
			final List<List<Character>> next = new ArrayList<>();
			for ( int i = 0; i < curr.size(); i++ ) {
				final ArrayList<Character> tmp = new ArrayList<>();
				for ( int j = 0; j < curr.get( 0 ).size(); j++ ) {
					//compute cell for next generation
					tmp.add( computeCell( curr, i, j ) );
				}
				//store next generation
				next.add( tmp );
			}
			curr = next;
			//check if we have a cycle
			cycle = !seen.add( curr );
			print( curr );
		}
		//in case of cycle compute the result
		return itoa( computeResult( curr ) );
	}

	private static void print( final List<List<Character>> grid ) {
		if ( PRINT ) {
			try {
				clearScreen();
				Thread.sleep( 100 );
				for ( final var row : grid ) {
					for ( final var cell : row ) {
						System.out.print( cell );
					}
					System.out.println();
				}
				System.out.println();
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}

	private static List<List<Character>> getInitialState( final Stream<String> input ) {
		return input.map(
				row -> row.chars().mapToObj( c -> (char) c ).collect( toUnmodifiableList() ) )
				.collect( toUnmodifiableList() );
	}

	private static long computeResult( final List<List<Character>> grid ) {
		long res = 0L;
		int i = 0;
		for ( final var row : grid ) {
			for ( final var cell : row ) {
				if ( cell == HASH ) {
					res += (long) Math.pow( 2, i );
				}
				i++;
			}
		}
		return res;
	}

	private static Character computeCell( final List<List<Character>> grid, final int i,
			final int j ) {
		final Character c = grid.get( i ).get( j );
		int countBugs = 0;
		for ( int y = Math.max( 0, i - 1 ); y <= Math.min( grid.size() - 1, i + 1 ); y++ ) {
			for ( int x = Math.max( 0, j - 1 ); x <= Math.min( grid.get( 0 ).size() - 1,
					j + 1 ); x++ ) {
				if ( y == i && j == x || y != i && j != x ) {
					continue; //skip this cell and diagonal cells
				}
				if ( grid.get( y ).get( x ) == HASH ) {
					countBugs++;
				}
			}
		}
		return switch ( c ) {
			case HASH -> countBugs == 1 ? HASH : DOT;
			case DOT -> countBugs == 1 || countBugs == 2 ? HASH : DOT;
			default -> c;
		};
	}

}
