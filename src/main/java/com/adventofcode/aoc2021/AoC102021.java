package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.itoa;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC102021 implements Solution {

	private static final Map<Character, Character> MATCHING_PAIRS = Map.of( '(', ')', '[', ']', '{',
			'}', '<', '>' );
	private static final Map<Character, Integer> CORRUPTED_SCORES = Map.of( ')', 3, ']', 57, '}',
			1197, '>', 25137 );
	private static final Map<Character, Integer> INCOMPLETE_SCORES = Map.of( ')', 1, ']', 2, '}', 3,
			'>', 4 );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, line -> computeScore( line, CORRUPTED_SCORES, Map.of() ),
				scores -> Arrays.stream( scores ).sum() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, line -> computeScore( line, Map.of(), INCOMPLETE_SCORES ),
				scores -> scores[scores.length / 2] );
	}

	private String solve( final Stream<String> input, final ToLongFunction<String> computeScore,
			final ToLongFunction<long[]> computeResult ) {
		final var scores = input.mapToLong( computeScore )
				.filter( score -> score != 0 )
				.sorted()
				.toArray();
		return itoa( computeResult.applyAsLong( scores ) );
	}

	private long computeScore( final String line, final Map<Character, Integer> corruptedScores,
			final Map<Character, Integer> incompleteScores ) {
		final Deque<Character> stack = new LinkedList<>();
		for ( final var c : line.toCharArray() ) {
			if ( MATCHING_PAIRS.containsKey( c ) ) {
				// open
				stack.push( c );
			} else if ( c == MATCHING_PAIRS.get( stack.pop() ) ) {
				// close
			} else {
				return corruptedScores.getOrDefault( c, 0 );
			}
		}

		long result = 0;
		while ( !stack.isEmpty() ) {
			final var open = stack.pop();
			final var close = MATCHING_PAIRS.get( open );
			result *= 5;
			result += incompleteScores.getOrDefault( close, 0 );
		}
		return result;
	}
}
