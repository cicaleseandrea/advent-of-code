package com.adventofcode.aoc2017;

import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.sqrt;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class AoC212017 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 5 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 18 );
  }

  private String solve(final Stream<String> input, int iterations) {
    final var inputList = input.toList();
    if ( inputList.size() < 5 ) {
      iterations = 2;
    }

    final var rules = getRules( inputList );
    final var rulesTwo = rules.getFirst();
    final var rulesThree = rules.getSecond();

    var grid = toGrid( "010001111" );
    for ( int i = 0; i < iterations; i++ ) {
      grid = getNextGrid( grid, rulesTwo, rulesThree );
    }

    return itoa( Arrays.stream( grid ).flatMap( Arrays::stream ).filter( c -> c == '1' ).count() );
  }

  private Pair<Map<Integer, Character[][]>, Map<Integer, Character[][]>> getRules(
      final List<String> input) {
    final Map<Integer, Character[][]> rulesTwo = new HashMap<>();
    final Map<Integer, Character[][]> rulesThree = new HashMap<>();
    for ( final var line : input ) {
      final var inOut = line.replace( "/", "" ).replace( ".", "0" ).replace( "#", "1" )
          .split( " => " );
      var in = toGrid( inOut[0] );
      final var out = toGrid( inOut[1] );
      final var rules = in.length == 2 ? rulesTwo : rulesThree;
      for ( int i = 0; i < 4; i++ ) {
        rules.put( toDecimal( in ), out );
        rules.put( toDecimal( mirror( in ) ), out );

        in = rotateClockwise( in );
      }
    }
    return new Pair<>( rulesTwo, rulesThree );
  }

  private Character[][] getNextGrid(final Character[][] grid,
      final Map<Integer, Character[][]> rulesTwo, final Map<Integer, Character[][]> rulesThree) {
    final int increment;
    final int nextIncrement;
    final Map<Integer, Character[][]> rules;
    if ( grid.length % 2 == 0 ) {
      increment = 2;
      nextIncrement = 3;
      rules = rulesTwo;
    } else {
      increment = 3;
      nextIncrement = 4;
      rules = rulesThree;
    }
    final int nextLength = grid.length / increment * nextIncrement;
    final var nextGrid = new Character[nextLength][nextLength];
    for ( int i = 0, nextI = 0; i < grid.length; i += increment, nextI += nextIncrement ) {
      for ( int j = 0, nextJ = 0; j < grid.length; j += increment, nextJ += nextIncrement ) {
        final var out = rules.get( toDecimal( grid, i, j, increment ) );
        copyGrid( out, nextGrid, nextI, nextJ );
      }
    }
    return nextGrid;
  }

  private Character[][] mirror(final Character[][] grid) {
    return moveGrid( grid, true );
  }

  private Character[][] rotateClockwise(final Character[][] grid) {
    return moveGrid( grid, false );
  }

  private Character[][] moveGrid(final Character[][] grid, final boolean mirror) {
    //grid is mirrored vertically or rotated clockwise
    final var length = grid.length;
    final var nextGrid = new Character[length][length];
    for ( int i = 0; i < length; i++ ) {
      for ( int j = 0; j < length; j++ ) {
        final var newI = mirror ? i : j;
        final var newJ = mirror ? length - 1 - j : length - 1 - i;
        nextGrid[newI][newJ] = grid[i][j];
      }
    }
    return nextGrid;
  }

  private void copyGrid(final Character[][] src, final Character[][] dst, final int destI,
      final int destJ) {
    for ( int i = 0; i < src.length; i++ ) {
      System.arraycopy( src[i], 0, dst[i + destI], destJ, src[i].length );
    }
  }

  private int toDecimal(final Character[][] src, final int startI, final int startJ,
      final int length) {
    var decimal = 0;
    for ( int i = startI; i < startI + length; i++ ) {
      for ( int j = startJ; j < startJ + length; j++ ) {
        decimal *= 2;
        decimal += charToInt( src[i][j] );
      }
    }
    return decimal;
  }

  private int toDecimal(final Character[][] grid) {
    return toDecimal( grid, 0, 0, grid.length );
  }

  private Character[][] toGrid(final String s) {
    final var length = (int) sqrt( s.length() );
    final var grid = new Character[length][length];
    var k = 0;
    for ( int i = 0; i < length; i++ ) {
      for ( int j = 0; j < length; j++ ) {
        grid[i][j] = s.charAt( k );
        k++;
      }
    }
    return grid;
  }
}
