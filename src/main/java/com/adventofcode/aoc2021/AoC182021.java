package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC182021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var numbers = input.toList();
		var max = Long.MIN_VALUE;
		for ( int i = 0; i < numbers.size(); i++ ) {
			for ( int j = i + 1; j < numbers.size(); j++ ) {
				var result = new Tree( parseNumber( numbers.get( i ) ),
						parseNumber( numbers.get( j ) ) );
				Optional<Tree> reduced;
				do {
					// reduction
					reduced = reduce( result );
				} while ( reduced.isPresent() );
				long magnitude = magnitude( result );
				if ( magnitude >= max ) {
					max = magnitude;
				}
				result = new Tree( parseNumber( numbers.get( j ) ),
						parseNumber( numbers.get( i ) ) );
				do {
					// reduction
					reduced = reduce( result );
				} while ( reduced.isPresent() );
				magnitude = magnitude( result );
				if ( magnitude >= max ) {
					max = magnitude;
				}
			}
		}
		return itoa( max );
	}

	private String solve( final Stream<String> input ) {
		final var numbers = input.map( this::parseNumber ).toList();

		var result = numbers.get( 0 );
		for ( final var number : numbers.subList( 1, numbers.size() ) ) {
			// addition
			result = new Tree( result, number );
			Optional<Tree> reduced;
			do {
				// reduction
				reduced = reduce( result );
			} while ( reduced.isPresent() );
		}
		final var magnitude = magnitude( result );
		return itoa( magnitude );
	}

	private long magnitude( final Tree result ) {
		if ( result == null ) {
			return 0;
		} else if ( result.value != null ) {
			return result.value;
		}
		return 3 * magnitude( result.left ) + 2 * magnitude( result.right );
	}

	private Optional<Tree> reduce( final Tree number ) {
		// explode
		final Tree exploded = explode( number, 4 );
		if ( exploded != null ) {
			return Optional.of( exploded );
		}
		// split
		return Optional.ofNullable( split( number ) );
	}

	private Tree split( final Tree number ) {
		var split = leftmost( number.left, 10 );
		split = ( split != null ) ? split : leftmost( number.right, 10 );
		if ( split != null ) {
			split.left = new Tree( ( split.value / 2 ) );
			split.right = new Tree( (int) Math.ceil( (double) split.value / 2 ) );
			split.value = null;
		}
		return split;
	}

	private Tree explode( final Tree number, final int levels ) {
		if ( number == null ) {
			return null;
		} else if ( levels == 0 ) {
			if ( number.value == null ) {
				number.value = 0;
				return number;
			}
			return null;
		}

		var explode = explode( number.left, levels - 1 );
		if ( explode != null ) {
			if ( explode.right != null ) {
				final var firstRight = leftmost( number.right );
				firstRight.value += explode.right.value;
				explode.right = null;
			}
			return explode;
		}
		explode = explode( number.right, levels - 1 );
		if ( explode != null ) {
			if ( explode.left != null ) {
				final var leftRight = rightmost( number.left );
				leftRight.value += explode.left.value;
				explode.left = null;
			}
			return explode;
		}
		return null;
	}

	private Tree leftmost( final Tree number ) {
		return leftmost( number, 0 );
	}

	private Tree leftmost( final Tree number, final int min ) {
		if ( number == null ) {
			return null;
		} else if ( number.value != null ) {
			return number.value >= min ? number : null;
		}
		final var left = leftmost( number.left, min );
		if ( left != null ) {
			return left;
		} else {
			return leftmost( number.right, min );
		}
	}

	private Tree rightmost( final Tree number ) {
		if ( number == null || number.value != null ) {
			return number;
		}
		final var right = rightmost( number.right );
		if ( right != null ) {
			return right;
		} else {
			return rightmost( number.left );
		}
	}

	private Tree parseNumber( final String line ) {
		return parseNumber( line, new AtomicInteger() );
	}

	private Tree parseNumber( final String line, final AtomicInteger index ) {
		Tree right = null;
		Tree left = null;
		while ( index.get() < line.length() ) {
			final char c = line.charAt( index.getAndIncrement() );
			switch ( c ) {
			case '[' -> left = parseNumber( line, index );
			case ',' -> right = parseNumber( line, index );
			case ']' -> {
				return new Tree( left, right );
			}
			default -> {
				return new Tree( charToInt( c ) );
			}
			}
		}
		throw new IllegalStateException();
	}

	private static final class Tree {
		Tree left;
		Tree right;
		Integer value;

		private Tree( Tree left, Tree right, Integer value ) {
			this.left = left;
			this.right = right;
			this.value = value;
		}

		Tree( final Integer number ) {
			this( null, null, number );
		}

		Tree( final Tree left, final Tree right ) {
			this( left, right, null );
		}

		@Override
		public String toString() {
			if ( value != null ) {
				return value.toString();
			} else {
				return "[" + left + "," + right + "]";
			}
		}

		@Override
		public boolean equals( Object obj ) {
			if ( obj == this ) {
				return true;
			}
			if ( obj == null || obj.getClass() != this.getClass() ) {
				return false;
			}
			var that = (Tree) obj;
			return Objects.equals( this.left, that.left ) && Objects.equals( this.right,
					that.right ) && Objects.equals( this.value, that.value );
		}

		@Override
		public int hashCode() {
			return Objects.hash( left, right, value );
		}

	}

}
