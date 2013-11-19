package compiler.IR;

import java.util.HashSet;
import java.util.LinkedList;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.ClassErrorMethod;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.MethodNotFound;
import compiler.Exceptions.TypeCheckerException;

public class MJNew extends MJExpression {

	private LinkedList<MJExpression> arglist;
	private MJMethod target;
	
	public MJNew(MJType type) {
		this.type = type;
		this.target = null;
	}
	
	public MJNew(MJType type, LinkedList<MJExpression> arglist) {
		this(type);
		this.arglist = arglist;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		boolean first = true;

		prepri.print("new " + this.type + "(");
		for (MJExpression arg : arglist) {

			if (!first) {
				prepri.print(", ");
			} else {
				first = false;
			}

			arg.prettyPrint(prepri);
		}
		prepri.print(")");

	}

	public MJExpression rewriteTwo() {
		
		int position = 0;
		for (MJExpression arg : arglist) {
			arg = arg.rewriteTwo();
			arglist.add(position, arg);
			arglist.remove(position+1);
		}

		return this;
	}
	
	MJType typeCheck() throws TypeCheckerException {
		
		// type check the type of new
		this.type.typeCheck();

		for (MJExpression arg : this.arglist ) {
			arg.typeCheck();
		}

		// find constructor to call
		try {
			MJMethod m = IR.classes.lookupConstructor(this.type.getDecl(), this.arglist);
			this.target = m;
		} catch (ClassErrorMethod e) {
			// this should not happen
		} catch (MethodNotFound e) {
			// no constructor, that's fine
		}
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		for (MJExpression args : this.arglist) {
			args.variableInit(initialized);
		}
	}

	public int requiredStackSize() { 
		
		return 1;
	}

	public void generateCode(CODE code) throws CodeGenException {
		
		LC3label l = code.newLabel();
		LC3label c = code.newLabel();
		LC3label newmethod = code.newLabel();

		code.comment(" NEW BEGIN ");
		code.add( new LC3BR(c));
		code.commentline(" address of allocation routine ");
		code.add(newmethod);
		code.add( new LC3labeldata(code.newroutine));
		code.commentline(" size of object to allocate ");
		code.add(l);
		code.add( new LC3int( this.getType().getDecl().getSize()));
		code.commentline(" load address and size and invoke new method ");
		code.add(c);
		code.add( new LC3LD(CODE.TMP0, l));
		code.push(CODE.TMP0);
		code.add( new LC3LD(CODE.TMP1, newmethod));
		code.add( new LC3JSRR(CODE.TMP1));
		
		code.commentline(" new HP is address, is on stack");
		
		if (this.target != null) {
			code.commentline(" call constructor");
			
			code.comment(" CALL "+this.target.getName());
			LC3label blab = code.newLabel();
			LC3label dlab = code.newLabel();
			LC3label mlab = code.newLabel();
			code.add(new LC3BR(blab));
			code.commentline(" slot for SP ");
			code.add(dlab);
			code.add(new LC3int(0));
			code.commentline(" address of method to call ");
			code.add(mlab);
			code.add(new LC3labeldata( this.target.getLabel()));
			code.add(blab);
			code.commentline(" save SP ");
			code.add(new LC3ST(CODE.SP, dlab));
			code.commentline(" save SFP ");
			code.add(new LC3STR(CODE.SFP, CODE.SP, 1));
			code.commentline(" increment SP to save arguments and make space for admin area ");
			code.add(new LC3ADD(CODE.SP, CODE.SP, 3));
			code.commentline(" push arguments ");
			
			code.push(CODE.HP);
			
			for (MJExpression arg : this.arglist) {
				code.commentline(" argument ");
				arg.generateCode(code);
			}
	
			code.commentline(" set new SFP (this is the old SP)");
			code.add(new LC3LD(CODE.SFP, dlab));
			code.commentline(" get method address and jump to it");
			code.add(new LC3LD(CODE.TMP0, mlab));
			code.add(new LC3JSRR( CODE.TMP0 ));
			code.commentline(" once returned reset SP (this is the SFP)");
			code.add( new LC3ADD(CODE.SP, CODE.SFP, 0));
			code.commentline(" restore the old SFP - this was stored at offset one from the SFP");
			code.add( new LC3LDR(CODE.SFP, CODE.SFP, 1));
			code.commentline(" the first cell on the stack contains the result, so increase SP by one");
			code.add(new LC3ADD(CODE.SP, CODE.SP, 1));
			code.comment(" CALL END "+this.target.getName());
		}
		
		code.comment(" NEW END ");
	}

}
