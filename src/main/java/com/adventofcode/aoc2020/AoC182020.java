package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC182020 implements Solution {

	private static final Map<Character, BinaryOperator<Long>> FUNCTIONS = Map.of( '+',
			( a, b ) -> a + b, '-', ( a, b ) -> a - b, '*', ( a, b ) -> a * b );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var operatorsPrecedence = Map.of( '+', 0, '-', 0, '*', 0 );
		return itoa( input.mapToLong( exp -> computeResult( exp, operatorsPrecedence ) ).sum() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var operatorsPrecedence = Map.of( '+', 0, '-', -1, '*', -2 );
		return itoa( input.mapToLong( exp -> computeResult( exp, operatorsPrecedence ) ).sum() );
	}

	private long computeResult( final String expression,
			final Map<Character, Integer> operatorsPrecedence ) {
		final Deque<Character> opStack = new LinkedList<>();
		final Deque<Long> numStack = new LinkedList<>();
		for ( final char c : expression.toCharArray() ) {
			if ( Character.isDigit( c ) ) {
				numStack.push( (long) charToInt( c ) );
			} else if ( isOperator( c ) ) {
				while ( isOperatorOnTop( opStack ) && operatorsPrecedence.get(
						opStack.peek() ) >= operatorsPrecedence.get( c ) ) {
					evaluate( opStack, numStack );
				}
				opStack.push( c );
			} else if ( c == '(' ) {
				opStack.push( c );
			} else if ( c == ')' ) {
				while ( isOperatorOnTop( opStack ) ) {
					evaluate( opStack, numStack );
				}
				// get rid of '('
				opStack.pop();
			}
		}
		while ( isOperatorOnTop( opStack ) ) {
			evaluate( opStack, numStack );
		}
		return numStack.pop();
	}

	private boolean isOperatorOnTop( final Deque<Character> opStack ) {
		return !opStack.isEmpty() && isOperator( opStack.peek() );
	}

	private boolean isOperator( final char c ) {
		return FUNCTIONS.containsKey( c );
	}

	private void evaluate( final Deque<Character> opStack, final Deque<Long> numStack ) {
		final char op = opStack.pop();
		final long a = numStack.pop();
		final long b = numStack.pop();
		numStack.push( FUNCTIONS.get( op ).apply( a, b ) );
	}

}
