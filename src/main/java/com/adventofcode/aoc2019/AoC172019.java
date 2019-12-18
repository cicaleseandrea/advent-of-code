package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.LEFT;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.clearScreen;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;

class AoC172019 implements Solution {
	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );

	//3 repeating groups of max 21 chars each (including trailing comma)
	private static final Pattern REGEXP = Pattern.compile(
			"^(.{1,21})(?:\\1)*(.{1,21})(?:\\1|\\2)*(.{1,21})(?:\\1|\\2|\\3)*$" );

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

		final Map<Pair<Long, Long>, Character> grid = initializeGrid( out, first );

		if ( first ) {
			return itoa( computeIntersections( grid ) );
		}

		out.clear();

		//move robot
		final String[] commands = findCommands( findMovements( grid ) );
		Arrays.stream( commands )
				.map( String::chars )
				.flatMap( IntStream::boxed )
				.forEach( c -> in.add( c.longValue() ) );

		//wait for robot to stop
		startComputer( program, first, in, out );

		if ( PRINT ) {
			try {
				long previous = DOT;
				int i = 0;
				for ( Iterator<Long> iterator = out.iterator(); iterator.hasNext(); ) {
					final long current = iterator.next();
					if ( iterator.hasNext() ) {
						System.out.print( (char) current );
					} else {
						System.out.print( "Dust collected: " + current );
					}
					if ( current == ':' || current == '?' ) {
						System.out.println( SPACE + commands[i++] );
						Thread.sleep( 2000 );
					} else if ( current == '\n' && current == previous ) {
						clearScreen();
						Thread.sleep( 100 );
					}
					previous = current;
				}
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}

		return itoa( out.removeLast() );
	}

	private String[] findCommands( final String movements ) {
		//add trailing comma to find repeating groups
		final Matcher match = REGEXP.matcher( movements + "," );
		if ( !match.matches() ) {
			throw new IllegalArgumentException();
		}

		//remove trailing comma from groups
		final String A = match.group( 1 ).replaceAll( ",$", "" );
		final String B = match.group( 2 ).replaceAll( ",$", "" );
		final String C = match.group( 3 ).replaceAll( ",$", "" );

		final String mainRoutine = movements.replaceAll( A, "A" )
				.replaceAll( B, "B" )
				.replaceAll( C, "C" );

		final String[] commands = new String[5];
		commands[0] = mainRoutine + '\n';
		commands[1] = A + '\n';
		commands[2] = B + '\n';
		commands[3] = C + '\n';
		if ( PRINT ) {
			commands[4] = "y\n";
		} else {
			commands[4] = "n\n";
		}
		return commands;
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

	private Map<Pair<Long, Long>, Character> initializeGrid( final BlockingDeque<Long> out,
			final boolean first ) {
		final Map<Pair<Long, Long>, Character> grid = new HashMap<>();
		final Pair<Long, Long> pos = new Pair<>( 0L, 0L );
		Long symbol;
		while ( ( symbol = out.poll() ) != null ) {
			final char c = (char) symbol.intValue();
			grid.put( new Pair<>( pos ), c );
			if ( PRINT && first ) {
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
