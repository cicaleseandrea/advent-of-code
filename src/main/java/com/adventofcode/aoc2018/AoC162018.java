package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Operation;

import java.util.*;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;
import static java.util.stream.Collectors.toUnmodifiableList;

class AoC162018 implements Solution {

    private static String solve(final List<String> input, final boolean second) {
        int i = 0;
        final Collection<Operation> availableOperations = EnumSet.allOf(Operation.class);
        final Map<Integer, Operation> operationMappings = new HashMap<>();
        long res = 0;
        do {
            final int[] before = getRegisters(input.get(i++));
            final int[] operation = getRegisters(input.get(i++));
            final int[] next = getRegisters(input.get(i++));
            final int opCode = getOpCode(operation);
            if (second && operationMappings.containsKey(opCode)) {
                continue; //no need to test this opCode
            }
            int matching = matchOperations(availableOperations, operationMappings, before, operation, next);
            if (second) {
                if (matching == 1) {
                    //mapping found is the only possible one
                    availableOperations.remove(operationMappings.get(opCode));
                } else {
                    //this was not the only mapping possible
                    operationMappings.remove(opCode);
                }
            } else if (matching >= 3) {
                res++;
            }
        } while (++i < input.size() && input.get(i).contains("B"));
        if (second) {
            //perform operations
            return performOperations(input, i + 1, operationMappings);
        } else {
            return itoa(res);
        }
    }

    private static int[] getRegisters(final String s) {
        final int[] registers = new int[4];
        final String[] split = s.split("[\\D]+");
        int r = split[0].equals(EMPTY) ? 1 : 0;
        int w = 0;
        while (r < split.length) {
            registers[w++] = atoi(split[r++]);
        }
        return registers;
    }

    private static int matchOperations(final Collection<Operation> availableOperations,
                                       final Map<Integer, Operation> operationMappings, final int[] before,
                                       final int[] operation, final int[] next) {
        int matching = 0;
        for (final Operation operationTested : availableOperations) {
            final int[] tmp = Arrays.copyOf(before, 4);
            operationTested.op.accept(tmp, operation);
            if (Arrays.equals(tmp, next)) {
                operationMappings.put(getOpCode(operation), operationTested);
                matching++;
            }
        }
        return matching;
    }

    private static String performOperations(final List<String> input, int i,
                                            final Map<Integer, Operation> operationMappings) {
        final int[] registers = new int[4];
        while (++i < input.size()) {
            final int[] operation = getRegisters(input.get(i));
            operationMappings.get(getOpCode(operation)).op.accept(registers, operation);
        }
        return itoa(registers[0]);
    }

    private static int getOpCode(final int[] operation) {
        return operation[0];
    }

    public String solveFirstPart(final Stream<String> input) {
        return solve(input.collect(toUnmodifiableList()), false);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(input.collect(toUnmodifiableList()), true);
    }

}