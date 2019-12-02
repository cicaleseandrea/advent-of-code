package com.adventofcode.aoc2019;

import com.adventofcode.Solution;

import java.util.List;
import java.util.stream.Stream;

import static com.adventofcode.utils.Utils.*;

class AoC022019 implements Solution {

    public String solveFirstPart(final Stream<String> input) {
        final Computer2019 computer = new Computer2019();
        final List<Long> program = setProgramInputs(toLongList(getFirstString(input)), 12L, 2L);
        computer.loadProgram(program);
        computer.run();
        return itoa(computer.memory.get(0));
    }

    public String solveSecondPart(final Stream<String> input) {
        final Computer2019 computer = new Computer2019();
        final List<Long> program = toLongList(getFirstString(input));
        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                computer.loadProgram(setProgramInputs(program, noun, verb));
                computer.setPointer(0);
                computer.run();
                if (computer.memory.get(0) == 19690720) {
                    return itoa(100 * noun + verb);
                }
            }
        }
        throw new IllegalStateException();
    }

    private List<Long> setProgramInputs(final List<Long> memory, final long noun, final long verb) {
        if (memory.size() > 10) {
            memory.set(1, noun);
            memory.set(2, verb);
        }
        return memory;
    }
}
