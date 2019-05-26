package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import static com.adventofcode.utils.Pair.ZERO;
import static com.adventofcode.utils.Utils.*;

class AoC152018 implements Solution {
    private static final int BASE_ATTACK = 3;
    private static final char GOBLIN = 'G';
    private static final char ELF = 'E';
    private static final boolean PRINT = false;
    private static SortedMap<Pair<Long, Long>, Character> map;
    private static SortedMap<Pair<Long, Long>, Unit> originalElves;
    private static SortedMap<Pair<Long, Long>, Unit> originalGoblins;

    private static String solve(final List<String> input, final Predicate<Boolean> stopEarly) {
        initializeState(input);
        SortedMap<Pair<Long, Long>, Unit> elves;
        SortedMap<Pair<Long, Long>, Unit> goblins;
        final AtomicBoolean elvesLost = new AtomicBoolean();
        int attackPower = BASE_ATTACK;
        int turns;
        do {
            turns = 0;
            SortedMap<Pair<Long, Long>, Unit> units = new TreeMap<>(getPairComparator());
            elves = new TreeMap<>(getPairComparator());
            goblins = new TreeMap<>(getPairComparator());
            updateUnits(originalElves, elves, units, attackPower);
            updateUnits(originalGoblins, goblins, units, BASE_ATTACK);
            elvesLost.set(false);
            while (!(goblins.isEmpty() || elves.isEmpty() || stopEarly.test(elvesLost.get()))) {
                final var nextUnits = new TreeMap<>(units);
                for (final Unit unit : units.values()) {
                    //identify enemies
                    final var enemies = unit.isElf() ? goblins : elves;
                    final var allies = unit.isElf() ? elves : goblins;
                    if (enemies.isEmpty()) {
                        turns--;
                        //no enemies: victory!
                        break;
                    } else if (!unit.isDead()) {
                        //check if we are already close to a target
                        var targetOpt = selectTarget(unit.getPosition(), enemies);
                        //move if we can't already attack
                        if (targetOpt.isEmpty() && move(enemies, allies, nextUnits, unit)) {
                            //check again if we are close to a target
                            targetOpt = selectTarget(unit.getPosition(), enemies);
                        }
                        //attack
                        targetOpt.ifPresent(target -> {
                            if (target.hit(unit.getAttackPower())) {
                                //target killed
                                enemies.remove(target.getPosition());
                                nextUnits.remove(target.getPosition());
                                if (target.isElf()) {
                                    elvesLost.set(true);
                                }
                            }
                        });
                    }
                }
                units = nextUnits;
                turns++;
                if (PRINT) {
                    printState(map, units, turns);
                }
            }
            attackPower++;
        } while (stopEarly.test(elvesLost.get()));
        long sum = elves.values().stream().mapToInt(Unit::getHitPoints).sum();
        sum += goblins.values().stream().mapToInt(Unit::getHitPoints).sum();
        return itoa(turns * sum);
    }

    private static void updateUnits(final SortedMap<Pair<Long, Long>, Unit> original,
                                    final SortedMap<Pair<Long, Long>, Unit> allies,
                                    final SortedMap<Pair<Long, Long>, Unit> units, final int attackPower) {
        for (final Map.Entry<Pair<Long, Long>, Unit> e : original.entrySet()) {
            final Pair<Long, Long> pos = e.getKey();
            final Unit unit = new Unit(e.getValue(), attackPower);
            allies.put(pos, unit);
            units.put(pos, unit);
        }
    }

    private static boolean move(final SortedMap<Pair<Long, Long>, Unit> enemies,
                                final SortedMap<Pair<Long, Long>, Unit> allies,
                                final SortedMap<Pair<Long, Long>, Unit> nextUnits,
                                final Unit unit) {
        if (!targetsInRange(enemies, nextUnits)) {
            //don't move
            return false;
        }
        //Dijkstra to find shortest path to the target
        final NavigableMap<Integer, Queue<Pair<Long, Long>>> priorityQueue = new TreeMap<>();
        final Map<Pair<Long, Long>, Integer> distances = new HashMap<>();
        final Map<Pair<Long, Long>, Pair<Long, Long>> prev = new HashMap<>();
        //start from source
        final Queue<Pair<Long, Long>> tmp = new LinkedList<>();
        final Pair<Long, Long> position = unit.getPosition();
        tmp.add(position);
        priorityQueue.put(0, tmp);
        distances.put(position, 0);
        while (!priorityQueue.isEmpty()) {
            //remove position with shortest distance
            final var closestPositions = priorityQueue.firstEntry().getValue();
            final Pair<Long, Long> curr = closestPositions.remove();
            if (closestPositions.isEmpty()) {
                priorityQueue.pollFirstEntry();
            }
            //target found!
            if (enemies.containsKey(curr)) {
                //find the first step
                Pair<Long, Long> newPos = curr;
                while (!prev.get(newPos).equals(position)) {
                    newPos = prev.get(newPos);
                }
                updatePosition(unit, newPos, allies, nextUnits);
                return true;
            }
            final int newDistance = distances.get(curr) + 1;
            for (final Pair<Long, Long> n : computeNeighbours(curr)) {
                if (isOpenPosition(n, allies)) {
                    final int oldDistance = distances.getOrDefault(n, Integer.MAX_VALUE);
                    //distance improved
                    if (newDistance < oldDistance) {
                        //remove old distance of n from the queue
                        if (priorityQueue.getOrDefault(oldDistance, new LinkedList<>()).remove(n) &&
                                priorityQueue.get(oldDistance).isEmpty()) {
                            priorityQueue.remove(oldDistance);
                        }
                        //update node to reach n with shortest distance
                        prev.put(n, curr);
                        //update distance of n in the queue
                        priorityQueue.computeIfAbsent(newDistance, k -> new LinkedList<>()).add(n);
                        //update distance of n
                        distances.put(n, newDistance);
                    }
                }
            }
        }
        return false;
    }

