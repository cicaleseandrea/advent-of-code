package com.adventofcode.utils;

import java.util.Iterator;

import com.google.common.base.MoreObjects;

public class LinkedListNode<T> implements Iterable<LinkedListNode<T>> {
	private final T value;
	public LinkedListNode<T> next;

	public LinkedListNode( final T value ) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this ).add( "value", getValue() ).toString();
	}

	@Override
	public Iterator<LinkedListNode<T>> iterator() {
		return new Iterator<>() {
			LinkedListNode<T> curr = LinkedListNode.this;

			@Override
			public boolean hasNext() {
				return curr != null;
			}

			@Override
			public LinkedListNode<T> next() {
				final var res = curr;
				curr = curr.next;
				return res;
			}
		};
	}
}
