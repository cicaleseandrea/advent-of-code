package com.adventofcode.utils;

import com.google.common.base.MoreObjects;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DisjointSet<T> {

  private final Map<T, Node<T>> nodes = new HashMap<>();
  private int size = 0;

  public void makeSet(final T value) {
    nodes.computeIfAbsent(value, v -> {
      size++;
      return new Node<>(v);
    });
  }

  public Node<T> find(final T value) {
    final var node = nodes.get(value);
    return find(node);
  }

  private Node<T> find(final Node<T> node) {
    //find representative
    if (node.parent == null) {
      return node;
    } else {
      node.parent = find(node.parent);
      return node.parent;
    }
  }

  public boolean union(final T a, final T b) {
    final var representativeA = find(a);
    final var representativeB = find(b);
    if (representativeA != representativeB) {
      //different sets
      //merge sets
      representativeB.parent = representativeA;
      //update sizes
      representativeA.size += representativeB.size;
      size--;
      return true;
    }
    return false;
  }

  public int getSize(final T value) {
    return find(value).size;
  }

  public int getSize() {
    return size;
  }

  public List<Integer> getSizes() {
    return nodes.values().stream().filter(n -> n.parent == null).map(Node::getSize).toList();
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).addValue(nodes.values()).toString();
  }

  public static class Node<T> {

    private final T value;
    private Node<T> parent;
    private int size;

    public Node(T value) {
      this.value = Objects.requireNonNull(value);
      this.parent = null;
      this.size = 1;
    }

    public T getValue() {
      return value;
    }

    public int getSize() {
      return size;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("").addValue(value).add("parent", parent).toString();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Node<?> node = (Node<?>) o;
      return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }
}
