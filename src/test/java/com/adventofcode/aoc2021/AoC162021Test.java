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
public class AoC162021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC162021();

    public AoC162021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "8A004A801A8002F478", "16" },
                { FIRST, "620080001611562C8802118E34", "12" },
                { FIRST, "C0015000016115A2E0802F182340", "23" },
                { FIRST, "A0016C880162017C3686B18A3D4780", "31" },
                { FIRST, getInput( INSTANCE ), "1012" },
                { SECOND, "D2FE28", "2021" },
                { SECOND, "38006F45291200", "1" },
                { SECOND, "EE00D40C823060", "3" },
                { SECOND, "C200B40A82", "3" },
                { SECOND, "04005AC33890", "54" },
                { SECOND, "880086C3E88112", "7" },
                { SECOND, "CE00C43D881120", "9" },
                { SECOND, "D8005AC2A8F0", "1" },
                { SECOND, "F600BC2D8F", "0" },
                { SECOND, "9C005AC2F8F0", "0" },
                { SECOND, "9C0141080250320F1802104A08", "1" },
                { SECOND, getInput( INSTANCE ), "2223947372407" }
        });
    }
}
