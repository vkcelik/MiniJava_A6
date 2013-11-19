package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJWhile extends MJStatement {

	private MJExpression condition;
	private MJBlock body;

	public MJWhile(MJExpression condition, MJBlock body) {
		this.condition = condition;
		this.body = body;
	}

	public MJBlock getBody() {
		return this.body;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("while (");
		this.condition.prettyPrint(prepri);
		prepri.println(")");
		this.body.prettyPrint(prepri);
	}
	
	public void rewriteTwo() {
		this.condition = condition.rewriteTwo();
		
		this.body.rewriteTwo();
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// condition must be boolean
		
		MJType condType = this.condition.typeCheck();
		
		if (!condType.isBoolean()) {
			throw new TypeCheckerException("type of condition must be boolean");
		}
		
		// and body must be typechecked
		
		this.body.typeCheck();
		
		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// check condition
		
		this.condition.variableInit(initialized);
		
		// check body of while with a new copy of initialized 
		
		HashSet<MJVariable> bodyset = (HashSet<MJVariable>) initialized.clone();
		this.body.variableInit(bodyset);
	}

	public int requiredStackSize() { 
		return Math.max(this.condition.requiredStackSize(), 1 + this.body.requiredStackSize());
	}

	public void generateCode(CODE code) throws CodeGenException {

		code.comment(" WHILE BEGIN ");
		code.comment(" WHILE END");
	}

}
