// Code by Angelo Gomez, 1535298
// CSCI 4355

public class MyErrorHandler{
	
	public static void unexpectedCharErr(int lineCount, char ch){
		System.out.printf("Error: Unexpected character '%c' found on line %d.\nEnding Scan.\n", ch, lineCount);
		System.exit(0);
	}
	
	public static void illegalCharErr(int lineCount, String s){
		System.out.printf("Error: Illegal character '%s' found on line %d.\nEnding Scan.\n", s, lineCount);
		System.exit(0);
	}
	
	public static void keywordAsVarErr(int lineCount, String s){
		System.out.printf("Error: Keyword '%s' on line %d cannot be used as a variable.\nEnding Scan.\n", s, lineCount);
		System.exit(0);
	}
	
	public static void illegalKeywordUseErr(int lineCount, String s){
		System.out.printf("Error: Keyword '%s' on line %d cannot be used in that context.\nEnding Scan.\n", s, lineCount);
		System.exit(0);
	}
	
	public static void missingExpectedSyntaxErr(int lineCount, String expectedVal, String actualVal){
		System.out.printf("Error: Improper syntax. Expected '%s' on line %d, but found '%s' instead.\nEnding Parse.\n", expectedVal, lineCount, actualVal);
		System.exit(0);
	}
	
	public static void alreadyDecSymbErr(int lineCount, String symbol){
		System.out.printf("Error: Symbol %s on line %d has already been declared.\nEnding Parse.\n", symbol, lineCount);
		System.exit(0);		
	}
	
	public static void undeclaredSymbErr(int lineCount, String symbol){
		System.out.printf("Error: Symbol %s on line %d was never declared.\nEnding Parse.\n", symbol, lineCount);
		System.exit(0);		
	}
	
	public static void expectedEOFErr(int lineCount, String symbol){
		System.out.printf("Error: Expected to find 'EOF' symbol on line %d, but found '%s' instead.", lineCount, symbol);
		System.exit(0);	
	}
	
	public static void unexpectedSymbolErr(int lineCount, String symbol){
		System.out.printf("Error: Unexpected symbol '%s' found on line %d.\nEnding Parse.\n", symbol, lineCount);
		System.exit(0);
	}
	
	public static void illegalNumberErr(int lineCount, String symbol){
		System.out.printf("Error: Illegal number '%s' found on line %d.\nEnding Parse.\n", symbol, lineCount);
		System.exit(0);
	}

}