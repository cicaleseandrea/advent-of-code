package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.itoa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC052015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final Pattern pattern;
		if ( first ) {
			//@formatter:off
			pattern = Pattern.compile(
					"(?!.*ab|.*cd|.*pq|.*xy)" //does not contain these substrings
					+ "(?=(?:.*[aeiou]){3})" //at least 3 vowels
					+ "(?=.*(.)\\1)" //at least one double letter
					+ ".*"// whatever
			);
			//@formatter:on
		} else {
			//@formatter:off
			pattern = Pattern.compile(
					"(?=.*(..).*\\1)" //pair of letters that appears at least twice
					+ "(?=.*(.).\\2)" //one letter which repeats with exactly one letter between them
					+ ".*" //whatever
			);
			//@formatter:on
		}
		return itoa( input.map( pattern::matcher ).filter( Matcher::matches ).count() );
	}

}
