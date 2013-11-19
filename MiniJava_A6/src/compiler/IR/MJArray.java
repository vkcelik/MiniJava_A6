	package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public class MJArray extends MJIdentifier {

	private MJIdentifier array;
	private MJExpression index;

	public MJArray(MJIdentifier array, MJExpression idx) {
		this.array = array;
		this.index = idx;
	}

	public MJIdentifier getArray() {
		return array;
	}

	public MJExpression getIndex() {
		return index;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		this.array.prettyPrint(prepri);
		prepri.print("[");
		this.index.prettyPrint(prepri);
		prepri.print("]");
	}
	
	public MJIdentifier rewriteTwo() {
		this.array = array.rewriteTwo();
		this.index = index.rewriteTwo();
		return this;
	}

	MJType typeCheck() throws TypeCheckerException {
		
		// typecheck the identifier
		MJType idtype = this.array.typeCheck();
		
		// which must have array type
		if (!idtype.isArray()) {
			throw new TypeCheckerException(this.array.getName()+" must have array type");
		}
		
		// typecheck the index
		MJType idxtype = this.index.typeCheck();
		
		// which must have type integer
		if (!idxtype.isInt()) {
			throw new TypeCheckerException(this.array.getName()+" must have type int");			
		}
		
		// type of the element is that of the base type
		this.type = idtype.getBaseType();
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
	throws TypeCheckerException {
		variableInit(initialized, false);
	}
	
	void variableInit(HashSet<MJVariable> initialized, boolean lvalue)
			throws TypeCheckerException {
		
		// the index only uses variables
		this.index.variableInit(initialized);
		
		// the array might be defined - but we do not care about this
		// we do not track individual elements of the array
		// BUT we need to check whether array is initialized or not
		this.array.variableInit(initialized);
	}
	
	public int requiredStackSize() { 
		return this.array.requiredStackSize() + this.index.requiredStackSize();
	}

	public void generateCode(CODE code, boolean lvalue) throws CodeGenException {
		
		LC3label sizelab = code.newLabel();
		LC3label contlab = code.newLabel();
		LC3label errorlab = code.newLabel();
		LC3label newmethod = code.newLabel();
		LC3label multmethod = code.newLabel();

		code.comment(" ARRAY BEGIN ");
		
		code.add( new LC3BR(contlab));
		if (this.getType().getSize()>1) {
			code.add(multmethod);
			code.add( new LC3labeldata(code.multiply));
			code.commentline(" size of element type ");
			code.add( sizelab );
			code.add( new LC3int(this.getType().getSize()));
		}
		code.add(errorlab);
		code.add( new LC3LD( CODE.TMP0, 1));
		code.add( new LC3JSRR( CODE.TMP0));
		code.add( new LC3labeldata(code.indexoutofbounds));
		code.add(contlab);
		code.commentline(" generate code for identifier ");
		this.array.generateCode(code);  							// stack: array
		code.commentline(" check for NPE");
		LC3label notnull = code.newLabel();
		
		code.add( new LC3LDR(CODE.TMP0, CODE.SP, -1));
		code.add( new LC3BRNP( notnull ));
		
		// throw NPE
		
		code.add( new LC3LD( CODE.TMP0, 1));
		code.add( new LC3JSRR( CODE.TMP0));
		code.add( new LC3labeldata(code.nullpointer));
		
		code.add( notnull);

		code.commentline(" look at top of stack without popping it ");
		code.commentline(" generate code for index ");
		this.index.generateCode(code);								// stack: array index
		code.comment(" check index against array boundaries (0 and size, stored at first slot)");
		code.commentline(" look at top of stack without popping it ");
		code.add( new LC3LDR(CODE.TMP1, CODE.SP, -2));
		code.add( new LC3LDR(CODE.TMP0, CODE.SP, -1));
		code.add( new LC3LDR(CODE.TMP1, CODE.TMP1, 0));
		code.add( new LC3NOT(CODE.TMP1, CODE.TMP1));
		code.add( new LC3ADD(CODE.TMP1, CODE.TMP1, 1));
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, CODE.TMP1));
		code.commentline(" must be negative (index - size)");
		code.add( new LC3BRZP(errorlab));
		code.commentline(" look at top of stack without popping it ");
		code.add( new LC3LDR(CODE.TMP0, CODE.SP, -1));
		code.commentline(" size must not be negative ");
		code.add( new LC3BRN(errorlab) );
		if (this.getType().getSize()>1) {
			code.commentline(" load size of elements");
			code.add( new LC3LD(CODE.TMP0, sizelab));
			code.push(CODE.TMP0);
			code.commentline(" multiply ");
			code.add( new LC3LD(CODE.TMP1, multmethod));
			code.add( new LC3JSRR(CODE.TMP1));
		}
		code.commentline(" get offset ");
		code.pop(CODE.TMP0);
		code.commentline(" size must not be negative ");
		code.add( new LC3BRN(errorlab) );
		code.commentline(" get address ");
		code.pop(CODE.TMP1);
		code.add( new LC3LDR( CODE.TMP1, CODE.TMP1, 1));
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, CODE.TMP0));
		
		if (lvalue) {
			code.push( CODE.TMP1 );
		} else {
			code.add( new LC3LDR(CODE.TMP0, CODE.TMP1, 0) );
			code.push(CODE.TMP0);
		}
		code.comment(" ARRAY END ");
	}

	public void generateCode(CODE code) throws CodeGenException {
		generateCode(code, false);
	}

}
