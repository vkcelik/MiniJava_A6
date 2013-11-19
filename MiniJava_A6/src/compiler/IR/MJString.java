package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJString extends MJExpression {

	private String string;

	public MJString(String string) {
		this.string = string;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print(this.string);
	}

	public MJExpression rewriteTwo() {
		return this;
	}

	MJType typeCheck() throws TypeCheckerException {
		
		this.type = MJType.getClassType("String");
		this.type.typeCheck();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// nothing to be done here
		
		return;
	}

	public int requiredStackSize() { 
		return 1;
	}

	public void generateCode(CODE code) throws CodeGenException {
		
		// we need to allocate an object on the heap
		// plus allocate space for the string
		
		code.comment(" STRING CONST BEGIN ");
		LC3label o = code.newLabel();
		LC3label oa = code.newLabel();
		LC3label c = code.newLabel();
		LC3label cont = code.newLabel();
		
		String s = this.string;
		
		if (s.startsWith("\"")) {
			s = s.substring(1);
		}
		
		if (s.endsWith("\"")) {
			s = s.substring(0, s.length()-1);
		}
		
		code.add( new LC3BR(cont));
		code.add( c );
		code.add( new LC3string(s));
		code.add( o );
		code.add( new LC3labeldata(c) );
		code.add( new LC3int(this.string.length()));
		code.add( oa );
		code.add( new LC3labeldata(o));
		code.add(cont);
		code.add( new LC3LD(CODE.TMP0, oa));
		code.push(CODE.TMP0);
		code.comment(" STRING CONST END ");
	}

}
