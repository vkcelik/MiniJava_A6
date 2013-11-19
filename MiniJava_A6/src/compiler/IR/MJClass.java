package compiler.IR;

import java.util.HashSet;
import java.util.LinkedList;

import compiler.PrettyPrinter;
import compiler.Exceptions.TypeCheckerException;
import compiler.Exceptions.VariableAlreadyDeclared;
import compiler.Exceptions.VariableNotFound;

public class MJClass extends IR {

	private String name;
	private MJType superClass;
	private LinkedList<MJMethod> methodList = new LinkedList<MJMethod>();
	private LinkedList<MJVariable> fieldList = new LinkedList<MJVariable>();
	private int size;
	private int fieldOffsets = -1;

	// this we have just for the main class

	public MJClass(String name, MJMethod md) {
		this.name = name;
		LinkedList<MJMethod> methodList = new LinkedList<MJMethod>();
		methodList.addFirst(md);
		this.methodList = methodList;
		this.superClass = MJType.getClassType("Object");
	}

	// and this is for the general case

	public MJClass(String name, String superClassName,
			LinkedList<MJVariable> vdl, LinkedList<MJMethod> mdl) {
		this.name = name;
		this.fieldList = vdl;
		this.methodList = mdl;
		this.superClass = MJType.getClassType(superClassName);
	}

	public LinkedList<MJMethod> getMethodList() {
		return methodList;
	}

	public String getName() {
		return name;
	}

	public LinkedList<MJVariable> getFieldList() {
		return fieldList;
	}

	public void setSuperClass(MJType superClass) {
		this.superClass = superClass;
	}

	public MJType getSuperClass() {
		return superClass;
	}

	public void setSize(int size) {
		this.size = size;		
	}

	public int getSize() {
		return this.size;		
	}
	
	public int getFieldOffsets() {
		return this.fieldOffsets;
	}
	
	public void setFieldOffsets(int i) {
		this.fieldOffsets = i;
	}
	
	public void prettyPrint(PrettyPrinter prepri) {
		prepri.println("class " + this.getName() + " extends "
				+ this.getSuperClass() + " {");
		prepri.in();

		for (MJVariable v : this.fieldList) {
			v.prettyPrint(prepri);
			prepri.println(";");
		}

		prepri.println("");

		for (MJMethod m : this.methodList) {
			m.prettyPrint(prepri);
		}

		prepri.out();
		prepri.println("}");
	}

	public MJType typeCheck() throws TypeCheckerException {

		// remember the current class
		IR.currentClass = this;

		// typecheck super class
		
		this.superClass.typeCheck();
		
		// check fields

		for (MJVariable f : this.getFieldList()) {
			f.typeCheck();
		}

		// and check methods

		for (MJMethod m : this.getMethodList()) {

			m.typeCheck();
		}

		return MJType.getVoidType();
	}

	public void variableInit() throws TypeCheckerException {
		
		// check all methods
		for (MJMethod m : this.getMethodList()) {

			// create a new set containing all fields
			HashSet<MJVariable> initialized = new HashSet<MJVariable>(this.fieldList);
			
			// find the "this" field and add it to initialized
			MJVariable thisVar = null;
			try {
				thisVar = IR.find("this");
			} catch (VariableNotFound e) {
				// this can not happen
			}
			initialized.add(thisVar);
			
			m.variableInit(initialized);
		}
	}

}
