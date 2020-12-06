package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;
import static com.google.common.collect.Collections2.permutations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntBinaryOperator;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC092015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, Math::min );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, Math::max );
	}

	private static String solve( final Stream<String> input,
			final IntBinaryOperator chooseDistance ) {
		final var edges = getEdges( input );
		final var paths = permutations( edges.keySet() );

		final int distance = paths.stream()
				.mapToInt( path -> computeDistance( path, edges ) )
				.reduce( chooseDistance )
				.orElseThrow();
		return itoa( distance );
	}

	private static int computeDistance( final List<String> path,
			final Map<String, Map<String, Integer>> edges ) {
		int pathDistance = 0;
		for ( int i = 0; i < path.size() - 1; i++ ) {
			final String v = path.get( i );
			final String w = path.get( i + 1 );
			final int distance = edges.get( v ).get( w );
			pathDistance += distance;
		}
		return pathDistance;
	}

	private static Map<String, Map<String, Integer>> getEdges( final Stream<String> input ) {
		final var edges = new HashMap<String, Map<String, Integer>>();
		input.map( Utils::splitOnTabOrSpace ).forEach( tokens -> {
			final String v = tokens.get( 0 );
			final String w = tokens.get( 2 );
			final int distance = atoi( tokens.get( 4 ) );
			edges.computeIfAbsent( v, k -> new HashMap<>() ).put( w, distance );
			edges.computeIfAbsent( w, k -> new HashMap<>() ).put( v, distance );
		} );
		return edges;
	}

}
