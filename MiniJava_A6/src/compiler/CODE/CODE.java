package compiler.CODE;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

import compiler.*;
import compiler.Compiler;
import compiler.CODE.LC3.*;
import compiler.Exceptions.CodeGenException;

public class CODE {

	public static LC3regs TMP0   = LC3regs.R0;
	public static LC3regs TMP1   = LC3regs.R1;
	public static LC3regs CONST0 = LC3regs.R2;
	public static LC3regs CONST1 = LC3regs.R3;
	public static LC3regs HP     = LC3regs.R4;
	public static LC3regs SP     = LC3regs.R5;
	public static LC3regs SFP    = LC3regs.R6;
	public static LC3regs RET    = LC3regs.R7;
	
	public LC3label newlineroutine;
	public LC3label nonewlineroutine;
	public LC3label newroutine;
	public LC3label inttostring;
	public LC3label multiply;
	public LC3label nullify;
	public LC3label addstrings;
	public LC3label nullpointer;
	public LC3label indexoutofbounds;
	public LC3label arguments;
	
	private int orig = 0x3000;
	private int heap = 0xFDFF;
	private int framestack = 0x8000;
	private int addr;

	private Vector<LC3> data = new Vector<LC3>(1000, 100);

	public CODE() {
		this.nonewlineroutine = this.newLabel();
		this.newlineroutine = this.newLabel();
		this.newroutine     = this.newLabel();
		this.inttostring    = this.newLabel();
		this.multiply       = this.newLabel();
		this.nullify        = this.newLabel();
		this.addstrings     = this.newLabel();
		this.nullpointer    = this.newLabel();
		this.indexoutofbounds = this.newLabel();
		this.arguments      = this.newLabel();
	}

	public CODE(int i) {
		this();
		this.orig = i;
		this.addr = i;
	}

	public int getFramestack() {
		return this.framestack;
	}

	public int getHeap() {
		return heap;
	}

	public LC3label newLabel() {
		return new LC3label();
	}

	public void add(LC3 item) throws CodeGenException {
		data.add(item);

		if (item instanceof LC3label) {
			LC3label.resolveLabel((LC3label)item, addr);
//			if (Compiler.isDebug()) {
//				System.err.println("Resolved label "+(LC3label)item+" to 0x"+Integer.toHexString(addr));
//			}
		} else {
			addr++;
		}
		
		if (addr>this.framestack) {
			throw new CodeGenException("program is too long");
		}
	}

	public void addData(LC3value item) {
		data.add(item);
		addr++;
	}
	
	public void push(LC3regs r) throws CodeGenException {
		this.add(new LC3ADD(CODE.SP, CODE.SP, 1));
		this.add(new LC3STR(r, CODE.SP, -1));
	}

	public void pop(LC3regs r) throws CodeGenException {
		this.add(new LC3ADD(CODE.SP, CODE.SP, -1));
		this.add(new LC3LDR(r, CODE.SP, 0));
	}

	public void pop2(LC3regs r0, LC3regs r1) throws CodeGenException {
		this.add(new LC3ADD(CODE.SP, CODE.SP, -2));
		this.add(new LC3LDR(r0, CODE.SP, 0));
		this.add(new LC3LDR(r1, CODE.SP, 1));
	}

	public void decrSP() throws CodeGenException {
		this.add(new LC3ADD(CODE.SP, CODE.SP, -1));
	}

	public void dump() throws CodeGenException {
		
		FileOutputStream out = null;
		
		try {
			out = new FileOutputStream(Compiler.getOutputfilename());
		} catch (FileNotFoundException e) {
			throw new CodeGenException("Could not open output file.");
		}

        // Connect print stream to the output stream
        PrintStream p = new PrintStream( out );
        
		int addr = this.orig;
		
		p.println(".ORIG x"+Integer.toHexString(this.orig));
		
		for (LC3 l : this.data) {
			// System.err.print(Integer.toHexString(addr) + " ");
			
			String lstr = l.toString();
			
			if (l instanceof LC3int || l instanceof LC3labeldata) {
				lstr = ".FILL " + lstr;
			} else
			if (l instanceof LC3string) {
				lstr = ".STRINGZ " + "\""+lstr+"\"";
			} else
			if (!(l instanceof LC3label)) {
				lstr = "  "+lstr;
			}
			
			p.println(lstr);
			
			if (!(l instanceof LC3label || l instanceof LC3comment)) {
				addr++;
			}
		}
		
		p.println(".END");
	}

	public void saveregs() {
	}

	public void comment(String string) throws CodeGenException {
		this.add(new LC3comment(""));
		this.add(new LC3comment(string));
		this.add(new LC3comment(""));
	}

	public void startComment() throws CodeGenException {
		this.add(new LC3comment(""));
	}
	
	public void endComment() throws CodeGenException {
		this.add(new LC3comment(""));
	}

	public void commentline(String string) throws CodeGenException {
		this.add(new LC3comment(string));
	}

	public void commentLine(String l) throws CodeGenException {
		this.add(new LC3comment(l));
	}

	public void space(int i) throws CodeGenException {
		this.add( new LC3space(i));
	}

}
