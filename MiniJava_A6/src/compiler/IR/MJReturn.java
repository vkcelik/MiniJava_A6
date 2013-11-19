package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJReturn extends MJStatement {

	private MJExpression retExp;

	public MJReturn(MJExpression retExp) {
		this.retExp = retExp;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("return");

		if (!(this.retExp instanceof MJNoExpression)) {
			prepri.print(" ");
			this.retExp.prettyPrint(prepri);
		}

		prepri.println(";");
	}

	public void rewriteTwo() {
		this.retExp = retExp.rewriteTwo();
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// get the type of the returned expression
		
		MJType retType = this.retExp.typeCheck();
		
		// and compare it to the return type of the current method
		
		if (!MJType.isAssignable(retType, IR.currentMethod.getType())) {
			throw new TypeCheckerException("Return argument must have same type as current method.");
		}
		
		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// visit the returned expression
		
		this.retExp.variableInit(initialized);
	}

	public int requiredStackSize() { 
		return this.retExp.requiredStackSize();
	}

	public void generateCode(CODE code) throws CodeGenException {
		
		code.comment(" RETURN BEGIN");
		if ( !(this.retExp instanceof MJNoExpression) ) {
			code.commentline( " return value ");
			this.retExp.generateCode(code);
			code.pop(CODE.TMP0);
			code.commentline( " put in stack frame ");
			code.add( new LC3STR(CODE.TMP0, CODE.SFP, 0));
		}
		
		code.commentline(" get return PC from stack frame");
		code.add( new LC3LDR(LC3regs.R7, CODE.SFP, 2) );
		code.add( new LC3RET());
		code.comment(" RETURN END");
		
	}

}
