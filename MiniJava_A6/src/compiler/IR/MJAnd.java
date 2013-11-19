package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.*;

public class MJAnd extends MJBinaryOp {

	public MJAnd(MJExpression a, MJExpression b) {
		super(a, b);
	}

	public void prettyPrint(PrettyPrinter prepri) {
		this.lhs.prettyPrint(prepri);
		prepri.print(" && ");
		this.rhs.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// check lhs and rhs
		
		MJType leftType = this.lhs.typeCheck();
		MJType rightType = this.rhs.typeCheck();

		// the types must be the same
		
		if (!leftType.isSame(rightType)) {
			throw new TypeCheckerException("types in && op must be the same ("+leftType.getName()+","+rightType.getName()+","+this.getClass().getName()+")");
		}

		this.type = leftType;		

		if (!this.type.isBoolean()) {
			throw new TypeCheckerException("Arguments to && must have type boolean.");
		}
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// check both arguments
		this.lhs.variableInit(initialized);
		this.rhs.variableInit(initialized);
	}

	public void generateCode(CODE code) throws CodeGenException {
		code.comment(" ADD BEGIN ");
		code.commentline(" ADD lhs ");
		this.lhs.generateCode(code);
		code.commentline(" ADD rhs ");
		this.rhs.generateCode(code);
		code.pop2(CODE.TMP0, CODE.TMP1);
		code.add(new LC3AND(CODE.TMP0, CODE.TMP0, CODE.TMP1));
		code.push( CODE.TMP0 );
		code.comment(" ADD END ");
	}

}
