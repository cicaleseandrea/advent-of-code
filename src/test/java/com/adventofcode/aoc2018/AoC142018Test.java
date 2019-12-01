package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC142018Test extends Generic {

    private static final Solution INSTANCE = new AoC142018();

    public AoC142018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "9", "5158916779"},
                {FIRST, "5", "0124515891"},
                {FIRST, "18", "9251071085"},
                {FIRST, "2018", "5941429882"},
                {FIRST, getInput(INSTANCE), "1631191756"},
                {SECOND, "51589", "9"},
                {SECOND, "01245", "5"},
                {SECOND, "92510", "18"},
                {SECOND, "59414", "2018"},
                {SECOND, getInput(INSTANCE), "20219475"}
        });
    }
}