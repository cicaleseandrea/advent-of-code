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
import java.util.function.ToLongFunction;
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
    final Iterator<String> inputIterator = input.iterator();
    final Map<String, Workflow2> workflows = getWorkflows2( inputIterator );

    final long combinations = getRanges( new Range( 1, 4000 ), workflows, "in" ).stream().mapToLong(
        Range::countCombinations ).sum();
    return itoa( combinations );
  }

  private static List<Range> getRanges(final Range range, final Map<String, Workflow2> workflows,
      final String start) {
    if ( start.equals( REJECTED ) ) {
      return List.of();
    } else if ( start.equals( ACCEPTED ) ) {
      return List.of( range );
    }

    return workflows.get( start ).split( range )
        .stream().map( next -> getRanges( next.getFirst(), workflows, next.getSecond() ) )
        .flatMap( Collection::stream ).toList();
  }

  private static String solve(final Stream<String> input, final boolean first) {
    final Iterator<String> inputIterator = input.iterator();
    final Map<String, Workflow> workflows = getWorkflows( inputIterator );
    final List<Part> parts = getParts( inputIterator );

    final long sum = parts.stream().filter( part -> isAccepted( part, workflows, "in" ) )
        .mapToLong( Part::getTotal ).sum();
    return itoa( sum );
  }

  private static List<Part> getParts(final Iterator<String> inputIterator) {
    final List<Part> parts = new ArrayList<>();
    while ( inputIterator.hasNext() ) {
      final List<Long> numbers = Utils.toLongList( inputIterator.next() );
      parts.add( new Part( numbers.get( 0 ), numbers.get( 1 ), numbers.get( 2 ),
          numbers.get( 3 ) ) );
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
      final Workflow workflow = getWorkflow( line.substring( startRules + 1, endRules ) );
      workflows.put( name, workflow );
    }
    return workflows;
  }

  private static Map<String, Workflow2> getWorkflows2(final Iterator<String> inputIterator) {
    final Map<String, Workflow2> workflows = new HashMap<>();
    String line;
    while ( inputIterator.hasNext() && !(line = inputIterator.next()).isEmpty() ) {
      final int startRules = line.indexOf( '{' );
      final int endRules = line.length() - 1;
      final String name = line.substring( 0, startRules );
      final Workflow2 workflow = getWorkflow2( line.substring( startRules + 1, endRules ) );
      workflows.put( name, workflow );
    }
    return workflows;
  }

  private static Workflow2 getWorkflow2(final String workflow) {
    final List<Split> splits = new ArrayList<>();
    for ( final String rule : workflow.split( "," ) ) {
      final String[] checkArr = rule.split( ":" );
      if ( checkArr.length == 1 ) {
        splits.add( new Split( rule ) );
      } else {
        final boolean greater = checkArr[0].contains( ">" );
        final char letter = checkArr[0].charAt( 0 );
        final int number = Utils.extractIntegerFromString( rule );
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
        splits.add( new Split( a, b, checkArr[1] ) );
      }
    }
    return new Workflow2( splits );
  }

  private static Workflow getWorkflow(final String workflow) {
    final List<Rule> rules = new ArrayList<>();
    for ( final String rule : workflow.split( "," ) ) {
      final String[] checkArr = rule.split( ":" );
      if ( checkArr.length == 1 ) {
        rules.add( new Rule( part -> true, rule ) );
      } else {
        final boolean greater = checkArr[0].contains( ">" );
        final ToLongFunction<Part> extract =
            switch ( Utils.toWordList( rule ).get( 0 ) ) {
              case "x" -> part -> part.x;
              case "m" -> part -> part.m;
              case "a" -> part -> part.a;
              case "s" -> part -> part.s;
              default -> throw new IllegalStateException(
                  "Unexpected value: " + Utils.toWordList( rule ).get( 0 ) );
            };
        final long number = Utils.extractIntegerFromString( rule );
        final Predicate<Part> check = part -> {
          long value = extract.applyAsLong( part );
          return (greater && value > number) || (!greater && value < number);
        };
        rules.add( new Rule( check, checkArr[1] ) );
      }
    }
    return new Workflow( rules );
  }

  private static boolean isAccepted(final Part part, final Map<String, Workflow> workflows,
      String curr) {
    while ( !curr.equals( ACCEPTED ) && !curr.equals( REJECTED ) ) {
      curr = workflows.get( curr ).send( part );
    }

    return curr.equals( ACCEPTED );
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

    long getTotal() {
      return x + m + a + s;
    }
  }

  private record Range(int minX, int maxX, int minM, int maxM, int minA, int maxA, int minS,
                       int maxS) {

    static Range empty() {
      return new Range( 1, 0 );
    }

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
