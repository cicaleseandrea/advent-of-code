package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.util.function.UnaryOperator.identity;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

class AoC192023 implements Solution {

  private static final String ACCEPTED = "A";
  private static final String REJECTED = "R";

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private static String solve(final Stream<String> input, final boolean first) {
    final Iterator<String> inputIterator = input.iterator();
    final Map<String, Workflow> workflows = getWorkflows( inputIterator );
    final List<Range> starts = first ? getParts( inputIterator ) : List.of( new Range( 1, 4000 ) );

    final long sum = starts.stream().map( range -> getRanges( "in", range, workflows, first ) )
        .flatMap( Collection::stream ).mapToLong( first ? Range::getSum : Range::countCombinations )
        .sum();
    return itoa( sum );
  }

  private static List<Range> getRanges(final String workflowName, final Range range,
      final Map<String, Workflow> workflows, final boolean first) {
    if ( workflowName.equals( REJECTED ) ) {
      return List.of();
    } else if ( workflowName.equals( ACCEPTED ) ) {
      return List.of( range );
    }
    final Workflow workflow = workflows.get( workflowName );
    if ( first ) {
      return getRanges( workflow.getNext( range ), range, workflows, first );
    } else {
      return workflow.splitRange( range ).stream()
          .map( next -> getRanges( next.getFirst(), next.getSecond(), workflows, first ) )
          .flatMap( Collection::stream ).toList();
    }
  }

  private static List<Range> getParts(final Iterator<String> inputIterator) {
    final List<Range> parts = new ArrayList<>();
    while ( inputIterator.hasNext() ) {
      final List<Long> numbers = Utils.toLongList( inputIterator.next() );
      parts.add( new Range( numbers.get( 0 ).intValue(), numbers.get( 1 ).intValue(),
          numbers.get( 2 ).intValue(), numbers.get( 3 ).intValue() ) );
    }
    return parts;
  }

  private static Map<String, Workflow> getWorkflows(final Iterator<String> inputIterator) {
    final Map<String, Workflow> workflows = new HashMap<>();
    String line;
    while ( inputIterator.hasNext() && !(line = inputIterator.next()).isEmpty() ) {
      final int startRules = line.indexOf( '{' );
      final int endRules = line.length() - 1;
      final String name = line.substring( 0, startRules );
      workflows.put( name, getWorkflow( line.substring( startRules + 1, endRules ) ) );
    }
    return workflows;
  }

  private static Workflow getWorkflow(final String workflow) {
    final List<Rule> rules = Stream.of( workflow.split( "," ) ).map( rule -> rule.split( ":" ) )
        .map( rule -> {
          if ( rule.length == 1 ) {
            return new Rule( rule[0] );
          } else {
            final boolean greater = rule[0].contains( ">" );
            final char letter = rule[0].charAt( 0 );
            final int number = Utils.extractIntegerFromString( rule[0] );
            final String next = rule[1];
            return getRule( greater, number, letter, next );
          }
        } ).toList();
    return new Workflow( rules );
  }

  private static Rule getRule(final boolean greater, final int number, final char letter,
      final String next) {
    final Predicate<Range> check = part -> {
      final long value = part.get( letter );
      return (greater && value > number) || (!greater && value < number);
    };
    final UnaryOperator<Range> left = range -> {
      if ( greater ) {
        return range.updateMin( number + 1, letter );
      } else {
        return range.updateMax( number - 1, letter );
      }
    };
    final UnaryOperator<Range> right = range -> {
      if ( greater ) {
        return range.updateMax( number, letter );
      } else {
        return range.updateMin( number, letter );
      }
    };
    return new Rule( check, left, right, next );
  }

  private record Workflow(List<Rule> rules) {

    String getNext(final Range range) {
      for ( final Rule rule : rules ) {
        if ( rule.check.test( range ) ) {
          return rule.next;
        }
      }
      throw new IllegalArgumentException();
    }

    List<Pair<String, Range>> splitRange(Range toSplit) {
      final List<Pair<String, Range>> list = new ArrayList<>();
      for ( final Rule rule : rules ) {
        final Range split = rule.left.apply( toSplit );
        toSplit = rule.right.apply( toSplit );
        list.add( new Pair<>( rule.next, split ) );
      }
      return list;
    }
  }


  private record Rule(Predicate<Range> check, UnaryOperator<Range> left, UnaryOperator<Range> right,
                      String next) {

    Rule(String next) {
      this( part -> true, identity(), identity(), next );
    }
  }


  private record Range(int minX, int maxX, int minM, int maxM, int minA, int maxA, int minS,
                       int maxS) {

    Range(final int min, final int max) {
      this( min, max, min, max, min, max, min, max );
    }

    Range(final int x, final int m, final int a, final int s) {
      this( x, x, m, m, a, a, s, s );
    }

    int get(char c) {
      return switch ( c ) {
        case 'x' -> minX;
        case 'm' -> minM;
        case 'a' -> minA;
        case 's' -> minS;
        default -> throw new IllegalStateException( "Unexpected value: " + c );
      };
    }

    Range updateMin(int min, char c) {
      return switch ( c ) {
        case 'x' -> new Range( min, maxX, minM, maxM, minA, maxA, minS, maxS );
        case 'm' -> new Range( minX, maxX, min, maxM, minA, maxA, minS, maxS );
        case 'a' -> new Range( minX, maxX, minM, maxM, min, maxA, minS, maxS );
        case 's' -> new Range( minX, maxX, minM, maxM, minA, maxA, min, maxS );
        default -> throw new IllegalStateException( "Unexpected value: " + c );
      };
    }

    Range updateMax(int max, char c) {
      return switch ( c ) {
        case 'x' -> new Range( minX, max, minM, maxM, minA, maxA, minS, maxS );
        case 'm' -> new Range( minX, maxX, minM, max, minA, maxA, minS, maxS );
        case 'a' -> new Range( minX, maxX, minM, maxM, minA, max, minS, maxS );
        case 's' -> new Range( minX, maxX, minM, maxM, minA, maxA, minS, max );
        default -> throw new IllegalStateException( "Unexpected value: " + c );
      };
    }

    long countCombinations() {
      return (maxX - minX + 1L) * (maxM - minM + 1L) * (maxA - minA + 1L) * (maxS - minS + 1L);
    }

    long getSum() {
      return minX + minM + minA + minS;
    }
  }
}
