package com.adventofcode.aoc2015;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Triplet;

class AoC142015 implements Solution {
	private static final Pattern SPEED_REGEX = Pattern.compile(
			"\\w+ can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds." );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var reindeer = input.map( line -> {
			final var matcher = SPEED_REGEX.matcher( line );
			if ( !matcher.matches() ) {
				throw new IllegalArgumentException();
			}
			final int speed = atoi( matcher.group( 1 ) );
			final int runTime = atoi( matcher.group( 2 ) );
			final int restTime = atoi( matcher.group( 3 ) );
			return new Triplet<>( speed, runTime, restTime );
		} ).collect( toList() );

		final int seconds = reindeer.size() < 3 ? 1000 : 2503;
		if ( first ) {
			return itoa( Collections.max( computeRun( reindeer, seconds ) ) );
		}

		final int[] finalScores = new int[reindeer.size()];
		for ( int i = 1; i <= seconds; i++ ) {
			final List<Integer> tmpScores = computeRun( reindeer, i );
			final int max = Collections.max( tmpScores );
			IntStream.range( 0, tmpScores.size() )
					.filter( r -> max == tmpScores.get( r ) )
					.forEach( winner -> finalScores[winner]++ );
		}
		return itoa( Arrays.stream( finalScores ).max().orElseThrow() );
	}

	private List<Integer> computeRun( final List<Triplet<Integer, Integer, Integer>> reindeer,
			final int i ) {
		return reindeer.stream()
				.map( r -> computeScore( i, r.getFirst(), r.getSecond(), r.getThird() ) )
				.collect( toList() );
	}

	private int computeScore( final int seconds, final int speed, final int runTime,
			final int restTime ) {
		final int time = runTime + restTime;
		return seconds / time * runTime * speed + Math.min( seconds % time, runTime ) * speed;
	}

}
