package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.DOT;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;
import com.adventofcode.utils.Utils;
import java.util.stream.Stream;

class AoC042025 implements Solution {

  private static final char ROLL = '@';

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve(input, true);
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve(input, false);
  }

  private String solve(final Stream<String> input, final boolean first) {
    Character[][] grid = getGrid(input);
    int removed = 0;
    State state;
    do {
      state = removeRolls(grid);
      grid = state.grid();
      removed += state.removed();
    } while (state.removed() > 0 && !first);
    return itoa(removed);
  }

  private State removeRolls(Character[][] grid) {
    int rows = grid.length;
    int columns = grid[0].length;
    Character[][] nextGrid = new Character[rows][columns];
    int result = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        nextGrid[i][j] = grid[i][j];
        if (grid[i][j] == ROLL) {
          if (countRolls(grid, i, j, rows, columns) < 4) {
            //remove roll
            nextGrid[i][j] = DOT;
            result++;
          }
        }
      }
    }
    return new State(nextGrid, result);
  }

  private int countRolls(Character[][] grid, int i, int j, int rows, int columns) {
    return (int) Utils.NEIGHBOURS_8
        .stream()
        .map(offset -> new Pair<>(i + offset.getFirst(), j + offset.getSecond()))
        .filter(position -> position.getFirst() >= 0 && position.getFirst() < rows
            && position.getSecond() >= 0 && position.getSecond() < columns)
        .filter(position -> grid[position.getFirst()][position.getSecond()] == ROLL)
        .count();
  }

  private Character[][] getGrid(final Stream<String> input) {
    return input
        .map(row -> row.chars().mapToObj(c -> (char) c).toArray(Character[]::new))
        .toArray(Character[][]::new);
  }

  private record State(Character[][] grid, int removed) {}
}
