// Code by Angelo Gomez, 1535298
// CSCI 4355


public class MySymbols{
	
	private String name;
	/* 
	All variables in this language are floats.
	Generic typing would be used to implement
	the value variable otherwise.
	*/
	private float value;
	private String type; 
	
	public MySymbol(String name){
		this.name = name;
		type = "float";
	}
	
	public String getName(){return name;}
	/* 
	These functions would also implement 
	generics if all variables werent floats.
	*/
	public void setValue(float x){value = x;}
	public float getValue(){return value;}
	
	public String getType(){return float;}
	
}