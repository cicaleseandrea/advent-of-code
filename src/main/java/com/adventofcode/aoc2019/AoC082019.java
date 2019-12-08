package com.adventofcode.aoc2019;

import static java.util.Comparator.comparingLong;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingByConcurrent;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.getFirstString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;

class AoC082019 implements Solution {

	private static final Map<Character, Character> PIXEL_VALUES = Map.of( '0', SPACE, '1', HASH );

	public String solveFirstPart( final Stream<String> input ) {
		return solve( getFirstString( input ), this::computeResultFirst );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( getFirstString( input ), this::computeResultSecond );
	}

	public String solve( final String input,
			final Function<List<List<List<Character>>>, String> computeResult ) {
		final int rows = input.length() < 20 ? 2 : 6;
		final int columns = input.length() < 20 ? 2 : 25;
		final List<List<List<Character>>> image = new ArrayList<>();
		final Iterator<Character> inputChars = input.chars().mapToObj( c -> (char) c ).iterator();
		while ( inputChars.hasNext() ) {
			image.add( createLayer( inputChars, rows, columns ) );
		}
		return computeResult.apply( image );
	}

	private String computeResultFirst( final List<List<List<Character>>> image ) {
		return image.stream()
				.map( this::computeLayerValue )
				.min( comparingLong( Pair::getFirst ) )
				.map( Pair::getSecond )
				.map( Utils::itoa )
				.orElseThrow();
	}

	private Pair<Long, Long> computeLayerValue( final List<List<Character>> layer ) {
		final var frequencies = layer.parallelStream()
				.flatMap( Collection::stream )
				.collect( groupingByConcurrent( identity(), counting() ) );
		final long zeroes = frequencies.getOrDefault( '0', 0L );
		final long value = frequencies.getOrDefault( '1', 0L ) * frequencies.getOrDefault( '2',
				0L );
		return new Pair<>( zeroes, value );
	}

	private String computeResultSecond( final List<List<List<Character>>> image ) {
		final StringBuilder result = new StringBuilder();
		for ( int i = 0; i < image.get( 0 ).size(); i++ ) {
			for ( int j = 0; j < image.get( 0 ).get( 0 ).size(); j++ ) {
				result.append( findPixel( image, i, j ) );
			}
			result.append( "\n" );
		}
		return result.toString();
	}

	private Character findPixel( final List<List<List<Character>>> image, final int i,
			final int j ) {
		return image.stream()
				.map( layer -> layer.get( i ).get( j ) )
				.filter( PIXEL_VALUES::containsKey )
				.findFirst()
				.map( PIXEL_VALUES::get )
				.orElseThrow();
	}

	private List<List<Character>> createLayer( final Iterator<Character> input, final int rows,
			final int columns ) {
		final List<List<Character>> matrix = new ArrayList<>();
		for ( int i = 0; i < rows; i++ ) {
			final List<Character> row = new ArrayList<>();
			for ( int j = 0; j < columns; j++ ) {
				row.add( input.next() );
			}
			matrix.add( row );
		}
		return matrix;
	}
}
