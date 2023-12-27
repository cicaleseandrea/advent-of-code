package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.max;
import static java.util.Comparator.comparingInt;
import static java.util.function.Predicate.not;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class AoC222023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private static String solve(final Stream<String> input, final boolean first) {
    final List<Brick> bricks = input.map( AoC222023::toBrick )
        .sorted( comparingInt( brick -> brick.end.z ) ) //sort by end point z axis
        .toList();
    final List<Brick> settledBricks = fallDown( bricks ).getSecond();
    final Set<Brick> safeToDisintegrate = getSafeToDisintegrate( settledBricks );
    if ( first ) {
      return itoa( safeToDisintegrate.size() );
    }

    final int fallenBricks = settledBricks.stream()
        //this brick would cause other bricks to fall down
        .filter( brick -> !safeToDisintegrate.contains( brick ) )
        //remove brick
        .map( toRemove -> settledBricks.stream().filter( not( toRemove::equals ) ).toList() )
        //simulate fall
        .mapToInt( removed -> fallDown( removed ).getFirst() )
        .sum();
    return itoa( fallenBricks );
  }

  private static Pair<Integer, List<Brick>> fallDown(final List<Brick> bricks) {
    int fallen = 0;
    final List<Brick> settledBricks = new ArrayList<>();
    for ( final Brick brick : bricks ) {
      int newZ = 1;
      for ( final Brick settledBrick : settledBricks ) {
        if ( settledBrick.couldSupport( brick ) ) {
          newZ = max( newZ, settledBrick.end.z + 1 );
        }
      }
      if ( newZ != brick.start.z ) {
        fallen++;
      }
      final Brick fallenBrick = brick.withStartZ( newZ );
      settledBricks.add( fallenBrick );
    }
    return new Pair<>( fallen, settledBricks );
  }

  private static Set<Brick> getSafeToDisintegrate(final List<Brick> bricks) {
    final Set<Brick> safe = new HashSet<>( bricks );
    for ( final Brick brick : bricks ) {
      final Collection<Brick> supporting = bricks.stream()
          .filter( other -> other.supports( brick ) ).toList();
      if ( supporting.size() == 1 ) {
        safe.removeAll( supporting );
      }
    }
    return safe;
  }

  private static Brick toBrick(final String str) {
    final List<Long> numbers = Utils.toPositiveLongList( str );
    return new Brick(
        new Point( numbers.get( 0 ).intValue(), numbers.get( 1 ).intValue(),
            numbers.get( 2 ).intValue() ),
        new Point( numbers.get( 3 ).intValue(), numbers.get( 4 ).intValue(),
            numbers.get( 5 ).intValue() )
    );
  }

  private record Brick(Point start, Point end) {

    boolean couldSupport(final Brick other) {
      return overlaps( start.x, end.x, other.start.x, other.end.x ) && overlaps( start.y, end.y,
          other.start.y, other.end.y ) && end.z < other.start.z;
    }

    boolean supports(final Brick other) {
      return couldSupport( other ) && end.z + 1 == other.start.z;
    }

    Brick withStartZ(final int z) {
      return new Brick( start.withZ( z ), end.withZ( end.z - (start.z - z) ) );
    }
  }

  private record Point(int x, int y, int z) {

    Point withZ(int z) {
      return new Point( x, y, z );
    }
  }

  private static boolean overlaps(int startA, int endA, int startB, int endB) {
    return !(startA > endB || startB > endA);
  }
}
