package com.adventofcode.aoc2024;


import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;

import com.adventofcode.Solution;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AoC232024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    Set<String> computers = new HashSet<>();
    Multimap<String, String> connections = HashMultimap.create();
    input.forEach( pair -> {
      String a = pair.substring( 0, 2 );
      String b = pair.substring( 3, 5 );
      computers.add( a );
      computers.add( b );
      if ( a.compareTo( b ) < 0 ) {
        connections.put( a, b );
      } else {
        connections.put( b, a );
      }
    } );
    if ( first ) {
      return itoa( count3TGroup( computers, connections ) );
    } else {
      return computers.stream()
          .parallel()
          .map( start -> getLargestClique( start, connections ) )
          .max( comparingInt( Set::size ) ).orElseThrow()
          .stream()
          .sorted()
          .collect( joining( "," ) );
    }
  }

  private long count3TGroup(final Set<String> computers,
      final Multimap<String, String> connections) {
    long result = 0;
    for ( final String a : computers ) {
      for ( final String b : connections.get( a ) ) {
        for ( final String c : connections.get( b ) ) {
          if ( connections.containsEntry( a, c )
              && (a.startsWith( "t" ) || b.startsWith( "t" ) || c.startsWith( "t" )) ) {
            result++;
          }
        }
      }
    }
    return result;
  }

  private Set<String> getLargestClique(final String start,
      final Multimap<String, String> connections) {
    Set<String> largestClique = new HashSet<>();
    for ( final String next : connections.get( start ) ) {
      Set<String> largeClique = getLargestClique( next, connections )
          .stream()
          .filter( n -> connections.containsEntry( start, n ) )
          .collect( Collectors.toSet() );
      if ( largeClique.size() > largestClique.size() ) {
        largestClique = largeClique;
      }
    }
    largestClique.add( start );
    return largestClique;
  }
}
