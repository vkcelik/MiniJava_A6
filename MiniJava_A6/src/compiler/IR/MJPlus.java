package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJPlus extends MJBinaryOp {

	public void prettyPrint(PrettyPrinter prepri) {
		this.lhs.prettyPrint(prepri);
		prepri.print(" + ");
		this.rhs.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {

		// check lhs and rhs
		
		MJType leftType = this.lhs.typeCheck();
		MJType rightType = this.rhs.typeCheck();

		// the types must be the same
		
		if (!leftType.isSame(rightType)) {
			throw new TypeCheckerException("types in + op must be the same ("+leftType.getName()+","+rightType.getName()+","+this.getClass().getName()+")");
		}

		this.type = leftType;
		
		// the arguments must be either integer or String
		
		if (!this.type.isInt() && !(this.type.isClass() && this.type.getName()=="String")) {
			new TypeCheckerException("Arguments to + must have type int or String.");
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
		
		code.comment(" PLUS BEGIN");
		code.commentline(" lhs ");
		this.lhs.generateCode(code);
		code.commentline(" rhs ");
		this.rhs.generateCode(code);
		
		if (this.getType().isInt()) {
			code.commentline(" add integers ");
			code.pop2( CODE.TMP0, CODE.TMP1);
			code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, CODE.TMP1));
			code.push( CODE.TMP0 );
		} else {
			// we need a string object that can hold the sum of the two strings
			
			LC3label cont = code.newLabel();
			
			code.commentline(" add strings ");
			code.add(new LC3LD( CODE.TMP0, 1));
			code.add( new LC3BR(cont));
			code.add( new LC3labeldata(code.addstrings));
			code.add(cont);
			code.add( new LC3JSRR(CODE.TMP0));
		}
		code.comment(" PLUS END");
	}

}
