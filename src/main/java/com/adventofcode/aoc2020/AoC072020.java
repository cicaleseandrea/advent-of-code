package com.adventofcode.aoc2020;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

class AoC072020 implements Solution {

	private static final Pattern BAGS_REGEX = Pattern.compile( "((\\d?) ?([a-z]* [a-z]*)) bag" );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var containedMap = ArrayListMultimap.<String, String>create();
		initialize( input, containedMap, ArrayListMultimap.create() );

		return itoa( getBagsContaining( containedMap, "shiny gold" ).size() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var containsMap = ArrayListMultimap.<String, Pair<String, Integer>>create();
		initialize( input, ArrayListMultimap.create(), containsMap );

		return itoa( countBagsContained( containsMap, "shiny gold" ) );
	}

	private void initialize( final Stream<String> input,
			final Multimap<String, String> containedMap,
			final Multimap<String, Pair<String, Integer>> containsMap ) {
		input.map( BAGS_REGEX::matcher )
				.map( Matcher::results )
				.map( resultStream -> resultStream.collect( toList() ) )
				.forEach( bags -> {
					final String outside = bags.get( 0 ).group( 1 );
					bags.subList( 1, bags.size() ).forEach( insideDescription -> {
						final int insideNumber = atoi( insideDescription.group( 2 )
								.isEmpty() ? "0" : insideDescription.group( 2 ) );
						final String insideName = insideDescription.group( 3 );
						containedMap.put( insideName, outside );
						containsMap.put( outside, new Pair<>( insideName, insideNumber ) );
					} );
				} );
	}

	private static Set<String> getBagsContaining( final Multimap<String, String> containedMap,
			final String input ) {
		final Set<String> bagsContaining = new HashSet<>();
		for ( final String outsideBag : containedMap.get( input ) ) {
			if ( bagsContaining.add( outsideBag ) ) {
				bagsContaining.addAll( getBagsContaining( containedMap, outsideBag ) );
			}
		}
		return bagsContaining;
	}

	private static int countBagsContained(
			final Multimap<String, Pair<String, Integer>> containsMap, final String input ) {
		final Map<String, Integer> bagsContainedCount = new HashMap<>();
		for ( final var insideDescription : containsMap.get( input ) ) {
			final String insideName = insideDescription.getFirst();
			final int insideNumber = insideDescription.getSecond();
			final int insideNumberAllLevels = bagsContainedCount.computeIfAbsent( insideName,
					k -> countBagsContained( containsMap, k ) );

			bagsContainedCount.merge( input,
					insideNumber + ( insideNumber * insideNumberAllLevels ), Integer::sum );
		}
		return bagsContainedCount.getOrDefault( input, 0 );
	}

}
