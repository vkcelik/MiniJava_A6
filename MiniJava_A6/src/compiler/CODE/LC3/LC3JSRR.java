package compiler.CODE.LC3;


public class LC3JSRR extends LC3JSR {

	private LC3regs reg;
	
	public LC3JSRR(LC3regs o) {
		this.reg = o;
	}
	
	public String toString() {
		return "JSRR "+this.reg;
	}

}
