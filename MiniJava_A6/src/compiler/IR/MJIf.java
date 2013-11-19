package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJIf extends MJStatement {

	private MJExpression condition;
	private MJBlock thenblock;

	public MJIf(MJExpression condition, MJBlock thenblock) {
		this.condition = condition;
		this.thenblock = thenblock;
	}
	
	public MJExpression getCondition() {
		return condition;
	}

	public MJBlock getThenblock() {
		return thenblock;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("if (");
		this.condition.prettyPrint(prepri);
		prepri.println(")");
		this.thenblock.prettyPrint(prepri);
	}
	
	public void rewriteTwo() {
		this.condition = condition.rewriteTwo();
		this.thenblock.rewriteTwo();
	}

	MJType typeCheck() throws TypeCheckerException {

		// typecheck the condition
		MJType condType = this.condition.typeCheck();

		// which must have type boolean
		if (!condType.isBoolean()) {
			throw new TypeCheckerException("Type of condition must be boolean");
		}

		// then typecheck the then block
		this.thenblock.typeCheck();

		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// check the condition
		this.condition.variableInit(initialized);

		// check the then block with a new copy of the set
		HashSet<MJVariable> thenset = (HashSet<MJVariable>) initialized.clone();
		this.thenblock.variableInit(thenset);
	}

	public int requiredStackSize() {
		
		int maxstack = this.thenblock.requiredStackSize();
		
		return maxstack;
	}

	public void generateCode(CODE code) throws CodeGenException {
		
		code.comment(" IF ");
		code.comment(" IF END ");
	}
}
