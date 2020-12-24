package com.adventofcode.aoc2020;

import static com.adventofcode.aoc2020.AoC242020.Direction.E;
import static com.adventofcode.aoc2020.AoC242020.Direction.NE;
import static com.adventofcode.aoc2020.AoC242020.Direction.NW;
import static com.adventofcode.aoc2020.AoC242020.Direction.SE;
import static com.adventofcode.aoc2020.AoC242020.Direction.SW;
import static com.adventofcode.aoc2020.AoC242020.Direction.W;
import static com.adventofcode.utils.Utils.getIterable;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC242020 implements Solution {
	private static final Map<Direction, Pair<Integer, Integer>> DIRECTIONS = Map.of( E,
			new Pair<>( +1, 0 ), NE, new Pair<>( +1, -1 ), NW, new Pair<>( 0, -1 ), W,
			new Pair<>( -1, 0 ), SW, new Pair<>( -1, +1 ), SE, new Pair<>( 0, +1 ) );

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
		Map<Pair<Integer, Integer>, Integer> floor = new HashMap<>();
		for ( final var line : getIterable( input ) ) {
			var tile = new Pair<>( 0, 0 );
			for ( final var direction : getDirections( line ) ) {
				tile = addTiles( tile, DIRECTIONS.get( direction ) );
			}
			floor.merge( tile, 1, Integer::sum );
		}
		if ( !first ) {
			Map<Pair<Integer, Integer>, Integer> prevFloor;
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

	private int newColor( final Pair<Integer, Integer> tilePos, int tileColor,
			final Map<Pair<Integer, Integer>, Integer> floor ) {
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

	private List<Direction> getDirections( final String line ) {
		final ArrayList<Direction> directions = new ArrayList<>();
		for ( int i = 0; i < line.length(); i++ ) {
			final Direction direction = switch ( line.charAt( i ) ) {
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

	private Pair<Integer, Integer> addTiles( final Pair<Integer, Integer> tileA,
			final Pair<Integer, Integer> tileB ) {
		return new Pair<>( tileA.getFirst() + tileB.getFirst(),
				tileA.getSecond() + tileB.getSecond() );
	}

	enum Direction {
		E,
		NE,
		NW,
		W,
		SW,
		SE,
	}

}
