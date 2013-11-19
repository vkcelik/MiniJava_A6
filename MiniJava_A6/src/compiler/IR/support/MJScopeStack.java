package compiler.IR.support;

import java.util.Stack;

import compiler.Exceptions.VariableAlreadyDeclared;
import compiler.Exceptions.VariableNotFound;
import compiler.IR.MJVariable;

public class MJScopeStack {

	private Stack<MJSymbolTable> stack = new Stack<MJSymbolTable>();
	
	public void enterScope() {
		stack.push(new MJSymbolTable());
	}
	
	public void leaveScope() {
		stack.pop();
	}
	public MJVariable find(String name) throws VariableNotFound {
		
		MJVariable decl = null;
		
		for (int i = stack.size()-1; i>=0; i--) {
			MJSymbolTable s = stack.elementAt(i);
			try {
				decl = s.lookup(name);
			} catch (VariableNotFound e) {
				// ignored - we look at next scope
				continue;
			}
			if (decl!=null) 
				break;
		}
		
		if (decl==null) {
			throw new VariableNotFound(name);
		}
		return decl;
	}
	
	public void add(MJVariable decl) throws VariableAlreadyDeclared {
		
		MJVariable existingdecl=null;
		
		try {
			existingdecl = find(decl.getName());
		} catch (VariableNotFound e) {
			// this means we can add it!
			stack.lastElement().add(decl);
			return;
		}

		throw new VariableAlreadyDeclared(existingdecl.getName());
	}

}
