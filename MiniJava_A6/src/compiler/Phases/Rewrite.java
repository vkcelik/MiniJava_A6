package compiler.Phases;

import compiler.Exceptions.*;
import compiler.IR.*;

public class Rewrite {

	public static void rewriteOne(IR ir) {
		
		ir.getProgram().rewriteOne();
	}

	public static void rewriteTwo(IR ir) {
		
		ir.getProgram().rewriteTwo();
	}

}
