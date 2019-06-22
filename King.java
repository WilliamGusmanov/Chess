//the main piece, if it dies, the game ends
//can only move 1 spot, if its in check, 
// a move that prevents check must be done using a CheckMethod
//with
public class King extends chessPiece {
	
	public King(int color) {
		super(color);
		if (color == 0) {
			this.setDisplayPiece("0K");
		}
		else this.setDisplayPiece("1K");
		// TODO Auto-generated constructor stub
	}
	
	public boolean Move(int oldX, int oldY, int newX, int newY, chessPiece [][] theMatrix) {
		boolean xvalid = false;
		
		if (theMatrix[newX][newY] != null && theMatrix[newX][newY].Getteam() != this.Getteam()) {
		if (newX == oldX + 1 || newX == oldX - 1) {
			xvalid = true; 
		}
		if (xvalid && (newY == oldY + 1 || newY == oldY - 1)) {
			return true; 
		}
		}
		return false; 
	}
	//check if king can move (a method that moves king and checks if still in check)
	//check if piece can be killed (put in the piece's position into all the pieces move methods
	//check if path can be blocked (find the attack path of the piece. put all these positions into 
	//all the pieces move methods.)
	
	
	// will check if checkMate using backtracking(maybe don't need it) to check if king can move to a safe place(at most 8)
	// have to check if another piece can block king or attack piece that is attacking King.
	// check if another piece can block king by storing the attack path locations (only if not knight)
	// 
	// and then checking if all pieces can reach any of the locations in the attack path.
	// (this would be a traversal problem, choose an optimal search method, given that we are 
	// traversing through
	//
	// if it is a knight, then skip blocking check and go directly into if can kill
	// probably should combine block and kill to save iterations.
	//maybe this should be in the main chess class
	public void CheckMate() {
		
	}

}
