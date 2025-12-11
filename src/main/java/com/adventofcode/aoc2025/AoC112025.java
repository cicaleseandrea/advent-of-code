package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.util.List;
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
      wordList.subList(1, wordList.size()).forEach(word -> {
        connections.put(wordList.get(0), word);
      });
    });
    if (first) {
      return itoa(countAllPaths(START_FIRST_PART, END, connections, Integer.MAX_VALUE));
    } else {
      //TODO remove hardcoded max length
      long svr_dac = countAllPaths(START_SECOND_PART, MUST_VISIT1, connections, 8);
      long dac_fft = 0;
      long fft_end = 0;
      if (svr_dac > 0) {
        dac_fft = countAllPaths(MUST_VISIT1, MUST_VISIT2, connections, 16);
        fft_end = countAllPaths(MUST_VISIT2, END, connections, 9);
      }
      long svr_fft = countAllPaths(START_SECOND_PART, MUST_VISIT2, connections, 8);
      System.out.println(svr_fft);
      long fft_dac = 0;
      long dac_end = 0;
      if (svr_fft > 0) {
        fft_dac = countAllPaths(MUST_VISIT2, MUST_VISIT1, connections, 16);
        System.out.println(fft_dac);
        dac_end = countAllPaths(MUST_VISIT1, END, connections, 9);
        System.out.println(dac_end);
      }
      long one = svr_dac * dac_fft * fft_end;
      long two = svr_fft * fft_dac * dac_end;
      return itoa(one + two);
    }
  }

  private long countAllPaths(String start, String end, ListMultimap<String, String> connections, int maxLength) {
    return countAllPaths(start, end, connections, 0, maxLength);
  }

  private long countAllPaths(String start, String end, ListMultimap<String, String> connections, int currPathLength, int maxLength) {
    if (currPathLength > maxLength) {
      return 0;
    }
    long count = 0;
    for (String neighbour : connections.get(start)) {
      if (neighbour.equals(end)) {
        count++;
      } else {
        count += countAllPaths(neighbour, end, connections, currPathLength + 1, maxLength);
      }
    }
    return count;
  }
}
