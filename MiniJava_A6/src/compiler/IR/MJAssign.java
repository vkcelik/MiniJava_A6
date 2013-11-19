package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.ClassErrorField;
import compiler.Exceptions.ClassNotFound;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableNotFound;

public class MJAssign extends MJStatement {

	private MJExpression rhs;
	private MJIdentifier lhs;

	public MJAssign(MJIdentifier lhs, MJExpression rhs) {
		this.rhs = rhs;
		this.lhs = lhs;
	}

	public MJIdentifier getLhs() {
		return this.lhs;
	}

	public MJExpression getRhs() {
		return this.rhs;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		this.lhs.prettyPrint(prepri);
		prepri.print(" = ");
		this.rhs.prettyPrint(prepri);
		prepri.println(";");
	}
	
	MJType typeCheck() throws TypeCheckerException {
		
		// typecheck the rhs and lhs
		MJType rhsType = this.rhs.typeCheck();
		MJType lhsType = this.lhs.typeCheck();
		
		// check that rhs is assignable to lhs
		if (!MJType.isAssignable(rhsType, lhsType)) {
			throw new TypeCheckerException("Types in assignment are not assignable ("+lhsType+","+rhsType+")");
		}
		
		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// first check the rhs 
		this.rhs.variableInit(initialized);
		
		// only then check the lhs, which is assigned to
		// why do we need to first check rhs then lhs?
		
		this.lhs.variableInit(initialized, true);
	}

	public int requiredStackSize() { 
		return this.lhs.requiredStackSize() + this.rhs.requiredStackSize();
	}
	
	public void generateCode(CODE code) throws CodeGenException {
		
		code.comment(" ASSIGN ");
		code.commentline(" rhs ");
		this.rhs.generateCode(code);
		code.commentline( " lhs ");
		this.lhs.generateCode(code, true);
		code.commentline( " store ");
		code.pop2(CODE.TMP0, CODE.TMP1);
		code.add( new LC3STR(CODE.TMP0, CODE.TMP1, 0));
		code.comment(" ASSIGN END");
	}

	public void rewriteTwo() {
		this.lhs = this.lhs.rewriteTwo();
		this.rhs = this.rhs.rewriteTwo();
	}

}
