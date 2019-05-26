package com.adventofcode.utils;

import java.util.Objects;

public class Pair<T, U> {
    public static final Pair<Long, Long> ZERO = new Pair<>(0L, 0L);
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = Objects.requireNonNull(first);
        this.second = Objects.requireNonNull(second);
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public U getSecond() {
        return second;
    }

    public void setSecond(U second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return first + "," + second;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Pair<?, ?> pair = (Pair<?, ?>) o;
        return getFirst().equals(pair.getFirst()) &&
                getSecond().equals(pair.getSecond());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond());
    }

}

