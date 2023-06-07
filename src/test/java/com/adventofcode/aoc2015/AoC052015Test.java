package com.adventofcode.aoc2015;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC052015Test extends Generic {

    private static final Solution INSTANCE = new AoC052015();

    public AoC052015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "ugknbfddgicrmopn", "1"},
                {FIRST, "aaa", "1"},
                {FIRST, "jchzalrnumimnmhp", "0"},
                {FIRST, "haegwjzuvuyypxyu", "0"},
                {FIRST, "dvszwmarrgswjxmb", "0"},
                {FIRST, getInput(INSTANCE), "238"},
                {SECOND, "qjhvhtzxzqqjkmpb", "1"},
                {SECOND, "xxyxx", "1"},
                {SECOND, "uurcxstgmygtbstg", "0"},
                {SECOND, "ieodomkazucvgmuy", "0"},
                {SECOND, getInput(INSTANCE), "69"},
        });
    }
}