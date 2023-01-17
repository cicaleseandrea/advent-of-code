package com.adventofcode.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class TreeNode<K> {
    private final K key;
    private final List<TreeNode<K>> children;
    private TreeNode<K> parent;

    public TreeNode(K key) {
        this.key = Objects.requireNonNull(key);
        children = new ArrayList<>(1);
    }

    public K getKey() {
        return key;
    }

    public Optional<TreeNode<K>> getParent() {
        return Optional.ofNullable(parent);
    }

    private void setParent(TreeNode<K> parent) {
        this.parent = parent;
    }

    public List<TreeNode<K>> getChildren() {
        return children;
    }

    private void removeParent() {
        parent = null;
    }

    public void addChild(TreeNode<K> child) {
        if (child != null) {
            children.add(child);
            child.setParent(this);
        }
    }

    public void removeChild(TreeNode<K> child) {
        if (children.remove(child)) {
            child.removeParent();
        }
    }

    public void visit(Consumer<K> consumer) {
        consumer.accept(key);
        for ( TreeNode<K> n : children) {
            n.visit(consumer);
        }
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        visit(k -> str.append(k).append(", "));
        str.delete(str.length() - 2, str.length());
        return str.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TreeNode<?> node = (TreeNode<?>) o;
        return getKey().equals(node.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }
}

