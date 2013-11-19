package compiler.CODE.LC3;

public class LC3labeldata extends LC3value {

	private String value;
	
	public LC3labeldata(LC3label lc3label) {
		this.value = lc3label.toString();
	}

	public String toString() {
		return this.value;
	}
	
}
