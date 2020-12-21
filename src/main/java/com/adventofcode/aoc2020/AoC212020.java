package com.adventofcode.aoc2020;

import static com.adventofcode.utils.Utils.itoa;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

class AoC212020 implements Solution {
	private static final Pattern INGREDIENTS_REGEX = Pattern.compile( "(.*) \\(contains (.*)\\)" );

	@Override
	public String solveFirstPart( final Stream<String> input ) {
		return solve( input, true );
	}

	@Override
	public String solveSecondPart( final Stream<String> input ) {
		return solve( input, false );
	}

	private String solve( final Stream<String> input, final boolean first ) {
		final SetMultimap<String, String> allergenToIngredient = HashMultimap.create();
		final Map<String, String> finalizedAllergens = new HashMap<>();
		final Map<String, Integer> ingredientsCount = new HashMap<>();
		input.map( INGREDIENTS_REGEX::matcher ).filter( Matcher::matches ).forEach( matcher -> {
			final var ingredientsLine = Set.of( matcher.group( 1 ).split( " " ) );
			final var allergensLine = matcher.group( 2 ).split( ", " );
			ingredientsLine.forEach(
					ingredient -> ingredientsCount.merge( ingredient, 1, Integer::sum ) );
			for ( final var allergen : allergensLine ) {
				final var ingredientsWithAllergen = allergenToIngredient.get( allergen );
				if ( ingredientsWithAllergen.isEmpty() ) {
					ingredientsWithAllergen.addAll( ingredientsLine );
				} else {
					ingredientsWithAllergen.removeIf( Predicate.not( ingredientsLine::contains ) );
					ingredientsWithAllergen.removeIf( finalizedAllergens::containsValue );
				}
				finalizeAllergen( allergen, allergenToIngredient, finalizedAllergens );
			}
		} );
		final int numberOfAllergens = allergenToIngredient.keySet().size();
		while ( finalizedAllergens.size() != numberOfAllergens ) {
			for ( final var allergen : allergenToIngredient.keySet() ) {
				finalizeAllergen( allergen, allergenToIngredient, finalizedAllergens );
			}
		}

		if ( first ) {
			return itoa( ingredientsCount.keySet()
					.stream()
					.filter( Predicate.not( finalizedAllergens::containsValue ) )
					.mapToInt( ingredientsCount::get )
					.sum() );
		} else {
			return finalizedAllergens.keySet()
					.stream()
					.sorted()
					.map( finalizedAllergens::get )
					.collect( Collectors.joining( "," ) );
		}
	}

	private void finalizeAllergen( final String allergen,
			final SetMultimap<String, String> allergenToIngredient,
			final Map<String, String> finalizedAllergens ) {
		final var ingredientsWithAllergen = allergenToIngredient.get( allergen );
		if ( ingredientsWithAllergen.size() == 1 ) {
			final String finalizedIngredient = ingredientsWithAllergen.iterator().next();
			finalizedAllergens.put( allergen, finalizedIngredient );
			allergenToIngredient.entries()
					.removeIf( e -> !e.getKey().equals( allergen ) && e.getValue()
							.equals( finalizedIngredient ) );
		}
	}
}
