package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

import java.util.*;
import java.util.function.Predicate;

import static com.adventofcode.utils.Utils.*;

class AoC222018 implements Solution {

    private int depth;
    private Pair<Long, Long> target;

    @Override
    public String solveFirstPart(final List<String> input) {
        initializeState(input);
        final Map<Pair<Long, Long>, List<Region>> matrix = new HashMap<>();
        final SortedMap<Long, Set<Region>> priorityQueue = new TreeMap<>();
        int res = 0;
        for (int y = 0; y <= target.getSecond(); y++) {
            for (int x = 0; x <= target.getFirst(); x++) {
                final int risk = computeRegion(matrix, priorityQueue, x, y);
                //add risk level
                res += risk;
            }
        }
        return itoa(res);
    }

    @Override
    public String solveSecondPart(final List<String> input) {
        initializeState(input);
        final Map<Pair<Long, Long>, List<Region>> matrix = new HashMap<>();
        final NavigableMap<Long, Set<Region>> priorityQueue = new TreeMap<>();
        //create only start position, other positions will be created only when needed
        computeRegion(matrix, priorityQueue, 0, 0);
        //A* to find shortest path to the target (Dijkstra would be slow)
        //heuristic function is manhattan distance to the target
        //start from source
        while (!priorityQueue.isEmpty()) {
            //remove region with shortest heuristic distance
            final Set<Region> closestRegions = priorityQueue.firstEntry().getValue();
            final Region curr = closestRegions.iterator().next();
            if (closestRegions.remove(curr) && closestRegions.isEmpty()) {
                priorityQueue.pollFirstEntry();
            }
            final Pair<Long, Long> currPos = curr.getPosition();
            final int currTool = curr.getTool();
            final long currDistance = curr.getDistance();
            //target found!
            if (currPos.equals(target) && currTool == 0) {
                return itoa(currDistance);
            }
            for (final Region n : computeNeighbours(curr, matrix, priorityQueue)) {
                //if same tool, we are moving (add 1); otherwise we are changing tool (add 7)
                long distance = currDistance + (currTool == n.getTool() ? 1 : 7);
                //distance improved
                if (distance < n.getDistance()) {
                    //remove old heuristic distance of n from the queue
                    final long oldHeuristic = n.getHeuristic();
                    if (priorityQueue.get(oldHeuristic).remove(n) && priorityQueue.get(oldHeuristic).isEmpty()) {
                        priorityQueue.remove(oldHeuristic);
                    }
                    //update distance of n
                    n.setDistance(distance);
                    //add new heuristic distance of n in the queue
                    final long newHeuristic = n.getHeuristic();
                    priorityQueue.computeIfAbsent(newHeuristic, k -> new HashSet<>()).add(n);
                }
            }
        }
        return "";
    }

    private void initializeState(final List<String> input) {
        depth = atoi(extractNumberFromString(input.get(0)));
        target = createPairLong(input.get(1).substring(8).split(","));
    }

    private int computeRegion(final Map<Pair<Long, Long>, List<Region>> matrix,
                              final SortedMap<Long, Set<Region>> queue, final long x, final long y) {
        if (x < 0 || y < 0) {
            return 0;
        }
        //compute geologic index
        final long geo = computeGeologicIndex(matrix, queue, x, y);
        //compute erosion level
        final long erosion = computeErosion(geo);
        final Pair<Long, Long> position = new Pair<>(x, y);
        //Types are:
        //rocky 0
        //wet 1
        //narrow 2
        final int type = (int) (erosion % 3);
        //For each region the following tools can be used:
        //rocky 0 -> 0, 1
        //wet 1 -> 1, 2
        //narrow 2 -> 2, 0
        long distance = (x == 0 && y == 0 && type == 0) ? 0 : Long.MAX_VALUE;
        final Region regionTool1 = new Region(type, position, erosion, distance);
        queue.computeIfAbsent(regionTool1.getHeuristic(), k -> new HashSet<>()).add(regionTool1);
        distance = Long.MAX_VALUE;
        final Region regionTool2 = new Region(incrementMod(type, 3), position, erosion, distance);
        queue.computeIfAbsent(regionTool2.getHeuristic(), k -> new HashSet<>()).add(regionTool2);
        matrix.put(position, List.of(regionTool1, regionTool2));
        return type;
    }

