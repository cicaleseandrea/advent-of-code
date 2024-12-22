package com.adventofcode.aoc2024;


import static com.adventofcode.utils.Direction.DOWN;
import static com.adventofcode.utils.Direction.LEFT;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Direction.UP;
import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC212024 implements Solution {

  private static final char A = 'A';
  private static final char ZERO = '0';
  private static final char ONE = '1';
  private static final Map<Character, Point> NUMPAD = getNumPad();
  private static final Map<Character, Point> ARROW_KEYS = getArrowKeys();
  private static final Map<State, Long> CACHE = new HashMap<>();

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 3 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 26 );
  }

  private String solve(final Stream<String> input, final int steps) {
    long result = input.mapToLong( code -> getComplexity( code, steps ) ).sum();
    return itoa( result );
  }

  private long getComplexity(final String code, final int steps) {
    List<Character> codeButtons = code.chars().mapToObj( c -> (char) c ).toList();
    long length = getPresses( codeButtons, steps, true );
    long number = extractIntegerFromString( code );
    return length * number;
  }

  private long getPresses(final List<Character> input, final int steps, final boolean isNumpad) {
    State state = new State( input, steps );
    if ( CACHE.containsKey( state ) ) {
      return CACHE.get( state );
    }
    if ( steps == 0 ) {
      CACHE.put( state, (long) input.size() );
      return input.size();
    }
    long result = 0;
    for ( int i = 0; i < input.size(); i++ ) {
      char from = i == 0 ? A : input.get( i - 1 );
      char to = input.get( i );
      List<Character> presses = getPresses( from, to, isNumpad );
      result += getPresses( presses, steps - 1, false );
    }
    CACHE.put( state, result );
    return result;
  }

  private List<Character> getPresses(final char from, final char to, final boolean isNumpad) {
    Map<Character, Point> keyPad = isNumpad ? NUMPAD : ARROW_KEYS;
    Point fromPos = keyPad.get( from );
    Point toPos = keyPad.get( to );
    int diffI = toPos.i() - fromPos.i();
    int diffJ = toPos.j() - fromPos.j();
    //mind the gap! presses priorities: left, down, up, right
    boolean moveHorizontallyFirst;
    if ( diffJ < 0
        && !(isNumpad && fromPos.i() == 3 && toPos.j() == 0)
        && !(!isNumpad && fromPos.i() == 0 && toPos.j() == 0) ) {
      //if possible, go left first
      moveHorizontallyFirst = true;
    } else {
      //if needed to avoid the gap, go right first
      moveHorizontallyFirst = (isNumpad && fromPos.j() == 0 && toPos.i() == 3)
          || (!isNumpad && fromPos.j() == 0 && toPos.i() == 0);
      //otherwise, vertical first
    }
    List<Character> presses = new ArrayList<>();
    presses.addAll( getPresses( diffI, diffJ, moveHorizontallyFirst ) );
    presses.add( A );
    return presses;
  }

  private List<Character> getPresses(final int diffI, final int diffJ,
      final boolean moveHorizontallyFirst) {
    Stream<Character> vertical = IntStream
        .range( 0, Math.abs( diffI ) )
        .mapToObj( i -> diffI > 0 ? DOWN : UP )
        .map( Direction::getSymbol );
    Stream<Character> horizontal = IntStream
        .range( 0, Math.abs( diffJ ) )
        .mapToObj( i -> diffJ > 0 ? RIGHT : LEFT )
        .map( Direction::getSymbol );
    return Stream.concat(
            moveHorizontallyFirst ? horizontal : vertical,
            moveHorizontallyFirst ? vertical : horizontal )
        .toList();
  }

  private static Map<Character, Point> getNumPad() {
    Map<Character, Point> numpad = new HashMap<>();
    for ( int i = 0; i < 3; i++ ) {
      for ( int j = 0; j < 3; j++ ) {
        numpad.put( (char) (ONE + 3 * (3 - 1 - i) + j), new Point( i, j ) );
      }
    }
    numpad.put( ZERO, new Point( 3, 1 ) );
    numpad.put( A, new Point( 3, 2 ) );
    return numpad;
  }

  private static Map<Character, Point> getArrowKeys() {
    Map<Character, Point> arrowKeys = new HashMap<>();
    arrowKeys.put( UP.getSymbol(), new Point( 0, 1 ) );
    arrowKeys.put( A, new Point( 0, 2 ) );
    arrowKeys.put( LEFT.getSymbol(), new Point( 1, 0 ) );
    arrowKeys.put( DOWN.getSymbol(), new Point( 1, 1 ) );
    arrowKeys.put( RIGHT.getSymbol(), new Point( 1, 2 ) );
    return arrowKeys;
  }

  private record State(List<Character> seq, int steps) {

  }

}
