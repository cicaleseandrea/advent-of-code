package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.LEFT;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.clearScreen;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;

class AoC172019 implements Solution {
	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );

	//@formatter:off
	private static final Map<Direction, UnaryOperator<Pair<Long, Long>>> MOVE_POSITION = Map.of(
			UP, pos -> new Pair<>( pos.getFirst(), pos.getSecond() - 1 ),
			DOWN, pos -> new Pair<>( pos.getFirst(), pos.getSecond() + 1 ),
			LEFT, pos -> new Pair<>( pos.getFirst() - 1, pos.getSecond() ),
			RIGHT, pos -> new Pair<>( pos.getFirst() + 1, pos.getSecond() ) );
	//@formatter:on

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final BlockingQueue<Long> in = new LinkedBlockingQueue<>();
		final BlockingDeque<Long> out = new LinkedBlockingDeque<>();
		final List<Long> program = toLongList( getFirstString( input ) );
		startComputer( program, true, in, out );

		final Map<Pair<Long, Long>, Character> grid = initializeGrid( out );

		if ( first ) {
			return itoa( computeIntersections( grid ) );
		}

		//move robot
		final String movements = findMovements( grid );
		//L,4,L,4,L,10,R,4,R,4,L,4,L,4,R,8,R,10,L,4,L,4,L,10,R,4,R,4,L,10,R,10,L,4,L,4,L,10,R,4,R,4,L,10,R,10,R,4,L,4,L,4,R,8,R,10,R,4,L,10,R,10,R,4,L,10,R,10,R,4,L,4,L,4,R,8,R,10
		//TODO find commands programmatically
		String commands = "A,C,A,B,A,B,C,B,B,C\nL,4,L,4,L,10,R,4\nR,4,L,10,R,10\nR,4,L,4,L,4,R,8,R,10\n";
		if ( PRINT ) {
			commands += "y\n";
		} else {
			commands += "n\n";
		}
		commands.chars().forEach( c -> in.add( (long) c ) );
		out.clear();

		//wait for robot to stop
		startComputer( program, first, in, out );

		if ( PRINT ) {
			long previous = DOT;
			for ( final long current : out ) {
				System.out.print( (char) current );
				if ( current == '\n' && current == previous ) {
					clearScreen();
					try {
						Thread.sleep( 100 );
					} catch ( InterruptedException e ) {
						e.printStackTrace();
					}
				}
				previous = current;
			}
		}

		return itoa( out.removeLast() );
	}

	private String findMovements( final Map<Pair<Long, Long>, Character> grid ) {
		final StringBuilder sb = new StringBuilder();

		Pair<Long, Long> droid = new Pair<>( grid.entrySet()
				.stream()
				.filter( p -> Direction.fromSymbol( p.getValue() ) != null )
				.map( Map.Entry::getKey )
				.findFirst()
				.orElseThrow() );

		Direction direction = Direction.fromSymbol( grid.get( droid ) );
		boolean stop = false;
		long steps = 0;

		while ( !stop ) {
			if ( grid.getOrDefault( MOVE_POSITION.get( direction ).apply( droid ), DOT ) == HASH ) {
				//move forward
				droid = MOVE_POSITION.get( direction ).apply( droid );
				steps++;
			} else {
				if ( steps != 0 ) {
					sb.append( "," ).append( steps );
				}
				steps = 0;
				if ( grid.getOrDefault(
						MOVE_POSITION.get( direction.rotateClockwise() ).apply( droid ),
						DOT ) == HASH ) {
					direction = direction.rotateClockwise();
					sb.append( ",R" );
				} else if ( grid.getOrDefault(
						MOVE_POSITION.get( direction.rotateCounterClockwise() ).apply( droid ),
						DOT ) == HASH ) {
					direction = direction.rotateCounterClockwise();
					sb.append( ",L" );
				} else {
					stop = true;
				}
			}
		}
		return sb.deleteCharAt( 0 ).toString();
	}

	private Map<Pair<Long, Long>, Character> initializeGrid( final BlockingDeque<Long> out ) {
		final Map<Pair<Long, Long>, Character> grid = new HashMap<>();
		final Pair<Long, Long> pos = new Pair<>( 0L, 0L );
		Long symbol;
		while ( ( symbol = out.poll() ) != null ) {
			final char c = (char) symbol.intValue();
			grid.put( new Pair<>( pos ), c );
			if ( PRINT ) {
				System.out.print( c );
			}
			//move right
			pos.setFirst( pos.getFirst() + 1 );
			if ( symbol == '\n' ) {
				//move to new line
				pos.setFirst( 0L );
				pos.setSecond( pos.getSecond() + 1 );
			}
		}
		return grid;
	}

	private long computeIntersections( final Map<Pair<Long, Long>, Character> grid ) {
		long res = 0L;
		for ( final var point : grid.keySet() ) {
			if ( isIntersection( point, grid ) ) {
				res += ( point.getFirst() * point.getSecond() );
			}
		}
		return res;
	}

	private boolean isIntersection( final Pair<Long, Long> point,
			final Map<Pair<Long, Long>, Character> grid ) {

		final var neighbours = List.of( point,
				new Pair<>( point.getFirst() - 1, point.getSecond() ),
				new Pair<>( point.getFirst() + 1, point.getSecond() ),
				new Pair<>( point.getFirst(), point.getSecond() - 1 ),
				new Pair<>( point.getFirst(), point.getSecond() + 1 ) );

		return neighbours.stream()
				.map( p -> grid.getOrDefault( p, DOT ) )
				.filter( c -> c != HASH )
				.findAny()
				.isEmpty();
	}

	private void startComputer( final List<Long> program, final boolean first,
			final BlockingQueue<Long> in, final BlockingDeque<Long> out ) {
		if ( !first ) {
			program.set( 0, 2L );
		}
		final Computer2019 computer = new Computer2019( in, out );
		computer.loadProgram( program );
		try {
			computer.runAsync().get();
		} catch ( InterruptedException | ExecutionException e ) {
			e.printStackTrace();
		}
	}
}
