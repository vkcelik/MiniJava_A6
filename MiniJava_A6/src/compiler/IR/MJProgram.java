package compiler.IR;

import java.util.LinkedList;

import compiler.Compiler;
import compiler.PrettyPrinter;
import compiler.CODE.CODE;
import compiler.CODE.LC3.*;
import compiler.Exceptions.ClassAlreadyDeclared;
import compiler.Exceptions.ClassErrorField;
import compiler.Exceptions.ClassErrorMethod;
import compiler.Exceptions.ClassNotFound;
import compiler.Exceptions.CodeGenException;
import compiler.Exceptions.InheritanceError;
import compiler.Exceptions.TypeCheckerException;

public class MJProgram extends IR {

	private LinkedList<MJClass> classes;

	public MJProgram(LinkedList<MJClass> cdl) {
		this.classes = cdl;
	}

	public LinkedList<MJClass> getClasses() {
		return classes;
	}

	public void prettyPrint(PrettyPrinter prettyPrinter) {
		for (MJClass c : classes) {
			c.prettyPrint(prettyPrinter);
		}
	}
	
	public void rewriteOne() {
		
		for (MJClass c : this.classes) {

			IR.currentClass = c;
			
			for (MJMethod m : c.getMethodList()) {
			
				IR.currentMethod = m;
				
				m.rewriteOne();

			}
		}
		
	}

	public void rewriteTwo() {
		
		for (MJClass c : this.classes) {

			IR.currentClass = c;
			
			for (MJVariable f : c.getFieldList()) {
				f.rewriteTwo();
			}
			
			for (MJMethod m : c.getMethodList()) {
			
				IR.currentMethod = m;
				
				m.rewriteTwo();

			}
		}
		
	}

	public MJType typeCheck() throws TypeCheckerException {

		// we need a super class in our class table - Object

		MJClass oc = new MJClass("Object", "Object",
				new LinkedList<MJVariable>(), new LinkedList<MJMethod>());

		this.classes.addLast(oc);
		
		LinkedList<MJVariable> varlist = new LinkedList<MJVariable>();
		MJVariable length = new MJVariable(MJType.getIntType(), "length");
		MJVariable text = new MJVariable(MJType.getClassType("String"), "text");
		varlist.add(text);
		varlist.addLast(length);

		oc = new MJClass("String", "Object", varlist, new LinkedList<MJMethod>());
		
		this.classes.addLast(oc);

		// and add its methods - this is actually empty

		// now iterate over all classes in the program and add them to the
		// classtable

		for (MJClass c : this.getClasses()) {
			try {
				IR.classes.add(c);
			} catch (ClassAlreadyDeclared e1) {
				throw new TypeCheckerException("Class " + e1.getMessage()
						+ " already declared.");
			} catch (ClassErrorField e1) {
				throw new TypeCheckerException("Class " + c.getName()
						+ " has two fields with name " + e1.getMessage());
			}
		}

		// and add all methods as well

		for (MJClass c : this.getClasses()) {
			try {
				IR.classes.addMethods(c);
			} catch (ClassErrorMethod e1) {
				throw new TypeCheckerException("Class " + e1.getMessage()
						+ " already declared.");
			} catch (ClassNotFound e1) {
				throw new TypeCheckerException("Class " + e1.getMessage()
						+ " not found.");
			} catch (InheritanceError e1) {
				throw new TypeCheckerException("Class " + c.getName()
						+ " overwrites a method.");
			}
		}

		// finally we can typecheck the individual classes

		for (MJClass c : this.getClasses()) {
			c.typeCheck();
		}

		return MJType.getVoidType();
	}

	public void variableInit() throws TypeCheckerException {

		// now we can invoke variableinit on the classes

		for (MJClass c : this.getClasses()) {
			c.variableInit();
		}
	}

