package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC042020 implements Solution {

	private static final Pattern PASSPORT_FIELDS_REGEX = Pattern.compile( "[^\\s:]+:[^\\s:]+" );
	private static final Pattern HEIGHT_REGEX = Pattern.compile( "(\\d{2,3})(cm|in)" );
	private static final String YEAR_REGEX = "\\d{4}";
	private static final String PID_REGEX = "\\d{9}";
	private static final String HAIR_REGEX = "#[0-9a-f]{6}";
	private static final Set<String> MANDATORY_FIELDS = Set.of( "byr", "iyr", "eyr", "hgt", "hcl",
			"ecl", "pid" );
	private static final Set<String> VALID_EYES_COLORS = Set.of( "amb", "blu", "brn", "gry", "grn",
			"hzl", "oth" );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		final var passports = getPassports( input );
		return itoa( passports.stream().filter( AoC042020::isValidFirstPart ).count() );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		final var passports = getPassports( input );
		return itoa( passports.stream().filter( AoC042020::isValidSecondPart ).count() );
	}

	private static boolean isValidFirstPart( final Map<String, String> passport ) {
		return MANDATORY_FIELDS.stream().allMatch( passport::containsKey );
	}

	private static boolean isValidSecondPart( final Map<String, String> passport ) {
		return MANDATORY_FIELDS.stream().allMatch( field -> {
			final String value = passport.getOrDefault( field, "" );
			return switch ( field ) {
				case "byr" -> isValidYear( value, 1920, 2002 );
				case "iyr" -> isValidYear( value, 2010, 2020 );
				case "eyr" -> isValidYear( value, 2020, 2030 );
				case "hgt" -> isValidHeight( value );
				case "hcl" -> value.matches( HAIR_REGEX );
				case "ecl" -> VALID_EYES_COLORS.contains( value );
				case "pid" -> value.matches( PID_REGEX );
				default -> throw new IllegalStateException( "Unexpected field: " + field );
			};
		} );
	}

	private static boolean isValidHeight( final String value ) {
		final Matcher matcher = HEIGHT_REGEX.matcher( value );
		if ( matcher.matches() ) {
			final int height = atoi( matcher.group( 1 ) );
			final String unit = matcher.group( 2 );
			return switch ( unit ) {
				case "cm" -> 150 <= height && height <= 193;
				case "in" -> 59 <= height && height <= 76;
				default -> throw new IllegalStateException( "Unexpected value: " + unit );
			};
		} else {
			return false;
		}
	}

	private static boolean isValidYear( final String value, final int min, final int max ) {
		if ( value.matches( YEAR_REGEX ) ) {
			final int year = Integer.parseInt( value );
			return min <= year && year <= max;
		} else {
			return false;
		}
	}

	private static List<Map<String, String>> getPassports( final Stream<String> passports ) {
		final var result = new ArrayList<Map<String, String>>();
		final var remainingPassports = passports.iterator();
		while ( remainingPassports.hasNext() ) {
			result.add( getPassport( remainingPassports ) );
		}
		return result;
	}

	private static Map<String, String> getPassport( final Iterator<String> passports ) {
		final var pairs = new HashMap<String, String>();
		String line;
		while ( passports.hasNext() && !( line = passports.next() ).isEmpty() ) {
			PASSPORT_FIELDS_REGEX.matcher( line )
					.results()
					.map( MatchResult::group )
					.map( s -> s.split( ":" ) )
					.forEach( pair -> pairs.put( pair[0], pair[1] ) );
		}
		return pairs;
	}

}
