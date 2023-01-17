package com.adventofcode.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class LinkedListNode<T> implements Iterable<LinkedListNode<T>> {

  public final T value;
  public LinkedListNode<T> next;

  public LinkedListNode(final T value) {
    this.value = value;
  }


  @Override
  public String toString() {
    return "[" + StreamSupport.stream( spliterator(), false ).map( e -> String.valueOf( e.value ) )
        .collect( Collectors.joining( ", " ) ) + "]";
  }

  @Override
  public Iterator<LinkedListNode<T>> iterator() {
    return new Iterator<>() {
      LinkedListNode<T> curr = LinkedListNode.this;
      final Set<LinkedListNode<T>> seen = new HashSet<>();

      @Override
      public boolean hasNext() {
        return curr != null && !seen.contains( curr );
      }

      @Override
      public LinkedListNode<T> next() {
        if ( !hasNext() ) {
          throw new NoSuchElementException();
        }
        final var res = curr;
        seen.add( res );
        curr = curr.next;
        return res;
      }
    };
  }
}
