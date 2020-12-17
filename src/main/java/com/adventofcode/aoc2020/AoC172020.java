package com.adventofcode.aoc2020;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC172020 implements Solution {
	private static final char ACTIVE = HASH;
	private static final char INACTIVE = DOT;
	private static final int CYCLES = 6;

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		var hypercube = getHypercube( input.collect( toList() ), !first );
		var prevHypercube = hypercube;
		final int cubesNumber = hypercube.length;
		final int cubeSize = hypercube[0].length;
		for ( int cycle = 0; cycle < CYCLES; cycle++ ) {
			prevHypercube = hypercube;
			hypercube = new char[cubesNumber][cubeSize][cubeSize][cubeSize];
			for ( int w = 0; w < cubesNumber; w++ ) {
				for ( int z = 0; z < cubeSize; z++ ) {
					for ( int y = 0; y < cubeSize; y++ ) {
						for ( int x = 0; x < cubeSize; x++ ) {
							hypercube[w][z][y][x] = nextCell( prevHypercube, w, z, y, x );
						}
					}
				}
			}
		}

		return itoa( Arrays.stream( hypercube )
				.flatMap( Arrays::stream )
				.flatMap( Arrays::stream )
				.flatMapToInt( row -> CharBuffer.wrap( row ).chars() )
				.filter( cell -> cell == ACTIVE )
				.count() );
	}

	private char nextCell( final char[][][][] hypercube, final int w, final int z, final int y,
			final int x ) {
		final char cell = hypercube[w][z][y][x];
		final int activeNeighbours = countActiveNeighbours( hypercube, w, z, y, x );
		if ( cell == ACTIVE && ( activeNeighbours < 2 || 3 < activeNeighbours ) ) {
			return INACTIVE;
		} else if ( cell != ACTIVE && activeNeighbours == 3 ) {
			return ACTIVE;
		} else {
			return cell;
		}
	}

	private int countActiveNeighbours( final char[][][][] hypercube, final int w, final int z,
			final int y, final int x ) {
		final int cubesNumber = hypercube.length;
		final int cubeSize = hypercube[0].length;
		int active = 0;
		for ( int l = -1; l <= 1; l++ ) {
			for ( int k = -1; k <= 1; k++ ) {
				for ( int j = -1; j <= 1; j++ ) {
					for ( int i = -1; i <= 1; i++ ) {
						if ( !( l == 0 && k == 0 && j == 0 && i == 0 ) && checkCell( w, l,
								cubesNumber ) && checkCell( z, k, cubeSize ) && checkCell( y, j,
								cubeSize ) && checkCell( x, i,
								cubeSize ) && hypercube[w + l][z + k][y + j][x + i] == ACTIVE ) {
							active++;
						}
					}
				}
			}
		}
		return active;
	}

	private char[][][][] getHypercube( final List<String> input, final boolean isHypercube ) {
		final int cubeSize = input.size() + 2 * CYCLES + 1;
		final int cubesNumber = isHypercube ? cubeSize : 1;
		final var hypercube = new char[cubesNumber][cubeSize][cubeSize][cubeSize];
		final int inputLevelPosition = CYCLES + input.size() / 2;
		final int inputCubePosition = isHypercube ? inputLevelPosition : 0;
		insertInputLevel( input, hypercube[inputCubePosition][inputLevelPosition] );
		return hypercube;
	}

	private void insertInputLevel( final List<String> input, final char[][] level ) {
		final int size = input.size();
		for ( int i = 0; i < size; i++ ) {
			final var inputRow = input.get( i ).toCharArray();
			final var destinationRow = level[i + CYCLES];
			System.arraycopy( inputRow, 0, destinationRow, CYCLES, size );
		}
	}

	private boolean checkCell( final int a, final int b, final int size ) {
		return 0 <= a + b && a + b < size;
	}

}
