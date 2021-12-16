package com.adventofcode.aoc2021;

import static java.lang.Long.parseLong;

import static com.adventofcode.utils.Utils.itoa;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC162021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var packet = input.findAny().map( this::hexToBinary ).orElseThrow();
		return itoa( parsePacket( packet, new AtomicInteger(), true ) );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var packet = input.findAny().map( this::hexToBinary ).orElseThrow();
		return itoa( parsePacket( packet, new AtomicInteger(), false ) );
	}

	private long parsePacket( final String packet, final AtomicInteger index,
			final boolean first ) {
		final var version = binaryStringToDecimal( getSubstring( packet, index, 3 ) );
		final var type = (int) binaryStringToDecimal( getSubstring( packet, index, 3 ) );
		if ( type == 4 ) {
			// literal value
			final var literalValue = getLiteralValue( packet, index );
			return first ? version : literalValue;
		}

		// operator
		final var lengthType = getSubstring( packet, index, 1 ).equals( "1" );
		final var subpacketsNumber = binaryStringToDecimal(
				getSubstring( packet, index, ( lengthType ? 11 : 15 ) ) );
		final var lastbit = index.get() + subpacketsNumber;
		final IntPredicate parseNext = i -> lengthType ? ( i < subpacketsNumber ) : ( index.get() < lastbit );
		final List<Long> subpackets = IntStream.iterate( 0, parseNext, i -> i + 1 )
				.mapToObj( i -> parsePacket( packet, index, first ) )
				.toList();

		if ( first ) {
			return version + subpackets.stream().mapToLong( l -> l ).sum();
		}

		return switch ( type ) {
			case 0 -> subpackets.stream().mapToLong( l -> l ).sum();
			case 1 -> subpackets.stream().mapToLong( l -> l ).reduce( 1, ( x, y ) -> x * y );
			case 2 -> subpackets.stream().mapToLong( l -> l ).min().orElseThrow();
			case 3 -> subpackets.stream().mapToLong( l -> l ).max().orElseThrow();
			case 5 -> subpackets.get( 0 ) > subpackets.get( 1 ) ? 1 : 0;
			case 6 -> subpackets.get( 0 ) < subpackets.get( 1 ) ? 1 : 0;
			case 7 -> Objects.equals( subpackets.get( 0 ), subpackets.get( 1 ) ) ? 1 : 0;
			default -> throw new IllegalStateException( "Unexpected value: " + type );
		};
	}

	private String getSubstring( final String packet, final AtomicInteger index,
			final int length ) {
		final var start = index.get();
		final var end = index.addAndGet( length );
		return packet.substring( start, end );
	}

	private long getLiteralValue( final String packet, final AtomicInteger index ) {
		final var binary = new StringBuilder();
		String group;
		do {
			group = getSubstring( packet, index, 5 );
			binary.append( group.substring( 1 ) );
		} while ( group.charAt( 0 ) == '1' );
		return binaryStringToDecimal( binary.toString() );
	}

	private String hexToBinary( String hex ) {
		hex = hex.replace( "0", "0000" );
		hex = hex.replace( "1", "0001" );
		hex = hex.replace( "2", "0010" );
		hex = hex.replace( "3", "0011" );
		hex = hex.replace( "4", "0100" );
		hex = hex.replace( "5", "0101" );
		hex = hex.replace( "6", "0110" );
		hex = hex.replace( "7", "0111" );
		hex = hex.replace( "8", "1000" );
		hex = hex.replace( "9", "1001" );
		hex = hex.replace( "A", "1010" );
		hex = hex.replace( "B", "1011" );
		hex = hex.replace( "C", "1100" );
		hex = hex.replace( "D", "1101" );
		hex = hex.replace( "E", "1110" );
		hex = hex.replace( "F", "1111" );
		return hex;
	}

	private long binaryStringToDecimal( final String binary ) {
		return parseLong( binary, 2 );
	}
}
