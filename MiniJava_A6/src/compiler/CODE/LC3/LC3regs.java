package compiler.CODE.LC3;

public class LC3regs extends LC3value {
	
	int number;

	public static LC3regs R0 = new LC3regs(0);
	public static LC3regs R1 = new LC3regs(1);
	public static LC3regs R2 = new LC3regs(2);
	public static LC3regs R3 = new LC3regs(3);
	public static LC3regs R4 = new LC3regs(4);
	public static LC3regs R5 = new LC3regs(5);
	public static LC3regs R6 = new LC3regs(6);
	public static LC3regs R7 = new LC3regs(7);

	public LC3regs(int i) {
		this.number = i;
	}
	
	public String toString() {
		return "R"+this.number;
	}
	
}
