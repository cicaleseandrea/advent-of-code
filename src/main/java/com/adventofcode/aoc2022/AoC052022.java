package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.toLongList;
import static java.lang.Character.isAlphabetic;
import static java.util.stream.LongStream.rangeClosed;

import com.adventofcode.Solution;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AoC052022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var iterator = input.iterator();
    final var stacks = getStacks( iterator );

    while ( iterator.hasNext() ) {
      final var numbers = toLongList( iterator.next() );
      final var cratesNumber = numbers.get( 0 );
      final var from = stacks.get( numbers.get( 1 ) );
      final var to = stacks.get( numbers.get( 2 ) );
      final var tmp = new LinkedList<Character>();
      if ( !first ) {
        for ( int i = 0; i < cratesNumber; i++ ) {
          tmp.push( from.pop() );
        }
      }
      for ( int i = 0; i < cratesNumber; i++ ) {
        to.push( first ? from.pop() : tmp.pop() );
      }
    }

    return rangeClosed( 1, stacks.size() ).mapToObj( stacks::get ).map( Deque::pop )
        .map( Object::toString ).collect( Collectors.joining() );
  }

  private Map<Long, Deque<Character>> getStacks(final Iterator<String> iterator) {
    final Map<Long, Deque<Character>> stacks = new HashMap<>();
    String line;
    do {
      line = iterator.next();
      for ( int i = 1; i < line.length(); i += 4 ) {
        final var c = line.charAt( i );
        if ( isAlphabetic( c ) ) {
          stacks.computeIfAbsent( (i / 4L) + 1, k -> new LinkedList<>() ).add( c );
        }
      }
    } while ( !line.isBlank() );
    return stacks;
  }

}
