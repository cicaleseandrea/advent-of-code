package com.adventofcode.aoc2025;

import static com.adventofcode.utils.Utils.atol;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.Utils;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

class AoC022025 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve(input, true);
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve(input, false);
  }

  private String solve(final Stream<String> input, final boolean first) {
    AtomicLong result = new AtomicLong();
    input
        .flatMap(line -> Utils.splitOnRegex(line, ","))
        .forEach(rangesStr -> {
          String[] range = rangesStr.split("-");
          long start = atol(range[0]);
          long end = atol(range[1]);
          for (long id = start; id <= end; id++) {
            int idLength = (int) (Math.log10(id) + 1);
            for (int nSequences = 2; nSequences <= (first ? 2 : idLength); nSequences++) {
              if (idLength % nSequences == 0) {
                int sequenceLength = idLength / nSequences;
                long tens = (long) Math.pow(10, sequenceLength);
                long sequence = id % tens; //candidate sequence
                long toProcess = id;
                for (int i = 0; i < nSequences - 1 && sequence == (toProcess % tens); i++) {
                  //remove the sequence that matched
                  toProcess /= tens;
                }
                if (sequence == toProcess) {
                  //last sequence matches the candidate sequence
                  result.addAndGet(id);
                  break;
                }
              }
            }
          }
        });
    return itoa(result.get());
  }

}
