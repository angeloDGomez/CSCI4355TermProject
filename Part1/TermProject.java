// Code by Angelo Gomez, 1535298
// CSCI 4355

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class TermProject{
	public static Scanner sc;
	public static String inputFile;
	public static int inLen; // Keeps track of how long to loop.
	
	public static int lineCount; // Identifies which line is the problem during error handling. 
	public static int strPtr; // Tracks position. Always points at nextChar.
	
	public static char currChar;
	public static int currCharType;
	
	// Character Types 
	static final int whiteSpace = -1;
	static final int letter = 0;
	static final int digit = 1;
	static final int symbol = 2;
	
	// Variables to be later stored in token list.
	public static String lexeme;
	public static int tokenID;
	
	//Token IDs
	public static int ident =0;
	public static int floatLit = 5;
	// Operator IDs will be their index in Array + 10
	
	//Keywords
	/* begin, end, then, input, output, and loop are all not used in the given grammar but will remain in the keywords until the document is updated.
	cout, cin, repeat, and until were added as they are used in the given grammar.*/
	public static String[] keywords =  {"program", "begin", "end", "if", "then", "else", 
	"input", "output", "float", "while", "cout", "cin", "loop", "repeat", "until"};
	
	/* Operators
	first row - arithmetic 
	second row - comparison
	third row - other */
	public static String[] ops = {":=", "+", "-", "*", "/", "%", 
	"<", ">", "=", "<>",  
	"{", "}", "(", ")", ",", ":", ";", "<<", ">>"};
	
	//Token Storage - will be more utilized in parser
	public static ArrayList<Token1> myTokens = new ArrayList<Token1>();
	
	// Symbol Table is one dimensional since all variables are floats.
	public static ArrayList<String> symTab = new ArrayList<String>();
	
	
	public static void main(String[] args) throws FileNotFoundException{
		//Open file
		File inFile = new File("testFiles/testFile4.txt"); // Change file path here to change file 
		//I can implement asking for file path from command line in future iterations if that is preferred.
		sc = new Scanner(inFile);
		storeFile();
		sc.close();
		inLen = inputFile.length();
		getChar();
		do{
			lex();
		}while(strPtr <= inLen);
	}
	
	public static void storeFile(){
		inputFile = "";
		lineCount = 1;
		strPtr = 0;
		// Include new line to track line count. Every file will end in a single \n.
		while(sc.hasNextLine()){inputFile = inputFile.concat(sc.nextLine() +"\n");}
	}	
	
	public static void getChar(){
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
	public static void getNonBlank(){
		while(Character.isWhitespace(currChar) && strPtr!= inLen){
			if(currChar == '\n'){lineCount++;}
			getChar();
		}
	}	
	public static boolean isKeyword(String lex){
		// Make string lowercase. Instructions say language is case insensitive.
		return Arrays.asList(keywords).contains(lex.toLowerCase());}
		
	// Check if symbol is a valid operator
	public static boolean inOPList(String testOP){
		return Arrays.asList(ops).contains(testOP);}
	
	// Check if user defined var is already in symbol table
	public static boolean inSTab(String testLex){
		return symTab.contains(testLex.toLowerCase());}// Make string lowercase. Instructions say language is case insensitive.
		
		
	public static void lex(){
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
					unexpectedCharErr();
				}
				// Add new variable name to the symbol table.
				if (!isKeyword(lexeme) && !inSTab(lexeme)){symTab.add(lexeme.toLowerCase());} // Make string lowercase. Instructions say language is case insensitive.
				tokenID = ident;
				break;
			case digit:
				while(currCharType == digit){
					lexeme += currChar;
					getChar();
				}
				// All numbers are floating point so need .
				if(currChar != '.'){
					unexpectedCharErr();
				}
				lexeme += currChar;
				getChar();
				// Ensure digits for second half of floating point.
				if (!Character.isDigit(currChar)){
					unexpectedCharErr();
				
				}
				while(currCharType == digit){
					lexeme += currChar;
					getChar();
				}
				// Digit can be followed by a symbol but not by a char.
				if (Character.isLetter(currChar)){
					unexpectedCharErr();
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
					else{unexpectedCharErr();}
				}
				// Check for valid 1 char long operators
				else{
					if(inOPList(lexeme)){
						tokenID += Arrays.asList(ops).indexOf(lexeme);
					}else{unexpectedCharErr();}
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
		// Print out for scanner portion of assignment and store tokens.
		System.out.printf("Next token's typeID is %d. Next lexeme is %s.\n", tokenID, lexeme);
		Token1 toTokList = new Token1(lexeme, tokenID);
		myTokens.add(toTokList);
	}
	
	public static void unexpectedCharErr(){
		System.out.printf("Error: Unexpected character found on line %d.\nEnding Scan.\n", lineCount);
		System.exit(0);
	}
}	
// Token class to more easily store the token type and its lexeme.
class Token1{
	private String lexeme;
	private int tID;
	
	public Token1(String lex, int tID){
		this.tID = tID;
		this.lexeme = lex;
	}
	public int getTID(){return this.tID;}
	public String getLexeme(){return this.lexeme;}
	@Override
	public String toString(){
		return "Lex:" + this.lexeme + " tID:" + Integer.toString(this.tID);
	}
}