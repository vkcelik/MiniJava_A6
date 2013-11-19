package compiler.IR;

import java.util.HashSet;

import compiler.PrettyPrinter;
import compiler.Exceptions.ClassErrorField;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableAlreadyDeclared;
import compiler.Exceptions.VariableNotFound;
import compiler.IR.support.MJClassTable;
import compiler.IR.support.MJScopeStack;

public class IR {

	private MJProgram program;

	public static MJClassTable classes = new MJClassTable();
	public static MJScopeStack stack = new MJScopeStack();
	public static MJMethod currentMethod = null;
	public static MJClass currentClass = null;
	
	public IR() {
	}

	public IR(MJProgram program) {
		this.program = program;
	}

	public MJProgram getProgram() {
		return program;
	}

	public void prettyPrint() {
		this.program.prettyPrint(new PrettyPrinter());
	}

	public static MJVariable find(String name) throws VariableNotFound {

		MJVariable decl = null;
		try {
			decl = IR.stack.find(name);
		} catch (VariableNotFound e) {
			try {
				decl = IR.classes.lookupField(currentClass, name);
			} catch (ClassErrorField e2) {
				throw new VariableNotFound(name);
			}
		}

		return decl;
	}

	MJType typeCheck() throws TypeCheckerException {
		throw new TypeCheckerException("not implemented");
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		throw new TypeCheckerException("not implemented");
	}

}
