package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.itoa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC072016 implements Solution {
	private static final Pattern ABBA_REGEX = Pattern.compile( ".*(\\w)(?!\\1)(\\w)\\2\\1.*" );
	private static final Pattern ABBA_BRACKETS_REGEX = Pattern.compile(
			".*\\[.*(\\w)(?!\\1)(\\w)\\2\\1[^\\[]*].*" );
	private static final Pattern ABA_REGEX = Pattern.compile( "(\\w)(?!\\1)(\\w)\\1" );
	private static final String ABA_START_REGEX = "[^\\[]*ABA.*";
	private static final String ABA_END_REGEX = ".*][^\\[]*ABA\\w*";
	private static final String ABA_MIDDLE_REGEX = ".*][^\\[]*ABA[^]]*\\[.*";
	private static final String ABA_BRACKETS_REGEX = ".*\\[.*ABA[^\\[]*].*";

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final long count = input.filter( line -> ABBA_REGEX.matcher( line ).matches() )
				.filter( line -> !ABBA_BRACKETS_REGEX.matcher( line ).matches() )
				.count();
		return itoa( count );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final long count = input.filter( line -> {
			final Matcher aba = ABA_REGEX.matcher( line );
			int index = 0;
			while ( aba.find( index ) ) {
				index = aba.start() + 1;
				final char a = aba.group().charAt( 0 );
				final char b = aba.group().charAt( 1 );
				// 🙈🙈🙈
				if ( line.matches( ABA_BRACKETS_REGEX.replace( 'A', a ).replace( 'B', b ) ) ) {
					if ( line.matches( ABA_START_REGEX.replace( 'A', b ).replace( 'B', a ) ) || line
							.matches( ABA_MIDDLE_REGEX.replace( 'A', b ).replace( 'B', a ) ) || line
							.matches( ABA_END_REGEX.replace( 'A', b ).replace( 'B', a ) ) ) {
						return true;
					}
				}
			}
			return false;
		} ).count();
		return itoa( count );
	}

}
