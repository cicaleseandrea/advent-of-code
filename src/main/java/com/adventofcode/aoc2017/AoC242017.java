package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.HashSet;
import java.util.stream.Stream;

class AoC242017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final Multimap<Integer, Component> ports = HashMultimap.create();
    input.map( Utils::toPositiveLongList )
        .map( numbers -> new Component( numbers.get( 0 ).intValue(), numbers.get( 1 ).intValue() ) )
        .forEach( c -> {
          ports.put( c.portA, c );
          ports.put( c.portB, c );
        } );
    return itoa( getMaxStrength( ports, first ) );
  }

  private long getMaxStrength(final Multimap<Integer, Component> ports, final boolean first) {
    return getMax( 0, ports, first ).getFirst();
  }

  private Pair<Long, Long> getMax(final int in, Multimap<Integer, Component> ports,
      final boolean first) {
    var maxStrength = 0L;
    var maxLength = 0L;
    for ( final var comp : new HashSet<>( ports.get( in ) ) ) {
      final var out = comp.portA == in ? comp.portB : comp.portA;
      ports.get( in ).remove( comp );
      ports.get( out ).remove( comp );
      final var curr = getMax( out, ports, first );
      //update strength
      curr.setFirst( curr.getFirst() + comp.portA + comp.portB );
      //update length
      curr.setSecond( curr.getSecond() + 1 );
      //compute max
      if ( (first || curr.getSecond() == maxLength) && curr.getFirst() > maxStrength ) {
        maxStrength = curr.getFirst();
      } else if ( !first && curr.getSecond() > maxLength ) {
        maxLength = curr.getSecond();
        maxStrength = curr.getFirst();
      }
      ports.put( in, comp );
      ports.put( out, comp );
    }
    return new Pair<>( maxStrength, maxLength );
  }

  private record Component(int portA, int portB) {

  }
}
