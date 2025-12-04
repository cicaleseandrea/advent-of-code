package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.itoa;
import static java.lang.Math.max;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.stream.Stream;

class AoC032025 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve(input, 2);
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve(input, 12);
  }

  private String solve(final Stream<String> input, final int digits) {
    long result = 0;
    for (String line : input.toList()) {
      int[] inputArray = Utils.toDigitsArray(line);
      int[] outputArray = new int[digits];
      for (int i = 0; i < inputArray.length; i++) {
        int digit = inputArray[i];
        int nDigitsToProcess = inputArray.length - i;
        for (int j = max(0, outputArray.length - nDigitsToProcess); j < outputArray.length; j++) {
          if (digit > outputArray[j]) {
            //update with higher value
            outputArray[j] = digit;
            if (j + 1 < outputArray.length) {
              //clear next digit, we can find a higher value for it
              outputArray[j + 1] = 0;
            }
            break;
          }
        }
      }
      long joltage = 0;
      for (int digit : outputArray) {
        joltage = joltage * 10 + digit;
      }
      result += joltage;
    }
    return itoa(result);
  }
}
