package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableNotFound;

public class MJIdentifier extends MJExpression {

	private String name;
	protected MJVariable decl;

	public MJIdentifier() {
	}

	public MJIdentifier(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public MJVariable getDecl() {
		return decl;
	}

	public void setDecl(MJVariable v) {
		this.decl = v;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print(this.name);
	}

	public MJIdentifier rewriteTwo() {
		
		if (this.decl.isField()) {
			MJIdentifier id = new MJIdentifier("this");
			MJSelector sel = new MJSelector(id, this);
			id.type = MJType.getClassType(IR.currentClass);
			id.decl = IR.currentMethod.getParameters().getFirst();
			sel.decl = sel.getField().decl;
			sel.type = sel.getField().getType();
			
			return sel;
		}

		return this;
	}

	MJType typeCheck() throws TypeCheckerException {

		// find the declaration for the identifier on the stack
		MJVariable var;
		String name = this.name;
		
		if (this.name == "this" && IR.currentMethod.isStatic()) {
			throw new TypeCheckerException("this encountered in static method.");
		}

		if (this.name == "super") {
			if (IR.currentMethod.isStatic()) {
				throw new TypeCheckerException("super encountered in static method.");
			}
			name = "this";
		}
		
		

		try {
			var = IR.find(name);
		} catch (VariableNotFound e) {
			throw new TypeCheckerException("Unkown identifier " + this.name);
		}

		if (this.name != "super") {
			// remember the declaration
		
			this.decl = var;

			// and use the type
			this.type = var.getType();
		} else {
			
			// super can only occur in selectors, and there the declaration of the object does not matter
			this.decl = null;
			this.type = var.getType().getDecl().getSuperClass();
		}
		
		return this.type;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		variableInit(initialized, false);
	}

	void variableInit(HashSet<MJVariable> initialized, boolean lvalue)
			throws TypeCheckerException {

		MJVariable var = this.decl;

		if (!lvalue) {
			// if we are on the right hand side
			// check whether the identifier is in the set
			if (!initialized.contains(var)) {
				throw new TypeCheckerException("Variable " + this.name
						+ " not initialized.");
			}
		} else {
			// if we are on the left hand side
			initialized.add(var);
		}
	}

	public int requiredStackSize() {
		return 1;
	}

	public void generateCode(CODE code, boolean lvalue) throws CodeGenException {

		code.comment(" IDENTIFIER " + this.name + " "
				+ (lvalue ? "lhs " : "rhs "));

		if (this.name.equals("super")) {
			this.decl = IR.currentMethod.getParameters().getFirst();
		}

		if (this.decl.isField()) {
			
			MJVariable thisdecl = IR.currentMethod.getParameters().getFirst();
			MJIdentifier thisid = new MJIdentifier("this");
			thisid.decl = thisdecl;
			thisid.type = thisdecl.getType();
			MJSelector sel = new MJSelector(thisid, this);
			sel.type = this.type;
			sel.decl = this.decl;
			sel.generateCode(code,lvalue);
		} else {
			
			// identifier denotes a variable or parameter
			
			if (lvalue) {
				code.commentline(" push address ");
				code.add(new LC3ADD(CODE.TMP0, CODE.SFP, 3 + this.decl
						.getOffset()));
				code.push(CODE.TMP0);
			} else {
				code.commentline(" push value ");
				code.add(new LC3LDR(CODE.TMP0, CODE.SFP, 3 + this.decl
						.getOffset()));
				code.push(CODE.TMP0);
			}
		}
		code.comment(" IDENTIFIER END ");

	}

	public void generateCode(CODE code) throws CodeGenException {
		generateCode(code, false);
	}

}
