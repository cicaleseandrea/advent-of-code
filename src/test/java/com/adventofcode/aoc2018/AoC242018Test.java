package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC242018Test extends Generic {

    private static final Solution INSTANCE = new AoC242018();

    public AoC242018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        Immune System:
                        17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2
                        989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3
                        
                        Infection:
                        801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1
                        4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4""", "5216"},
                {FIRST, getInput(INSTANCE), "20340"},
                {SECOND, """
                        Immune System:
                        17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2
                        989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3
                        
                        Infection:
                        801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1
                        4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4""", "51"},
                {SECOND, getInput(INSTANCE), "3862"}
        });
    }
}