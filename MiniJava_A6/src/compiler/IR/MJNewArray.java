package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJNewArray extends MJNew {

	private MJExpression size;

	public MJNewArray(MJType type, MJExpression size) {
		super(type);
		this.size = size;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print("new " + this.type.getBaseType().getName() + "[");
		this.size.prettyPrint(prepri);
		prepri.print("]");
	}

	public MJExpression rewriteTwo() {
		
		this.size = size.rewriteTwo();
		return this;

	}
	
	MJType typeCheck() throws TypeCheckerException {
		
		// type check the type
		this.type.typeCheck();
		
		// and the size argument
		MJType sizeType = this.size.typeCheck();
		
		// which must have type int
		if (!sizeType.isInt()) {
			throw new TypeCheckerException("Arraysize must have type int");
		}
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// we only need to check the size argument
		this.size.variableInit(initialized);
	}
	
	public void generateCode(CODE code) throws CodeGenException {
		
		LC3label sizelab = code.newLabel();
		LC3label contlab = code.newLabel();
		LC3label errorlab = code.newLabel();
		LC3label newmethod = code.newLabel();
		LC3label multmethod = code.newLabel();
		LC3label nullify = code.newLabel();

		code.comment(" NEW ARRAY BEGIN ");
		code.add( new LC3BR(contlab));
		code.commentline(" address of allocation routine ");
		code.add(newmethod);
		code.add( new LC3labeldata(code.newroutine));
		code.add(multmethod);
		code.add( new LC3labeldata(code.multiply));
		code.add(nullify);
		code.add( new LC3labeldata(code.nullify));
		code.commentline(" size of element type to allocate ");
		code.add(sizelab);
		code.add( new LC3int( this.getType().getBaseType().getSize()));
		code.add(errorlab);
		code.add(contlab);
		code.commentline(" compute size of array ");
		this.size.generateCode(code);
		code.commentline(" look at top of stack without popping it ");
		code.add( new LC3LDR(CODE.TMP0, CODE.SP, -1));
		code.commentline(" size must not be negative ");
		code.add( new LC3BRN(errorlab) );
		code.commentline(" compute required space (size * sizeof elementtype)");
		code.push( CODE.TMP0 );
		code.commentline(" load size of elements");
		code.add( new LC3LD(CODE.TMP0, sizelab));
		code.push(CODE.TMP0);
		code.commentline(" multiply ");
		code.add( new LC3LD(CODE.TMP1, multmethod));
		code.add( new LC3JSRR(CODE.TMP1));
		code.commentline(" get size ");
		code.pop( CODE.TMP0 );
		code.commentline(" increment with 2 to store size of array and array");
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, 2));
		code.commentline(" size must not be negative ");
		code.add( new LC3BRN(errorlab) );
		code.push(CODE.HP);
		code.push(CODE.TMP0);
		code.commentline(" load address and size and invoke method ");
		code.add( new LC3LD(CODE.TMP1, newmethod));
		code.add( new LC3JSRR(CODE.TMP1));
		code.commentline(" top of stack is new address ");
		code.add( new LC3LD( CODE.TMP1, nullify));
		code.add( new LC3JSRR( CODE.TMP1 ));
		code.commentline(" get size of array and store as first element");
		code.pop( CODE.TMP0 );
		code.add( new LC3STR( CODE.TMP0, CODE.HP, 0));
		code.add( new LC3ADD( CODE.TMP0, CODE.HP, 2));
		code.add( new LC3STR( CODE.TMP0, CODE.HP, 1));
		code.push( CODE.HP );
		code.comment(" NEW ARRAY END ");
	}



}
