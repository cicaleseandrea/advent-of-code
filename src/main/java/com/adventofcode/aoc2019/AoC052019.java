package com.adventofcode.aoc2019;

import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.toLongList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Computer2019;

class AoC052019 implements Solution {

	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, (byte) '1' );
	}

	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, (byte) '5' );
	}

	public String solve( final Stream<String> input, final byte inputNumber ) {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final InputStream in = new ByteArrayInputStream( new byte[] { inputNumber } );

		final Computer2019 computer = new Computer2019( true, in, out );
		final List<Long> program = toLongList( getFirstString( input ) );
		computer.loadProgram( program );
		computer.run();

		final BufferedReader reader = new BufferedReader(
				new InputStreamReader( new ByteArrayInputStream( out.toByteArray() ) ) );
		return reader.lines().reduce( ( a, b ) -> b ).orElseThrow(); //get last line
	}

}
