package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public final class MJNoExpression extends MJExpression {

	public void prettyPrint(PrettyPrinter prepri) {
	}
	
	public MJExpression rewriteTwo() {
		return this;
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// this one is easy
		this.type = MJType.getVoidType();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// nothing to do here
		return;
	}

	public int requiredStackSize() { 
		return 0;
	}

	public void generateCode(CODE code) throws CodeGenException {
	}

}