	public void codeGen(CODE code) throws CodeGenException {

		// compute size of classes

		for (MJClass c : this.classes) {

			// compute size of classes

			int size = 0;
			MJClass cl = c;

			while (true) {

				for (MJVariable f : cl.getFieldList()) {
					int fieldsize = f.getType().getSize();

					size += fieldsize;
				}

				if (cl.getName() != "Object") {
					cl = cl.getSuperClass().getDecl();
				} else {
					break;
				}
			}

			if (size==0) size=1;
			
			c.setSize(size);

			if (Compiler.isDebug()) {
				System.err.println("Size Class " + c.getName() + ": " + size);
			}

			for (MJMethod m : c.getMethodList()) {

				// assign labels to each method

				LC3label l = code.newLabel();
				m.setLabel(l);

				// check whether the last statement is a return statement

				MJStatement last = null;
				
				if (m.getBody().getStatements().size()>0) {
					last = m.getBody().getStatements().getLast();
				}
				
				if (m.getBody().getStatements().size()==0 || !(last instanceof MJReturn)) {
						// this is the main method of the main class - the only method that does not need to have a return statement
						// who designed this stupid language?
					
					MJReturn r = new MJReturn(new MJNoExpression());
					m.getBody().getStatements().addLast(r);
				}
				
				// take care of variable initialization
				// insert assignment statements at the beginning of the block
				
				removeVarInit(m.getBody());
			}
		}

		for (MJClass c : this.classes) {

			if (Compiler.isDebug()) {
				System.err.println("Class "+c.getName());
			}

			assignFieldOffsets(0, c);

			for (MJMethod m : c.getMethodList()) {

				if (Compiler.isDebug()) {
					System.err.println("  Method "+m.getName());
				}

				int offset = 0;
				offset = assignOffsets(offset, m.getParameters());
				
				offset = findLocalVars(offset, m.getBody());
				
				m.setVarCount(offset);
			}
		}
		
		for (MJClass c : this.classes) {

			if (Compiler.isDebug()) {
				System.err.println("Class "+c.getName());
			}

			int maxStackSize;

			for (MJMethod m : c.getMethodList()) {

				if (Compiler.isDebug()) {
					System.err.print("  Method "+m.getName());
				}

				maxStackSize = 3;
				
				maxStackSize += m.getVarCount();
				
				maxStackSize += m.getBody().requiredStackSize();
				
				// if the method returns something the stack size must at least be 4
				
				if (!m.getType().isSame(MJType.getVoidType()) && maxStackSize==3) {
					maxStackSize = 4;
				}
				
				System.err.println(" MSS "+maxStackSize);
				m.setMaxStackSize(maxStackSize);
			}
		}

		code.commentline(" initialize CONST0 and CONST1");
		code.add(new LC3AND(CODE.CONST0, CODE.CONST0, 0));
		code.add(new LC3ADD(CODE.CONST1, CODE.CONST0, 1));

		LC3label cont = code.newLabel();
		LC3label data = code.newLabel();
		LC3label heap = code.newLabel();
	
		code.commentline(" set SFP and HP ");
		code.add( new LC3LD(CODE.SFP, data));
		code.add( new LC3LD(CODE.HP, heap));
	
		code.add( new LC3BR(cont) );
		code.commentline(" data for SFP and HP");
		code.add(data);
		code.add( new LC3int(code.getFramestack()) );
		code.add(heap);
		code.add( new LC3int(code.getHeap()));

		String[] args = Compiler.getArguments();
		code.commentline(" arguments to main");
		if (args.length>0) {
			code.commentline(" string contents");
			LC3label[] contlabs = new LC3label[args.length];
			LC3label[] strlabs = new LC3label[args.length];
			for (int i=0;i<args.length;i++) {
				contlabs[i] = code.newLabel();
				code.add( contlabs[i] );
				code.add( new LC3string(args[i]));
			}
			code.commentline(" strings");
			for (int i=0;i<args.length;i++) {
				strlabs[i] = code.newLabel();
				code.add( strlabs[i]);
				code.add( new LC3labeldata(contlabs[i]) );
				code.add( new LC3int(args[i].length()+1));	
			}
			code.commentline(" args array");
			code.add(code.arguments);
			code.add(new LC3int(args.length));
			for (int i=0;i<args.length;i++) {
				code.add( new LC3labeldata(strlabs[i]));
			}
		} else {
			code.add(code.arguments);
			code.add(new LC3int(0));
		}
		code.add(cont);
		
		for (MJClass c : this.classes) {
			IR.currentClass = c;
			for (MJMethod meth : c.getMethodList()) {
				IR.currentMethod = meth;
				code.add(meth.getLabel());
				meth.generateCode(code);
			}
		}
		
		code.comment(" helper functions ");
		code.comment("");
		
		code.comment(" translate top of stack to string, pushes result");
		code.add( code.inttostring );
		
		code.startComment();
		code.commentLine("This algorithm takes the 2's complement representation of a signed");
		code.commentLine("integer, within the range -999 to +999, and converts it into an ASCII"); 
		code.commentLine("string consisting of a sign digit, followed by three decimal digits."); 
		code.endComment();
		
		code.pop( CODE.TMP0 );
		code.push(CODE.RET);
		
		LC3label asciibuf = code.newLabel();
		LC3label asciiMinus = code.newLabel();
		LC3label asciiOffset = code.newLabel();
		LC3label begin10000 = code.newLabel();
		LC3label neg10000 = code.newLabel();
		LC3label loop10000 = code.newLabel();
		LC3label end10000 = code.newLabel();
		LC3label pos10000 = code.newLabel();
		LC3label begin1000 = code.newLabel();
		LC3label neg1000 = code.newLabel();
		LC3label loop1000 = code.newLabel();
		LC3label end1000 = code.newLabel();
		LC3label pos1000 = code.newLabel();
		LC3label begin100 = code.newLabel();
		LC3label neg100 = code.newLabel();
		LC3label loop100 = code.newLabel();
		LC3label end100 = code.newLabel();
		LC3label pos100 = code.newLabel();
		LC3label begin10 = code.newLabel();
		LC3label neg10 = code.newLabel();
		LC3label loop10 = code.newLabel();
		LC3label end10 = code.newLabel();
		LC3label pos10 = code.newLabel();
		LC3label begin1 = code.newLabel();
		LC3label neg1 = code.newLabel();
		LC3label pos1 = code.newLabel();
		LC3label no1 = code.newLabel();
		LC3label no10 = code.newLabel();
		LC3label no100 = code.newLabel();
		LC3label no1000 = code.newLabel();
		LC3label no10000 = code.newLabel();
		
		code.add( new LC3LEA(CODE.TMP1, asciibuf));
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, 0));
		code.add( new LC3BRZP(begin10000));
		
		code.add( new LC3LD(CODE.RET, asciiMinus));
		code.add( new LC3STR(CODE.RET, CODE.TMP1, 0));
		code.add( new LC3ADD(CODE.TMP1, CODE.TMP1, 1));
		code.add( new LC3NOT(CODE.TMP0, CODE.TMP0));
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, 1));

		code.add(begin10000);
				
		//code.add( new LC3LD(CODE.RET, asciiOffset));
		code.add( new LC3AND(CODE.RET, CODE.RET, 0));
		
		code.add( new LC3LD(CODE.CONST0, neg10000));
		code.add( loop10000 );
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, CODE.CONST0));
		code.add( new LC3BRN(end10000));
		code.add( new LC3ADD(CODE.RET, CODE.RET, 1));
		code.add( new LC3BR( loop10000 ));
		code.startComment();
		
		code.add( end10000);
		code.add( new LC3ADD( CODE.RET, CODE.RET, 0));
		code.add( new LC3BRZ(no10000));
		code.add( new LC3LD( CODE.CONST0, asciiOffset));
		code.add( new LC3ADD( CODE.RET, CODE.RET, CODE.CONST0));
		code.add( new LC3STR( CODE.RET, CODE.TMP1, 0));
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, 1));
		code.add( no10000 );
		code.add( new LC3LD(CODE.CONST0, pos10000));
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, CODE.CONST0 ));
		code.startComment();
		code.add( new LC3AND(CODE.RET, CODE.RET, 0));
		
		code.add( begin1000 ); 
		code.add( new LC3LD( CODE.CONST0, neg1000));
		code.add( loop1000 );
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, CODE.CONST0));
		code.add( new LC3BRN(end1000));
		code.add( new LC3ADD( CODE.RET, CODE.RET, 1));
		code.add( new LC3BR( loop1000));
		code.startComment();

		code.add(end1000);
		code.add( new LC3ADD( CODE.RET, CODE.RET, 0));
		code.add( new LC3BRZ(no1000));
		code.add( new LC3LD( CODE.CONST0, asciiOffset));
		code.add( new LC3ADD( CODE.RET, CODE.RET, CODE.CONST0));
		code.add( new LC3STR( CODE.RET, CODE.TMP1, 0));
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, 1));
		code.add( no1000 );
		code.add( new LC3LD(CODE.CONST0, pos1000));
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, CODE.CONST0 ));
		code.startComment();
		code.add( new LC3AND(CODE.RET, CODE.RET, 0));
		
		code.add( begin100 ); 
		code.add( new LC3LD( CODE.CONST0, neg100));
		code.add( loop100 );
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, CODE.CONST0));
		code.add( new LC3BRN(end100));
		code.add( new LC3ADD( CODE.RET, CODE.RET, 1));
		code.add( new LC3BR( loop100));
		code.startComment();

		code.add(end100);
		code.add( new LC3ADD( CODE.RET, CODE.RET, 0));
		code.add( new LC3BRZ(no100));
		code.add( new LC3LD( CODE.CONST0, asciiOffset));
		code.add( new LC3ADD( CODE.RET, CODE.RET, CODE.CONST0));
		code.add( new LC3STR( CODE.RET, CODE.TMP1, 0));
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, 1));
		code.add( no100 );
		code.add( new LC3LD(CODE.CONST0, pos100));
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, CODE.CONST0 ));
		code.startComment();
		code.add( new LC3AND(CODE.RET, CODE.RET, 0));
		
		code.add( begin10 ); 
		code.add( new LC3LD( CODE.CONST0, neg10));
		code.add( loop10 );
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, CODE.CONST0));
		code.add( new LC3BRN(end10));
		code.add( new LC3ADD( CODE.RET, CODE.RET, 1));
		code.add( new LC3BR( loop10));
		code.startComment();

		code.add(end10);
		code.add( new LC3ADD( CODE.RET, CODE.RET, 0));
		code.add( new LC3BRZ(no10));
		code.add( new LC3LD( CODE.CONST0, asciiOffset));
		code.add( new LC3ADD( CODE.RET, CODE.RET, CODE.CONST0));
		code.add( new LC3STR( CODE.RET, CODE.TMP1, 0));
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, 1));
		code.add( no10 );
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, 10 ));
		code.startComment();
		code.add( new LC3LD( CODE.RET, asciiOffset));
		
		code.add( begin1 );
		code.add( new LC3ADD(CODE.RET, CODE.RET, CODE.TMP0));
		code.add( new LC3STR(CODE.RET, CODE.TMP1, 0));
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, 1));		
		code.add( new LC3AND(CODE.CONST0, CODE.CONST0, 0) );
		code.add( new LC3STR( CODE.CONST0, CODE.TMP1, 0));
		code.pop(CODE.RET);
		code.add( new LC3LEA(CODE.TMP0, asciibuf));
		code.push(CODE.TMP0);
		code.add( new LC3RET());

		code.comment("data");
		code.add(asciibuf);
		code.space(7);
		code.add(asciiMinus);
		code.add(new LC3int(0x2D));
		code.add(asciiOffset);
		code.add(new LC3int(0x30));
		code.add(neg10000);
		code.add(new LC3int(-10000));
		code.add(neg1000);
		code.add(new LC3int(-1000));
		code.add(neg100);
		code.add(new LC3int(-100));
		code.add(neg10);
		code.add(new LC3int(-10));
		code.add(neg1);
		code.add(new LC3int(-1));
		code.add(pos10000);
		code.add(new LC3int(10000));
		code.add(pos1000);
		code.add(new LC3int(1000));
		code.add(pos100);
		code.add(new LC3int(100));
		code.add(pos10);
		code.add(new LC3int(10));
		code.add(pos1);
		code.add(new LC3int(1));

		code.comment(" print newline ");
		code.add( code.newlineroutine );
		code.push( CODE.RET );
		
		LC3label nlcont = code.newLabel();
		LC3label newline = code.newLabel();
		code.add( new LC3LD(CODE.TMP0, 1));
		code.add( new LC3BR(nlcont));
		code.add( new LC3labeldata(newline));
		code.add( newline );
		code.add( new LC3string("\\n"));
		code.add( nlcont );
		code.add( new LC3TRAP(0x22) );
		code.pop( CODE.RET );
		code.add( new LC3RET());
		
		code.add( code.newroutine );
		code.comment(" create an object with size top of stack, result in HP");
		
		code.pop(CODE.TMP0);
		code.add( new LC3comment("allocate object"));
		code.add( new LC3NOT(CODE.TMP0, CODE.TMP0));
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, 1));
		code.add( new LC3ADD(CODE.HP, CODE.HP, CODE.TMP0));
		
		// HP contains new heap pointer
		// check whether this is smaller than SP
		
		LC3label ok = code.newLabel();
		LC3label Lok = code.newLabel();
		LC3label Lsppos = code.newLabel();
		LC3label Lerr = code.newLabel();
		LC3label Lsub = code.newLabel();

		code.add( new LC3ADD(CODE.SP, CODE.SP, 0 ));
		code.add( new LC3BRP(Lsppos));
		code.add( new LC3ADD(CODE.HP, CODE.HP, 0));
		code.add( new LC3BRP(Lerr));
		code.add( Lsub );
		// subtract

		code.add( new LC3ADD(CODE.TMP1, CODE.SP, 0));
		code.add( new LC3NOT(CODE.TMP1, CODE.TMP1));
		code.add( new LC3ADD(CODE.TMP1, CODE.TMP1, 1));
		code.add( new LC3ADD(CODE.TMP1, CODE.HP, CODE.TMP1));

		code.add( new LC3BRP(Lok));
		code.add( new LC3BR(Lerr));
		code.add( Lsppos );
		
		code.add( new LC3ADD( CODE.HP, CODE.HP, 0));
		code.add( new LC3BRN(Lok));
		
		code.add( new LC3BR( Lsub ));
		
		code.add( Lerr );
		code.add( new LC3TRAP(0x25));

		code.add( Lok);
		code.push(CODE.HP);
		code.add( new LC3RET());
		
		code.comment(" nullify ");
		code.commentline(" overwrites memory area a to b with 0s ");
		code.commentline(" expects operands in top of stack ");
		code.commentline(" assumes a<b!!! ");
		code.add( code.nullify );

		LC3label eraseloop = code.newLabel();
		
		code.pop2(CODE.TMP0, CODE.TMP1);
		code.push( CODE.TMP0 );
		code.add( new LC3NOT(CODE.TMP0, CODE.TMP0));
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, 1));
		code.add( new LC3ADD(CODE.TMP1, CODE.TMP1, CODE.TMP0));
		code.pop( CODE.TMP0 );
		code.add( eraseloop );
		code.add( new LC3STR( CODE.CONST0, CODE.TMP0, 0));
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, 1));
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, -1));
		code.add( new LC3BRP(eraseloop));
		code.add( new LC3RET());
		
		code.comment(" multiplication routine ");
		code.commentline(" expects operands on top of stack ");
		code.add( code.multiply);
		LC3label l = code.newLabel();
		LC3label m = code.newLabel();
		LC3label zero = code.newLabel();
		LC3label apos = code.newLabel();
		LC3label aneg_bpos = code.newLabel();
		LC3label negateb = code.newLabel();
		LC3label mullab = code.newLabel();
		LC3label nosign = code.newLabel();
		
		code.commentline(" while loop that multiplies a and b, R7 is sum ");
		
		code.pop2(CODE.TMP0, CODE.TMP1);
		code.commentline(" get a and b");
		code.commentline(" check signs ");
		code.commentline(" CONST0 is used to store the flag of the result");
		code.commentline(" 0 means positive (default), 1 negative");
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, 0));
		code.commentline(" if one is zero we're done");
		code.add( new LC3BRZ(zero));
		code.commentline(" if a is positive, jump");
		code.add( new LC3BRP(apos));
		code.commentline(" negate a ");
		code.add( new LC3NOT(CODE.TMP0, CODE.TMP0));
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, 1));
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, 0));
		code.commentline(" if one is zero we're done");
		code.add( new LC3BRZ(zero));
		code.commentline(" if b is positive, jump");
		code.add( new LC3BRP(aneg_bpos));
		code.commentline(" a is neg, b is too");
		code.commentline(" negate b");
		code.add( negateb );
		code.add( new LC3NOT(CODE.TMP1, CODE.TMP1));
		code.add( new LC3ADD(CODE.TMP1, CODE.TMP1, 1));
		code.commentline(" go, multiply!");
		code.add( new LC3BR(l));
		code.add( aneg_bpos);
		code.commentline(" a is negative, b is positive");
		code.commentline(" set flag to 1 for negative result");
		code.add( new LC3ADD(CODE.CONST0, CODE.CONST0, 1));
		code.commentline(" go, multiply!");
		code.add( new LC3BR(l));
		code.add( apos );
		code.commentline( " a is positive ");
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, 0));
		code.commentline(" if one is zero we're done");
		code.add( new LC3BRZ(zero));
		code.commentline(" if b is positive, go multiply!");
		code.add( new LC3BRP(l));
		code.commentline(" a is pos, b is neg");
		code.commentline(" set flag to 1 for negative result");
		code.add( new LC3ADD(CODE.CONST0, CODE.CONST0, 1));
		code.add( new LC3BR( negateb ));

		code.comment(" multiply ");
		code.add(l);
		code.push(CODE.RET);
		code.commentline(" reset sum");
		code.add( new LC3AND(CODE.RET, CODE.RET, 0));
		code.add(mullab);
		code.add( new LC3ADD( CODE.RET, CODE.RET, CODE.TMP0));  
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, -1));
		code.add( new LC3BRP(mullab) );
		code.commentline(" adjust sign ");
		code.add( new LC3ADD( CODE.CONST0, CODE.CONST0, 0));
		code.add( new LC3BRZ( nosign ));
		code.add( new LC3NOT(CODE.RET, CODE.RET));
		code.add( new LC3ADD(CODE.RET, CODE.RET, 1));
		code.add(nosign);
		code.commentline( " reset CONST0 ");
		code.add( new LC3AND( CODE.CONST0, CODE.CONST0, 0));
		code.commentline(" transfer sum to TMP0, get RET from stack");
		code.add( new LC3ADD( CODE.TMP0, CODE.RET, 0));
		code.pop(CODE.RET);
		code.commentline( " result in R0 ");
		code.push(CODE.TMP0);
		code.add( new LC3RET());
		code.commentline( " one factor was 0");
		code.add( zero );
		code.push(CODE.CONST0);
		code.add(new LC3RET());

		
		code.comment(" null pointer exception ");
		code.commentline( " prints error message and exits");
		code.add( code.nullpointer );

		LC3label npeerrmsg = code.newLabel();
		
		code.add( new LC3LEA( CODE.TMP0, npeerrmsg ));
		code.add( new LC3TRAP(0x22));
		code.add( new LC3TRAP(0x25));
		code.add( npeerrmsg);
		code.add( new LC3string("Null pointer exception\n"));
		
		code.comment(" index out of bounds exception ");
		code.commentline( " prints error message and exits");
		code.add( code.indexoutofbounds );

		LC3label iooberrmsg = code.newLabel();
		
		code.add( new LC3LEA( CODE.TMP0, iooberrmsg ));
		code.add( new LC3TRAP(0x22));
		code.add( new LC3TRAP(0x25));
		code.add( iooberrmsg);
		code.add( new LC3string("Index out of bounds exception\n"));

		code.comment(" add two strings ");
		code.commentline( " expects args on top of stack, puts result on stack");
		code.add( code.addstrings );
		
		LC3label stringlab = code.newLabel();
		LC3label stringcont = code.newLabel();
		LC3label newmethod = code.newLabel();
		LC3label anotnull = code.newLabel();
		LC3label bnotnull = code.newLabel();
		LC3label nullstring = code.newLabel();
		LC3label _nullstring = code.newLabel();
		
		code.add( new LC3LDR( CODE.TMP0, CODE.SP, -2 ));
		code.add( new LC3BRNP(anotnull));
		code.add( new LC3LEA( CODE.TMP0, nullstring));
		code.add( new LC3STR( CODE.TMP0, CODE.SP, -2));
		code.add( new LC3BR( bnotnull));
		code.add(anotnull);
		code.add( new LC3LDR( CODE.TMP0, CODE.SP, -1 ));
		code.add( new LC3BRNP(bnotnull));
		code.add( new LC3LEA( CODE.TMP0, nullstring));
		code.add( new LC3STR( CODE.TMP0, CODE.SP, -1));
		code.add( new LC3BR( bnotnull));
		code.add( nullstring);
		code.add( new LC3labeldata( _nullstring ));
		code.add( new LC3int(5));
		code.add( _nullstring);
		code.add( new LC3string("null"));
		code.add(bnotnull);
		code.pop2( CODE.TMP0, CODE.TMP1 );
		code.push( CODE.RET);
		code.commentline(" compute combined length ");
		code.add( new LC3LDR(CODE.RET, CODE.TMP0, 1));
		code.push(CODE.TMP0);
		code.add( new LC3ADD(CODE.TMP0, CODE.RET, 0));
		code.add( new LC3LDR(CODE.RET, CODE.TMP1, 1));
		code.push(CODE.TMP1);
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP0, CODE.RET));
		code.add( new LC3BR(stringcont));
		code.add(newmethod);
		code.add( new LC3labeldata(code.newroutine));
		code.add(stringlab);
		try {
			code.add( new LC3int(IR.classes.lookup("String").getSize()));
		} catch (ClassNotFound e) {
			// this cannot happen
		}

		code.commentline(" copy string ");

		LC3label loopentry = code.newLabel();
		LC3label loopentryaddr = code.newLabel();

		code.add(loopentryaddr);
		code.add( new LC3labeldata(loopentry));
		
		code.add(loopentry);
		code.push(CODE.RET);
		code.add( new LC3LDR(CODE.TMP0, CODE.TMP0, 0) );
		
		LC3label loopdone = code.newLabel();
		LC3label loop = code.newLabel();
		code.add(loop);
		code.add( new LC3LDR( CODE.RET, CODE.TMP0, 0 ));
		code.add( new LC3BRZ(loopdone));
		code.add( new LC3STR( CODE.RET, CODE.TMP1, 0));
		code.add( new LC3ADD( CODE.TMP0, CODE.TMP0, 1));
		code.add( new LC3ADD( CODE.TMP1, CODE.TMP1, 1));
		code.add( new LC3BR(loop));
		code.add( loopdone );
		code.pop(CODE.RET);
		code.add( new LC3RET());
		
		code.add(stringcont);
		
		code.commentline(" invoke new method ");
		code.add( new LC3LD(CODE.TMP1, stringlab));
		code.add( new LC3ADD(CODE.TMP0, CODE.TMP1, CODE.TMP0));
		code.push(CODE.TMP0);
		code.add( new LC3LD(CODE.TMP1, newmethod));
		code.add( new LC3JSRR(CODE.TMP1));
		
		// now HP contains reference to the new object
		
		// the string is stored after the object
		code.commentline(" initialize object pointed to by HP ");
		code.commentline(" initialize string ");

		code.add( new LC3ADD(CODE.TMP0, CODE.HP, 2) );
		code.add( new LC3STR(CODE.TMP0, CODE.HP, 0) );
		
		// set the length
		code.commentline(" initialize length ");
		code.pop(CODE.TMP0);
		code.add( new LC3STR(CODE.TMP0, CODE.HP, 1) );

		// now we "only" need to copy the two strings...

		code.commentline(" get two strings ");
		code.pop2(CODE.TMP0, CODE.TMP1);

		// we start with the lhs, so back to the stack with you...
		code.push(CODE.TMP1);
		
		code.commentline(" copy first string from TMP0->0 to HP->0 ");
		code.add( new LC3LDR(CODE.TMP1, CODE.HP, 0) );
		// code.pop(CODE.TMP0);
		code.add( new LC3LD(CODE.RET, loopentryaddr));
		code.add( new LC3JSRR( CODE.RET));

		code.commentline(" copy second string from TMP0->0 to HP->0 ");
		code.pop(CODE.TMP0);
		code.add( new LC3LD(CODE.RET, loopentryaddr));
		code.add( new LC3JSRR( CODE.RET));
		code.pop( CODE.RET );
		code.push( CODE.HP );
		code.add( new LC3RET() );

	}

	private int assignFieldOffsets(int i, MJClass c) throws CodeGenException {

		int offset = i;
		
		if ( c.getFieldOffsets() != -1 ) {
			return c.getFieldOffsets();
		}
		
		if (!c.getName().equals("Object")) {
			offset = assignFieldOffsets( offset, c.getSuperClass().getDecl());
		}
		
		offset = assignOffsets(offset, c.getFieldList());
		c.setFieldOffsets(offset);
		return offset;
	}

	private void removeVarInit(MJBlock b) {

		int pos = 0;
		for (MJVariable v : b.getVariables()) {
			if (!(v.getInit() instanceof MJNoExpression)) {
				
				MJIdentifier i = new MJIdentifier(v.getName());
				i.setType(v.getType());
				i.setDecl(v);
				
				MJAssign a = new MJAssign(i, v.getInit());
				b.getStatements().add(pos, a);
				pos++;
			}
		}
	}

	private int findLocalVars(int offset, MJBlock body) throws CodeGenException {
		
		offset = assignOffsets(offset, body.getVariables());
		
		for (MJStatement s: body.getStatements()) {
			
			if (s instanceof MJBlock) {
				offset = findLocalVars(offset, (MJBlock)s);
			}

			if (s instanceof MJIfElse) {
				MJIfElse s_if = (MJIfElse) s;
				offset = findLocalVars(offset, s_if.getThenblock());
				if (s_if.getElseblock()!=null) {
					offset = findLocalVars(offset, s_if.getElseblock());
				}
			}
			
			if (s instanceof MJWhile) {
				MJWhile s_w = (MJWhile) s;
				offset = findLocalVars(offset, s_w.getBody());
			}
		}
		
		return offset;
	}

	private int assignOffsets(int offset, LinkedList<MJVariable> fieldList) throws CodeGenException {

		
		for (MJVariable f : fieldList) {
			f.setOffset(offset);
			
			if (Compiler.isDebug()) {
				System.err.println("    Offset "+f.getName()+" "+offset);
			}
			
			offset += f.getType().getSize();
		}
		
		return offset;
	}

}
