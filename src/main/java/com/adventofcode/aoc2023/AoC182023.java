package com.adventofcode.aoc2023;

import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Integer.parseInt;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Point;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

class AoC182023 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, AoC182023::getInstructionFirstPart );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, AoC182023::getInstructionSecondPart );
  }

  private static String solve(final Stream<String> input,
      final Function<String, Instruction> getInstruction) {
    final List<Instruction> instructions = input.map( getInstruction ).toList();
    final List<Point> points = getPoints( instructions );
    final long nBoundaryPoints = instructions.stream().mapToLong( Instruction::distance ).sum();
    final long nInteriorPoints = Point.countPointsInside( points, nBoundaryPoints );
    return itoa( nBoundaryPoints + nInteriorPoints );
  }

  private static List<Point> getPoints(final List<Instruction> instructions) {
    final List<Point> points = new ArrayList<>();
    Point curr = new Point( 0, 0 );
    for ( final Instruction instruction : instructions ) {
      curr = curr.move( instruction.direction, instruction.distance );
      points.add( curr );
    }
    return points;
  }

  private static Instruction getInstructionFirstPart(String str) {
    final List<String> info = Utils.splitOnTabOrSpace( str );
    final Direction direction = switch ( info.get( 0 ) ) {
      case "R" -> Direction.RIGHT;
      case "D" -> Direction.DOWN;
      case "L" -> Direction.LEFT;
      case "U" -> Direction.UP;
      default -> throw new IllegalStateException( "Unexpected value: " + info.get( 0 ) );
    };
    final int distance = parseInt( info.get( 1 ) );
    return new Instruction( direction, distance );
  }

  private static Instruction getInstructionSecondPart(String str) {
    final List<String> info = Utils.splitOnTabOrSpace( str );
    final String hexCode = info.get( 2 );
    final Direction direction = switch ( hexCode.charAt( 7 ) ) {
      case '0' -> Direction.RIGHT;
      case '1' -> Direction.DOWN;
      case '2' -> Direction.LEFT;
      case '3' -> Direction.UP;
      default -> throw new IllegalStateException( "Unexpected value: " + info.get( 0 ) );
    };
    final int distance = parseInt( hexCode.substring( 2, 7 ), 16 );
    return new Instruction( direction, distance );
  }

  private record Instruction(Direction direction, int distance) {

  }
}
