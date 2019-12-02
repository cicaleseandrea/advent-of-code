package com.adventofcode.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Computer2019 {
    public List<Long> memory;
    public int pointer = 0;

    private int getAddress(final int index) {
        return getValue(index).intValue();
    }

    private Long getValueIndirect(final int index) {
        return getValue(getAddress(index));
    }

    private Long getValue(final int address) {
        return memory.get(address);
    }

    private Long setValueIndirect(final int index, final Long val) {
        return setValue(getAddress(index), val);
    }

    private Long setValue(final int address, final Long val) {
        return memory.set(address, val);
    }

    private Long getNextParameterIndirect() {
        return getValueIndirect(advancePointer());
    }

    private Long getNextParameter() {
        return getValue(advancePointer());
    }

    public int advancePointer() {
        return ++pointer;
    }

    public void loadProgram(final List<Long> program) {
        memory = new ArrayList<>(program);
    }

    public void setPointer(final int pointer) {
        this.pointer = pointer;
    }

    private OpCode currentInstruction() {
        return OpCode.valueOf(getValue(pointer));
    }

    public Long executeOneStep() {
        final OpCode instruction = currentInstruction();
        if (instruction == null) {
            return null;
        }
        final Long res = instruction.apply(this);
        advancePointer();
        return res;
    }

    public void run() {
        while (true) {
            if (executeOneStep() == null) return;
        }
    }

    public OpCode printOneStep() {
        final OpCode instruction = currentInstruction();
        final String str = instruction != null ? instruction.toString(this) : null;
        System.out.println(str);
        return instruction;
    }

    public void printProgram() {
        System.out.println("PROGRAM");
        System.out.println("=======");
        final int origPointer = pointer;

        setPointer(0);
        while (pointer < memory.size()) {
            final OpCode instruction = printOneStep();
            final int nParams = instruction != null ? instruction.nParams : 0;
            pointer += nParams + 1;
        }
        System.out.println("=======");

        this.pointer = origPointer;
    }

    private enum OpCode implements Function<Computer2019, Long> {
        ADD(1, 3) {
            @Override
            public Long apply(final Computer2019 computer) {
                final Long first = computer.getNextParameterIndirect();
                final Long second = computer.getNextParameterIndirect();
                return computer.setValueIndirect(computer.advancePointer(), first + second);
            }

            @Override
            public String toString(final Computer2019 computer) {
                final Long first = computer.getValue(computer.pointer + 1);
                final Long second = computer.getValue(computer.pointer + 2);
                final Long third = computer.getValue(computer.pointer + 3);
                return "m[" + third + "] = m[" + first + "] + m[" + second + "]";
            }
        }, MULTIPLY(2, 3) {
            @Override
            public Long apply(final Computer2019 computer) {
                final Long first = computer.getNextParameterIndirect();
                final Long second = computer.getNextParameterIndirect();
                return computer.setValueIndirect(computer.advancePointer(), first * second);
            }

            @Override
            public String toString(final Computer2019 computer) {
                final Long first = computer.getValue(computer.pointer + 1);
                final Long second = computer.getValue(computer.pointer + 2);
                final Long third = computer.getValue(computer.pointer + 3);
                return "m[" + third + "] = m[" + first + "] * m[" + second + "]";
            }
        }, HALT(99, 0) {
            @Override
            public Long apply(final Computer2019 computer) {
                return null;
            }

            @Override
            public String toString(final Computer2019 computer) {
                return "nop";
            }
        };

        private final long code;
        private final int nParams;

        OpCode(final long code, int nParams) {
            this.code = code;
            this.nParams = nParams;
        }

        private static final Map<Long, OpCode> OP_CODES = new HashMap<>();

        static {
            for (final OpCode op : OpCode.values()) {
                OP_CODES.put(op.code, op);
            }
        }

        static OpCode valueOf(long code) {
            return OP_CODES.get(code);
        }

        long getValue() {
            return code;
        }

        abstract String toString(final Computer2019 computer);

        public abstract Long apply(final Computer2019 computer);
    }
}
