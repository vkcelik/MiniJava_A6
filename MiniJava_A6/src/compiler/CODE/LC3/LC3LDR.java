package compiler.CODE.LC3;

public class LC3LDR extends LC3Instruction {

	private int code = 7;
	
	private LC3regs dst;
	private LC3regs base;
	private int offset;
	
	public LC3LDR(LC3regs src, LC3regs base, int offset) {
		this.dst = src;
		this.base = base;
		this.offset = offset;
	}

	public String toString() {
		return "LDR "+this.dst+", "+this.base+", "+this.offset;
	}

}
