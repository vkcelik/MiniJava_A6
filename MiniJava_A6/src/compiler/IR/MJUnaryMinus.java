package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.LC3ADD;
import compiler.CODE.LC3.LC3NOT;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJUnaryMinus extends MJUnaryOp {

	public MJUnaryMinus(MJExpression l) {
		super(l);
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("-");
		this.arg.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {
		
		
		// typecheck the argument
		this.type = this.arg.typeCheck();
		
		// it must have type int
		
		if (!this.type.isInt()) {
			throw new TypeCheckerException("Argument of unary - must be int.");
		}
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// visit the argument
		this.arg.variableInit(initialized);
	}

	public void generateCode(CODE code) throws CodeGenException {
		
		code.comment(" UNARY MINUS BEGIN ");
		code.commentline(" argument ");
		this.arg.generateCode(code);
		code.commentline(" negate ");
		code.pop(CODE.TMP0);
		code.add( new LC3NOT(CODE.TMP0, CODE.TMP0) );
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, 1) );
		code.push(CODE.TMP0);
		code.comment(" UNARY MINUS END ");
	}

}
