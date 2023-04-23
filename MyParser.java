// Code by Angelo Gomez, 1535298
// CSCI 4355

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class MyParser{
	//Token Storage
	private ArrayList<MyTokens> myToks = new ArrayList<MyTokens>();
	
	// Contains the list of unique variables that were found in the parser
	// private ArrayList<String> symTab = new ArrayList<String>();
	// parser is the one that puts stuff in the symbol table maybe use MySymbols
	private ArrayList<MySymbols> symTab = new ArrayList<MySymbols>();
	
	private MyTokens currTok;
	private int tokCount; // Always points at the next token.
	private int nodeCount;
	
	//Keywords
	/* begin, end, then, input, output, and loop are all not used in the given grammar
	but will remain in the keywords until the document is updated.
	cout, cin, repeat, and until were added as they are used in the given grammar.*/
	public static String[] keywords =  {"program", "begin", "end", "if", "then", "else", 
	"input", "output", "float", "while", "cout", "cin", "loop", "repeat", "until"};	
	
	public MyParser(ArrayList<MyTokens> t){
		this.myToks = t;
		this.tokCount = 0;
		this.nodeCount = 1;
		currTok = myToks.get(tokCount);
		tokCount +=1;
	}
	
	public void parseProgram(){
		int totalToks = myToks.size();
		r1();
		getNextTok();
		if (currTok.getTID() != -1 && currTok.getLexeme().equals("EOF")){
			MyErrorHandler.expectedEOFErr(currTok.getLineNum(), currTok.getLexeme());
		}
	}
	
	private void getNextTok(){
		System.out.println(currTok);
		currTok = myToks.get(tokCount);
		tokCount += 1;
	}
		
	// Functions to cover the grammar rules
	
	// Rule 1 -> PROGRAM
	private void r1(){
		if (currTok.getLexeme().equals("program") && nodeCount == 1){
			getNextTok();
			if (currTok.getTID() == 20){
				getNextTok();
				r2();
				r6();
				if (currTok.getTID() == 21){
				}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "}", currTok.getLexeme());}
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "{", currTok.getLexeme());}
			
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "program", currTok.getLexeme());}
		
		
		
	}
	
	// Rule 2 -> DECL_SEC
	private void r2(){
		r3();
		if(!(Arrays.asList(keywords).contains(currTok.getLexeme()) || myToks.get(tokCount).getTID() == 10)){
			r2();
		}
	}
	
	// Rule 3 -> DECL
	private void r3(){
		r4(1);
		if (currTok.getTID() == 25){
			getNextTok();
			if(currTok.getLexeme().equals("float")){
				getNextTok();
				if(currTok.getTID() == 26){
					getNextTok();
				}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ";", currTok.getLexeme());}
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "Float", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ":", currTok.getLexeme());}
	}
	
	// Rule 4 -> ID_LIST
	/*
	Rule 5 has different modes based on where it is called from.
	Rule 4 will also have a mode parameter because it is used as an intemediary 
	for calling Rule 5.
	Available modes:
	1 = Declaration Section
	2 = Statement Section
	*/
	private void r4(int mode){
		r5(mode);
		if(currTok.getTID() == 24){
			getNextTok();
			r4(mode);
		}
	}
	
	// find way to handle dupes in symbol table without it affecting other calls to ID rule
	// Rule 5 -> ID
	private void r5(int mode){
		if (mode == 1){
			if ()
		}
		
		/*if (currTok.getLexeme().matches("[a-zA-Z]+")){
			getNextTok();
			/*
			if (symTab.contains(currTok.getLexeme())){
				getNextTok();
			}else{MyErrorHandler.alreadyDecSymbErr(currTok.getLineNum(), currTok.getLexeme());}
			
		}else{MyErrorHandler.illegalSymbolErr(currTok.getLineNum());}	*/
	}

	// Rule 6 -> STMT_SEC
	private void r6(){
		r7();
		if (currTok.getTID() != 21){
			r6();
		}
		
	}
	
	// Rule 7 -> STMT
	private void r7(){
		//if (!Arrays.asList(keywords).contains(currTok.getLexeme())){r8();}
		if (currTok.getLexeme().equals("if")){r9();}
		else if (currTok.getLexeme().equals("while")){r10();}
		else if (currTok.getLexeme().equals("repeat")){r11();}
		else if (currTok.getLexeme().equals("cin")){r12();}
		else if (currTok.getLexeme().equals("cout")){r13();}
		else{r8();} // Unless there is some unexpected error I cant think of this should work.
		// Add better comment later here.
	}
	
	// Rule 8 -> ASSIGN
	private void r8(){
		r5();
		if (currTok.getTID() == 10){
			getNextTok();
			r14();
			if (currTok.getTID() == 26){
				getNextTok();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ";", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ":=", currTok.getLexeme());}
	}
	
	// Rule 9 -> IFSTMT
	public void r9(){
		getNextTok();
		
	}
	
	// Rule 10 -> WHILESTMT
	public void r10(){
		getNextTok();
		
	}
	
	// Rule 11 -> REPEAT
	public void r11(){
		getNextTok();
		
	}
	
	// Rule 12 -> INPUT
	private void r12(){
		getNextTok();
		if (currTok.getTID() == 27){
			getNextTok();
			r4();
			if(currTok.getTID() == 26){
				getNextTok();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ";", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "<<", currTok.getLexeme());}
	}
	
	// Rule 13 -> OUTPUT
	private void r13(){
		getNextTok();
		if (currTok.getTID() == 28){
			getNextTok();
			r4();
			if(currTok.getTID() == 26){
				getNextTok();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ";", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ">>", currTok.getLexeme());}
		
	}
	
	// Rule 14 -> EXPR
	private void r14(){
		r15();
		while(currTok.getTID() == 11 || currTok.getTID() == 12){
			getNextTok();
			r14();
		}
		
	}
	
	// Rule 15 -> FACTOR
	private void r15(){
		r16();
		while(currTok.getTID() == 13 || currTok.getTID() == 14 || currTok.getTID() == 15){
			getNextTok();
			r15();
		}	
	}
	
	// Rule 16 -> OPERAND
	private void r16(){
		// FLOAT
		if (Character.isDigit(currTok.getLexeme().charAt(0))){
			r17();
		}
		// ID
		else if (Character.isAlphabetic(currTok.getLexeme().charAt(0))){
			r5();
		}
		// (EXPR)
		else{ 
			if (currTok.getTID() == 22){
				getNextTok();
				r14();
				if (currTok.getTID() == 23){
					getNextTok();
				}else {MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ")", currTok.getLexeme());}
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "(", currTok.getLexeme());}
		}
	}
	
	// Rule 17 -> FLOAT
	/* 
	This function's existence is compulsory because it 
	is a requirement for the project. The scanner already
	guarantees that the floating point digit will be properly formatted.
	*/
	private void r17(){
		try{
			float y = Float.parseFloat(currTok.getLexeme());
			getNextTok();
		}
		catch(NumberFormatException e){
			MyErrorHandler.illegalSymbolErr(currTok.getLineNum());
		}
		
		
	}
	
	// Rule 18 -> COMP
	private void r18(){
		
	}


}

