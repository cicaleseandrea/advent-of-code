package com.adventofcode.utils;

import java.util.function.BiConsumer;

public enum Operation2018 {
    ADDR((r, o) -> r[o[3]] = registerA(r, o) + registerB(r, o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] + r[" + valueB(o) + "]";
        }
    },
    ADDI((r, o) -> r[o[3]] = registerA(r, o) + valueB(o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] + " + valueB(o);
        }
    },
    MULR((r, o) -> r[o[3]] = registerA(r, o) * registerB(r, o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] * r[" + valueB(o) + "]";
        }
    },
    MULI((r, o) -> r[o[3]] = registerA(r, o) * valueB(o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] * " + valueB(o);
        }
    },
    BANR((r, o) -> r[o[3]] = registerA(r, o) & registerB(r, o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] & r[" + valueB(o) + "]";
        }
    },
    BANI((r, o) -> r[o[3]] = registerA(r, o) & valueB(o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] & " + valueB(o);
        }
    },
    BORR((r, o) -> r[o[3]] = registerA(r, o) | registerB(r, o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] | r[" + valueB(o) + "]";
        }
    },
    BORI((r, o) -> r[o[3]] = registerA(r, o) | valueB(o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] | " + valueB(o);
        }
    },
    SETR((r, o) -> r[o[3]] = registerA(r, o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "]";
        }
    },
    SETI((r, o) -> r[o[3]] = valueA(o)) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " " + valueA(o);
        }
    },
    GTIR((r, o) -> r[o[3]] = valueA(o) > registerB(r, o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " " + valueA(o) + " > r[" + valueB(o) + "]";
        }
    },
    GTRI((r, o) -> r[o[3]] = registerA(r, o) > valueB(o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] > " + valueB(o);
        }
    },
    GTRR((r, o) -> r[o[3]] = registerA(r, o) > registerB(r, o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] > r[" + valueB(o) + "]";
        }
    },
    EQIR((r, o) -> r[o[3]] = valueA(o) == registerB(r, o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " " + valueA(o) + " == r[" + valueB(o) + "]";
        }
    },
    EQRI((r, o) -> r[o[3]] = registerA(r, o) == valueB(o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] == " + valueB(o);
        }
    },
    EQRR((r, o) -> r[o[3]] = registerA(r, o) == registerB(r, o) ? 1 : 0) {
        @Override
        public String stringRepresentation(final int[] o) {
            return super.stringRepresentation(o) + " r[" + valueA(o) + "] == r[" + valueB(o) + "]";
        }
    };

    public final BiConsumer<int[], int[]> op;

    Operation2018(final BiConsumer<int[], int[]> op) {
        this.op = op;
    }

    private static int registerA(final int[] registers, final int[] op) {
        return registers[valueA(op)];
    }

    private static int registerB(final int[] registers, final int[] op) {
        return registers[valueB(op)];
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

