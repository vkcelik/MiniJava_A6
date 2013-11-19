package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.*;

public class MJSelector extends MJIdentifier {

	private MJIdentifier object;
	private MJIdentifier field;

	public MJSelector(MJIdentifier t, MJIdentifier field) {
		this.object = t;
		this.field = field;
	}

	public MJIdentifier getObject() {
		return object;
	}

	public MJIdentifier getField() {
		return field;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		this.object.prettyPrint(prepri);
		prepri.print(".");
		this.field.prettyPrint(prepri);
	}

	public MJIdentifier rewriteTwo() {
		return this;
	}
	
	MJType typeCheck() throws TypeCheckerException {

		// a selector has the form object.field

		// first type check the object
		// this sets also the object.decl 

		MJType idtype = this.object.typeCheck();

		// the object must have class type

		if (!idtype.isClass()) {
			throw new TypeCheckerException("Type of an object in a selector must be a class type.");
		}

		// now get the class declaration of object

		MJClass classDecl;
		try {
			classDecl = IR.classes.lookup(idtype.getName());
		} catch (ClassNotFound e) {
			throw new TypeCheckerException("No class declaration for object's type found.");
		}

		// now we can finally search for the field in the declaration

		MJVariable fieldDecl;
		try {
			fieldDecl = IR.classes.lookupField(classDecl, this.field.getName());
		} catch (ClassErrorField e) {
			throw new TypeCheckerException("Class "+ classDecl.getName() + 
					" has no field "+ this.field.getName() + ".");
		}

		// and from the field declaration we get the type...

		this.decl = fieldDecl;
		this.type = fieldDecl.getType();
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
	throws TypeCheckerException {
		variableInit(initialized, false);
	}

	void variableInit(HashSet<MJVariable> initialized, boolean lvalue)
	throws TypeCheckerException {

		// the object must be tested to be initialized
		this.object.variableInit(initialized);

		// but the field might be
		// this does not matter actually - why?
	}

	public int requiredStackSize() {
		return 2;
	}
	
	public void generateCode(CODE code) throws CodeGenException {
		generateCode(code, false);
	}
		
	public void generateCode(CODE code, boolean lvalue) throws CodeGenException {
		
		code.comment(" SELECTOR BEGIN " + (lvalue?"lhs":"rhs"));
		code.commentline(" get adress ");
		this.object.generateCode(code);

		// this should leave the value of the object on the stack
		
		// if this is null, we should throw a null pointer exception
		
		LC3label notnull = code.newLabel();
		
		code.add( new LC3LDR(CODE.TMP0, CODE.SP, -1));
		code.add( new LC3BRNP( notnull ));
		
		// throw NPE
		
		code.add( new LC3LD( CODE.TMP0, 1));
		code.add( new LC3JSRR( CODE.TMP0));
		code.add( new LC3labeldata(code.nullpointer));
		
		code.add( notnull);
		int offs = this.decl.getOffset();
				
		if (offs>1) {
			code.commentline(" compute offset ");
			code.pop(CODE.TMP0);
			
			LC3label Lcont = code.newLabel();
			LC3label Loffs = code.newLabel();
		
			code.add( new LC3BR(Lcont));
			code.add(Loffs);
			code.add( new LC3int( offs ));
			code.add(Lcont);
			code.add( new LC3LD(CODE.TMP1, Loffs));
			code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, CODE.TMP1));
		} else {
			if (offs==1) {
				code.pop(CODE.TMP0);
				code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, CODE.CONST1) );
			}
		}
		
		// TMP0 contains reference plus offset
		
		if (lvalue) {
			if (offs!=0) {
				code.commentline("push address");
				code.push(CODE.TMP0);
			}
		} else {
			if (offs==0) {
				code.pop(CODE.TMP0);
			}
			code.commentline(" get value from address and push");
			code.add( new LC3LDR(CODE.TMP0, CODE.TMP0, 0));
			code.push(CODE.TMP0);
		}
		
		code.comment(" SELECTOR END ");
	}
}
