package com.adventofcode.aoc2016;

import static com.adventofcode.utils.Utils.atoi;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import com.adventofcode.utils.LinkedListNode;
import java.util.stream.Stream;

class AoC192016 implements Solution {

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    int size = atoi( getFirstString( input ) );
    var curr = getCircularList( size );

    curr = getNode( curr, first ? 0 : (size / 2) - 1 );
    while ( size > 1 ) {
      //remove node
      curr.next = curr.next.next;
      size--;
      if ( first || size % 2 == 0 ) {
        //move pointer
        curr = curr.next;
      }
    }

    return itoa( curr.value );
  }

  private LinkedListNode<Integer> getCircularList(final int size) {
    final LinkedListNode<Integer> head = new LinkedListNode<>( null );
    var curr = head;
    for ( int i = 1; i <= size; i++ ) {
      curr.next = new LinkedListNode<>( i );
      curr = curr.next;
    }
    //circular list
    curr.next = head.next;

    return curr.next;
  }

  private <T> LinkedListNode<T> getNode(final LinkedListNode<T> head, final int index) {
    var curr = head;
    for ( int i = 0; i < index; i++ ) {
      curr = curr.next;
    }
    return curr;
  }
}
