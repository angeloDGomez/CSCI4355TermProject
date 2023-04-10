// Code by Angelo Gomez, 1535298
// CSCI 4355

public class MyTokens{
	private String lexeme;
	private int tID;
	
	public MyTokens(String lex, int tID){
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