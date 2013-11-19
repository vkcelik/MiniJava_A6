package compiler.IR.support;

import java.util.HashMap;
import java.util.LinkedList;

import compiler.Exceptions.*;
import compiler.IR.IR;
import compiler.IR.MJBlock;
import compiler.IR.MJClass;
import compiler.IR.MJExpression;
import compiler.IR.MJIdentifier;
import compiler.IR.MJIfElse;
import compiler.IR.MJMethod;
import compiler.IR.MJMethodCallStmt;
import compiler.IR.MJStatement;
import compiler.IR.MJType;
import compiler.IR.MJVariable;
import compiler.IR.MJWhile;

public class MJClassTable {
	private HashMap<String, MJClass> map = new HashMap<String, MJClass>();
	private HashMap<MJClass, MJSymbolTable> fieldmap = new HashMap<MJClass, MJSymbolTable>();
	private HashMap<MJClass, MJMethodTable> methodMap = new HashMap<MJClass, MJMethodTable>();

	public MJClassTable() {
		try {
			this.addArray("int");
			this.addArray("boolean");
		} catch (Exception e) {
			// nothing can go wrong here
		}
	}

	public MJClass lookup(String name) throws ClassNotFound {
		MJClass decl = (MJClass) map.get(name);
		if (decl == null) {
			throw new ClassNotFound(name);
		}
		return decl;
	}

	public void add(MJClass decl) throws ClassAlreadyDeclared, ClassErrorField {
		addClass(decl);
		addArray(decl.getName());
	}
	
	public void addClass(MJClass decl) throws ClassAlreadyDeclared, ClassErrorField {

		// check whether the class already exists
		
		MJClass existingdecl = (MJClass) map.get(decl.getName());
		
		if (existingdecl != null) {
			throw new ClassAlreadyDeclared(decl.getName());
		}
		
		// if not, add it
		
		map.put(decl.getName(), decl);

		// and add its fields
		
		MJSymbolTable newmap = new MJSymbolTable();
		
		fieldmap.put(decl, newmap);

		for (MJVariable var : decl.getFieldList()) {
			try {
				newmap.add(var);
			} catch (VariableAlreadyDeclared e) {
				throw new ClassErrorField(e.getMessage());
			}
		}
	}
	
	private void addArray(String name) throws ClassAlreadyDeclared, ClassErrorField {
		
		MJClass arrayDecl;
		
		LinkedList<MJVariable> vl = new LinkedList<MJVariable>();
		LinkedList<MJMethod> ml = new LinkedList<MJMethod>();
		
		MJVariable l = new MJVariable(MJType.getIntType(), "length");
		vl.add(l);
		
		arrayDecl = new MJClass(name+"[]", "Object", vl, ml);
		
		addClass(arrayDecl);
	}

	public MJVariable lookupField(MJClass decl, String name)
			throws ClassErrorField {

		MJSymbolTable map = fieldmap.get(decl);
		MJClass superclass = null;
		MJVariable v;

		try {
			v = map.lookup(name);
			return v;
		} catch (VariableNotFound e) {
			if (decl.getName().equals("Object")) {
				throw new ClassErrorField("Field " + name
						+ " not found in class " + decl.getName() + ".");
			}
		}

		try {
			superclass = IR.classes.lookup(decl.getSuperClass().getName());
		} catch (ClassNotFound e) {
			// this should not happen
		}
		v = lookupField(superclass, name);
		return v;
	}

	// when adding methods we need to make sure that non of the super classes
	// implements a method with the same name or types either

	public void addMethods(MJClass decl) throws ClassErrorMethod,
			ClassNotFound, InheritanceError {

		MJMethodTable newMethodMap = new MJMethodTable();
		methodMap.put(decl, newMethodMap);

		for (MJMethod meth : decl.getMethodList()) {
			try {

				checkMethod(decl, meth);
				newMethodMap.add(meth);
			} catch (ClassErrorMethod e) {
				throw new ClassErrorMethod(e.getMessage());
			}
		}

	}

	public MJMethod lookupMethod(MJClass decl, String name,
			LinkedList<MJExpression> arglist) throws ClassErrorMethod,
			MethodNotFound {
		return lookupMethod(decl, name, arglist, true);
		
	}
	
	public MJMethod lookupConstructor(MJClass decl) throws ClassErrorMethod, MethodNotFound {
	
		return lookupConstructor(decl, new LinkedList<MJExpression>());
	}

	public MJMethod lookupConstructor(MJClass decl, LinkedList<MJExpression> arglist) 
			throws ClassErrorMethod, MethodNotFound {
		
		MJMethod m = null;
		LinkedList<MJExpression> args = arglist;
		MJClass c = decl;
		
		while (true) {
			try {
				m = lookupMethod(c, c.getName(), args, false);
			} catch (MethodNotFound e) {

				if (c.getName().equals("Object")) {
					throw new MethodNotFound("No constructor.");
				}
				
				if (arglist.size()!=0) {
					args = new LinkedList<MJExpression>();
					continue;
				}
				
				MJClass c1 = c.getSuperClass().getDecl();
				
				if (c1 == null) {
					try {
						c.getSuperClass().typeCheck();
						c1 = c.getSuperClass().getDecl();
					} catch (TypeCheckerException e1) {
						// this should not happen
					}
				}
				
				c = c1;
				continue;
			}	
			
			break;
		}
		
		return m;
	}
	
	private MJMethod lookupMethod(MJClass decl, String name,
			LinkedList<MJExpression> arglist, boolean visitSuperClasses ) throws ClassErrorMethod,
			MethodNotFound {

		MJMethod meth = null;

		while (true) {

			MJMethodTable methods = methodMap.get(decl);

			try {
				meth = methods.lookup(name, arglist);
			} catch (MethodNotFound e) {
				// do nothing
			}

			if (meth != null)
				break;

			if (!visitSuperClasses || decl.getName().equals("Object")) {
				break;
			}

			MJType superClass = decl.getSuperClass();
			MJClass superdecl = null;
			try {
				superdecl = this.lookup(superClass.getName());
			} catch (ClassNotFound e) {
				// this should not happen - should have been caught before
			}

			decl = superdecl;
		}

		if (meth == null)
			throw new MethodNotFound(name);

		return meth;
	}

	private void checkMethod(MJClass decl, MJMethod meth) throws ClassNotFound,
			InheritanceError {

		MJType superClass = decl.getSuperClass();

		if (superClass.getName().equals("Object")) {
			return;
		}

		MJClass superdecl = this.lookup(superClass.getName());

		MJMethodTable methods = methodMap.get(superdecl);
		try {
			methods.check(meth);
		} catch (ClassErrorMethod e) {
			throw new InheritanceError(e.getMessage());
		}

		checkMethod(superdecl, meth);
	}

}
