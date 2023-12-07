package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC072023 implements Solution {

  private static final char JOKER = '0';
  private static final int N_CARDS = 5;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input.map( line -> line.replace( 'J', JOKER ) ) );
  }

  private static String solve(final Stream<String> input) {
    final List<Bid> bids = getBids( input );
    bids.sort( comparing( Bid::hand ) );

    return itoa( IntStream.rangeClosed( 1, bids.size() )
        .mapToLong( rank -> bids.get( rank - 1 ).amount * rank ).sum() );
  }

  private static List<Bid> getBids(final Stream<String> input) {
    return input.map( line -> {
      final String[] pair = line.split( " " );
      return new Bid( new Hand( pair[0] ), Long.parseLong( pair[1] ) );
    } ).collect( toCollection( ArrayList::new ) );
  }

  private record Hand(List<Integer> cardsStrength, int typeStrength) implements Comparable<Hand> {

    private static Comparator<Hand> comparator;

    static {
      comparator = Comparator.comparing( Hand::typeStrength );
      for ( int i = 0; i < N_CARDS; i++ ) {
        final int finalI = i;
        comparator = comparator.thenComparing( hand -> hand.cardsStrength.get( finalI ) );
      }
    }

    Hand(String str) {
      this( computeCardsStrength( str ), computeTypeStrength( str ) );
    }

    private static List<Integer> computeCardsStrength(final String str) {
      return str.chars().mapToObj( c -> (char) c ).map( c -> switch ( c ) {
        case 'T' -> 10;
        case 'J' -> 11;
        case 'Q' -> 12;
        case 'K' -> 13;
        case 'A' -> 14;
        default -> Utils.charToInt( c );
      } ).toList();
    }

    private static int computeTypeStrength(final String str) {
      final Map<Character, Long> frequencies = str.chars().mapToObj( c -> (char) c )
          .collect( groupingBy( identity(), counting() ) );
      //remove jokers
      final Optional<Long> nJokers = Optional.ofNullable( frequencies.replace( JOKER, 0L ) );

      final List<Long> frequenciesSorted = frequencies.values().stream().sorted( reverseOrder() )
          .collect( toCollection( ArrayList::new ) );
      //pad to 5 elements
      while ( frequenciesSorted.size() < N_CARDS ) {
        frequenciesSorted.add( 0L );
      }

      int typeStrength = 0;
      for ( int i = 0; i < N_CARDS; i++ ) {
        typeStrength *= 10;
        typeStrength += frequenciesSorted.get( i );
        // use jokers for best type
        if ( i == 0 ) {
          typeStrength += nJokers.orElse( 0L );
        }
      }
      return typeStrength;
    }

    @Override
    public int compareTo(final Hand other) {
      return comparator.compare( this, other );
    }
  }

  private record Bid(Hand hand, long amount) {

  }
}
