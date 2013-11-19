package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJPrintln extends MJStatement {

	private MJExpression parameter;

	public MJPrintln(MJExpression parameter) {
		this.parameter = parameter;
	}

	public MJExpression getParameter() {
		return parameter;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("System.out.println(");
		this.parameter.prettyPrint(prepri);
		prepri.println(");");
	}

	public void rewriteTwo() {
		this.parameter = parameter.rewriteTwo();
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// typecheck the parameter - and done.
		this.parameter.typeCheck();
		
		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		// check that all variables in the parameter are initialized 
		this.parameter.variableInit(initialized);
	}

	public int requiredStackSize() { 
		return 1 + this.parameter.requiredStackSize();
	}

	public void generateCode(CODE code) throws CodeGenException {
		
		LC3label newline = code.newLabel();
		LC3label cont = code.newLabel();
		
		MJType t = this.parameter.getType();

		this.parameter.generateCode(code);

		if (t.isClass()) { 
			if (t.getName().equals("String")) {		
				LC3label nullstring = code.newLabel();
				LC3label nullcont = code.newLabel();
				code.pop(CODE.TMP0);
				code.add( new LC3BRZ(nullcont));
				code.add( new LC3LDR(CODE.TMP0, CODE.TMP0, 0));
				code.add( new LC3BR(cont));
				code.add( nullcont);
				code.add( new LC3LEA(CODE.TMP0, nullstring));
				code.add( new LC3BR(cont));
				code.add( nullstring);
				code.add( new LC3string("null"));
			} else {
				// here should go an error message
			}
		} else if (t.isInt()){
			// type must be int, must be translated to string first
			LC3label intcont = code.newLabel();
			LC3label inttostringaddr = code.newLabel();
			
			code.add(new LC3BR(intcont));
			code.add(inttostringaddr);
			code.addData( new LC3labeldata(code.inttostring));
			code.add(intcont);
			code.add( new LC3LD(CODE.TMP1, inttostringaddr));
			code.add( new LC3JSRR(CODE.TMP1));
			code.pop(CODE.TMP0);
		} else if (t.isBoolean()) {
			LC3label flab = code.newLabel();
			LC3label tstr = code.newLabel();
			LC3label fstr = code.newLabel();
			code.pop(CODE.TMP0);
			code.add( new LC3BRZ(flab));
			code.add( new LC3LEA( CODE.TMP0, tstr ));
			code.add( new LC3BR(cont));
			code.add( flab );
			code.add( new LC3LEA( CODE.TMP0, fstr ));
			code.add( new LC3BR(cont));
			code.add( tstr );
			code.add( new LC3string("true"));
			code.add( fstr );
			code.add( new LC3string("false"));
		}

		code.add( new LC3BR(cont));
		code.add(newline);
		code.add( new LC3labeldata(code.newlineroutine));
		code.add(cont);
		code.add( new LC3TRAP(0x22));
		code.add( new LC3LD(CODE.TMP1, newline));
		code.add( new LC3JSRR(CODE.TMP1));
	}
}
