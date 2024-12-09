package com.adventofcode.aoc2024;

import static com.adventofcode.utils.Utils.charToInt;
import static com.adventofcode.utils.Utils.getFirstString;
import static com.adventofcode.utils.Utils.itoa;

import com.adventofcode.Solution;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

class AoC092024 implements Solution {

  private static final int FREE_ID = -1;

  @Override
  public String solveFirstPart(final Stream<String> input) {
    return solve( input, true );
  }

  @Override
  public String solveSecondPart(final Stream<String> input) {
    return solve( input, false );
  }

  private String solve(final Stream<String> input, final boolean first) {
    List<Block> freeBlocks = new ArrayList<>();
    List<Block> fileBlocks = new ArrayList<>();
    initializeBlocks( getFirstString( input ).toCharArray(), freeBlocks, fileBlocks );

    List<Block> movedBlocks = new ArrayList<>();
    //iterate file blocks from the last
    for ( int i = fileBlocks.size() - 1; i >= 0; i-- ) {
      Block fileBlock = fileBlocks.get( i );
      //iterate free blocks from the first
      boolean fullFileMoved = false;
      for ( Iterator<Block> freeBlocksItr = freeBlocks.iterator();
          freeBlocksItr.hasNext() && !fullFileMoved; ) {
        Block freeBlock = freeBlocksItr.next();
        if ( freeBlock.start > fileBlock.start ) {
          //crossed free blocks
          break;
        }
        int movedBlockStart = freeBlock.start;
        int movedBlockEnd = movedBlockStart + fileBlock.size();
        if ( freeBlock.size() == fileBlock.size() ) {
          fullFileMoved = true;
          freeBlocksItr.remove();
        } else if ( freeBlock.size() > fileBlock.size() ) {
          fullFileMoved = true;
          //resize free block
          freeBlock.setStart( movedBlockEnd );
        } else if ( first ) {
          //move file partially
          movedBlockEnd = freeBlock.end;
          fileBlock.setEnd( fileBlock.end - freeBlock.size() );
          freeBlocksItr.remove();
        }
        if ( fullFileMoved || first ) {
          movedBlocks.add( new Block( fileBlock.id, movedBlockStart, movedBlockEnd ) );
        }
      }
      if ( !fullFileMoved ) {
        //keep file in the same position
        movedBlocks.add( fileBlock );
      } else if ( !first ) {
        freeBlocks.add( new Block( FREE_ID, fileBlock.start, fileBlock.end ) );
        mergeBlocks( freeBlocks );
      }
    }

    return itoa( movedBlocks.stream().mapToLong( Block::computeCheckSum ).sum() );
  }

  private void initializeBlocks(final char[] disk, final List<Block> freeBlocks,
      final List<Block> fileBlocks) {
    int id = 0;
    int start = 0;
    for ( int i = 0; i < disk.length; i++ ) {
      int size = charToInt( disk[i] );
      int end = start + size;
      if ( i % 2 != 0 ) {
        freeBlocks.add( new Block( FREE_ID, start, end ) );
      } else {
        fileBlocks.add( new Block( id++, start, end ) );
      }
      start = end;
    }
  }

  private void mergeBlocks(final List<Block> freeBlocks) {
    if ( freeBlocks.isEmpty() ) {
      return;
    }
    Iterator<Block> iterator = freeBlocks.iterator();
    Block prev = iterator.next();
    while ( iterator.hasNext() ) {
      Block curr = iterator.next();
      if ( prev.end == curr.start ) {
        prev.setEnd( curr.end );
        iterator.remove();
      } else {
        prev = curr;
      }
    }
  }

  private static final class Block {

    final int id;
    int start;
    int end;

    private Block(int id, int start, int end) {
      this.id = id;
      this.start = start;
      this.end = end;
    }

    void setStart(int start) {
      this.start = start;
    }

    void setEnd(int end) {
      this.end = end;
    }

    int size() {
      return end - start;
    }

    public long computeCheckSum() {
      long result = 0;
      for ( long j = start; j < end; j++ ) {
        result += (id * j);
      }
      return result;
    }

    @Override
    public String toString() {
      return "id: " + id + " block: [" + start + ", " + end + "] size: " + size();
    }
  }
}
