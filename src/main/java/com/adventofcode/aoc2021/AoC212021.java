package com.adventofcode.aoc2021;

import static java.lang.Math.max;
import static java.lang.Math.min;

import static com.adventofcode.utils.Utils.itoa;

import java.util.HashMap;
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

	// used for memoization
	private static final Map<Game, Pair<Long, Long>> CACHE = new HashMap<>();

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		var game = getGame( input.toList() );
		var rolls = 0;
		final var winningValue = 1000;
		while ( game.playerOne.score < winningValue && game.playerTwo.score < winningValue ) {
			final var rollValue = ( ( rolls + 2 ) * 3 ) % 10;
			game = game.rollDie( rollValue );
			rolls += 3;
		}

		return itoa( min( game.playerOne.score, game.playerTwo.score ) * rolls );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var game = getGame( input.toList() );
		final var victories = play( game );

		return itoa( max( victories.getFirst(), victories.getSecond() ) );
	}

	private Pair<Long, Long> play( final Game game ) {
		final var winningValue = 21;
		if ( game.playerOne.score >= winningValue ) {
			CACHE.put( game, new Pair<>( 1L, 0L ) );
		} else if ( game.playerTwo.score >= winningValue ) {
			CACHE.put( game, new Pair<>( 0L, 1L ) );
		}

		if ( CACHE.containsKey( game ) ) {
			return CACHE.get( game );
		}

		var totalVictoriesOne = 0L;
		var totalVictoriesTwo = 0L;
		for ( final var roll : ROLLS_COMBINATIONS.entrySet() ) {
			final var rollValue = roll.getKey();
			final var nextGame = game.rollDie( rollValue );

			final var victories = play( nextGame );
			totalVictoriesOne += roll.getValue() * victories.getFirst();
			totalVictoriesTwo += roll.getValue() * victories.getSecond();
		}

		final var totalVictories = new Pair<>( totalVictoriesOne, totalVictoriesTwo );
		CACHE.put( game, totalVictories );
		return totalVictories;
	}

	private Game getGame( final List<String> input ) {
		final var matcherOne = PLAYER_POSITION_REGEX.matcher( input.get( 0 ) );
		final var matcherTwo = PLAYER_POSITION_REGEX.matcher( input.get( 1 ) );
		if ( !matcherOne.matches() || !matcherTwo.matches() ) {
			throw new IllegalArgumentException();
		}
		final var playerOne = new Player( Integer.parseInt( matcherOne.group( 2 ) ) );
		final var playerTwo = new Player( Integer.parseInt( matcherTwo.group( 2 ) ) );
		return new Game( playerOne, playerTwo );
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

	private record Game(Player playerOne, Player playerTwo, boolean turnOne) {
		Game( Player playerOne, Player playerTwo ) {
			this( playerOne, playerTwo, true );
		}

		Game rollDie( long rollValue ) {
			final Player nextPlayerOne;
			final Player nextPlayerTwo;
			if ( turnOne ) {
				nextPlayerOne = playerOne.rollDie( rollValue );
				nextPlayerTwo = playerTwo;
			} else {
				nextPlayerOne = playerOne;
				nextPlayerTwo = playerTwo.rollDie( rollValue );
			}
			return new Game( nextPlayerOne, nextPlayerTwo, !turnOne );
		}
	}
}
