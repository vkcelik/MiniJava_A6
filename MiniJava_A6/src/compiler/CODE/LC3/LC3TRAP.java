package compiler.CODE.LC3;


public class LC3TRAP extends LC3Instruction {

	private int code = 15;
	
	private int vector;
	
	public LC3TRAP(int i) {
		this.vector = i;
	}
	
	public String toString() {
		return "TRAP x"+Integer.toHexString(this.vector);
	}

}
