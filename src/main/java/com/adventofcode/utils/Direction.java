package com.adventofcode.utils;

import static com.adventofcode.utils.Utils.decrementMod;
import static com.adventofcode.utils.Utils.incrementMod;
import static java.util.Comparator.comparingInt;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    DOWN('v', 0), LEFT('<', 1), UP('^', 2), RIGHT('>', 3);

    static final List<Direction> BY_POSITION =
        Arrays.stream( values() ).sorted( comparingInt( Direction::getPosition ) ).toList();

    final char symbol;
    final int position;

    Direction(final char c, final int position) {
        this.symbol = c;
        this.position = position;
    }

    public Direction rotateClockwise() {
        return BY_POSITION.get(incrementMod(position, BY_POSITION.size()));
    }

    public Direction rotateCounterClockwise() {
        return BY_POSITION.get(decrementMod(position, BY_POSITION.size()));
    }

    public Direction rotate(final char c) {
        if (this == UP || this == DOWN) {
            if (c == '\\') return rotateCounterClockwise();
            else if (c == '/') return rotateClockwise();
        } else if (this == RIGHT || this == LEFT) {
            if (c == '\\') return rotateClockwise();
            else if (c == '/') return rotateCounterClockwise();
        }
        return this;
    }

    public void move(final Pair<Long, Long> point) {
        switch (this) {
            case UP -> point.setSecond(point.getSecond() + 1L);
            case DOWN -> point.setSecond(point.getSecond() - 1L);
            case LEFT -> point.setFirst(point.getFirst() - 1L);
            case RIGHT -> point.setFirst(point.getFirst() + 1L);
        }
    }

    public static Direction fromSymbol( final char c ) {
        for ( final Direction d : Direction.values() ) {
            if ( d.getSymbol() == c ) {
                return d;
            }
        }
        return null;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
