package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.*;

public class MJBoolean extends MJExpression {

	private enum MJBooleanValues {
		True, False
	};

	private MJBooleanValues value;

	public static MJBoolean False = new MJBoolean(MJBooleanValues.False);
	public static MJBoolean True = new MJBoolean(MJBooleanValues.True);

	public MJBoolean(MJBooleanValues value) {
		this.value = value;
	}

	public boolean isTrue() {
		return (this.value == MJBooleanValues.True);
	}

	public boolean isFalse() {
		return (this.value == MJBooleanValues.False);
	}

	public void prettyPrint(PrettyPrinter prepri) {
		if (this.isFalse()) {
			prepri.print("false");
		} else {
			prepri.print("true");
		}
	}
	
	public MJExpression rewriteTwo() {
		return this;
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// this one is easy
		this.type = MJType.getBooleanType();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// nothing to do here
		return;
	}

	public int requiredStackSize() { 
		return 1;
	}

	public void generateCode(CODE code) throws CodeGenException {
		
		code.comment(" BOOLEAN CONST "+this.value);
		if (this.isTrue()) {
			code.push(CODE.CONST1);						
		} else {
			code.push(CODE.CONST0);			
		}
		
	}

}
