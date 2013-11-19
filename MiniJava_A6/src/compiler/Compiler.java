package compiler;

import compiler.Exceptions.*;
import compiler.Phases.Backend;
import compiler.Phases.Analysis;
import compiler.Phases.Frontend;
import compiler.Phases.Rewrite;
import compiler.IR.*;

public class Compiler {

	private static String filename;
	private static String outputfilename;
		
	private static boolean debug;
	
	private static String[] arguments=new String[0];
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Compiler c = new Compiler(args);
			c.compile();
		} 
		catch (CompilerError e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	public Compiler(String[] args) throws CompilerError {
		//
		// here we should inspect the arguments
		//

		if (args.length==0) {
			help();
		}

		this.filename = null;
		this.debug = false;

		for (int i=0;i<args.length;i++) {
			String arg = args[i];
			if (arg.charAt(0)!='-') {

				//
				// store the filename
				//

				this.filename = args[i];
				this.outputfilename = this.filename.substring(0, this.filename.lastIndexOf('.'))+".asm";
			} else {
				if (arg.equals("-v")) {
					this.debug=true;
					continue;
				}
				if (arg.equals("-o")) {

					if (i==args.length) {
						throw new CompilerError("argument for -o is missing");
					}
					
					this.outputfilename = args[i++];
					continue;
				}
				if (arg.equals("--")) {
					// from here on the arguments should go to the program
					
					Compiler.arguments = new String[(args.length - i - 1)];
					for (int ai = i+1; ai<args.length; ai++) {
						Compiler.arguments[ai-i-1] = args[ai];
					}
					break;
				}
			}
		}
		
		if (this.filename == null) {
			throw new CompilerError("No filename specified");
		}

	}

	public void compile() throws CompilerError
	{
		IR ir;
		
		System.out.print("Parsing... ");

		try {
			ir = Frontend.parse(this.filename);
		} catch (ParseError e) {
			throw new CompilerError("Parse Error: "+e.getMessage());
		}
		
		System.out.println("done.");
		
		if (this.isDebug()) {
			ir.prettyPrint();
		}
		
		System.out.println("Rewriting 1... ");
		Rewrite.rewriteOne(ir);
		System.out.println("done.");

		if (this.isDebug()) {
			ir.prettyPrint();
		}
		
		System.out.println("Analysing... ");

		try {
			Analysis.analyse(ir);
		} catch (TypeCheckerException e) {
			throw new CompilerError("TypeCheckError "+e.getMessage());
		}

		System.out.println("done.");
		
		System.out.println("Rewriting 2... ");
		Rewrite.rewriteTwo(ir);
		System.out.println("done.");

		if (this.isDebug()) {
			ir.prettyPrint();
		}
				
		System.out.println("Generating code... ");

		try {
			Backend.generate(ir);
		} catch (CodeGenException e) {
			throw new CompilerError("CodeGenError "+e.getMessage());
		}
		
		System.out.println("done.");
	}
	
	private void help() {
		
		System.err.println("\n");
		System.err.println("MiniJava compiler\n");
		System.err.println("====================\n\n");
		System.err.println("Invoke with \"java -jar path/to/jar/file \" or via GUI.");
		System.err.println("arguments: [-v] filename -- args\n\n");
		System.err.println("  -v            be verbose\n");
		System.err.println("  filename      source filename\n");
		System.err.println("  args          arguments passed on to Java program\n");
		System.exit(-1);
	}

	public static String getFilename() {
		return filename;
	}

	public static String getOutputfilename() {
		return outputfilename;
	}

	public static String[] getArguments() {
		return arguments;
	}

	public static boolean isDebug() {
		return debug;
	}
	
}
