package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.AT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

class AoC182019 implements Solution {

	private static final List<Pair<Integer, Integer>> NEIGHBOURS = List.of( new Pair<>( -1, 0 ),
			new Pair<>( 1, 0 ), new Pair<>( 0, -1 ), new Pair<>( 0, 1 ) );

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final List<Pair<Pair<Integer, Integer>, BitSet>> sources = new ArrayList<>();
		final BitSet dst = new BitSet( 26 );
		final var map = initialize( input, sources, dst, first );

		return itoa( sources.stream().mapToInt( src -> computeDistance( map, src, dst ) ).sum() );
	}

	private int computeDistance( final char[][] map, final Pair<Pair<Integer, Integer>, BitSet> src,
			final BitSet dst ) {
		// BFS to find shortest path (unweighted graphs, no need for Dijkstra)
		final Queue<Pair<Pair<Pair<Integer, Integer>, BitSet>, Integer>> queue = new LinkedList<>();
		final Set<Pair<Pair<Integer, Integer>, BitSet>> seen = new HashSet<>();
		// start from source
		queue.add( new Pair<>( src, 0 ) );
		while ( !queue.isEmpty() ) {
			// remove current node
			final var curr = queue.remove();
			final var cell = curr.getFirst();
			final int distance = curr.getSecond();
			// destination found: all keys
			if ( cell.getSecond().equals( dst ) ) {
				return distance;
			}
			for ( final var n : computeNeighbours( cell, map ) ) {
				// first time you see this cell
				if ( seen.add( n ) ) {
					// add cell to the queue, computing distance
					queue.add( new Pair<>( n, distance + 1 ) );
				}
			}
		}
		throw new IllegalStateException();
	}

	private char[][] initialize( final Stream<String> input,
			final List<Pair<Pair<Integer, Integer>, BitSet>> src, final BitSet dst,
			final boolean first ) {

		final Map<Pair<Integer, Integer>, Character> temporaryMap = new HashMap<>();
		final Pair<Integer, Integer> curr = new Pair<>( 0, 0 );
		input.forEach( line -> {
			curr.setFirst( 0 );
			for ( final char c : line.toCharArray() ) {
				if ( c == AT ) {
					src.add( new Pair<>( new Pair<>( curr ), new BitSet() ) );
				}
				temporaryMap.put( new Pair<>( curr ), c );
				curr.setFirst( curr.getFirst() + 1 );
			}
			curr.setSecond( curr.getSecond() + 1 );
		} );

		// destination: all keys
		dst.or( allKeys( temporaryMap ) );

		if ( !first ) {
			final int x = src.get( 0 ).getFirst().getFirst();
			final int y = src.get( 0 ).getFirst().getSecond();
			src.clear();

			// split map in 4 sections
			temporaryMap.put( new Pair<>( x, y ), HASH );
			temporaryMap.put( new Pair<>( x, y - 1 ), HASH );
			temporaryMap.put( new Pair<>( x, y + 1 ), HASH );
			temporaryMap.put( new Pair<>( x - 1, y ), HASH );
			temporaryMap.put( new Pair<>( x + 1, y ), HASH );

			// consider keys in other sections as already collected
			initializeSource( src, temporaryMap, x - 1, y - 1,
					keys( temporaryMap, l -> l <= x - 1, l -> l <= y - 1 ) );
			initializeSource( src, temporaryMap, x - 1, y + 1,
					keys( temporaryMap, l -> l <= x - 1, l -> l >= y + 1 ) );
			initializeSource( src, temporaryMap, x + 1, y + 1,
					keys( temporaryMap, l -> l >= x + 1, l -> l >= y + 1 ) );
			initializeSource( src, temporaryMap, x + 1, y - 1,
					keys( temporaryMap, l -> l >= x + 1, l -> l <= y - 1 ) );
		}
		final int maxX = temporaryMap.keySet()
				.stream()
				.mapToInt( Pair::getFirst )
				.max()
				.orElseThrow();
		final int maxY = temporaryMap.keySet()
				.stream()
				.mapToInt( Pair::getSecond )
				.max()
				.orElseThrow();
		final char[][] map = new char[maxX + 1][maxY + 1];
		for ( final var point : temporaryMap.entrySet() ) {
			map[point.getKey().getFirst()][point.getKey().getSecond()] = point.getValue();
		}
		return map;
	}

	private void initializeSource( final List<Pair<Pair<Integer, Integer>, BitSet>> src,
			final Map<Pair<Integer, Integer>, Character> map, final int x, final int y,
			final BitSet keysToBeCollected ) {
		final var keysCollected = allKeys( map );
		keysCollected.andNot( keysToBeCollected );
		src.add( new Pair<>( new Pair<>( x, y ), keysCollected ) );
		map.put( new Pair<>( x, y ), AT );
	}

	private BitSet allKeys( final Map<Pair<Integer, Integer>, Character> map ) {
		return keys( map, l -> true, l -> true );
	}

	private BitSet keys( final Map<Pair<Integer, Integer>, Character> map, final IntPredicate testX,
			final IntPredicate testY ) {
		return map.entrySet()
				.stream()
				// filter by position
				.filter( n -> testX.test( n.getKey().getFirst() ) )
				.filter( n -> testY.test( n.getKey().getSecond() ) )
				.map( Map.Entry::getValue )
				// filter keys
				.filter( this::isKey )
				.map( this::charToBit )
				// collect into BitSet
				.collect( BitSet::new, BitSet::set, BitSet::or );
	}

	private List<Pair<Pair<Integer, Integer>, BitSet>> computeNeighbours(
			final Pair<Pair<Integer, Integer>, BitSet> cell, final char[][] map ) {
		final var keys = cell.getSecond();
		final int x = cell.getFirst().getFirst();
		final int y = cell.getFirst().getSecond();
		return NEIGHBOURS.stream()
				// add only cells that can be reached, updating keys collected
				.map( pos -> getNeighbour( x + pos.getFirst(), y + pos.getSecond(), map, keys ) )
				.flatMap( Optional::stream )
				.toList();
	}

	private Optional<Pair<Pair<Integer, Integer>, BitSet>> getNeighbour( final int x, final int y,
			final char[][] map, final BitSet keys ) {
		final char c = x < map.length && y < map[0].length ? map[x][y] : HASH;
		if ( !isBlocked( c, keys ) ) {
			final BitSet newKeys;
			if ( isKey( c ) ) {
				// new key collected
				newKeys = new BitSet();
				newKeys.or( keys );
				newKeys.set( charToBit( c ) );
			} else {
				newKeys = keys;
			}
			return Optional.of( new Pair<>( new Pair<>( x, y ), newKeys ) );
		} else {
			return Optional.empty();
		}
	}

	private boolean isBlocked( final char c, final BitSet keys ) {
		return c == HASH || ( isDoor( c ) && !keys.get( charToBit( toLowerCase( c ) ) ) );
	}

	private boolean isKey( final char c ) {
		return isLowerCase( c );
	}

	private boolean isDoor( final char c ) {
		return isUpperCase( c );
	}

	private int charToBit( final char c ) {
		return c - 'a';
	}
}
