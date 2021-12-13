package com.adventofcode.aoc2021;

import static java.lang.Character.isLowerCase;

import static com.adventofcode.utils.Utils.incrementMapElement;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;

@SuppressWarnings("UnstableApiUsage")
class AoC122021 implements Solution {

	private static final String START = "start";
	private static final String END = "end";

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 1 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 2 );
	}

	private String solve( final Stream<String> input, final int maxSmallCavesVisits ) {
		final MutableGraph<String> caves = GraphBuilder.undirected().build();
		input.map( line -> line.split( "-" ) ).forEach( edge -> caves.putEdge( edge[0], edge[1] ) );

		return itoa( findPaths( START, new Path(), caves, maxSmallCavesVisits ).size() );
	}

	private List<Path> findPaths( final String currentCave, final Path pathSoFar,
			final Graph<String> caves, final int maxSmallCavesVisits ) {
		final var updatedPath = new Path( pathSoFar ).addNode( currentCave );

		if ( currentCave.equals( END ) ) {
			return List.of( updatedPath );
		}

		return caves.adjacentNodes( currentCave )
				.stream()
				.filter( adjacentCave -> !adjacentCave.equals( START ) )
				.filter( adjacentCave -> !isSmallCave( adjacentCave ) || !updatedPath.contains(
						adjacentCave ) || moreSmallCavesAllowed( updatedPath,
						maxSmallCavesVisits ) )
				.flatMap( adjacentCave -> findPaths( adjacentCave, updatedPath, caves,
						maxSmallCavesVisits ).stream() )
				.toList();
	}

	private boolean moreSmallCavesAllowed( final Path currentPath, final int maxSmallCavesVisits ) {
		return currentPath.nodes.entrySet()
				.stream()
				.noneMatch( entry -> isSmallCave(
						entry.getKey() ) && entry.getValue() >= maxSmallCavesVisits );
	}

	private boolean isSmallCave( final String cave ) {
		return isLowerCase( cave.charAt( 0 ) );
	}

	private record Path(Map<String, Long> nodes) {
		public Path() {
			this( new HashMap<>() );
		}

		public Path( final Path path ) {
			this( new HashMap<>( path.nodes ) );
		}

		public Path addNode( final String node ) {
			incrementMapElement( nodes, node );
			return this;
		}

		public boolean contains( final String cave ) {
			return nodes.containsKey( cave );
		}
	}
}
