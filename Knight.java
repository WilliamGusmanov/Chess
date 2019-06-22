// the knight is the horse.
// it can move right 2 up 1 or down 1
// it can move left 2 down 1 or up 1
// it can move up 2, left 1 or right 1
// it can move down 2, left 1 or right 1
public class Knight extends chessPiece{
	//constructor w/ super
	public Knight(int color) {
		super(color);
		if (color == 0) {
			this.setDisplayPiece("0H");
		}
		else this.setDisplayPiece("1H");
	}
	//override
	//return true if piece can be moved to location, false if spot cannot be reached
	public boolean Move(int x, int y, int newx, int newy, chessPiece [][] TM) {
		boolean movementGood = false; 
		boolean emptyspot = false;
		boolean enemyinspot = false;
		if ((newx == x + 2 && newy == y + 1 || newx == x + 2 && newy == y - 1) || //checks if the destination is a valid movement
			(newx == x - 2 && newy == y + 1 || newx == x - 2 && newy == y - 1) || 
			(newx == x + 1 && newy == y + 2 || newx == x - 1 && newy == y + 2) ||
			(newx == x + 1 && newy == y - 2 || newx == x - 1 && newy == y - 2)) {
			movementGood = true; }
		if(TM[newx][newy] == null)
			emptyspot = true;
		if(TM[newx][newy] != null && TM[newx][newy].Getteam() != this.Getteam()) 
			enemyinspot = true;
		if (movementGood && (emptyspot || enemyinspot)) {
			return true;
		}
		else return false; //return false if invalid move
	}
}
