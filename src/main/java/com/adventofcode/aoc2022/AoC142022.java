package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnRegex;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class AoC142022 implements Solution {

  private static final char ROCK = HASH;
  private static final char AIR = DOT;
  private static final char SAND = 'o';

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var map = getMap( input );
    final var maxY = map.keySet().stream().mapToInt( Pair::getSecond ).max().orElseThrow();
    final var floor = maxY + 2;
    final var start = new Pair<>( 500, 0 );

    int units = 0;
    while ( true ) {
      final var sand = new Pair<>( start );
      boolean falling = true;
      while ( falling ) {
        if ( first && isOutOfBorders( sand, maxY ) ) {
          return itoa( units );
        }
        // move down
        sand.setSecond( sand.getSecond() + 1 );
        if ( isBlocked( map, sand, floor ) ) {
          // move down-left
          sand.setFirst( sand.getFirst() - 1 );
          if ( isBlocked( map, sand, floor ) ) {
            // move down-right
            sand.setFirst( sand.getFirst() + 2 );
            if ( isBlocked( map, sand, floor ) ) {
              // stop moving
              sand.setFirst( sand.getFirst() - 1 );
              sand.setSecond( sand.getSecond() - 1 );
              map.put( sand, SAND );
              falling = false;
              units++;
              if ( sand.equals( start ) ) {
                return itoa( units );
              }
            }
          }
        }
      }
    }
  }

  private boolean isOutOfBorders(final Pair<Integer, Integer> sand, final int maxY) {
    return sand.getSecond() > maxY;
  }

  private static boolean isBlocked(final Map<Pair<Integer, Integer>, Character> map,
      final Pair<Integer, Integer> tile, final int floor) {
    return tile.getSecond() == floor || map.getOrDefault( tile, AIR ) == ROCK
        || map.getOrDefault( tile, AIR ) == SAND;
  }

  private Map<Pair<Integer, Integer>, Character> getMap(final Stream<String> input) {
    final Map<Pair<Integer, Integer>, Character> map = new HashMap<>();
    input.forEach( line -> {

      final var points = splitOnRegex( line, " -> " ).map( point -> {
        final var pair = point.split( "," );
        return new Pair<>( atoi( pair[0] ), atoi( pair[1] ) );
      } ).toList();

      for ( int p = 1; p < points.size(); p++ ) {
        final var start = points.get( p - 1 );
        final var end = points.get( p );
        final var startX = start.getFirst();
        final var startY = start.getSecond();
        final var endX = end.getFirst();
        final var endY = end.getSecond();

        for ( int i = min( startX, endX ); i <= max( startX, endX ); i++ ) {
          for ( int j = min( startY, endY ); j <= max( startY, endY ); j++ ) {
            map.put( new Pair<>( i, j ), ROCK );
          }
        }
      }
    } );

    return map;
  }
}
