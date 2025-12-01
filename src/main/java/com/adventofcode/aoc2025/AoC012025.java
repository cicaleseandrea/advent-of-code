package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.atoi;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.base.Preconditions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class AoC012025 implements Solution {

  private static final int MAX_DIAL = 100;
  private static final Pattern ROTATION_REGEX = Pattern.compile(
      "([L|R])(\\d+)");

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve(input, true);
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve(input, false);
  }

  private String solve(final Stream<String> input, final boolean first) {
    int position = 50;
    int result = 0;
    for (String line : input.toList()) {
      Matcher matcher = ROTATION_REGEX.matcher(line);
      Preconditions.checkArgument(matcher.matches());
      String direction = matcher.group(1);
      int distance = atoi(matcher.group(2));
      int zeroes = 0;
      switch (direction) {
        case "R" -> position += distance;
        case "L" -> {
          if (position > 0) {
            zeroes = 1;
          }
          position -= distance;
          if (position > 0) {
            zeroes = 0;
          }
        }
        default -> throw new IllegalStateException("Unexpected direction: " + direction);
      }
      zeroes += Math.abs(position / MAX_DIAL);
      position = Math.floorMod(position, MAX_DIAL);

      if (!first) {
        result += zeroes;
      } else if (position == 0) {
        result++;
      }
    }
    return Utils.itoa(result);
  }

}
