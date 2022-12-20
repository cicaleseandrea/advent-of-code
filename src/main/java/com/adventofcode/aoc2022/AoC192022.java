package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

class AoC192022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var blueprints = getBlueprints( input );
    var result = first ? 0 : 1;
    final var n = first ? blueprints.size() : min( 3, blueprints.size() );
    for ( int i = 0; i < n; i++ ) {
      final var max = getMaxGeodes( blueprints.get( i ), first ? 24 : 32 );
      if ( first ) {
        result += max * (i + 1);
      } else {
        result *= max;
      }
    }
    return itoa( result );
  }

  private int getMaxGeodes(final Blueprint blueprint, final int minutesLeft) {
    return getMaxGeodes( createLRUMap(), blueprint, minutesLeft, 1, 0, 0, 0, 0, 0, 0, 0,
        new int[1] );
  }

  private int getMaxGeodes(final Map<State, Integer> cache, final Blueprint blueprint,
      final int minutesLeft, final int oreRobot, final int clayRobot, final int obsidianRobot,
      final int geodeRobot, final int ore, final int clay, final int obsidian, final int geode,
      final int[] overallMax) {
    //no time left
    if ( minutesLeft == 0 ) {
      //max for this branch is the number of geodes accumulated
      final var currMax = geode;
      return updateCacheAndMax( cache, null, geode, overallMax, currMax );
    }

    //if the best case scenario (building a geode robot every minute) is still worse than the
    //overall max found so far, stop trying this branch!
    final var theoreticalMax = getTheoreticalMax( geode, geodeRobot, minutesLeft );
    if ( overallMax[0] >= theoreticalMax ) {
      //give up. max for this branch is the number of geodes accumulated so far
      final var currMax = geode;
      return updateCacheAndMax( cache, null, geode, overallMax, currMax );
    }

    //use cached value
    final var state = new State( minutesLeft, oreRobot, clayRobot, obsidianRobot, geodeRobot, ore,
        clay, obsidian );
    if ( cache.containsKey( state ) ) {
      //State doesn't include geodes accumulated so far, so we need to add it to the cached result
      final var currMax = cache.get( state ) + geode;
      return updateCacheAndMax( cache, state, geode, overallMax, currMax );
    }

    var buildGeodeRobot = -1;
    if ( blueprint.geodeObsidianCost() <= obsidian && blueprint.geodeOreCost() <= ore ) {
      //enough resources to build geode robot
      buildGeodeRobot = getMaxGeodes( cache, blueprint, minutesLeft - 1, oreRobot, clayRobot,
          obsidianRobot, geodeRobot + 1, ore + oreRobot - blueprint.geodeOreCost(),
          clay + clayRobot, obsidian + obsidianRobot - blueprint.geodeObsidianCost(),
          geode + geodeRobot, overallMax );
    }

    var buildObsidianRobot = -1;
    final var obsidianPerMinute = getResourceProductionPerMinute( obsidian, obsidianRobot,
        minutesLeft );
    if ( blueprint.geodeObsidianCost() > obsidianPerMinute ) {
      //geode robots might need more obsidian
      if ( blueprint.obsidianClayCost() <= clay && blueprint.obsidianOreCost() <= ore ) {
        //enough resources to build obsidian robot
        buildObsidianRobot = getMaxGeodes( cache, blueprint, minutesLeft - 1, oreRobot, clayRobot,
            obsidianRobot + 1, geodeRobot, ore + oreRobot - blueprint.obsidianOreCost(),
            clay + clayRobot - blueprint.obsidianClayCost(), obsidian + obsidianRobot,
            geode + geodeRobot, overallMax );
      }
    }

    var buildClayRobot = -1;
    final var clayPerMinute = getResourceProductionPerMinute( clay, clayRobot, minutesLeft );
    if ( blueprint.obsidianClayCost() > clayPerMinute ) {
      //obsidian robots might need more clay
      if ( blueprint.clayOreCost() <= ore ) {
        //enough resources to build clay robot
        buildClayRobot = getMaxGeodes( cache, blueprint, minutesLeft - 1, oreRobot, clayRobot + 1,
            obsidianRobot, geodeRobot, ore + oreRobot - blueprint.clayOreCost(), clay + clayRobot,
            obsidian + obsidianRobot, geode + geodeRobot, overallMax );
      }
    }

    var buildOreRobot = -1;
    final var orePerMinute = getResourceProductionPerMinute( ore, oreRobot, minutesLeft );
    if ( blueprint.geodeOreCost() > orePerMinute || blueprint.obsidianOreCost() > orePerMinute
        || blueprint.clayOreCost() > orePerMinute ) {
      //geode, obsidian or clay robots might need more ore
      if ( blueprint.oreOreCost() <= ore ) {
        //enough resources to build ore robot
        buildOreRobot = getMaxGeodes( cache, blueprint, minutesLeft - 1, oreRobot + 1, clayRobot,
            obsidianRobot, geodeRobot, ore + oreRobot - blueprint.oreOreCost(), clay + clayRobot,
            obsidian + obsidianRobot, geode + geodeRobot, overallMax );
      }
    }

    final var dontBuild = getMaxGeodes( cache, blueprint, minutesLeft - 1, oreRobot, clayRobot,
        obsidianRobot, geodeRobot, ore + oreRobot, clay + clayRobot, obsidian + obsidianRobot,
        geode + geodeRobot, overallMax );

    //pick the max from the branch with the best decision
    final var currMax = max(
        max( max( max( dontBuild, buildGeodeRobot ), buildObsidianRobot ), buildClayRobot ),
        buildOreRobot );
    return updateCacheAndMax( cache, state, geode, overallMax, currMax );
  }

  private int getTheoreticalMax(final int resource, final int robot, final int minutesLeft) {
    //current resource + resource produced by existing robots + one new robot per minute
    return resource + (robot * minutesLeft) + ((minutesLeft - 1) * minutesLeft) / 2;
  }

  private int updateCacheAndMax(final Map<State, Integer> cache, final State state, final int geode,
      final int[] overallMax, final int max) {
    //State doesn't include geodes accumulated so far, so we need to remove it from the result
    cache.put( state, max - geode );
    overallMax[0] = max( overallMax[0], max );
    return max;
  }

  private double getResourceProductionPerMinute(final double resource, final int robot,
      final int minutesLeft) {
    //resource produced by existing robots + current resource spread over time left
    return robot + (resource / minutesLeft);
  }

  private List<Blueprint> getBlueprints(final Stream<String> input) {
    return input.map( Utils::toPositiveLongList ).map(
        list -> new Blueprint( list.get( 1 ).intValue(), list.get( 2 ).intValue(),
            list.get( 3 ).intValue(), list.get( 4 ).intValue(), list.get( 5 ).intValue(),
            list.get( 6 ).intValue() ) ).toList();
  }

  private <K, V> Map<K, V> createLRUMap() {
    final int LRU_CACHE_SIZE = 3_000_000;

    return new LinkedHashMap<>( LRU_CACHE_SIZE * 2 ) {
      @Override
      protected boolean removeEldestEntry(final Entry<K, V> eldest) {
        return size() > LRU_CACHE_SIZE;
      }
    };
  }

  private record Blueprint(int oreOreCost, int clayOreCost, int obsidianOreCost,
                           int obsidianClayCost, int geodeOreCost, int geodeObsidianCost) {

  }

  private record State(int minutes, int oreRobot, int clayRobot, int obsidianRobot, int geodeRobot,
                       int ore, int clay, int obsidian) {

  }
}
