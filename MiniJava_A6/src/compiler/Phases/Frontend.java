package compiler.Phases;

import java.io.IOException;
import org.antlr.runtime.*;

import compiler.Exceptions.*;
import compiler.IR.*;
import compiler.Frontend.*;

public class Frontend {

	public static IR parse(String filename) throws ParseError, CompilerError {
		
		MJProgram program = null;
		
		try{
			
			// open the input file
			CharStream input = new ANTLRFileStream(filename);
			
			// create a lexer/scanner
			MiniJavaLexer lex = new MiniJavaLexer(input);
			
			// get the stream of tokens from the scanner
			CommonTokenStream tokens = new CommonTokenStream(lex);
			
			// create a parser
			MiniJavaParser parser = new MiniJavaParser(tokens);
			
			// and parse
			program = parser.program();
			
			// if we found errors, throw an exception
			if (parser.getNumberOfSyntaxErrors()>0) {
				throw new ParseError("Found "+parser.getNumberOfSyntaxErrors()+" error" + 
						((parser.getNumberOfSyntaxErrors()>1)?"s":"") +".");
			}
		} catch (IOException e) {
			throw new CompilerError(e.getMessage());
		} catch (RecognitionException e) {
			throw new ParseError(e.getMessage());
		}
		
		return new IR(program);
	}
}
