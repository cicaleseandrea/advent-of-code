package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AoC082020 implements Solution {

	private static final Pattern INSTRUCTION_REGEX = Pattern.compile( "([a-z]{3}) ([+-]\\d+)" );
	private static final String ACC = "acc";
	private static final String JMP = "jmp";
	private static final String NOP = "nop";

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var instructions = input.collect( Collectors.toList() );
		return itoa( compute( instructions, true ).orElseThrow() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var instructions = input.collect( Collectors.toList() );
		Optional<Integer> accumulator = Optional.empty();
		int posLastChange = 0;
		boolean revert = false;
		while ( accumulator.isEmpty() ) {
			posLastChange = modifyInstruction( instructions, posLastChange, revert );
			accumulator = compute( instructions, false );
			revert = true;
		}
		return itoa( accumulator.orElseThrow() );
	}

	private int modifyInstruction( final List<String> instructions, int pos,
			final boolean revert ) {
		if ( revert ) {
			changeOperation( instructions, pos );
			pos++;
		}
		while ( isAcc( instructions.get( pos ) ) ) {
			pos++;
		}
		changeOperation( instructions, pos );
		return pos;
	}

	private void changeOperation( final List<String> instructions, final int pos ) {
		final String instruction = instructions.get( pos );
		final String replacement = switch ( instruction.charAt( 0 ) ) {
			case 'j' -> instruction.replace( JMP, NOP );
			case 'n' -> instruction.replace( NOP, JMP );
			default -> throw new IllegalStateException();
		};
		instructions.set( pos, replacement );
	}

	private Optional<Integer> compute( final List<String> instructions, final boolean first ) {
		final Set<Integer> seen = new HashSet<>();
		int accumulator = 0;
		int pos = 0;
		while ( pos < instructions.size() && seen.add( pos ) ) {
			final Matcher instruction = getInstruction( instructions, pos );
			final String operation = instruction.group( 1 );
			final int argument = atoi( instruction.group( 2 ) );
			switch ( operation ) {
				case ACC -> accumulator += argument;
				case JMP -> pos += ( argument - 1 );
				default -> {/*nop*/}
			}
			pos++;
		}
		if ( first || pos == instructions.size() ) {
			return Optional.of( accumulator );
		} else {
			return Optional.empty();
		}
	}

	private Matcher getInstruction( final List<String> instructions, final int pos ) {
		final Matcher instruction = INSTRUCTION_REGEX.matcher( instructions.get( pos ) );
		if ( !instruction.matches() ) {
			throw new IllegalArgumentException();
		}
		return instruction;
	}

	private boolean isAcc( final String instruction ) {
		return instruction.charAt( 0 ) == 'a';
	}

}
