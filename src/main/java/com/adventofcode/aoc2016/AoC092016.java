package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.POSITIVE_LONG_PATTERN;
import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC092016 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return itoa( computeLength( Utils.getFirstString( input ), false ) );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return itoa( computeLength( Utils.getFirstString( input ), true ) );
	}

	private static long computeLength( final String compressed, final boolean recursion ) {
		long decompressedTotalLength = 0;
		int compressedSubseqLength = 0;
		int times = 0;
		boolean decompress = false;
		final var matcher = POSITIVE_LONG_PATTERN.matcher( compressed );
		int i = 0;
		while ( i < compressed.length() ) {
			if ( decompress ) {
				decompress = false;
				final String compressedSubseq = compressed.substring( i,
						i + compressedSubseqLength );
				final long decompressedSubseqLength = recursion ? computeLength( compressedSubseq,
						true ) : compressedSubseqLength;
				decompressedTotalLength += decompressedSubseqLength * times;
				i += compressedSubseqLength;
			} else if ( compressed.charAt( i ) != '(' ) {
				decompressedTotalLength++;
				i++;
			} else {
				decompress = true;
				compressedSubseqLength = matcher.find( i ) ? atoi( matcher.group() ) : 0;
				i = matcher.end() + 1;
				times = matcher.find( i ) ? atoi( matcher.group() ) : 0;
				i = matcher.end() + 1;
			}
		}

		return decompressedTotalLength;
	}

}
