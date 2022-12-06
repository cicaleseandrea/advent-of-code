package com.adventofcode.aoc2015;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.rangeClosed;

import com.adventofcode.Solution;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class AoC162015 implements Solution {

  private static final Pattern SUE_REGEX = Pattern.compile(
      "Sue (\\d+): ([a-zA-Z]+): (\\d+), ([a-zA-Z]+): (\\d+), ([a-zA-Z]+): (\\d+)" );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, (actual, goal) -> !actual.equals( goal.getValue() ) );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, this::advancedFilter );
  }

  private String solve(final Stream<String> input,
      final BiPredicate<Integer, Entry<String, Integer>> isWrongSue) {
    final var compounds = getCompounds( input );
    final var thisSue = Map.of( "children", 3, "cats", 7, "samoyeds", 2, "pomeranians", 3, "akitas",
        0, "vizslas", 0, "goldfish", 5, "trees", 3, "cars", 2, "perfumes", 1 );
    final var potentialSues = rangeClosed( 1, 500 ).boxed().collect( toCollection( HashSet::new ) );

    thisSue.entrySet().forEach( goal -> compounds.get( goal.getKey() ).entrySet().stream()
        .filter( s -> isWrongSue.test( s.getValue(), goal ) )
        .forEach( s -> potentialSues.remove( s.getKey() ) ) );

    assert potentialSues.size() == 1;
    return itoa( potentialSues.iterator().next() );
  }

  private boolean advancedFilter(final int actualAmount, final Entry<String, Integer> goal) {
    final var compound = goal.getKey();
    final var goalAmount = goal.getValue();
    return switch ( compound ) {
      case "cats", "trees" -> actualAmount <= goalAmount;
      case "pomeranians", "goldfish" -> actualAmount >= goalAmount;
      default -> actualAmount != goalAmount;
    };
  }

  private Map<String, Map<Integer, Integer>> getCompounds(Stream<String> input) {
    final var compounds = new HashMap<String, Map<Integer, Integer>>();
    input.map( SUE_REGEX::matcher ).filter( Matcher::matches ).forEach( matcher -> {
      final var sue = atoi( matcher.group( 1 ) );
      final var compoundA = matcher.group( 2 );
      final var amountA = atoi( matcher.group( 3 ) );
      final var compoundB = matcher.group( 4 );
      final var amountB = atoi( matcher.group( 5 ) );
      final var compoundC = matcher.group( 6 );
      final var amountC = atoi( matcher.group( 7 ) );
      compounds.computeIfAbsent( compoundA, k -> new HashMap<>() ).put( sue, amountA );
      compounds.computeIfAbsent( compoundB, k -> new HashMap<>() ).put( sue, amountB );
      compounds.computeIfAbsent( compoundC, k -> new HashMap<>() ).put( sue, amountC );
    } );
    return compounds;
  }
}
