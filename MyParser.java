// Code by Angelo Gomez, 1535298
// CSCI 4355

import java.util.ArrayList;
import java.util.Collections;

public class MyParser{
	//Token Storage
	private ArrayList<MyTokens> myToks = new ArrayList<MyTokens>();
	
	// Contains the list of unique variables that were found in the parser
	private ArrayList<String> symTab = new ArrayList<String>();
	
	private MyTokens currTok;
	private int tokCount; // Always points at the next token.
	private int nodeCount;
	
	//Keywords
	/* begin, end, then, input, output, and loop are all not used in the given grammar
	but will remain in the keywords until the document is updated.
	cout, cin, repeat, and until were added as they are used in the given grammar.*/
	public static String[] keywords =  {"program", "begin", "end", "if", "then", "else", 
	"input", "output", "float", "while", "cout", "cin", "loop", "repeat", "until"};	
	
	public MyParser(ArrayList<MyTokens> t, ArrayList<String> st){
		this.myToks = t;
		this.symTab = st;
		this.tokCount = 0;
		this.nodeCount = 1;
		currTok = myToks.get(tokCount);
		tokCount +=1;
	}
	
	public void parseProgram(){
		int totalToks = myToks.size();
		r1();
		/*do{
			r1();
		}while(tokCount < totalToks);*/
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
				//getNextTok();
				r6();
				
				if (currTok.getTID() == 21){
					System.out.println("Parser should be done here.");
				}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "}");}
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "{");}
			
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "program");}
		
		
		
	}
	
	// Rule 2 -> DECL_SEC
	private void r2(){
		r3();
		
	}
	
	// Rule 3 -> DECL
	private void r3(){
		r4();
		if (currTok.getTID() == 25){
			getNextTok();
			if(currTok.getLexeme().equals("Float")){
				getNextTok();
				if(currTok.getTID() == 26){
					getNextTok();
				}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ";");}
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "Float");}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ":");}
	}
	
	// Rule 4 -> ID_LIST
	private void r4(){
		r5();
		while(currTok.getTID() == 24){
			getNextTok();
			r5();
		}
	}
	
	// Rule 5 -> ID
	private void r5(){
		if (currTok.getLexeme().matches("[a-zA-Z]+")){
			if (symTab.get(0).equals(currTok.getLexeme())){
				Collections.rotate(symTab, -1); // rotate symbol table, this means that if a duplicate is found in the declaration it will error.
				getNextTok();
			}else{MyErrorHandler.alreadyDecSymbErr(currTok.getLineNum(), currTok.getLexeme());}
		}else{MyErrorHandler.illegalSymbolErr(currTok.getLineNum());}	
	}

	// Rule 6 -> STMT_SEC
	private void r6(){
		
	}
	
	// Rule 7 -> STMT
	public void r7(){
		
	}
	
	// Rule 8 -> ASSIGN
	public void r8(){
		
	}
	
	// Rule 9 -> IFSTMT
	public void r9(){
		
	}
	
	// Rule 10 -> WHILESTMT
	public void r10(){
		
	}
	
	// Rule 11 -> REPEAT
	public void r11(){
		
	}
	
	// Rule 12 -> INPUT
	public void r12(){
		
	}
	
	// Rule 13 -> OUTPUT
	public void r13(){
		
	}
	
	// Rule 14 -> EXPR
	public void r14(){
		
	}
	
	// Rule 15 -> FACTOR
	public void r15(){
		
	}
	
	// Rule 16 -> OPERAND
	public void r16(){
		
	}
	
	// Rule 17 -> FLOAT
	public void r17(){
		
	}
	
	// Rule 18 -> COMP
	public void r18(){
		
	}


}

