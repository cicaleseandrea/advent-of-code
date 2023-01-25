package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class AoC212015 implements Solution {

  private static final Map<Integer, Pair<Integer, Integer>> WEAPONS = Map.of( 8, new Pair<>( 4, 0 ),
      10, new Pair<>( 5, 0 ), 25, new Pair<>( 6, 0 ), 40, new Pair<>( 7, 0 ), 74,
      new Pair<>( 8, 0 ) );
  private static final Map<Integer, Pair<Integer, Integer>> ARMORS = Map.of( 13, new Pair<>( 0, 1 ),
      31, new Pair<>( 0, 2 ), 53, new Pair<>( 0, 3 ), 75, new Pair<>( 0, 4 ), 102,
      new Pair<>( 0, 5 ) );
  private static final Map<Integer, Pair<Integer, Integer>> RINGS = Map.of( 25, new Pair<>( 1, 0 ),
      50, new Pair<>( 2, 0 ), 100, new Pair<>( 3, 0 ), 20, new Pair<>( 0, 1 ), 40,
      new Pair<>( 0, 2 ), 80, new Pair<>( 0, 3 ) );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var inputList = input.toList();
    final var boss = new Stats( extractIntegerFromString( inputList.get( 0 ) ),
        extractIntegerFromString( inputList.get( 1 ) ),
        extractIntegerFromString( inputList.get( 2 ) ) );
    final int totalCost = Stream.concat( WEAPONS.keySet().stream(),
            Stream.concat( ARMORS.keySet().stream(), RINGS.keySet().stream() ) )
        .mapToInt( Integer::intValue ).sum();
    int max = 0;
    for ( int gold = 0; gold <= totalCost; gold++ ) {
      for ( final var player : getStats( gold ) ) {
        final boolean win = player.wins( boss );
        if ( first == win ) {
          if ( first ) {
            //return min gold to win
            return itoa( gold );
          } else {
            //store max gold to lose
            max = gold;
            break;
          }
        }
      }
    }
    return itoa( max );
  }

  private Set<Stats> getStats(final int gold) {
    //add players with one weapon
    final Set<Stats> stats = new HashSet<>( buyItems( new Stats( gold ), WEAPONS, 1 ) );
    for ( final var player : new HashSet<>( stats ) ) {
      //add players with one armor
      stats.addAll( buyItems( player, ARMORS, 1 ) );
    }
    for ( final var player : new HashSet<>( stats ) ) {
      //add players with one or two rings
      stats.addAll( buyItems( player, RINGS, 2 ) );
    }
    //keep only players that spent all their gold
    stats.removeIf( p -> p.gold != 0 );
    return stats;
  }

  private Set<Stats> buyItems(final Stats player, final Map<Integer, Pair<Integer, Integer>> items,
      final int maxAmount) {
    final Set<Stats> stats = new HashSet<>();
    for ( int i = 1; i <= maxAmount; i++ ) {
      for ( final var itemsSet : Sets.combinations( items.entrySet(), i ) ) {
        var playerItem = player;
        for ( final var item : itemsSet ) {
          playerItem = getUpdatedStats( playerItem, item.getValue().getFirst(),
              item.getValue().getSecond(), item.getKey() );
          if ( playerItem != null ) {
            stats.add( playerItem );
          }
        }
      }
    }
    return stats;
  }

  private Stats getUpdatedStats(final Stats player, final int damage, final int armor,
      final int cost) {
    if ( player == null || player.gold < cost ) {
      return null;
    } else {
      return new Stats( player.HP, player.damage + damage, player.armor + armor,
          player.gold - cost );
    }
  }

  private record Stats(int HP, int damage, int armor, int gold) {

    Stats(final int HP, final int damage, final int armor) {
      this( HP, damage, armor, 0 );
    }

    Stats(final int gold) {
      this( 100, 0, 0, gold );
    }

    boolean wins(final Stats other) {
      return Math.ceil( HP / Math.max( other.damage - armor, 1.0 ) ) >= Math.ceil(
          other.HP / Math.max( damage - other.armor, 1.0 ) );
    }
  }
}
