package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnTabOrSpace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;

class AoC082021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, this::countEasyDigits );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, this::joinDigits );
	}

	private String solve( final Stream<String> input,
			final ToLongFunction<List<Integer>> computeResult ) {
		return itoa( input.map( str -> str.split( " \\| " ) )
				.map( arr -> new Pair<>( splitOnTabOrSpace( arr[0] ),
						splitOnTabOrSpace( arr[1] ) ) )
				.map( pair -> new Pair<>(
						pair.getFirst().stream().map( Utils::stringToCharacterSet ).toList(),
						pair.getSecond().stream().map( Utils::stringToCharacterSet ).toList() ) )
				.mapToLong(
						pair -> decode( pair.getSecond(), computeSignalsMapping( pair.getFirst() ),
								computeResult ) )
				.sum() );
	}

	private long decode( final List<Set<Character>> encodedOutput,
			final Map<Set<Character>, Integer> signalsMapping,
			final ToLongFunction<List<Integer>> computeResult ) {
		final var digits = encodedOutput.stream().map( signalsMapping::get ).toList();
		return computeResult.applyAsLong( digits );
	}

	private Map<Set<Character>, Integer> computeSignalsMapping(
			final List<Set<Character>> signals ) {
		final var signalsSortedBySize = new ArrayList<>( signals );
		signalsSortedBySize.sort( Comparator.comparing( Set::size ) );

		final BiMap<Set<Character>, Integer> signalsMapping = HashBiMap.create();
		for ( final var signal : signalsSortedBySize ) {
			switch ( signal.size() ) {
			case 2 -> signalsMapping.put( signal, 1 );
			case 3 -> signalsMapping.put( signal, 7 );
			case 4 -> signalsMapping.put( signal, 4 );
			case 7 -> signalsMapping.put( signal, 8 );
			case 5 -> {
				final var one = signalsMapping.inverse().get( 1 );
				final var four = signalsMapping.inverse().get( 4 );
				if ( Sets.intersection( signal, one ).size() == 2 ) {
					signalsMapping.put( signal, 3 );
				} else if ( Sets.intersection( signal, four ).size() == 2 ) {
					signalsMapping.put( signal, 2 );
				} else {
					signalsMapping.put( signal, 5 );
				}
			}
			case 6 -> {
				final var one = signalsMapping.inverse().get( 1 );
				final var four = signalsMapping.inverse().get( 4 );
				if ( Sets.intersection( signal, four ).size() == 4 ) {
					signalsMapping.put( signal, 9 );
				} else if ( Sets.intersection( signal, one ).size() == 1 ) {
					signalsMapping.put( signal, 6 );
				} else {
					signalsMapping.put( signal, 0 );
				}
			}
			default -> throw new IllegalStateException( "Unexpected value: " + signal.size() );
			}
		}

		return signalsMapping;
	}

	private long countEasyDigits( final List<Integer> digits ) {
		return digits.stream()
				.filter( digit -> digit == 1 || digit == 4 || digit == 7 || digit == 8 )
				.count();
	}

	private long joinDigits( final List<Integer> digits ) {
		long output = 0;
		for ( final var digit : digits ) {
			output *= 10;
			output += digit;
		}
		return output;
	}
}
