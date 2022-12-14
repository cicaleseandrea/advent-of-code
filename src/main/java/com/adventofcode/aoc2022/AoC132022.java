package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Character.isDigit;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

class AoC132022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final List<Pair<Packet, Packet>> packets = getPacketPairs( input.iterator() );

    if ( first ) {
      return itoa( range( 0, packets.size() ).filter(
              i -> packets.get( i ).getFirst().compareTo( packets.get( i ).getSecond() ) < 0 )
          .map( i -> i + 1 ).sum() );
    } else {
      final var dividerOne = new Packet( List.of( new Packet( List.of( new Packet( 2 ) ) ) ) );
      final var dividerTwo = new Packet( List.of( new Packet( List.of( new Packet( 6 ) ) ) ) );

      final var sortedPackets = concat( packets.stream(),
          Stream.of( new Pair<>( dividerOne, dividerTwo ) ) ).flatMap(
          pair -> Stream.of( pair.getFirst(), pair.getSecond() ) ).sorted().toList();

      return itoa(
          (sortedPackets.indexOf( dividerOne ) + 1L) * (sortedPackets.indexOf( dividerTwo ) + 1L) );
    }
  }

  private List<Pair<Packet, Packet>> getPacketPairs(final Iterator<String> iterator) {
    final var list = new ArrayList<Pair<Packet, Packet>>();
    while ( iterator.hasNext() ) {
      list.add( getPacketPair( iterator ) );
    }
    return list;
  }

  private Pair<Packet, Packet> getPacketPair(final Iterator<String> iterator) {
    final var first = getPacket( iterator.next() );
    final var second = getPacket( iterator.next() );
    final var pair = new Pair<>( first, second );
    if ( iterator.hasNext() ) {
      iterator.next();
    }
    return pair;
  }

  private Packet getPacket(final String string) {
    return getNextPacket( string, new AtomicInteger() );
  }

  private Packet getNextPacket(final String string, final AtomicInteger index) {
    List<Packet> list = null;
    while ( index.get() < string.length() ) {
      var c = string.charAt( index.getAndIncrement() );
      if ( c == '[' ) {
        list = new ArrayList<>();
        final var packet = getNextPacket( string, index );
        if ( packet != null ) {
          list.add( packet );
        } else {
          return new Packet( List.of() );
        }
      } else if ( c == ',' ) {
        list.add( getNextPacket( string, index ) );
      } else if ( c == ']' ) {
        return list != null ? new Packet( list ) : null;
      } else {
        int number = 0;
        while ( isDigit( c ) ) {
          number *= 10;
          number += charToInt( c );
          c = string.charAt( index.getAndIncrement() );
        }
        index.decrementAndGet();
        return new Packet( number );
      }
    }

    throw new IllegalStateException();
  }

  private static class Packet implements Comparable<Packet> {

    private Integer number;
    private List<Packet> list;

    public Packet(final int number) {
      checkState();
      this.number = number;
    }

    public Packet(final List<Packet> list) {
      checkState();
      this.list = Objects.requireNonNull( list );
    }

    private void checkState() {
      if ( number != null || list != null ) {
        throw new IllegalStateException( "Packet already initialized" );
      }
    }

    @Override
    public int compareTo(final Packet other) {
      if ( number != null && other.number != null ) {
        return number - other.number;
      } else if ( list != null && other.list != null ) {
        for ( int i = 0; i < list.size() && i < other.list.size(); i++ ) {
          final int comparison = list.get( i ).compareTo( other.list.get( i ) );
          if ( comparison != 0 ) {
            return comparison;
          }
        }
        return list.size() - other.list.size();
      } else {
        final var left = list != null ? this : new Packet( List.of( this ) );
        final var right = other.list != null ? other : new Packet( List.of( other ) );
        return left.compareTo( right );
      }
    }

    @Override
    public String toString() {
      if ( number != null ) {
        return number.toString();
      } else {
        return list.toString();
      }
    }
  }
}
