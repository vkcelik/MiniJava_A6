package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJMinus extends MJBinaryOp {

	public void prettyPrint(PrettyPrinter prepri) {
		this.lhs.prettyPrint(prepri);
		prepri.print(" - ");
		this.rhs.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// check lhs and rhs
		
		MJType leftType = this.lhs.typeCheck();
		MJType rightType = this.rhs.typeCheck();

		// the types must be the same
		
		if (!leftType.isSame(rightType)) {
			throw new TypeCheckerException("types in - op must be the same ("+leftType.getName()+","+rightType.getName()+","+this.getClass().getName()+")");
		}

		this.type = leftType;
		
		// arguments must be int
		
		if (!this.type.isInt()) {
			new TypeCheckerException("Arguments to - must have type int.");
		}
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {		
		
		// just check both sides
		
		this.lhs.variableInit(initialized);
		this.rhs.variableInit(initialized);
	}

	public void generateCode(CODE code) throws CodeGenException {
		code.comment(" MINUS BEGIN ");
		code.commentline(" ADD lhs ");
		this.lhs.generateCode(code);
		code.commentline(" ADD rhs ");
		this.rhs.generateCode(code);
		code.pop2(CODE.TMP1, CODE.TMP0);
		//LD R1
		//LD R2
		//NOT R2, R2
		//ADD R2, R2 , #1
		//ADD R3, R1, R2
		
		code.comment(" MINUS END ");
	}

}
