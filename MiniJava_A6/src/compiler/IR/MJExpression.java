package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public abstract class MJExpression extends IR {

	protected MJType type;

	public MJType getType() {
		return type;
	}

	public void setType(MJType type) {
		this.type = type;
	}

	public abstract void prettyPrint(PrettyPrinter prepri);

	public abstract int requiredStackSize();

	public abstract void generateCode(CODE code) throws CodeGenException;

	public abstract MJExpression rewriteTwo();
	
}
