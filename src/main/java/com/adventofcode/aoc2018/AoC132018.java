package com.adventofcode.aoc2018;

import com.adventofcode.Solution;
import com.adventofcode.utils.Direction;
import com.adventofcode.utils.Pair;

import java.util.*;
import java.util.stream.Stream;

import static com.adventofcode.utils.Direction.LEFT;
import static com.adventofcode.utils.Direction.RIGHT;
import static com.adventofcode.utils.Utils.*;

class AoC132018 implements Solution {

    private static final boolean PRINT = Boolean.parseBoolean(System.getProperty("print"));

    private static String solve(final Stream<String> input, final boolean first) {
        final Map<Pair<Long, Long>, Character> track = new HashMap<>();
        Map<Pair<Long, Long>, Cart> carts = new TreeMap<>(getPairComparator());
        final Pair<Long, Long> lastPoint = init(input, carts, track);
        Optional<Pair<Long, Long>> result = Optional.empty();
        while (result.isEmpty()) {
            final Map<Pair<Long, Long>, Cart> nextCarts = new TreeMap<>(getPairComparator());
            final Iterator<Cart> iterator = carts.values().iterator();
            //perform a "tick" and move all carts
            while (iterator.hasNext()) {
                final Cart cart = iterator.next();
                iterator.remove();
                //compute new position
                if (!cart.crashed) {
                    final Pair<Long, Long> newPoint = cart.move(track);
                    if (carts.containsKey(newPoint) || nextCarts.containsKey(newPoint)) {
                        //someone is already here
                        if (first) {
                            //crash!
                            result = Optional.ofNullable(newPoint);
                        } else {
                            //remove the carts
                            nextCarts.remove(newPoint);
                            carts.getOrDefault(newPoint, new Cart(DOT, Pair.ZERO)).setCrashed();
                        }
                    } else {
                        //safe move
                        nextCarts.put(newPoint, cart);
                    }
                }
            }
            carts = nextCarts;
            if (PRINT) {
                print(track, carts, lastPoint);
            }
            if (carts.size() == 1) {
                result = carts.keySet().stream().findAny();
            }
        }
        return result.get().toString();
    }

    private static Pair<Long, Long> init(final Stream<String> input, final Map<Pair<Long, Long>, Cart> carts,
                                         final Map<Pair<Long, Long>, Character> track) {
        long y = 0;
        long x = 0;
        for (final String line : getIterable(input)) {
            x = 0;
            for (final char c : line.toCharArray()) {
                final var position = new Pair<>(x, y);
                if (Cart.isCart(c)) {
                    final Cart cart = new Cart(c, position);
                    carts.put(position, cart);
                    track.put(position, cart.direction == RIGHT || cart.direction == LEFT ? '-' : PIPE);
                } else {
                    track.put(position, c);
                }
                x++;
            }
            y++;
        }
        return new Pair<>(x, y);
    }

    private static void print(final Map<Pair<Long, Long>, Character> track, final Map<Pair<Long, Long>, Cart> carts,
                              final Pair<Long, Long> last) {
        clearScreen();
        for (int i = 0; i < last.getSecond(); i++) {
            for (int j = 0; j < last.getFirst(); j++) {
                final Pair<Integer, Integer> p = new Pair<>(j, i);
                if (carts.containsKey(p)) {
                    System.out.print(carts.get(p));
                } else {
                    System.out.print(track.getOrDefault(p, DOT));
                }
            }
            System.out.println();
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String solveFirstPart(final Stream<String> input) {
        return solve(input, true);
    }

    public String solveSecondPart(final Stream<String> input) {
        return solve(input, false);
    }

    private static class Cart {
        Direction direction;
        boolean crashed;
        private Pair<Long, Long> position;
        private int i;

        Cart(final char symbol, final Pair<Long, Long> position) {
            direction = symbolDirection(symbol);
            this.position = position;
            i = 0;
        }

        static boolean isCart(final char c) {
            boolean isCart = false;
            for (Direction d : Direction.values()) {
                if (d.getSymbol() == c)
                    isCart = true;
            }
            return isCart;
        }

        private Direction symbolDirection(final char c) {
            for (final Direction d : Direction.values()) {
                if (d.getSymbol() == c)
                    return d;
            }
            return null;
        }

        Pair<Long, Long> move(final Map<Pair<Long, Long>, Character> track) {
            updatePosition();
            rotate(track);
            return position;
        }

        private void updatePosition() {
            position =
                    switch (direction) {
                        case UP -> new Pair<>(position.getFirst(), position.getSecond() - 1);
                        case DOWN -> position = new Pair<>(position.getFirst(), position.getSecond() + 1);
                        case LEFT -> position = new Pair<>(position.getFirst() - 1, position.getSecond());
                        case RIGHT -> position = new Pair<>(position.getFirst() + 1, position.getSecond());
                    };
        }

        private void rotate(final Map<Pair<Long, Long>, Character> track) {
            final Character c = track.get(position);
            if (c == '+') {
                direction = switch (i) {
                    case 0 -> direction.rotateCounterClockwise();
                    case 2 -> direction.rotateClockwise();
                    default -> direction;
                };
                i = incrementMod(i, 3);
            } else {
                direction = direction.rotate(c);
            }
        }

        @Override
        public String toString() {
            return direction.toString();
        }

        void setCrashed() {
            crashed = true;
        }
    }
}