package com.adventofcode.aoc2020;

import static java.util.stream.Collectors.joining;

import static com.adventofcode.utils.Utils.EMPTY;
import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.iteratorToStream;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

class AoC192020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final Map<Long, String> regexes = new HashMap<>();
		final Multimap<Long, Long> missingDependencies = HashMultimap.create();
		final HashSet<Long> complete = new HashSet<>();
		final var it = input.iterator();
		initialize( it, regexes, missingDependencies, complete, first );

		while ( !complete.contains( 0L ) ) {
			for ( final var dependency : new ArrayList<>( missingDependencies.entries() ) ) {
				final var full = dependency.getValue();
				if ( complete.contains( full ) ) {
					final Long partial = dependency.getKey();
					fillPartialRegex( partial, full, regexes );
					missingDependencies.remove( partial, full );
					if ( !missingDependencies.containsKey( partial ) ) {
						saveFullRegex( partial, regexes.get( partial ), regexes, complete );
					}
				}
			}
		}

		final var regex = Pattern.compile( regexes.get( 0L ) );
		return itoa(
				iteratorToStream( it ).map( regex::matcher ).filter( Matcher::matches ).count() );
	}

	private void fillPartialRegex( final Long partial, final Long full,
			final Map<Long, String> regexes ) {
		regexes.put( partial, replaceRegex( regexes.get( partial ), full, regexes.get( full ) ) );
	}

	private String replaceRegex( final String partialRegex, final Long rule,
			final String ruleRegex ) {
		final String target = " " + rule + " ";
		final String replacement = " " + ruleRegex + " ";
		String result = partialRegex;
		for ( int i = 0; i < 2; i++ ) {
			result = result.replace( target, replacement );
		}
		return result;
	}

	private void initialize( final Iterator<String> input, final Map<Long, String> regexes,
			final Multimap<Long, Long> missingDependencies, final HashSet<Long> complete,
			final boolean first ) {
		String line;
		while ( !( line = getNextLine( input, first ) ).isEmpty() ) {
			final var ruleArr = line.split( ":" );
			final long rule = atol( ruleArr[0] );
			final String regex = ruleArr[1];
			if ( regex.contains( "\"" ) ) {
				saveFullRegex( rule, regex.replace( "\"", EMPTY ), regexes, complete );
			} else {
				regexes.put( rule, "(?: " + regex + " )" );
			}
			Utils.toLongList( regex ).forEach( value -> missingDependencies.put( rule, value ) );
		}
	}

	private String getNextLine( final Iterator<String> input, final boolean first ) {
		final String line = input.next();
		if ( !first && line.equals( "8: 42" ) ) {
			return "8: ( 42 )+";
		} else if ( !first && line.equals( "11: 42 31" ) ) {
			return "11:" + IntStream.rangeClosed( 1, 4 )
					.mapToObj( i -> " 42" .repeat( i ) + " 31" .repeat( i ) )
					.collect( joining( " |" ) );
		} else {
			return line;
		}
	}

	private void saveFullRegex( final Long rule, final String regex,
			final Map<Long, String> regexes, final HashSet<Long> complete ) {
		regexes.put( rule, regex.replace( " ", EMPTY ) );
		complete.add( rule );
	}

}
