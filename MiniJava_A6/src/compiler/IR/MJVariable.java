package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableAlreadyDeclared;

public class MJVariable extends IR {

	private MJType type;
	private String name;
	private MJExpression init;
	private boolean field;
	
	// this stores information for the code generator
	// for fields this means offset into the object's memory
	// for variables and parameters this means offset into the call frame
	
	private int offset = -1;

	public MJVariable(MJType t, String name) {
		this(t, name, new MJNoExpression());
	}

	public MJVariable(MJType t, String name, MJExpression init) {
		this.type = t;
		this.name = name;
		this.init = init;
	}

	public MJType getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	public boolean isField() {
		return this.field;
	}

	public void setField() {
		this.field = true;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getOffset() {
		return this.offset;
	}

	public MJExpression getInit() {
		return this.init;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.print(this.type + " " + this.name);

		if (!(this.init instanceof MJNoExpression)) {
			prepri.print(" = ");
			this.init.prettyPrint(prepri);
		}
	}

	public void rewriteTwo() {
		this.init = init.rewriteTwo();
	}

	public MJType typeCheck() throws TypeCheckerException {
		
		// we only need to typecheck the type of the variable
		// adding to the scope stack ensures that the name is unique
		
		this.getType().typeCheck();
		
		if (!(this.init instanceof MJNoExpression)) {
			MJType initType = this.init.typeCheck();
			
			if (!MJType.isAssignable(initType, this.getType())) {
				throw new TypeCheckerException("Initialized with incorrect type!");
			}
		}
		
		return MJType.getVoidType();
	}

	public void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		// if the variable has an initializer we need to add it to initialized
		
		if (!(this.init instanceof MJNoExpression)) {
			initialized.add(this);
		}

	}

}
