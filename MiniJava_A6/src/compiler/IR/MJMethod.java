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
import compiler.Exceptions.VariableAlreadyDeclared;

public class MJMethod extends IR {

	private MJType type;
	private String name;
	private LinkedList<MJVariable> parameters;
	private MJBlock body;
	private boolean isStatic;
	private boolean isPublic;
	private boolean isEntry;

	// this represents the offset into the virtual method table

	private int offset = -1;
	private LC3label label;
	private int varCount = 0;
	private int MSS;

	public MJMethod(MJType type, String name, LinkedList<MJVariable> parList,
			MJBlock b, boolean isStatic, boolean isPublic, boolean isEntry) {
		this.type = type;
		this.name = name;
		this.parameters = parList;
		this.body = b;
		this.isStatic = isStatic;
		this.isPublic = isPublic;
		this.isEntry = isEntry;
	}

	public MJMethod(MJType type, String name, LinkedList<MJVariable> parList,
			MJBlock b, boolean isStatic, boolean isPublic) {
		this(type, name, parList, b, isStatic, isPublic, false);
	}
	public String getName() {
		return name;
	}

	public LinkedList<MJVariable> getParameters() {
		return parameters;
	}

	public MJType getType() {
		return type;
	}

	public MJBlock getBody() {
		return body;
	}

	public void setBody(MJBlock body) {
		this.body = body;
	}

	public void setLabel(LC3label newLabel) {
		this.label = newLabel;
	}

	public LC3label getLabel() {
		return this.label;
	}

	public void setVarCount(int cnt) {
		this.varCount = cnt;
	}

	public int getVarCount() {
		return this.varCount;
	}

	public void setMaxStackSize(int maxStackSize) {
		this.MSS = maxStackSize;
	}

	public int getMaxStackSize() {
		return this.MSS;
	}

	public boolean isStatic() {
		return this.isStatic;
	}

	public boolean isPublic() {
		return this.isPublic;
	}

	public boolean isConstructor() {
		return this.type.isConstructor();
	}

	public void prettyPrint(PrettyPrinter prepri) {
		if (this.isPublic()) {
			prepri.print("public ");
		}
		if (this.isStatic()) {
			prepri.print("static ");
		}
		prepri.print(this.name + "(");
		boolean first = true;
		for (MJVariable v : this.parameters) {
			if (!first) {
				prepri.print(", ");
			} else {
				first = false;
			}
			v.prettyPrint(prepri);
		}
		prepri.println(")");
		body.prettyPrint(prepri);
		prepri.println("");
	}

	public void rewriteOne() {

		// add 'this' to virtual methods
		
		if (!this.isStatic()) {
			
			MJType t = MJType.getClassType( IR.currentClass.getName() );
			MJVariable v = new MJVariable(t,"this");
			
			this.parameters.addFirst(v);
		}
		
	}

	public void rewriteTwo() {
		
		// visit the block and rewrite all identifiers that denote a field to selectors
		
		this.body.rewriteTwo();
	}
	
	MJType typeCheck() throws TypeCheckerException {

		// remember which method we are in
		IR.currentMethod = this;

		// typecheck the return type
		this.type.typeCheck();

		// we need a new scope for the parameters
		IR.stack.enterScope();

		for (MJVariable par : this.parameters) {

			// each parameter is type checked
			par.typeCheck();

			// and added to the scope
			try {
				IR.stack.add(par);
			} catch (VariableAlreadyDeclared e) {
				throw new TypeCheckerException("Method "+this.name+" has duplicate parameter "+par.getName());
			}
		}

		// now we typecheck the body
		this.body.typeCheck();

		// and leave the scope
		IR.stack.leaveScope();

		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
	throws TypeCheckerException {

		// note that method arguments are initialized!!!
		for (MJVariable p : this.parameters) {
			initialized.add(p);
		}

		// check the method body
		this.body.variableInit(initialized);

	}

	public void generateCode(CODE code) throws CodeGenException {

		code.comment(" METHOD "+this.name);
		// save return address (r7) in word 3 of stack
		// STR R7 R6 3
		code.commentline(" save SFP R7 and initialize SP R5");
		code.add( new LC3STR(LC3regs.R7, CODE.SFP, 2) );
		if (this.isEntry) {
			code.add(new LC3LEA(CODE.TMP0, code.arguments));
			code.add( new LC3STR(CODE.TMP0, CODE.SFP, 3));
		}
		code.add( new LC3ADD(CODE.SP, CODE.SFP, 3+this.getVarCount()));
		code.commentline(" body ");		
		this.body.generateCode(code);
		code.comment(" METHOD END "+this.name);
	}

}
