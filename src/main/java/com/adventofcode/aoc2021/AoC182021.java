package com.adventofcode.aoc2021;

import static java.lang.Math.max;
import static java.util.Optional.ofNullable;

import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntPredicate;
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
		final var numbers = input.map( this::parseNumber ).toList();
		var maxMagnitude = 0;
		for ( final var left : numbers ) {
			for ( final var right : numbers ) {
				final var sum = sum( new Tree( left ), new Tree( right ) );
				maxMagnitude = max( maxMagnitude, getMagnitude( sum ) );
			}
		}
		return itoa( maxMagnitude );
	}

	private Tree sum( final Tree left, final Tree right ) {
		// addition
		final var sum = new Tree( left, right );
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
		final var split = getLeftmost( number, n -> n >= 10 );

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
		if ( number == null || ( levels == 0 && number.value != null ) ) {
			// not a pair
			return null;
		} else if ( levels == 0 ) {
			// it's a pair. explode
			number.value = 0;
			return number;
		}

		final boolean explodedLeft;
		var exploded = explode( number.left, levels - 1 );
		if ( exploded != null ) {
			explodedLeft = true;
		} else {
			explodedLeft = false;
			exploded = explode( number.right, levels - 1 );
		}

		if ( exploded != null ) {
			if ( ( explodedLeft && exploded.right != null ) || ( !explodedLeft && exploded.left != null ) ) {
				final var nextNumber = explodedLeft ? getLeftmost( number.right ) : getRightmost(
						number.left );
				nextNumber.value += explodedLeft ? exploded.right.value : exploded.left.value;
				exploded.right = explodedLeft ? null : exploded.right;
				exploded.left = explodedLeft ? exploded.left : null;
			}
		}
		return exploded;
	}

	private Tree getOuter( final Tree number, final IntPredicate accept, final boolean left ) {
		if ( number == null ) {
			return null;
		} else if ( number.value != null ) {
			return accept.test( number.value ) ? number : null;
		}

		final var outer = getOuter( left ? number.left : number.right, accept, left );
		if ( outer != null ) {
			return outer;
		} else {
			return getOuter( left ? number.right : number.left, accept, left );
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
		return getLeftmost( number, n -> true );
	}

	private Tree getRightmost( final Tree number ) {
		return getOuter( number, n -> true, false );
	}

	private Tree getLeftmost( final Tree number, final IntPredicate accept ) {
		return getOuter( number, accept, true );
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

		Tree( final Tree tree ) {
			this( ofNullable( tree.left ).map( Tree::new ).orElse( null ),
					ofNullable( tree.right ).map( Tree::new ).orElse( null ), tree.value );
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
