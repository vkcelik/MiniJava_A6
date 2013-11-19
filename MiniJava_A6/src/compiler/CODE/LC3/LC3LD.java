package compiler.CODE.LC3;

public class LC3LD extends LC3Instruction {

	private int code = 3;
	
	private LC3regs src;
	private LC3 offset;
	
	public LC3LD(LC3regs src, int offset) {
		this.src = src;
		this.offset = new LC3int(offset);
	}

	public LC3LD(LC3regs src, LC3label lab) {
		this.src = src;
		this.offset = lab;
	}

	public String toString() {
		return "LD "+this.src+", "+this.offset;
	}

}
