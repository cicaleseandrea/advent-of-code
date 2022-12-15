package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.clearScreen;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.shouldPrint;
import static com.adventofcode.utils.Utils.splitOnRegex;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class AoC142022 implements Solution {

  private static final char ROCK = HASH;
  private static final char AIR = DOT;
  private static final char SAND = 'o';
  private static final int FLOOR_DISTANCE = 2;
  private static final int START_X = 500;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var map = getMap( getMap( input ) );
    final var maxY = (map[0].length - 1) - FLOOR_DISTANCE;
    final var start = new Pair<>( START_X, 0 );

    if ( !first ) {
      //floor
      for ( int i = 0; i < map.length; i++ ) {
        map[i][map[0].length - 1] = ROCK;
      }
    }

    int units = 0;
    while ( true ) {
      final var sand = new Pair<>( start );
      boolean falling = true;
      while ( falling ) {
        if ( first && isOutOfBorders( sand, maxY ) ) {
          print( map );
          return itoa( units );
        }
        // move down
        sand.setSecond( sand.getSecond() + 1 );
        if ( isBlocked( map, sand ) ) {
          // move down-left
          sand.setFirst( sand.getFirst() - 1 );
          if ( isBlocked( map, sand ) ) {
            // move down-right
            sand.setFirst( sand.getFirst() + 2 );
            if ( isBlocked( map, sand ) ) {
              // stop moving
              sand.setFirst( sand.getFirst() - 1 );
              sand.setSecond( sand.getSecond() - 1 );
              map[sand.getFirst()][sand.getSecond()] = SAND;
              falling = false;
              units++;
              if ( map[0].length < 20 ) {
                print( map );
              }
              if ( sand.equals( start ) ) {
                print( map );
                return itoa( units );
              }
            }
          }
        }
      }
    }
  }

  private boolean isOutOfBorders(final Pair<Integer, Integer> tile, final int maxY) {
    return tile.getSecond() > maxY;
  }

  private static boolean isBlocked(final char[][] map, final Pair<Integer, Integer> tile) {
    final var tileValue = map[tile.getFirst()][tile.getSecond()];
    return tileValue == ROCK || tileValue == SAND;
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

  private char[][] getMap(final Map<Pair<Integer, Integer>, Character> map) {
    final var maxX = map.keySet().stream().mapToInt( Pair::getFirst ).max().orElseThrow();
    final var maxY = map.keySet().stream().mapToInt( Pair::getSecond ).max().orElseThrow();
    final var matrix = new char[(maxX + 1) + 200][(maxY + 1) + FLOOR_DISTANCE];
    for ( final var row : matrix ) {
      Arrays.fill( row, AIR );
    }
    for ( final var point : map.entrySet() ) {
      matrix[point.getKey().getFirst()][point.getKey().getSecond()] = point.getValue();
    }
    return matrix;
  }

  private static void print(final char[][] map) {
    if ( shouldPrint() ) {
      try {
        clearScreen();
        Thread.sleep( 300 );
        for ( int y = 0; y < map[0].length; y++ ) {
          for ( int x = START_X - (map[0].length - 1); x <= START_X + (map[0].length - 1); x++ ) {
            System.out.print( map[x][y] );
          }
          System.out.println();
        }
        System.out.println();
      } catch ( InterruptedException e ) {
        e.printStackTrace();
      }
    }
  }
}
