package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC142020 implements Solution {

	private static final Pattern MASK_REGEX = Pattern.compile( "mask = (.*)" );
	private static final Pattern NUMBERS_REGEX = Pattern.compile( "mem\\[(\\d+)] = (\\d+)" );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var list = input.toList();
		final Map<Long, Long> memory = new HashMap<>();
		long andMask = 1L;
		long orMask = 0L;
		List<Integer> floatingBits = List.of();
		for ( final String s : list ) {
			Matcher matcher;
			if ( ( matcher = MASK_REGEX.matcher( s ) ).matches() ) {
				final var bitmask = matcher.group( 1 );
				if ( first ) {
					andMask = Long.parseLong( bitmask.replace( 'X', '1' ), 2 );
				} else {
					floatingBits = getIndexes( bitmask );
				}
				orMask = Long.parseLong( bitmask.replace( 'X', '0' ), 2 );
			} else if ( ( matcher = NUMBERS_REGEX.matcher( s ) ).matches() ) {
				long index = atol( matcher.group( 1 ) );
				long value = atol( matcher.group( 2 ) );
				if ( first ) {
					memory.put( index, value & andMask | orMask );
				} else {
					writeToMemoryAddresses( memory, index | orMask, value, floatingBits );
				}
			}
		}
		return itoa( memory.values().stream().mapToLong( Long::longValue ).sum() );
	}

	private void writeToMemoryAddresses( final Map<Long, Long> memory, final long index,
			final long value, final List<Integer> floatingBits ) {
		long newIndex = index;
		// Using Gray Code to cycle through masks by only flipping one bit at a time
		// https://en.wikipedia.org/wiki/Gray_code#Constructing_an_n-bit_Gray_code
		// Using https://oeis.org/A007814 (http://mathworld.wolfram.com/BinaryCarrySequence.html) to find which bit to flip next
		for ( long i = 0; i < Math.pow( 2, floatingBits.size() ); i++ ) {
			final int position = Long.numberOfTrailingZeros( i ) % 64;
			// flip bit
			newIndex = newIndex ^ ( 1L << floatingBits.get( position ) );
			memory.put( newIndex, value );
		}
	}

	private List<Integer> getIndexes( final String bitmask ) {
		return IntStream.range( 0, bitmask.length() )
				.filter( i -> bitmask.charAt( i ) == 'X' )
				.map( i -> bitmask.length() - i - 1 )
				.boxed()
				.toList();
	}
}
