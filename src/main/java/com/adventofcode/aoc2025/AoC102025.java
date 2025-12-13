package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Sets;
import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AoC102025 implements Solution {

  private static final Pattern MACHINE_REGEX = Pattern.compile("(\\[.*])|(\\([^)]*\\))|(\\{.*})");

  @Override
  public String solveFirstPart(Stream<String> input) {
    return solve(input, true);
  }

  @Override
  public String solveSecondPart(Stream<String> input) {
    return solve(input, false);
  }

  private String solve(Stream<String> input, boolean first) {
    List<String> inputList = input.toList();
    AtomicInteger result = new AtomicInteger();
    inputList.stream()
        .parallel()
        .forEach(line -> {
          Machine machine = getMachine(line);
          int machineResult;
          if (first) {
            machineResult = configureLights(machine.diagram, machine.buttons);
          } else {
            machineResult = configureJoltage(machine.buttons, machine.joltage);
          }
          result.addAndGet(machineResult);
        });
    return itoa(result.get());
  }

  private int configureLights(BitSet diagram, List<Set<Integer>> buttons) {
    Set<BitSet> buttonsSet = buttons.stream()
        .map(set -> set.stream().collect(BitSet::new, BitSet::set, BitSet::or))
        .collect(toSet());
    for (int i = 1; i < Integer.MAX_VALUE; i++) {
      // try all buttons combinations of size i. no need to press a button more than once
      for (Set<BitSet> buttonsToPress : Sets.combinations(buttonsSet, i)) {
        BitSet lights = new BitSet();
        // press all buttons within the combination
        buttonsToPress.forEach(lights::xor);
        if (lights.equals(diagram)) {
          return i;
        }
      }
    }
    throw new IllegalArgumentException("No solution found");
  }

  private int configureJoltage(List<Set<Integer>> buttons, List<Integer> joltage) {
    // import native libraries needed for using ortools
    Loader.loadNativeLibraries();

    // create the linear solver
    MPSolver solver = MPSolver.createSolver("CP_SAT");
    if (solver == null) {
      throw new IllegalArgumentException("Could not create solver using ortools");
    }

    // each variable represents how many times a button is pressed
    int maxPresses = joltage.stream().mapToInt(Integer::intValue).sum();
    List<MPVariable> variables = IntStream.range(0, buttons.size()).mapToObj(i -> solver.makeIntVar(0, maxPresses, "x" + i)).toList();

    // the function to minimize represents the total number of button presses
    MPObjective objective = solver.objective();
    variables.forEach(var -> objective.setCoefficient(var, 1));
    objective.setMinimization();

    // each constraint represents the required joltage level
    for (int i = 0; i < joltage.size(); i++) {
      int requiredValue = joltage.get(i);
      MPConstraint constraint = solver.makeConstraint(requiredValue, requiredValue);
      for (int j = 0; j < buttons.size(); j++) {
        // buttons that contribute to this joltage level
        if (buttons.get(j).contains(i)) {
          constraint.setCoefficient(variables.get(j), 1);
        }
      }
    }

    // solve the problem
    final MPSolver.ResultStatus resultStatus = solver.solve();
    return (int) objective.value();
  }

  private Machine getMachine(String line) {
    BitSet diagram = new BitSet();
    List<Set<Integer>> buttons = new ArrayList<>();
    List<Integer> joltage = new ArrayList<>();
    List<String> groups = MACHINE_REGEX.matcher(line).results().map(MatchResult::group).toList();
    for (int i = 0; i < groups.size(); i++) {
      String group = groups.get(i);
      if (i == 0) {
        diagram = getDiagram(group);
      } else if (i == groups.size() - 1) {
        joltage = Utils.toLongStream(group).map(Long::intValue).toList();
      } else {
        buttons.add(Utils.toLongStream(group).map(Long::intValue).collect(toSet()));
      }
    }
    return new Machine(diagram, buttons, joltage);
  }

  private BitSet getDiagram(String group) {
    BitSet diagram = new BitSet();
    for (int i = 0; i < group.length(); i++) {
      if (group.charAt(i) == HASH) {
        diagram.flip(i - 1);
      }
    }
    return diagram;
  }

  private record Machine(BitSet diagram, List<Set<Integer>> buttons, List<Integer> joltage) {}
}
