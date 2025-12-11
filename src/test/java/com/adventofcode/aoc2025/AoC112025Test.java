package com.adventofcode.aoc2025;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC112025Test extends AbstractSolutionTest {

  private static final Solution INSTANCE = new AoC112025();

  public AoC112025Test(final Type type, final String input, final String result) {
    super(INSTANCE, type, input, result);
  }

  @Parameters(name = PARAMETERS_MESSAGE)
  public static Iterable<Object[]> data() {
    return List.of(new Object[][]{
        {FIRST, """
                aaa: you hhh
                you: bbb ccc
                bbb: ddd eee
                ccc: ddd eee fff
                ddd: ggg
                eee: out
                fff: out
                ggg: out
                hhh: ccc fff iii
                iii: out
                """, "5"},
        {FIRST, getInput(INSTANCE), "668"},
        {SECOND, """
                svr: aaa bbb
                aaa: fft
                fft: ccc
                bbb: tty
                tty: ccc
                ccc: ddd eee
                ddd: hub
                hub: fff
                eee: dac
                dac: fff
                fff: ggg hhh
                ggg: out
                hhh: out
                """, "2"},
        {SECOND, getInput(INSTANCE), "294310962265680"}
    });
  }
}