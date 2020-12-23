package com.adventofcode.aoc2020;

import static java.util.stream.Collectors.joining;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.LinkedListNode;
import com.adventofcode.utils.Utils;

class AoC232020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final int maxCups = first ? 9 : 1_000_000;
		final Map<Integer, LinkedListNode<Integer>> labelToCup = new HashMap<>();
		var current = initialize( input, maxCups, labelToCup );
		for ( int i = 0; i < ( first ? 100 : 10_000_000 ); i++ ) {
			// select 3 cups head
			final var subStart = current.next;
			// detach main list from 3 cups
			current.next = current.next.next.next.next;
			// detach and save sublist
			final HashSet<Integer> subset = new HashSet<>();
			var subEnd = detachSublist( subset, subStart );
			final var destination = labelToCup.get(
					getDestinationLabel( current.getValue(), subset, maxCups ) );
			// insert 3 cups after destination
			subEnd.next = destination.next;
			destination.next = subStart;

			current = current.next;
		}
		while ( current.getValue() != 1 ) {
			current = current.next;
		}
		if ( first ) {
			return Stream.generate( current.iterator()::next )
					.skip( 1 )
					.limit( maxCups - 1L )
					.map( LinkedListNode::getValue )
					.map( Utils::itoa )
					.collect( joining() );
		} else {
			return itoa( (long) current.next.getValue() * current.next.next.getValue() );
		}
	}

	private LinkedListNode<Integer> initialize( final Stream<String> input, final int maxCups,
			final Map<Integer, LinkedListNode<Integer>> labelToCup ) {
		final var cups = IntStream.concat( getFirstString( input ).chars().map( Utils::charToInt ),
				IntStream.rangeClosed( 10, maxCups ) );
		var head = new LinkedListNode<>( 0 );
		var current = head;
		for ( final var cup : (Iterable<Integer>) cups::iterator ) {
			current.next = new LinkedListNode<>( cup );
			current = current.next;
			// save nodes for faster retrieval
			labelToCup.put( cup, current );
		}
		head = head.next;
		// make list circular
		current.next = head;
		return head;
	}

	private LinkedListNode<Integer> detachSublist( final HashSet<Integer> subset,
			final LinkedListNode<Integer> subStart ) {
		var subEnd = subStart;
		final int size = 3;
		for ( int j = 0; j < size; j++ ) {
			// save nodes for faster retrieval
			subset.add( subEnd.getValue() );
			if ( j < size - 1 ) {
				subEnd = subEnd.next;
			} else {
				// detach 3 cups from main list
				subEnd.next = null;
			}
		}
		return subEnd;
	}

	private int getDestinationLabel( final int currentLabel, final Collection<Integer> sublist,
			final int max ) {
		int destination = currentLabel;
		do {
			if ( destination > 1 ) {
				destination--;
			} else {
				destination = max;
			}
		} while ( sublist.contains( destination ) );
		return destination;
	}

}
