
//can only move diagonally
public class Bishop extends chessPiece {
	public Bishop(int color) {
		super(color);
		if (color == 0) this.setDisplayPiece("0B");
		else this.setDisplayPiece("1B");
	}//old x is row, old y is col 
	public boolean Move(int oldX, int oldY, int newX, int newY, chessPiece [][] theMatrix) {
		int tempX = oldX;
		int tempY = oldY;
		int X = 1;
		int Y = 1; 
		boolean valid = false;
		boolean enemy = false;
		boolean markAttackPath = false; 
		chessPiece TM = theMatrix[tempX][tempY]; 
		if (theMatrix[newX][newY] != null && theMatrix[newX][newY].getClass().getTypeName() == "King" && theMatrix[newX][newY].Getteam() != this.Getteam()) {
		markAttackPath = true; 	
		}
		
		if (newX > oldX && newY > oldY) { //DOWN RIGHT
			 X = 1;
			 Y = 1;
		}
		else if(newX < oldX && newY > oldY) { //UP RIGHT 
			X = -1;
			Y = 1;
		}
		else if(newX > oldX && newY < oldY) { //DOWN LEFT 
			X = 1;
			Y = -1;
		}
		else if(newX < oldX && newY < oldY) { //left-up UP LEFT
			X = 1;
			Y = -1;
		}
		while(!enemy && !valid && tempX <= 7 && tempY <= 7) {
			if (markAttackPath) {
				AttackPath.add(Integer.toString(tempX).concat(Integer.toString(tempY)));
			}
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
		if (valid) {
			return true;
		}
		else {
			AttackPath.clear();
			return false; 
		}
	}
}
