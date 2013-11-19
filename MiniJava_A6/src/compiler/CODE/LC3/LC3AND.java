package compiler.CODE.LC3;


public class LC3AND extends LC3Instruction {

	private int code = 5;
	
	private LC3regs DR;
	private LC3regs SR1;
	private LC3value ARG2;
	
	public LC3AND(LC3regs dst, LC3regs src, int imm) {
		this.DR = dst;
		this.SR1 = src;
		this.ARG2 = new LC3int(imm);
	}
	
	public LC3AND(LC3regs dst, LC3regs src, LC3regs S2) {
		this.DR = dst;
		this.SR1 = src;
		this.ARG2 = S2;
	}
	
	public String toString() {
		return "AND "+this.DR+", "+this.SR1+", "+this.ARG2;
	}
	
}
