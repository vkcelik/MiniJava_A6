package compiler.IR;

public abstract class MJBinaryOp extends MJExpression {

	protected MJExpression lhs;
	protected MJExpression rhs;

	public void setLeftOperand(MJExpression op) {
		this.lhs = op;
	}

	public void setRightOperand(MJExpression op) {
		this.rhs = op;
	}

	public MJBinaryOp() {
	};

	public MJBinaryOp(MJExpression a, MJExpression b) {
		this.lhs = a;
		this.rhs = b;
	}

	public int requiredStackSize() { 
		return Math.max(this.lhs.requiredStackSize(), this.lhs.requiredStackSize()) + 1;
	}
	
	public MJExpression rewriteTwo() {
		this.lhs = lhs.rewriteTwo();
		this.rhs = rhs.rewriteTwo();
		return this;
	}
	
}
