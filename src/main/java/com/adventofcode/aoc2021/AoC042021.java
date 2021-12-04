package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toLongList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.base.MoreObjects;

class AoC042021 implements Solution {

	private static final int BOARD_SIZE = 5;

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var bingoInput = getBingoInput( input.toList() );
		final var numbers = bingoInput.getFirst();
		final var boards = bingoInput.getSecond();

		long score = 0;
		for ( final long number : numbers ) {
			for ( final Iterator<Board> iterator = boards.iterator(); iterator.hasNext(); ) {
				final var board = iterator.next();
				if ( board.hasWon( number ) ) {
					iterator.remove();
					score = number * board.sumUnmarked();
					if ( first ) {
						return itoa( score );
					}
				}
			}
		}
		return itoa( score );
	}

	private Pair<List<Long>, List<Board>> getBingoInput( final List<String> input ) {
		final var numbers = toLongList( input.get( 0 ) );
		final var boards = new ArrayList<Board>();
		for ( int i = 2; i < input.size(); i += BOARD_SIZE + 1 ) {
			boards.add( new Board( input, i ) );
		}
		return new Pair<>( numbers, boards );
	}

	private static class Board {
		final List<List<Long>> rows = new ArrayList<>();
		final List<List<Long>> columns = new ArrayList<>();

		public Board( final List<String> input, final int position ) {
			for ( int i = 0; i < BOARD_SIZE; i++ ) {
				final var row = toLongList( input.get( position + i ) );
				rows.add( row );
			}
			for ( int i = 0; i < BOARD_SIZE; i++ ) {
				final var column = new ArrayList<Long>();
				for ( final var row : rows ) {
					column.add( row.get( i ) );
				}
				columns.add( column );
			}
		}

		public boolean hasWon( final long number ) {
			return checkVictory( rows, number ) | checkVictory( columns, number );
		}

		private boolean checkVictory( final Collection<? extends Collection<Long>> board,
				final long number ) {
			for ( final var numbers : board ) {
				if ( numbers.remove( number ) ) {
					return numbers.isEmpty();
				}
			}
			return false;
		}

		public long sumUnmarked() {
			return rows.stream().flatMap( Collection::stream ).mapToLong( Long::longValue ).sum();
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper( this )
					.add( "rows", rows )
					.add( "columns", columns )
					.toString();
		}
	}
}
