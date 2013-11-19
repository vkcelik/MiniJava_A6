package compiler.CODE.LC3;

public class LC3int extends LC3value {

	private int value;
	
	public LC3int(int value) {
		this.value = value;
	}

	public String toString() {
		return Integer.toString(this.value);
	}
	
}
