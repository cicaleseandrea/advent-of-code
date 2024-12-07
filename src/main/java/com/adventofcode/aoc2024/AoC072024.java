package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
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
    Long result = numbers.get( 0 );
    Iterator<Long> operands = numbers.subList( 1, numbers.size() ).iterator();
    List<Long> tmpResults = List.of( operands.next() );
    return hasResult( result, operands, tmpResults, operators ) ? result : 0;
  }

  private boolean hasResult(final Long result, final Iterator<Long> operands,
      final List<Long> tmpResults, final List<LongBinaryOperator> operators) {
    if ( !operands.hasNext() ) {
      return tmpResults.stream().anyMatch( n -> n.equals( result ) );
    }
    long operand = operands.next();
    List<Long> updatedTmpResults = new ArrayList<>();
    for ( LongBinaryOperator operator : operators ) {
      for ( Long tmpResult : tmpResults ) {
        long updatedTmpResult = operator.applyAsLong( tmpResult, operand );
        if ( updatedTmpResult <= result ) {
          updatedTmpResults.add( updatedTmpResult );
        }
      }
    }
    return hasResult( result, operands, updatedTmpResults, operators );
  }
}
