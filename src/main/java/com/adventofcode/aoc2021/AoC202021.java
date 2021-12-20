package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.NEIGHBOURS_8;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.shouldPrint;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC202021 implements Solution {
	private static final List<Pair<Integer, Integer>> PIXELS = Stream.concat( NEIGHBOURS_8.stream(),
					Stream.of( new Pair<>( 0, 0 ) ) )
			.sorted( Comparator.<Pair<Integer, Integer>>comparingInt( Pair::getFirst )
					.thenComparing( Pair::getSecond ) )
			.toList();

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 2 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 50 );
	}

	private String solve( final Stream<String> input, final int steps ) {
		final var inputList = input.toList();

		final var algorithm = inputList.get( 0 );
		final var algorithmLightsOn = new boolean[512];
		IntStream.range( 0, algorithm.length() )
				.forEach( i -> algorithmLightsOn[i] = algorithm.charAt( i ) == HASH );

		var image = getImage( inputList.subList( 2, inputList.size() ) );
		printImage( image );

		int min = 0;
		int max = inputList.size() - 2;
		for ( int i = 0; i < steps; i++ ) {
			image = enhance( image, algorithmLightsOn, inputList.size() > 10 && i % 2 != 0, min--,
					max++ );
			printImage( image );
		}

		return itoa( image.size() );
	}

	private Set<Point> enhance( final Set<Point> image, final boolean[] algorithmLightsOn,
			final boolean infinityLightsOn, final int min, final int max ) {
		final Set<Point> enhanced = new HashSet<>();

		if ( infinityLightsOn ) {
			fillBorders( image, min, max );
		}

		for ( int i = min - 1; i <= max + 1; i++ ) {
			for ( int j = min - 1; j <= max + 1; j++ ) {
				final var point = new Point( i, j );
				final var index = getIndex( point, image );
				if ( algorithmLightsOn[index] ) {
					enhanced.add( point );
				}
			}
		}
		return enhanced;
	}

	private int getIndex( final Point point, final Set<Point> image ) {
		int index = 0;
		for ( final var pixelOffset : PIXELS ) {
			final var pixel = new Point( point.x + pixelOffset.getFirst(),
					point.y + pixelOffset.getSecond() );
			index *= 2;
			index += image.contains( pixel ) ? 1 : 0;
		}
		return index;
	}

	private void fillBorders( final Set<Point> image, final int min, final int max ) {
		for ( int j = min - 2; j <= max + 2; j++ ) {
			image.add( new Point( min - 2, j ) );
			image.add( new Point( min - 1, j ) );
			image.add( new Point( max + 1, j ) );
			image.add( new Point( max + 2, j ) );
		}
		for ( int i = min; i <= max; i++ ) {
			image.add( new Point( i, min - 2 ) );
			image.add( new Point( i, min - 1 ) );
			image.add( new Point( i, max + 1 ) );
			image.add( new Point( i, max + 2 ) );
		}
	}

	private Set<Point> getImage( final List<String> input ) {
		final Set<Point> image = new HashSet<>();
		for ( int i = 0; i < input.size(); i++ ) {
			for ( int j = 0; j < input.get( 0 ).length(); j++ ) {
				if ( input.get( i ).charAt( j ) == HASH ) {
					image.add( new Point( i, j ) );
				}
			}
		}
		return image;
	}

	private void printImage( final Set<Point> image ) {
		if ( shouldPrint() ) {
			final var statsX = image.stream().mapToInt( Point::x ).summaryStatistics();
			final var statsY = image.stream().mapToInt( Point::y ).summaryStatistics();
			for ( int i = statsX.getMin(); i <= statsX.getMax(); i++ ) {
				for ( int j = statsY.getMin(); j <= statsY.getMax(); j++ ) {
					System.out.print( image.contains( new Point( i, j ) ) ? HASH : DOT );
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	private record Point(int x, int y) {}
}
