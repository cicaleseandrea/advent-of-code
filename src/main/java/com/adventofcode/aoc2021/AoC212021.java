package com.adventofcode.aoc2021;

import static java.lang.Math.max;
import static java.lang.Math.min;

import static com.adventofcode.utils.Utils.itoa;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC212021 implements Solution {
	private static final Pattern PLAYER_POSITION_REGEX = Pattern.compile(
			"Player (\\d) starting position: (\\d+)" );
	private static final Map<Integer, Integer> ROLLS_COMBINATIONS = Map.of( 3, 1, 4, 3, 5, 6, 6, 7,
			7, 6, 8, 3, 9, 1 );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var players = getPlayers( input.toList() );
		var playerOne = players.getFirst();
		var playerTwo = players.getSecond();

		var rolls = 0;
		var turnOne = true;
		final var winningValue = 1000;
		while ( playerOne.score < winningValue && playerTwo.score < winningValue ) {
			final var rollValue = ( ( rolls + 2 ) * 3 ) % 10;
			if ( turnOne ) {
				playerOne = playerOne.rollDie( rollValue );
			} else {
				playerTwo = playerTwo.rollDie( rollValue );
			}
			turnOne = !turnOne;
			rolls += 3;
		}
		return itoa( min( playerOne.score, playerTwo.score ) * rolls );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var players = getPlayers( input.toList() );
		var playerOne = players.getFirst();
		var playerTwo = players.getSecond();

		final var victories = play( playerOne, playerTwo, true );

		return itoa( max( victories.getFirst(), victories.getSecond() ) );
	}

	private Pair<Long, Long> play( final Player playerOne, final Player playerTwo,
			final boolean turnOne ) {
		final var winningValue = 21;
		if ( playerOne.score >= winningValue ) {
			return new Pair<>( 1L, 0L );
		} else if ( playerTwo.score >= winningValue ) {
			return new Pair<>( 0L, 1L );
		}

		final var totalVictories = new Pair<>( 0L, 0L );
		for ( final var roll : ROLLS_COMBINATIONS.entrySet() ) {
			final var rollValue = roll.getKey();
			var nextPlayerOne = playerOne;
			var nextPlayerTwo = playerTwo;
			if ( turnOne ) {
				nextPlayerOne = playerOne.rollDie( rollValue );
			} else {
				nextPlayerTwo = playerTwo.rollDie( rollValue );
			}

			final var victories = play( nextPlayerOne, nextPlayerTwo, !turnOne );
			totalVictories.setFirst(
					totalVictories.getFirst() + roll.getValue() * victories.getFirst() );
			totalVictories.setSecond(
					totalVictories.getSecond() + roll.getValue() * victories.getSecond() );
		}
		return totalVictories;
	}

	private Pair<Player, Player> getPlayers( final List<String> input ) {
		final var matcherOne = PLAYER_POSITION_REGEX.matcher( input.get( 0 ) );
		final var matcherTwo = PLAYER_POSITION_REGEX.matcher( input.get( 1 ) );
		if ( !matcherOne.matches() || !matcherTwo.matches() ) {
			throw new IllegalArgumentException();
		}
		final var playerOne = new Player( Integer.parseInt( matcherOne.group( 2 ) ) );
		final var playerTwo = new Player( Integer.parseInt( matcherTwo.group( 2 ) ) );
		return new Pair<>( playerOne, playerTwo );
	}

	private record Player(long position, long score) {
		Player( long position ) {
			this( position, 0 );
		}

		Player rollDie( long rollValue ) {
			var nextPosition = ( position + rollValue ) % 10;
			if ( nextPosition == 0 ) {
				nextPosition = 10;
			}
			return new Player( nextPosition, score + nextPosition );
		}
	}
}
