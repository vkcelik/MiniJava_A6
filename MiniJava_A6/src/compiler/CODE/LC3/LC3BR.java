package compiler.CODE.LC3;


public class LC3BR extends LC3Instruction {

	private int code = 0;
	
	protected enum flags { none, Z, N, P, ZN, ZP, NP, ZNP};
	
	private LC3label l;
	private flags f;
	
	public LC3BR(LC3label l) {
		this(l, flags.none);
	}

	protected LC3BR(LC3label l, flags f) {
		this.l = l;
		this.f = f;
	}
	
	public String toString() {
		String r = "BR";
		
		if (this.f == flags.N || this.f == flags.ZN || this.f == flags.NP || this.f == flags.ZNP) {
			r+="n";
		}
		if (this.f == flags.Z || this.f == flags.ZP || this.f == flags.ZN || this.f == flags.ZNP) {
			r+="z";
		}
		if (this.f == flags.P || this.f == flags.ZP || this.f == flags.NP || this.f == flags.ZNP) {
			r+="p";
		}

		r += " "+this.l;
		
		return r;
	}
	
}
