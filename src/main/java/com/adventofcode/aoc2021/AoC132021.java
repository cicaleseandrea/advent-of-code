package com.adventofcode.aoc2021;

import static java.util.stream.Collectors.joining;

import static com.adventofcode.utils.Utils.BLACK;
import static com.adventofcode.utils.Utils.WHITE;
import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC132021 implements Solution {

	private static final Pattern FOLD_REGEX = Pattern.compile( "fold along (\\D)=(\\d+)" );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var instructions = input.iterator();
		final var paper = getPaper( getDots( instructions ) );
		final var origami = fold( paper, instructions, first );

		if ( first ) {
			return itoa( origami.flatMapToInt( String::chars )
					.filter( c -> (char) c == BLACK )
					.count() );
		} else {
			return origami.collect( joining( "\n" ) );
		}
	}

	private Stream<String> fold( final char[][] startingPaper, final Iterator<String> instructions,
			final boolean first ) {
		var paper = startingPaper;
		String line;
		do {
			line = instructions.next();
			final var matcher = FOLD_REGEX.matcher( line );
			if ( !matcher.matches() ) {
				throw new IllegalArgumentException();
			}
			final var axis = matcher.group( 1 );
			final var lineNumber = atoi( matcher.group( 2 ) );
			paper = foldOnce( paper, lineNumber, axis.equals( "y" ) );
		} while ( !first && instructions.hasNext() );

		return Arrays.stream( paper ).map( String::new );
	}

	private char[][] foldOnce( final char[][] paper, final int lineNumber, final boolean foldUp ) {
		final var rows = paper.length;
		final var columns = paper[0].length;
		final var foldedRows = foldUp ? lineNumber : rows;
		final var foldedColumns = foldUp ? columns : lineNumber;

		final var folded = new char[foldedRows][foldedColumns];
		for ( int i = 0; i < foldedRows; i++ ) {
			for ( int j = 0; j < foldedColumns; j++ ) {
				final var overlappedI = foldUp ? rows - i - 1 : i;
				final var overlappedJ = foldUp ? j : columns - j - 1;
				folded[i][j] = mergeOverlappingDots( paper[i][j], paper[overlappedI][overlappedJ] );
			}
		}

		return folded;
	}

	private char[][] getPaper( final Set<Point> dots ) {
		final var maxX = dots.stream().mapToInt( Point::x ).max().orElseThrow();
		final var maxY = dots.stream().mapToInt( Point::y ).max().orElseThrow();
		final var paper = new char[maxY + 1][maxX + 1];
		for ( int i = 0; i < paper.length; i++ ) {
			for ( int j = 0; j < paper[0].length; j++ ) {
				paper[i][j] = dots.contains( new Point( j, i ) ) ? BLACK : WHITE;
			}
		}
		return paper;
	}

	private Set<Point> getDots( final Iterator<String> instructions ) {
		final Set<Point> dots = new HashSet<>();
		var instruction = instructions.next();
		while ( !instruction.isEmpty() ) {
			final var dot = instruction.split( "," );
			dots.add( new Point( atoi( dot[0] ), atoi( dot[1] ) ) );
			instruction = instructions.next();
		}
		return dots;
	}

	private char mergeOverlappingDots( final char a, final char b ) {
		return ( a == BLACK || b == BLACK ) ? BLACK : WHITE;
	}

	private record Point(int x, int y) {}
}
