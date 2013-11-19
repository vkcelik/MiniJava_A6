package compiler.IR;

import java.util.HashSet;
import java.util.LinkedList;

import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.LC3comment;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableAlreadyDeclared;

public class MJBlock extends MJStatement {

	private LinkedList<MJVariable> variables = new LinkedList<MJVariable>();
	private LinkedList<MJStatement> statements = new LinkedList<MJStatement>();

	public MJBlock(LinkedList<MJVariable> vdl, LinkedList<MJStatement> sdl) {
		this.variables = vdl;
		this.statements = sdl;
	}

	public LinkedList<MJVariable> getVariables() {
		return this.variables;
	}

	public LinkedList<MJStatement> getStatements() {
		return this.statements;
	}

	public void prettyPrint(PrettyPrinter prepri) {
		prepri.println("{");
		prepri.in();

		for (MJVariable v : this.variables) {
			v.prettyPrint(prepri);
			prepri.println(";");
		}

		if (this.variables.size() > 0)
			prepri.println("");

		for (MJStatement s : this.statements) {
			s.prettyPrint(prepri);
		}

		prepri.out();
		prepri.println("}");
	}

	public void rewriteTwo() {

		for (MJVariable v : this.variables) {
			v.rewriteTwo();
		}
		
		for (MJStatement s : this.statements) {
			s.rewriteTwo();
		}
	}

	MJType typeCheck() throws TypeCheckerException {

		// enter a new scope
		IR.stack.enterScope();

		// add all variables
		for (MJVariable v : this.variables) {
			try {
				v.typeCheck();
				IR.stack.add(v);
			} catch (VariableAlreadyDeclared e) {
				throw new TypeCheckerException("Variable "+v.getName()+" already declared.");
			}
		}

		// typecheck all statements
		for (MJStatement s : this.statements) {
			s.typeCheck();
		}

		// leave the scope
		IR.stack.leaveScope();

		return MJType.getVoidType();
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {

		// check all variables
		for (MJVariable v : this.variables) {
				v.variableInit(initialized);
		}

		// check all statements
		for (MJStatement s : this.statements) {
			s.variableInit(initialized);
		}

	}

	public int requiredStackSize() { 
		
		int maxsize = 0;
		
		for (MJStatement s : this.statements) {
			maxsize = Math.max(maxsize, s.requiredStackSize());
		}
		return maxsize;
	}

	public void generateCode(CODE code) throws CodeGenException {
		
		for (MJStatement s : this.statements) {
			s.generateCode(code);
		}
	}

}
