package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.manhattanDistance;
import static com.adventofcode.utils.Utils.toLongList;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Comparator.comparingInt;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.base.MoreObjects;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

class AoC152022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var sensors = input.map( this::getSensor ).toList();
    int y = first ? (sensors.size() < 20 ? 10 : 2_000_000) : 0;
    final var size = sensors.size() < 20 ? 20 : 4_000_000;
    while ( y <= size ) {
      //coverage ranges in row y for all sensors, sorted
      final var sortedRanges = getSortedRanges( sensors, y );
      //non-overlapping coverage ranges in row y
      final var mergedRanges = mergeRanges( sortedRanges );
      if ( first ) {
        //total sensors coverage in row y
        int result = mergedRanges.stream().mapToInt( this::getRangeSize ).sum();
        //remove known beacons in row y
        final var finalY = y;
        result -= sensors.stream().map( Sensor::getBeacon )
            .filter( beacon -> beacon.getSecond() == finalY ).distinct().count();
        return itoa( result );
      } else if ( mergedRanges.size() > 1 ) {
        //missing coverage in row y
        final var x = mergedRanges.get( 0 ).getSecond() + 1;
        return itoa( x * 4_000_000L + y );
      }
      y++;
    }
    throw new IllegalStateException();
  }

  private List<Pair<Integer, Integer>> getSortedRanges(final List<Sensor> sensors, final int y) {
    final var ranges = new ArrayList<Pair<Integer, Integer>>();
    for ( final var sensor : sensors ) {
      final var range = getRange( sensor, y );
      if ( range != null ) {
        ranges.add( range );
      }
    }
    ranges.sort( comparingInt( Pair::getFirst ) );
    return ranges;
  }

  private List<Pair<Integer, Integer>> mergeRanges(
      final Iterable<Pair<Integer, Integer>> sortedRanges) {
    final var mergedRanges = new LinkedList<Pair<Integer, Integer>>();
    final var iterator = sortedRanges.iterator();
    mergedRanges.add( iterator.next() );
    while ( iterator.hasNext() ) {
      final var prev = mergedRanges.removeLast();
      final var curr = iterator.next();
      if ( overlap( prev, curr ) ) {
        mergedRanges.add( mergeRanges( prev, curr ) );
      } else {
        mergedRanges.add( prev );
        mergedRanges.add( curr );
      }
    }
    return mergedRanges;
  }

  private Pair<Integer, Integer> getRange(final Sensor sensor, final int y) {
    final var diffY = abs( sensor.getPosition().getSecond() - y );
    final var diffX = sensor.getRadius() - diffY;
    if ( diffX >= 0 ) {
      final var x = sensor.getPosition().getFirst();
      return new Pair<>( x - diffX, x + diffX );
    } else {
      return null;
    }
  }

  private boolean overlap(final Pair<Integer, Integer> a, final Pair<Integer, Integer> b) {
    return !(a.getFirst() > b.getSecond() + 1 || b.getFirst() > a.getSecond() + 1);
  }

  private Pair<Integer, Integer> mergeRanges(final Pair<Integer, Integer> a,
      final Pair<Integer, Integer> b) {
    return new Pair<>( min( a.getFirst(), b.getFirst() ), max( a.getSecond(), b.getSecond() ) );
  }

  private int getRangeSize(final Pair<Integer, Integer> range) {
    return range.getSecond() - range.getFirst() + 1;
  }

  private Sensor getSensor(final String line) {
    final var numbers = toLongList( line );
    return new Sensor( new Pair<>( numbers.get( 0 ).intValue(), numbers.get( 1 ).intValue() ),
        new Pair<>( numbers.get( 2 ).intValue(), numbers.get( 3 ).intValue() ) );
  }

  private static class Sensor {

    private final Pair<Integer, Integer> position;
    private final Pair<Integer, Integer> beacon;
    private final int radius;

    private Sensor(final Pair<Integer, Integer> position, final Pair<Integer, Integer> beacon) {
      this.position = position;
      this.beacon = beacon;
      this.radius = (int) manhattanDistance( position.getFirst(), position.getSecond(),
          beacon.getFirst(), beacon.getSecond() );
    }

    public Pair<Integer, Integer> getPosition() {
      return position;
    }

    public Pair<Integer, Integer> getBeacon() {
      return beacon;
    }

    public int getRadius() {
      return radius;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper( this ).add( "position", position ).add( "beacon", beacon )
          .add( "radius", radius ).toString();
    }
  }
}
