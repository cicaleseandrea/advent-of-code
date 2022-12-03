package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.itoa;
import static com.google.common.collect.Sets.intersection;
import static java.lang.Character.isLowerCase;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;

class AoC032022 implements Solution {


  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var rucksacks = input.toList();
    final var commonItems = new ArrayList<Character>();
    final var group = first ? 1 : 3;
    for ( int i = group - 1; i < rucksacks.size(); i += group ) {
      if ( first ) {
        final var rucksack = rucksacks.get( i );
        final var left = getItems( rucksack, 0, rucksack.length() / 2 );
        final var right = getItems( rucksack, rucksack.length() / 2, rucksack.length() );
        commonItems.addAll( intersection( left, right ) );
      } else {
        final var rucksack1 = getItems( rucksacks.get( i ) );
        final var rucksack2 = getItems( rucksacks.get( i - 1 ) );
        final var rucksack3 = getItems( rucksacks.get( i - 2 ) );
        commonItems.addAll( intersection( intersection( rucksack1, rucksack2 ), rucksack3 ) );
      }
    }

    return itoa( commonItems.stream().mapToInt( this::getPriority ).sum() );
  }

  private Set<Character> getItems(final String rucksack) {
    return getItems( rucksack, 0, rucksack.length() );
  }

  private Set<Character> getItems(final String rucksack, final long from, final long to) {
    return rucksack.chars().skip( from ).limit( to - from ).mapToObj( c -> (char) c )
        .collect( toSet() );
  }

  private int getPriority(final char item) {
    return isLowerCase( item ) ? item - 'a' + 1 : item - 'A' + 27;
  }

}
