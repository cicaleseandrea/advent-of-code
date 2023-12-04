package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;
import java.util.stream.Stream;

class AoC042023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private static String solve(final Stream<String> input, final boolean first) {
    final List<Card> cards = getCards( input );
    long points = 0;
    final long[] cardCount = new long[cards.size()];
    //count original cards
    Arrays.fill( cardCount, 1 );

    for ( int cardNumber = 0; cardNumber < cards.size(); cardNumber++ ) {
      final int matchingNumbers = cards.get( cardNumber ).countMatchingNumbers();
      if ( matchingNumbers > 0 ) {
        points += (1L << matchingNumbers - 1);
      }

      //count cards won
      for ( int j = 1; j <= matchingNumbers && cardNumber + j < cards.size(); j++ ) {
        cardCount[cardNumber + j] += cardCount[cardNumber];
      }
    }

    if ( first ) {
      return itoa( points );
    } else {
      return itoa( LongStream.of( cardCount ).sum() );
    }
  }

  private static List<Card> getCards(final Stream<String> input) {
    return input.map( cardStr -> cardStr.split( ":" )[1] )
        .map( numbersStr -> numbersStr.split( "\\|" ) )
        .map( numbersStr -> new Card( getNumbers( numbersStr[0] ), getNumbers( numbersStr[1] ) ) )
        .toList();
  }

  private static Set<Long> getNumbers(final String numbers) {
    return Utils.toLongStream( numbers ).collect( toSet() );
  }

  private record Card(Set<Long> winning, Set<Long> mine) {

    private int countMatchingNumbers() {
      return Sets.intersection( winning, mine ).size();
    }
  }
}
