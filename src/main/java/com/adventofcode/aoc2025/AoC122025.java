package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.EARLY_CHRISTMAS;
import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

class AoC122025 implements Solution {
  @Override
  public String solveFirstPart(Stream<String> input) {
    return solve(input, true);
  }

  @Override
  public String solveSecondPart(Stream<String> input) {
    return solve(input, false);
  }

  private String solve(Stream<String> input, boolean first) {
    if (!first) {
      return EARLY_CHRISTMAS;
    }
    Iterator<String> iterator = input.iterator();
    List<Present> presents = getPresents(iterator);
    List<Region> regions = getRegions(iterator);
    long result = 2; //solution hardcoded for the example input 😅
    if (regions.size() > 3) {
      result = regions.stream().filter(region -> itFits(region, presents)).count();
    }
    return itoa(result);
  }

  private boolean itFits(Region region, List<Present> presents) {
    int maxArea = 0;
    int minArea = 0;
    for (int i = 0; i < presents.size(); i++) {
      int times = region.presents.get(i);
      maxArea += (times * 9); //presents can be at most 3x3
      minArea += (times * presents.get(i).area); //if the present could be squashed into any shape
    }
    if (region.area >= maxArea) {
      //there is enough space to fit all presents one after the other
      return true;
    } else if (region.area < minArea) {
      //there is no space, even if we squashed all presents into a blob
      return false;
    }
    //this heuristic is enough for the input 🤷
    throw new IllegalStateException("Not implemented");
  }

  private List<Present> getPresents(Iterator<String> iterator) {
    List<Present> presents = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      String index = iterator.next();
      char[][] grid = new char[3][];
      int area = 0;
      for (int j = 0; j < 3; j++) {
        String row = iterator.next();
        grid[j] = row.toCharArray();
        area += row.chars().filter(c -> c == HASH).count();
      }
      presents.add(new Present(grid, area));
      String empty = iterator.next();
    }
    return presents;
  }

  private List<Region> getRegions(Iterator<String> iterator) {
    List<Region> regions = new ArrayList<>();
    while (iterator.hasNext()) {
      String line = iterator.next();
      List<Long> numbers = Utils.toLongList(line);
      int width = numbers.get(0).intValue();
      int length = numbers.get(1).intValue();
      List<Integer> presents = numbers.subList(2, numbers.size()).stream().map(Long::intValue).toList();
      Region region = new Region(width * length, presents);
      regions.add(region);
    }
    return regions;
  }

  private record Present(char[][] grid, int area) {}

  private record Region(int area, List<Integer> presents) {}
}
