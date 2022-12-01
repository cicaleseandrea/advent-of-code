package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.clearScreen;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

class AoC242019 implements Solution {

	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true, AoC242019::computeResultFirst );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false, levels -> levels.stream()
				.flatMap( Arrays::stream )
				.flatMap( Arrays::stream )
				.filter( c -> c == HASH )
				.count() );
	}

	private static String solve( final Stream<String> input, final boolean first,
			final Function<List<Character[][]>, Long> computeResult ) {
		final var seen = new HashSet<>();
		var curr = getInitialState( input );
		List<Character[][]> levels = new ArrayList<>();
		levels.add( curr );
		boolean cycle = false;
		int iter = 0;
		final int maxIter = first ? Integer.MAX_VALUE : 200;
		//stop when you have a cycle or performed enough iterations
		while ( !( cycle || iter++ >= maxIter ) ) {
			levels = nextGeneration( levels, first );
			cycle = first && !seen.add( Arrays.stream( levels.get( 0 ) )
					.flatMap( Arrays::stream )
					.toList() );
			print( levels.get( 0 ), first );
		}

		return itoa( computeResult.apply( levels ) );
	}

	private static List<Character[][]> nextGeneration( final List<Character[][]> levels,
			final boolean first ) {
		final List<Character[][]> nextLevels = new ArrayList<>();
		for ( int l = first ? 0 : -1; l < levels.size() + ( first ? 0 : 1 ); l++ ) {
			final Character[][] curr = getOrCreateLevel( levels, l );
			final Character[][] out = getOrCreateLevel( levels, l - 1 );
			final Character[][] in = getOrCreateLevel( levels, l + 1 );
			final Character[][] next = new Character[curr.length][curr[0].length];
			for ( int i = 0; i < curr.length; i++ ) {
				for ( int j = 0; j < curr[0].length; j++ ) {
					//compute cell for next generation
					next[i][j] = computeNextCell( curr[i][j],
							adjacentBugs( curr, out, in, i, j, first ) );
				}
			}
			//store level for next generation
			nextLevels.add( next );
		}
		return nextLevels;
	}

	private static Character[][] getOrCreateLevel( final List<Character[][]> levels, final int i ) {
		if ( i >= 0 && i < levels.size() ) {
			return levels.get( i );
		} else {
			final var level = new Character[levels.get( 0 ).length][levels.get( 0 )[0].length];
			Arrays.stream( level ).forEach( row -> Arrays.fill( row, '.' ) );
			return level;
		}
	}

	private static int adjacentBugs( final Character[][] curr, final Character[][] out,
			final Character[][] in, final int i, final int j, final boolean first ) {
		int bugs = 0;
		if ( first ) {
			for ( int y = Math.max( 0, i - 1 ); y <= Math.min( curr.length - 1, i + 1 ); y++ ) {
				for ( int x = Math.max( 0, j - 1 ); x <= Math.min( curr[0].length - 1,
						j + 1 ); x++ ) {
					//skip this cell and diagonal cells
					if ( ( y != i || j != x ) && ( y == i || j == x ) && curr[y][x] == HASH ) {
						bugs++;
					}
				}
			}
		} else {
			if ( i == 2 && j == 2 ) {
				return 0; //this cell doesn't exist
			}

			//up
			int y = i - 1;
			int x = j;
			if ( y < 0 ) {
				bugs += countBugs( out, 2, 2, 1, 1 );
			} else if ( x == 2 && y == 2 ) {
				bugs += countBugs( in, 0, 4, 4, 4 );
			} else {
				bugs += countBugs( curr, x, x, y, y );
			}

			//down
			y = i + 1;
			x = j;
			if ( y > 4 ) {
				bugs += countBugs( out, 2, 2, 3, 3 );
			} else if ( x == 2 && y == 2 ) {
				bugs += countBugs( in, 0, 4, 0, 0 );
			} else {
				bugs += countBugs( curr, x, x, y, y );
			}

			//left
			y = i;
			x = j - 1;
			if ( x < 0 ) {
				bugs += countBugs( out, 1, 1, 2, 2 );
			} else if ( x == 2 && y == 2 ) {
				bugs += countBugs( in, 4, 4, 0, 4 );
			} else {
				bugs += countBugs( curr, x, x, y, y );
			}

			//right
			y = i;
			x = j + 1;
			if ( x > 4 ) {
				bugs += countBugs( out, 3, 3, 2, 2 );
			} else if ( x == 2 && y == 2 ) {
				bugs += countBugs( in, 0, 0, 0, 4 );
			} else {
				bugs += countBugs( curr, x, x, y, y );
			}
		}
		return bugs;
	}

	private static int countBugs( final Character[][] level, final int minX, final int maxX,
			final int minY, final int maxY ) {
		int bugs = 0;
		for ( int x = minX; x <= maxX; x++ ) {
			for ( int y = minY; y <= maxY; y++ ) {
				if ( level[y][x] == HASH ) {
					bugs++;
				}
			}
		}
		return bugs;
	}

	private static Character computeNextCell( final Character c, final int bugs ) {
		return switch ( c ) {
			case HASH -> bugs == 1 ? HASH : DOT;
			case DOT -> bugs == 1 || bugs == 2 ? HASH : DOT;
			default -> throw new IllegalArgumentException();
		};
	}

	private static Character[][] getInitialState( final Stream<String> input ) {
		return input.map( row -> row.chars().mapToObj( c -> (char) c ).toArray( Character[]::new ) )
				.toArray( Character[][]::new );
	}

	private static long computeResultFirst( final List<Character[][]> levels ) {
		long res = 0L;
		int i = 0;
		for ( final var level : levels ) {
			for ( final var row : level ) {
				for ( final var cell : row ) {
					if ( cell == HASH ) {
						res += (long) Math.pow( 2, i );
					}
					i++;
				}
			}
		}
		return res;
	}

	private static void print( final Character[][] grid, final boolean first ) {
		if ( first && PRINT ) {
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
}
