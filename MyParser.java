// Code by Angelo Gomez, 1535298
// CSCI 4355

import java.util.ArrayList;

public class MyParser{
	//Token Storage
	private ArrayList<MyTokens> myToks = new ArrayList<MyTokens>();
	
	// Symbol Table is one dimensional since all variables are floats.
	private ArrayList<String> symTab = new ArrayList<String>();
	
	private MyTokens currTok;
	private int tokCount;
	private int lineNum;
	
	public MyParser(ArrayList<MyTokens> t, ArrayList<String> st){
		this.myToks = t;
		this.symTab = st;
		this.tokCount = 0;
		this.lineNum = 0;
		currTok = myToks.get(tokCount);
		tokCount +=1;
	}
	
	public void parseProgram(){
		getNextTok();
		int totalToks = myToks.size();
		/*do{
			r1();
		}while(tokCount < totalToks);*/
		
	
	}
	
	// sepearte into skip newlines and getNextTok
	public void getNextTok(){
		// maybe replace this with id variables
		while(currTok.getTID() == 9){
			System.out.println(currTok);
			currTok = myToks.get(tokCount);
			tokCount += 1;
			lineNum += 1;
		}
	}
	
	
	
	// Functions to cover the grammar rules
	
	// Rule 1 -> PROGRAM
	public void r1(){
		
	}
	
	// Rule 2 -> DECL_SEC
	public void r2(){
		
	}
	
	// Rule 3 -> DECL
	public void r3(){
		
	}
	
	// Rule 4 -> ID_LIST
	public void r4(){
		
	}
	
	// Rule 5 -> ID
	public void r5(){
		
	}

	// Rule 6 -> STMT_SEC
	public void r6(){
		
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

