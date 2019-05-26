package com.adventofcode.utils;

import java.util.function.BiConsumer;

public enum Operation {
    ADDR((r, o) -> r[o[3]] = registerA(r, o) + registerB(r, o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] + r[" + o[2] + "]";
        }
    },
    ADDI((r, o) -> r[o[3]] = registerA(r, o) + valueB(o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] + " + o[2];
        }
    },
    MULR((r, o) -> r[o[3]] = registerA(r, o) * registerB(r, o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] * r[" + o[2] + "]";
        }
    },
    MULI((r, o) -> r[o[3]] = registerA(r, o) * valueB(o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] * " + o[2];
        }
    },
    BANR((r, o) -> r[o[3]] = registerA(r, o) & registerB(r, o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] & r[" + o[2] + "]";
        }
    },
    BANI((r, o) -> r[o[3]] = registerA(r, o) & valueB(o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] & " + o[2];
        }
    },
    BORR((r, o) -> r[o[3]] = registerA(r, o) | registerB(r, o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] | r[" + o[2] + "]";
        }
    },
    BORI((r, o) -> r[o[3]] = registerA(r, o) | valueB(o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] | " + o[2];
        }
    },
    SETR((r, o) -> r[o[3]] = registerA(r, o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "]";
        }
    },
    SETI((r, o) -> r[o[3]] = valueA(o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " " + o[1];
        }
    },
    GTIR((r, o) -> r[o[3]] = valueA(o) > registerB(r, o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " " + o[1] + " > r[" + o[2] + "]";
        }
    },
    GTRI((r, o) -> r[o[3]] = registerA(r, o) > valueB(o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] > " + o[2];
        }
    },
    GTRR((r, o) -> r[o[3]] = registerA(r, o) > registerB(r, o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] > r[" + o[2] + "]";
        }
    },
    EQIR((r, o) -> r[o[3]] = valueA(o) == registerB(r, o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " " + o[1] + " == r[" + o[2] + "]";
        }
    },
    EQRI((r, o) -> r[o[3]] = registerA(r, o) == valueB(o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] == " + o[2];
        }
    },
    EQRR((r, o) -> r[o[3]] = registerA(r, o) == registerB(r, o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + o[1] + "] == r[" + o[2] + "]";
        }
    };

    public final BiConsumer<int[], int[]> op;

    Operation(final BiConsumer<int[], int[]> op) {
        this.op = op;
    }

    private static int registerA(final int[] registers, final int[] op) {
        return registers[op[1]];
    }

    private static int registerB(final int[] registers, final int[] op) {
        return registers[op[2]];
    }

    private static int valueA(final int[] op) {
        return op[1];
    }

    private static int valueB(final int[] op) {
        return op[2];
    }

    public String stringRepresentation(final int[] o) {
        return "r[" + o[3] + "] =";
    }
}

