package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class AoC112025 implements Solution {

  private static final String START_FIRST_PART = "you";
  private static final String START_SECOND_PART = "svr";
  private static final String MUST_VISIT1 = "dac";
  private static final String MUST_VISIT2 = "fft";
  private static final String END = "out";

  @Override
  public String solveFirstPart(Stream<String> input) {
    return solve(input, true);
  }

  @Override
  public String solveSecondPart(Stream<String> input) {
    return solve(input, false);
  }

  private String solve(Stream<String> input, boolean first) {
    ListMultimap<String, String> connections = ArrayListMultimap.create();
    input.forEach(line -> {
      List<String> wordList = Utils.toWordList(line);
      wordList.subList(1, wordList.size())
          .forEach(word -> connections.put(wordList.get(0), word));
    });

    if (first) {
      return itoa(countAllPaths(START_FIRST_PART, END, connections));
    } else {
      long result = 0;
      long svrDac = countAllPaths(START_SECOND_PART, MUST_VISIT1, connections);
      long dacFft = countAllPaths(MUST_VISIT1, MUST_VISIT2, connections);
      long fftOut = countAllPaths(MUST_VISIT2, END, connections);
      result += (svrDac * dacFft * fftOut);
      long svrFft = countAllPaths(START_SECOND_PART, MUST_VISIT2, connections);
      long fftDac = countAllPaths(MUST_VISIT2, MUST_VISIT1, connections);
      long dacOut = countAllPaths(MUST_VISIT1, END, connections);
      result += (svrFft * fftDac * dacOut);
      return itoa(result);
    }
  }

  private long countAllPaths(String start, String end, ListMultimap<String, String> connections) {
    return countAllPaths(start, end, connections, new HashMap<>());
  }

  private int countAllPaths(String start, String end, ListMultimap<String, String> connections, Map<String, Integer> distances) {
    if (distances.containsKey(start)) {
      return distances.get(start);
    }
    int count = 0;
    for (String neighbour : connections.get(start)) {
      if (neighbour.equals(end)) {
        count++;
      } else {
        count += countAllPaths(neighbour, end, connections, distances);
      }
    }
    distances.put(start, count);
    return count;
  }
}
