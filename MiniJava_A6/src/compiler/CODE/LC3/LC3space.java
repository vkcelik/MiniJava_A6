package compiler.CODE.LC3;

public class LC3space extends LC3value {

	private int value;
	
	public LC3space(int value) {
		this.value = value;
	}

	public String toString() {
		return ".BLKW "+value;
	}
	
}
