package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

class AoC192021 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var scanners = getScanners( input.toList() );
    //keep set of scanners found with position relative to scanner 0
    final var scannersPos = new HashSet<>( Set.of( new Point( 0, 0, 0 ) ) );
    //keep set of beacons found with position relative to scanner 0
    final var beaconsPos = new HashSet<>( scanners.remove( 0 ) );

    for ( int i = 0; !scanners.isEmpty(); i = incrementMod( i, scanners.size() ) ) {
      //foreach scanner not already paired
      //check scanner beacons vs existing beacons
      final var res = getBeacons( scanners.get( i ), beaconsPos );
      if ( res != null ) {
        scanners.remove( i );
        //update scannerPos and beaconsPos with result
        scannersPos.add( res.getFirst() );
        beaconsPos.addAll( res.getSecond() );
      }
    }
    return itoa( first ? beaconsPos.size() : getMaxDistance( scannersPos ) );
  }

  private Pair<Point, Set<Point>> getBeacons(final Set<Point> newBeacons,
      final Set<Point> existingBeacons) {
    return getTransforms().stream()
        //foreach transformation of newBeacons
        .map( transform -> newBeacons.stream().map( transform ).collect( toSet() ) )
        .map( transformedBeacons -> Sets.cartesianProduct( transformedBeacons, existingBeacons )
            .parallelStream()
            //compute difference between all existing beacons and transformed beacons
            .map( pair -> pair.get( 1 ).subtract( pair.get( 0 ) ) )
            //check if there are at least 12 matches with this transformation and difference
            .filter( diff -> matches( transformedBeacons, diff, existingBeacons ) )
            .findAny()
            //compute the resulting positions
            .map( diff -> new Pair<>( diff,
                transformedBeacons.stream().map( p -> p.add( diff ) ).collect( toSet() ) ) ) )
        .filter( Optional::isPresent )
        .findAny()
        .map( Optional::get )
        .orElse( null );
  }

  private boolean matches(final Set<Point> newBeacons, final Point diff,
      final Set<Point> existing) {
    int matches = 0;
    int left = newBeacons.size();
    final var itr = newBeacons.iterator();
    while ( itr.hasNext() && left + matches >= 12 && matches < 12 ) {
      if ( existing.contains( itr.next().add( diff ) ) ) {
        matches++;
      }
      left--;
    }
    return matches >= 12;
  }

  private int getMaxDistance(final Set<Point> scanners) {
    int max = 0;
    for ( final var pair : Sets.combinations( scanners, 2 ) ) {
      final var itr = pair.iterator();
      max = Math.max( max, itr.next().distance( itr.next() ) );
    }
    return max;
  }

  private List<Set<Point>> getScanners(final List<String> input) {
    final var scanners = new LinkedList<Set<Point>>();
    var set = new HashSet<Point>();
    for ( final var line : input ) {
      if ( line.contains( "scanner" ) ) {
        set = new HashSet<>();
        scanners.add( set );
      } else if ( !line.isEmpty() ) {
        final var numbers = Utils.toLongList( line );
        set.add( new Point( numbers.get( 0 ).intValue(), numbers.get( 1 ).intValue(),
            numbers.get( 2 ).intValue() ) );
      }
    }

    return scanners;
  }

  private List<UnaryOperator<Point>> getTransforms() {
    return List.of( p -> p, p -> new Point( p.x, p.z, -p.y ), p -> new Point( -p.z, p.x, -p.y ),
        p -> new Point( -p.x, -p.z, -p.y ), p -> new Point( p.z, -p.x, -p.y ),
        p -> new Point( p.z, -p.y, p.x ), p -> new Point( p.y, p.z, p.x ),
        p -> new Point( -p.z, p.y, p.x ), p -> new Point( -p.y, -p.z, p.x ),
        p -> new Point( -p.y, p.x, p.z ), p -> new Point( -p.x, -p.y, p.z ),
        p -> new Point( p.y, -p.x, p.z ), p -> new Point( -p.z, -p.x, p.y ),
        p -> new Point( p.x, -p.z, p.y ), p -> new Point( p.z, p.x, p.y ),
        p -> new Point( -p.x, p.z, p.y ), p -> new Point( -p.x, p.y, -p.z ),
        p -> new Point( -p.y, -p.x, -p.z ), p -> new Point( p.x, -p.y, -p.z ),
        p -> new Point( p.y, p.x, -p.z ), p -> new Point( p.y, -p.z, -p.x ),
        p -> new Point( p.z, p.y, -p.x ), p -> new Point( -p.y, p.z, -p.x ),
        p -> new Point( -p.z, -p.y, -p.x ) );
  }

  private record Point(int x, int y, int z) {

    Point add(final Point other) {
      return new Point( x + other.x, y + other.y, z + other.z );
    }

    Point subtract(final Point other) {
      return new Point( x - other.x, y - other.y, z - other.z );
    }

    int distance(final Point other) {
      return Math.abs( x - other.x ) + Math.abs( y - other.y ) + Math.abs( z - other.z );
    }
  }
}
