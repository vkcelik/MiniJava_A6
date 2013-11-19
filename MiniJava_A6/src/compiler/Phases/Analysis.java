package compiler.Phases;

import compiler.Exceptions.*;
import compiler.IR.*;

public class Analysis {

	public static void analyse(IR ir) throws TypeCheckerException {
		
		ir.getProgram().typeCheck();
		ir.getProgram().variableInit();
	}
}
