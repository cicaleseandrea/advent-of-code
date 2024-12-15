package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.BLACK;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.GraphUtils;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AoC142024 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    List<Robot> robots = input
        .map( Utils::toLongList )
        .map( numbers ->
            new Robot(
                new Pair<>( numbers.get( 1 ), numbers.get( 0 ) ),
                new Pair<>( numbers.get( 3 ), numbers.get( 2 ) )
            )
        )
        .toList();
    int rows = robots.size() < 15 ? 7 : 103;
    int columns = robots.size() < 15 ? 11 : 101;
    for ( int seconds = 1; seconds <= 100 || !first; seconds++ ) {
      robots.forEach( robot -> robot.move( rows, columns ) );
      if ( !first ) {
        Set<Pair<Long, Long>> positions = robots.stream()
            .map( Robot::position )
            .collect( Collectors.toSet() );
        if ( robots.size() != positions.size() ) {  //to speedup part 2
          continue;
        }
        for ( final Pair<Long, Long> start : positions ) {
          Set<Pair<Long, Long>> connectedRobots = GraphUtils.fill( start,
              n -> getNeighbours( n, positions ) );
          if ( connectedRobots.size() > robots.size() / 5 ) {
            printRobots( rows, columns, positions );
            return itoa( seconds );
          }
        }
      }
    }
    return getSafetyFactor( robots, rows, columns );
  }

  private String getSafetyFactor(final List<Robot> robots, final int rows, final int columns) {
    Predicate<Pair<Long, Long>> up = p -> p.getFirst() < rows / 2;
    Predicate<Pair<Long, Long>> down = p -> p.getFirst() > rows / 2;
    Predicate<Pair<Long, Long>> left = p -> p.getSecond() < columns / 2;
    Predicate<Pair<Long, Long>> right = p -> p.getSecond() > columns / 2;
    long q1 = robots.stream().map( Robot::position ).filter( up ).filter( left ).count();
    long q2 = robots.stream().map( Robot::position ).filter( up ).filter( right ).count();
    long q3 = robots.stream().map( Robot::position ).filter( down ).filter( left ).count();
    long q4 = robots.stream().map( Robot::position ).filter( down ).filter( right ).count();
    return itoa( q1 * q2 * q3 * q4 );
  }

  private void printRobots(final int rows, final int columns,
      final Set<Pair<Long, Long>> positions) {
    if ( !Utils.shouldPrint() ) {
      return;
    }
    for ( int i = 0; i < rows; i++ ) {
      for ( int j = 0; j < columns; j++ ) {
        if ( positions.contains( new Pair<>( (long) i, (long) j ) ) ) {
          System.out.print( "🤖" );
        } else {
          System.out.print( BLACK );
        }
      }
      System.out.println();
    }
  }

  private Collection<Pair<Long, Long>> getNeighbours(final Pair<Long, Long> p,
      final Set<Pair<Long, Long>> robots) {
    return Stream.of( Direction.values() )
        .map( d -> {
          Pair<Long, Long> n = new Pair<>( p );
          d.move( n );
          return n;
        } )
        .filter( robots::contains )
        .toList();
  }

  private record Robot(Pair<Long, Long> position, Pair<Long, Long> velocity) {

    public void move(int rows, int columns) {
      position.setFirst( Utils.decrementMod( position.getFirst(), -velocity.getFirst(), rows ) );
      position.setSecond(
          Utils.decrementMod( position.getSecond(), -velocity.getSecond(), columns ) );
    }
  }
}
