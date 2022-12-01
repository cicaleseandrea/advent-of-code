package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

class AoC222019 implements Solution {

	private BigInteger cardPosition; //tracks the position of a specific card (handy for part 1, but not necessary)

	//full representation of the deck at any moment in time
	private BigInteger top; //the card on top of the deck
	private BigInteger increment; //the difference between two consecutive cards
	private BigInteger deckSize;

	private void dealIncrement( final BigInteger n ) {
		//the position of a card is multiplied by n
		cardPosition = cardPosition.multiply( n ).mod( deckSize );

		//from here: https://www.reddit.com/r/adventofcode/comments/ee0rqi/2019_day_22_solutions/fbnkaju/
		//after "deal n", the ith card becomes the i*nth card
		//the card at position 1 after "deal n", was at position 1/n before "deal n"
		//using our two variables to describe that card
		//before:
		//card = top + old_increment*old_pos = top + old_increment*1/n = top + old_increment/n
		//after:
		//card = top + new_increment*new_pos = top + new_increment*1 = top + new_increment
		//which means that increment changed like this:
		//new_increment = old_increment/n
		increment = increment.multiply( n.modInverse( deckSize ) );

		//top is unchanged
	}

	private void cut( final BigInteger n ) {
		//the position of a card is shifted by n
		cardPosition = cardPosition.subtract( n ).mod( deckSize );

		//card at position n goes on top of the deck
		top = getCardAtPosition( top, increment, n, deckSize );

		//increment is unchanged
	}

	private void dealNew() {
		//the position of a card is mirrored with respect to the middle
		cardPosition = cardPosition.add( BigInteger.ONE ).negate().mod( deckSize );

		//last card (position - 1) goes on top of the deck
		top = getCardAtPosition( top, increment, BigInteger.ONE.negate(), deckSize );

		//increment is negated so that deck direction is inverted
		increment = increment.negate();
	}

	private static BigInteger getCardAtPosition( final BigInteger top, final BigInteger increment,
			final BigInteger position, BigInteger deckSize ) {
		//count "position" cards starting from top and moving by increment
		return top.add( increment.multiply( position ) ).mod( deckSize );
	}

	public String solveFirstPart( final Stream<String> input ) {
		//we tracked the position of the specific card we wanted
		//(we could also remove that variable and just look for that card in the current deck)
		return solve( input, true, () -> itoa( cardPosition ) );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false, () -> {
			//starting from increment and top that are the result of one shuffle
			//calculate increment and top as a result of many shuffles
			final BigInteger shuffles = BigInteger.valueOf( 101741582076661L );

			//after each shuffle, increment is just multiplied by some constant number
			//incrementShuffles = 1 * increment_const * increment_const * increment_const ... * increment_const (shuffles times)
			final BigInteger incrementShuffles = increment.modPow( shuffles, deckSize );

			//from here: https://www.reddit.com/r/adventofcode/comments/ee0rqi/2019_day_22_solutions/fbnkaju/
			//after each shuffle, top is incremented by some constant multiple of the increment at the beginning of the shuffle
			//topShuffles = 0 + top_const*(increment before any shuffle) + top_const*(increment after 1 shuffle) + top_const*(increment after 2 shuffles) + ... + top_const*(increment after shuffles-1 shuffles)
			//topShuffles = 0 + top_const*1 + top_const*increment_const + top_const*increment_const^2 + ... + top_const*increment_const^(shuffles-1)
			//topShuffles = top_const * (1 + increment_const + increment_const^2 + ... + increment_const^(shuffles-1))
			//using geometric series:
			//topShuffles = top_const * (1 - increment_const^shuffles) / (1 - increment_const)
			final BigInteger topShuffles = top.multiply(
					BigInteger.ONE.subtract( incrementShuffles ) )
					.multiply( BigInteger.ONE.subtract( increment ).modInverse( deckSize ) )
					.mod( deckSize );
			return getCardAtPosition( topShuffles, incrementShuffles, BigInteger.valueOf( 2020 ),
					deckSize ).toString();
		} );
	}

	private String solve( final Stream<String> input, final boolean first,
			final Supplier<String> computeResult ) {
		final List<String> lines = input.toList();
		deckSize = ( lines.size() < 20 ) ? BigInteger.TEN : ( first ? BigInteger.valueOf(
				10007 ) : BigInteger.valueOf( 119315717514047L ) );
		cardPosition = lines.size() < 20 ? BigInteger.valueOf( 9 ) : BigInteger.valueOf( 2019 );
		top = BigInteger.ZERO;
		increment = BigInteger.ONE;
		for ( final String line : lines ) {
			if ( line.startsWith( "cut" ) ) {
				cut( BigInteger.valueOf( extractIntegerFromString( line ) ) );
			} else if ( line.startsWith( "deal with increment" ) ) {
				dealIncrement( BigInteger.valueOf( extractIntegerFromString( line ) ) );
			} else {
				dealNew();
			}
		}

		return computeResult.get();
	}

}
