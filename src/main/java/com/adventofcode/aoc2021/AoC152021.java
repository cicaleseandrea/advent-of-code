package com.adventofcode.aoc2021;

import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC152021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, 1 );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, 5 );
	}

	private String solve( final Stream<String> input, final int repetitions ) {
		final var grid = initializeGrid( repetitions, input );

		final var start = new Point( 0, 0 );
		final var end = grid.keySet()
				.stream()
				.max( comparingInt( Point::x ).thenComparingInt( Point::y ) )
				.orElseThrow();

		// Dijkstra to find the shortest path
		final var distances = grid.keySet()
				.stream()
				.collect( toMap( identity(), e -> Integer.MAX_VALUE ) );
		distances.put( start, 0 );
		final Queue<Point> priorityQueue = new PriorityQueue<>( comparingInt( distances::get ) );
		priorityQueue.add( start );

		while ( !priorityQueue.isEmpty() ) {
			final var curr = priorityQueue.poll();
			if ( curr.equals( end ) ) {
				// target found
				return itoa( distances.get( curr ) );
			}

			NEIGHBOURS_4.stream()
					.map( neighbourOffset -> new Point( curr.x + neighbourOffset.getFirst(),
							curr.y + neighbourOffset.getSecond() ) )
					.filter( grid::containsKey )
					.forEach( neighbour -> {
						final var updatedDistance = distances.get( curr ) + grid.get( neighbour );
						// distance improved
						if ( updatedDistance < distances.getOrDefault( neighbour,
								Integer.MAX_VALUE ) ) {
							distances.put( neighbour, updatedDistance );
							priorityQueue.remove( neighbour );
							priorityQueue.add( neighbour );
						}
					} );
		}
		throw new IllegalStateException();
	}

	private Map<Point, Integer> initializeGrid( final int repetitions,
			final Stream<String> input ) {
		final var inputList = input.toList();
		final int rows = inputList.size();
		final int columns = inputList.get( 0 ).length();
		final Map<Point, Integer> grid = new HashMap<>();
		for ( int k = 0; k < repetitions; k++ ) {
			for ( int l = 0; l < repetitions; l++ ) {
				for ( int i = 0; i < rows; i++ ) {
					for ( int j = 0; j < columns; j++ ) {
						int value = charToInt( inputList.get( i ).charAt( j ) ) + k + l;
						if ( value > 9 ) {
							value -= 9;
						}
						grid.put( new Point( i + rows * k, j + columns * l ), value );
					}
				}
			}
		}
		return grid;
	}

	private record Point(int x, int y) {}
}
