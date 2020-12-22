package com.adventofcode.aoc2020;

import static java.util.stream.Collectors.toCollection;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC222020 implements Solution {

	private static final int DECK_A = 1;
	private static final int DECK_B = 2;

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private static String solve( final Stream<String> input, final boolean first ) {
		final var it = input.iterator();
		final var deckA = getDeck( it );
		final var deckB = getDeck( it );
		final var winner = playGame( deckA, deckB, !first );
		return itoa( computeScore( getWinnerArg( winner, deckA, deckB ) ) );
	}

	private static int playGame( final Deque<Integer> deckA, final Deque<Integer> deckB,
			final boolean recursive ) {
		final var roundsA = new HashSet<>();
		final var roundsB = new HashSet<>();
		boolean newRounds = true;
		int winner;
		while ( !deckA.isEmpty() && !deckB.isEmpty() && ( !recursive || ( newRounds = roundsA.add(
				new LinkedList<>( deckA ) ) && roundsB.add( new LinkedList<>( deckB ) ) ) ) ) {
			final int cardA = deckA.poll();
			final int cardB = deckB.poll();
			if ( recursive && deckA.size() >= cardA && deckB.size() >= cardB ) {
				winner = playGame(
						deckA.stream().limit( cardA ).collect( toCollection( LinkedList::new ) ),
						deckB.stream().limit( cardB ).collect( toCollection( LinkedList::new ) ),
						true );
			} else {
				winner = cardA > cardB ? DECK_A : DECK_B;
			}
			final var winnerDeck = getWinnerArg( winner, deckA, deckB );
			winnerDeck.add( getWinnerArg( winner, cardA, cardB ) );
			winnerDeck.add( getWinnerArg( winner, cardB, cardA ) );
		}
		return !newRounds || deckB.isEmpty() ? DECK_A : DECK_B;
	}

	private static <T> T getWinnerArg( final int winner, final T a, final T b ) {
		return winner == DECK_A ? a : b;
	}

	private static int computeScore( final Collection<Integer> deck ) {
		int score = 0;
		int mul = deck.size();
		for ( final int card : deck ) {
			score += card * mul;
			mul--;
		}
		return score;
	}

	private static Deque<Integer> getDeck( final Iterator<String> cards ) {
		final Deque<Integer> deck = new LinkedList<>();
		cards.next();
		String card;
		while ( cards.hasNext() && !( card = cards.next() ).isEmpty() ) {
			deck.add( atoi( card ) );
		}
		return deck;
	}

}
