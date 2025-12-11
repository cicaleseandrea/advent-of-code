package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.HASH;
import static com.adventofcode.utils.Utils.itoa;
import static java.util.stream.Collectors.toSet;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
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
    if (!first && inputList.size() > 3) {
      //TODO solve part 2 for real input
      return itoa(-1);
    }
    AtomicLong result = new AtomicLong();
    inputList.stream()
        .parallel()
        .forEach(line -> {
          Machine machine = getInput(line);
          if (first) {
            result.addAndGet(configureLights(machine.diagram, machine.buttons));
          } else {
            List<List<Integer>> buttonsSet = machine.buttons.stream()
                .sorted(Comparator.comparingInt((List l) -> l.size()).reversed()) //TODO remove
                .toList();
            int min = getMinPresses(buttonsSet, machine.joltage, 0, Integer.MAX_VALUE);
            System.out.println(min);
            result.addAndGet(min);
          }
        });
    return itoa(result.get());
  }

  private int configureLights(BitSet diagram, List<List<Integer>> buttons) {
    Set<BitSet> buttonsSet = buttons.stream()
        .map(list -> list.stream().collect(BitSet::new, BitSet::set, BitSet::or))
        .collect(toSet());
    for (int i = 1; i < Integer.MAX_VALUE; i++) {
      for (Set<BitSet> buttonPresses : Sets.combinations(buttonsSet, i)) {
        BitSet lights = new BitSet();
        buttonPresses.forEach(lights::xor);
        if (lights.equals(diagram)) {
          return i;
        }
      }
    }
    throw new IllegalArgumentException("No solution found");
  }

  private int getMinPresses(List<List<Integer>> buttons, int[] joltage, int pressesSoFar, int totalBestSoFar) {
    int minPressesForThis = Arrays.stream(joltage)
        .max()
        .orElseThrow();
    if (pressesSoFar + minPressesForThis >= totalBestSoFar) {
      return totalBestSoFar;
    }

    for (List<Integer> button : buttons) {
      int maxPressesForThisButton = button.stream()
          .mapToInt(i -> joltage[i])
          .min()
          .orElseThrow();
      maxPressesForThisButton = Math.min(maxPressesForThisButton, totalBestSoFar - pressesSoFar - 1);
      for (int pressed = maxPressesForThisButton; pressed > 0; pressed--) {
        //press this button
        int totalPressed = pressesSoFar + pressed;
        if (totalPressed >= totalBestSoFar) {
          break;
        }
        int[] nextJoltage = getNextJoltage(joltage, button, pressed);
        if (pressed == maxPressesForThisButton) {
          //check if reached target
          boolean reached = true;
          for (int left : nextJoltage) {
            if (left != 0) {
              reached = false;
              break;
            }
          }
          if (reached) {
            totalBestSoFar = Math.min(totalBestSoFar, totalPressed);
            continue;
          }
        }
        boolean stop = false;
        for (int minPresses : nextJoltage) {
          if (totalPressed + minPresses >= totalBestSoFar) {
            stop = true;
            break;
          }
        }
        if (stop) {
          continue;
        }
        int resultForNext = getMinPresses(buttons, nextJoltage, totalPressed, totalBestSoFar);
        totalBestSoFar = Math.min(totalBestSoFar, resultForNext);
      }
    }
    return totalBestSoFar;
  }

  private static int[] getNextJoltage(int[] joltage, List<Integer> button, int pressed) {
    int[] nextJoltage = new int[joltage.length];
    System.arraycopy(joltage, 0, nextJoltage, 0, joltage.length);
    for (int i : button) {
      nextJoltage[i] -= pressed;
    }
    return nextJoltage;
  }

  private Machine getInput(String line) {
    BitSet diagram = new BitSet();
    List<List<Integer>> buttons = new ArrayList<>();
    int[] joltage = new int[0];
    List<String> groups = MACHINE_REGEX.matcher(line).results().map(MatchResult::group).toList();
    for (int i = 0; i < groups.size(); i++) {
      String group = groups.get(i);
      if (i == 0) {
        diagram = getDiagram(group);
      } else if (i == groups.size() - 1) {
        joltage = Utils.toLongStream(group).mapToInt(Long::intValue).toArray();
      } else {
        buttons.add(Utils.toLongStream(group).map(Long::intValue).toList());
      }
    }
    return new Machine(diagram, joltage, buttons);
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

  private record Machine(BitSet diagram, int[] joltage, List<List<Integer>> buttons) {}
}
