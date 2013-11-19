// $ANTLR 3.5 /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g 2013-11-19 00:37:37

  package compiler.Frontend;
  
  import java.util.LinkedList;
  import compiler.IR.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class MiniJavaParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "CHAR", "COMMENT", "IDENT", "INT", 
		"LOWER", "NEWLINE", "NONNULL", "NUMBER", "STRING", "UPPER", "WHITESPACE", 
		"'!'", "'&&'", "'('", "')'", "'*'", "'+'", "','", "'-'", "'.'", "';'", 
		"'<'", "'='", "'=='", "'String[]'", "'System.out.print'", "'System.out.println'", 
		"'['", "']'", "'boolean'", "'class'", "'else'", "'extends'", "'false'", 
		"'if'", "'int'", "'main'", "'new'", "'public'", "'return'", "'static'", 
		"'this'", "'true'", "'void'", "'while'", "'{'", "'}'"
	};
	public static final int EOF=-1;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int T__32=32;
	public static final int T__33=33;
	public static final int T__34=34;
	public static final int T__35=35;
	public static final int T__36=36;
	public static final int T__37=37;
	public static final int T__38=38;
	public static final int T__39=39;
	public static final int T__40=40;
	public static final int T__41=41;
	public static final int T__42=42;
	public static final int T__43=43;
	public static final int T__44=44;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int T__48=48;
	public static final int T__49=49;
	public static final int T__50=50;
	public static final int CHAR=4;
	public static final int COMMENT=5;
	public static final int IDENT=6;
	public static final int INT=7;
	public static final int LOWER=8;
	public static final int NEWLINE=9;
	public static final int NONNULL=10;
	public static final int NUMBER=11;
	public static final int STRING=12;
	public static final int UPPER=13;
	public static final int WHITESPACE=14;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public MiniJavaParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public MiniJavaParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return MiniJavaParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g"; }





	// $ANTLR start "program"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:17:1: program returns [MJProgram p] :mc= mainClass (cd= classDeclaration )* ;
	public final MJProgram program() throws RecognitionException {
		MJProgram p = null;


		MJClass mc =null;
		MJClass cd =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:18:3: (mc= mainClass (cd= classDeclaration )* )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:18:5: mc= mainClass (cd= classDeclaration )*
			{
			 LinkedList<MJClass> cdl = new LinkedList<MJClass>(); 
			pushFollow(FOLLOW_mainClass_in_program52);
			mc=mainClass();
			state._fsp--;

			 cdl.add(mc); 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:21:7: (cd= classDeclaration )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==34) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:21:9: cd= classDeclaration
					{
					pushFollow(FOLLOW_classDeclaration_in_program74);
					cd=classDeclaration();
					state._fsp--;

					 cdl.add(cd); 
					}
					break;

				default :
					break loop1;
				}
			}

			 
			      p = new MJProgram(cdl);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return p;
	}
	// $ANTLR end "program"



	// $ANTLR start "classDeclaration"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:29:1: classDeclaration returns [MJClass c] : 'class' cname= IDENT ( 'extends' scname= IDENT )? '{' (vd= varDeclaration )* (md= methodDeclaration )* '}' ;
	public final MJClass classDeclaration() throws RecognitionException {
		MJClass c = null;


		Token cname=null;
		Token scname=null;
		MJVariable vd =null;
		MJMethod md =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:30:3: ( 'class' cname= IDENT ( 'extends' scname= IDENT )? '{' (vd= varDeclaration )* (md= methodDeclaration )* '}' )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:30:5: 'class' cname= IDENT ( 'extends' scname= IDENT )? '{' (vd= varDeclaration )* (md= methodDeclaration )* '}'
			{
			 String superClass = "Object"; 
			      LinkedList<MJVariable> vdl = new LinkedList<MJVariable>();
			      LinkedList<MJMethod> mdl = new LinkedList<MJMethod>();
			    
			match(input,34,FOLLOW_34_in_classDeclaration126); 
			cname=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDeclaration132); 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:34:27: ( 'extends' scname= IDENT )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==36) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:34:29: 'extends' scname= IDENT
					{
					match(input,36,FOLLOW_36_in_classDeclaration136); 
					scname=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDeclaration142); 
					 superClass = (scname!=null?scname.getText():null); 
					}
					break;

			}

			match(input,49,FOLLOW_49_in_classDeclaration153); 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:35:9: (vd= varDeclaration )*
			loop3:
			while (true) {
				int alt3=2;
				switch ( input.LA(1) ) {
				case 33:
					{
					int LA3_2 = input.LA(2);
					if ( (LA3_2==IDENT) ) {
						int LA3_5 = input.LA(3);
						if ( (LA3_5==24) ) {
							alt3=1;
						}

					}

					}
					break;
				case 39:
					{
					int LA3_3 = input.LA(2);
					if ( (LA3_3==IDENT) ) {
						int LA3_5 = input.LA(3);
						if ( (LA3_5==24) ) {
							alt3=1;
						}

					}

					}
					break;
				case IDENT:
					{
					int LA3_4 = input.LA(2);
					if ( (LA3_4==IDENT) ) {
						int LA3_5 = input.LA(3);
						if ( (LA3_5==24) ) {
							alt3=1;
						}

					}

					}
					break;
				}
				switch (alt3) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:35:11: vd= varDeclaration
					{
					pushFollow(FOLLOW_varDeclaration_in_classDeclaration161);
					vd=varDeclaration();
					state._fsp--;

					 vd.setField();
					            vdl.add(vd);
					          
					}
					break;

				default :
					break loop3;
				}
			}

			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:40:9: (md= methodDeclaration )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==IDENT||LA4_0==33||LA4_0==39||LA4_0==42||LA4_0==44||LA4_0==47) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:40:11: md= methodDeclaration
					{
					pushFollow(FOLLOW_methodDeclaration_in_classDeclaration202);
					md=methodDeclaration();
					state._fsp--;

					 mdl.add(md);
					          
					}
					break;

				default :
					break loop4;
				}
			}

			match(input,50,FOLLOW_50_in_classDeclaration233); 

			      c = new MJClass((cname!=null?cname.getText():null), superClass, vdl, mdl);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return c;
	}
	// $ANTLR end "classDeclaration"



	// $ANTLR start "mainClass"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:50:1: mainClass returns [MJClass c] : 'class' cname= IDENT '{' 'public' 'static' 'void' 'main' '(' 'String[]' parname= IDENT ')' b= block '}' ;
	public final MJClass mainClass() throws RecognitionException {
		MJClass c = null;


		Token cname=null;
		Token parname=null;
		MJBlock b =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:51:3: ( 'class' cname= IDENT '{' 'public' 'static' 'void' 'main' '(' 'String[]' parname= IDENT ')' b= block '}' )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:51:5: 'class' cname= IDENT '{' 'public' 'static' 'void' 'main' '(' 'String[]' parname= IDENT ')' b= block '}'
			{
			match(input,34,FOLLOW_34_in_mainClass256); 
			cname=(Token)match(input,IDENT,FOLLOW_IDENT_in_mainClass262); 
			match(input,49,FOLLOW_49_in_mainClass269); 
			match(input,42,FOLLOW_42_in_mainClass278); 
			match(input,44,FOLLOW_44_in_mainClass280); 
			match(input,47,FOLLOW_47_in_mainClass282); 
			match(input,40,FOLLOW_40_in_mainClass284); 
			match(input,17,FOLLOW_17_in_mainClass286); 
			match(input,28,FOLLOW_28_in_mainClass288); 
			parname=(Token)match(input,IDENT,FOLLOW_IDENT_in_mainClass294); 
			match(input,18,FOLLOW_18_in_mainClass296); 
			pushFollow(FOLLOW_block_in_mainClass302);
			b=block();
			state._fsp--;

			match(input,50,FOLLOW_50_in_mainClass308); 

			      MJType partype = MJType.getArrayType(MJType.getClassType("String"));
			      MJVariable par = new MJVariable( partype, (parname!=null?parname.getText():null));
			      LinkedList<MJVariable> parlist = new LinkedList<MJVariable>();
			      parlist.add(par);
			     
			      MJMethod md = new MJMethod(MJType.getVoidType(), "main", parlist, b, true, true, true);
			      c = new MJClass((cname!=null?cname.getText():null), md);
			  
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return c;
	}
	// $ANTLR end "mainClass"



	// $ANTLR start "block"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:66:1: block returns [MJBlock b] : '{' (vd= varDeclaration )* (sd= statement )* '}' ;
	public final MJBlock block() throws RecognitionException {
		MJBlock b = null;


		MJVariable vd =null;
		MJStatement sd =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:67:3: ( '{' (vd= varDeclaration )* (sd= statement )* '}' )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:67:5: '{' (vd= varDeclaration )* (sd= statement )* '}'
			{
			  LinkedList<MJVariable> vdl = new LinkedList<MJVariable>();
			       LinkedList<MJStatement> sdl = new LinkedList<MJStatement>();    
			    
			match(input,49,FOLLOW_49_in_block339); 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:70:9: (vd= varDeclaration )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==IDENT) ) {
					int LA5_2 = input.LA(2);
					if ( (LA5_2==IDENT) ) {
						alt5=1;
					}

				}
				else if ( (LA5_0==33||LA5_0==39) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:70:11: vd= varDeclaration
					{
					pushFollow(FOLLOW_varDeclaration_in_block347);
					vd=varDeclaration();
					state._fsp--;

					 vdl.add(vd); 
					}
					break;

				default :
					break loop5;
				}
			}

			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:73:9: (sd= statement )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( ((LA6_0 >= COMMENT && LA6_0 <= IDENT)||(LA6_0 >= 29 && LA6_0 <= 30)||LA6_0==38||LA6_0==45||(LA6_0 >= 48 && LA6_0 <= 49)) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:73:11: sd= statement
					{
					pushFollow(FOLLOW_statement_in_block388);
					sd=statement();
					state._fsp--;

					 sdl.add(sd); 
					}
					break;

				default :
					break loop6;
				}
			}

			match(input,50,FOLLOW_50_in_block418); 

			       b = new MJBlock(vdl, sdl);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return b;
	}
	// $ANTLR end "block"



	// $ANTLR start "varDeclaration"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:82:1: varDeclaration returns [MJVariable vd] : t= type n= IDENT ';' ;
	public final MJVariable varDeclaration() throws RecognitionException {
		MJVariable vd = null;


		Token n=null;
		MJType t =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:83:3: (t= type n= IDENT ';' )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:83:5: t= type n= IDENT ';'
			{
			pushFollow(FOLLOW_type_in_varDeclaration449);
			t=type();
			state._fsp--;

			n=(Token)match(input,IDENT,FOLLOW_IDENT_in_varDeclaration455); 
			match(input,24,FOLLOW_24_in_varDeclaration458); 
			 vd = new MJVariable(t, (n!=null?n.getText():null));
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return vd;
	}
	// $ANTLR end "varDeclaration"



	// $ANTLR start "type"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:88:1: type returns [MJType t] : ( 'boolean' | 'int' | IDENT );
	public final MJType type() throws RecognitionException {
		MJType t = null;


		Token IDENT1=null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:89:3: ( 'boolean' | 'int' | IDENT )
			int alt7=3;
			switch ( input.LA(1) ) {
			case 33:
				{
				alt7=1;
				}
				break;
			case 39:
				{
				alt7=2;
				}
				break;
			case IDENT:
				{
				alt7=3;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 7, 0, input);
				throw nvae;
			}
			switch (alt7) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:89:5: 'boolean'
					{
					match(input,33,FOLLOW_33_in_type482); 
					 t = MJType.getBooleanType(); 
					}
					break;
				case 2 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:91:5: 'int'
					{
					match(input,39,FOLLOW_39_in_type494); 
					 t = MJType.getIntType(); 
					}
					break;
				case 3 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:93:5: IDENT
					{
					IDENT1=(Token)match(input,IDENT,FOLLOW_IDENT_in_type507); 
					 t = MJType.getClassType((IDENT1!=null?IDENT1.getText():null)); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return t;
	}
	// $ANTLR end "type"



	// $ANTLR start "methodDeclaration"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:97:1: methodDeclaration returns [MJMethod md] : ( 'public' )? ( 'static' )? mtype= procType mname= IDENT '(' (t1= type n1= IDENT ( ',' t2= type n2= IDENT )* )? ')' '{' (vd= varDeclaration )* (sd= statement )* 'return' retExp= optExpression ';' '}' ;
	public final MJMethod methodDeclaration() throws RecognitionException {
		MJMethod md = null;


		Token mname=null;
		Token n1=null;
		Token n2=null;
		MJType mtype =null;
		MJType t1 =null;
		MJType t2 =null;
		MJVariable vd =null;
		MJStatement sd =null;
		MJExpression retExp =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:98:3: ( ( 'public' )? ( 'static' )? mtype= procType mname= IDENT '(' (t1= type n1= IDENT ( ',' t2= type n2= IDENT )* )? ')' '{' (vd= varDeclaration )* (sd= statement )* 'return' retExp= optExpression ';' '}' )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:98:5: ( 'public' )? ( 'static' )? mtype= procType mname= IDENT '(' (t1= type n1= IDENT ( ',' t2= type n2= IDENT )* )? ')' '{' (vd= varDeclaration )* (sd= statement )* 'return' retExp= optExpression ';' '}'
			{
			 LinkedList<MJVariable> parlist = new LinkedList<MJVariable>(); 
			      boolean isStatic = false;
			      boolean isPublic = false;
			      LinkedList<MJVariable> vdl = new LinkedList<MJVariable>();
			      LinkedList<MJStatement> sdl = new LinkedList<MJStatement>();    
			    
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:104:5: ( 'public' )?
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==42) ) {
				alt8=1;
			}
			switch (alt8) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:104:7: 'public'
					{
					match(input,42,FOLLOW_42_in_methodDeclaration539); 
					 isPublic = true; 
					}
					break;

			}

			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:105:5: ( 'static' )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==44) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:105:7: 'static'
					{
					match(input,44,FOLLOW_44_in_methodDeclaration552); 
					 isStatic = true; 
					}
					break;

			}

			pushFollow(FOLLOW_procType_in_methodDeclaration567);
			mtype=procType();
			state._fsp--;

			mname=(Token)match(input,IDENT,FOLLOW_IDENT_in_methodDeclaration573); 
			match(input,17,FOLLOW_17_in_methodDeclaration580); 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:108:7: (t1= type n1= IDENT ( ',' t2= type n2= IDENT )* )?
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0==IDENT||LA11_0==33||LA11_0==39) ) {
				alt11=1;
			}
			switch (alt11) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:108:9: t1= type n1= IDENT ( ',' t2= type n2= IDENT )*
					{
					pushFollow(FOLLOW_type_in_methodDeclaration595);
					t1=type();
					state._fsp--;

					n1=(Token)match(input,IDENT,FOLLOW_IDENT_in_methodDeclaration601); 
					 parlist.add(new MJVariable(t1,(n1!=null?n1.getText():null))); 
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:110:9: ( ',' t2= type n2= IDENT )*
					loop10:
					while (true) {
						int alt10=2;
						int LA10_0 = input.LA(1);
						if ( (LA10_0==21) ) {
							alt10=1;
						}

						switch (alt10) {
						case 1 :
							// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:110:11: ',' t2= type n2= IDENT
							{
							match(input,21,FOLLOW_21_in_methodDeclaration624); 
							pushFollow(FOLLOW_type_in_methodDeclaration630);
							t2=type();
							state._fsp--;

							n2=(Token)match(input,IDENT,FOLLOW_IDENT_in_methodDeclaration636); 
							 parlist.add(new MJVariable(t2,(n2!=null?n2.getText():null))); 
							}
							break;

						default :
							break loop10;
						}
					}

					}
					break;

			}

			match(input,18,FOLLOW_18_in_methodDeclaration677); 
			match(input,49,FOLLOW_49_in_methodDeclaration684); 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:115:9: (vd= varDeclaration )*
			loop12:
			while (true) {
				int alt12=2;
				int LA12_0 = input.LA(1);
				if ( (LA12_0==IDENT) ) {
					int LA12_2 = input.LA(2);
					if ( (LA12_2==IDENT) ) {
						alt12=1;
					}

				}
				else if ( (LA12_0==33||LA12_0==39) ) {
					alt12=1;
				}

				switch (alt12) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:115:11: vd= varDeclaration
					{
					pushFollow(FOLLOW_varDeclaration_in_methodDeclaration692);
					vd=varDeclaration();
					state._fsp--;

					 vdl.add(vd); 
					}
					break;

				default :
					break loop12;
				}
			}

			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:118:9: (sd= statement )*
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( ((LA13_0 >= COMMENT && LA13_0 <= IDENT)||(LA13_0 >= 29 && LA13_0 <= 30)||LA13_0==38||LA13_0==45||(LA13_0 >= 48 && LA13_0 <= 49)) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:118:11: sd= statement
					{
					pushFollow(FOLLOW_statement_in_methodDeclaration733);
					sd=statement();
					state._fsp--;

					 sdl.add(sd); 
					}
					break;

				default :
					break loop13;
				}
			}

			match(input,43,FOLLOW_43_in_methodDeclaration767); 
			pushFollow(FOLLOW_optExpression_in_methodDeclaration773);
			retExp=optExpression();
			state._fsp--;

			match(input,24,FOLLOW_24_in_methodDeclaration775); 
			 
			          MJReturn r = new MJReturn(retExp);
			          sdl.add(r);
			        
			match(input,50,FOLLOW_50_in_methodDeclaration791); 

			      MJBlock b = new MJBlock(vdl, sdl);
			      md = new MJMethod(mtype, (mname!=null?mname.getText():null), parlist,  b, isStatic, isPublic);
			    
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return md;
	}
	// $ANTLR end "methodDeclaration"



	// $ANTLR start "procType"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:133:1: procType returns [MJType pt] : (t= type | 'void' );
	public final MJType procType() throws RecognitionException {
		MJType pt = null;


		MJType t =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:134:3: (t= type | 'void' )
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==IDENT||LA14_0==33||LA14_0==39) ) {
				alt14=1;
			}
			else if ( (LA14_0==47) ) {
				alt14=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}

			switch (alt14) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:134:5: t= type
					{
					pushFollow(FOLLOW_type_in_procType822);
					t=type();
					state._fsp--;

					 pt = t; 
					}
					break;
				case 2 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:136:5: 'void'
					{
					match(input,47,FOLLOW_47_in_procType832); 
					 pt = MJType.getVoidType(); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return pt;
	}
	// $ANTLR end "procType"



	// $ANTLR start "statement"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:140:1: statement returns [MJStatement s] : (c= comment |b= block | 'if' '(' condition= expression ')' thenblock= block ( 'else' elseblock= block )? | 'while' '(' condition= expression ')' whilebody= block |lhs= id '=' rhs= expression ';' |lhs= id '[' idx= expression ']' '=' rhs= expression ';' | 'System.out.println' '(' argument= expression ')' ';' | 'System.out.print' '(' argument= expression ')' ';' |methodname= id '(' (a1= expression ( ',' a2= expression )* )? ')' ';' );
	public final MJStatement statement() throws RecognitionException {
		MJStatement s = null;


		MJComment c =null;
		MJBlock b =null;
		MJExpression condition =null;
		MJBlock thenblock =null;
		MJBlock elseblock =null;
		MJBlock whilebody =null;
		MJIdentifier lhs =null;
		MJExpression rhs =null;
		MJExpression idx =null;
		MJExpression argument =null;
		MJIdentifier methodname =null;
		MJExpression a1 =null;
		MJExpression a2 =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:141:3: (c= comment |b= block | 'if' '(' condition= expression ')' thenblock= block ( 'else' elseblock= block )? | 'while' '(' condition= expression ')' whilebody= block |lhs= id '=' rhs= expression ';' |lhs= id '[' idx= expression ']' '=' rhs= expression ';' | 'System.out.println' '(' argument= expression ')' ';' | 'System.out.print' '(' argument= expression ')' ';' |methodname= id '(' (a1= expression ( ',' a2= expression )* )? ')' ';' )
			int alt18=9;
			switch ( input.LA(1) ) {
			case COMMENT:
				{
				alt18=1;
				}
				break;
			case 49:
				{
				alt18=2;
				}
				break;
			case 38:
				{
				alt18=3;
				}
				break;
			case 48:
				{
				alt18=4;
				}
				break;
			case IDENT:
				{
				switch ( input.LA(2) ) {
				case 26:
					{
					alt18=5;
					}
					break;
				case 23:
					{
					int LA18_10 = input.LA(3);
					if ( (LA18_10==IDENT) ) {
						switch ( input.LA(4) ) {
						case 26:
							{
							alt18=5;
							}
							break;
						case 31:
							{
							alt18=6;
							}
							break;
						case 17:
							{
							alt18=9;
							}
							break;
						default:
							int nvaeMark = input.mark();
							try {
								for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
									input.consume();
								}
								NoViableAltException nvae =
									new NoViableAltException("", 18, 13, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 18, 10, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

					}
					break;
				case 31:
					{
					alt18=6;
					}
					break;
				case 17:
					{
					alt18=9;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 18, 5, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case 45:
				{
				switch ( input.LA(2) ) {
				case 26:
					{
					alt18=5;
					}
					break;
				case 23:
					{
					int LA18_10 = input.LA(3);
					if ( (LA18_10==IDENT) ) {
						switch ( input.LA(4) ) {
						case 26:
							{
							alt18=5;
							}
							break;
						case 31:
							{
							alt18=6;
							}
							break;
						case 17:
							{
							alt18=9;
							}
							break;
						default:
							int nvaeMark = input.mark();
							try {
								for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
									input.consume();
								}
								NoViableAltException nvae =
									new NoViableAltException("", 18, 13, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 18, 10, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

					}
					break;
				case 31:
					{
					alt18=6;
					}
					break;
				case 17:
					{
					alt18=9;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 18, 6, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case 30:
				{
				alt18=7;
				}
				break;
			case 29:
				{
				alt18=8;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 18, 0, input);
				throw nvae;
			}
			switch (alt18) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:141:5: c= comment
					{
					pushFollow(FOLLOW_comment_in_statement859);
					c=comment();
					state._fsp--;

					 s = c; 
					}
					break;
				case 2 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:143:5: b= block
					{
					pushFollow(FOLLOW_block_in_statement875);
					b=block();
					state._fsp--;

					 s = b; 
					}
					break;
				case 3 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:145:5: 'if' '(' condition= expression ')' thenblock= block ( 'else' elseblock= block )?
					{
					match(input,38,FOLLOW_38_in_statement887); 
					match(input,17,FOLLOW_17_in_statement889); 
					pushFollow(FOLLOW_expression_in_statement895);
					condition=expression();
					state._fsp--;

					match(input,18,FOLLOW_18_in_statement897); 
					pushFollow(FOLLOW_block_in_statement903);
					thenblock=block();
					state._fsp--;


					      s = new MJIf(condition, thenblock);
					    
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:149:5: ( 'else' elseblock= block )?
					int alt15=2;
					int LA15_0 = input.LA(1);
					if ( (LA15_0==35) ) {
						alt15=1;
					}
					switch (alt15) {
						case 1 :
							// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:149:7: 'else' elseblock= block
							{
							match(input,35,FOLLOW_35_in_statement918); 
							pushFollow(FOLLOW_block_in_statement924);
							elseblock=block();
							state._fsp--;


							        s = new MJIfElse(condition, thenblock, elseblock);
							      
							}
							break;

					}

					}
					break;
				case 4 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:154:5: 'while' '(' condition= expression ')' whilebody= block
					{
					match(input,48,FOLLOW_48_in_statement946); 
					match(input,17,FOLLOW_17_in_statement948); 
					pushFollow(FOLLOW_expression_in_statement954);
					condition=expression();
					state._fsp--;

					match(input,18,FOLLOW_18_in_statement956); 
					pushFollow(FOLLOW_block_in_statement962);
					whilebody=block();
					state._fsp--;


					      s = new MJWhile(condition, whilebody);
					    
					}
					break;
				case 5 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:158:5: lhs= id '=' rhs= expression ';'
					{
					pushFollow(FOLLOW_id_in_statement978);
					lhs=id();
					state._fsp--;

					match(input,26,FOLLOW_26_in_statement980); 
					pushFollow(FOLLOW_expression_in_statement986);
					rhs=expression();
					state._fsp--;

					match(input,24,FOLLOW_24_in_statement988); 

					    s = new MJAssign(lhs, rhs);
					  
					}
					break;
				case 6 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:162:5: lhs= id '[' idx= expression ']' '=' rhs= expression ';'
					{
					pushFollow(FOLLOW_id_in_statement1002);
					lhs=id();
					state._fsp--;

					match(input,31,FOLLOW_31_in_statement1004); 
					pushFollow(FOLLOW_expression_in_statement1010);
					idx=expression();
					state._fsp--;

					match(input,32,FOLLOW_32_in_statement1012); 
					match(input,26,FOLLOW_26_in_statement1014); 
					pushFollow(FOLLOW_expression_in_statement1020);
					rhs=expression();
					state._fsp--;

					match(input,24,FOLLOW_24_in_statement1022); 

					    s = new MJAssign(new MJArray(lhs, idx), rhs);
					  
					}
					break;
				case 7 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:166:5: 'System.out.println' '(' argument= expression ')' ';'
					{
					match(input,30,FOLLOW_30_in_statement1032); 
					match(input,17,FOLLOW_17_in_statement1034); 
					pushFollow(FOLLOW_expression_in_statement1040);
					argument=expression();
					state._fsp--;

					match(input,18,FOLLOW_18_in_statement1042); 
					match(input,24,FOLLOW_24_in_statement1044); 

					      s = new MJPrintln(argument);
					    
					}
					break;
				case 8 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:170:5: 'System.out.print' '(' argument= expression ')' ';'
					{
					match(input,29,FOLLOW_29_in_statement1056); 
					match(input,17,FOLLOW_17_in_statement1058); 
					pushFollow(FOLLOW_expression_in_statement1064);
					argument=expression();
					state._fsp--;

					match(input,18,FOLLOW_18_in_statement1066); 
					match(input,24,FOLLOW_24_in_statement1068); 

					      s = new MJPrint(argument);
					    
					}
					break;
				case 9 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:174:5: methodname= id '(' (a1= expression ( ',' a2= expression )* )? ')' ';'
					{
					 LinkedList<MJExpression> argumentList = new LinkedList<MJExpression>(); 
					pushFollow(FOLLOW_id_in_statement1091);
					methodname=id();
					state._fsp--;

					match(input,17,FOLLOW_17_in_statement1093); 
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:176:5: (a1= expression ( ',' a2= expression )* )?
					int alt17=2;
					int LA17_0 = input.LA(1);
					if ( ((LA17_0 >= IDENT && LA17_0 <= INT)||LA17_0==STRING||LA17_0==15||LA17_0==17||LA17_0==22||LA17_0==37||LA17_0==41||(LA17_0 >= 45 && LA17_0 <= 46)) ) {
						alt17=1;
					}
					switch (alt17) {
						case 1 :
							// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:176:7: a1= expression ( ',' a2= expression )*
							{
							pushFollow(FOLLOW_expression_in_statement1106);
							a1=expression();
							state._fsp--;

							 argumentList.add(a1); 
							// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:178:7: ( ',' a2= expression )*
							loop16:
							while (true) {
								int alt16=2;
								int LA16_0 = input.LA(1);
								if ( (LA16_0==21) ) {
									alt16=1;
								}

								switch (alt16) {
								case 1 :
									// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:178:8: ',' a2= expression
									{
									match(input,21,FOLLOW_21_in_statement1125); 
									pushFollow(FOLLOW_expression_in_statement1131);
									a2=expression();
									state._fsp--;

									 argumentList.add(a2); 
									}
									break;

								default :
									break loop16;
								}
							}

							}
							break;

					}

					match(input,18,FOLLOW_18_in_statement1161); 
					match(input,24,FOLLOW_24_in_statement1163); 
					 s = new MJMethodCallStmt( methodname, argumentList  ); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return s;
	}
	// $ANTLR end "statement"



	// $ANTLR start "comment"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:185:1: comment returns [MJComment c] : COMMENT ;
	public final MJComment comment() throws RecognitionException {
		MJComment c = null;


		Token COMMENT2=null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:186:3: ( COMMENT )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:186:5: COMMENT
			{
			COMMENT2=(Token)match(input,COMMENT,FOLLOW_COMMENT_in_comment1191); 
			 c = new MJComment((COMMENT2!=null?COMMENT2.getText():null)); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return c;
	}
	// $ANTLR end "comment"



	// $ANTLR start "expression"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:190:1: expression returns [MJExpression res] : arg1= level1 ( '&&' arg2= level1 )* ;
	public final MJExpression expression() throws RecognitionException {
		MJExpression res = null;


		MJExpression arg1 =null;
		MJExpression arg2 =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:191:3: (arg1= level1 ( '&&' arg2= level1 )* )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:191:5: arg1= level1 ( '&&' arg2= level1 )*
			{
			pushFollow(FOLLOW_level1_in_expression1221);
			arg1=level1();
			state._fsp--;

			 res = arg1; 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:193:5: ( '&&' arg2= level1 )*
			loop19:
			while (true) {
				int alt19=2;
				int LA19_0 = input.LA(1);
				if ( (LA19_0==16) ) {
					alt19=1;
				}

				switch (alt19) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:193:7: '&&' arg2= level1
					{
					match(input,16,FOLLOW_16_in_expression1236); 
					pushFollow(FOLLOW_level1_in_expression1242);
					arg2=level1();
					state._fsp--;

					 MJAnd op = new MJAnd( res, arg2);
					        res = op;
					      
					}
					break;

				default :
					break loop19;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return res;
	}
	// $ANTLR end "expression"



	// $ANTLR start "level1"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:200:1: level1 returns [MJExpression res] : arg1= level2 ( '==' arg2= level2 )* ;
	public final MJExpression level1() throws RecognitionException {
		MJExpression res = null;


		MJExpression arg1 =null;
		MJExpression arg2 =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:201:3: (arg1= level2 ( '==' arg2= level2 )* )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:201:5: arg1= level2 ( '==' arg2= level2 )*
			{
			pushFollow(FOLLOW_level2_in_level11279);
			arg1=level2();
			state._fsp--;

			  res = arg1; 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:203:5: ( '==' arg2= level2 )*
			loop20:
			while (true) {
				int alt20=2;
				int LA20_0 = input.LA(1);
				if ( (LA20_0==27) ) {
					alt20=1;
				}

				switch (alt20) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:203:7: '==' arg2= level2
					{
					match(input,27,FOLLOW_27_in_level11293); 
					pushFollow(FOLLOW_level2_in_level11299);
					arg2=level2();
					state._fsp--;

					 MJEqual op = new MJEqual( res, arg2); 
					        res = op;
					      
					}
					break;

				default :
					break loop20;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return res;
	}
	// $ANTLR end "level1"



	// $ANTLR start "level2"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:210:1: level2 returns [MJExpression res] : arg1= level3 ( '<' arg2= level3 )* ;
	public final MJExpression level2() throws RecognitionException {
		MJExpression res = null;


		MJExpression arg1 =null;
		MJExpression arg2 =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:211:3: (arg1= level3 ( '<' arg2= level3 )* )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:211:5: arg1= level3 ( '<' arg2= level3 )*
			{
			pushFollow(FOLLOW_level3_in_level21338);
			arg1=level3();
			state._fsp--;

			  res = arg1; 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:213:5: ( '<' arg2= level3 )*
			loop21:
			while (true) {
				int alt21=2;
				int LA21_0 = input.LA(1);
				if ( (LA21_0==25) ) {
					alt21=1;
				}

				switch (alt21) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:213:7: '<' arg2= level3
					{
					match(input,25,FOLLOW_25_in_level21352); 
					pushFollow(FOLLOW_level3_in_level21358);
					arg2=level3();
					state._fsp--;

					 MJLess op = new MJLess( res, arg2 ); 
					        res = op;
					      
					}
					break;

				default :
					break loop21;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return res;
	}
	// $ANTLR end "level2"



	// $ANTLR start "level3"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:220:1: level3 returns [MJExpression res] : arg1= level4 ( ( '+' | '-' ) arg2= level4 )* ;
	public final MJExpression level3() throws RecognitionException {
		MJExpression res = null;


		MJExpression arg1 =null;
		MJExpression arg2 =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:221:3: (arg1= level4 ( ( '+' | '-' ) arg2= level4 )* )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:221:5: arg1= level4 ( ( '+' | '-' ) arg2= level4 )*
			{
			pushFollow(FOLLOW_level4_in_level31399);
			arg1=level4();
			state._fsp--;

			  res = arg1; 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:223:5: ( ( '+' | '-' ) arg2= level4 )*
			loop23:
			while (true) {
				int alt23=2;
				int LA23_0 = input.LA(1);
				if ( (LA23_0==20||LA23_0==22) ) {
					alt23=1;
				}

				switch (alt23) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:223:7: ( '+' | '-' ) arg2= level4
					{
					 MJBinaryOp op = null;
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:224:7: ( '+' | '-' )
					int alt22=2;
					int LA22_0 = input.LA(1);
					if ( (LA22_0==20) ) {
						alt22=1;
					}
					else if ( (LA22_0==22) ) {
						alt22=2;
					}

					else {
						NoViableAltException nvae =
							new NoViableAltException("", 22, 0, input);
						throw nvae;
					}

					switch (alt22) {
						case 1 :
							// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:224:9: '+'
							{
							match(input,20,FOLLOW_20_in_level31423); 
							 op = new MJPlus(); 
							}
							break;
						case 2 :
							// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:224:38: '-'
							{
							match(input,22,FOLLOW_22_in_level31429); 
							 op = new MJMinus(); 
							}
							break;

					}

					pushFollow(FOLLOW_level4_in_level31446);
					arg2=level4();
					state._fsp--;

					 op.setLeftOperand( res );
					        op.setRightOperand(arg2);
					        res = op;
					      
					}
					break;

				default :
					break loop23;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return res;
	}
	// $ANTLR end "level3"



	// $ANTLR start "level4"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:233:1: level4 returns [MJExpression res] : arg1= level5 ( '*' arg2= level5 )* ;
	public final MJExpression level4() throws RecognitionException {
		MJExpression res = null;


		MJExpression arg1 =null;
		MJExpression arg2 =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:234:3: (arg1= level5 ( '*' arg2= level5 )* )
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:234:6: arg1= level5 ( '*' arg2= level5 )*
			{
			pushFollow(FOLLOW_level5_in_level41484);
			arg1=level5();
			state._fsp--;

			  res = arg1; 
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:236:5: ( '*' arg2= level5 )*
			loop24:
			while (true) {
				int alt24=2;
				int LA24_0 = input.LA(1);
				if ( (LA24_0==19) ) {
					alt24=1;
				}

				switch (alt24) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:236:7: '*' arg2= level5
					{
					match(input,19,FOLLOW_19_in_level41498); 
					pushFollow(FOLLOW_level5_in_level41504);
					arg2=level5();
					state._fsp--;

					 MJMult op = new MJMult( res, arg2 ); 
					        res = op;
					      
					}
					break;

				default :
					break loop24;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return res;
	}
	// $ANTLR end "level4"



	// $ANTLR start "level5"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:243:1: level5 returns [MJExpression e] : ( '-' l= level5 | '!' l= level5 | 'new' 'int' '[' size= expression ']' | 'new' IDENT '(' (a1= expression ( ',' a2= expression )* )? ')' |i= id |i= id '[' idx= expression ']' |m= id '(' (a1= expression ( ',' a2= expression )* )? ')' | '(' argument= expression ')' | 'true' | 'false' | INT | STRING );
	public final MJExpression level5() throws RecognitionException {
		MJExpression e = null;


		Token IDENT3=null;
		Token INT4=null;
		Token STRING5=null;
		MJExpression l =null;
		MJExpression size =null;
		MJExpression a1 =null;
		MJExpression a2 =null;
		MJIdentifier i =null;
		MJExpression idx =null;
		MJIdentifier m =null;
		MJExpression argument =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:244:3: ( '-' l= level5 | '!' l= level5 | 'new' 'int' '[' size= expression ']' | 'new' IDENT '(' (a1= expression ( ',' a2= expression )* )? ')' |i= id |i= id '[' idx= expression ']' |m= id '(' (a1= expression ( ',' a2= expression )* )? ')' | '(' argument= expression ')' | 'true' | 'false' | INT | STRING )
			int alt29=12;
			switch ( input.LA(1) ) {
			case 22:
				{
				alt29=1;
				}
				break;
			case 15:
				{
				alt29=2;
				}
				break;
			case 41:
				{
				int LA29_3 = input.LA(2);
				if ( (LA29_3==39) ) {
					alt29=3;
				}
				else if ( (LA29_3==IDENT) ) {
					alt29=4;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 29, 3, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case IDENT:
				{
				switch ( input.LA(2) ) {
				case 16:
				case 18:
				case 19:
				case 20:
				case 21:
				case 22:
				case 24:
				case 25:
				case 27:
				case 32:
					{
					alt29=5;
					}
					break;
				case 23:
					{
					int LA29_14 = input.LA(3);
					if ( (LA29_14==IDENT) ) {
						switch ( input.LA(4) ) {
						case 16:
						case 18:
						case 19:
						case 20:
						case 21:
						case 22:
						case 24:
						case 25:
						case 27:
						case 32:
							{
							alt29=5;
							}
							break;
						case 31:
							{
							alt29=6;
							}
							break;
						case 17:
							{
							alt29=7;
							}
							break;
						default:
							int nvaeMark = input.mark();
							try {
								for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
									input.consume();
								}
								NoViableAltException nvae =
									new NoViableAltException("", 29, 17, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 29, 14, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

					}
					break;
				case 31:
					{
					alt29=6;
					}
					break;
				case 17:
					{
					alt29=7;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 29, 4, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case 45:
				{
				switch ( input.LA(2) ) {
				case 16:
				case 18:
				case 19:
				case 20:
				case 21:
				case 22:
				case 24:
				case 25:
				case 27:
				case 32:
					{
					alt29=5;
					}
					break;
				case 23:
					{
					int LA29_14 = input.LA(3);
					if ( (LA29_14==IDENT) ) {
						switch ( input.LA(4) ) {
						case 16:
						case 18:
						case 19:
						case 20:
						case 21:
						case 22:
						case 24:
						case 25:
						case 27:
						case 32:
							{
							alt29=5;
							}
							break;
						case 31:
							{
							alt29=6;
							}
							break;
						case 17:
							{
							alt29=7;
							}
							break;
						default:
							int nvaeMark = input.mark();
							try {
								for (int nvaeConsume = 0; nvaeConsume < 4 - 1; nvaeConsume++) {
									input.consume();
								}
								NoViableAltException nvae =
									new NoViableAltException("", 29, 17, input);
								throw nvae;
							} finally {
								input.rewind(nvaeMark);
							}
						}
					}

					else {
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 29, 14, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

					}
					break;
				case 31:
					{
					alt29=6;
					}
					break;
				case 17:
					{
					alt29=7;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 29, 5, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
				}
				break;
			case 17:
				{
				alt29=8;
				}
				break;
			case 46:
				{
				alt29=9;
				}
				break;
			case 37:
				{
				alt29=10;
				}
				break;
			case INT:
				{
				alt29=11;
				}
				break;
			case STRING:
				{
				alt29=12;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 29, 0, input);
				throw nvae;
			}
			switch (alt29) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:244:5: '-' l= level5
					{
					match(input,22,FOLLOW_22_in_level51539); 
					pushFollow(FOLLOW_level5_in_level51545);
					l=level5();
					state._fsp--;

					 e = new MJUnaryMinus(l); 
					}
					break;
				case 2 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:246:5: '!' l= level5
					{
					match(input,15,FOLLOW_15_in_level51557); 
					pushFollow(FOLLOW_level5_in_level51563);
					l=level5();
					state._fsp--;

					 e = new MJNegate(l); 
					}
					break;
				case 3 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:248:5: 'new' 'int' '[' size= expression ']'
					{
					match(input,41,FOLLOW_41_in_level51576); 
					match(input,39,FOLLOW_39_in_level51578); 
					match(input,31,FOLLOW_31_in_level51580); 
					pushFollow(FOLLOW_expression_in_level51586);
					size=expression();
					state._fsp--;

					match(input,32,FOLLOW_32_in_level51588); 
					 e = new MJNewArray(MJType.getIntType(), size);
					}
					break;
				case 4 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:250:5: 'new' IDENT '(' (a1= expression ( ',' a2= expression )* )? ')'
					{
					 LinkedList<MJExpression> arglist = new LinkedList<MJExpression>(); 
					match(input,41,FOLLOW_41_in_level51606); 
					IDENT3=(Token)match(input,IDENT,FOLLOW_IDENT_in_level51608); 
					match(input,17,FOLLOW_17_in_level51610); 
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:252:5: (a1= expression ( ',' a2= expression )* )?
					int alt26=2;
					int LA26_0 = input.LA(1);
					if ( ((LA26_0 >= IDENT && LA26_0 <= INT)||LA26_0==STRING||LA26_0==15||LA26_0==17||LA26_0==22||LA26_0==37||LA26_0==41||(LA26_0 >= 45 && LA26_0 <= 46)) ) {
						alt26=1;
					}
					switch (alt26) {
						case 1 :
							// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:252:7: a1= expression ( ',' a2= expression )*
							{
							pushFollow(FOLLOW_expression_in_level51622);
							a1=expression();
							state._fsp--;

							 arglist.add(a1); 
							// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:254:7: ( ',' a2= expression )*
							loop25:
							while (true) {
								int alt25=2;
								int LA25_0 = input.LA(1);
								if ( (LA25_0==21) ) {
									alt25=1;
								}

								switch (alt25) {
								case 1 :
									// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:254:8: ',' a2= expression
									{
									match(input,21,FOLLOW_21_in_level51641); 
									pushFollow(FOLLOW_expression_in_level51647);
									a2=expression();
									state._fsp--;

									 arglist.add(a2); 
									}
									break;

								default :
									break loop25;
								}
							}

							}
							break;

					}

					match(input,18,FOLLOW_18_in_level51682); 
					 e = new MJNew(MJType.getClassType((IDENT3!=null?IDENT3.getText():null)), arglist); 
					}
					break;
				case 5 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:260:5: i= id
					{
					pushFollow(FOLLOW_id_in_level51698);
					i=id();
					state._fsp--;

					 e = i; 
					}
					break;
				case 6 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:262:5: i= id '[' idx= expression ']'
					{
					pushFollow(FOLLOW_id_in_level51714);
					i=id();
					state._fsp--;

					match(input,31,FOLLOW_31_in_level51716); 
					pushFollow(FOLLOW_expression_in_level51722);
					idx=expression();
					state._fsp--;

					match(input,32,FOLLOW_32_in_level51724); 
					 e = new MJArray(i, idx); 
					}
					break;
				case 7 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:264:5: m= id '(' (a1= expression ( ',' a2= expression )* )? ')'
					{
					 LinkedList<MJExpression> arglist = new LinkedList<MJExpression>(); 
					pushFollow(FOLLOW_id_in_level51747);
					m=id();
					state._fsp--;

					match(input,17,FOLLOW_17_in_level51749); 
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:266:5: (a1= expression ( ',' a2= expression )* )?
					int alt28=2;
					int LA28_0 = input.LA(1);
					if ( ((LA28_0 >= IDENT && LA28_0 <= INT)||LA28_0==STRING||LA28_0==15||LA28_0==17||LA28_0==22||LA28_0==37||LA28_0==41||(LA28_0 >= 45 && LA28_0 <= 46)) ) {
						alt28=1;
					}
					switch (alt28) {
						case 1 :
							// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:266:7: a1= expression ( ',' a2= expression )*
							{
							pushFollow(FOLLOW_expression_in_level51762);
							a1=expression();
							state._fsp--;

							 arglist.add(a1); 
							// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:268:7: ( ',' a2= expression )*
							loop27:
							while (true) {
								int alt27=2;
								int LA27_0 = input.LA(1);
								if ( (LA27_0==21) ) {
									alt27=1;
								}

								switch (alt27) {
								case 1 :
									// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:268:8: ',' a2= expression
									{
									match(input,21,FOLLOW_21_in_level51781); 
									pushFollow(FOLLOW_expression_in_level51787);
									a2=expression();
									state._fsp--;

									 arglist.add(a2); 
									}
									break;

								default :
									break loop27;
								}
							}

							}
							break;

					}

					match(input,18,FOLLOW_18_in_level51817); 
					 e = new MJMethodCallExpr(m, arglist); 
					}
					break;
				case 8 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:273:5: '(' argument= expression ')'
					{
					match(input,17,FOLLOW_17_in_level51833); 
					pushFollow(FOLLOW_expression_in_level51839);
					argument=expression();
					state._fsp--;

					match(input,18,FOLLOW_18_in_level51841); 
					 e = new MJParentheses(argument); 
					}
					break;
				case 9 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:275:5: 'true'
					{
					match(input,46,FOLLOW_46_in_level51853); 
					 e = MJBoolean.True; 
					}
					break;
				case 10 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:277:5: 'false'
					{
					match(input,37,FOLLOW_37_in_level51866); 
					 e = MJBoolean.False; 
					}
					break;
				case 11 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:279:5: INT
					{
					INT4=(Token)match(input,INT,FOLLOW_INT_in_level51879); 
					 e = new MJInteger((INT4!=null?INT4.getText():null)); 
					}
					break;
				case 12 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:281:5: STRING
					{
					STRING5=(Token)match(input,STRING,FOLLOW_STRING_in_level51892); 
					 e = new MJString((STRING5!=null?STRING5.getText():null)); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return e;
	}
	// $ANTLR end "level5"



	// $ANTLR start "id"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:285:3: id returns [MJIdentifier e] : ( IDENT | 'this' |t= thisid '.' IDENT );
	public final MJIdentifier id() throws RecognitionException {
		MJIdentifier e = null;


		Token IDENT6=null;
		Token IDENT7=null;
		MJIdentifier t =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:286:3: ( IDENT | 'this' |t= thisid '.' IDENT )
			int alt30=3;
			int LA30_0 = input.LA(1);
			if ( (LA30_0==IDENT) ) {
				int LA30_1 = input.LA(2);
				if ( ((LA30_1 >= 16 && LA30_1 <= 22)||(LA30_1 >= 24 && LA30_1 <= 27)||(LA30_1 >= 31 && LA30_1 <= 32)) ) {
					alt30=1;
				}
				else if ( (LA30_1==23) ) {
					alt30=3;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 30, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}
			else if ( (LA30_0==45) ) {
				int LA30_2 = input.LA(2);
				if ( ((LA30_2 >= 16 && LA30_2 <= 22)||(LA30_2 >= 24 && LA30_2 <= 27)||(LA30_2 >= 31 && LA30_2 <= 32)) ) {
					alt30=2;
				}
				else if ( (LA30_2==23) ) {
					alt30=3;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 30, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 30, 0, input);
				throw nvae;
			}

			switch (alt30) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:286:5: IDENT
					{
					IDENT6=(Token)match(input,IDENT,FOLLOW_IDENT_in_id1920); 
					 e = new MJIdentifier((IDENT6!=null?IDENT6.getText():null));
					}
					break;
				case 2 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:288:5: 'this'
					{
					match(input,45,FOLLOW_45_in_id1932); 
					 e= new MJIdentifier("this"); 
					}
					break;
				case 3 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:290:5: t= thisid '.' IDENT
					{
					pushFollow(FOLLOW_thisid_in_id1948);
					t=thisid();
					state._fsp--;

					match(input,23,FOLLOW_23_in_id1950); 
					IDENT7=(Token)match(input,IDENT,FOLLOW_IDENT_in_id1952); 
					 
					      MJIdentifier i = new MJIdentifier((IDENT7!=null?IDENT7.getText():null));
					      e = new MJSelector(t,i);
					    
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return e;
	}
	// $ANTLR end "id"



	// $ANTLR start "thisid"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:297:1: thisid returns [MJIdentifier e] : ( 'this' | IDENT );
	public final MJIdentifier thisid() throws RecognitionException {
		MJIdentifier e = null;


		Token IDENT8=null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:298:3: ( 'this' | IDENT )
			int alt31=2;
			int LA31_0 = input.LA(1);
			if ( (LA31_0==45) ) {
				alt31=1;
			}
			else if ( (LA31_0==IDENT) ) {
				alt31=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 31, 0, input);
				throw nvae;
			}

			switch (alt31) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:298:5: 'this'
					{
					match(input,45,FOLLOW_45_in_thisid1975); 
					 e= new MJIdentifier("this"); 
					}
					break;
				case 2 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:300:5: IDENT
					{
					IDENT8=(Token)match(input,IDENT,FOLLOW_IDENT_in_thisid1988); 
					 e = new MJIdentifier((IDENT8!=null?IDENT8.getText():null));
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return e;
	}
	// $ANTLR end "thisid"



	// $ANTLR start "optExpression"
	// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:304:1: optExpression returns [MJExpression e] : (ex= expression |);
	public final MJExpression optExpression() throws RecognitionException {
		MJExpression e = null;


		MJExpression ex =null;

		try {
			// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:305:3: (ex= expression |)
			int alt32=2;
			int LA32_0 = input.LA(1);
			if ( ((LA32_0 >= IDENT && LA32_0 <= INT)||LA32_0==STRING||LA32_0==15||LA32_0==17||LA32_0==22||LA32_0==37||LA32_0==41||(LA32_0 >= 45 && LA32_0 <= 46)) ) {
				alt32=1;
			}
			else if ( (LA32_0==24) ) {
				alt32=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 32, 0, input);
				throw nvae;
			}

			switch (alt32) {
				case 1 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:305:5: ex= expression
					{
					pushFollow(FOLLOW_expression_in_optExpression2017);
					ex=expression();
					state._fsp--;

					 e = ex; 
					}
					break;
				case 2 :
					// /Users/probst/workspace/MiniJava_A6/src/compiler/Frontend/MiniJava.g:308:5: 
					{
					 e = new MJNoExpression(); 
					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return e;
	}
	// $ANTLR end "optExpression"

	// Delegated rules



	public static final BitSet FOLLOW_mainClass_in_program52 = new BitSet(new long[]{0x0000000400000002L});
	public static final BitSet FOLLOW_classDeclaration_in_program74 = new BitSet(new long[]{0x0000000400000002L});
	public static final BitSet FOLLOW_34_in_classDeclaration126 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_IDENT_in_classDeclaration132 = new BitSet(new long[]{0x0002001000000000L});
	public static final BitSet FOLLOW_36_in_classDeclaration136 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_IDENT_in_classDeclaration142 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_classDeclaration153 = new BitSet(new long[]{0x0004948200000040L});
	public static final BitSet FOLLOW_varDeclaration_in_classDeclaration161 = new BitSet(new long[]{0x0004948200000040L});
	public static final BitSet FOLLOW_methodDeclaration_in_classDeclaration202 = new BitSet(new long[]{0x0004948200000040L});
	public static final BitSet FOLLOW_50_in_classDeclaration233 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_34_in_mainClass256 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_IDENT_in_mainClass262 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_mainClass269 = new BitSet(new long[]{0x0000040000000000L});
	public static final BitSet FOLLOW_42_in_mainClass278 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_mainClass280 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_mainClass282 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_mainClass284 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_mainClass286 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_28_in_mainClass288 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_IDENT_in_mainClass294 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_mainClass296 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_block_in_mainClass302 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_mainClass308 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_49_in_block339 = new BitSet(new long[]{0x000720C260000060L});
	public static final BitSet FOLLOW_varDeclaration_in_block347 = new BitSet(new long[]{0x000720C260000060L});
	public static final BitSet FOLLOW_statement_in_block388 = new BitSet(new long[]{0x0007204060000060L});
	public static final BitSet FOLLOW_50_in_block418 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_varDeclaration449 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_IDENT_in_varDeclaration455 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_varDeclaration458 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_33_in_type482 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_39_in_type494 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_type507 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_42_in_methodDeclaration539 = new BitSet(new long[]{0x0000908200000040L});
	public static final BitSet FOLLOW_44_in_methodDeclaration552 = new BitSet(new long[]{0x0000808200000040L});
	public static final BitSet FOLLOW_procType_in_methodDeclaration567 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_IDENT_in_methodDeclaration573 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_methodDeclaration580 = new BitSet(new long[]{0x0000008200040040L});
	public static final BitSet FOLLOW_type_in_methodDeclaration595 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_IDENT_in_methodDeclaration601 = new BitSet(new long[]{0x0000000000240000L});
	public static final BitSet FOLLOW_21_in_methodDeclaration624 = new BitSet(new long[]{0x0000008200000040L});
	public static final BitSet FOLLOW_type_in_methodDeclaration630 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_IDENT_in_methodDeclaration636 = new BitSet(new long[]{0x0000000000240000L});
	public static final BitSet FOLLOW_18_in_methodDeclaration677 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_methodDeclaration684 = new BitSet(new long[]{0x000328C260000060L});
	public static final BitSet FOLLOW_varDeclaration_in_methodDeclaration692 = new BitSet(new long[]{0x000328C260000060L});
	public static final BitSet FOLLOW_statement_in_methodDeclaration733 = new BitSet(new long[]{0x0003284060000060L});
	public static final BitSet FOLLOW_43_in_methodDeclaration767 = new BitSet(new long[]{0x00006220014290C0L});
	public static final BitSet FOLLOW_optExpression_in_methodDeclaration773 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_methodDeclaration775 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_methodDeclaration791 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_type_in_procType822 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_procType832 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_comment_in_statement859 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_block_in_statement875 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_38_in_statement887 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_statement889 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_statement895 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_statement897 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_block_in_statement903 = new BitSet(new long[]{0x0000000800000002L});
	public static final BitSet FOLLOW_35_in_statement918 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_block_in_statement924 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_48_in_statement946 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_statement948 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_statement954 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_statement956 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_block_in_statement962 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_id_in_statement978 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_26_in_statement980 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_statement986 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_statement988 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_id_in_statement1002 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_statement1004 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_statement1010 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_statement1012 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_26_in_statement1014 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_statement1020 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_statement1022 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_30_in_statement1032 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_statement1034 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_statement1040 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_statement1042 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_statement1044 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_29_in_statement1056 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_statement1058 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_statement1064 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_statement1066 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_statement1068 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_id_in_statement1091 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_statement1093 = new BitSet(new long[]{0x00006220004690C0L});
	public static final BitSet FOLLOW_expression_in_statement1106 = new BitSet(new long[]{0x0000000000240000L});
	public static final BitSet FOLLOW_21_in_statement1125 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_statement1131 = new BitSet(new long[]{0x0000000000240000L});
	public static final BitSet FOLLOW_18_in_statement1161 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_24_in_statement1163 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COMMENT_in_comment1191 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_level1_in_expression1221 = new BitSet(new long[]{0x0000000000010002L});
	public static final BitSet FOLLOW_16_in_expression1236 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_level1_in_expression1242 = new BitSet(new long[]{0x0000000000010002L});
	public static final BitSet FOLLOW_level2_in_level11279 = new BitSet(new long[]{0x0000000008000002L});
	public static final BitSet FOLLOW_27_in_level11293 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_level2_in_level11299 = new BitSet(new long[]{0x0000000008000002L});
	public static final BitSet FOLLOW_level3_in_level21338 = new BitSet(new long[]{0x0000000002000002L});
	public static final BitSet FOLLOW_25_in_level21352 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_level3_in_level21358 = new BitSet(new long[]{0x0000000002000002L});
	public static final BitSet FOLLOW_level4_in_level31399 = new BitSet(new long[]{0x0000000000500002L});
	public static final BitSet FOLLOW_20_in_level31423 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_22_in_level31429 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_level4_in_level31446 = new BitSet(new long[]{0x0000000000500002L});
	public static final BitSet FOLLOW_level5_in_level41484 = new BitSet(new long[]{0x0000000000080002L});
	public static final BitSet FOLLOW_19_in_level41498 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_level5_in_level41504 = new BitSet(new long[]{0x0000000000080002L});
	public static final BitSet FOLLOW_22_in_level51539 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_level5_in_level51545 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_15_in_level51557 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_level5_in_level51563 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_41_in_level51576 = new BitSet(new long[]{0x0000008000000000L});
	public static final BitSet FOLLOW_39_in_level51578 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_level51580 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_level51586 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_level51588 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_41_in_level51606 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_IDENT_in_level51608 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_level51610 = new BitSet(new long[]{0x00006220004690C0L});
	public static final BitSet FOLLOW_expression_in_level51622 = new BitSet(new long[]{0x0000000000240000L});
	public static final BitSet FOLLOW_21_in_level51641 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_level51647 = new BitSet(new long[]{0x0000000000240000L});
	public static final BitSet FOLLOW_18_in_level51682 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_id_in_level51698 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_id_in_level51714 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_31_in_level51716 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_level51722 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_32_in_level51724 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_id_in_level51747 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_level51749 = new BitSet(new long[]{0x00006220004690C0L});
	public static final BitSet FOLLOW_expression_in_level51762 = new BitSet(new long[]{0x0000000000240000L});
	public static final BitSet FOLLOW_21_in_level51781 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_level51787 = new BitSet(new long[]{0x0000000000240000L});
	public static final BitSet FOLLOW_18_in_level51817 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_17_in_level51833 = new BitSet(new long[]{0x00006220004290C0L});
	public static final BitSet FOLLOW_expression_in_level51839 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_level51841 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_46_in_level51853 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_37_in_level51866 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_level51879 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_in_level51892 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_id1920 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_45_in_id1932 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_thisid_in_id1948 = new BitSet(new long[]{0x0000000000800000L});
	public static final BitSet FOLLOW_23_in_id1950 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_IDENT_in_id1952 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_45_in_thisid1975 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_IDENT_in_thisid1988 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expression_in_optExpression2017 = new BitSet(new long[]{0x0000000000000002L});
}
