package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJNegate extends MJUnaryOp {

	public MJNegate(MJExpression l) {
		super(l);
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("!");
		this.arg.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// type check the argument
		
		this.type = this.arg.typeCheck();
		
		// which must have type boolean
		
		if (!this.type.isBoolean()) {
			new TypeCheckerException("Argument of ! must have type boolean");
		}
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// just check the argument
		
		this.arg.variableInit(initialized);
	}

	public void generateCode(CODE code) throws CodeGenException {
		code.comment(" NEGATE BEGIN ");
		code.comment(" NEGATE END ");
	}

}