    private List<Region> computeNeighbours(final Region r, final Map<Pair<Long, Long>, List<Region>> matrix,
                                           final SortedMap<Long, Set<Region>> queue) {
        final ArrayList<Region> neighbours = new ArrayList<>();
        final long x = r.getPosition().getFirst();
        final long y = r.getPosition().getSecond();
        final int tool = r.getTool();
        //change tool
        addNeighbour(matrix, queue, neighbours, x, y, n -> tool != n.getTool());
        //same tool
        addNeighbour(matrix, queue, neighbours, x - 1, y, n -> tool == n.getTool());
        addNeighbour(matrix, queue, neighbours, x + 1, y, n -> tool == n.getTool());
        addNeighbour(matrix, queue, neighbours, x, y - 1, n -> tool == n.getTool());
        addNeighbour(matrix, queue, neighbours, x, y + 1, n -> tool == n.getTool());
        return neighbours;
    }

    private void addNeighbour(final Map<Pair<Long, Long>, List<Region>> matrix,
                              final SortedMap<Long, Set<Region>> queue, final ArrayList<Region> neighbours,
                              final long x, final long y, final Predicate<Region> toAdd) {
        //if a neighbour does not exist yet, create it
        if (!matrix.containsKey(new Pair<>(x, y))) {
            computeRegion(matrix, queue, x, y);
        }

        for (final Region n : matrix.getOrDefault(new Pair<>(x, y), Collections.emptyList())) {
            if (toAdd.test(n)) {
                neighbours.add(n);
            }
        }
    }

    private long computeGeologicIndex(final Map<Pair<Long, Long>, List<Region>> matrix,
                                      final SortedMap<Long, Set<Region>> queue, final long x, final long y) {
        if (y == 0 && x == 0 || y == target.getSecond() && x == target.getFirst()) {
            return 0L;
        } else if (y == 0) {
            return 16807L * x;
        } else if (x == 0) {
            return 48271L * y;
        } else if (x > 0 && y > 0) {
            //if a neighbour necessary to compute the geologic index does not exist yet, create it
            final long prevX = x - 1;
            if (!matrix.containsKey(new Pair<>(prevX, y))) {
                computeRegion(matrix, queue, prevX, y);
            }
            final long prevY = y - 1;
            if (!matrix.containsKey(new Pair<>(x, prevY))) {
                computeRegion(matrix, queue, x, prevY);
            }
            return matrix.get(new Pair<>(prevX, y)).get(0).getErosion() *
                    matrix.get(new Pair<>(x, prevY)).get(0).getErosion();
        }
        return 0L;
    }

    private long computeErosion(final long geo) {
        return (geo + depth) % 20183;
    }

    private class Region {
        //Tools are:
        //torch 0
        //climb 1
        //neither 2
        private final int tool;
        private final Pair<Long, Long> position;
        private final long erosion;
        private final long heuristic;
        private long distance;

        private Region(final int tool, final Pair<Long, Long> position, final long erosion, final long distance) {
            this.tool = tool;
            this.position = position;
            this.erosion = erosion;
            this.distance = distance;
            this.heuristic = manhattanDistance(position, target);
        }

        int getTool() {
            return tool;
        }

        Pair<Long, Long> getPosition() {
            return position;
        }

        long getDistance() {
            return distance;
        }

        void setDistance(final long distance) {
            this.distance = distance;
        }

        long getHeuristic() {
            return getDistance() + (getDistance() == Long.MAX_VALUE ? 0 : heuristic);
        }

        long getErosion() {
            return erosion;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Region region = (Region) o;
            return getTool() == region.getTool() &&
                    getErosion() == region.getErosion() &&
                    getPosition().equals(region.getPosition());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getPosition(), getTool(), getErosion());
        }
    }
}