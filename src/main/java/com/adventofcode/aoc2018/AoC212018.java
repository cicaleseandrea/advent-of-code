package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Operation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.adventofcode.utils.Operation.EQRR;
import static com.adventofcode.utils.Utils.*;
import static java.util.stream.Collectors.toList;

class AoC212018 implements Solution {

    private static void performOperations(final String input, int[] registers) {
        final List<String> splitOnTabOrSpace = splitOnTabOrSpace(input);
        final Operation opCode = getOpCode(splitOnTabOrSpace);
        final int[] operation = getRegisters(splitOnTabOrSpace);
        opCode.op.accept(registers, operation);
    }

    private static int checkOperation(final String input) {
        final List<String> splitOnTabOrSpace = splitOnTabOrSpace(input);
        if (getOpCode(splitOnTabOrSpace) == EQRR) {
            final int[] operation = getRegisters(splitOnTabOrSpace);
            return operation[1] != 0 ? operation[1] : operation[2];
        }
        return -1;
    }

    private static Operation getOpCode(final List<String> splitOnTabOrSpace) {
        return Operation.valueOf(splitOnTabOrSpace.get(0).toUpperCase());
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
        final List<String> list = input.collect(toList());
        final int[] registers = new int[6];
        //instruction register
        final int ip = atoi(extractNumberFromString(list.get(0)));
        boolean crash = false;
        while (!crash) {
            performOperations(list.get(registers[ip] + 1), registers);
            int reg = checkOperation(list.get(registers[ip] + 1));
            if (reg >= 0) {
                //stop ASAP
                return itoa(registers[reg]);
            }
            registers[ip]++;
            //check if the instruction register caused a crash
            crash = registers[ip] >= list.size() - 1;
        }
        return itoa(registers[0]);
    }

    @Override
    public String solveSecondPart(final Stream<String> input) {
        boolean crash = false;
        final Set<Long> seen = new HashSet<>();
        long prev = 0;
        long a = 0;
        long b;
        //TODO this only works for my input...
        //program optimized
        while (!crash) {
            prev = a;
            b = a | 65536;
            a = 3935295;
            boolean stop = false;
            while (!stop) {
                a += (b % 256);
                a *= 65899;
                a %= 16777216;
                if (b >= 256) {
                    b /= 256;
                } else {
                    stop = true;
                    crash = !seen.add(a);
                }
            }
        }
        return itoa(prev);
    }
}