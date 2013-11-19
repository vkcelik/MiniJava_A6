package compiler.IR.support;

import java.util.HashMap;
import compiler.Exceptions.*;
import compiler.IR.MJVariable;

public class MJSymbolTable {

	private HashMap<String, MJVariable> map = new HashMap<String, MJVariable>();
	
	public MJVariable lookup(String name) throws VariableNotFound {
		MJVariable decl = (MJVariable)map.get(name);
		if (decl==null) {
			throw new VariableNotFound(name);
		}
		return decl;
	}
	
	public void add(MJVariable decl) throws VariableAlreadyDeclared {
		MJVariable existingdecl = (MJVariable)map.get(decl.getName());
		if (existingdecl!=null) {
			throw new VariableAlreadyDeclared(decl.getName());
		}
		map.put(decl.getName(), decl);
	}
}
