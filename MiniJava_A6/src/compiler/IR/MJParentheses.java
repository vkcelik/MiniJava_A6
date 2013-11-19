package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJParentheses extends MJExpression {

	private MJExpression exp;

	public MJParentheses(MJExpression e) {
		this.exp = e;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("( ");
		this.exp.prettyPrint(prepri);
		prepri.print(" )");
	}

	public MJExpression rewriteTwo() {
		this.exp = exp.rewriteTwo();
		return this;
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// typecheck the argument
		
		this.type = this.exp.typeCheck();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// check the argument
		this.exp.variableInit(initialized);
	}

	public int requiredStackSize() { 
		return this.exp.requiredStackSize();
	}

	public void generateCode(CODE code) throws CodeGenException {
		code.commentline(" PARENTHESES ");
		this.exp.generateCode(code);
	}

}
