package com.adventofcode.aoc2017;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;
import static com.adventofcode.aoc2017.AoC012017.INSTANCE;

import com.adventofcode.AbstractSolutionTest;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AoC012017Test extends AbstractSolutionTest {

    public AoC012017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "1122", "3"},
                {FIRST, "1111", "4"},
                {FIRST, "1234", "0"},
                {FIRST, "91212129", "9"},
                {FIRST, getInput(INSTANCE), "1253"},
                {SECOND, "1212", "6"},
                {SECOND, "1221", "0"},
                {SECOND, "123425", "4"},
                {SECOND, "123123", "12"},
                {SECOND, "12131415", "4"},
                {SECOND, getInput(INSTANCE), "1278"}
        });
    }
}