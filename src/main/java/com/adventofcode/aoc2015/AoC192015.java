package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

class AoC192015 implements Solution {

	@Override
	public String solveFirstPart(final Stream<String> input) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart(final Stream<String> input) {
		return solve( input, false );
	}

	private String solve(final Stream<String> input, final boolean first) {
		final var inputList = input.toList();
		final var substitutions = getSubstitutions( inputList.subList( 0, inputList.size() - 2 ) );
		var medicine = inputList.get( inputList.size() - 1 );
		return itoa( first ? getMolecules( medicine, substitutions, false ).size()
				: computeShortestPath( medicine, substitutions ) );
	}

	private int computeShortestPath(final String medicine,
			final Multimap<String, String> substitutions) {
		//A* to find shortest path to the target (BFS/Dijkstra would be slow)
		//heuristic function is string length
		final var queue = new PriorityQueue<State>();
		final var distances = new HashMap<State, Integer>();

		final var src = new State( medicine, heuristic( medicine ) );
		queue.add( src );
		distances.put( src, 0 );

		while ( !queue.isEmpty() ) {
			final var curr = queue.remove();
			if ( curr.molecule.equals( "e" ) ) {
				return distances.get( curr );
			}
			for ( final var molecule : getMolecules( curr.molecule, substitutions, true ) ) {
				final var newDistance = distances.get( curr ) + 1;
				final var neighbour = new State( molecule, newDistance + heuristic( molecule ) );
				if ( newDistance < distances.getOrDefault( neighbour, Integer.MAX_VALUE ) ) {
					//add to the queue
					queue.add( neighbour );
					//update tentative distance
					distances.put( neighbour, newDistance );
				}
			}
		}

		throw new IllegalStateException();
	}

	private int heuristic(final String molecule) {
		return molecule.length();
	}

	private Set<String> getMolecules(final String molecule,
			final Multimap<String, String> substitutions, final boolean reverse) {
		return substitutions.entries().stream()
				.flatMap( substitution -> getMolecules( molecule, substitution, reverse ).stream() )
				.collect( toSet() );
	}

	private Set<String> getMolecules(final String molecule, final Entry<String, String> substitution,
			final boolean reverse) {
		final var molecules = new HashSet<String>();
		final var from = reverse ? substitution.getValue() : substitution.getKey();
		final var to = reverse ? substitution.getKey() : substitution.getValue();
		int index = molecule.indexOf( from );
		while ( index != -1 ) {
			molecules.add(
					new StringBuilder( molecule ).replace( index, index + from.length(), to ).toString() );
			index = molecule.indexOf( from, index + 1 );
		}
		return molecules;
	}

	private Multimap<String, String> getSubstitutions(final List<String> input) {
		return input.stream().map( line -> line.split( " => " ) ).collect(
				ImmutableListMultimap.toImmutableListMultimap( tokens -> tokens[0], tokens -> tokens[1] ) );
	}

	private record State(String molecule, int score) implements Comparable<State> {

		@Override
		public boolean equals(Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			State state = (State) o;
			//States are equal if molecule is the same
			return molecule.equals( state.molecule );
		}

		@Override
		public int hashCode() {
			return Objects.hash( molecule );
		}

		@Override
		public int compareTo(final State o) {
			//comparison based on score
			return Integer.compare( this.score, o.score );
		}
	}
}
