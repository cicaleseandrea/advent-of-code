package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.NEIGHBOURS_8;
import static com.adventofcode.utils.Utils.getCharMatrix;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.listGetOrDefault;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC182015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, grid -> {}, false );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, this::cornerLightsOn, true );
	}

	private String solve( final Stream<String> input,
			final Consumer<List<List<Character>>> initialize, final boolean cornerLightsOn ) {
		var grid = getCharMatrix( input );
		initialize.accept( grid );
		for ( int i = 0; i < 100; i++ ) {
			grid = runOneStep( grid, cornerLightsOn );
		}
		return itoa( grid.stream().flatMap( Collection::stream ).filter( c -> c == HASH ).count() );
	}

	private List<List<Character>> runOneStep( final List<List<Character>> grid,
			final boolean cornerLightsOn ) {
		final List<List<Character>> nextGrid = new ArrayList<>();
		for ( int i = 0; i < grid.size(); i++ ) {
			final List<Character> row = new ArrayList<>();
			for ( int j = 0; j < grid.get( 0 ).size(); j++ ) {
				row.add( computeNext( grid, i, j, cornerLightsOn ) );
			}
			nextGrid.add( row );
		}
		return nextGrid;
	}

	private char computeNext( final List<List<Character>> grid, final int i, final int j,
			final boolean cornerLightsOn ) {
		final var neighboursOn = NEIGHBOURS_8.stream()
				.map( neighbourOffset -> listGetOrDefault(
						listGetOrDefault( grid, i + neighbourOffset.getFirst(), List.of() ),
						j + neighbourOffset.getSecond(), DOT ) )
				.filter( neighbour -> neighbour == HASH )
				.count();

		final char c;
		if ( cornerLightsOn && isCornerLight( grid, i, j ) ) {
			c = HASH;
		} else if ( neighboursOn == 3 ) {
			c = HASH;
		} else if ( neighboursOn == 2 ) {
			c = grid.get( i ).get( j );
		} else {
			c = DOT;
		}
		return c;
	}

	private boolean isCornerLight( final List<List<Character>> grid, final int i, final int j ) {
		final int rows = grid.size();
		final int columns = grid.get( 0 ).size();
		return i == 0 && j == 0 || i == 0 && j == columns - 1 || i == rows - 1 && j == 0 || i == rows - 1 && j == columns - 1;
	}

	private void cornerLightsOn( final List<List<Character>> grid ) {
		final var rows = grid.size();
		final var columns = grid.get( 0 ).size();
		grid.get( 0 ).set( 0, HASH );
		grid.get( 0 ).set( columns - 1, HASH );
		grid.get( rows - 1 ).set( 0, HASH );
		grid.get( rows - 1 ).set( columns - 1, HASH );
	}
}
