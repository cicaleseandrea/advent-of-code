package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.comparingInt;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

class AoC222015 implements Solution {


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
    final var boss = new Boss( extractIntegerFromString( inputList.get( 0 ) ),
        extractIntegerFromString( inputList.get( 1 ) ) );
    return itoa( computeShortestPath( boss, first ) );
  }

  private int computeShortestPath(final Boss boss, final boolean first) {
    //Dijkstra to find shortest path to the target
    final var distances = new HashMap<State, Integer>();
    final var queue = new PriorityQueue<State>( comparingInt( distances::get ) );
    final var src = new State( new Player(), boss, true );
    distances.put( src, 0 );
    queue.add( src );

    while ( !queue.isEmpty() ) {
      final var curr = queue.remove();
      if ( curr.boss.HP <= 0 ) {
        return distances.get( curr );
      }
      for ( final var neighbour : getNeighbours( curr, first ) ) {
        final var neighbourCost = neighbour.getFirst();
        final var neighbourState = neighbour.getSecond();
        final var newDistance = distances.get( curr ) + neighbourCost;
        if ( newDistance < distances.getOrDefault( neighbourState, Integer.MAX_VALUE ) ) {
          //update distance
          distances.put( neighbourState, newDistance );
          //add to the queue
          queue.add( neighbourState );
        }
      }
    }

    throw new IllegalStateException();
  }

  private Collection<Pair<Integer, State>> getNeighbours(final State state, final boolean first) {
    final List<Pair<Integer, State>> states = new ArrayList<>();
    final var startOfTurn = startOfTurn( state, first );
    final var player = startOfTurn.getFirst();
    final var boss = startOfTurn.getSecond();
    if ( player.HP > 0 ) {
      //still alive
      if ( boss.HP <= 0 ) {
        //won
        states.add( new Pair<>( 0, new State( player, boss, false ) ) );
      } else if ( state.playerTurn ) {
        playerAttack( player, boss, states );
      } else {
        bossAttack( player, boss, states );
      }
    }
    return states;
  }

  private Pair<Player, Boss> startOfTurn(final State state, final boolean first) {
    final var boss = state.boss;
    final var player = state.player;
    final var nextPlayer = new Player( player.HP - (first ? 0 : 1),
        updateStat( player.mana, player.recharge, 101 ), player.shield - 1, player.poison - 1,
        player.recharge - 1 );
    final var nextBoss = new Boss( updateStat( boss.HP, player.poison, -3 ), boss.damage );
    return new Pair<>( nextPlayer, nextBoss );
  }

  private void bossAttack(final Player player, final Boss boss,
      final Collection<Pair<Integer, State>> states) {
    final var armor = updateStat( 0, player.shield, 7 );
    final var nextHP = player.HP - Math.max( boss.damage - armor, 1 );
    if ( nextHP > 0 ) {
      //still alive
      states.add( new Pair<>( 0, new State(
          new Player( nextHP, player.mana, player.shield, player.poison, player.recharge ), boss,
          true ) ) );
    }
  }

  private void playerAttack(final Player player, final Boss boss,
      final Collection<Pair<Integer, State>> states) {
    missileAttack( player, boss, states );
    drainAttack( player, boss, states );
    shieldAttack( player, boss, states );
    poisonAttack( player, boss, states );
    rechargeAttack( player, boss, states );
  }

  private void missileAttack(final Player player, final Boss boss,
      final Collection<Pair<Integer, State>> states) {
    final var cost = 53;
    final var nextMana = player.mana - cost;
    if ( nextMana >= 0 ) {
      //enough mana
      states.add( new Pair<>( cost, new State(
          new Player( player.HP, nextMana, player.shield, player.poison, player.recharge ),
          new Boss( boss.HP - 4, boss.damage ), false ) ) );
    }
  }

  private void drainAttack(final Player player, final Boss boss,
      final Collection<Pair<Integer, State>> states) {
    final var cost = 73;
    final var nextMana = player.mana - cost;
    if ( nextMana >= 0 ) {
      //enough mana
      states.add( new Pair<>( cost, new State(
          new Player( Math.min( player.HP + 2, 50 ), nextMana, player.shield, player.poison,
              player.recharge ), new Boss( boss.HP - 2, boss.damage ), false ) ) );
    }
  }

  private void shieldAttack(final Player player, final Boss boss,
      final Collection<Pair<Integer, State>> states) {
    final var cost = 113;
    final var nextMana = player.mana - cost;
    if ( nextMana >= 0 && player.shield <= 0 ) {
      //enough mana and effect is inactive
      states.add( new Pair<>( cost,
          new State( new Player( player.HP, nextMana, 6, player.poison, player.recharge ), boss,
              false ) ) );
    }
  }

  private void poisonAttack(final Player player, final Boss boss,
      final Collection<Pair<Integer, State>> states) {
    final var cost = 173;
    final var nextMana = player.mana - cost;
    if ( nextMana >= 0 && player.poison <= 0 ) {
      //enough mana and effect is inactive
      states.add( new Pair<>( cost,
          new State( new Player( player.HP, nextMana, player.shield, 6, player.recharge ), boss,
              false ) ) );
    }
  }

  private void rechargeAttack(final Player player, final Boss boss,
      final Collection<Pair<Integer, State>> states) {
    final var cost = 229;
    final var nextMana = player.mana - cost;
    if ( nextMana >= 0 && player.recharge <= 0 ) {
      //enough mana and effect is inactive
      states.add( new Pair<>( cost,
          new State( new Player( player.HP, nextMana, player.shield, player.poison, 5 ), boss,
              false ) ) );
    }
  }

  private int updateStat(final int stat, final int effect, final int amount) {
    return stat + (effect > 0 ? amount : 0);
  }

  private record State(Player player, Boss boss, boolean playerTurn) {

  }

  private record Player(int HP, int mana, int shield, int poison, int recharge) {

    Player() {
      this( 50, 500, 0, 0, 0 );
    }

  }

  private record Boss(int HP, int damage) {

  }
}
