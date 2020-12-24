package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.getIterable;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.adventofcode.Solution;
import com.adventofcode.utils.Triplet;

class AoC242020 implements Solution {
	private static final int E = 0;
	private static final int NE = 1;
	private static final int NW = 2;
	private static final int W = 3;
	private static final int SW = 4;
	private static final int SE = 5;

	private static final Map<Integer, Triplet<Integer, Integer, Integer>> DIRECTIONS = Map.of( E,
			new Triplet<>( +1, -1, 0 ), NE, new Triplet<>( +1, 0, -1 ), NW,
			new Triplet<>( 0, +1, -1 ), W, new Triplet<>( -1, +1, 0 ), SW,
			new Triplet<>( -1, 0, +1 ), SE, new Triplet<>( 0, -1, +1 ) );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	@NotNull
	private String solve( final Stream<String> input, final boolean first ) {
		Map<Triplet<Integer, Integer, Integer>, Integer> floor = new HashMap<>();
		for ( final var line : getIterable( input ) ) {
			var tile = new Triplet<>( 0, 0, 0 );
			for ( final var direction : getDirections( line ) ) {
				tile = addTiles( tile, DIRECTIONS.get( direction ) );
			}
			floor.merge( tile, 1, Integer::sum );
		}
		if ( !first ) {
			Map<Triplet<Integer, Integer, Integer>, Integer> prevFloor;
			for ( int i = 0; i < 100; i++ ) {
				prevFloor = floor;
				floor = new HashMap<>();
				for ( final var tile : prevFloor.entrySet() ) {
					final var tilePos = tile.getKey();
					floor.put( tilePos, newColor( tilePos, tile.getValue(), prevFloor ) );
					for ( final var direction : DIRECTIONS.values() ) {
						final var neighbour = addTiles( tilePos, direction );
						if ( !prevFloor.containsKey( neighbour ) && !floor.containsKey(
								neighbour ) ) {
							floor.put( neighbour, newColor( neighbour, 0, prevFloor ) );
						}
					}
				}
			}
		}
		return itoa( floor.values().stream().filter( color -> color % 2 == 1 ).count() );
	}

	private List<Integer> getDirections( final String line ) {
		final ArrayList<Integer> directions = new ArrayList<>();
		for ( int i = 0; i < line.length(); i++ ) {
			final int direction = switch ( line.charAt( i ) ) {
				case 'e' -> E;
				case 'w' -> W;
				case 'n' -> line.charAt( ++i ) == 'w' ? NW : NE;
				case 's' -> line.charAt( ++i ) == 'w' ? SW : SE;
				default -> throw new IllegalStateException();
			};
			directions.add( direction );
		}
		return directions;
	}

	private int newColor( final Triplet<Integer, Integer, Integer> tilePos, int tileColor,
			final Map<Triplet<Integer, Integer, Integer>, Integer> floor ) {
		int neighbours = 0;
		for ( final var direction : DIRECTIONS.values() ) {
			final var neighbour = addTiles( tilePos, direction );
			neighbours += floor.getOrDefault( neighbour, 0 ) % 2;
		}
		if ( tileColor % 2 == 1 && ( neighbours == 0 || neighbours > 2 ) ) {
			return 0;
		} else if ( tileColor % 2 == 0 && neighbours == 2 ) {
			return 1;
		} else {
			return tileColor;
		}
	}

	private Triplet<Integer, Integer, Integer> addTiles(
			final Triplet<Integer, Integer, Integer> tileA,
			final Triplet<Integer, Integer, Integer> tileB ) {
		return new Triplet<>( tileA.getFirst() + tileB.getFirst(),
				tileA.getSecond() + tileB.getSecond(), tileA.getThird() + tileB.getThird() );
	}

}
