package compiler.IR;

import java.util.HashSet;

import compiler.Exceptions.ClassNotFound;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.TypeCheckerException;

public final class MJType extends IR {

	private enum TypeEnum {
		INT, BOOLEAN, NONE, CLASS, ARRAY, NULL, CONSTRUCTOR
	};

	private TypeEnum type;
	private String name;
	private MJType baseType;
	private MJClass decl;
	
	private MJType(TypeEnum type) {
		this.type = type;
		this.name = type.toString();
	}

	private MJType(TypeEnum type, String tname) {
		this.type = type;
		this.name = tname;
	}

	private MJType(String name) {
		this(TypeEnum.CLASS);
		this.name = name;
	}

	private MJType(MJClass c) {
		this(TypeEnum.CLASS);
		this.name = c.getName();
		this.decl = c;
	}

	private MJType(MJType t) {
		this(TypeEnum.ARRAY);
		this.name = t.getName() + "[]";
		this.baseType = t;
	}

	static public MJType getIntType() {
		return MJType.Tint;
	}
	
	static public MJType getBooleanType() {
		return MJType.Tboolean;
	}
	
	static public MJType getVoidType() {
		return MJType.Tnone;
	}
	
	static public MJType getClassType(String s) {
		return new MJType(s);
	}
	
	static public MJType getClassType(MJClass c) {
		return new MJType(c);
	}
	
	static public MJType getArrayType(MJType c) {
		return new MJType(c);
	}
	
	// create some static object to be used for standard types
	private static MJType Tint = new MJType(TypeEnum.INT, "int");
	private static MJType Tboolean = new MJType(TypeEnum.BOOLEAN, "boolean");
	private static MJType Tnone = new MJType(TypeEnum.NONE, "void");
	private static MJType Tnull = new MJType(TypeEnum.NULL, "null");
	private static MJType Tconstructor = new MJType(TypeEnum.CONSTRUCTOR, "");

	// check whether two types are the same

	public boolean isSame(MJType t) {

		if (t.getType() == this.getType()) {
			if (t.getType() == TypeEnum.CLASS) {
				if (this.isNull()) {
					return true;
				}
				if (t.getName().equals(this.getName())) {
					return true;
				}
				return false;
			}
			if (t.isNull()) {
				if (this.isNull() || this.isClass()) {
					return true;
				}
				return false;
			}
			if (t.getType() == TypeEnum.ARRAY) {
				if (this.getBaseType().isSame(t.getBaseType())) {
					return true;
				}
				return false;
			}
			return true;
		}
		return false;
	}
	
	// check whether one type can be assigned to the other
	
	public static boolean isAssignable(MJType src, MJType dest) {
		
		// if they have the same type, this should be easy
		
		if (src.getType() == dest.getType()) {
			
			// if they are class types
			
			if (src.getType() == TypeEnum.CLASS) {
				
				// if they are the same class, return true
				
				if (src.getName().equals(dest.getName())) {
					return true;
				}
				
				// may be dest is a super class of src...
				
				// but only if we are not yet at the root
				
				if (src.getName().equals("Object")) {
					return false;
				}
				
				if (src.decl==null) {
					try {
						src.typeCheck();
					} catch (TypeCheckerException e) {
						// we hope this cannot happen
						e.printStackTrace();
					}
				}
				
				return isAssignable(src.decl.getSuperClass(), dest);

			}
			
			// if they are array types
			
			if (src.getType() == TypeEnum.ARRAY) {
				if (dest.isClass() && dest.getName().equals("Object")){
					return true;
				}
				return MJType.isAssignable(src.getBaseType(), dest.getBaseType());
			}
			
			// otherwise they must be basic types
			return true;
		}
		
		if (src.isNull() && dest.isClass()) {
			return true;
		}
		
		if (src.isVoid() && dest.isConstructor()) {
			return true;
		}

		return false;
	}

	// convert type to string

	public String toString() {
		return name;
	}

	// check for a certain type

	public boolean isInt() {
		return (this.type == TypeEnum.INT);
	}

	public boolean isBoolean() {
		return (this.type == TypeEnum.BOOLEAN);
	}

	public boolean isVoid() {
		return (this.type == TypeEnum.NONE);
	}

	public boolean isClass() {
		return (this.type == TypeEnum.CLASS || this.type == TypeEnum.ARRAY);
	}

	public boolean isArray() {
		return (this.type == TypeEnum.ARRAY);
	}

	public boolean isNull() {
		return (this.type == TypeEnum.NULL);
	}

	public boolean isConstructor() {
		return (this.type == TypeEnum.CONSTRUCTOR);
	}

	// local getter functions

	public TypeEnum getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public MJType getBaseType() {
		return baseType;
	}

	public MJClass getDecl() {
		return decl;
	}

	public MJType typeCheck() throws TypeCheckerException {
		
		// if this is a class type, we need to check that we have seen a declaration for the class
		
		MJType t = this;
		
		if (t.isArray()) {
			// we need to check the base type
			
			this.baseType.typeCheck();
			
			t = t.baseType;
		}
		
		if (t.isClass()) {
			try {
				t.decl = IR.classes.lookup(t.name);
			} catch (ClassNotFound e) {
				throw new TypeCheckerException("No declaration of class "+this.name+" found.");
			}
		}
				
		return MJType.Tnone;
	}

	void variableInit(HashSet<MJVariable> initialized)
			throws TypeCheckerException {
		
		// nothing to be done here
		
		return;
	}

	public int getSize() throws CodeGenException {

		if (this.isInt()) {
			return 1;
		}
		if (this.isBoolean()) {
			return 1;
		}
		if (this.isArray()) {
			return 1;
		}
		if (this.isClass()) {
			return 1;
		}
		if (this.isNull()) {
			return 1;
		}
		throw new CodeGenException("Unknown type in getSize().");
	}

}
