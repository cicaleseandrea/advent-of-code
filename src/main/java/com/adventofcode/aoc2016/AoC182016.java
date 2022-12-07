package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.shouldPrint;
import static java.util.Arrays.stream;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.stream.Stream;

class AoC182016 implements Solution {

  public static final char SAFE = Utils.WHITE;
  public static final char TRAP = Utils.BLACK;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, 40 );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, 400000 );
  }

  private String solve(final Stream<String> input, final int inputRows) {
    var row = input.flatMapToInt( String::chars ).map( c -> c == '.' ? SAFE : TRAP )
        .mapToObj( c -> (char) c ).toArray( Character[]::new );
    final var totalRows = row.length > 10 ? inputRows : 10;
    printRow( row, totalRows );

    var res = stream( row ).filter( c -> c == SAFE ).count();
    for ( int i = 0; i < totalRows - 1; i++ ) {
      final var tmp = new Character[row.length];
      for ( int j = 0; j < row.length; j++ ) {
        final var left = getOrDefault( row, j - 1 );
        final var right = getOrDefault( row, j + 1 );
        final var c = right != left ? TRAP : SAFE;
        tmp[j] = c;
        if ( c == SAFE ) {
          res++;
        }
      }
      row = tmp;
      printRow( row, totalRows );
    }

    return itoa( res );
  }

  private char getOrDefault(final Character[] row, final int i) {
    if ( i >= 0 && i < row.length ) {
      return row[i];
    } else {
      return SAFE;
    }
  }

  private void printRow(final Character[] row, final int totalRows) {
    if ( shouldPrint() && totalRows < 100 ) {
      stream( row ).forEach( System.out::print );
      System.out.println();
    }
  }
}
