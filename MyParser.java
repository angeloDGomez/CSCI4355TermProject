// Code by Angelo Gomez, 1535298
// CSCI 4355

import java.util.ArrayList;

public class MyParser{
	//Token Storage
	private ArrayList<MyTokens> myToks = new ArrayList<MyTokens>();
	
	// Symbol Table is one dimensional since all variables are floats.
	private ArrayList<String> symTab = new ArrayList<String>();
	
	public MyParser(ArrayList<MyTokens> t, ArrayList<String> st){
		this.myToks = t;
		this.symTab = st;
	}
	
	public void parseProgram(){
	
	}
}