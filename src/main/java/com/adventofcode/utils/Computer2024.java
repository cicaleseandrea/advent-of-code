package com.adventofcode.utils;

import java.util.ArrayList;
import java.util.List;

public class Computer2024 {

  private int instrPointer;
  private final int[] instructions;
  private final long[] registers;
  private final List<Integer> output = new ArrayList<>();

  public Computer2024(final List<Integer> instructions, final long a) {
    this.instrPointer = 0;
    this.instructions = instructions.stream().mapToInt( Integer::intValue ).toArray();
    this.registers = new long[]{a, 0, 0};
  }

  public boolean step() {
    if ( instrPointer >= instructions.length ) {
      return false;
    }
    int opCode = instructions[instrPointer];
    switch ( opCode ) {
      case 0 -> registers[0] = registers[0] >> getComboOperand();
      case 1 -> registers[1] ^= getLiteralOperand();
      case 2 -> registers[1] = getComboOperand() % 8;
      case 3 -> instrPointer = registers[0] != 0 ? getLiteralOperand() - 2 : instrPointer;
      case 4 -> registers[1] ^= registers[2];
      case 5 -> output.add( (int) (getComboOperand() % 8) );
      case 6 -> registers[1] = registers[0] >> getComboOperand();
      case 7 -> registers[2] = registers[0] >> getComboOperand();
      default -> throw new IllegalStateException( "Unexpected opCode: " + opCode );
    }
    instrPointer += 2;
    return true;
  }

  private int getLiteralOperand() {
    return instructions[instrPointer + 1];
  }

  private long getComboOperand() {
    int operand = getLiteralOperand();
    return switch ( operand ) {
      case 0, 1, 2, 3 -> operand;
      case 4, 5, 6 -> registers[operand - 4];
      default -> throw new IllegalStateException( "Unexpected combo operand: " + operand );
    };
  }

  public List<Integer> getOutput() {
    return output;
  }
}
