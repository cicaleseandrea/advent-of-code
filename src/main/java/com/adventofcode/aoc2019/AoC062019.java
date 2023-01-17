package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnRegex;

import com.adventofcode.Solution;
import com.adventofcode.utils.TreeNode;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

class AoC062019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, new HashMap<>(), this::countOrbits );
	}

	public String solveSecondPart( final Stream<String> input ) {
		final Map<String, TreeNode<String>> nodes = new HashMap<>();
		return solve( input, nodes, n -> countTransfers( nodes ) );
	}

	private String solve( final Stream<String> input, final Map<String, TreeNode<String>> nodes,
			final ToLongFunction<TreeNode<String>> computeResult ) {
		final TreeNode<String> COM = new TreeNode<>( "COM" );
		nodes.put( "COM", COM );

		input.forEach( line -> {
			final String[] connection = splitOnRegex( line, "\\)" ).toArray( String[]::new );
			getOrAddNode( nodes, connection[0] ).addChild( getOrAddNode( nodes, connection[1] ) );
		} );

		return itoa( computeResult.applyAsLong( COM ) );
	}

	private TreeNode<String> getOrAddNode( final Map<String, TreeNode<String>> nodes,
			final String nodeStr ) {
		return nodes.computeIfAbsent( nodeStr, TreeNode::new );
	}

	private long countOrbits( final TreeNode<String> node ) {
		return countOrbits( node, 0 );
	}

	private long countOrbits( final TreeNode<String> node, final long count ) {
		long val = count;
		for ( final TreeNode<String> n : node.getChildren() ) {
			val += countOrbits( n, count + 1 );
		}
		return val;
	}

	private long countTransfers( final Map<String, TreeNode<String>> nodes ) {
		final Map<TreeNode<String>, Long> youAncestors = new HashMap<>();
		TreeNode<String> youAncestor = nodes.get( "YOU" );
		final Map<TreeNode<String>, Long> sanAncestors = new HashMap<>();
		TreeNode<String> sanAncestor = nodes.get( "SAN" );

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

	private TreeNode<String> addAncestor( final Map<TreeNode<String>, Long> ancestors,
			final TreeNode<String> node, final long distance ) {
		final TreeNode<String> ancestor = node.getParent().orElseThrow();
		ancestors.put( ancestor, distance );
		return ancestor;
	}

	private Optional<Long> getDistance( final Map<TreeNode<String>, Long> ancestorsFirst,
			final TreeNode<String> ancestorSecond, final Map<TreeNode<String>, Long> ancestorsSecond,
			final TreeNode<String> nodeFirst ) {
		final Long distance = ancestorsFirst.get( ancestorSecond );
		if ( distance != null ) {
			return Optional.of( distance );
		} else {
			return Optional.ofNullable( ancestorsSecond.get( nodeFirst ) );
		}
	}

}
