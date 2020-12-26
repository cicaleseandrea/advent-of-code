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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

	private String solve( final Stream<String> input, final boolean first ) {
		Set<Pair<Integer, Integer>> blackTiles = new HashSet<>();
		for ( final var line : getIterable( input ) ) {
			var tile = new Pair<>( 0, 0 );
			for ( final var direction : getDirections( line ) ) {
				tile = addTiles( tile, direction );
			}
			// flip tile
			if ( !blackTiles.add( tile ) ) {
				blackTiles.remove( tile );
			}
		}
		if ( !first ) {
			for ( int i = 0; i < 100; i++ ) {
				final var prevBlackTiles = blackTiles;
				blackTiles = new HashSet<>();
				for ( final var blackTile : prevBlackTiles ) {
					if ( newColorIsBlack( blackTile, true, prevBlackTiles ) ) {
						blackTiles.add( blackTile );
					}
					DIRECTIONS.values()
							.stream()
							.map( direction -> addTiles( blackTile, direction ) )
							.filter( Predicate.not( prevBlackTiles::contains ) )
							.filter( Predicate.not( blackTiles::contains ) )
							.filter( neighbour -> newColorIsBlack( neighbour, false,
									prevBlackTiles ) )
							.forEach( blackTiles::add );
				}
			}
		}
		return itoa( blackTiles.size() );
	}

	private static boolean newColorIsBlack( final Pair<Integer, Integer> tilePos,
			final boolean isBlackTile, final Set<Pair<Integer, Integer>> blackTiles ) {
		final long blackNeighbours = DIRECTIONS.values()
				.stream()
				.map( direction -> addTiles( tilePos, direction ) )
				.filter( blackTiles::contains )
				.count();
		if ( isBlackTile && ( blackNeighbours == 0 || blackNeighbours > 2 ) ) {
			return false;
		} else if ( !isBlackTile && blackNeighbours == 2 ) {
			return true;
		} else {
			return isBlackTile;
		}
	}

	private static List<Pair<Integer, Integer>> getDirections( final String line ) {
		final List<Pair<Integer, Integer>> directions = new ArrayList<>();
		for ( int i = 0; i < line.length(); i++ ) {
			final Direction direction = switch ( line.charAt( i ) ) {
				case 'e' -> E;
				case 'w' -> W;
				case 'n' -> line.charAt( ++i ) == 'w' ? NW : NE;
				case 's' -> line.charAt( ++i ) == 'w' ? SW : SE;
				default -> throw new IllegalStateException();
			};
			directions.add( DIRECTIONS.get( direction ) );
		}
		return directions;
	}

	private static Pair<Integer, Integer> addTiles( final Pair<Integer, Integer> tileA,
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
