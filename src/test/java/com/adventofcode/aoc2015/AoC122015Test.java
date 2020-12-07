package com.adventofcode.aoc2015;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC122015Test extends Generic {

    private static final Solution INSTANCE = new AoC122015();

    public AoC122015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "{\"a\":2,\"b\":4}", "6"},
                {FIRST, "{\"a\":{\"b\":4},\"c\":-1}", "3"},
                {FIRST, "{\"a\":[-1,1]}", "0"},
                {FIRST, getInput(INSTANCE), "156366"},
//                {SECOND, "[1,2,3]", "6"},
//                {SECOND, "[1,{\"c\":\"red\",\"b\":2},3]", "4"},
                {SECOND, "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}", "0"},
//                {SECOND, "[1,\"red\",5]", "6"},
                {SECOND, getInput(INSTANCE), "0"},
        });
    }
}
