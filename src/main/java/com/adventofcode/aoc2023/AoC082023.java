package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class AoC082023 implements Solution {

  private static final String START = "AAA";
  private static final String END = "ZZZ";
  private static final Pattern NODE_REGEX = Pattern.compile( "(\\w+) = \\((\\w+), (\\w+)\\)" );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, n -> n.equals( START ), n -> n.equals( END ) );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, n -> n.charAt( 2 ) == START.charAt( 2 ),
        n -> n.charAt( 2 ) == END.charAt( 2 ) );
  }

  private static String solve(final Stream<String> input, final Predicate<String> startTest,
      final Predicate<String> stopTest) {
    final Iterator<String> inputIterator = input.iterator();
    final List<Direction> directions = inputIterator.next().chars().mapToObj( c -> (char) c )
        .map( Direction::get ).toList();
    inputIterator.next();
    final Map<String, Node> nodes = getNodes( inputIterator );

    final Set<String> startNodes = nodes.keySet().stream().filter( startTest ).collect( toSet() );
    final long steps = startNodes.stream()
        .map( node -> countSteps( node, directions, nodes, stopTest ) ).reduce( 1L, Utils::lcm );
    return itoa( steps );
  }

  private static long countSteps(String current, final List<Direction> directions,
      final Map<String, Node> nodes, final Predicate<String> stop) {
    long steps = 0;
    while ( !stop.test( current ) ) {
      final Direction direction = directions.get( (int) (steps % directions.size()) );
      current = nodes.get( current ).move( direction );
      steps++;
    }
    return steps;
  }

  private static Map<String, Node> getNodes(final Iterator<String> inputIterator) {
    final Map<String, Node> nodes = new HashMap<>();
    while ( inputIterator.hasNext() ) {
      final Matcher matcher = NODE_REGEX.matcher( inputIterator.next() );
      if ( !matcher.matches() ) {
        throw new IllegalArgumentException();
      }
      final String nodeName = matcher.group( 1 );
      final String left = matcher.group( 2 );
      final String right = matcher.group( 3 );
      nodes.put( nodeName, new Node( nodeName, left, right ) );
    }
    return nodes;
  }

  private record Node(String name, String left, String right) {

    String move(Direction direction) {
      return switch ( direction ) {
        case LEFT -> left;
        case RIGHT -> right;
      };
    }
  }

  private enum Direction {
    LEFT, RIGHT;

    static Direction get(char c) {
      return switch ( c ) {
        case 'L' -> LEFT;
        case 'R' -> RIGHT;
        default -> throw new IllegalStateException( "Unexpected value: " + c );
      };
    }
  }

}
