
//the castle
//can only move vertically and horizontally.
//Castle logic: must check for left and right castle conditions (both similar)
//path between King and Castle must be null and not under attack
//Castle and King must be in their original positions and not have made a move. (not made a move proves the other)
public class Rook extends chessPiece {
	public Rook(int color) {
		super(color);
		if (color == 0) this.setDisplayPiece("0R");
		else this.setDisplayPiece("1R");
	}public boolean Move(int oldX, int oldY, int newX, int newY, chessPiece [][] theMatrix) {
		System.out.println("old X" + oldX + " oldY " + oldY + " newX" + newX + " newY" + newY);
		int tempX = oldX;
		int tempY = oldY;
		int OLD = oldX;
		int NEW = oldY;
		int t; // can be +/- 1 to iterate up,down,left,right
		boolean horz = false;
		boolean valid = false; //is true if path to new position is legal
		boolean markAttackPath = false;
		if (theMatrix[newX][newY] != null && theMatrix[newX][newY].getClass().getTypeName() == "King" && theMatrix[newX][newY].Getteam() != this.Getteam()) {
			markAttackPath = true;
			}
			if (oldX == newX && oldY != newY) { //move horizontally 
				//System.out.println("horizontal");
				OLD = oldY;
				NEW = newY;
				horz = true;
			}
			else if(oldY == newY && oldX != newX) { //move vertically
				//System.out.println("vertical");
				OLD = oldX;
				NEW = newX;
			}
			else {
				//System.out.println("returned false, post vert/horz");
				return false;
			}
			//if going right, ex.) 0 - > 2
			//if going left ex.) 2 -> 0
			//if going up ex.) 2 -> 0
			//if going down ex.) 0 -> 2
			if (NEW > OLD) { 
				t = 1; //determines whether to move left/right or up/down
			}
			else { 
			t = -1;
			}						
			//System.out.println("preloop old & new: " + OLD + " " + NEW);
			chessPiece CurrentSpot;
			for (int i = OLD; i != (NEW+t) && !valid; i=i+(t)) { 
				//System.out.println("Rook: for loop entered: i:" + i + " t:" + t);
				
				if (horz) CurrentSpot = theMatrix[oldX][i];
				else CurrentSpot = theMatrix[i][oldY];
				
				if (markAttackPath) {
					AttackPath.add(Integer.toString(tempX).concat(Integer.toString(tempY)));
				}
				if (i != OLD && i != NEW && CurrentSpot != null) {
					//System.out.println("valid set to false");
					valid = false; //if false, there is a piece in the way.  
				}
				else if (i == NEW && CurrentSpot != null && CurrentSpot.Getteam() != this.Getteam()) { //enemy piece
					//System.out.println("valid set to true");
					valid = true;
				}
				else if (i == NEW && CurrentSpot == null) {
					//System.out.println("valid set to true");
					valid = true; //empty spot
				}
			}
		 
		if (valid) {
				return true;	
		}	
		else {
			AttackPath.clear();
			return false; 
		}
	}

}
