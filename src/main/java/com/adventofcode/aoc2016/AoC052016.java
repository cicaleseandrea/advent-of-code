package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.computeMD5AsBytes;
import static com.adventofcode.utils.Utils.getFirstString;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;

import com.adventofcode.Solution;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC052016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, (i, character) -> i, character -> 0 <= character && character < 16,
        bytes -> (int) bytes[2] );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, (i, character) -> character, position -> 0 <= position && position < 8,
        bytes -> {
          int character = bytes[3];
          // remove the last 4 bits (the eighth character)
          character = character >>> 4;
          // keep only the last 4 bits (the seventh character)
          character = character & 0xF;
          return character;
        } );
  }

  private String solve(final Stream<String> input, final IntBinaryOperator getPosition,
      final IntPredicate acceptCharacter, final ToIntFunction<byte[]> getCharacter) {
    final Map<Integer, Map<Integer, Character>> password = new ConcurrentHashMap<>();
    final String id = getFirstString( input );
    IntStream.iterate( 0, i -> password.size() < 8, i -> i + 1 ).parallel().forEach( i -> {
      final var bytes = computeMD5AsBytes( id + i );
      final int character = bytes[2];
      if ( bytes[0] == 0 && bytes[1] == 0 && acceptCharacter.test( character ) ) {
        final int position = getPosition.applyAsInt( i, character );

        password.computeIfAbsent( position, k -> new ConcurrentHashMap<>() )
            .put( i, Integer.toHexString( getCharacter.applyAsInt( bytes ) ).charAt( 0 ) );
      }
    } );
    return password.entrySet().stream().sorted( comparingInt( Map.Entry::getKey ) ).limit( 8 )
        .map( Map.Entry::getValue ).map(
            m -> m.entrySet().stream().min( comparingInt( Map.Entry::getKey ) )
                .map( Map.Entry::getValue ).orElseThrow() ).map( Object::toString )
        .collect( joining() );
  }
}
