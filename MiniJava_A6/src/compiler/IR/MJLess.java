package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJLess extends MJBinaryOp {

	public MJLess(MJExpression a, MJExpression b) {
		super(a, b);
	}

	public void prettyPrint(PrettyPrinter prepri) {
		this.lhs.prettyPrint(prepri);
		prepri.print(" < ");
		this.rhs.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// check lhs and rhs
		
		MJType leftType = this.lhs.typeCheck();
		MJType rightType = this.rhs.typeCheck();

		// the types must be the same
		
		if (!leftType.isSame(rightType)) {
			throw new TypeCheckerException("types in < op must be the same ("+leftType.getName()+","+rightType.getName()+","+this.getClass().getName()+")");
		}

		// arguments must be int
		
		if (!this.lhs.type.isInt()) {
			throw new TypeCheckerException("Arguments to < must have type int.");
		}
		
		// and the result type is boolean
		this.type = MJType.getBooleanType();
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// simply visit both arguments
		this.lhs.variableInit(initialized);
		this.rhs.variableInit(initialized);
	}

	public void generateCode(CODE code) throws CodeGenException {
		
		code.comment(" LESS ");

		code.commentline(" lhs ");
		this.lhs.generateCode(code);
		code.commentline(" rhs ");
		this.rhs.generateCode(code);
		
		code.commentline("  compute lhs - rhs  ");
		code.pop2(CODE.TMP0, CODE.TMP1);
		code.add( new LC3NOT(CODE.TMP1, CODE.TMP1));
		code.add( new LC3ADD(CODE.TMP1, CODE.TMP1, 1));
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, CODE.TMP1));
		
		// if they are less, N flag is set
		code.commentline(" if lhs < rhs, N flag is set");
		LC3label ltrue = code.newLabel();
		LC3label lend = code.newLabel();
		code.add( new LC3BRN(ltrue) );
		code.commentline(" if not negative, push 0 ");
		code.push(CODE.CONST0);
		code.add( new LC3BR(lend));
		code.add(ltrue);
		code.commentline(" if negative, push 1 ");
		code.push(CODE.CONST1);
		code.add(lend);
		code.comment(" LESS END ");
	}

}
