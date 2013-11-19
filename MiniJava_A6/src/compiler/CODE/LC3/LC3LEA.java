package compiler.CODE.LC3;

public class LC3LEA extends LC3Instruction {

	private int code = 14;
	
	private LC3regs DR;
	private LC3 offset;
	
	public LC3LEA(LC3regs dest, int offset) {
		this.DR = dest;
		this.offset = new LC3int(offset);
	}

	public LC3LEA(LC3regs dest, LC3label lab) {
		this.DR = dest;
		this.offset = lab;
	}

	public String toString() {
		return "LEA "+this.DR+", "+this.offset;
	}

}
