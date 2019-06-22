
//Queen can move diagonally (like a knight) and vertically and horizontally (like a rook)
public class Queen extends chessPiece {
	public Queen(int color) {
		super(color);
		if (color == 0)
		this.setDisplayPiece("0Q");
		else this.setDisplayPiece("1Q");
	}

	public boolean Move(int oldX, int oldY, int newX, int newY, chessPiece [][] theMatrix) {
		int tempX = oldX;
		int tempY = oldY;
		int OLD = oldX;
		int NEW = oldY;
		int t;
		int X = 1;
		int Y = 1; 
		int M = 0;
		int N = 0;
		boolean horz = false;
		boolean RookMove = false;
		boolean valid = false; //is true if path to new position is legal
		boolean enemy = false; //enemy is in the path
		boolean markAttackPath = false; 
		chessPiece TM = theMatrix[tempX][tempY];
		
		if (theMatrix[newX][newY].getClass().getTypeName() == "King" && theMatrix[newX][newY].Getteam() != this.Getteam()) {
			markAttackPath = true; 	
			}
		//check rook move first
		if (oldX == newX && oldY != newY) { //move horizontally 
			OLD = oldY;
			NEW = newY;
			horz = true;
			RookMove = true;
		}
		else if(oldY == newY && oldX != newX) { //move vertically
			OLD = oldX;
			NEW = newX;
			RookMove = true;
		}
		if (RookMove) {	
		if (NEW > OLD) t = 1; //determines whether to move left/right or up/down
		else t = -1;
		chessPiece CurrentSpot;
		for (int i=OLD; i != (NEW+t) && !valid; i=i+(t)) {	
			
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
			System.out.println("rook valid : " + valid);
			return true;	
			}
		}
		//// the bishop move
		if (!RookMove) {
		AttackPath.clear();	
		if (newX > oldX && newY > oldY) { 
			 X = 1;
			 Y = 1;
		}
		else if(newX < oldX && newY > oldY) { 
			X = -1;
			Y = 1;
		}
		else if(newX > oldX && newY < oldY) { 
			X = 1;
			Y = -1;
		}
		else if(newX < oldX && newY < oldY) {
			X = 1;
			Y = -1;
		}
		while(!enemy && !valid && tempX <= 7 && tempY <= 7) {
			if (markAttackPath) AttackPath.add(Integer.toString(tempX).concat(Integer.toString(tempY))); //yes I did.
			if(TM != null && TM.Getteam() != this.Getteam()) {
				enemy = true;
			}
			if(tempX != oldX && tempY != oldY && TM != null && !enemy) {
				valid = false;
			}
			if(tempX == newX && tempY == newY) { //intended location reached
				valid = true;
				continue;
			}
			tempX = tempX + X;
			tempY = tempY + Y;
			}
		}
		if (valid) {
			return true;
		}
		else return false;
	}
}
