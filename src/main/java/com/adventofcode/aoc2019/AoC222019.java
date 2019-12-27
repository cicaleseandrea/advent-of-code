package com.adventofcode.aoc2019;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC222019 implements Solution {

	private BigInteger deckSize;
	private BigInteger positionResult;
	private BigInteger head;
	private BigInteger increment;

	private void deal( final BigInteger n ) {
		//the position of a card is multiplied by n
		positionResult = positionResult.multiply( n ).mod( deckSize );

		//from here: https://www.reddit.com/r/adventofcode/comments/ee0rqi/2019_day_22_solutions/fbnkaju/
		//after "deal n", the ith card becomes the i*nth card
		//card at position 1 after "deal n", was at position 1/n before "deal n"
		//using our two variables to describe its position, that card moved
		//from:
		//head + old_increment/n
		//to:
		//head + new_increment
		//which means that increment changed like this:
		//increment = (increment/n) % size
		increment = increment.multiply( n.modInverse( deckSize ) );

		//head is unchanged
	}

	private void cut( final BigInteger n ) {
		//the position of a card is shifted by n
		positionResult = positionResult.subtract( n ).mod( deckSize );

		//card at position n becomes new head
		head = getCardAtPosition( head, increment, n );

		//increment is unchanged
	}

	private void invert() {
		//the position of a card is mirrored with respect to the middle
		positionResult = positionResult.add( BigInteger.ONE ).negate().mod( deckSize );

		//last card (position -1) becomes new head
		head = getCardAtPosition( head, increment, BigInteger.ONE.negate() );

		//increment is inverted
		increment = increment.negate();
	}

	private BigInteger getCardAtPosition( final BigInteger head, final BigInteger increment,
			final BigInteger position ) {
		return head.add( increment.multiply( position ) ).mod( deckSize );
	}

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true, () -> itoa( positionResult ) );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false, () -> {
			//starting from increment and head that are the result of one shuffle
			//calculate increment and head as a result of many shuffles
			final BigInteger shuffles = BigInteger.valueOf( 101741582076661L );

			//after each shuffle, increment is just multiplied by some constant number
			//incrementShuffles = increment * increment * increment ... * increment (shuffles times)
			final BigInteger incrementShuffles = increment.modPow( shuffles, deckSize );

			//from here: https://www.reddit.com/r/adventofcode/comments/ee0rqi/2019_day_22_solutions/fbnkaju/
			//after each shuffle, head is incremented by some constant multiple of the current increment
			//headShuffles = head*(increment before any shuffle) + head*(increment after 1 shuffle) + head*(increment after 2 shuffles) + ... + head*(increment after shuffles-1 shuffles)
			//headShuffles = head*1 + head*increment + head*increment^2 + ... + head*increment^(shuffles-1)
			//headShuffles = head * (1 + increment + increment^2 + ... + increment^(shuffles-1))
			//using geometric series:
			//headShuffles = head * (1 - increment^shuffles) / (1 - increment)
			final BigInteger headShuffles = head.multiply(
					BigInteger.ONE.subtract( incrementShuffles ) )
					.multiply( BigInteger.ONE.subtract( increment ).modInverse( deckSize ) )
					.mod( deckSize );
			return getCardAtPosition( headShuffles, incrementShuffles,
					BigInteger.valueOf( 2020 ) ).toString();
		} );
	}

	private String solve( final Stream<String> input, final boolean first,
			final Supplier<String> computeResult ) {
		final List<String> lines = input.collect( toList() );
		deckSize = lines.size() < 20 ? BigInteger.TEN : first ? BigInteger.valueOf(
				10007 ) : BigInteger.valueOf( 119315717514047L );
		positionResult = lines.size() < 20 ? BigInteger.valueOf( 9 ) : BigInteger.valueOf( 2019 );
		head = BigInteger.ZERO;
		increment = BigInteger.ONE;
		for ( final String line : lines ) {
			if ( line.startsWith( "cut" ) ) {
				cut( BigInteger.valueOf( extractIntegerFromString( line ) ) );
			} else if ( line.startsWith( "deal into" ) ) {
				invert();
			} else {
				deal( BigInteger.valueOf( extractIntegerFromString( line ) ) );
			}
		}

		return computeResult.get();
	}

}
