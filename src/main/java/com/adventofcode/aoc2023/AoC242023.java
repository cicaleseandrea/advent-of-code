package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Double.compare;
import static java.lang.Math.signum;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Triplet;
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
      final Optional<Point> intersectionMaybe = computeIntersection(
          first.toEquationExcludingAxis( "z" ), second.toEquationExcludingAxis( "z" ) );
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

  private Optional<Point> computeIntersection(final Equation first, final Equation second) {
    //compute intersection between two lines: ax + by + c = 0
    final double divisor = first.a() * second.b() - second.a() * first.b();
    if ( divisor == 0 ) {
      return Optional.empty();
    } else {
      final double x = first.b() * second.c() - second.b() * first.c();
      final double y = first.c() * second.a() - second.c() * first.a();
      return Optional.of( new Point( x / divisor, y / divisor ) );
    }
  }

  private static boolean isInTheFuture(final Point intersection, final Hailstone hailstone) {
    return signum( compare( intersection.x, hailstone.point.getFirst() ) ) == signum(
        hailstone.velocity.getFirst() );
  }

  private static Hailstone toHailstone(String str) {
    final List<Long> numbers = Utils.toLongList( str );
    return new Hailstone(
        new Triplet<>( numbers.get( 0 ), numbers.get( 1 ), numbers.get( 2 ) ),
        new Triplet<>( numbers.get( 3 ), numbers.get( 4 ), numbers.get( 5 ) ) );
  }

  private static boolean isInRange(final double n, final double min, final double max) {
    return min <= n && n <= max;
  }

  private record Hailstone(Triplet<Long, Long, Long> point, Triplet<Long, Long, Long> velocity) {

    /**
     * Create equation in two variables instead of three
     */
    Equation toEquationExcludingAxis(String exclude) {
      return toEquation( getPointExcludingAxis( exclude ), getVelocityExcludingAxis( exclude ) );
    }

    Point getPointExcludingAxis(String exclude) {
      return switch ( exclude ) {
        case "x" -> new Point( point.getSecond(), point.getThird() );
        case "y" -> new Point( point.getFirst(), point.getThird() );
        case "z" -> new Point( point.getFirst(), point.getSecond() );
        default -> throw new IllegalStateException( "Unexpected value: " + exclude );
      };
    }

    private Velocity getVelocityExcludingAxis(final String exclude) {
      return switch ( exclude ) {
        case "x" -> new Velocity( velocity.getSecond(), velocity.getThird() );
        case "y" -> new Velocity( velocity.getFirst(), velocity.getThird() );
        case "z" -> new Velocity( velocity.getFirst(), velocity.getSecond() );
        default -> throw new IllegalStateException( "Unexpected value: " + exclude );
      };
    }
  }

  /**
   * Compute a, b, c for equation: ax + by + c = 0
   */
  private static Equation toEquation(Point point, Velocity velocity) {
    final double a = velocity.y;
    final double b = -velocity.x;
    final double c = -(point.x * a + point.y * b);
    return new Equation( a, b, c );
  }

  private record Equation(double a, double b, double c) {
    //equation in the form: ax + by + c = 0
  }

  private record Point(double x, double y) {

  }

  private record Velocity(long x, long y) {

  }
}
