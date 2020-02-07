package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.itoa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;

class AoC062015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		//TODO optimize!!
		final Map<Pair<Long, Long>, Long> lights = new HashMap<>();
		input.forEach( line -> {
			final List<Long> numbers = Utils.toLongList( line );
			final Consumer<Pair<Long, Long>> consumer = getConsumer( first, lights, line );
			for ( long y = numbers.get( 1 ); y <= numbers.get( 3 ); y++ ) {
				for ( long x = numbers.get( 0 ); x <= numbers.get( 2 ); x++ ) {
					consumer.accept( new Pair<>( x, y ) );
				}
			}
		} );
		return itoa( lights.values().stream().mapToLong( Long::valueOf ).sum() );
	}

	private Consumer<Pair<Long, Long>> getConsumer( final boolean first,
			final Map<Pair<Long, Long>, Long> lights, final String line ) {
		if ( line.startsWith( "toggle" ) ) {
			if ( first ) {
				return ( pos ) -> toggle( lights, pos );
			} else {
				return ( pos ) -> increase( lights, pos, 2L );
			}
		} else {
			final boolean off = line.contains( "off" );
			if ( first ) {
				return ( pos ) -> set( lights, pos, off ? 0L : 1L );
			} else {
				return ( pos ) -> increase( lights, pos, off ? -1L : 1L );
			}
		}
	}

	private void set( final Map<Pair<Long, Long>, Long> lights, final Pair<Long, Long> pos,
			final Long onOff ) {
		lights.put( pos, onOff );
	}

	private void toggle( final Map<Pair<Long, Long>, Long> lights, final Pair<Long, Long> pos ) {
		lights.merge( pos, 1L, ( old, val ) -> old ^ val );
	}

	private void increase( final Map<Pair<Long, Long>, Long> lights, final Pair<Long, Long> pos,
			final Long num ) {
		lights.merge( pos, Math.max( 0, num ), ( old, val ) -> Math.max( 0, old + num ) );
	}
}
