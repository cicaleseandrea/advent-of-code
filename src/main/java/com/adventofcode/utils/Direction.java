package com.adventofcode.utils;

public enum Direction {
    DOWN('v') {
        @Override
        public Direction rotateClockwise() {
            return LEFT;
        }

        @Override
        public Direction rotateCounterClockwise() {
            return RIGHT;
        }
    }, LEFT('<') {
        @Override
        public Direction rotateClockwise() {
            return UP;
        }

        @Override
        public Direction rotateCounterClockwise() {
            return DOWN;
        }
    }, RIGHT('>') {
        @Override
        public Direction rotateClockwise() {
            return DOWN;
        }

        @Override
        public Direction rotateCounterClockwise() {
            return UP;
        }
    }, UP('^') {
        @Override
        public Direction rotateClockwise() {
            return RIGHT;
        }

        @Override
        public Direction rotateCounterClockwise() {
            return LEFT;
        }
    };
    final char symbol;

    Direction(final char c) {
        this.symbol = c;
    }

    public abstract Direction rotateClockwise();

    public abstract Direction rotateCounterClockwise();

    public char getSymbol() {
        return symbol;
    }

    public Direction rotate(final char c) {
        switch (c) {
            case '\\':
                if (this == UP || this == DOWN) return rotateCounterClockwise();
                if (this == RIGHT || this == LEFT) return rotateClockwise();
                break;
            case '/':
                if (this == UP || this == DOWN) return rotateClockwise();
                if (this == RIGHT || this == LEFT) return rotateCounterClockwise();
                break;
            default:
                return this;
        }
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
