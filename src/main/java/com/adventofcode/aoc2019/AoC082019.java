package com.adventofcode.aoc2019;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingByConcurrent;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC082019 implements Solution {
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	public String solve( final Stream<String> input, final boolean first ) {
		final String str = getFirstString( input );
		final int rows = rowsNumber( str );
		final int columns = columnsNumber( str );
		final AtomicInteger k = new AtomicInteger();
		long min = Long.MAX_VALUE;
		long res = 0;
		final ArrayList<List<List<Character>>> image = new ArrayList<>();
		while ( k.get() < str.length() ) {
			final var layer = createLayer( str, rows, columns, k );
			image.add( layer );
			final var digits = layer.parallelStream()
					.flatMap( Collection::stream )
					.collect( groupingByConcurrent( identity(), counting() ) );
			long zeroes = digits.getOrDefault( '0', 0L );
			if ( zeroes < min ) {
				min = zeroes;
				res = digits.getOrDefault( '1', 0L ) * digits.getOrDefault( '2', 0L );
			}
		}

		if ( first ) {
			return itoa( res );
		}
		final StringBuilder result = new StringBuilder();
		for ( int i = 0; i < rows; i++ ) {
			for ( int j = 0; j < columns; j++ ) {
				result.append( findPixel( image, i, j ) );
			}
			result.append( "\n" );
		}
		return result.toString();
	}

	private Character findPixel( final ArrayList<List<List<Character>>> image, final int i,
			final int j ) {
		for ( final var layer : image ) {
			final Character pixel = layer.get( i ).get( j );
			if ( pixel != '2' ) {
				return pixel == '1' ? HASH : SPACE;
			}
		}
		throw new IllegalArgumentException();
	}

	private List<List<Character>> createLayer( final String str, final int rows, final int columns,
			final AtomicInteger k ) {
		final List<List<Character>> matrix = new ArrayList<>();
		for ( int i = 0; i < rows; i++ ) {
			final List<Character> row = new ArrayList<>();
			for ( int j = 0; j < columns; j++ ) {
				final char digit = str.charAt( k.getAndIncrement() );
				row.add( digit );
			}
			matrix.add( row );
		}
		return matrix;
	}

	private int rowsNumber( final String str ) {
		return str.length() < 20 ? 2 : 6;
	}

	private int columnsNumber( final String str ) {
		return str.length() < 20 ? 2 : 25;
	}

}
