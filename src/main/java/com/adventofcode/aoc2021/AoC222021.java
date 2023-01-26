package com.adventofcode.aoc2021;

import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Integer.parseInt;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Collections.binarySearch;
import static java.util.stream.Collectors.toCollection;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class AoC222021 implements Solution {

  private static final Pattern STEP_REGEX = Pattern.compile(
      "(\\D+) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)" );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var steps = input.map( line -> getStep( line, first ) ).toList();
    //put x/y/z values in sorted lists
    final var xs = getSortedList( steps, step -> Stream.of( step.x1, step.x2 ) );
    final var ys = getSortedList( steps, step -> Stream.of( step.y1, step.y2 ) );
    final var zs = getSortedList( steps, step -> Stream.of( step.z1, step.z2 ) );

    final var cubes = new BitSet[xs.size()][ys.size()]; //using BitSet to save some memory
    for ( final var row : cubes ) {
      Arrays.setAll( row, BitSet::new );
    }
    //set cubes on/off based on x/y/z indexes (rather than values)
    for ( final var step : steps ) {
      final int endI = binarySearch( xs, step.x2 );
      for ( int i = binarySearch( xs, step.x1 ); i < endI; i++ ) {
        final int endJ = binarySearch( ys, step.y2 );
        for ( int j = binarySearch( ys, step.y1 ); j < endJ; j++ ) {
          final int endK = binarySearch( zs, step.z2 );
          for ( int k = binarySearch( zs, step.z1 ); k < endK; k++ ) {
            cubes[i][j].set( k, step.on );
          }
        }
      }
    }

    //compute the size of each cube that is "on", using x/y/z values
    long size = 0;
    for ( int i = 0; i < xs.size(); i++ ) {
      for ( int j = 0; j < ys.size(); j++ ) {
        final var bitSet = cubes[i][j];
        for ( int k = bitSet.nextSetBit( 0 ); k >= 0; k = bitSet.nextSetBit( k + 1 ) ) {
          final long x = xs.get( i + 1 ) - xs.get( i );
          final long y = ys.get( j + 1 ) - ys.get( j );
          final long z = zs.get( k + 1 ) - zs.get( k );
          size += x * y * z;
        }
      }
    }
    return itoa( size );
  }

  private List<Integer> getSortedList(final Collection<Step> steps,
      final Function<Step, Stream<Integer>> getIndexes) {
    return steps.stream().flatMap( getIndexes ).sorted().distinct()
        .collect( toCollection( ArrayList::new ) );
  }

  private Step getStep(final String step, final boolean first) {
    final var matcher = STEP_REGEX.matcher( step );
    if ( !matcher.matches() ) {
      throw new IllegalArgumentException();
    }
    final var on = matcher.group( 1 ).equals( "on" );
    //make "from" inclusive and "to" exclusive
    final var x1 = getAdjustedIndex( parseInt( matcher.group( 2 ) ), first );
    final var x2 = getAdjustedIndex( parseInt( matcher.group( 3 ) ) + 1, first );
    final var y1 = getAdjustedIndex( parseInt( matcher.group( 4 ) ), first );
    final var y2 = getAdjustedIndex( parseInt( matcher.group( 5 ) ) + 1, first );
    final var z1 = getAdjustedIndex( parseInt( matcher.group( 6 ) ), first );
    final var z2 = getAdjustedIndex( parseInt( matcher.group( 7 ) ) + 1, first );
    return new Step( on, x1, x2, y1, y2, z1, z2 );
  }

  private int getAdjustedIndex(final int n, final boolean first) {
    if ( first ) {
      return min( max( n, -50 ), 51 );
    } else {
      return n;
    }
  }

  private record Step(boolean on, int x1, int x2, int y1, int y2, int z1, int z2) {

  }
}
