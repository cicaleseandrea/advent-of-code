package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.NEIGHBOURS_8;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Character.isDigit;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;

class AoC032023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, Optional.empty(), AoC032023::getSum );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, Optional.of( '*' ), AoC032023::getGearRatio );
  }

  private static String solve(final Stream<String> input, Optional<Character> symbol,
      ToLongFunction<Collection<Long>> reduce) {
    final List<List<Character>> matrix = Utils.getCharMatrix( input );
    final Multimap<Pair<Integer, Integer>, Long> symbolToNumber = getSymbolToNumber( matrix,
        symbol );
    return itoa( symbolToNumber.asMap().values().stream().mapToLong( reduce ).sum() );
  }

  private static Multimap<Pair<Integer, Integer>, Long> getSymbolToNumber(
      final List<List<Character>> matrix, final Optional<Character> symbol) {
    final Multimap<Pair<Integer, Integer>, Long> symbolToNumbers = ArrayListMultimap.create();
    long number = 0;
    Pair<Integer, Integer> symbolPosition = null;
    for ( int i = 0; i < matrix.size(); i++ ) {
      for ( int j = 0; j < matrix.get( i ).size(); j++ ) {
        final char c = matrix.get( i ).get( j );
        if ( isDigit( c ) ) {
          number *= 10;
          number += Utils.charToInt( c );
          if ( symbolPosition == null ) {
            symbolPosition = getSymbolPosition( matrix, i, j, symbol );
          }
        } else {
          saveNumber( symbolToNumbers, symbolPosition, number );
          number = 0;
          symbolPosition = null;
        }
      }
      saveNumber( symbolToNumbers, symbolPosition, number );
      number = 0;
      symbolPosition = null;
    }

    return symbolToNumbers;
  }

  private static void saveNumber(final Multimap<Pair<Integer, Integer>, Long> symbolToNumbers,
      final Pair<Integer, Integer> symbolPosition, final long number) {
    if ( symbolPosition != null ) {
      symbolToNumbers.put( symbolPosition, number );
    }
  }

  private static Pair<Integer, Integer> getSymbolPosition(final List<List<Character>> matrix,
      final int i, final int j, final Optional<Character> symbol) {
    for ( final Pair<Integer, Integer> n : NEIGHBOURS_8 ) {
      final int nI = i + n.getFirst();
      final List<Character> row = Utils.listGetOrDefault( matrix, nI, List.of() );
      final int nJ = j + n.getSecond();
      final char c = Utils.listGetOrDefault( row, nJ, DOT );
      if ( c != DOT && !Character.isDigit( c ) && (symbol.isEmpty() || c == symbol.get()) ) {
        return new Pair<>( nI, nJ );
      }
    }
    return null;
  }

  private static long getSum(final Collection<Long> numbers) {
    return numbers.stream().reduce( 0L, Long::sum );
  }

  private static long getGearRatio(final Collection<Long> numbers) {
    if ( numbers.size() < 2 ) {
      return 0;
    } else {
      return numbers.stream().reduce( 1L, (a, b) -> a * b );
    }
  }
}
