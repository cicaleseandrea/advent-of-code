package com.adventofcode.aoc2022;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC112022Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC112022();

    public AoC112022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        Monkey 0:
                          Starting items: 79, 98
                          Operation: new = old * 19
                          Test: divisible by 23
                            If true: throw to monkey 2
                            If false: throw to monkey 3
                        
                        Monkey 1:
                          Starting items: 54, 65, 75, 74
                          Operation: new = old + 6
                          Test: divisible by 19
                            If true: throw to monkey 2
                            If false: throw to monkey 0
                        
                        Monkey 2:
                          Starting items: 79, 60, 97
                          Operation: new = old * old
                          Test: divisible by 13
                            If true: throw to monkey 1
                            If false: throw to monkey 3
                        
                        Monkey 3:
                          Starting items: 74
                          Operation: new = old + 3
                          Test: divisible by 17
                            If true: throw to monkey 0
                            If false: throw to monkey 1
                        """, "10605" },
                { FIRST, getInput( INSTANCE ), "151312" },
                { SECOND, """
                        Monkey 0:
                          Starting items: 79, 98
                          Operation: new = old * 19
                          Test: divisible by 23
                            If true: throw to monkey 2
                            If false: throw to monkey 3
                        
                        Monkey 1:
                          Starting items: 54, 65, 75, 74
                          Operation: new = old + 6
                          Test: divisible by 19
                            If true: throw to monkey 2
                            If false: throw to monkey 0
                        
                        Monkey 2:
                          Starting items: 79, 60, 97
                          Operation: new = old * old
                          Test: divisible by 13
                            If true: throw to monkey 1
                            If false: throw to monkey 3
                        
                        Monkey 3:
                          Starting items: 74
                          Operation: new = old + 3
                          Test: divisible by 17
                            If true: throw to monkey 0
                            If false: throw to monkey 1
                        """, "2713310158" },
                { SECOND, getInput( INSTANCE ), "51382025916" }
        });
    }
}
