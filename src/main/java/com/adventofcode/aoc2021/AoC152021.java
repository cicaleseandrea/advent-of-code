package com.adventofcode.aoc2021;

import static java.util.Comparator.comparingInt;

import static com.adventofcode.utils.Utils.NEIGHBOURS_4;
import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Queue;
import java.util.TreeMap;
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

	private void updatePriorityQueueDistance(
			final NavigableMap<Integer, Queue<Point>> priorityQueue, final Point neighbour,
			final int newDistance, final int oldDistance ) {
		// remove old distance from the queue
		if ( priorityQueue.get( oldDistance ).remove( neighbour ) && priorityQueue.get(
				oldDistance ).isEmpty() ) {
			priorityQueue.remove( oldDistance );
		}
		// update distance in the queue
		priorityQueue.computeIfAbsent( newDistance, k -> new LinkedList<>() ).add( neighbour );
	}

	private Point removePointWithShortestDistance(
			final NavigableMap<Integer, Queue<Point>> priorityQueue ) {
		// remove position with shortest distance
		final var closestPositions = priorityQueue.firstEntry().getValue();
		final Point curr = closestPositions.remove();
		if ( closestPositions.isEmpty() ) {
			priorityQueue.pollFirstEntry();
		}
		return curr;
	}

	private String solve( final Stream<String> input, final int repetitions ) {
		final List<String> inputList = input.toList();
		final Map<Point, Integer> grid = initializeGrid( repetitions, inputList );

		final Point maxPoint = grid.keySet()
				.stream()
				.max( comparingInt( Point::x ).thenComparingInt( Point::y ) )
				.orElseThrow();
		final int rows = maxPoint.x + 1;
		final int columns = maxPoint.y + 1;
		final var START = new Point( 0, 0 );
		final var END = new Point( rows - 1, columns - 1 );

		// TODO this is too slooooooooooooooooooooooooooooow
		// Dijkstra to find shortest path to the target
		final NavigableMap<Integer, Queue<Point>> priorityQueue = new TreeMap<>();
		priorityQueue.put( Integer.MAX_VALUE, new LinkedList<>( grid.keySet() ) );
		priorityQueue.firstEntry().getValue().remove( START );
		// start from source
		priorityQueue.put( 0, new LinkedList<>( List.of( START ) ) );
		final Map<Point, Integer> distances = new HashMap<>( Map.of( START, 0 ) );

		while ( !priorityQueue.isEmpty() ) {
			final var curr = removePointWithShortestDistance( priorityQueue );
			if ( curr.equals( END ) ) {
				// target found
				return itoa( distances.get( curr ) );
			}
			NEIGHBOURS_4.stream()
					.filter( neighbourOffset -> curr.x + neighbourOffset.getFirst() >= 0 )
					.filter( neighbourOffset -> curr.x + neighbourOffset.getFirst() < rows )
					.filter( neighbourOffset -> curr.y + neighbourOffset.getSecond() >= 0 )
					.filter( neighbourOffset -> curr.y + neighbourOffset.getSecond() < columns )
					.map( neighbourOffset -> new Point( curr.x + neighbourOffset.getFirst(),
							curr.y + neighbourOffset.getSecond() ) )
					.forEach( neighbour -> {
						final var newDistance = distances.get( curr ) + grid.get( neighbour );
						final int oldDistance = distances.getOrDefault( neighbour,
								Integer.MAX_VALUE );
						// distance improved
						if ( newDistance < oldDistance ) {
							updatePriorityQueueDistance( priorityQueue, neighbour, newDistance,
									oldDistance );
							distances.put( neighbour, newDistance );
						}
					} );
		}
		throw new IllegalStateException();
	}

	private Map<Point, Integer> initializeGrid( final int repetitions,
			final List<String> inputList ) {
		final int rows = inputList.size();
		final int columns = inputList.get( 0 ).length();
		final Map<Point, Integer> grid = new HashMap<>();
		for ( int k = 0; k < repetitions; k++ ) {
			for ( int l = 0; l < repetitions; l++ ) {
				for ( int i = 0; i < rows; i++ ) {
					for ( int j = 0; j < columns; j++ ) {
						final int startingValue = charToInt( inputList.get( i ).charAt( j ) );
						int updatedValue = startingValue + k + l;
						if ( updatedValue > 9 ) {
							updatedValue -= 9;
						}
						grid.put( new Point( i + rows * k, j + columns * l ), updatedValue );
					}
				}
			}
		}
		return grid;
	}

	private record Point(int x, int y) {}
}
