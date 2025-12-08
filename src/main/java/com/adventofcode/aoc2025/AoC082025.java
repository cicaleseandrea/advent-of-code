package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.Comparator.reverseOrder;

import com.adventofcode.Solution;
import com.adventofcode.utils.DisjointSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class AoC082025 implements Solution {

  @Override
  public String solveFirstPart(Stream<String> input) {
    return solve(input, true);
  }

  @Override
  public String solveSecondPart(Stream<String> input) {
    return solve(input, false);
  }

  private String solve(Stream<String> input, boolean first) {
    List<Point> boxes = input.map(this::getBox).toList();
    DisjointSet<Point> circuits = initCircuits(boxes);
    List<PairWithDistance> pairsSortedByDistance = getPairsSortedByDistance(boxes);

    int maxConnections = boxes.size() > 20 ? 1000 : 10;
    for (int i = 0; i < (first ? maxConnections : pairsSortedByDistance.size()); i++) {
      PairWithDistance pair = pairsSortedByDistance.get(i);
      circuits.union(pair.first, pair.second);
      if (circuits.getSize() == 1 && !first) {
        return itoa((long) pair.first.x * pair.second.x);
      }
    }

    long result = circuits.getSizes()
        .stream()
        .sorted(reverseOrder())
        .limit(3)
        .reduce(1, (a, b) -> a * b);
    return itoa(result);
  }

  private Point getBox(String line) {
    String[] coordinates = line.split(",");
    return new Point(
        atoi(coordinates[0]),
        atoi(coordinates[1]),
        atoi(coordinates[2])
    );
  }

  private DisjointSet<Point> initCircuits(List<Point> boxes) {
    DisjointSet<Point> circuits = new DisjointSet<>();
    boxes.forEach(circuits::makeSet);
    return circuits;
  }

  private static List<PairWithDistance> getPairsSortedByDistance(List<Point> boxes) {
    List<PairWithDistance> pairsWithDistances = new ArrayList<>();
    for (int i = 0; i < boxes.size(); i++) {
      for (int j = i + 1; j < boxes.size(); j++) {
        pairsWithDistances.add(new PairWithDistance(boxes.get(i), boxes.get(j)));
      }
    }
    Collections.sort(pairsWithDistances);
    return pairsWithDistances;
  }

  private record PairWithDistance(Point first, Point second, double distance) implements Comparable<PairWithDistance> {
    PairWithDistance(Point first, Point second) {
      this(first, second, Point.euclideanDistance(first, second));
    }

    @Override
    public int compareTo(PairWithDistance o) {
      return Double.compare(this.distance, o.distance);
    }
  }

  private record Point(int x, int y, int z) {
    static double euclideanDistance(Point first, Point second) {
      return Math.sqrt(Math.pow(first.x - second.x, 2) + Math.pow(first.y - second.y, 2) + Math.pow(first.z - second.z, 2));
    }
  }
}
