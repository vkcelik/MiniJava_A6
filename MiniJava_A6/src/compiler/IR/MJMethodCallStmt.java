package compiler.IR;

import java.util.HashSet;
import java.util.LinkedList;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.ClassErrorMethod;
import compiler.Exceptions.ClassNotFound;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.MethodNotFound;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableNotFound;

public class MJMethodCallStmt extends MJStatement {

	private MJIdentifier method;
	private LinkedList<MJExpression> arglist;

	private MJMethod target;
	
	public MJMethodCallStmt(MJIdentifier m, LinkedList<MJExpression> arglist) {
		this.method = m;
		this.arglist = arglist;
	}

	public MJIdentifier getMethod() {
		return method;
	}

	public LinkedList<MJExpression> getArglist() {
		return arglist;
	}

	public MJMethod getTarget() {
		return target;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		boolean first = true;

		this.method.prettyPrint(prepri);
		prepri.print("(");
		for (MJExpression arg : arglist) {

			if (!first) {
				prepri.print(", ");
			} else {
				first = false;
			}

			arg.prettyPrint(prepri);
		}
		prepri.println(");");
	}
	
	public void rewriteTwo() {
		
		MJSelector sel;
		
		if (!(this.method instanceof MJSelector)) {
			MJIdentifier id = new MJIdentifier("this");
			MJType t = MJType.getClassType(IR.currentClass);
			id.setType(t);
			id.setDecl(IR.currentMethod.getParameters().getFirst());

			sel = new MJSelector(id, this.method);
		} else {
			sel = (MJSelector) this.method;
		}
		
		int position = 0;
		for (MJExpression arg : arglist) {
			arg = arg.rewriteTwo();
			arglist.add(position, arg);
			arglist.remove(position+1);
		}
		
		// now we add a this argument to non-static method calls
		
		if (!this.target.isStatic()) {
			MJIdentifier thisid = sel.getObject();			
			this.arglist.addFirst(thisid);
		}
	}

	MJType typeCheck() throws TypeCheckerException {
		

		MJClass defclass = IR.currentClass;
		MJMethod method;
		String mname;
		
		MJIdentifier id = this.method;
		
		if (id instanceof MJSelector) {
			MJSelector sel = (MJSelector)id;
			MJType objType = sel.getObject().typeCheck();

			if (!objType.isClass()) {
				throw new TypeCheckerException("Baseobject in selector must have class type.");
			}

			defclass = objType.getDecl();
			
			id = sel.getField();
			mname = id.getName();
		} else {
			if (id.getName().equals("this")) {
				defclass = IR.currentClass;
				mname = defclass.getName();
			} else if (id.getName().equals("super")) {
				defclass = IR.currentClass.getSuperClass().getDecl();
				mname = defclass.getName();
			} else {
				mname = id.getName();
			}
		}
		
		for (MJExpression arg : this.arglist) {
			arg.typeCheck();
		}

		try {
			method = IR.classes.lookupMethod(defclass, mname, arglist);
		} catch (ClassErrorMethod e) {
			throw new TypeCheckerException("No matching class found.");
		} catch (MethodNotFound e) {
			throw new TypeCheckerException("No matching method found.");
		}

		this.target = method;
				
		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		MJIdentifier id = this.method;
		
		if (id instanceof MJSelector) {
			
			MJSelector sel = (MJSelector)id;

			sel.getObject().variableInit(initialized);

		}
		
		for (MJExpression arg : arglist) {
			arg.variableInit(initialized);
		}

	}

	public int requiredStackSize() {
		
		int maxsize = 0;
		
		maxsize += this.arglist.size();
		
		for (MJExpression arg : this.arglist) {
			maxsize += arg.requiredStackSize();
		}
		
		maxsize += 6;
		
		return maxsize;
	}

	public void generateCode(CODE code) throws CodeGenException {
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
		code.comment(" CALL END "+this.target.getName());
	}

}
