package com.adventofcode.aoc2021;

import static java.util.stream.Collectors.toSet;

import static com.adventofcode.utils.Utils.NEIGHBOURS_8;
import static com.adventofcode.utils.Utils.clearScreen;
import static com.adventofcode.utils.Utils.getDigitsMatrix;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.listGetOrDefault;
import static com.adventofcode.utils.Utils.printMatrix;
import static com.adventofcode.utils.Utils.shouldPrint;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC112021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, steps -> steps <= 100 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, steps -> true );
	}

	private String solve( final Stream<String> input, final IntPredicate performStep ) {
		final var energyMap = getDigitsMatrix( input );
		print( energyMap );

		var totalFlashes = 0;
		for ( int steps = 1; performStep.test( steps ); steps++ ) {
			final var higherEnergyLevels = increaseAllEnergyLevels( energyMap );

			while ( !higherEnergyLevels.isEmpty() ) {
				// flash
				final var flash = higherEnergyLevels.stream().findAny().orElseThrow();
				energyMap.get( flash.x ).set( flash.y, 0 );
				higherEnergyLevels.remove( flash );
				totalFlashes++;

				// increase neighbours
				higherEnergyLevels.addAll( increaseNeighboursEnergyLevels( energyMap, flash ) );
			}

			print( energyMap );

			if ( allSynchronised( energyMap ) ) {
				return itoa( steps );
			}
		}

		return itoa( totalFlashes );
	}

	private Set<Point> increaseNeighboursEnergyLevels( final List<List<Integer>> energyMap,
			final Point flashed ) {
		return NEIGHBOURS_8.stream()
				.map( neighbourOffset -> new Point( flashed.x + neighbourOffset.getFirst(),
						flashed.y + neighbourOffset.getSecond() ) )
				.filter( neighbour -> listGetOrDefault(
						listGetOrDefault( energyMap, neighbour.x, List.of() ), neighbour.y,
						0 ) != 0 )
				.filter( neighbour -> isHigherEnergyLevelAfterIncrease( energyMap, neighbour ) )
				.collect( toSet() );
	}

	private Set<Point> increaseAllEnergyLevels( final List<List<Integer>> energyMap ) {
		return IntStream.range( 0, energyMap.size() )
				.boxed()
				.flatMap( i -> IntStream.range( 0, energyMap.get( 0 ).size() )
						.mapToObj( j -> new Point( i, j ) ) )
				.filter( neighbour -> isHigherEnergyLevelAfterIncrease( energyMap, neighbour ) )
				.collect( toSet() );
	}

	private boolean isHigherEnergyLevelAfterIncrease( final List<List<Integer>> energyMap,
			final Point point ) {
		final var updatedEnergy = energyMap.get( point.x ).get( point.y ) + 1;
		energyMap.get( point.x ).set( point.y, updatedEnergy );
		return updatedEnergy > 9;
	}

	private boolean allSynchronised( final List<List<Integer>> energyMap ) {
		return energyMap.stream().flatMap( Collection::stream ).allMatch( energy -> energy == 0 );
	}

	private static void print( final List<List<Integer>> energyMap ) {
		if ( shouldPrint() ) {
			clearScreen();
			printMatrix( energyMap );
			System.out.println();
			try {
				Thread.sleep( 200 );
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}

	private record Point(int x, int y) {}
}
