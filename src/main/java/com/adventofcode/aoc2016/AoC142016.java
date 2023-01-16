package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.computeMD5AsString;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AoC142016 implements Solution {

  private static final Pattern REGEX_THREE = Pattern.compile( "(.)\\1{2}" );
  private static final Pattern REGEX_FIVE = Pattern.compile( "(.)\\1{4}" );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    final var salt = getFirstString( input );
    final Multimap<String, Character> fives = HashMultimap.create();
    final Queue<String> hashes = new LinkedList<>();

    // initialization
    for ( int i = 0; i < 1000; i++ ) {
      addHash( salt, i, hashes, fives, first );
    }

    for ( int i = 0, keys = 0; ; i++ ) {
      addHash( salt, i + 1000, hashes, fives, first );
      final var hash = hashes.poll();
      fives.removeAll( hash );
      if ( fives.containsValue( computeThree( hash ) ) && ++keys == 64 ) {
        return itoa( i );
      }
    }
  }

  private void addHash(final String salt, final int i, final Queue<String> hashes,
      final Multimap<String, Character> fives, final boolean first) {
    String hash = salt + i;
    for ( int j = 0; j < (first ? 1 : 2017); j++ ) {
      hash = computeMD5AsString( hash );
    }
    hashes.add( hash );
    fives.putAll( hash, computeFives( hash ) );
  }

  private Character computeThree(final String hash) {
    final var matcher = REGEX_THREE.matcher( hash );
    return matcher.find() ? matcher.group( 1 ).charAt( 0 ) : null;
  }

  private Set<Character> computeFives(final String hash) {
    final var matcher = REGEX_FIVE.matcher( hash );
    return matcher.results().map( r -> r.group( 1 ).charAt( 0 ) ).collect( Collectors.toSet() );
  }
}
