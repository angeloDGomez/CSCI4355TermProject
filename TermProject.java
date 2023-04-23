// Code by Angelo Gomez, 1535298
// CSCI 4355

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class TermProject{
	public static Scanner sc;
	public static String inputFile;
	public static ArrayList<MyTokens> toks;
	
	public static void main(String[] args)throws FileNotFoundException{
		//Extract program from file.
		File inFile = new File("testFiles/testFile5.txt"); //Choose file from this line.
		//I can implement asking for file path from command line in future iterations if that is preferred.
		sc = new Scanner(inFile);
		storeFile();
		sc.close();
		MyScanner mySC = new MyScanner(inputFile);
		mySC.scanProgram();
		toks = mySC.getTokens();
		System.out.println(toks);
		System.out.println("\n\n\n\nThe tokens are printed above for reference.\nDelete this line later.\n\n\n");
		MyParser myP = new MyParser(toks);
		myP.parseProgram();
	}
	
	public static void storeFile(){
		inputFile = "";
		// Include new line to track line count. Every file will end in a single \n.
		while(sc.hasNextLine()){inputFile = inputFile.concat(sc.nextLine() +"\n");}		
	}
}