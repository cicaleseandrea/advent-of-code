package com.adventofcode.utils;

import static java.math.BigInteger.ZERO;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Computer2024 {

  public static final BigInteger EIGHT = BigInteger.valueOf( 8 );
  private int instrPointer;
  private final int[] instructions;
  private final BigInteger[] registers;
  private final List<Integer> output = new ArrayList<>();

  public Computer2024(final List<Integer> instructions, final BigInteger a) {
    this.instrPointer = 0;
    this.instructions = instructions.stream().mapToInt( Integer::intValue ).toArray();
    this.registers = new BigInteger[]{a, ZERO, ZERO};
  }

  public boolean step() {
    if ( instrPointer >= instructions.length ) {
      return false;
    }
    int opCode = getInstruction();
    switch ( opCode ) {
      case 0 -> registers[0] = division();
      case 1 -> registers[1] = xor( BigInteger.valueOf( getLiteralOperand() ) );
      case 2 -> registers[1] = getComboOperandMod8();
      case 3 -> {
        if ( !registers[0].equals( ZERO ) ) {
          instrPointer = getLiteralOperand() - 2;
        }
      }
      case 4 -> registers[1] = xor( registers[2] );
      case 5 -> output.add( getComboOperandMod8().intValue() );
      case 6 -> registers[1] = division();
      case 7 -> registers[2] = division();
      default -> throw new IllegalStateException( "Unexpected opCode: " + opCode );
    }
    instrPointer += 2;
    return true;
  }

  private BigInteger xor(final BigInteger operand) {
    return registers[1].xor( operand );
  }

  private BigInteger division() {
    BigInteger num = registers[0];
    BigInteger den = BigInteger.TWO
        .pow( getComboOperandMod8().intValue() );
    return num.divide( den );
  }

  private int getInstruction() {
    return instructions[instrPointer];
  }

  private int getLiteralOperand() {
    return instructions[instrPointer + 1];
  }

  private BigInteger getComboOperandMod8() {
    int operand = getLiteralOperand();
    BigInteger comboOperand = switch ( operand ) {
      case 0, 1, 2, 3 -> BigInteger.valueOf( operand );
      case 4, 5, 6 -> registers[operand - 4];
      default -> throw new IllegalStateException( "Unexpected comboOp: " + operand );
    };
    return comboOperand.mod( EIGHT );
  }

  public List<Integer> getOutput() {
    return output;
  }
}
