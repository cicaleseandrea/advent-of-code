package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.abs;
import static java.util.Comparator.comparingLong;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Triplet;
import com.adventofcode.utils.Utils;
import com.google.common.base.MoreObjects;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC202017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var particles = input.map( Utils::toLongList ).map( numbers -> new Particle(
        new Triplet<>( numbers.get( 0 ), numbers.get( 1 ), numbers.get( 2 ) ),
        new Triplet<>( numbers.get( 3 ), numbers.get( 4 ), numbers.get( 5 ) ),
        new Triplet<>( numbers.get( 6 ), numbers.get( 7 ), numbers.get( 8 ) ) ) ).toList();
    if ( first ) {
      //particle with minimum acceleration will stay closest in the long term
      return IntStream.range( 0, particles.size() )
          .mapToObj( i -> new Pair<>( i, particles.get( i ) ) )
          .map( p -> new Pair<>( p.getFirst(), getAbsSum( p.getSecond().acc ) ) )
          .min( comparingLong( Pair::getSecond ) ).map( p -> itoa( p.getFirst() ) ).orElseThrow();
    } else {
      Set<Particle> set = new HashSet<>( particles );
      //simulate enough ticks to find all collisions
      for ( int i = 0; i < 1_000; i++ ) {
        final var next = new HashMap<Particle, Long>();
        for ( final var particle : set ) {
          moveParticle( particle );
          next.merge( particle, 1L, Long::sum );
        }
        next.values().removeIf( n -> n > 1 );
        set = next.keySet();
      }
      return itoa( set.size() );
    }
  }

  private void moveParticle(final Particle particle) {
    final var pos = particle.pos;
    final var vel = particle.vel;
    final var acc = particle.acc;
    vel.setFirst( vel.getFirst() + acc.getFirst() );
    vel.setSecond( vel.getSecond() + acc.getSecond() );
    vel.setThird( vel.getThird() + acc.getThird() );
    pos.setFirst( pos.getFirst() + vel.getFirst() );
    pos.setSecond( pos.getSecond() + vel.getSecond() );
    pos.setThird( pos.getThird() + vel.getThird() );
  }

  private long getAbsSum(final Triplet<Long, Long, Long> t) {
    return abs( t.getFirst() ) + abs( t.getSecond() ) + abs( t.getThird() );
  }

  private record Particle(Triplet<Long, Long, Long> pos, Triplet<Long, Long, Long> vel,
                          Triplet<Long, Long, Long> acc) {

    //two particles are considered equal if they occupy the same position
    @Override
    public boolean equals(Object o) {
      if ( this == o ) {
        return true;
      }
      if ( o == null || getClass() != o.getClass() ) {
        return false;
      }
      Particle particle = (Particle) o;
      return pos.equals( particle.pos );
    }

    @Override
    public int hashCode() {
      return Objects.hash( pos );
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper( this ).add( "pos", pos ).add( "vel", vel )
          .add( "acc", acc ).toString();
    }
  }
}
