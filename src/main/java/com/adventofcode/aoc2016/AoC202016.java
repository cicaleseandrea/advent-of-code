package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Long.max;
import static java.lang.Long.min;
import static java.util.Comparator.comparingLong;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.LinkedList;
import java.util.stream.Stream;

class AoC202016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var ranges = input.map( this::toPair ).sorted( comparingLong( Pair::getFirst ) ).toList();

    final var mergedRanges = new LinkedList<Pair<Long, Long>>();
    mergedRanges.add( ranges.get( 0 ) );

    for ( int i = 1; i < ranges.size(); i++ ) {
      final var prev = mergedRanges.removeLast();
      final var curr = ranges.get( i );
      if ( overlap( prev, curr ) ) {
        mergedRanges.add( merge( prev, curr ) );
      } else {
        mergedRanges.add( prev );
        mergedRanges.add( curr );
      }
    }

    return itoa( first ? mergedRanges.get( 0 ).getSecond() + 1 : countAllowed( mergedRanges ) );
  }

  private long countAllowed(final Iterable<Pair<Long, Long>> mergedRanges) {
    final var iterator = mergedRanges.iterator();
    long res = 0L;
    var prev = iterator.next();
    while ( iterator.hasNext() ) {
      var curr = iterator.next();
      res += (curr.getFirst() - prev.getSecond() - 1);
      prev = curr;
    }
    return res + 4294967295L - prev.getSecond();
  }

  private Pair<Long, Long> merge(final Pair<Long, Long> a, final Pair<Long, Long> b) {
    return new Pair<>( min( a.getFirst(), b.getFirst() ), max( a.getSecond(), b.getSecond() ) );
  }

  private boolean overlap(final Pair<Long, Long> a, final Pair<Long, Long> b) {
    return !(a.getFirst() > b.getSecond() + 1 || b.getFirst() > a.getSecond() + 1);
  }

  private Pair<Long, Long> toPair(final String s) {
    final var numbers = s.split( "-" );
    return new Pair<>( atol( numbers[0] ), atol( numbers[1] ) );
  }
}
