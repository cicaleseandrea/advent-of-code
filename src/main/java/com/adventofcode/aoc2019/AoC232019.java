package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;

class AoC232019 implements Solution {

	private static final boolean PRINT = Boolean.parseBoolean( System.getProperty( "print" ) );

	private static final int COMPUTERS = 50;
	private static final int NAT = 255;

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final List<Long> program = toLongList( getFirstString( input ) );
		final Map<Integer, BlockingQueue<Long>> in = new HashMap<>();
		final List<BlockingQueue<Long>> out = new ArrayList<>();
		final List<Computer2019> computers = new ArrayList<>();

		for ( int i = 0; i < COMPUTERS; i++ ) {
			//initialize computer
			in.put( i, new LinkedBlockingQueue<>() );
			out.add( new LinkedBlockingQueue<>() );
			computers.add( new Computer2019( program, in.get( i ), out.get( i ) ) );
			//start computer
			in.get( i ).add( (long) i );
			computers.get( i ).runAsync();
		}

		//initialize NAT
		in.put( NAT, new LinkedBlockingQueue<>() );

		long res = runComputers( in, out, computers, first );

		return itoa( res );
	}

	private long runComputers( final Map<Integer, BlockingQueue<Long>> receivers,
			final List<BlockingQueue<Long>> senders, final List<Computer2019> computers,
			final boolean first ) {
		try {
			long lastYSent = 0L;
			while ( true ) {
				//always send a value to potentially unblock computers
				IntStream.range( 0, COMPUTERS )
						.parallel()
						.forEach( to -> receivers.get( to ).add( -1L ) );

				boolean idle = true;
				for ( int from = 0; from < COMPUTERS; from++ ) {
					final BlockingQueue<Long> sender = senders.get( from );
					final Integer to = Optional.ofNullable( sender.poll() )
							.map( Long::intValue )
							.orElse( null );
					if ( to != null ) {
						idle = false; //a value has been sent
						final BlockingQueue<Long> receiver = receivers.get( to );
						final long x = sender.take();
						final long y = sender.take();
						if ( to == NAT ) {
							if ( first ) {
								return y;
							} else {
								receiver.clear(); //stores only latest packet
							}
						}
						dispatchPacket( from, to, receiver, x, y );
					}
				}

				//no value can be sent or received
				if ( idle && isIdle( receivers, senders ) ) {
					final BlockingQueue<Long> sender = receivers.get( NAT );
					final BlockingQueue<Long> receiver = receivers.get( 0 );
					final long x = sender.take();
					final long y = sender.take();
					dispatchPacket( NAT, 0, receiver, x, y );
					if ( lastYSent == y ) {
						return y;
					} else {
						lastYSent = y;
					}
				}

			}
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}
		return 0L;
	}

	private void dispatchPacket( final int from, final int to, final BlockingQueue<Long> receiver,
			final long x, final long y ) {
		receiver.add( x );
		receiver.add( y );
		printPacket( from, to, x, y );
	}

	private void printPacket( final int from, final int to, final long x, final long y ) {
		if ( PRINT ) {
			if ( from == NAT ) {
				System.out.println( "IDLE" );
			}
			System.out.println( "from: " + from + " to: " + to + " x: " + x + " y: " + y );
		}
	}

	private boolean isIdle( final Map<Integer, BlockingQueue<Long>> in,
			final List<BlockingQueue<Long>> out ) {
		return IntStream.range( 0, COMPUTERS )
				.parallel()
				.allMatch( address -> blocked( in.get( address ), out.get( address ) ) );
	}

	private boolean blocked( final BlockingQueue<Long> in, final BlockingQueue<Long> out ) {
		return in.isEmpty() && out.isEmpty();
	}
}
