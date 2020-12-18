package com.adventofcode.aoc2016;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

class AoC102016 implements Solution {
	private static final String BOT = "bot ";
	private static final String OUTPUT = "output ";

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final var inputList = input.collect( toList() );
		final Map<String, Set<Integer>> bots = new HashMap<>();
		final Map<String, Integer> outputs = new HashMap<>();
		final Map<String, Pair<String, String>> botGives = new HashMap<>();
		final Set<String> complete = new HashSet<>();
		for ( final var line : inputList ) {
			complete.addAll( initialize( line, bots, outputs, botGives ) );
		}

		while ( complete.size() < bots.size() ) {
			final Set<String> lastComplete = new HashSet<>();
			for ( final var bot : complete ) {
				lastComplete.addAll( giveValue( bot, bots, outputs, botGives ) );
			}
			complete.addAll( lastComplete );
		}

		if ( first ) {
			final int a = inputList.size() < 10 ? 3 : 61;
			final int b = inputList.size() < 10 ? 5 : 17;
			return bots.entrySet()
					.stream()
					.filter( bot -> bot.getValue().contains( a ) && bot.getValue().contains( b ) )
					.map( bot -> bot.getKey().substring( BOT.length() ) )
					.findFirst()
					.orElseThrow();
		} else {
			return itoa( IntStream.rangeClosed( 0, 2 )
					.mapToObj( i -> ( OUTPUT + i ) )
					.mapToInt( outputs::get )
					.reduce( 1, ( a, b ) -> a * b ) );
		}
	}

	private Set<String> giveValue( final String bot, final Map<String, Set<Integer>> bots,
			final Map<String, Integer> outputs, final Map<String, Pair<String, String>> botGives ) {
		final Set<String> complete = new HashSet<>();
		final var out = botGives.get( bot );
		if ( out != null ) {
			final var chips = bots.get( bot );
			complete.addAll(
					putValue( out.getFirst(), Collections.min( chips ), bots, botGives, outputs ) );
			complete.addAll( putValue( out.getSecond(), Collections.max( chips ), bots, botGives,
					outputs ) );
		}
		return complete;
	}

	private Set<String> putValue( final String out, final int value,
			final Map<String, Set<Integer>> bots, final Map<String, Pair<String, String>> botGives,
			final Map<String, Integer> outputs ) {
		if ( out.startsWith( BOT ) ) {
			return addChip( out, value, bots, outputs, botGives );
		} else {
			outputs.put( out, value );
			return new HashSet<>();
		}
	}

	private Set<String> addChip( final String bot, final int value,
			final Map<String, Set<Integer>> bots, final Map<String, Integer> outputs,
			final Map<String, Pair<String, String>> botGives ) {
		final Set<String> complete = new HashSet<>();
		final var chips = bots.computeIfAbsent( bot, k -> new HashSet<>() );
		if ( chips.size() < 2 ) {
			chips.add( value );
			if ( chips.size() == 2 ) {
				complete.add( bot );
				complete.addAll( giveValue( bot, bots, outputs, botGives ) );
			}
		}
		return complete;
	}

	private Set<String> initialize( final String line, final Map<String, Set<Integer>> bots,
			final Map<String, Integer> outputs, final Map<String, Pair<String, String>> botGives ) {
		final var words = line.split( " " );
		if ( line.startsWith( BOT ) ) {
			final String bot = words[0] + SPACE + words[1];
			bots.putIfAbsent( bot, new HashSet<>() );
			botGives.put( bot,
					new Pair<>( words[5] + SPACE + words[6], words[10] + SPACE + words[11] ) );
			return new HashSet<>();
		} else {
			return addChip( words[4] + SPACE + words[5], atoi( words[1] ), bots, outputs,
					botGives );
		}
	}

}
