package com.adventofcode.aoc2019;

import static java.lang.Character.isLetter;

import static com.adventofcode.utils.Pair.ZERO;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

class AoC202019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var src = new Pair<>( ZERO, 0L );
		final var dst = new Pair<>( ZERO, 0L );
		final var edges = initialize( input, first, src, dst );
		final long res = computeDistance( edges, src, dst );

		return itoa( res );
	}

	private long computeDistance(
			final Multimap<Pair<Long, Long>, Pair<Pair<Long, Long>, Long>> edges,
			final Pair<Pair<Long, Long>, Long> src, final Pair<Pair<Long, Long>, Long> dst ) {
		//BFS to find shortest path (unweighted graphs, no need for Dijkstra)
		final Queue<Pair<Pair<Long, Long>, Long>> queue = new LinkedList<>();
		final Map<Pair<Pair<Long, Long>, Long>, Long> distances = new HashMap<>();
		//start from source
		queue.add( src );
		distances.put( src, 0L );
		while ( !queue.isEmpty() ) {
			//remove current node
			final var curr = queue.remove();
			if ( curr.equals( dst ) ) {
				return distances.get( curr );
			}
			for ( final var n : edges.get( curr.getFirst() ) ) {
				//compute floor of this cell
				final long floor = curr.getSecond() + n.getSecond();
				if ( floor == -1 ) {
					//this door is blocked
					continue;
				}
				//first time you see this cell
				final var cell = new Pair<>( n.getFirst(), floor );
				if ( !distances.containsKey( cell ) ) {
					//add cell to the queue
					queue.add( cell );
					//add distance from source
					distances.put( cell, distances.get( curr ) + 1 );
				}
			}
		}
		throw new IllegalStateException();
	}

	private Multimap<Pair<Long, Long>, Pair<Pair<Long, Long>, Long>> initialize(
			final Stream<String> input, final boolean first,
			final Pair<Pair<Long, Long>, Long> src, final Pair<Pair<Long, Long>, Long> dst ) {

		final Map<Pair<Long, Long>, Character> map = initializeMap( input );

		final long maxX = map.keySet().stream().mapToLong( Pair::getFirst ).max().orElseThrow();
		final long maxY = map.keySet().stream().mapToLong( Pair::getSecond ).max().orElseThrow();

		final Multimap<Pair<Long, Long>, Pair<Pair<Long, Long>, Long>> edges = HashMultimap.create();
		final Map<String, Pair<Long, Long>> portals = new HashMap<>();
		for ( final var p : map.entrySet() ) {
			//for normal cells
			if ( p.getValue() == DOT ) {
				//add passages
				for ( final var n : computeNeighbours( p.getKey(), map ) ) {
					edges.put( p.getKey(), new Pair<>( n, 0L ) );
					edges.put( n, new Pair<>( p.getKey(), 0L ) );
				}
			} else if ( isLetter( p.getValue() ) ) {
				//add portals
				addPortal( p, map, portals, edges, src, dst, maxX, maxY, first );
			}
		}

		return edges;
	}

	private Map<Pair<Long, Long>, Character> initializeMap( final Stream<String> input ) {
		final Map<Pair<Long, Long>, Character> map = new HashMap<>();

		final Pair<Long, Long> curr = new Pair<>( 0L, 0L );
		input.forEach( line -> {
			curr.setFirst( 0L );
			for ( final Character c : line.toCharArray() ) {
				map.put( new Pair<>( curr.getFirst(), curr.getSecond() ), c );
				curr.setFirst( curr.getFirst() + 1 );
			}
			curr.setSecond( curr.getSecond() + 1 );
		} );
		return map;
	}

	private void addPortal( final Map.Entry<Pair<Long, Long>, Character> p,
			final Map<Pair<Long, Long>, Character> map, final Map<String, Pair<Long, Long>> portals,
			final Multimap<Pair<Long, Long>, Pair<Pair<Long, Long>, Long>> edges,
			final Pair<Pair<Long, Long>, Long> src, final Pair<Pair<Long, Long>, Long> dst,
			final long maxX, final long maxY, final boolean first ) {
		final Pair<Long, Long> pos = p.getKey();
		final Character c = p.getValue();
		final String portal = getPortalName( map, pos, c );
		final var cells = computeNeighbours( pos, map );

		if ( !cells.isEmpty() ) {
			final Pair<Long, Long> cell = cells.get( 0 );
			if ( portals.containsKey( portal ) ) {
				final Pair<Long, Long> in;
				final Pair<Long, Long> out;
				if ( isOutPortal( cell, maxX, maxY ) ) {
					out = cell;
					in = portals.get( portal );
				} else {
					out = portals.get( portal );
					in = cell;
				}
				final long direction = first ? 0 : 1;
				edges.put( in, new Pair<>( out, direction ) );
				edges.put( out, new Pair<>( in, -direction ) );
			} else if ( portal.equals( "AA" ) ) {
				src.setFirst( cell );
				src.setSecond( 0L ); //floor 0
			} else if ( portal.equals( "ZZ" ) ) {
				dst.setFirst( cell );
				dst.setSecond( 0L ); //floor 0
			} else {
				portals.put( portal, cell );
			}
		}

	}

	private boolean isOutPortal( final Pair<Long, Long> cell, final long maxX, final long maxY ) {
		final long x = cell.getFirst();
		final long y = cell.getSecond();
		return x == 2 || y == 2 || x == maxX - 2 || y == maxY - 2;
	}

	private String getPortalName( final Map<Pair<Long, Long>, Character> map,
			final Pair<Long, Long> pos, final Character c ) {

		var n = new Pair<>( pos );

		n.setFirst( pos.getFirst() + 1 );
		if ( isPortal( map, n ) ) {
			return concat( c, map.get( n ) );
		}

		n.setFirst( pos.getFirst() - 1 );
		if ( isPortal( map, n ) ) {
			return concat( map.get( n ), c );
		}

		n.setFirst( pos.getFirst() );

		n.setSecond( pos.getSecond() + 1 );
		if ( isPortal( map, n ) ) {
			return concat( c, map.get( n ) );
		}

		n.setSecond( pos.getSecond() - 1 );
		if ( isPortal( map, n ) ) {
			return concat( map.get( n ), c );
		}

		throw new IllegalStateException();
	}

	private String concat( final Character a, final Character b ) {
		return String.valueOf( a ) + b;
	}

	private List<Pair<Long, Long>> computeNeighbours( final Pair<Long, Long> pos,
			final Map<Pair<Long, Long>, Character> map ) {
		//add all the adjacent cells that can be reached
		final List<Pair<Long, Long>> neighbours = new ArrayList<>();

		var n = new Pair<>( pos );

		n.setFirst( pos.getFirst() + 1 );
		if ( isCell( map, n ) ) {
			neighbours.add( new Pair<>( n ) );
		}

		n.setFirst( pos.getFirst() - 1 );
		if ( isCell( map, n ) ) {
			neighbours.add( new Pair<>( n ) );
		}

		n.setFirst( pos.getFirst() );

		n.setSecond( pos.getSecond() + 1 );
		if ( isCell( map, n ) ) {
			neighbours.add( new Pair<>( n ) );
		}

		n.setSecond( pos.getSecond() - 1 );
		if ( isCell( map, n ) ) {
			neighbours.add( new Pair<>( n ) );
		}

		return neighbours;
	}

	private boolean isCell( final Map<Pair<Long, Long>, Character> map,
			final Pair<Long, Long> n ) {
		return map.getOrDefault( n, HASH ) == DOT;
	}

	private boolean isPortal( final Map<Pair<Long, Long>, Character> map,
			final Pair<Long, Long> n ) {
		return isLetter( map.getOrDefault( n, HASH ) );
	}
}
