package compiler.CODE.LC3;


public class LC3JMP extends LC3Instruction {

	private int code = 12;
	
	private LC3regs BaseR;
	
	public LC3JMP(LC3regs baseR) {
		this.BaseR = baseR;
	}
	
	public String toString() {
		return "JMP "+this.BaseR;
	}

}
