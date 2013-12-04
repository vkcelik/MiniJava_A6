package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJMult extends MJBinaryOp {

	public MJMult(MJExpression a, MJExpression b) {
		super(a, b);
	}

	public void prettyPrint(PrettyPrinter prepri) {
		this.lhs.prettyPrint(prepri);
		prepri.print(" * ");
		this.rhs.prettyPrint(prepri);
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// check lhs and rhs
		
		MJType leftType = this.lhs.typeCheck();
		MJType rightType = this.rhs.typeCheck();

		// the types must be the same
		
		if (!leftType.isSame(rightType)) {
			throw new TypeCheckerException("types in * op must be the same ("+leftType.getName()+","+rightType.getName()+","+this.getClass().getName()+")");
		}

		this.type = leftType;
		
		// the arguments must be integers 
		if (!this.type.isInt()) {
			throw new TypeCheckerException("Arguments to * must have type int.");
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

		LC3label plusLoop = code.newLabel();
		code.comment(" MULT BEGIN ");
		code.commentline(" ADD lhs ");
		this.lhs.generateCode(code);
		code.commentline(" ADD rhs ");
		this.rhs.generateCode(code);
		code.pop2(CODE.TMP0, CODE.TMP1);
		code.commentline(" sum = 0 ");
		code.add(new LC3AND(CODE.RET, CODE.RET, 0));
		code.commentline(" add label for loop ");
		code.add(plusLoop);
		code.commentline(" sum = sum + multiplikator ");
		code.add(new LC3ADD(CODE.RET, CODE.RET, CODE.TMP0));
		code.commentline(" multiplikand = multiplikand + (-1) ");
		code.add(new LC3ADD(CODE.TMP1, CODE.TMP1, -1));
		code.commentline(" jump to loop while multiplikand > 0 ");
		code.add(new LC3BRP(plusLoop));
		code.commentline(" push result on stack ");
		code.push(CODE.RET);
		code.comment(" MULT END ");
	}
}
