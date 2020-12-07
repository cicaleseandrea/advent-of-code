package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC072020Test extends Generic {

    private static final Solution INSTANCE = new AoC072020();

    public AoC072020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        light red bags contain 1 bright white bag, 2 muted yellow bags.
                        dark orange bags contain 3 bright white bags, 4 muted yellow bags.
                        bright white bags contain 1 shiny gold bag.
                        muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
                        shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
                        dark olive bags contain 3 faded blue bags, 4 dotted black bags.
                        vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
                        faded blue bags contain no other bags.
                        dotted black bags contain no other bags.
                        """, "4" },
                { FIRST, getInput( INSTANCE ), "289" },
                { SECOND, """
                        light red bags contain 1 bright white bag, 2 muted yellow bags.
                        dark orange bags contain 3 bright white bags, 4 muted yellow bags.
                        bright white bags contain 1 shiny gold bag.
                        muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
                        shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
                        dark olive bags contain 3 faded blue bags, 4 dotted black bags.
                        vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
                        faded blue bags contain no other bags.
                        dotted black bags contain no other bags.
                        """, "32" },
                { SECOND, """
                        shiny gold bags contain 2 dark red bags.
                        dark red bags contain 2 dark orange bags.
                        dark orange bags contain 2 dark yellow bags.
                        dark yellow bags contain 2 dark green bags.
                        dark green bags contain 2 dark blue bags.
                        dark blue bags contain 2 dark violet bags.
                        dark violet bags contain no other bags.
                        """, "126" },
                { SECOND, getInput( INSTANCE ), "30055" }
        });
    }
}
