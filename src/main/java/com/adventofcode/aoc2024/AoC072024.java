package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.stream.Stream;

class AoC072024 implements Solution {

  private static final LongBinaryOperator SUM = Long::sum;
  private static final LongBinaryOperator MUL = (a, b) -> a * b;
  private static final LongBinaryOperator CONCAT = (a, b) -> Long.parseLong( a + "" + b );

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, List.of( SUM, MUL ) );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, List.of( SUM, MUL, CONCAT ) );
  }

  private String solve(final Stream<String> input, final List<LongBinaryOperator> operators) {
    return itoa(
        input.map( Utils::toLongList ).mapToLong( numbers -> getResult( numbers, operators ) )
            .sum() );
  }

  private long getResult(final List<Long> numbers, final List<LongBinaryOperator> operators) {
    long result = numbers.get( 0 );
    List<Long> operands = numbers.subList( 1, numbers.size() );
    return hasResult( result, operands.get( 0 ), operands, 1, operators ) ? result : 0;
  }

  private boolean hasResult(final long total, final long tmpResult, final List<Long> operands,
      final int index, final List<LongBinaryOperator> operators) {
    if ( index == operands.size() ) {
      return tmpResult == total;
    }
    for ( LongBinaryOperator operator : operators ) {
      long updatedTmpResult = operator.applyAsLong( tmpResult, operands.get( index ) );
      if ( updatedTmpResult <= total ) {
        if ( hasResult( total, updatedTmpResult, operands, index + 1, operators ) ) {
          return true;
        }
      }
    }
    return false;
  }
}
