package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJComment extends MJStatement {

	private String comment;

	public MJComment(String c) {
		this.comment = c;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.println(this.comment);
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// nothing to do here
		return MJType.getVoidType();
	}
	
	public void rewriteTwo() {
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
