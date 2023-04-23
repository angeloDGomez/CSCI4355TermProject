// Code by Angelo Gomez, 1535298
// CSCI 4355

import java.util.Arrays;
import java.util.ArrayList;

public class MyScanner{
	private String inputFile;
	
	private int lineCount; // Identifies which line is the problem during error handling. 
	private int strPtr; // Tracks position. Always points at nextChar.
	private int inLen; // Keeps track of how long to loop.
	
	private char currChar;
	private int currCharType;
	
	// Character Types 
	static final int whiteSpace = -1;
	static final int letter = 0;
	static final int digit = 1;
	static final int symbol = 2;
	
	// Variables that will be stored in the token list.
	private String lexeme;
	private int tokenID;
	
	//Token IDs
	static final int ident =0;
	static final int floatLit = 5;
	// Operator IDs will be their index in the operator array + 10

	/* Operators
	first row - arithmetic 
	second row - comparison
	third row - other */
	private static String[] ops = {":=", "+", "-", "*", "/", "%", 
	"<", ">", "=", "<>",  
	"{", "}", "(", ")", ",", ":", ";", "<<", ">>"};
	
	//Keywords
	/* begin, end, then, input, output, and loop are all not used in the given grammar
	but will remain in the keywords until the document is updated.
	cout, cin, repeat, and until were added as they are used in the given grammar.*/
	public static String[] keywords =  {"program", "begin", "end", "if", "then", "else", 
	"input", "output", "float", "while", "cout", "cin", "loop", "repeat", "until"};
	
	//Token Storage - will be more utilized in parser
	private ArrayList<MyTokens> myToks = new ArrayList<MyTokens>();
	
	// Symbol Table is one dimensional since all variables are floats.
	private ArrayList<String> symTab = new ArrayList<String>();
	
	public MyScanner(String myProg){
		this.lineCount = 1;
		this.strPtr = 0;
		this.inputFile = myProg;
		this.inLen = myProg.length();
	}
	
	public void scanProgram(){
		getChar();
		do{
			lex();
		}while(strPtr <= inLen);
	}
	
	private void getChar(){
		// If there are still characters available
		if (strPtr != inLen){
			char temp = inputFile.charAt(strPtr);
			currChar = temp;
			if (Character.isLetter(temp)){currCharType = letter;}
			else if(Character.isDigit(temp)){currCharType = digit;}
			else if(Character.isWhitespace(temp)){currCharType = whiteSpace;}
			else{currCharType = symbol;}
		}else{} // Indicates end of file.
		strPtr++;
	}
	
	// Loop until currentChar is not a whiteSpace character.
	private void getNonBlank(){
		while(Character.isWhitespace(currChar) && strPtr!= inLen){
			if(currChar == '\n'){
				lineCount++;
				}
			getChar();
		}
	}
	
	private boolean isKeyword(String lex){
		return Arrays.asList(keywords).contains(lex);}

	// Check if symbol is a valid operator
	private boolean inOPList(String testOP){
		return Arrays.asList(ops).contains(testOP);}

	// Check if user defined var is already in symbol table
	private boolean inSTab(String testLex){
		return symTab.contains(testLex);}
	
	private void lex(){
		lexeme = "";
		getNonBlank();
		tokenID = -1; // If -1 token is printed without EOF as its lexeme. There is a major uncaught error.
		switch(currCharType){
			case letter:
				while(currCharType == letter){
					lexeme += currChar;
					getChar();
				}
				// Char can be followed by a symbol but not by a digit.
				if (Character.isDigit(currChar)){
					MyErrorHandler.unexpectedCharErr(lineCount);
				}
				// Add new variable name to the symbol table.
				lexeme = lexeme.toLowerCase(); // Make string lowercase. Instructions say language is case insensitive.
				//if (!isKeyword(lexeme) && !inSTab(lexeme)){symTab.add(lexeme);} 
				tokenID = ident;
				break;
			case digit:
				while(currCharType == digit){
					lexeme += currChar;
					getChar();
				}
				// All numbers are floating point so need .
				if(currChar != '.'){
					MyErrorHandler.unexpectedCharErr(lineCount);
				}
				lexeme += currChar;
				getChar();
				// Ensure digits for second half of floating point.
				if (!Character.isDigit(currChar)){
					MyErrorHandler.unexpectedCharErr(lineCount);
				
				}
				while(currCharType == digit){
					lexeme += currChar;
					getChar();
				}
				// Digit can be followed by a symbol but not by a char.
				if (Character.isLetter(currChar)){
					MyErrorHandler.unexpectedCharErr(lineCount);
				}
				tokenID = floatLit;
				break;
			case symbol:
				lexeme += currChar;
				getChar();
				tokenID = 10; //tokenID is 10 + pos in OP list
				if (currChar == ';' || currChar == '(' || currChar == '{' || currChar == ')' || currChar == '}'){
					// do nothing if these are the symbols
					// reasons to do nothing x := (a + (b * c))
					// issues with parenthesis and bracket order will be dealt with by parser
				}
				// Check for valid 2 char long operators
				else if (currCharType == symbol){
					String temp = lexeme + currChar;
					if (inOPList(temp)){
						lexeme = temp;
						getChar();
						tokenID += Arrays.asList(ops).indexOf(lexeme);
						}
					else{MyErrorHandler.unexpectedCharErr(lineCount);}
				}
				// Check for valid 1 char long operators
				else{
					if(inOPList(lexeme)){
						tokenID += Arrays.asList(ops).indexOf(lexeme);
					}else{MyErrorHandler.unexpectedCharErr(lineCount);}
				}
				break;
			/* This case should only be hit once you go through the entire code.
			The last line should always be a \n which will usually be eliminated
			by getNonBlank(). However, because it will be the last char getNonBlank() cannot loop.*/
			case whiteSpace:
				lexeme = "EOF";
				getChar();
				break;				
		}
		// Uncomment the following line of code so the scanner will print out the appropriate tokens and lexemes.
		//System.out.printf("Next token's typeID is %d. Next lexeme is %s.\n", tokenID, lexeme);
		MyTokens toTokList = new MyTokens(lexeme, tokenID, lineCount);
		myToks.add(toTokList);
	}
	
	public ArrayList<MyTokens> getTokens(){return myToks;}
	
	public ArrayList<String> getSymbolTable(){return symTab;}
	
}