    private static void updatePosition(final Unit unit, final Pair<Long, Long> newPos,
                                       final SortedMap<Pair<Long, Long>, Unit> allies,
                                       final SortedMap<Pair<Long, Long>, Unit> units) {
        allies.remove(unit.getPosition());
        units.remove(unit.getPosition());
        unit.setPosition(newPos);
        allies.put(unit.getPosition(), unit);
        units.put(unit.getPosition(), unit);
    }

    private static List<Pair<Long, Long>> computeNeighbours(final Pair<Long, Long> pos) {
        final List<Pair<Long, Long>> res = new ArrayList<>();
        res.add(new Pair<>(pos.getFirst(), pos.getSecond() - 1));
        res.add(new Pair<>(pos.getFirst() - 1, pos.getSecond()));
        res.add(new Pair<>(pos.getFirst() + 1, pos.getSecond()));
        res.add(new Pair<>(pos.getFirst(), pos.getSecond() + 1));
        return res;
    }

    private static Optional<Unit> selectTarget(final Pair<Long, Long> pos, final SortedMap<Pair<Long, Long>, Unit> targets) {
        int minHitPoints = Integer.MAX_VALUE;
        Unit res = null;
        for (final Pair<Long, Long> n : computeNeighbours(pos)) {
            Unit tmp = targets.getOrDefault(n, Unit.ZERO);
            if (tmp.getHitPoints() < minHitPoints) {
                minHitPoints = tmp.getHitPoints();
                res = tmp;
            }
        }
        return Optional.ofNullable(res);
    }

    private static boolean targetsInRange(final SortedMap<Pair<Long, Long>, Unit> targets,
                                          final SortedMap<Pair<Long, Long>, Unit> units) {
        for (final Pair<Long, Long> pos : targets.keySet()) {
            for (final Pair<Long, Long> n : computeNeighbours(pos)) {
                if (isOpenPosition(n, units)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isOpenPosition(final Pair<Long, Long> position,
                                          final SortedMap<Pair<Long, Long>, Unit> units) {
        return map.getOrDefault(position, HASH) != HASH && !units.containsKey(position);
    }

    private static void initializeState(final List<String> input) {
        map = new TreeMap<>(getPairComparator());
        originalElves = new TreeMap<>(getPairComparator());
        originalGoblins = new TreeMap<>(getPairComparator());
        long y = 0;
        for (final String row : input) {
            long x = 0;
            for (final char c : row.toCharArray()) {
                final Pair<Long, Long> position = new Pair<>(x, y);
                final char terrain;
                if (c != GOBLIN && c != ELF) {
                    terrain = c;
                } else {
                    final Unit unit = new Unit(position, c, BASE_ATTACK);
                    if (unit.isElf()) {
                        originalElves.put(position, unit);
                    } else {
                        originalGoblins.put(position, unit);
                    }
                    terrain = DOT;
                }
                map.put(position, terrain);
                x++;
            }
            y++;
        }
    }

    private static void printState(final SortedMap<Pair<Long, Long>, Character> map,
                                   final SortedMap<Pair<Long, Long>, Unit> units,
                                   final int turns) {
        Pair<Long, Long> prevPosition = ZERO;
        System.out.println("Turn: " + turns);
        for (final Map.Entry<Pair<Long, Long>, Character> e : map.entrySet()) {
            final Pair<Long, Long> position = e.getKey();
            final char c;
            if (units.containsKey(position)) {
                c = units.get(position).isElf() ? ELF : GOBLIN;
            } else {
                c = e.getValue();
            }
            if (!position.getSecond().equals(prevPosition.getSecond())) {
                System.out.println();
            }
            System.out.print(c);
            prevPosition = position;
        }
        System.out.println();
    }

    public String solveFirstPart(final List<String> input) {
        return solve(input, b -> false);
    }

    public String solveSecondPart(final List<String> input) {
        return solve(input, b -> b);
    }

    private static class Unit {
        static final Unit ZERO = new Unit(Pair.ZERO, true, Integer.MAX_VALUE, BASE_ATTACK);
        private final boolean elf;
        private int hitPoints;
        private int attackPower;
        private Pair<Long, Long> position;

        Unit(final Pair<Long, Long> position, final char c, final int attackPower) {
            this(position, c == ELF, 200, attackPower);
        }

        Unit(final Unit unit, final int attackPower) {
            this(unit.getPosition(), unit.isElf(), unit.getHitPoints(), attackPower);
        }

        private Unit(final Pair<Long, Long> position, final boolean isElf, final int hitPoints,
                     final int attackPower) {
            this.position = position;
            this.elf = isElf;
            this.hitPoints = hitPoints;
            this.attackPower = attackPower;
        }

        Pair<Long, Long> getPosition() {
            return position;
        }

        void setPosition(final Pair<Long, Long> position) {
            this.position = position;
        }

        boolean isElf() {
            return elf;
        }

        int getHitPoints() {
            return hitPoints;
        }

        boolean isDead() {
            return getHitPoints() <= 0;
        }

        boolean hit(final int hit) {
            this.hitPoints -= hit;
            return isDead();
        }

        int getAttackPower() {
            return attackPower;
        }
    }
}