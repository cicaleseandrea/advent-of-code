package com.adventofcode.aoc2015;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC122015Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC122015();

    public AoC122015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "{\"a\":2,\"b\":4}", "6"},
                {FIRST, "{\"a\":{\"b\":4},\"c\":-1}", "3"},
                {FIRST, "{\"a\":[-1,1]}", "0"},
                {FIRST, getInput(INSTANCE), "156366"},
                {SECOND, "[1,2,3]", "6"},
                {SECOND, "[1,{\"c\":\"red\",\"b\":2},3]", "4"},
                {SECOND, "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}", "0"},
                {SECOND, "[1,\"red\",5]", "6"},
                {SECOND, getInput(INSTANCE), "96852"},
        });
    }
}
