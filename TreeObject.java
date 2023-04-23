// Code by Angelo Gomez, 1535298
// CSCI 4355

public class TreeObject{
	
	private int nodeNum;
	private int ruleNum;
	private int branchNum = 0;
	private int branchNum2 = 0;
	private int branchNum3 = 0;
	private int altNum = 0;
	private String idFloat = "";
	
	public TreeObject(int nodeNum, int ruleNum){
		this.nodeNum = nodeNum;
		this.ruleNum = ruleNum;
	}
	
	public int getNodeNum(){return this.nodeNum;}
	
	public void setBranchNum(int bn){this.branchNum = bn;}
	public void setBranchNum2(int bn){this.branchNum2 = bn;}
	public void setBranchNum3(int bn){this.branchNum3 = bn;}
	public void setAltNum(int an){this.altNum = an;}
	public void setIDFloat(String idf){this.idFloat = idf;}
	
	@Override
	public String toString(){
		String outputStr = "";
		if (nodeNum < 10){
			outputStr += "Node #:   ";
		}else if(nodeNum< 100){
			outputStr += "Node #:  ";
		}else{outputStr += "Node #: ";}
		outputStr += nodeNum;
		if (ruleNum < 10){
			outputStr +=  ", Rule #:   ";
		}else if (ruleNum < 100){
			outputStr +=  ", Rule #:  ";
		}else{outputStr +=  ", Rule #: ";}
		outputStr += ruleNum;
		if (branchNum < 10){
			outputStr +=  ", Branch 1:   ";		
		}else if (branchNum < 100){
			outputStr +=  ", Branch 1:  ";
		}else{outputStr +=  ", Branch 1: ";}
		outputStr += branchNum;
		if (branchNum2 < 10){
			outputStr +=  ", Branch 2:   ";
		}else if (branchNum2 < 100){
			outputStr +=  ", Branch 2:  ";
		}else{outputStr +=  ", Branch 2: ";}
		outputStr += branchNum2;
		if (branchNum3 < 10){
			outputStr +=  ", Branch 3:   ";
		}else if (branchNum3 < 100){
			outputStr +=  ", Branch 3:  ";
		}else{outputStr +=  ", Branch 3: ";}
		outputStr = outputStr + branchNum3 + ", Alternative #: " + altNum + ", Id/Float Value: " + idFloat;	
		return outputStr;
	}

}