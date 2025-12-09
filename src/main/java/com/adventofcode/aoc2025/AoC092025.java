package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.incrementMod;
import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Integer.signum;

import com.adventofcode.Solution;
import java.util.List;
import java.util.stream.Stream;

class AoC092025 implements Solution {

  @Override
  public String solveFirstPart(Stream<String> input) {
    return solve(input, true);
  }

  @Override
  public String solveSecondPart(Stream<String> input) {
    return solve(input, false);
  }

  private String solve(Stream<String> input, boolean first) {
    List<Point> vertices = input.map(this::getTile).toList();
    long result = 0;
    for (int i = 0; i < vertices.size(); i++) {
      for (int j = 0; j < vertices.size(); j++) {
        long area = Point.area(vertices.get(i), vertices.get(j));
        if (area > result) {
          if (first || isInsidePolygon(vertices.get(i), vertices.get(j), vertices)) {
            result = area;
          }
        }
      }
    }
    return itoa(result);
  }

  private boolean isInsidePolygon(Point a, Point b, List<Point> vertices) {
    Point c = new Point(a.x, b.y);
    Point d = new Point(b.x, a.y);
    // check if the other corners are outside the polygon
    if (!isInsidePolygon(c, vertices) || !isInsidePolygon(d, vertices)) {
      return false;
    }
    // check if the edges are inside the polygon
    return isInsidePolygon(List.of(a, c, b, d), vertices);
  }

  private boolean isInsidePolygon(Point point, List<Point> vertices) {
    boolean isInside = false;
    for (int i = 0; i < vertices.size(); i++) {
      Point edgeStart = vertices.get(i);
      Point edgeEnd = vertices.get(incrementMod(i, vertices.size()));
      // if the point is on an edge, it's inside the polygon
      if (isBetweenIncluded(point.x, edgeStart.x, edgeEnd.x) && isBetweenIncluded(point.y, edgeStart.y, edgeEnd.y)) {
        return true;
      } else if (edgeStart.x == edgeEnd.x) {
        // keep track of all vertical edges to the right of the point
        if (point.x < edgeStart.x && isBetween(point.y, edgeStart.y, edgeEnd.y)) {
          isInside = !isInside;
        }
      }
    }
    return isInside;
  }

  private boolean isInsidePolygon(List<Point> rectangle, List<Point> vertices) {
    for (int i = 0; i < rectangle.size(); i++) {
      Point rectangleEdgeStart = rectangle.get(i);
      Point rectangleEdgeEnd = rectangle.get(incrementMod(i, rectangle.size()));
      for (int j = 0; j < vertices.size(); j++) {
        Point edgeStart = vertices.get(j);
        Point edgeEnd = vertices.get(incrementMod(j, vertices.size()));
        // if a rectangle edge crosses the polygon edge, the rectangle is outside the polygon
        if (isBetween(rectangleEdgeStart.x, edgeStart.x, edgeEnd.x) && isBetween(edgeStart.y, rectangleEdgeStart.y, rectangleEdgeEnd.y)) {
          return false;
        } else if (isBetween(rectangleEdgeStart.y, edgeStart.y, edgeEnd.y) && isBetween(edgeStart.x, rectangleEdgeStart.x, rectangleEdgeEnd.x)) {
          return false;
        }

        // for a vertical rectangle edge, check horizontal polygon edges
        if (rectangleEdgeStart.x == rectangleEdgeEnd.x && edgeStart.y == edgeEnd.y) {
          int edgeY = edgeStart.y;
          int rectangleEdgeDirection = signum(rectangleEdgeEnd.y - rectangleEdgeStart.y);
          // if a rectangle vertex touches the polygon edge, check another point of the rectangle edge to see if it's outside the polygon
          int nextY;
          if (rectangleEdgeStart.y == edgeY) {
            nextY = edgeY + rectangleEdgeDirection;
          } else if (rectangleEdgeEnd.y == edgeY) {
            nextY = edgeY - rectangleEdgeDirection;
          } else {
            continue;
          }
          if (!isInsidePolygon(new Point(rectangleEdgeStart.x, nextY), vertices)) {
            return false;
          }
        }

        // for a horizontal rectangle edge, check vertical polygon edges
        if (rectangleEdgeStart.y == rectangleEdgeEnd.y && edgeStart.x == edgeEnd.x) {
          int edgeX = edgeStart.x;
          int rectangleEdgeDirection = signum(rectangleEdgeEnd.x - rectangleEdgeStart.x);
          // if a rectangle vertex touches the polygon edge, check another point of the rectangle edge to see if it's outside the polygon
          int nextX;
          if (rectangleEdgeStart.x == edgeX) {
            nextX = edgeX + rectangleEdgeDirection;
          } else if (rectangleEdgeEnd.x == edgeX) {
            nextX = edgeX - rectangleEdgeDirection;
          } else {
            continue;
          }
          if (!isInsidePolygon(new Point(nextX, rectangleEdgeStart.y), vertices)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  private Point getTile(String line) {
    String[] coordinates = line.split(",");
    return new Point(
        atoi(coordinates[0]),
        atoi(coordinates[1])
    );
  }

  private static boolean isBetween(int value, int start, int end) {
    return Math.min(start, end) < value && value < Math.max(start, end);
  }

  private static boolean isBetweenIncluded(int value, int start, int end) {
    return Math.min(start, end) <= value && value <= Math.max(start, end);
  }

  private record Point(int x, int y) {
    static long area(Point first, Point second) {
      return Math.multiplyExact((long) Math.abs(first.x - second.x) + 1, Math.abs(first.y - second.y) + 1);
    }
  }
}
