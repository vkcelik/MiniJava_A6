package compiler.IR;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.Exceptions.CodeGenException;

public abstract class MJStatement extends IR {
	
	public MJStatement() {}
	
	public abstract void prettyPrint(PrettyPrinter prepri);

	abstract public int requiredStackSize();

	public abstract void generateCode(CODE code) throws CodeGenException;

	public abstract void rewriteTwo();

}
