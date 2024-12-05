package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

class AoC052024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean printAlreadyOrdered) {
    Multimap<Long, Long> pageToNextPages = HashMultimap.create();
    List<List<Long>> updatesList = new ArrayList<>();
    input.forEach( line -> {
      List<Long> numbers = Utils.toLongList( line );
      if ( line.contains( "|" ) ) {
        pageToNextPages.put( numbers.get( 0 ), numbers.get( 1 ) );
      } else if ( line.contains( "," ) ) {
        updatesList.add( numbers );
      }
    } );

    int result = 0;
    for ( List<Long> update : updatesList ) {
      List<Long> orderedUpdate = new ArrayList<>( update );
      orderedUpdate.sort( getComparator( pageToNextPages ) );
      boolean alreadyOrdered = update.equals( orderedUpdate );
      //for first part, consider update if it was already ordered
      //for second part, consider update if it was incorrectly ordered
      if ( printAlreadyOrdered == alreadyOrdered ) {
        result += orderedUpdate.get( orderedUpdate.size() / 2 );
      }
    }
    return itoa( result );
  }

  private Comparator<Long> getComparator(final Multimap<Long, Long> pageToNextPages) {
    return (a, b) -> {
      if ( a.equals( b ) ) {
        return 0;
      } else {
        return pageToNextPages.get( a ).contains( b ) ? -1 : 1;
      }
    };
  }

}
