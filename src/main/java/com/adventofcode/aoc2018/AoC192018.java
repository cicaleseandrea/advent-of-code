package com.adventofcode.aoc2018;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.extractIntegerFromString;
import static com.adventofcode.utils.Utils.itoa;
import static com.adventofcode.utils.Utils.splitOnTabOrSpace;

import com.adventofcode.Solution;
import com.adventofcode.utils.Operation2018;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class AoC192018 implements Solution {

    private static String solve(final List<String> input, final int i) {
        final int[] registers = new int[6];
        registers[0] = i;
        //instruction register
        final int ip = extractIntegerFromString( input.get( 0 ) );
        boolean crash = false;
        while (!crash) {
            if (input.size() > 10 && registers[ip] == 1) {
                //optimize the program
                return itoa( computeDivisorsSum( Arrays.stream( registers ).max().orElseThrow() ) );
            }
            performOperations(input.get(registers[ip] + 1), registers);
            registers[ip]++;
            //check if the instruction register caused a crash
            crash = registers[ip] >= input.size() - 1;
        }
        return itoa(registers[0]);
    }

    private static long computeDivisorsSum(final int n) {
        long res = 0;
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                res += i;
                if ((n / i) != i) res += (n / i);
            }
        }
        return res;
    }

    private static void performOperations(final String input, int[] registers) {
        final List<String> splitOnTabOrSpace = splitOnTabOrSpace(input);
        final Operation2018 opCode = getOpCode(splitOnTabOrSpace);
        final int[] operation = getRegisters(splitOnTabOrSpace);
        opCode.op.accept(registers, operation);
    }

    private static Operation2018 getOpCode(final List<String> splitOnTabOrSpace) {
        return Operation2018.valueOf(splitOnTabOrSpace.get(0).toUpperCase());
    }

    private static int[] getRegisters(final List<String> splitOnTabOrSpace) {
        final int[] registers = new int[4];
        for (int i = 1; i < splitOnTabOrSpace.size(); i++) {
            final String s = splitOnTabOrSpace.get(i);
            registers[i] = atoi(s);
        }
        return registers;
    }

    @Override
    public String solveFirstPart(final Stream<String> input) {
        return solve(input.toList(), 0);
    }

    @Override
    public String solveSecondPart(final Stream<String> input) {
        return solve(input.toList(), 1);
    }
}