package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnRegex;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Node;

class AoC062019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	public String solve( final Stream<String> input, final boolean first ) {
		final Map<String, Node<String>> nodes = new HashMap<>();
		final Node<String> COM = new Node<>( "COM" );
		nodes.put( "COM", COM );

		input.forEach( line -> {
			final String[] connection = splitOnRegex( line, "\\)" ).toArray( String[]::new );
			getOrAddNode( nodes, connection[0] ).addChild( getOrAddNode( nodes, connection[1] ) );
		} );

		if ( first ) {
			return itoa( countOrbits( COM, 0 ) );
		} else {
			return itoa( countTransfers( nodes ) );
		}
	}

	private Node<String> getOrAddNode( final Map<String, Node<String>> nodes,
			final String nodeStr ) {
		final Node<String> node;
		if ( nodes.containsKey( nodeStr ) ) {
			node = nodes.get( nodeStr );
		} else {
			node = new Node<>( nodeStr );
			nodes.put( nodeStr, node );
		}
		return node;
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

		long count = 0;
		Optional<Long> res;
		do {
			count++;
			youAncestor = addAncestor( youAncestors, youAncestor, count );
			sanAncestor = addAncestor( sanAncestors, sanAncestor, count );
			res = countAncestors( youAncestors, sanAncestor, sanAncestors, youAncestor );
		} while ( res.isEmpty() );
		return res.get() + count - 2;
	}

	private Node<String> addAncestor( final Map<Node<String>, Long> ancestors,
			final Node<String> node, final long count ) {
		final Node<String> ancestor = node.getParent().orElseThrow();
		ancestors.put( ancestor, count );
		return ancestor;
	}

	private Optional<Long> countAncestors( final Map<Node<String>, Long> nodesFirst,
			final Node<String> nodeSecond, final Map<Node<String>, Long> nodesSecond,
			final Node<String> nodeFirst ) {
		final Long count = nodesFirst.get( nodeSecond );
		if ( count != null ) {
			return Optional.of( count );
		} else {
			return Optional.ofNullable( nodesSecond.get( nodeFirst ) );
		}
	}

}
