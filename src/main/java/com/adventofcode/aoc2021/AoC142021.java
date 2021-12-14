package com.adventofcode.aoc2021;

import static java.util.stream.Collectors.toMap;

import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.incrementMapElement;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC142021 implements Solution {
	private static final Pattern RULE_REGEX = Pattern.compile( "(\\D)(\\D) -> (\\D)" );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 10 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 40 );
	}

	private String solve( final Stream<String> input, final int steps ) {
		final var inputList = input.toList();
		final Map<Pair<Character, Character>, Character> rules = getRules( inputList );

		Map<Pair<Character, Character>, Long> pairs = getPairs( inputList.get( 0 ) );
		for ( int i = 0; i < steps; i++ ) {
			pairs = runStep( pairs, rules );
		}

		final var stats = pairs.entrySet()
				.stream()
				.collect( toMap( entry -> entry.getKey().getFirst(), Map.Entry::getValue,
						Long::sum ) )
				.values()
				.stream()
				.mapToLong( Long::longValue )
				.summaryStatistics();
		return itoa( stats.getMax() - stats.getMin() );
	}

	private Map<Pair<Character, Character>, Long> runStep(
			final Map<Pair<Character, Character>, Long> pairs,
			final Map<Pair<Character, Character>, Character> rules ) {
		final Map<Pair<Character, Character>, Long> updatedPairs = new HashMap<>();
		pairs.forEach( ( pair, count ) -> {
			final var start = pair.getFirst();
			final var end = pair.getSecond();
			final var mid = rules.getOrDefault( pair, SPACE );
			incrementMapElement( updatedPairs, new Pair<>( start, mid ), count );
			if ( mid != SPACE ) {
				incrementMapElement( updatedPairs, new Pair<>( mid, end ), count );
			}
		} );
		return updatedPairs;
	}

	private Map<Pair<Character, Character>, Long> getPairs( final String template ) {
		final Map<Pair<Character, Character>, Long> pairs = new HashMap<>();
		for ( int i = 0; i < template.length(); i++ ) {
			final var curr = template.charAt( i );
			final var next = i + 1 < template.length() ? template.charAt( i + 1 ) : SPACE;
			incrementMapElement( pairs, new Pair<>( curr, next ) );
		}
		return pairs;
	}

	private Map<Pair<Character, Character>, Character> getRules( final List<String> list ) {
		final Map<Pair<Character, Character>, Character> rules = new HashMap<>();
		for ( int i = 2; i < list.size(); i++ ) {
			final var rule = list.get( i );
			final var matcher = RULE_REGEX.matcher( rule );
			if ( !matcher.matches() ) {
				throw new IllegalArgumentException();
			}
			final var a = matcher.group( 1 ).charAt( 0 );
			final var b = matcher.group( 2 ).charAt( 0 );
			final var c = matcher.group( 3 ).charAt( 0 );
			rules.put( new Pair<>( a, b ), c );
		}
		return rules;
	}
}
