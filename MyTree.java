// Code by Angelo Gomez, 1535298
// CSCI 4355
import java.util.ArrayList;

public class MyTree{
	
	private ArrayList<TreeObject> theTree;
	
	public MyTree(){
		theTree = new ArrayList<TreeObject>();
	}
	
	public void addTreeObj(TreeObject t){
		if (theTree.size() == 0){
			theTree.add(t);
		}else{
			int i;
			for (i = 0; i < theTree.size(); i++){
				if(theTree.get(i).getNodeNum() > t.getNodeNum()){
					theTree.add(i, t);
					break;
				}
			}
			if (i == theTree.size()){
				theTree.add(t);
			}
		}
	}
	
	@Override 
	public String toString(){
		String treeOutput = "";
		for(int i = 0; i < theTree.size(); i++){
			treeOutput += theTree.get(i).toString();
			treeOutput += "\n\n";
		}
		return treeOutput;
	}

}