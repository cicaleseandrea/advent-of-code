package com.adventofcode.aoc2023;

import static com.adventofcode.aoc2023.AoC242023.Pair.toPair;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Long.compare;
import static java.lang.Math.round;
import static java.lang.Math.signum;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Triplet;
import com.adventofcode.utils.Utils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC242023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    final Set<Hailstone> hailstones = input.map( AoC242023::toHailstone ).collect( toSet() );
    final long min = hailstones.size() < 10 ? 7 : 200_000_000_000_000L;
    final long max = hailstones.size() < 10 ? 27 : 400_000_000_000_000L;

    long count = 0;
    final var pairs = Sets.combinations( hailstones, 2 ).iterator();
    while ( pairs.hasNext() ) {
      final var pair = pairs.next().iterator();
      final Hailstone first = pair.next();
      final Hailstone second = pair.next();
      final Optional<Pair> intersectionMaybe = computeIntersection( first.toEquation(),
          second.toEquation() );
      if ( intersectionMaybe.isPresent() ) {
        final Pair intersection = intersectionMaybe.orElseThrow();
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
    final Set<Hailstone> hailstones = input.map( AoC242023::toHailstone ).collect( toSet() );
    final Pair rockXY = findRock( hailstones, "z" );
    final Pair rockXZ = findRock( hailstones, "y" );
    Preconditions.checkArgument( rockXY.x == rockXZ.x );
    return itoa( rockXY.x + rockXY.y + rockXZ.y );
  }

  private static Pair findRock(final Set<Hailstone> hailstones, final String axisExcluded) {
    //from here: https://www.reddit.com/r/adventofcode/comments/18pptor/comment/keps780/
    //consider the frame of reference of the rock (modify all hailstones velocities relative to rock's velocity)
    //for the right rock's velocity, all hailstones hit the rock, i.e. they all intersect at one point

    //try all velocities in two axis (infinite stream)
    final Stream<Pair> velocities = IntStream.iterate( 0, i -> i + 1 ).boxed()
        .flatMap( i -> IntStream.rangeClosed( 0, i ).boxed()
            .flatMap( j -> Stream.of( new Pair( i, j ), new Pair( j, i ) )
                .flatMap( p -> Stream.of( p, new Pair( -p.x, -p.y ), new Pair( -p.x, p.y ),
                    new Pair( p.x, -p.y ) ) ) ) );
    for ( final Pair velocity : Utils.getIterable( velocities ) ) {
      final Set<Pair> intersections = new HashSet<>();
      final var pairs = Sets.combinations( hailstones, 2 ).iterator();
      //speedup: only check few hailstones
      int checks = 0;
      while ( checks < 5 && pairs.hasNext() ) {
        final var pair = pairs.next().iterator();
        //modify hailstones' velocities and compute their intersection
        final Equation first = pair.next().toEquationAddingVelocity( axisExcluded, velocity );
        final Equation second = pair.next().toEquationAddingVelocity( axisExcluded, velocity );
        computeIntersection( first, second ).ifPresent( intersections::add );
        checks++;
      }
      if ( intersections.size() == 1 ) {
        //if all hailstones intersect at one point, this is the rock position
        return intersections.iterator().next();
      }
    }
    throw new IllegalArgumentException();
  }

  private static Optional<Pair> computeIntersection(final Equation first, final Equation second) {
    //compute intersection between two lines in the form: ax + by + c = 0
    //using double to manage bigger numbers
    final double divisor = (double) first.a() * second.b() - (double) second.a() * first.b();
    if ( divisor == 0 ) {
      return Optional.empty();
    } else {
      final double x = (double) first.b() * second.c() - (double) second.b() * first.c();
      final double y = (double) first.c() * second.a() - (double) second.c() * first.a();
      //rounding to long and returning integer intersection
      return Optional.of( new Pair( round( x / divisor ), round( y / divisor ) ) );
    }
  }

  private static boolean isInTheFuture(final Pair intersection, final Hailstone hailstone) {
    return signum( compare( intersection.x, hailstone.point.getFirst() ) ) == signum(
        hailstone.velocity.getFirst() );
  }

  private static boolean isInRange(final long n, final long min, final long max) {
    return min <= n && n <= max;
  }

  private static Hailstone toHailstone(String str) {
    final List<Long> numbers = Utils.toLongList( str );
    return new Hailstone(
        new Triplet<>( numbers.get( 0 ), numbers.get( 1 ), numbers.get( 2 ) ),
        new Triplet<>( numbers.get( 3 ), numbers.get( 4 ), numbers.get( 5 ) ) );
  }

  private record Hailstone(Triplet<Long, Long, Long> point, Triplet<Long, Long, Long> velocity) {

    /**
     * Consider two variables instead of three, add velocity and create equation
     */
    Equation toEquationAddingVelocity(String axisExcluded, Pair velocity) {
      return Equation.toEquation( toPair( this.point, axisExcluded ),
          toPair( this.velocity, axisExcluded ).add( velocity ) );
    }

    Equation toEquation() {
      return toEquationAddingVelocity( "z", Pair.ZERO );
    }
  }

  record Equation(long a, long b, long c) {

    /**
     * Compute a, b, c for equation: ax + by + c = 0
     */
    static Equation toEquation(Pair point, Pair velocity) {
      final long a = velocity.y;
      final long b = -velocity.x;
      final long c = -(point.x * a + point.y * b);
      return new Equation( a, b, c );
    }
  }

  record Pair(long x, long y) {

    static final Pair ZERO = new Pair( 0, 0 );

    Pair add(Pair other) {
      return new Pair( x + other.x, y + other.y );
    }

    static Pair toPair(final Triplet<Long, Long, Long> triplet, final String exclude) {
      return switch ( exclude ) {
        case "x" -> new Pair( triplet.getSecond(), triplet.getThird() );
        case "y" -> new Pair( triplet.getFirst(), triplet.getThird() );
        case "z" -> new Pair( triplet.getFirst(), triplet.getSecond() );
        default -> throw new IllegalStateException( "Unexpected value: " + exclude );
      };
    }
  }
}
