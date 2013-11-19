package compiler.IR.support;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import compiler.Exceptions.ClassErrorMethod;
import compiler.Exceptions.MethodNotFound;
import compiler.IR.MJExpression;
import compiler.IR.MJMethod;
import compiler.IR.MJType;
import compiler.IR.MJVariable;

public class MJMethodTable {
	private HashMap<String, HashSet<MJMethod>> map = new HashMap<String, HashSet<MJMethod>>();

	private enum AddFlag {
		add, check
	};

	void add(MJMethod m) throws ClassErrorMethod {
		add(m, AddFlag.add);
	}

	void check(MJMethod m) throws ClassErrorMethod {
		add(m, AddFlag.check);
	}

	void add(MJMethod m, AddFlag flag) throws ClassErrorMethod {

		HashSet<MJMethod> mset = null;

		// get the method name

		String name = m.getName();

		if (!map.containsKey(name)) {

			// if we have not seen any methods with this name we create a new
			// set and map the name to the empty set
			if (flag == AddFlag.add) {
				mset = new HashSet<MJMethod>();
				map.put(name, mset);
			}
		} else {

			// else we get the set

			mset = map.get(name);

			// check whether we already have seen a method with the same
			// argument types

			for (MJMethod o : mset) {
				LinkedList<MJVariable> newpars, oldpars;

				newpars = m.getParameters();
				oldpars = o.getParameters();

				int newoff = 0;
				int oldoff = 0;
				
//				 ( m.isStatic() ? 0 : 1);
//				 ( o.isStatic() ? 0 : 1);
				// if parameter lists have different length, we can ignore this
				// method

				if (newpars.size() - newoff != oldpars.size() - oldoff) {
					continue;
				}

				// now we know that the parameter lists have the same length, so
				// we iterate
				// over both and compare the types

				boolean matchfound = true;
				
				for (int i = newoff, j = oldoff; i < newpars.size() && j < oldpars.size(); i++, j++) {
					MJVariable newpar = newpars.get(i);
					MJVariable oldpar = oldpars.get(j);

					MJType newtype = newpar.getType();
					MJType oldtype = oldpar.getType();

					// if two types differ, we go to the next method
					if (!newtype.isSame(oldtype)) {
						matchfound = false;
						break;
					}
				}

				// if we iterated to the end, we found a method with same types
				if (matchfound == true) {
					throw new ClassErrorMethod(m.getName());
				}
			}

			// if we get here then either there where no methods, or all methods
			// had parameter lists
			// of differing length, or no method had the same parameter types
		}

		// add the method to the set if we were not just checking

		if (flag == AddFlag.add) {
			mset.add(m);
		}
	}

	MJMethod lookup(String mname, LinkedList<MJExpression> arglist)
			throws ClassErrorMethod, MethodNotFound {

		// get all methods for this name

		HashSet<MJMethod> decls = map.get(mname);

		if (decls != null) {
			// iterate over all methods m

			for (MJMethod m : decls) {

				// and compare types of parameters with types of arguments

				LinkedList<MJVariable> pars;

				pars = m.getParameters();

				// if parameter list has different length than argument list, we
				// can ignore this method

				if ( (m.isStatic() && pars.size() != arglist.size())
						|| (!m.isStatic() && (pars.size()-1 != arglist.size()))
				   ) {
					continue;
				}

				// now we know that the parameter list and the argument list
				// have the same length, so we iterate
				// over both and compare the types

				boolean matchfound = true;
				int start = 0;
				if (!m.isStatic()) start+=1;
				for (int i = start; i < pars.size(); i++) {
					MJVariable par = pars.get(i);
					MJExpression arg = arglist.get(i-start);

					MJType partype = par.getType();
					MJType argtype = arg.getType();

					// if two types differ, we go to the next method
					if ( ! MJType.isAssignable(argtype, partype) ) {
						matchfound = false;
						break;
					}
				}

				// if we iterated to the end, we found a method with same types
				if (matchfound == true) {
					return m;
				}

			}
		}
		throw new MethodNotFound(mname);
	}
	
}
