package compiler.CODE.LC3;


public class LC3NOT extends LC3Instruction {

	private int code = 9;
	
	private LC3regs DR;
	private LC3regs SR;
	
	public LC3NOT(LC3regs dst, LC3regs src) {
		this.DR = dst;
		this.SR = src;
	}
	
	public String toString() {
		return "NOT "+this.DR+", "+this.SR;
	}

}
