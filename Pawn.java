//if pawn reaches end of board, it can become Queen
public class Pawn extends chessPiece {
	private Boolean first = true; //assume first move until a move is done
	private Boolean EnPassant = false; 
	private int Movement; //movement as a result of team
	
	public Pawn(int color) {
		super(color);
		
		if (color == 0) { //if it equals white, we make movement 1 to go up the board
			Movement = -1; 
			this.setDisplayPiece("0P");
		}
		else {
			this.setDisplayPiece("1P");
			Movement = 1; //else it is black and moves down the board.
		}
	}
	public void SetFirst(Boolean SF) {
		first = SF;
	}
	public Boolean getFirst() {
		return first;
	}
	private void SetEnPassant(Boolean EP) {
		EnPassant = EP; 
	}
	public Boolean GetEnPassant() {
		return EnPassant;
	}
	//old row, old col, new row, new col 
	public boolean Move(int oldX, int oldY, int newX, int newY, chessPiece[][] theMatrix) { //takes in x * y coordinates of new location. 
		boolean pathClear = false;
		//first move is checking if it can move 2 places
		boolean SameColumn = (oldY == newY);
		boolean FirstMovement = (newX == oldX + Movement*(2));
		boolean AfirstMove = (this.first && FirstMovement && SameColumn); //could be an error
//		System.out.println("SameColumn: " + SameColumn);
//		System.out.println("FirstMovement: " + FirstMovement);
//		System.out.println("this.first:" + this.first);
//		System.out.println("Afirstmove: " + AfirstMove + "\n");
//		//En Passant (if there's two pawns, the user should be able to pick which one) * * * 
		if (AfirstMove) {
			//its failing in this if condition somewhere D E B U G 
			for (int i = oldX+Movement; i != newX; i = i + Movement) { //0 won't be clear since that is where the piece is
				if (theMatrix[i][oldY] == null) {
					//System.out.println("path cleared");
					pathClear = true; 
				}
				else pathClear = false; 
				//pathClear = (theMatrix[i][oldY] == null ? true : false); //check if path is clear	
			}
			chessPiece enPassantCheck;
			if (oldY-1 >= 0) {
				enPassantCheck = theMatrix[oldX + Movement*2][oldY-1];
				if (pathClear && enPassantCheck != null && enPassantCheck.Getteam() != this.Getteam() && 
					(enPassantCheck.getClass().getTypeName() == "Pawn")) {
					SetEnPassant(true);
			    	}
				}
			if (oldY + 1 <= 7) {
				enPassantCheck = theMatrix[oldX + Movement*2][oldY+1];
				if (pathClear && enPassantCheck != null && enPassantCheck.Getteam() != this.Getteam() && 
						(enPassantCheck.getClass().getTypeName() == "Pawn")) {
					SetEnPassant(true); 
				}
			}
			if (GetEnPassant() && pathClear) { // if enPassant is true, the newX && newY will be new location
				return true; 
			}
			else if(pathClear) {	//makes a first move
				return true;
			}
		}
		//ADD IN A CHECK TO SEE IF ENEMY PIECE EN PASSANT IS TRUE //MIGHT HAVE TO ADD IN A TEAM CHECK
		//regular move 
		else if(newX == oldX + Movement && newY == oldY && theMatrix[newX][newY] == null){
			this.first = false;
			return true;
		}//DIAGONAL ATTACK for PAWN
		else if((newY == oldY + 1 || newY == oldY - 1) && newX == oldX + Movement) {
				if (theMatrix[newX][newY] != null && theMatrix[newX][newY].Getteam() != this.Getteam()){
					return true; 
				}
				else if (theMatrix[newX][newY] == null && theMatrix[newX+((-1)*Movement)][newY].GetEnPassant() == true) {
					this.SetEnPassant(true);
					return true; 
				}
		}
		return false; //returns true if it can move to the spot
	}
}
