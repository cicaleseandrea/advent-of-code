package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Pair.ZERO;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.EMPTY;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;

class AoC202019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final Pair<Pair<Long, Long>, Long> src = new Pair<>( ZERO, 0L );
		final Pair<Pair<Long, Long>, Long> dst = new Pair<>( ZERO, 0L );
		final var edges = initializeGrid( input, first, src, dst );
		final long res = computeDistance( edges, src, dst );

		return itoa( res );
	}

	private long computeDistance(
			final Multimap<Pair<Long, Long>, Pair<Pair<Long, Long>, Long>> edges,
			final Pair<Pair<Long, Long>, Long> src, final Pair<Pair<Long, Long>, Long> dst ) {
		//BFS to find shortest path (unweighted graphs, no need for Dijkstra)
		final Queue<Pair<Pair<Long, Long>, Long>> queue = new LinkedList<>();
		final Map<Pair<Pair<Long, Long>, Long>, Long> distances = new HashMap<>();
		final Set<Pair<Pair<Long, Long>, Long>> seen = new HashSet<>();
		//start from source
		queue.add( src );
		seen.add( src );
		distances.put( src, 0L );
		while ( !queue.isEmpty() ) {
			//remove current node
			final var curr = queue.remove();
			if ( curr.equals( dst ) ) {
				return distances.get( curr );
			}
			for ( final var n : edges.get( curr.getFirst() ) ) {
				//compute floor of this cell
				final Long floor = curr.getSecond() + n.getSecond();
				if ( floor == -1 ) {
					//this door is blocked
					continue;
				}
				//first time you see this cell
				final var cell = new Pair<>( n.getFirst(), floor );
				if ( !seen.contains( cell ) ) {
					//add cell to the queue
					queue.add( cell );
					//cell was discovered
					seen.add( cell );
					//add distance from source
					distances.put( cell, distances.get( curr ) + 1 );
				}
			}
		}
		throw new IllegalStateException();
	}

	private Multimap<Pair<Long, Long>, Pair<Pair<Long, Long>, Long>> initializeGrid(
			final Stream<String> input, final boolean first,
			final Pair<Pair<Long, Long>, Long> src, final Pair<Pair<Long, Long>, Long> dst ) {
		final Multimap<Pair<Long, Long>, Pair<Pair<Long, Long>, Long>> edges = HashMultimap.create();
		final ListMultimap<String, Pair<Long, Long>> portals = ArrayListMultimap.create();
		final Map<Pair<Long, Long>, Character> map = new HashMap<>();

		long y = 0;
		for ( final String line : input.collect( Collectors.toList() ) ) {
			long x = 0;
			for ( final Character c : line.toCharArray() ) {
				map.put( new Pair<>( x, y ), c );
				x++;
			}
			y++;
		}

		for ( final var p : map.entrySet() ) {
			//for normal cells
			if ( p.getValue() == DOT ) {
				//add passages
				for ( final var n : computeNeighbours( p.getKey(), map ) ) {
					edges.put( p.getKey(), new Pair<>( n, 0L ) );
					edges.put( n, new Pair<>( p.getKey(), 0L ) );
				}
			} else if ( Character.isLetter( p.getValue() ) ) {
				addPortal( p, map, portals, src, dst );
			}
		}

		for ( final var portal : portals.keySet() ) {
			if ( portals.get( portal ).size() > 1 ) {
				final Pair<Long, Long> a = portals.get( portal ).get( 0 );
				final Pair<Long, Long> b = portals.get( portal ).get( 1 );
				final Pair<Long, Long> in;
				final Pair<Long, Long> out;
				if ( a.getFirst() == 2 || a.getSecond() == 2 || a.getFirst() == 122 || a.getSecond() == 128 ) {// TODO find last two programmatically!
					in = b;
					out = a;
				} else {
					in = a;
					out = b;
				}
				final long direction = first ? 0 : 1;
				edges.put( in, new Pair<>( out, direction ) );
				edges.put( out, new Pair<>( in, -direction ) );
			}
		}

		return edges;
	}

	private void addPortal( final Map.Entry<Pair<Long, Long>, Character> c,
			final Map<Pair<Long, Long>, Character> map,
			final Multimap<String, Pair<Long, Long>> portals,
			final Pair<Pair<Long, Long>, Long> src, final Pair<Pair<Long, Long>, Long> dst ) {
		String portal = EMPTY;
		final var pos = c.getKey();

		var n = new Pair<>( pos.getFirst() + 1, pos.getSecond() );
		if ( isLetter( map, n ) ) {
			portal = concat( c.getValue(), map.get( n ) );
		}

		n = new Pair<>( pos.getFirst() - 1, pos.getSecond() );
		if ( isLetter( map, n ) ) {
			portal = concat( map.get( n ), c.getValue() );
		}

		n = new Pair<>( pos.getFirst(), pos.getSecond() + 1 );
		if ( isLetter( map, n ) ) {
			portal = concat( c.getValue(), map.get( n ) );
		}

		n = new Pair<>( pos.getFirst(), pos.getSecond() - 1 );
		if ( isLetter( map, n ) ) {
			portal = concat( map.get( n ), c.getValue() );
		}

		final var cells = computeNeighbours( c.getKey(), map );
		if ( cells.size() == 1 ) {
			final Pair<Long, Long> cell = cells.get( 0 );
			portals.put( portal, cell );
			if ( portal.equals( "AA" ) ) {
				src.setFirst( new Pair<>( cell.getFirst(), cell.getSecond() ) );
				src.setSecond( 0L ); //floor 0
			} else if ( portal.equals( "ZZ" ) ) {
				dst.setFirst( new Pair<>( cell.getFirst(), cell.getSecond() ) );
				dst.setSecond( 0L ); //floor 0
			}
		}

	}

	private String concat( final Character a, final Character b ) {
		return String.valueOf( a ) + b;
	}

	private List<Pair<Long, Long>> computeNeighbours( final Pair<Long, Long> pos,
			final Map<Pair<Long, Long>, Character> map ) {
		final List<Pair<Long, Long>> neighbours = new ArrayList<>();
		//add all the adjacent cells that can be reached
		var n = new Pair<>( pos.getFirst() + 1, pos.getSecond() );
		if ( isReachable( map, n ) ) {
			neighbours.add( n );
		}

		n = new Pair<>( pos.getFirst() - 1, pos.getSecond() );
		if ( isReachable( map, n ) ) {
			neighbours.add( n );
		}

		n = new Pair<>( pos.getFirst(), pos.getSecond() + 1 );
		if ( isReachable( map, n ) ) {
			neighbours.add( n );
		}

		n = new Pair<>( pos.getFirst(), pos.getSecond() - 1 );
		if ( isReachable( map, n ) ) {
			neighbours.add( n );
		}

		return neighbours;
	}

	private boolean isReachable( final Map<Pair<Long, Long>, Character> map,
			final Pair<Long, Long> n ) {
		return map.getOrDefault( n, HASH ) == DOT;
	}

	private boolean isLetter( final Map<Pair<Long, Long>, Character> map,
			final Pair<Long, Long> n ) {
		return Character.isLetter( map.getOrDefault( n, HASH ) );
	}
}
