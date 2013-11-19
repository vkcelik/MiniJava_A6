package compiler.CODE.LC3;

import java.util.HashMap;

import compiler.Exceptions.CodeGenException;

public class LC3label extends LC3 {
	
	private static int counter = 0;
	private static String prefix = "LAB_";
	private String label;
	
	private static HashMap<LC3label, Integer> target = new HashMap<LC3label, Integer>();
	
	public LC3label() {		
		label = prefix + counter;
		counter++;
		
		target.put(this, -1);

	}

	public String toString() {
		return this.label;
	}

	public static void resolveLabel(LC3label lab, int addr) throws CodeGenException {
		if (!target.containsKey(lab)) {
			throw new CodeGenException("Unknown label "+lab.label);
		}
		if (target.get(lab)!=-1)  {
			throw new CodeGenException("Label "+lab.label+" has already been resolved.");
		}
		target.put(lab, addr);
	}

}
