package compiler.CODE.LC3;

public class LC3comment extends LC3value {

	private String value;
	
	public LC3comment(String value) {
		this.value = value;
	}

	public String toString() {
		return "; "+value;
	}
	
}
