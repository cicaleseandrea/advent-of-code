package com.adventofcode.aoc2018;

import static java.util.stream.Collectors.toUnmodifiableList;

import static com.adventofcode.utils.Utils.EMPTY;
import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnTabOrSpace;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.adventofcode.Solution;

class AoC242018 implements Solution {

    private static String solve(final List<String> input, final Predicate<Boolean> stop) {
        int i = 1;
        String row;
        final Set<Group.GroupBuilder> immuneSystemBuilders = new HashSet<>();
        while (!(row = input.get(i++)).startsWith("Infection")) {
            initializeState(row, immuneSystemBuilders, Group.Army.IMMUNE_SYSTEM);
        }
        final Set<Group.GroupBuilder> infectionBuilders = new HashSet<>();
        while (i < input.size()) {
            initializeState(input.get(i++), infectionBuilders, Group.Army.INFECTION);
        }

        Set<Group> infection;
        Set<Group> immuneSystem;
        boolean immuneSystemWon = false;
        boolean boost = false;
        do {
            final SortedSet<Group> targetSelectionPhase = new TreeSet<>(Comparator.comparingLong(Group::getEffectivePower)
                    .thenComparingLong(Group::getInitiative).reversed());
            infection = buildArmy(infectionBuilders, false);
            targetSelectionPhase.addAll(infection);
            immuneSystem = buildArmy(immuneSystemBuilders, boost);
            targetSelectionPhase.addAll(immuneSystem);

            final SortedMap<Group, Group> attackingPhase = new TreeMap<>(Comparator.comparingLong(Group::getInitiative)
                    .reversed());
            while (!immuneSystem.isEmpty() && !infection.isEmpty()) {
                targetSelectionPhase(targetSelectionPhase, attackingPhase, infection, immuneSystem);
                final long killed = attackingPhase(targetSelectionPhase, attackingPhase, infection, immuneSystem);
                if (killed == 0) {
                    //stall: now winners
                    break;
                }
            }
            if (infection.isEmpty()) {
                immuneSystemWon = true;
            } else {
                //we need a boost
                boost = true;
            }
        } while (!stop.test(immuneSystemWon));
        long res = 0L;
        final Set<Group> winner = immuneSystem.isEmpty() ? infection : immuneSystem;
        for (final Group g : winner) {
            res += g.getUnits();
        }
        return itoa(res);
    }

    private static Set<Group> buildArmy(final Set<Group.GroupBuilder> builders, final boolean boost) {
        final Set<Group> army = new HashSet<>();
        for (final Group.GroupBuilder g : builders) {
            if (boost) {
                //add boost
                g.increaseAttackDamage();
            }
            army.add(g.build());
        }
        return army;
    }

    private static void targetSelectionPhase(final SortedSet<Group> targetSelectionPhase, final SortedMap<Group, Group> attackingPhase, final Set<Group> infection, final Set<Group> immuneSystem) {
        for (final Group selecting : targetSelectionPhase) {
            //select defending army
            final Set<Group> enemies = selecting.getArmy() == Group.Army.INFECTION ? immuneSystem : infection;
            Optional<Group> selectedOpt = Optional.empty();
            long selectedDamage = 0L;
            //choose defending group
            for (final Group defending : enemies) {
                final long potentialDamage = computeDamage(selecting, defending);
                if (potentialDamage == 0) {
                    //no reason to attack if no damage is inflicted
                    continue;
                }
                if (selectedOpt.isPresent()) {
                    Group selected = selectedOpt.get();
                    if (potentialDamage > selectedDamage
                            || potentialDamage == selectedDamage
                            && (defending.getEffectivePower() > selected.getEffectivePower()
                            || defending.getEffectivePower() == selected.getEffectivePower()
                            && defending.getInitiative() > selected.getInitiative())) {
                        selectedOpt = Optional.of(defending);
                        selectedDamage = potentialDamage;
                    }
                } else {
                    selectedOpt = Optional.of(defending);
                    selectedDamage = potentialDamage;
                }
            }
            selectedOpt.ifPresent(selected -> {
                //who is attacking who
                attackingPhase.put(selecting, selected);
                //the chosen defending group can't be attacked by anyone else
                enemies.remove(selected);
            });
        }
    }

    private static long attackingPhase(final SortedSet<Group> targetSelectionPhase, final SortedMap<Group, Group> attackingPhase, final Set<Group> infection, final Set<Group> immuneSystem) {
        long res = 0L;
        for (final Map.Entry<Group, Group> attack : attackingPhase.entrySet()) {
            final Group defending = attack.getValue();
            //the defending group will not be able to attack anymore, unless it survives
            targetSelectionPhase.remove(defending);
            //compute how many units were killed
            final long damage = computeDamage(attack.getKey(), defending);
            final long killed = Math.min(damage / defending.getHitPoints(), defending.getUnits());
            defending.removeUnits(killed);
            res += killed;
            if (defending.getUnits() > 0) {
                //the defending group will be able to attack again because it survived
                targetSelectionPhase.add(defending);
                //the defending group can be attacked again because it survived
                final Set<Group> army = defending.getArmy() == Group.Army.INFECTION ? infection : immuneSystem;
                army.add(defending);
            }
        }
        //attacking phase completed
        attackingPhase.clear();
        return res;
    }

