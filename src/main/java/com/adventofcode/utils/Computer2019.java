package com.adventofcode.utils;

import static com.adventofcode.utils.Computer2019.OpCode.HALT;
import static com.adventofcode.utils.Computer2019.OpCode.NOP;
import static com.adventofcode.utils.Utils.itoa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import com.google.common.base.Strings;

public class Computer2019 {
    public List<Long> memory;
    private int pointer = 0;
    private final boolean modes;
	private final BlockingQueue<Long> in;
	private final BlockingQueue<Long> out;
	private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

	public Computer2019() {
		this( false, null, null );
    }

	public Computer2019( final boolean modes, final BlockingQueue<Long> in,
			final BlockingQueue<Long> out ) {
        this.modes = modes;
		this.in = in;
		this.out = out;
    }

    private int getMode( final long value, final int param ) {
        int position = 10;
        for ( int i = 0; i < param; i++ ) {
            position *= 10;
        }
        return (int) ( ( value / position ) % 10 );
    }

    private int getAddress( final int index ) {
        return getValueDirect( index ).intValue();
    }

    private Long getValueIndirect( final int index ) {
        return getValueDirect( getAddress( index ) );
    }

    private Long getValueDirect( final int address ) {
        return memory.get( address );
    }

    private Long setValueIndirect( final int index, final Long val ) {
        return setValueDirect( getAddress( index ), val );
    }

    private Long setValueDirect( final int address, final Long val ) {
        return memory.set( address, val );
    }

    private Long getNextParameter( final int mode ) {
        return ( mode == 0 ) ? getValueIndirect( advancePointer() ) : getValueDirect(
                advancePointer() );
    }

	private String printParameter( final int mode, final int param ) {
		final Long address = getValueDirect( pointer + param );
		if ( mode == 0 ) {
			return "m[" + itoa( address ) + "]";
		} else {
			return itoa( address );
		}
    }

    public int advancePointer() {
        return ++pointer;
    }

    public void loadProgram( final List<Long> program ) {
        memory = new ArrayList<>( program );
    }

    public void setPointer( final int pointer ) {
        this.pointer = pointer;
    }

    private Optional<OpCode> getCurrentOpCode() {
        long opCode = currentInstruction();
        if ( modes ) {
            opCode %= 100;
        }
        return Optional.ofNullable( OpCode.valueOf( opCode ) );
    }

    private Long currentInstruction() {
        return getValueDirect( pointer );
    }

    public boolean executeOneStep() {
        final Optional<OpCode> opCode = getCurrentOpCode();
        opCode.orElse( NOP ).accept( this );
        advancePointer();
        return opCode.orElse( HALT ) != HALT;
    }

	public void run() {
		while ( true ) {
			if ( !executeOneStep() ) {
				return;
			}
		}
	}

	public Future<?> runAsync() {
		return EXECUTOR.submit( this::run );
	}

    public Optional<OpCode> printOneStep() {
        final Optional<OpCode> opCode = getCurrentOpCode();
        System.out.println( opCode.orElse( NOP ).toString( this ) );
        return opCode;
    }

    public void printProgram() {
		System.out.println( "=======BEGIN PROGRAM=======" );
        final int origPointer = pointer;

        setPointer( 0 );
        while ( pointer < memory.size() ) {
            final Optional<OpCode> opCode = printOneStep();
            pointer += opCode.orElse( NOP ).nParams + 1;
        }
		System.out.println( "========END PROGRAM========" );

        this.pointer = origPointer;
    }

