import java.util.ArrayList;

//if king is in check, a move that removes check can be played.
//we see if King is in check by: check every piece's move method to look for a king
//make a method in each piece that checks if it has the other King in Check.
//if an enemy king is found, place the other player in a state of CHECK
//
//
public abstract class chessPiece { //base class of all chess pieces
	private String displayPiece;
	private int team; //current color, 0 for white, 1 for black
	private Boolean EnPassant = false;
	private Boolean first = true; 
	ArrayList <String> AttackPath = new ArrayList <String>();
	//constructor
	chessPiece(int color){
		team = color;
	}
	//getters and setters
	public String getDisplayPiece() {
		return displayPiece;
	}
	public void setDisplayPiece(String x) {
		displayPiece = x; 
	}
	public int Getteam() {
		return team;
	}
	public void SetTeam(int t) {
		team = t; 
	}
	public Boolean GetEnPassant() {
		return EnPassant;
	}
	public void SetFirst(Boolean SF) {
		first = SF;
	}
	public Boolean getFirst() {
		return first;
	}
	//-------
	//you see if King is Check by essentially putting in the location of the King in the piece's move method
	//but without moving the pieces. 
	public boolean Check(int x, int y, int KingX, int KingY, chessPiece[][] theMatrix) {
		if (Move(x,y,KingX,KingY,theMatrix)) {
			return true; 
		}
		else return false; 
	}
	//will check if coordinates along path is blocked OR if target is a piece with opposing team marker. 
	public boolean Move(int oldX, int oldY, int newX, int newY, chessPiece [][] theMatrix) { //takes in x * y coordinates of new location. 
		return true; //returns true if it can move to the spot
	}
	//if checkMove is true AND target is an enemy piece. use Move() and delete 
	public void Remove(int oldX, int oldY, int newX, int newY, chessPiece [][] theMatrix) {
		theMatrix[newX][newY] = theMatrix[oldX][oldY]; //move pawn to new location
		theMatrix[oldX][oldY] = null; //set old location to null
	}
	
	
// an attribute for which team its on. 0 for white(team 1) and 1 for black(team 2).  	
// will contain a boolean move method to check if move is legal
// delete method to remove from matrix when attacked	
	
}

