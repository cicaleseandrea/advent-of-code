package com.adventofcode.aoc2020;

import static java.util.stream.Collectors.toList;

import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

class AoC162020 implements Solution {
	private static final Pattern FIELDS_REGEX = Pattern.compile(
			"([\\w ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)" );

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
		final Multimap<String, Predicate<Long>> fields = ArrayListMultimap.create();
		final List<List<Long>> tickets = new ArrayList<>();
		final Multimap<String, Integer> fieldsToPos = HashMultimap.create();
		initialize( inputList.iterator(), tickets, fields, fieldsToPos );

		final var invalidValues = scanTickets( tickets, fields, fieldsToPos );
		if ( first ) {
			return itoa( invalidValues.stream().mapToLong( Long::longValue ).sum() );
		}

		while ( fieldsToPos.asMap().values().stream().anyMatch( x -> x.size() > 1 ) ) {
			for ( final var field : fieldsToPos.keySet() ) {
				final var positions = fieldsToPos.get( field );
				if ( positions.size() == 1 ) {
					// remove finalized positions from all fields
					fieldsToPos.entries()
							.removeIf( e -> !e.getKey().equals( field ) && e.getValue()
									.equals( positions.iterator().next() ) );
				}
			}
		}

		final var myTicket = tickets.get( 0 );
		return itoa( fieldsToPos.keySet()
				.stream()
				.filter( field -> inputList.size() > 50 ? field.startsWith(
						"departure" ) : field.startsWith( "class" ) || field.startsWith( "row" ) )
				.mapToLong( field -> myTicket.get( fieldsToPos.get( field ).iterator().next() ) )
				.reduce( 1L, ( a, b ) -> a * b ) );
	}

	private List<Long> scanTickets( final List<List<Long>> tickets,
			final Multimap<String, Predicate<Long>> fields,
			final Multimap<String, Integer> fieldsToPos ) {
		final List<Long> invalidValues = new ArrayList<>();
		for ( final var ticket : tickets ) {
			// check each ticket
			boolean validTicket = true;
			final Multimap<String, Integer> ticketFieldsToPos = HashMultimap.create();
			for ( int i = 0; i < ticket.size(); i++ ) {
				// check each value
				final Long value = ticket.get( i );
				boolean validValue = false;
				for ( final var field : fields.keySet() ) {
					if ( fields.get( field ).stream().anyMatch( x -> x.test( value ) ) ) {
						ticketFieldsToPos.put( field, i );
						validValue = true;
					}
				}
				if ( !validValue ) {
					invalidValues.add( value );
					validTicket = false;
				}
			}
			if ( validTicket ) {
				// for each field keep only the intersection of positions
				ticketFieldsToPos.keySet()
						.forEach( field -> fieldsToPos.get( field )
								.removeIf( position -> !ticketFieldsToPos.get( field )
										.contains( position ) ) );
			}
		}
		return invalidValues;
	}

	private void initialize( final Iterator<String> input, final List<List<Long>> tickets,
			final Multimap<String, Predicate<Long>> fields,
			final Multimap<String, Integer> fieldsToPos ) {
		boolean initFields = true;
		while ( input.hasNext() ) {
			final String line = input.next();
			if ( line.startsWith( "your" ) ) {
				initFields = false;
			}
			if ( initFields ) {
				addField( line, fields, fieldsToPos );
			} else {
				addTicket( line, tickets );
			}
		}

	}

	private void addTicket( String line, final List<List<Long>> tickets ) {
		final var ticket = Utils.toLongList( line );
		if ( !ticket.isEmpty() ) {
			tickets.add( ticket );
		}
	}

	private void addField( final String line, final Multimap<String, Predicate<Long>> fields,
			final Multimap<String, Integer> fieldsToPos ) {
		final var matcher = FIELDS_REGEX.matcher( line );
		if ( matcher.matches() ) {
			final String field = matcher.group( 1 );
			fields.put( field,
					n -> atol( matcher.group( 2 ) ) <= n && n <= atol( matcher.group( 3 ) ) );
			fields.put( field,
					n -> atol( matcher.group( 4 ) ) <= n && n <= atol( matcher.group( 5 ) ) );
			final int fieldsSize = fields.keySet().size();
			for ( final var existingField : fields.keySet() ) {
				fieldsToPos.put( existingField, fieldsSize - 1 );
			}
			fieldsToPos.putAll( field,
					IntStream.range( 0, fieldsSize ).boxed().collect( toList() ) );
		}
	}

}
