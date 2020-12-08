package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.itoa;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;

class AoC122015 implements Solution {

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var json = new JSONArray( "[" + Utils.getFirstString( input ) + "]" );
		return itoa( countNumbers( json, val -> false ) );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var json = new JSONArray( "[" + Utils.getFirstString( input ) + "]" );
		return itoa( countNumbers( json, "red"::equals ) );
	}

	private int countNumbers( final Object obj, final Predicate<Object> exclude ) {
		if ( obj instanceof JSONArray jsonArray ) {
			int count = 0;
			for ( final Object o : jsonArray ) {
				count += countNumbers( o, exclude );
			}
			return count;
		} else if ( obj instanceof JSONObject jsonObject ) {
			int count = 0;
			for ( final String key : jsonObject.keySet() ) {
				final Object value = jsonObject.get( key );
				if ( exclude.test( value ) ) {
					return 0;
				} else {
					count += countNumbers( value, exclude );
				}
			}
			return count;
		} else if ( obj instanceof Integer num ) {
			return num;
		}
		return 0;
	}

}
