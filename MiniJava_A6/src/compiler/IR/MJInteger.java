package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJInteger extends MJExpression {

	private int value;

	public MJInteger(String string) {
		this.value = Integer.parseInt(string);
	}

	public MJInteger(int slot) {
		this.value = slot;
	}

	public int getValue() {
		return value;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print(Integer.toString(this.value));
	}
	
	public MJExpression rewriteTwo() {
		return this;
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// this one is easy...
		this.type = MJType.getIntType();
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
		
		LC3regs r = null;
		code.comment(" INT CONST "+ this.value);
		if (this.value == 0) {
			r = CODE.CONST0;
		} else if (this.value == 1) {
			r = CODE.CONST1;
		} else {
			r = CODE.TMP0;
			code.add( new LC3LD(CODE.TMP0, 3));
		}
		code.push( r );
		
		if (this.value != 0 && this.value != 1) {
			LC3label cont = code.newLabel();
			code.add( new LC3BR(cont) );
			code.commentline(" value ");
			code.add( new LC3int(this.value) );
			code.add(cont);
			code.comment(" INT CONST END");
		}
	}

}
