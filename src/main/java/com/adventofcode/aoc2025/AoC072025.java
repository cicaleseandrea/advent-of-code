package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.stream.Stream;

class AoC072025 implements Solution {
  private static final char START = 'S';
  private static final char SPLITTER = '^';
  private static final long SPLITTER_NUM = -1;
  private static final long BEAM_NUM = 1;

  @Override
  public String solveFirstPart(Stream<String> input) {
    return solve(input, true);
  }

  @Override
  public String solveSecondPart(Stream<String> input) {
    return solve(input, false);
  }

  private String solve(Stream<String> input, boolean first) {
    Long[][] grid = getGrid(input);
    int splits = 0;
    for (int i = 0; i < grid.length - 1; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] >= BEAM_NUM) {
          if (grid[i + 1][j] == SPLITTER_NUM) {
            grid[i + 1][j - 1] += grid[i][j];
            grid[i + 1][j + 1] += grid[i][j];
            splits++;
          } else {
            grid[i + 1][j] += grid[i][j];
          }
        }
      }
    }
    if (first) {
      return itoa(splits);
    } else {
      return itoa(Stream.of(grid[grid.length - 1]).mapToLong(n -> n).sum());
    }
  }

  private Long[][] getGrid(final Stream<String> input) {
    return input
        .map(row ->
            row.chars()
                .mapToObj(c ->
                    switch (c) {
                      case START -> BEAM_NUM;
                      case SPLITTER -> SPLITTER_NUM;
                      default -> 0L;
                    })
                .toArray(Long[]::new))
        .toArray(Long[][]::new);
  }
}
