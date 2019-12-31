package com.adventofcode.aoc2019;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import static com.adventofcode.utils.Utils.AT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.LongPredicate;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC182019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final List<Pair<Pair<Long, Long>, Set<Character>>> sources = new ArrayList<>();
		final Set<Character> dst = new HashSet<>();
		final var map = initialize( input, sources, dst, first );
		long res = 0;
		for ( final var src : sources ) {
			res += computeDistance( map, src, dst );
		}

		return itoa( res );
	}

	private long computeDistance( final Map<Pair<Long, Long>, Character> map,
			final Pair<Pair<Long, Long>, Set<Character>> src, final Set<Character> dst ) {
		//BFS to find shortest path (unweighted graphs, no need for Dijkstra)
		final Queue<Pair<Pair<Long, Long>, Set<Character>>> queue = new LinkedList<>();
		final Map<Pair<Pair<Long, Long>, Set<Character>>, Long> distances = new HashMap<>();
		//start from source
		queue.add( src );
		distances.put( src, 0L );
		while ( !queue.isEmpty() ) {
			//remove current node
			final var curr = queue.remove();
			if ( curr.getSecond().equals( dst ) ) {
				return distances.get( curr );
			}
			for ( final var n : computeNeighbours( curr, map ) ) {
				//first time you see this cell
				if ( !distances.containsKey( n ) ) {
					//add cell to the queue
					queue.add( n );
					//add distance from source
					distances.put( n, distances.get( curr ) + 1 );
				}
			}
		}
		throw new IllegalStateException();
	}

	private Map<Pair<Long, Long>, Character> initialize( final Stream<String> input,
			final List<Pair<Pair<Long, Long>, Set<Character>>> src, final Set<Character> dst,
			final boolean first ) {

		final Map<Pair<Long, Long>, Character> map = new HashMap<>();
		final Pair<Long, Long> curr = new Pair<>( 0L, 0L );
		input.forEach( line -> {
			curr.setFirst( 0L );
			for ( final Character c : line.toCharArray() ) {
				if ( c == AT ) {
					src.add( new Pair<>( new Pair<>( curr.getFirst(), curr.getSecond() ),
							new HashSet<>() ) );
				}
				map.put( new Pair<>( curr.getFirst(), curr.getSecond() ), c );
				curr.setFirst( curr.getFirst() + 1 );
			}
			curr.setSecond( curr.getSecond() + 1 );
		} );

		dst.addAll( allKeys( map ) );

		//split map in 4 sections. consider keys in other section as already taken
		if ( !first ) {
			final long x = src.get( 0 ).getFirst().getFirst();
			final long y = src.get( 0 ).getFirst().getSecond();
			src.clear();

			map.put( new Pair<>( x, y ), HASH );
			map.put( new Pair<>( x, y - 1 ), HASH );
			map.put( new Pair<>( x, y + 1 ), HASH );
			map.put( new Pair<>( x - 1, y ), HASH );
			map.put( new Pair<>( x + 1, y ), HASH );

			initializeSource( src, map, x - 1, y - 1, l -> l <= x - 1, l -> l <= y - 1 );
			initializeSource( src, map, x - 1, y + 1, l -> l <= x - 1, l -> l >= y + 1 );
			initializeSource( src, map, x + 1, y + 1, l -> l >= x + 1, l -> l >= y + 1 );
			initializeSource( src, map, x + 1, y - 1, l -> l >= x + 1, l -> l <= y - 1 );
		}

		return map;
	}

	private void initializeSource( final List<Pair<Pair<Long, Long>, Set<Character>>> src,
			final Map<Pair<Long, Long>, Character> map, final long x, final long y,
			final LongPredicate testX, final LongPredicate testY ) {
		final var startingKeys = allKeys( map );
		startingKeys.removeAll( keys( map, testX, testY ) );
		src.add( new Pair<>( new Pair<>( x, y ), startingKeys ) );
		map.put( new Pair<>( x, y ), AT );
	}

	private Set<Character> allKeys( final Map<Pair<Long, Long>, Character> map ) {
		return keys( map, l -> true, l -> true );
	}

	private Set<Character> keys( final Map<Pair<Long, Long>, Character> map,
			final LongPredicate testX, final LongPredicate testY ) {
		return map.entrySet()
				.stream()
				.filter( n -> testX.test( n.getKey().getFirst() ) )
				.filter( n -> testY.test( n.getKey().getSecond() ) )
				.map( Map.Entry::getValue )
				.filter( this::isKey )
				.collect( toSet() );
	}

	private List<Pair<Pair<Long, Long>, Set<Character>>> computeNeighbours(
			final Pair<Pair<Long, Long>, Set<Character>> cell,
			final Map<Pair<Long, Long>, Character> map ) {
		//add all the adjacent cells that can be reached, updating keys
		var keys = cell.getSecond();

		return Stream.of( new Pair<>( cell.getFirst().getFirst() + 1, cell.getFirst().getSecond() ),
				new Pair<>( cell.getFirst().getFirst() - 1, cell.getFirst().getSecond() ),
				new Pair<>( cell.getFirst().getFirst(), cell.getFirst().getSecond() - 1 ),
				new Pair<>( cell.getFirst().getFirst(), cell.getFirst().getSecond() + 1 ) )
				.map( pos -> getNeighbour( pos, map, keys ) )
				.flatMap( Optional::stream )
				.collect( toList() );
	}

	private Optional<Pair<Pair<Long, Long>, Set<Character>>> getNeighbour(
			final Pair<Long, Long> pos, final Map<Pair<Long, Long>, Character> map,
			final Set<Character> keys ) {
		final Character c = map.getOrDefault( pos, HASH );
		if ( !isBlocked( c, keys ) ) {
			final Set<Character> newKeys;
			if ( isKey( c ) ) {
				//new key obtained
				newKeys = new HashSet<>( keys );
				newKeys.add( c );
			} else {
				newKeys = keys;
			}
			return Optional.of( new Pair<>( pos, newKeys ) );
		} else {
			return Optional.empty();
		}
	}

	private boolean isBlocked( final Character c, final Set<Character> keys ) {
		return c == HASH || ( isDoor( c ) && !keys.contains( toLowerCase( c ) ) );
	}

	private boolean isKey( final Character c ) {
		return isLowerCase( c );
	}

	private boolean isDoor( final Character c ) {
		return isUpperCase( c );
	}
}
