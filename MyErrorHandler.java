// Code by Angelo Gomez, 1535298
// CSCI 4355

public class MyErrorHandler{
	
	public static void unexpectedCharErr(int lineCount){
		System.out.printf("Error: Unexpected character found on line %d.\nEnding Scan.\n", lineCount);
		System.exit(0);
	}
	/*
	public static void syntaxErr(int lineCount){
		System.out.printf("Error: Unexpected character found on line %d.\nEnding Scan.\n", lineCount);
		System.exit(0);
	}*/
	
	public static void missingExpectedSyntaxErr(int lineCount, String expectedVal){
		System.out.printf("Error: Improper syntax. '%s' not found on line %d.\nEnding Parse.\n", expectedVal, lineCount);
		System.exit(0);
	}
	
	public static void illegalSymbolErr(int lineCount){
		System.out.printf("Error: Illegal symbol found on line %d.\nEnding Parse.\n", lineCount);
		System.exit(0);
	}
	
	public static void alreadyDecSymbErr(int lineCount, String symbol){
		System.out.printf("Error: Symbol %s on line %d has already been declared.\nEnding Parse.\n", symbol, lineCount);
		System.exit(0);		
	}
	/*
	public static void unexpectedSymbolErr(int lineCount){
		System.out.printf("Error: Unexpected symbol found on line %d.\nEnding Parse.\n", lineCount);
		System.exit(0);
	}
	

	
	public static void unexpectedIdentifierErr(int lineCount){
		System.out.printf("Error: Unexpected identifier found on line %d.\nEnding Parse.\n", lineCount);
		System.exit(0);
	}*/

}