    enum OpCode implements Consumer<Computer2019> {
        ADD( 1, 3 ) {
			@Override
            public void accept( final Computer2019 computer ) {
                final Long instruction = computer.currentInstruction();
                final Long first = computer.getNextParameter( computer.getMode( instruction, 1 ) );
                final Long second = computer.getNextParameter( computer.getMode( instruction, 2 ) );
                computer.setValueIndirect( computer.advancePointer(), first + second );
            }

            @Override
            public String toString( final Computer2019 computer ) {
                final Long instruction = computer.currentInstruction();
				final String first = computer.printParameter( computer.getMode( instruction, 1 ),
						1 );
				final String second = computer.printParameter( computer.getMode( instruction, 2 ),
						2 );
				final String third = computer.printParameter( 0, 3 );
				return padZero( computer.pointer ) + ": " + third + " = " + first + " + " + second;
			}

		},
        MULTIPLY( 2, 3 ) {
            @Override
            public void accept( final Computer2019 computer ) {
                final Long instruction = computer.currentInstruction();
                final Long first = computer.getNextParameter( computer.getMode( instruction, 1 ) );
                final Long second = computer.getNextParameter( computer.getMode( instruction, 2 ) );
                computer.setValueIndirect( computer.advancePointer(), first * second );
            }

            @Override
            public String toString( final Computer2019 computer ) {
                final Long instruction = computer.currentInstruction();
				final String first = computer.printParameter( computer.getMode( instruction, 1 ),
						1 );
				final String second = computer.printParameter( computer.getMode( instruction, 2 ),
						2 );
				final String third = computer.printParameter( 0, 3 );
				return padZero( computer.pointer ) + ": " + third + " = " + first + " * " + second;
            }
        },
        IN( 3, 1 ) {
            @Override
            public void accept( final Computer2019 computer ) {
				final Long first;
				try {
					first = computer.in.take();
					computer.setValueIndirect( computer.advancePointer(), first );
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				}
            }

            @Override
            public String toString( final Computer2019 computer ) {
				final String first = computer.printParameter( 0, 1 );
				return padZero( computer.pointer ) + ": " + first + " = user input";
            }
        },
        OUT( 4, 1 ) {
            @Override
            public void accept( final Computer2019 computer ) {
                final Long instruction = computer.currentInstruction();
                final Long first = computer.getNextParameter( computer.getMode( instruction, 1 ) );
				try {
					computer.out.put( first );
				} catch ( InterruptedException e ) {
					e.printStackTrace();
				}
			}

            @Override
            public String toString( final Computer2019 computer ) {
                final Long instruction = computer.currentInstruction();
				final String first = computer.printParameter( computer.getMode( instruction, 1 ),
						1 );
				return padZero( computer.pointer ) + ": print: " + first;
            }
        },
        JUMP_TRUE( 5, 2 ) {
            @Override
            public void accept( final Computer2019 computer ) {
                final Long instruction = computer.currentInstruction();
                final Long first = computer.getNextParameter( computer.getMode( instruction, 1 ) );
                final Long second = computer.getNextParameter( computer.getMode( instruction, 2 ) );
                if ( first != 0 ) {
                    computer.pointer = second.intValue() - 1; //after executing an instruction, computer always advances the pointer
                }
            }

            @Override
            public String toString( final Computer2019 computer ) {
				final Long instruction = computer.currentInstruction();
				final String first = computer.printParameter( computer.getMode( instruction, 1 ),
						1 );
				final String second = computer.printParameter( computer.getMode( instruction, 2 ),
						2 );
				return padZero(
						computer.pointer ) + ": if ( " + first + " != 0 ) jump to " + second;
            }
        },
        JUMP_FALSE( 6, 2 ) {
            @Override
            public void accept( final Computer2019 computer ) {
                final Long instruction = computer.currentInstruction();
                final Long first = computer.getNextParameter( computer.getMode( instruction, 1 ) );
                final Long second = computer.getNextParameter( computer.getMode( instruction, 2 ) );
                if ( first == 0 ) {
                    computer.pointer = second.intValue() - 1; //after executing an instruction, computer always advances the pointer
                }
            }

            @Override
            public String toString( final Computer2019 computer ) {
				final Long instruction = computer.currentInstruction();
				final String first = computer.printParameter( computer.getMode( instruction, 1 ),
						1 );
				final String second = computer.printParameter( computer.getMode( instruction, 2 ),
						2 );
				return padZero(
						computer.pointer ) + ": if ( " + first + " == 0 ) jump to " + second;
            }
        },
        LESS_THAN( 7, 3 ) {
            @Override
            public void accept( final Computer2019 computer ) {
                final Long instruction = computer.currentInstruction();
                final Long first = computer.getNextParameter( computer.getMode( instruction, 1 ) );
                final Long second = computer.getNextParameter( computer.getMode( instruction, 2 ) );
                final long val = first < second ? 1L : 0L;
                computer.setValueIndirect( computer.advancePointer(), val );
            }

            @Override
            public String toString( final Computer2019 computer ) {
				final Long instruction = computer.currentInstruction();
				final String first = computer.printParameter( computer.getMode( instruction, 1 ),
						1 );
				final String second = computer.printParameter( computer.getMode( instruction, 2 ),
						2 );
				final String third = computer.printParameter( 0, 3 );
				return padZero(
						computer.pointer ) + ": " + third + " = ( " + first + " < " + second + " ) ? 1 : 0";
            }
        },
        EQUALS( 8, 3 ) {
            @Override
            public void accept( final Computer2019 computer ) {
                final Long instruction = computer.currentInstruction();
                final Long first = computer.getNextParameter( computer.getMode( instruction, 1 ) );
                final Long second = computer.getNextParameter( computer.getMode( instruction, 2 ) );
                final long val = first.equals( second ) ? 1L : 0L;
                computer.setValueIndirect( computer.advancePointer(), val );
            }

            @Override
            public String toString( final Computer2019 computer ) {
				final Long instruction = computer.currentInstruction();
				final String first = computer.printParameter( computer.getMode( instruction, 1 ),
						1 );
				final String second = computer.printParameter( computer.getMode( instruction, 2 ),
						2 );
				final String third = computer.printParameter( 0, 3 );
				return padZero(
						computer.pointer ) + ": " + third + " = ( " + first + " == " + second + " ) ? 1 : 0";
            }
        },
        HALT( 99, 0 ) {
            @Override
            public void accept( final Computer2019 computer ) {
                System.err.println( "HALT" );
            }

            @Override
            public String toString( final Computer2019 computer ) {
				return padZero( computer.pointer ) + ": " + "HALT";
            }
        },
        NOP( -1, 0 ) {
            @Override
            public String toString( final Computer2019 computer ) {
				final String first = computer.printParameter( 1, 0 );
				return padZero( computer.pointer ) + ": " + first;
            }
        };

        private final long code;
        private final int nParams;

        OpCode( final long code, int nParams ) {
            this.code = code;
            this.nParams = nParams;
        }

        private static final Map<Long, OpCode> OP_CODES = new HashMap<>();

        static {
            for ( final OpCode op : OpCode.values() ) {
                OP_CODES.put( op.code, op );
            }
        }

        static OpCode valueOf( long code ) {
            return OP_CODES.get( code );
        }

        long getValue() {
            return code;
        }

        abstract String toString( final Computer2019 computer );

		public void accept( final Computer2019 computer ) {}

		private static String padZero( final long num ) {
			final int PAD = 4;
			return Strings.padStart( itoa( num ), PAD, '0' );
		}
    }
}
