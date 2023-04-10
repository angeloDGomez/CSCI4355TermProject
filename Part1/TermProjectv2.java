// Code by Angelo Gomez, 1535298
// CSCI 4355

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TermProject{
	public static String inputFile;
	public static Scanner sc;

	public static int lineCount;
	public static int strPtr = 0;
	
	public static char currChar;
	public static int currCharType;
	static final int letter = 0;
	static final int digit = 1;
	static final int symbol = -1;
	public static String lexeme;
	
	public static void main(String[] args) throws FileNotFoundException{
		//Open file
		File inFile = new File("testFiles/testFile1.txt");
		sc = new Scanner(inFile);
		storeFile();
		sc.close();
		getChar();
		//System.out.println(currCharType);
		// do lexical analysis, while there are still characters that have not been looked at
		/*do{
			lex()
		}while(strPtr < len(inputFile);*/
	}
	
	public static void storeFile(){
		inputFile = "";
		lineCount = 1;
		// include new line to track line count
		while(sc.hasNextLine()){inputFile = inputFile.concat(sc.nextLine() +"\n");}
	}
	/*
	public static void getChar(){
		// skip over white space
		char temp = inputFile.charAt(strPtr);
		currChar = temp;
		if (Character.isLetter(temp)){currCharType = letter;}
		else if(Character.isDigit(temp)){currCharType = digit;}
		else{currCharType = symbol;}
		// need to do spaces before newline page 300 textbook example
	}
	
	public static void skipWhite(){
		while(inputFile.charAt(strPtr) == ' '|| inputFile.charAt(strPtr) == '\n'){
			strPtr++;
			if(inputFile.charAt(strPtr) == '\n'){lineCount ++;}}
	}
	*/
	public static void lex(){
		lexeme = "";
		switch (currCharType){
			case letter:
				lexeme += currChar;
				getChar();
				break;
			case digit:
				lexeme += currChar;
				getChar();
				break;
			case symbol:
				lexeme += currChar;
				getChar();
				break;
		}
	}
	
	
}