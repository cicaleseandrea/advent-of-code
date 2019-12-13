package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnRegex;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Node;

class AoC062019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, new HashMap<>(), this::countOrbits );
	}

	public String solveSecondPart( final Stream<String> input ) {
		final Map<String, Node<String>> nodes = new HashMap<>();
		return solve( input, nodes, n -> countTransfers( nodes ) );
	}

	private String solve( final Stream<String> input, final Map<String, Node<String>> nodes,
			final Function<Node<String>, Long> computeResult ) {
		final Node<String> COM = new Node<>( "COM" );
		nodes.put( "COM", COM );

		input.forEach( line -> {
			final String[] connection = splitOnRegex( line, "\\)" ).toArray( String[]::new );
			getOrAddNode( nodes, connection[0] ).addChild( getOrAddNode( nodes, connection[1] ) );
		} );

		return itoa( computeResult.apply( COM ) );
	}

	private Node<String> getOrAddNode( final Map<String, Node<String>> nodes,
			final String nodeStr ) {
		if ( !nodes.containsKey( nodeStr ) ) {
			nodes.put( nodeStr, new Node<>( nodeStr ) );
		}
		return nodes.get( nodeStr );
	}

	private long countOrbits( final Node<String> node ) {
		return countOrbits( node, 0 );
	}

	private long countOrbits( final Node<String> node, final long count ) {
		long val = count;
		for ( final Node<String> n : node.getChildren() ) {
			val += countOrbits( n, count + 1 );
		}
		return val;
	}

	private long countTransfers( final Map<String, Node<String>> nodes ) {
		final Map<Node<String>, Long> youAncestors = new HashMap<>();
		Node<String> youAncestor = nodes.get( "YOU" );
		final Map<Node<String>, Long> sanAncestors = new HashMap<>();
		Node<String> sanAncestor = nodes.get( "SAN" );

		long currDistance = 0;
		Optional<Long> distance;
		do {
			currDistance++;
			youAncestor = addAncestor( youAncestors, youAncestor, currDistance );
			sanAncestor = addAncestor( sanAncestors, sanAncestor, currDistance );
			distance = getDistance( youAncestors, sanAncestor, sanAncestors, youAncestor );
		} while ( distance.isEmpty() );
		return distance.get() + currDistance - 2;
	}

	private Node<String> addAncestor( final Map<Node<String>, Long> ancestors,
			final Node<String> node, final long distance ) {
		final Node<String> ancestor = node.getParent().orElseThrow();
		ancestors.put( ancestor, distance );
		return ancestor;
	}

	private Optional<Long> getDistance( final Map<Node<String>, Long> ancestorsFirst,
			final Node<String> ancestorSecond, final Map<Node<String>, Long> ancestorsSecond,
			final Node<String> nodeFirst ) {
		final Long distance = ancestorsFirst.get( ancestorSecond );
		if ( distance != null ) {
			return Optional.of( distance );
		} else {
			return Optional.ofNullable( ancestorsSecond.get( nodeFirst ) );
		}
	}

}
