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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import kotlin.jvm.functions.Function4;

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
    long sum;
    if ( first ) {
      final Map<String, Workflow> workflows = getWorkflows( inputIterator,
          workflow -> getWorkflow( workflow, Rule::new, AoC192023::createRule, Workflow::new ) );
      sum = getParts( inputIterator ).stream().filter( part -> isAccepted( "in", part, workflows ) )
          .mapToLong( Part::getTotal ).sum();
    } else {
      final Map<String, Workflow2> workflows = getWorkflows( inputIterator,
          workflow -> getWorkflow( workflow, Split::new, AoC192023::createSplit, Workflow2::new ) );
      sum = getRanges( "in", new Range( 1, 4000 ), workflows ).stream()
          .mapToLong( Range::countCombinations ).sum();
    }
    return itoa( sum );
  }

  private static List<Range> getRanges(final String start, final Range range,
      final Map<String, Workflow2> workflows) {
    if ( start.equals( REJECTED ) ) {
      return List.of();
    } else if ( start.equals( ACCEPTED ) ) {
      return List.of( range );
    }
    return workflows.get( start ).split( range ).stream()
        .map( next -> getRanges( next.getSecond(), next.getFirst(), workflows ) )
        .flatMap( Collection::stream ).toList();
  }

  private static boolean isAccepted(String start, final Part part,
      final Map<String, Workflow> workflows) {
    if ( start.equals( REJECTED ) ) {
      return false;
    } else if ( start.equals( ACCEPTED ) ) {
      return true;
    }
    return isAccepted( workflows.get( start ).send( part ), part, workflows );
  }

  private static List<Part> getParts(final Iterator<String> inputIterator) {
    final List<Part> parts = new ArrayList<>();
    while ( inputIterator.hasNext() ) {
      final List<Long> numbers = Utils.toLongList( inputIterator.next() );
      parts.add(
          new Part( numbers.get( 0 ), numbers.get( 1 ), numbers.get( 2 ), numbers.get( 3 ) ) );
    }
    return parts;
  }

  private static <T> Map<String, T> getWorkflows(final Iterator<String> inputIterator,
      Function<String, T> getWorkflow) {
    final Map<String, T> workflows = new HashMap<>();
    String line;
    while ( inputIterator.hasNext() && !(line = inputIterator.next()).isEmpty() ) {
      final int startRules = line.indexOf( '{' );
      final int endRules = line.length() - 1;
      final String name = line.substring( 0, startRules );
      final T workflow = getWorkflow.apply( line.substring( startRules + 1, endRules ) );
      workflows.put( name, workflow );
    }
    return workflows;
  }

  private static Rule createRule(final boolean greater, final int number, final char letter,
      final String next) {
    final Predicate<Part> check = part -> {
      long value = part.get( letter );
      return (greater && value > number) || (!greater && value < number);
    };
    return new Rule( check, next );
  }

  private static Split createSplit(final boolean greater, final int number, final char letter,
      final String next) {
    final UnaryOperator<Range> a = range -> {
      if ( greater ) {
        return range.updateMin( number + 1, letter );
      } else {
        return range.updateMax( number - 1, letter );
      }
    };
    final UnaryOperator<Range> b = range -> {
      if ( greater ) {
        return range.updateMax( number, letter );
      } else {
        return range.updateMin( number, letter );
      }
    };
    return new Split( a, b, next );
  }

  private static <T, R> R getWorkflow(final String workflow,
      final Function<String, T> toIntermediate1,
      final Function4<Boolean, Integer, Character, String, T> toIntermediate2,
      final Function<List<T>, R> toResult) {
    final List<T> splits = new ArrayList<>();
    for ( final String rule : workflow.split( "," ) ) {
      final String[] checkArr = rule.split( ":" );
      if ( checkArr.length == 1 ) {
        splits.add( toIntermediate1.apply( rule ) );
      } else {
        final boolean greater = checkArr[0].contains( ">" );
        final char letter = checkArr[0].charAt( 0 );
        final int number = Utils.extractIntegerFromString( rule );
        splits.add( toIntermediate2.invoke( greater, number, letter, checkArr[1] ) );
      }
    }
    return toResult.apply( splits );
  }

  private record Workflow(List<Rule> rules) {

    String send(Part part) {
      for ( final Rule rule : rules ) {
        if ( rule.check.test( part ) ) {
          return rule.next;
        }
      }
      throw new IllegalArgumentException();
    }
  }

  private record Rule(Predicate<Part> check, String next) {

    Rule(String next) {
      this( part -> true, next );
    }
  }


  private record Workflow2(List<Split> splits) {

    List<Pair<Range, String>> split(Range range) {
      final List<Pair<Range, String>> list = new ArrayList<>();
      for ( final Split split : splits ) {
        Range curr = split.a.apply( range );
        range = split.b.apply( range );
        list.add( new Pair<>( curr, split.next ) );
      }
      return list;
    }
  }

  private record Split(UnaryOperator<Range> a, UnaryOperator<Range> b, String next) {

    Split(String next) {
      this( identity(), identity(), next );
    }
  }

  private record Part(long x, long m, long a, long s) {

    long get(char c) {
      return switch ( c ) {
        case 'x' -> x;
        case 'm' -> m;
        case 'a' -> a;
        case 's' -> s;
        default -> throw new IllegalStateException( "Unexpected value: " + c );
      };
    }

    long getTotal() {
      return x + m + a + s;
    }
  }

  private record Range(int minX, int maxX, int minM, int maxM, int minA, int maxA, int minS,
                       int maxS) {

    Range(final int min, final int max) {
      this( min, max, min, max, min, max, min, max );
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
      return (long) (maxX - minX + 1) * (maxM - minM + 1) * (maxA - minA + 1) * (maxS - minS + 1);
    }
  }
}
