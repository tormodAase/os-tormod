
public class CPTree {
	private CPNode root;
	
	public static void main(String[] args) {
		CPTree t = new CPTree("00011101011", "ABCDE");
		System.out.println(t);
	}
	
	/*Real code*/
	public CPTree(String st, String con) {
		root = new CPNode();
		if (st.charAt(0) == '0') {
			root.label = 0;
			root.character = con.charAt(0);
			recursiveFunc(root, removeFirstCharInString(st), removeFirstCharInString(con));
		} else {
			root.label = 1; //Would be weird if this ever happened
		}
	}
	
	/*
	 * Return value returns an int array containing two values.
	 * ReturnValue[0] says how much of st were read and used
	 * ReturnValue[1] says how much of con were read and used.
	 */
	private int[] recursiveFunc(CPNode node, String st, String con) {
		char current = st.charAt(0);
		int[] returnValues = {2, 0}; //will always read two st values
		node.leftNode = new CPNode();
		if (current == '1') {
			node.leftNode.label = 1;
			st = removeFirstCharInString(st);
		} else {
			node.leftNode.label = 0;
			node.leftNode.character = con.charAt(0);
			returnValues[1]++;
			
			st= removeFirstCharInString(st);
			con = removeFirstCharInString(con);
			
			int[] returnValuesFromLeftNode = recursiveFunc(node.leftNode, st, con);
			
			//getting st and con ready for the rightNode
			for (int i=0; i<returnValuesFromLeftNode[0]; i++) {
				st = removeFirstCharInString(st);
			}
			for (int i=0; i<returnValuesFromLeftNode[1]; i++) {
				con = removeFirstCharInString(con);
			}
			
			//adding returnvalues from the left node to the total
			returnValues[0] += returnValuesFromLeftNode[0];
			returnValues[1] += returnValuesFromLeftNode[1];
		}
		
		//Code below looks quite similar to the code above. 
		current = st.charAt(0);
		node.rightNode = new CPNode();
		if (current == '1') {
			node.rightNode.label = 1;
			st = removeFirstCharInString(st);
		} else {
			node.rightNode.label = 0;
			node.rightNode.character = con.charAt(0);
			returnValues[1]++;
			int[] returnValuesFromRightNode = recursiveFunc(node.rightNode, removeFirstCharInString(st), removeFirstCharInString(con));
			
			returnValues[0] += returnValuesFromRightNode[0];
			returnValues[1] += returnValuesFromRightNode[1];
		}
		return returnValues;
	}
	
	
	private String removeFirstCharInString(String s) {
		s = s.substring(1, s.length());
		return s;
	}
	
	/*PRINTING
	*Below is code for printing the tree. It's not perfect, but good enough to get an idea about how the tree looks like.
	 */
	private CPNode[][] table;
	
	public String toString() {
		table = new CPNode[100][100];
		addNodeToTable(root, 0);
		
		int startPos=0;
		int rows = 0;
		for (int i=0; i<100; i++) {
			if (table[i][0] == null) {
				startPos = (i-1)*4;
				rows = i;
				break;
			}
		}
		String theString = "";
		for (int i=0; i<rows; i++) {
			for (int j=0; j<startPos;j++) {
				theString = theString + " ";
			}
			for (int j=0; j<100; j++) {
				if (table[i][j] == null) break;
				else {
					char symbol;
					if (table[i][j].label == 1) symbol = '1';
					else symbol = table[i][j].character;
					theString = theString + symbol + emptyString(4-i);
				}
			}
			theString = theString + "\n";
			startPos = startPos - 2;
		}
		return theString;
		
	}
	
	private void addNodeToTable(CPNode node, int row) {
		for (int i=0; i<100; i++) {
			if (table[row][i] == null) {
				table[row][i] = node;
				if (node != null) {
					addNodeToTable(node.leftNode, row+1);
					addNodeToTable(node.rightNode, row+1);
				}
				break;
			}
		}
	}
	
	private String emptyString(int length) {
		String s = "";
		for (int i=0; i<length; i++) {
			s += " ";
		}
		return s;
	}
}
