package compiler.IR;

import compiler.Exceptions.TypeCheckerException;

public abstract class MJUnaryOp extends MJExpression {

	protected MJExpression arg;

	public MJUnaryOp(MJExpression l) {
		this.arg = l;
	}

	public int requiredStackSize() { 
		return this.arg.requiredStackSize();
	}
	
	public MJExpression rewriteTwo() {
		this.arg = arg.rewriteTwo();
		return this;
	}


}
