package com.adventofcode.aoc2020;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.atoi;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;

class AoC132020 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var list = input.collect( toList() );
		final int timestamp = atoi( list.get( 0 ) );
		final var buses = Utils.toLongList( list.get( 1 ) );
		return buses.stream()
				.map( bus -> new Pair<>( bus, bus - ( timestamp % bus ) ) )
				.min( Comparator.comparing( Pair::getSecond ) )
				.map( result -> result.getFirst() * result.getSecond() )
				.map( Utils::itoa )
				.orElseThrow();
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var minutesWithIndexes = initialize( input );
		final var firstBus = minutesWithIndexes.next();
		final BigInteger firstIndex = firstBus.getFirst();
		BigInteger offset = firstBus.getSecond();
		BigInteger timestamp = BigInteger.ZERO;
		var next = minutesWithIndexes.next();
		while ( next != null ) {
			timestamp = timestamp.add( offset );
			final BigInteger difference = next.getFirst().subtract( firstIndex );
			final BigInteger minutes = next.getSecond();
			final BigInteger mod = timestamp.add( difference ).mod( minutes );
			if ( mod.equals( BigInteger.ZERO ) ) {
				offset = offset.multiply( minutes );
				next = minutesWithIndexes.hasNext() ? minutesWithIndexes.next() : null;
			}
		}
		return timestamp.toString();
	}

	private Iterator<Pair<BigInteger, BigInteger>> initialize( final Stream<String> input ) {
		final var minutes = input.collect( toList() ).get( 1 ).split( "," );
		return IntStream.range( 0, minutes.length )
				.filter( index -> !minutes[index].equals( "x" ) )
				.mapToObj( index -> new Pair<>( BigInteger.valueOf( index ),
						new BigInteger( minutes[index] ) ) )
				.iterator();
	}

}
