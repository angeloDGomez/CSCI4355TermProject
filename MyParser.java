// Code by Angelo Gomez, 1535298
// CSCI 4355

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class MyParser{
	//Token Storage
	private ArrayList<MyTokens> myToks = new ArrayList<MyTokens>();
	
	// Will contain the list of unique variables that were found in the parser
	private ArrayList<String> symTab = new ArrayList<String>();
	
	private MyTokens currTok;
	private int tokCount; // Always points at the next token.
	private int nodeCount;
	
	private MyTree parseTree;
	
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
		parseTree = new MyTree();
	}
	
	public void parseProgram(){
		int totalToks = myToks.size();
		r1();
		getNextTok();
		if (currTok.getTID() != -1 && !(currTok.getLexeme().equals("EOF"))){
			MyErrorHandler.expectedEOFErr(currTok.getLineNum(), currTok.getLexeme());
		}
		System.out.println("The following is the parse tree for the selected program:\n");
		System.out.println(parseTree);
	}
	
	private void getNextTok(){
		currTok = myToks.get(tokCount);
		tokCount += 1;
	}
		
	// Functions to cover the grammar rules
	
	// Rule 1 -> PROGRAM
	private void r1(){
		TreeObject node = new TreeObject(nodeCount, 1);
		if (currTok.getLexeme().equals("program") && nodeCount == 1){
			getNextTok();
			if (currTok.getTID() == 20){
				getNextTok();
				node.setBranchNum(nodeCount +1);
				r2();
				node.setBranchNum2(nodeCount + 1);
				r6();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "{", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "program", currTok.getLexeme());}
		node.setAltNum(1);
		parseTree.addTreeObj(node);
	}
	
	// Rule 2 -> DECL_SEC
	private void r2(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 2);
		node.setBranchNum(nodeCount +1);
		r3();
		node.setAltNum(1);
		if(!(Arrays.asList(keywords).contains(currTok.getLexeme()) || myToks.get(tokCount).getTID() == 10)){
			r2();
			node.setAltNum(2);
		}
		parseTree.addTreeObj(node);
	}
	
	// Rule 3 -> DECL
	private void r3(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 3);
		node.setBranchNum(nodeCount + 1);
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
		node.setAltNum(1);
		parseTree.addTreeObj(node);
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
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 4);
		node.setBranchNum(nodeCount +1);
		r5(mode);
		node.setAltNum(1);
		if(currTok.getTID() == 24){
			node.setAltNum(2);
			node.setBranchNum2(nodeCount + 1);
			getNextTok();
			r4(mode);
		}
		parseTree.addTreeObj(node);
	}
	
	// find way to handle dupes in symbol table without it affecting other calls to ID rule
	// Rule 5 -> ID
	private void r5(int mode){
		if (currTok.getLexeme().matches("[a-zA-Z]+")){
			nodeCount += 1;
			TreeObject node = new TreeObject(nodeCount, 5);
			node.setIDFloat(currTok.getLexeme());
			if (mode == 1){
				if (symTab.contains(currTok.getLexeme())){
					MyErrorHandler.alreadyDecSymbErr(currTok.getLineNum(), currTok.getLexeme());
				}
				else{
					symTab.add(currTok.getLexeme());
					getNextTok();
				}
			}
			else if (mode == 2){
				if (symTab.contains(currTok.getLexeme())){
					getNextTok();
				}
				else{MyErrorHandler.undeclaredSymbErr(currTok.getLineNum(), currTok.getLexeme());}
			}
			parseTree.addTreeObj(node);
		}else{MyErrorHandler.unexpectedSymbolErr(currTok.getLineNum(), currTok.getLexeme());}
	}

	// Rule 6 -> STMT_SEC
	private void r6(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 6);
		node.setBranchNum(nodeCount + 1);
		r7();
		node.setAltNum(1);
		if (currTok.getTID() != 21){
			if(currTok.getTID() != -1){
				node.setAltNum(2);
				node.setBranchNum2(nodeCount + 1);
				r6();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "}", currTok.getLexeme());}
		}
		parseTree.addTreeObj(node);
	}
	
	// Rule 7 -> STMT
	private void r7(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 7);
		//if (!Arrays.asList(keywords).contains(currTok.getLexeme())){r8();}
		if (currTok.getLexeme().equals("if")){
			node.setBranchNum(nodeCount + 1);
			node.setAltNum(2);
			r9();
			}
		else if (currTok.getLexeme().equals("while")){
			node.setBranchNum(nodeCount + 1);
			node.setAltNum(3);
			r10();
			}
		else if (currTok.getLexeme().equals("repeat")){
			node.setBranchNum(nodeCount + 1);
			node.setAltNum(4);
			r11();
		}
		else if (currTok.getLexeme().equals("cin")){
			node.setBranchNum(nodeCount + 1);
			node.setAltNum(5);
			r12();
			}
		else if (currTok.getLexeme().equals("cout")){
			node.setBranchNum(nodeCount + 1);
			node.setAltNum(6);
			r13();
			}
		else{
			node.setBranchNum(nodeCount + 1);
			node.setAltNum(1);
			r8();
		}
		parseTree.addTreeObj(node);
	}
	
	// Rule 8 -> ASSIGN
	private void r8(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 8);
		node.setBranchNum(nodeCount + 1);
		r5(2);
		if (currTok.getTID() == 10){
			getNextTok();
			node.setBranchNum2(nodeCount + 1);
			r14();
			if (currTok.getTID() == 26){
				getNextTok();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ";", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ":=", currTok.getLexeme());}
		node.setAltNum(1);
		parseTree.addTreeObj(node);
	}
	
	// Rule 9 -> IFSTMT
	public void r9(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 9);
		getNextTok();
		node.setBranchNum(nodeCount + 1);
		r18();
		if (currTok.getTID() == 20){
			getNextTok();
			node.setBranchNum2(nodeCount + 1);
			r6();
			if(currTok.getTID() == 21){
				getNextTok();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "}", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "{", currTok.getLexeme());}
		node.setAltNum(1);
		if (currTok.getLexeme().equals("else")){
			node.setAltNum(2);
			getNextTok();
			if (currTok.getTID() == 20){
				getNextTok();
				node.setBranchNum3(nodeCount + 1);
				r6();
				if(currTok.getTID() == 21){
					getNextTok();
				}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "}", currTok.getLexeme());}
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "{", currTok.getLexeme());}
		}
		parseTree.addTreeObj(node);
	}
	
	// Rule 10 -> WHILESTMT
	public void r10(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 10);
		getNextTok();
		node.setBranchNum(nodeCount + 1);
		r18();
		if (currTok.getTID() == 20){
			getNextTok();
			node.setBranchNum2(nodeCount + 1);
			r6();
			if(currTok.getTID() == 21){
				getNextTok();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "}", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "{", currTok.getLexeme());}
		node.setAltNum(1);
		parseTree.addTreeObj(node);
	}
	
	// Rule 11 -> REPEAT
	public void r11(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 11);
		getNextTok();
		if (currTok.getTID() == 20){
			getNextTok();
			node.setBranchNum(nodeCount + 1);
			r6();
			if(currTok.getTID() == 21){
				getNextTok();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "}", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "{", currTok.getLexeme());}
		if (currTok.getLexeme().equals("until")){
			getNextTok();
			node.setBranchNum2(nodeCount + 1);
			r18();
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "until", currTok.getLexeme());}
		if (currTok.getTID() == 26){
			getNextTok();
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ";", currTok.getLexeme());}
		node.setAltNum(1);
		parseTree.addTreeObj(node);		
	}
	
	// Rule 12 -> INPUT
	private void r12(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 12);
		getNextTok();
		if (currTok.getTID() == 27){
			getNextTok();
			node.setBranchNum(nodeCount + 1);
			r4(2);
			if(currTok.getTID() == 26){
				getNextTok();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ";", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "<<", currTok.getLexeme());}
		node.setAltNum(1);
		parseTree.addTreeObj(node);			
	}
	
	// Rule 13 -> OUTPUT
	private void r13(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 13);
		getNextTok();
		if (currTok.getTID() == 28){
			getNextTok();
			node.setBranchNum(nodeCount + 1);
			r4(2);
			if(currTok.getTID() == 26){
				getNextTok();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ";", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ">>", currTok.getLexeme());}
		node.setAltNum(1);
		parseTree.addTreeObj(node);			
	}
	
	// Rule 14 -> EXPR
	private void r14(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 14);
		node.setBranchNum(nodeCount + 1);
		r15();
		node.setAltNum(1);
		while(currTok.getTID() == 11 || currTok.getTID() == 12){
			if (currTok.getTID() == 11){node.setAltNum(2);}
			else{node.setAltNum(3);}
			getNextTok();
			node.setBranchNum2(nodeCount + 1);
			r14();
		}
		parseTree.addTreeObj(node);	
		
	}
	
	// Rule 15 -> FACTOR
	private void r15(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 15);
		node.setBranchNum(nodeCount + 1);
		r16();
		node.setAltNum(1);
		while(currTok.getTID() == 13 || currTok.getTID() == 14 || currTok.getTID() == 15){
			if (currTok.getTID() == 13){node.setAltNum(2);}
			else if (currTok.getTID() == 14){node.setAltNum(3);}
			else{node.setAltNum(3);}
			getNextTok();
			node.setBranchNum(nodeCount + 2);
			r15();
		}
		parseTree.addTreeObj(node);	
	}
	
	// Rule 16 -> OPERAND
	private void r16(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 16);
		// FLOAT
		if (Character.isDigit(currTok.getLexeme().charAt(0))){
			node.setBranchNum(nodeCount + 1);
			node.setAltNum(1);
			r17();
		}
		// ID
		else if (Character.isAlphabetic(currTok.getLexeme().charAt(0))){
			node.setBranchNum(nodeCount + 1);
			node.setAltNum(2);
			r5(2);
		}
		// (EXPR)
		else if (currTok.getTID() == 22){ 
			getNextTok();
			node.setBranchNum(nodeCount + 1);
			node.setAltNum(3);
			r14();
			if (currTok.getTID() == 23){
				getNextTok();
			}else {MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ")", currTok.getLexeme());}
		}else{MyErrorHandler.unexpectedSymbolErr(currTok.getLineNum(), currTok.getLexeme());}
		parseTree.addTreeObj(node);	
	}
	
	// Rule 17 -> FLOAT
	/* 
	This function's existence is compulsory because it 
	is a requirement for the project. The scanner already
	guarantees that the floating point digit will be properly formatted.
	*/
	private void r17(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 17);
		try{
			float y = Float.parseFloat(currTok.getLexeme());
			node.setIDFloat(currTok.getLexeme());
			getNextTok();
		}
		catch(NumberFormatException e){
			MyErrorHandler.illegalNumberErr(currTok.getLineNum(), currTok.getLexeme());
		}
		parseTree.addTreeObj(node);		
	}
	
	// Rule 18 -> COMP
	private void r18(){
		nodeCount += 1;
		TreeObject node = new TreeObject(nodeCount, 18);
		if (currTok.getTID() == 22){
			node.setBranchNum(nodeCount + 1);
			getNextTok();
			r16();
			if(currTok.getTID() == 16 || currTok.getTID() == 17 || currTok.getTID() == 18 || currTok.getTID() == 19){
				if (currTok.getTID() == 18){node.setAltNum(1);}
				else if (currTok.getTID() == 19){node.setAltNum(2);}
				else if (currTok.getTID() == 17){node.setAltNum(3);}
				else{node.setAltNum(4);}				
				node.setBranchNum2(nodeCount + 1);
				getNextTok();
				r16();
			}else{MyErrorHandler.unexpectedSymbolErr(currTok.getLineNum(), currTok.getLexeme());}
			if (currTok.getTID() == 23){	
				getNextTok();
			}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), ")", currTok.getLexeme());}
		}else{MyErrorHandler.missingExpectedSyntaxErr(currTok.getLineNum(), "(", currTok.getLexeme());}
		parseTree.addTreeObj(node);		
	}


}