    private static void initializeState(final String row, final Set<Group.GroupBuilder> army, final Group.Army armyType) {
        if (row.isEmpty()) return;
        final Group.GroupBuilder builder = new Group.GroupBuilder().setArmy(armyType);

        String[] tmp = row.split("units");
        builder.setUnits(atol(tmp[0].trim()));

        tmp = tmp[1].split("hit");
        builder.setHitPoints( extractIntegerFromString( tmp[0] ) );

        int beginIndex = row.indexOf('(');
        if (beginIndex >= 0) {
            final int midIndex = row.indexOf(';');
            final int endIndex = row.indexOf(')');
            if (midIndex >= 0) {
                extractQualities(splitOnTabOrSpace(row.substring(beginIndex + 1, midIndex)), builder);
                beginIndex = midIndex + 1;
            }
            extractQualities(splitOnTabOrSpace(row.substring(beginIndex + 1, endIndex)), builder);
        }

        final List<String> rest = splitOnTabOrSpace(tmp[1].split("does")[1]);

        builder.setAttackDamage(atol(rest.get(1))).setAttack(rest.get(2)).setInitiative(atol(rest.get(6)));

        army.add(builder);
    }

    private static void extractQualities(final List<String> str, final Group.GroupBuilder builder) {
        final Set<String> qualities = new HashSet<>();
        for (int i = 2; i < str.size(); i++) {
            final String quality = str.get(i);
            qualities.add(quality.replace(",", EMPTY));
        }
        if (str.get(0).equals("weak")) {
            builder.setWeaknesses(qualities);
        } else {
            builder.setImmunities(qualities);
        }
    }

    private static long computeDamage(final Group attacking, final Group defending) {
        final String attack = attacking.getAttack();
        if (defending.getImmunities().contains(attack)) {
            //defending group is immune == no damage
            return 0;
        } else {
            final long damage = attacking.getEffectivePower();
            //defending group is weak == double damage
            if ((defending.getWeaknesses().contains(attack))) {
                return damage * 2;
            } else {
                return damage;
            }
        }
    }

    @Override
    public String solveFirstPart(final Stream<String> input) {
        return solve(input.collect(toUnmodifiableList()), b -> true);
    }

    @Override
    public String solveSecondPart(final Stream<String> input) {
        return solve(input.collect(toUnmodifiableList()), b -> b);
    }

    private static class Group {
        final long hitPoints;
        final Set<String> weaknesses;
        final Set<String> immunities;
        final String attack;
        final long attackDamage;
        final long initiative;
        final Army army;
        long units;

        private Group(final GroupBuilder groupBuilder) {
            this.units = groupBuilder.units;
            this.hitPoints = groupBuilder.hitPoints;
            this.weaknesses = groupBuilder.weaknesses;
            this.immunities = groupBuilder.immunities;
            this.attack = groupBuilder.attack;
            this.attackDamage = groupBuilder.attackDamage;
            this.initiative = groupBuilder.initiative;
            this.army = groupBuilder.army;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Group group = (Group) o;
            return getHitPoints() == group.getHitPoints() &&
                    getAttackDamage() == group.getAttackDamage() &&
                    getInitiative() == group.getInitiative() &&
                    getWeaknesses().equals(group.getWeaknesses()) &&
                    getImmunities().equals(group.getImmunities()) &&
                    getAttack().equals(group.getAttack()) &&
                    getArmy() == group.getArmy();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getHitPoints(), getWeaknesses(), getImmunities(), getAttack(), getAttackDamage(),
                    getInitiative(), getArmy());
        }

        void removeUnits(final long units) {
            this.units -= units;
        }

        long getEffectivePower() {
            return units * attackDamage;
        }

        long getUnits() {
            return units;
        }

        long getHitPoints() {
            return hitPoints;
        }

        Set<String> getWeaknesses() {
            return weaknesses;
        }

        Set<String> getImmunities() {
            return immunities;
        }

        String getAttack() {
            return attack;
        }

        long getAttackDamage() {
            return attackDamage;
        }

        long getInitiative() {
            return initiative;
        }

        Army getArmy() {
            return army;
        }

        private enum Army {
            INFECTION, IMMUNE_SYSTEM
        }

        static class GroupBuilder {
            private long units;
            private long hitPoints;
            private Set<String> weaknesses = new HashSet<>();
            private Set<String> immunities = new HashSet<>();
            private String attack;
            private long attackDamage;
            private long initiative;
            private Army army;

            GroupBuilder setUnits(final long units) {
                this.units = units;
                return this;
            }

            GroupBuilder setHitPoints(final long hitPoints) {
                this.hitPoints = hitPoints;
                return this;
            }

            GroupBuilder setWeaknesses(final Set<String> weaknesses) {
                this.weaknesses = weaknesses;
                return this;
            }

            GroupBuilder setImmunities(final Set<String> immunities) {
                this.immunities = immunities;
                return this;
            }

            GroupBuilder setAttack(final String attack) {
                this.attack = attack;
                return this;
            }

            GroupBuilder setAttackDamage(final long attackDamage) {
                this.attackDamage = attackDamage;
                return this;
            }

            GroupBuilder increaseAttackDamage() {
                this.attackDamage++;
                return this;
            }

            GroupBuilder setInitiative(final long initiative) {
                this.initiative = initiative;
                return this;
            }

            GroupBuilder setArmy(final Army army) {
                this.army = army;
                return this;
            }

            Group build() {
                return new Group(this);
            }
        }
    }
}