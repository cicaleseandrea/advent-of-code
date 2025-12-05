package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Comparator.comparingLong;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

class AoC052025 implements Solution {

  @Override
  public String solveFirstPart(Stream<String> input) {
    return solve(input, true);
  }

  @Override
  public String solveSecondPart(Stream<String> input) {
    return solve(input, false);
  }

  private String solve(Stream<String> input, boolean first) {
    Iterator<String> inputIterator = input.iterator();
    List<Range> mergedRanges = mergeRanges(getRanges(inputIterator));
    List<Long> ids = getIds(inputIterator);

    long result;
    if (first) {
      result = ids
          .stream()
          .filter(id -> mergedRanges.stream().anyMatch(range -> range.contains(id)))
          .count();
    } else {
      result = mergedRanges
          .stream()
          .mapToLong(range -> range.end - range.start + 1)
          .sum();
    }
    return itoa(result);
  }

  private List<Range> getRanges(Iterator<String> inputIterator) {
    List<Range> ranges = new ArrayList<>();
    while (inputIterator.hasNext()) {
      String line = inputIterator.next();
      if (line.isEmpty()) {
        break;
      }
      String[] range = line.split("-");
      long start = atol(range[0]);
      long end = atol(range[1]);
      ranges.add(new Range(start, end));
    }
    return ranges;
  }

  private List<Long> getIds(Iterator<String> inputIterator) {
    return Utils.iteratorToStream(inputIterator).map(Long::parseLong).toList();
  }

  private List<Range> mergeRanges(Collection<Range> ranges) {
    Iterator<Range> sortedRanges = ranges.stream().sorted(comparingLong(Range::start)).iterator();
    LinkedList<Range> mergedRanges = new LinkedList<>();
    mergedRanges.add(sortedRanges.next());
    while (sortedRanges.hasNext()) {
      Range curr = sortedRanges.next();
      Range prev = mergedRanges.getLast();
      if (overlap(prev, curr)) {
        mergedRanges.removeLast();
        mergedRanges.add(mergeRanges(prev, curr));
      } else {
        mergedRanges.add(curr);
      }
    }
    return mergedRanges;
  }

  private Range mergeRanges(Range a, Range b) {
    return new Range(min(a.start, b.start), max(a.end, b.end));
  }

  private boolean overlap(Range a, Range b) {
    //overlap or adjacent
    return !(a.start > b.end + 1 || b.start > a.end + 1);
  }

  private record Range(long start, long end) {
    boolean contains(long value) {
      return start <= value && value <= end;
    }
  }
}
