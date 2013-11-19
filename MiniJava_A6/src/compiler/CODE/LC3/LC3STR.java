package compiler.CODE.LC3;

public class LC3STR extends LC3Instruction {

	private int code = 7;
	
	private LC3regs SR;
	private LC3regs BaseR;
	private int offset;
	
	public LC3STR(LC3regs src, LC3regs base, int offset) {
		this.SR = src;
		this.BaseR = base;
		this.offset = offset;
	}
	
	public String toString() {
		return "STR "+this.SR+", "+this.BaseR+", "+this.offset;
	}

}
