package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC212020Test extends Generic {

    private static final Solution INSTANCE = new AoC212020();

    public AoC212020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                        trh fvjkl sbzzf mxmxvkd (contains dairy)
                        sqjhc fvjkl (contains soy)
                        sqjhc mxmxvkd sbzzf (contains fish)""", "5"  },
                { FIRST, getInput( INSTANCE ), "2211" },
                { SECOND, """
                        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                        trh fvjkl sbzzf mxmxvkd (contains dairy)
                        sqjhc fvjkl (contains soy)
                        sqjhc mxmxvkd sbzzf (contains fish)""", "mxmxvkd,sqjhc,fvjkl"  },
                { SECOND, getInput( INSTANCE ), "vv,nlxsmb,rnbhjk,bvnkk,ttxvphb,qmkz,trmzkcfg,jpvz" }
        });
    }
}
