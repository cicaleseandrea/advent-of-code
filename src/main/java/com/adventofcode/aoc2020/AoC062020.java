package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC062020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return itoa( getGroups( input ).stream()
				.mapToLong( group -> countAnswers( group, Set::addAll ) )
				.sum() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return itoa( getGroups( input ).stream()
				.mapToLong( group -> countAnswers( group, Set::retainAll ) )
				.sum() );
	}

	private static long countAnswers( final List<String> group,
			final BiConsumer<Set<Integer>, Set<Integer>> collector ) {
		final var lines = group.stream()
				.map( String::chars )
				.map( IntStream::boxed )
				.map( line -> line.collect( toSet() ) )
				.toList();

		final var answersToCount = new HashSet<>( lines.get( 0 ) );
		lines.forEach( line -> collector.accept( answersToCount, line ) );
		return answersToCount.size();
	}

	private static List<List<String>> getGroups( final Stream<String> input ) {
		final var groups = new ArrayList<List<String>>();
		final var remainingAnswers = input.iterator();
		while ( remainingAnswers.hasNext() ) {
			final var group = new ArrayList<String>();
			String line;
			while ( remainingAnswers.hasNext() && !( line = remainingAnswers.next() ).isEmpty() ) {
				group.add( line );
			}
			groups.add( group );
		}
		return groups;
	}

}
