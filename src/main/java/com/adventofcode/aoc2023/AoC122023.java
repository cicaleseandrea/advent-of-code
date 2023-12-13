package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.toPositiveLongStream;
import static java.util.Collections.nCopies;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

class AoC122023 implements Solution {

  private static final char DAMAGED = HASH;
  private static final char OPERATIONAL = DOT;
  private static final char UNKNOWN = '?';

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 1 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 5 );
  }

  private String solve(final Stream<String> input, final int copies) {
    final long sum = input.map( line -> getInfo( line, copies ) ).parallel()
        .mapToLong( AoC122023::countArrangements ).sum();
    return itoa( sum );
  }

  private static long countArrangements(final Info info) {
    return count( info, 0, new ConcurrentHashMap<>() );
  }

  private static long count(final Info info, int index, final Map<Info, Long> cache) {
    if ( cache.containsKey( info ) ) {
      return cache.get( info );
    }

    final char[] springs = info.springs;
    final long[] damagedSizes = info.damagedSizes;
    if ( springs.length == 0 ) {
      return 1;
    } else if ( !isCorrect( springs, damagedSizes ) ) {
      return 0;
    }

    //remove a complete group of damaged springs and count combinations on the rest of the springs
    final int completeGroupEndIndex = getCompleteGroupEndIndex( springs );
    if ( completeGroupEndIndex >= 0 ) {
      final char[] subSprings = getCopy( springs, completeGroupEndIndex );
      final long[] subDamagedSizes = getCopy( damagedSizes, 1 );
      final long subCount = count( new Info( subSprings, subDamagedSizes ), 0, cache );
      //memoize result
      return cache.compute( new Info( subSprings, subDamagedSizes ), (k, v) -> subCount );
    }

    while ( index < springs.length ) {
      //find the first unknown spring
      if ( springs[index] == UNKNOWN ) {
        //replace unknown with damaged and count combinations
        final char[] damaged = getCopy( springs );
        damaged[index] = DAMAGED;
        final long count1 = count( new Info( damaged, damagedSizes ), index + 1, cache );
        //replace unknown with operational and count combinations
        final char[] operational = getCopy( springs );
        operational[index] = OPERATIONAL;
        final long count2 = count( new Info( operational, damagedSizes ), index + 1, cache );

        return count1 + count2;
      }
      index++;
    }
    return 1;
  }

  /**
   * If there is a known group of damaged springs, return their endIndex, otherwise return -1
   */
  private static int getCompleteGroupEndIndex(final char[] springs) {
    boolean groupStarted = false;
    int index = 0;
    while ( index < springs.length ) {
      final char spring = springs[index];
      if ( spring == UNKNOWN ) {
        return -1;
      } else if ( spring == DAMAGED ) {
        groupStarted = true;
      } else if ( spring == OPERATIONAL && groupStarted ) {
        return index;
      }
      index++;
    }
    return groupStarted ? index : -1;
  }

  private static boolean isCorrect(final char[] springs, final long[] damagedSizes) {
    int groupSize = 0;
    int nGroups = 0;
    int i = 0;
    while ( i < springs.length ) {
      final char spring = springs[i];
      if ( spring == UNKNOWN ) {
        //found an unknown spring
        break;
      } else if ( spring == DAMAGED ) {
        groupSize++;
      } else if ( spring == OPERATIONAL && groupSize > 0 ) {
        if ( !(nGroups < damagedSizes.length && groupSize == damagedSizes[nGroups]) ) {
          //damaged group is complete and does not have expected size
          return false;
        }
        nGroups++;
        groupSize = 0;
      }
      i++;
    }
    final boolean isUnknown = (i < springs.length);
    if ( isUnknown ) {
      if ( nGroups < damagedSizes.length ) {
        //check if incomplete damaged group is still smaller than expected size
        return groupSize <= damagedSizes[nGroups];
      }
    } else {
      if ( nGroups < damagedSizes.length - 1 ) {
        //not enough damaged groups
        return false;
      } else if ( nGroups == damagedSizes.length - 1 ) {
        //check if complete damaged group has expected size
        return groupSize == damagedSizes[nGroups];
      }
    }
    return groupSize == 0;
  }

  private static Info getInfo(final String str, final int copies) {
    final List<String> info = Utils.splitOnTabOrSpace( str );
    final char[] springs = String.join( "?", nCopies( copies, info.get( 0 ) ) ).toCharArray();
    final long[] damagedSizes = toPositiveLongStream(
        String.join( ",", nCopies( copies, info.get( 1 ) ) ) ).mapToLong( Long::longValue )
        .toArray();
    return new Info( springs, damagedSizes );
  }

  private static char[] getCopy(final char[] array) {
    return getCopy( array, 0 );
  }

  private static char[] getCopy(final char[] array, final int beginIndex) {
    final char[] copy = new char[array.length - beginIndex];
    System.arraycopy( array, beginIndex, copy, 0, copy.length );
    return copy;
  }

  private static long[] getCopy(final long[] array, final int beginIndex) {
    final long[] copy = new long[array.length - beginIndex];
    System.arraycopy( array, beginIndex, copy, 0, copy.length );
    return copy;
  }

  private record Info(char[] springs, long[] damagedSizes) {

    @Override
    public boolean equals(final Object o) {
      if ( this == o ) {
        return true;
      }
      if ( o == null || getClass() != o.getClass() ) {
        return false;
      }
      final Info info = (Info) o;
      return Arrays.equals( springs, info.springs ) && Arrays.equals( damagedSizes,
          info.damagedSizes );
    }

    @Override
    public int hashCode() {
      int result = Arrays.hashCode( springs );
      result = 31 * result + Arrays.hashCode( damagedSizes );
      return result;
    }
  }
}
