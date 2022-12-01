package com.adventofcode.aoc2016;

import com.adventofcode.Solution;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class AoC062016 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, Map.Entry.<Character, Integer>comparingByValue().reversed() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, Map.Entry.comparingByValue() );
	}

	private String solve( final Stream<String> input,
			final Comparator<Map.Entry<Character, Integer>> comparator ) {
		final StringBuilder message = new StringBuilder();
		final var lines = input.toList();
		final int size = lines.get( 0 ).length();
		for ( int i = 0; i < size; i++ ) {
			final Map<Character, Integer> frequencies = new HashMap<>();
			for ( final String word : lines ) {
				frequencies.merge( word.charAt( i ), 1, Integer::sum );
			}
			final var letter = frequencies.entrySet()
					.stream()
					.sorted( comparator )
					.map( Map.Entry::getKey )
					.findFirst()
					.orElseThrow();
			message.append( letter );
		}
		return message.toString();
	}

}
