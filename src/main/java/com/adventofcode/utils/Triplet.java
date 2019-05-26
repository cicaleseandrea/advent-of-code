package com.adventofcode.utils;

import java.util.Objects;

public class Triplet<T, U, V> {
    public static final Triplet<Long, Long, Long> ZERO = new Triplet<>(0L, 0L, 0L);
    private T first;
    private U second;
    private V third;

    public Triplet(T first, U second, V third) {
        this.first = Objects.requireNonNull(first);
        this.second = Objects.requireNonNull(second);
        this.third = Objects.requireNonNull(third);
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

    public V getThird() {
        return third;
    }

    public void setThird(V third) {
        this.third = third;
    }

    @Override
    public String toString() {
        return first + "," + second + "," + third;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
        return getFirst().equals(triplet.getFirst()) &&
                getSecond().equals(triplet.getSecond()) &&
                getThird().equals(triplet.getThird());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst(), getSecond(), getThird());
    }

}

