package com.adventofcode.aoc2021;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC102021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC102021();

    public AoC102021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        [({(<(())[]>[[{[]{<()<>>
                        [(()[<>])]({[<{<<[]>>(
                        {([(<{}[<>[]}>{[]{[(<()>
                        (((({<>}<{<{<>}{[]{[]{}
                        [[<[([]))<([[{}[[()]]]
                        [{[{({}]{}}([{[{{{}}([]
                        {<[[]]>}<{[{[{[]{()[[[]
                        [<(<(<(<{}))><([]([]()
                        <{([([[(<>()){}]>(<<{{
                        <{([{{}}[<[[[<>{}]]]>[]]
                        """, "26397" },
                { FIRST, getInput( INSTANCE ), "168417" },
                { SECOND, """
                        [({(<(())[]>[[{[]{<()<>>
                        [(()[<>])]({[<{<<[]>>(
                        {([(<{}[<>[]}>{[]{[(<()>
                        (((({<>}<{<{<>}{[]{[]{}
                        [[<[([]))<([[{}[[()]]]
                        [{[{({}]{}}([{[{{{}}([]
                        {<[[]]>}<{[{[{[]{()[[[]
                        [<(<(<(<{}))><([]([]()
                        <{([([[(<>()){}]>(<<{{
                        <{([{{}}[<[[[<>{}]]]>[]]
                        """, "288957" },
                { SECOND, getInput( INSTANCE ), "2802519786" }
        });
    }
}
