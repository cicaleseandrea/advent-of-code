package com.adventofcode.aoc2024;


import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

class AoC192024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    List<String> lines = input.toList();
    Set<String> patterns = Utils.splitOnRegex( lines.get( 0 ), ", " ).collect( Collectors.toSet() );
    Map<String, Long> cache = new HashMap<>();
    LongStream arrangements = lines.stream()
        .skip( 2 )
        .mapToLong( design -> countArrangements( design, patterns, cache ) );
    if ( first ) {
      return itoa( arrangements.filter( n -> n > 0 ).count() );
    } else {
      return itoa( arrangements.sum() );
    }
  }

  private long countArrangements(final String design, final Set<String> patterns,
      final Map<String, Long> cache) {
    if ( design.isEmpty() ) {
      return 1;
    }
    if ( cache.containsKey( design ) ) {
      return cache.get( design );
    }
    long count = 0;
    for ( int i = 1; i <= design.length(); i++ ) {
      String left = design.substring( 0, i );
      if ( patterns.contains( left ) ) {
        String right = design.substring( i );
        count += countArrangements( right, patterns, cache );
      }
    }
    cache.put( design, count );
    return count;
  }

}
