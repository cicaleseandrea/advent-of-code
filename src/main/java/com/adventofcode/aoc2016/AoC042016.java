package com.adventofcode.aoc2016;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Triplet;

class AoC042016 implements Solution {
	private static final Pattern ROOM_REGEX = Pattern.compile( "([\\w-]+)-(\\d+)\\[(\\w+)]" );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final BiFunction<String, Integer, String> compute = ( letters, n ) -> {
			final Map<Character, Long> frequencies = letters.chars()
					.filter( c -> c != '-' )
					.mapToObj( c -> (char) c )
					.collect( groupingBy( identity(), counting() ) );
			return frequencies.entrySet()
					.stream()
					.sorted( Entry.<Character, Long>comparingByValue().reversed()
							.thenComparing( Entry::getKey ) )
					.limit( 5 )
					.map( Entry::getKey )
					.map( Object::toString )
					.collect( joining() );
		};
		return solve( input, compute, String::equals );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final BiFunction<String, Integer, String> compute = ( letters, number ) -> letters.chars()
				.map( c -> c - 'a' )
				.map( c -> ( c + ( number % 26 ) ) % 26 )
				.map( c -> c + 'a' )
				.map( c -> c < 'a' ? SPACE : c )
				.mapToObj( c -> (char) c )
				.map( Object::toString )
				.collect( joining() );
		return solve( input, compute,
				( decrypted, t ) -> decrypted.equals( "northpole object storage" ) );
	}

	private String solve( final Stream<String> input,
			final BiFunction<String, Integer, String> compute,
			final BiPredicate<String, String> filter ) {
		final int sum = input.map( room -> {
			final var matcher = ROOM_REGEX.matcher( room );
			if ( !matcher.matches() ) {
				throw new IllegalArgumentException();
			}
			final String letters = matcher.group( 1 );
			final int number = atoi( matcher.group( 2 ) );
			final String checksum = matcher.group( 3 );
			final String computed = compute.apply( letters, number );
			return new Triplet<>( computed, checksum, number );
		} )
				.filter( args -> filter.test( args.getFirst(), args.getSecond() ) )
				.mapToInt( Triplet::getThird )
				.sum();
		return itoa( sum );
	}

}
