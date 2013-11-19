package compiler;

import java.io.PrintStream;

public class PrettyPrinter {

	private PrintStream ps;
	private int indent=0;
	private static int width=2;
	private boolean isindented = false;
	
	public PrettyPrinter() {
		this.ps = System.out;
	}
	
	public PrettyPrinter(PrintStream ps) {
		this.ps = ps;
	}
	
	public void in() {
		indent+=width;
	}
	
	public void out() {
		indent-=width;
		if (indent<0) {
			indent=0;
		}
	}
	
	private void indent() {
		if (isindented) return;
		for (int i=0;i<this.indent;i++) ps.print(' ');
		isindented=true;
	}
	
	public void println(String s) {
		this.indent();
		ps.println(s);
		isindented=false;
	}

	public void print(String s) {
		this.indent();
		ps.print(s);
	}
}
