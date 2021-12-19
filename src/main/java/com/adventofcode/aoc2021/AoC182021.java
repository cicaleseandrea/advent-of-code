package com.adventofcode.aoc2021;

import static java.lang.Math.max;

import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC182021 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var sum = input.map( this::parseNumber ).reduce( this::sum ).orElseThrow();
		return itoa( getMagnitude( sum ) );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var numbers = input.toList();
		var maxMagnitude = 0;
		for ( final String number1 : numbers ) {
			for ( final String number2 : numbers ) {
				final var sum = sum( parseNumber( number1 ), parseNumber( number2 ) );
				maxMagnitude = max( maxMagnitude, getMagnitude( sum ) );
			}
		}
		return itoa( maxMagnitude );
	}

	private Tree sum( final Tree number1, final Tree number2 ) {
		// addition
		final var sum = new Tree( number1, number2 );
		while ( reduce( sum ) ) {
			// reduction
		}
		return sum;
	}

	private int getMagnitude( final Tree number ) {
		if ( number == null ) {
			return 0;
		} else if ( number.value != null ) {
			return number.value;
		} else {
			return 3 * getMagnitude( number.left ) + 2 * getMagnitude( number.right );
		}
	}

	private boolean reduce( final Tree number ) {
		return explode( number ) || split( number );
	}

	private boolean split( final Tree number ) {
		final var split = getLeftmost( number, 10 );

		if ( split == null ) {
			return false;
		} else {
			split.left = new Tree( split.value / 2 );
			split.right = new Tree( (int) Math.ceil( split.value / 2.0 ) );
			split.value = null;
			return true;
		}
	}

	private boolean explode( final Tree number ) {
		return explode( number, 4 ) != null;
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

		final boolean explodedLeft;
		var exploded = explode( number.left, levels - 1 );
		if ( exploded == null ) {
			explodedLeft = false;
			exploded = explode( number.right, levels - 1 );
		} else {
			explodedLeft = true;
		}

		if ( exploded != null ) {
			if ( ( explodedLeft && exploded.right != null ) || ( !explodedLeft && exploded.left != null ) ) {
				final var first = explodedLeft ? getLeftmost( number.right ) : getRightmost(
						number.left );
				first.value += explodedLeft ? exploded.right.value : exploded.left.value;
				exploded.right = explodedLeft ? null : exploded.right;
				exploded.left = explodedLeft ? exploded.left : null;
			}
		}
		return exploded;
	}

	private Tree getMost( final Tree number, final int min, final boolean left ) {
		if ( number == null ) {
			return null;
		} else if ( number.value != null ) {
			return number.value >= min ? number : null;
		}
		final var most = getMost( left ? number.left : number.right, min, left );
		if ( most != null ) {
			return most;
		} else {
			return getMost( left ? number.right : number.left, min, left );
		}
	}

	private Tree parseNumber( final String expression, final AtomicInteger index ) {
		Tree left = null;
		Tree right = null;
		while ( index.get() < expression.length() ) {
			final var c = expression.charAt( index.getAndIncrement() );
			switch ( c ) {
			case '[' -> left = parseNumber( expression, index );
			case ',' -> right = parseNumber( expression, index );
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

	private Tree parseNumber( final String expression ) {
		return parseNumber( expression, new AtomicInteger() );
	}

	private Tree getLeftmost( final Tree number ) {
		return getLeftmost( number, 0 );
	}

	private Tree getLeftmost( final Tree number, final int min ) {
		return getMost( number, min, true );
	}

	private Tree getRightmost( final Tree number ) {
		return getMost( number, 0, false );
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

		Tree( final Integer value ) {
			this( null, null, value );
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
	}
}
