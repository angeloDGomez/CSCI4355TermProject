// Code by Angelo Gomez, 1535298
// CSCI 4355

public class MyTokens{
	private String lexeme;
	private int tID;
	private int lineNum;
	
	public MyTokens(String lex, int tID, int lineNum){
		this.tID = tID;
		this.lexeme = lex;
		this.lineNum = lineNum;
	}
	public int getTID(){return this.tID;}
	public String getLexeme(){return this.lexeme;}
	public int getLineNum(){return this.lineNum;}
	@Override
	public String toString(){
		return "Lex:" + this.lexeme + " tID:" + Integer.toString(this.tID) + " found on line:" + Integer.toString(this.lineNum) +"\n";
	}
}