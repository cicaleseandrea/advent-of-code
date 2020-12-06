package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC062020Test extends Generic {

    private static final Solution INSTANCE = new AoC062020();

    public AoC062020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        abc
                        
                        a
                        b
                        c
                        
                        ab
                        ac
                        
                        a
                        a
                        a
                        a
                        
                        b""", "11" },
                { FIRST, getInput( INSTANCE ), "6457" },
                { SECOND, """
                        abc
                        
                        a
                        b
                        c
                        
                        ab
                        ac
                        
                        a
                        a
                        a
                        a
                        
                        b""", "6" },
                { SECOND, getInput( INSTANCE ), "3260" }
        });
    }
}
