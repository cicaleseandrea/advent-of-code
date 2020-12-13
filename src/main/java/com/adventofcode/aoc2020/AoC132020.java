package com.adventofcode.aoc2020;

import static java.math.BigInteger.ZERO;
import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.atoi;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
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
		final var minutesWithIndexes = getList( input );
		final var firstBus = minutesWithIndexes.remove( 0 );
		final BigInteger firstIndex = firstBus.getFirst();
		BigInteger increment = firstBus.getSecond();
		BigInteger timestamp = ZERO;
		for ( final var bus : minutesWithIndexes ) {
			final BigInteger difference = bus.getFirst().subtract( firstIndex );
			final BigInteger minutes = bus.getSecond();
			while ( !timestamp.add( difference ).mod( minutes ).equals( ZERO ) ) {
				timestamp = timestamp.add( increment );
			}
			increment = increment.multiply( minutes );
		}
		return timestamp.toString();
	}

	private List<Pair<BigInteger, BigInteger>> getList( final Stream<String> input ) {
		final var minutes = input.collect( toList() ).get( 1 ).split( "," );
		return IntStream.range( 0, minutes.length )
				.filter( index -> !minutes[index].equals( "x" ) )
				.mapToObj( index -> new Pair<>( BigInteger.valueOf( index ),
						new BigInteger( minutes[index] ) ) )
				.collect( toList() );
	}

}
