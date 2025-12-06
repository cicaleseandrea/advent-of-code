package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.EMPTY;
import static com.adventofcode.utils.Utils.PLUS;
import static com.adventofcode.utils.Utils.SPACE;
import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toCollection;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC062025 implements Solution {

  @Override
  public String solveFirstPart(Stream<String> input) {
    List<List<String>> rows = input.map(String::trim).map(Utils::splitOnTabOrSpace).toList();
    int digits = rows.get(0).size();
    long result = 0;
    for (int j = 0; j < digits; j++) {
      result += solveProblem(rows, j);
    }
    return itoa(result);
  }

  @Override
  public String solveSecondPart(Stream<String> input) {
    List<List<Character>> rows = Utils.getCharMatrix(input);
    int maxRowLength = rows.stream().mapToInt(List::size).max().orElseThrow();
    List<Character> symbols = rows.get(rows.size() - 1);
    ArrayList<Integer> problemsPositions = IntStream
        .range(0, symbols.size())
        .filter(i -> symbols.get(i) != SPACE)
        .boxed()
        .collect(toCollection(ArrayList::new));
    problemsPositions.add(maxRowLength);

    long result = 0;
    for (int i = 0; i < problemsPositions.size() - 1; i++) {
      result += solveProblem(rows, problemsPositions.get(i), problemsPositions.get(i + 1));
    }
    return itoa(result);
  }

  private long solveProblem(List<List<String>> rows, int j) {
    int symbolsRowPosition = rows.size() - 1;
    char symbol = rows.get(symbolsRowPosition).get(j).charAt(0);
    LongBinaryOperator operation = getOperation(symbol);

    long result = getStartingResult(symbol);
    for (int i = 0; i < symbolsRowPosition; i++) {
      long number = atol(rows.get(i).get(j));
      result = operation.applyAsLong(result, number);
    }
    return result;
  }

  private long solveProblem(List<List<Character>> rows, int start, int end) {
    int symbolsRowPosition = rows.size() - 1;
    char symbol = rows.get(symbolsRowPosition).get(start);
    LongBinaryOperator operation = getOperation(symbol);

    long result = getStartingResult(symbol);
    for (int j = start; j < end; j++) {
      String number = EMPTY;
      for (int i = 0; i < symbolsRowPosition; i++) {
        List<Character> row = rows.get(i);
        if (j < row.size()) {
          number += row.get(j);
        }
      }
      if (!number.isBlank()) {
        result = operation.applyAsLong(result, atol(number.trim()));
      }
    }
    return result;
  }

  private static LongBinaryOperator getOperation(char symbol) {
    return (symbol == PLUS) ? Math::addExact : Math::multiplyExact;
  }

  private static int getStartingResult(char symbol) {
    return (symbol == PLUS) ? 0 : 1;
  }
}
