// Code by Angelo Gomez, 1535298
// CSCI 4355

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class TermProject{
	public static Scanner sc;
	public static String inputFile;
	public static int inLen;

	public static int lineCount;
	public static int strPtr; // this initializes to zero so it will always point at the next token
	
	public static char currChar;
	public static int currCharType;
	// Character Types 
	static final int letter = 0;
	static final int digit = 1;
	static final int symbol = -1;
	
	// Token Codes 
	public static int floatLit = 10;
	public static int ident = 11;
	public static int assignOp = 20;
	public static int addOp = 21;
	public static int subOp = 22;
	public static int mulOp = 23;
	public static int divOp = 24;
	public static int moduOp = 25;
	public static int greatOp = 30;
	public static int lessOp = 31;
	public static int equOp = 32;
	public static int notEquOp = 33;
	public static int lCurlOp = 40;
	public static int rCurlOp = 41;
	public static int commaOp = 42;
	public static int colonOp = 43;
	public static int semiCOp = 44;
	public static int cInput = 45;
	public static int cOutput = 46;
	
	
	public static int tokenID;
	public static String lexeme;
	
	public static String[] keywords =  {"program", "begin", "end", "if", "then", "else", 
	"input", "output", "float", "while", "cout", "cin", "loop"};
	
	// enumerate this so I dont have to do Token Codes in part 2?
	public static String[] ops = {":=", "+", "-", "*", "/", "%", 
	"<", ">", "=", "<>",  
	"{", "}", ",", ":", ";", "<<", ">>"};
	
	public static void main(String[] args) throws FileNotFoundException{
		//Open file
		File inFile = new File("testFiles/testFile2.txt");
		sc = new Scanner(inFile);
		storeFile();
		sc.close();
		inLen = inputFile.length();
		getChar();
		do{
			lex();
		}while(strPtr < inLen);
	}
	public static void storeFile(){
		inputFile = "";
		lineCount = 1;
		strPtr = 0;
		// include new line to track line count
		while(sc.hasNextLine()){inputFile = inputFile.concat(sc.nextLine() +"\n");}
	}
	
	public static void getChar(){
		// if there are still characters available
		if (strPtr != inLen){
			char temp = inputFile.charAt(strPtr);
			currChar = temp;
			if (Character.isLetter(temp)){currCharType = letter;}
			else if(Character.isDigit(temp)){currCharType = digit;}
			else{currCharType = symbol;}
			}else{System.out.println("EOF");}//find something else to do here later
			strPtr++;
		}
		
	public static void getNonBlank(){
		while(Character.isWhitespace(currChar) && strPtr!= inLen){
			if(currChar == '\n'){lineCount++;}
			getChar();
		}
	}
	
	public static boolean isKeyword(String lex){
		if (Arrays.asList(keywords).contains(lex)){return true;}
		else{return false;}}
		
	public static boolean validSymb(String lex){}
	
	public static void lex(){
		lexeme = "";
		getNonBlank();
		tokenID = -1; // if -1 token is ever printed that is an error.
		switch (currCharType){
			case letter:
				lexeme += currChar;
				getChar();
				while(currCharType == letter){
					lexeme += currChar;
					getChar();
				}
				if (Character.isDigit(currChar)){
					// error handling goes here
				}
				tokenID = ident;
				if(!isKeyword(lexeme)){
					// add to symbol table
				}
				break;
			case digit:
				lexeme += currChar;
				getChar();
				while(currCharType == digit){
					lexeme += currChar;
					getChar();
				}
				if(currChar != '.'){
					// unexpected token error
					//illegal character error
					// lexical error (doesnt match patern of any token)
				}
				else{
				lexeme += currChar;
				getChar();}
				while(currCharType == digit){
					lexeme += currChar;
					getChar();
				}
				tokenID = floatLit;
				break;
			case symbol:
				lexeme += currChar;
				getChar();
				break;
		}
		System.out.printf("Next token's typeID is %d. Next lexeme is %s.\n", tokenID, lexeme);
	}
}
class myErrors{
	
	
}