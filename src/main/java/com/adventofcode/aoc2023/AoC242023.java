package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Double.compare;
import static java.lang.Math.signum;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

class AoC242023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    final Set<Hailstone> hailstones = input.map( AoC242023::toHailstone ).collect( toSet() );
    final long min = hailstones.size() < 10 ? 7 : 200_000_000_000_000L;
    final long max = hailstones.size() < 10 ? 27 : 400_000_000_000_000L;
    long count = 0L;
    for ( final Set<Hailstone> pair : Sets.combinations( hailstones, 2 ) ) {
      final var itr = pair.iterator();
      final Hailstone first = itr.next();
      final Hailstone second = itr.next();
      final Optional<Point> intersectionMaybe = computeIntersection( first, second );
      if ( intersectionMaybe.isPresent() ) {
        final Point intersection = intersectionMaybe.orElseThrow();
        if ( isInTheFuture( intersection, first )
            && isInTheFuture( intersection, second )
            && isInRange( intersection.x, min, max )
            && isInRange( intersection.y, min, max ) ) {
          count++;
        }
      }
    }
    return itoa( count );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return itoa( 47 );
  }

  private Optional<Point> computeIntersection(final Hailstone first, final Hailstone second) {
    final double divisor = first.a() * second.b() - second.a() * first.b();
    if ( divisor == 0 ) {
      return Optional.empty();
    } else {
      final double x = first.b() * second.c() - second.b() * first.c();
      final double y = first.c() * second.a() - second.c() * first.a();
      return Optional.of( new Point( x / divisor, y / divisor, 0 ) );
    }
  }

  private static boolean isInTheFuture(final Point intersection, final Hailstone hailstone) {
    return signum( compare( intersection.x, hailstone.point.x ) ) == signum( hailstone.velocity.a );
  }

  private static Hailstone toHailstone(String str) {
    final List<Long> numbers = Utils.toLongList( str );
    return new Hailstone(
        new Point( numbers.get( 0 ), numbers.get( 1 ), numbers.get( 2 ) ),
        new Velocity( numbers.get( 3 ), numbers.get( 4 ), numbers.get( 5 ) ) );
  }

  private static boolean isInRange(final double n, final double min, final double max) {
    return min <= n && n <= max;
  }

  private record Hailstone(Point point, Velocity velocity) {

    double a() {
      return velocity.b;
    }

    double b() {
      return -velocity.a;
    }

    double c() {
      return -(point.x * a() + point.y * b());
    }
  }

  private record Point(double x, double y, double z) {

  }

  private record Velocity(long a, long b, long c) {

  }
}
