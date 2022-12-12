package com.adventofcode.aoc2022;

import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.toPositiveLongStream;
import static java.lang.Long.parseLong;
import static java.util.Comparator.reverseOrder;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.stream.Stream;

class AoC112022 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var iterator = input.iterator();
    final var monkeys = new ArrayList<Monkey>();
    while ( iterator.hasNext() ) {
      monkeys.add( getMonkey( iterator, first ) );
    }
    final var mod = monkeys.stream().map( Monkey::getDivisible ).reduce( Math::multiplyExact );
    monkeys.forEach( m -> m.setMod( mod.orElseThrow() ) );

    for ( int i = 0; i < (first ? 20 : 10000); i++ ) {
      monkeys.forEach( monkey -> monkey.throwItems().forEach(
          thrownItem -> monkeys.get( thrownItem.getFirst() ).addItem( thrownItem.getSecond() ) ) );
    }

    return monkeys.stream().map( Monkey::getInspectedItems ).sorted( reverseOrder() ).limit( 2 )
        .reduce( (a, b) -> a * b ).map( Utils::itoa ).orElseThrow();
  }

  private Monkey getMonkey(final Iterator<String> iterator, final boolean relief) {
    iterator.next();
    final var items = toPositiveLongStream( iterator.next() ).toList();
    final var worryLevelChange = getWorryLevelChange( iterator.next() );
    final var divisible = extractIntegerFromString( iterator.next() );
    final int monkeyTrue = extractIntegerFromString( iterator.next() );
    final int monkeyFalse = extractIntegerFromString( iterator.next() );

    if ( iterator.hasNext() ) {
      iterator.next();
    }

    return new Monkey( items, worryLevelChange, divisible, monkeyTrue, monkeyFalse, relief );
  }

  private LongUnaryOperator getWorryLevelChange(final String worryLevelChange) {
    final var tokens = worryLevelChange.split( " " );
    final var opString = tokens[tokens.length - 2];
    final var argString = tokens[tokens.length - 1];
    final LongBinaryOperator op = switch ( opString ) {
      case "+" -> Math::addExact;
      case "*" -> Math::multiplyExact;
      default -> throw new IllegalStateException( "Unexpected value: " + opString );
    };
    final LongUnaryOperator arg = old -> "old".equals( argString ) ? old : parseLong( argString );

    return old -> op.applyAsLong( old, arg.applyAsLong( old ) );
  }

  private static class Monkey {

    private final List<Long> items;
    private final LongUnaryOperator worryLevelChange;
    private final int divisible;
    private final int monkeyTrue;
    private final int monkeyFalse;
    private final boolean relief;
    private long inspectedItems;
    private Integer mod;

    private Monkey(final List<Long> items, final LongUnaryOperator worryLevelChange,
        final int divisible, final int monkeyTrue, final int monkeyFalse, final boolean relief) {
      this.items = new ArrayList<>( items );
      this.worryLevelChange = worryLevelChange;
      this.divisible = divisible;
      this.monkeyTrue = monkeyTrue;
      this.monkeyFalse = monkeyFalse;
      this.relief = relief;
    }

    private Pair<Integer, Long> nextMonkeyWorry(final long item) {
      inspectedItems++;

      var worry = worryLevelChange.applyAsLong( item );
      if ( relief ) {
        worry /= 3;
      } else {
        worry %= mod;
      }

      return new Pair<>( worry % divisible == 0 ? monkeyTrue : monkeyFalse, worry );
    }

    public List<Pair<Integer, Long>> throwItems() {
      final var thrownItems = items.stream().map( this::nextMonkeyWorry ).toList();
      items.clear();
      return thrownItems;
    }

    public void addItem(final long item) {
      items.add( item );
    }

    public void setMod(final int mod) {
      if ( this.mod != null ) {
        throw new IllegalStateException( "mod was already initialized to: " + this.mod );
      }
      this.mod = mod;
    }

    public long getInspectedItems() {
      return inspectedItems;
    }

    public int getDivisible() {
      return divisible;
    }
  }
}
