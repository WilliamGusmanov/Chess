//Questions: how would you save old moves so you can reverse the game?
//perhaps a linked list with a matrix of the chess piece positions as the elements.
//Should I store the pieces that have been eliminated so that when pawn reaches end of board. Pawn can be any dead piece.
//for movement, must check if anything is in the way between target and current location(except for knight)

//when a piece dies, it should enter a team's dead pile, 
import java.util.Scanner;
import java.util.*;
public class Chess { //the main chess game
		Scanner in = new Scanner(System.in);
		//used to take user input
		//ArrayList<chessPiece[][]> Team1Dead = new ArrayList<chessPiece[][]>(); //will hold dead pieces of team 1
		//ArrayList<chessPiece[][]> Team2Dead = new ArrayList<chessPiece[][]>(); //will hold dead pieces of team 2
		//LinkedList<chessPiece [][]> linkedList = new LinkedList<chessPiece[][]>(); //this will hold every play
		chessPiece [][] chessBoard = new chessPiece [8][8]; 
		// Key is the chessPiece. Element: A String containing current location, 2 chars "33" represents col 3 row 3. 
		HashMap <chessPiece, String> AllPieces = new HashMap <chessPiece, String>(); 
		ArrayList <chessPiece> AttackPieces = new ArrayList <chessPiece>();
		public static int turn = 0; //global variable that determines who's turn it is to play.
		//Player should be an object that has access to their own features * * * 
		//thus one player will have access to certain features while the other does not. 
		public boolean CheckMate = false; //used 
		King KingW = new King(0);
		King KingB = new King(1);
		//opposite side will be "opposing team" color will be determined if player 1 or player 2.
		//to see if a move is 'legal', see if a move returns no check. Then complete move
		//if a player wants to make a move. We place the location of where they want to move. See if that move 
		//creates a check. If it does not, then the move is valid. After that move is completed. We see if the other player is in check.
		
		//Check will be used along with move to make player moves.
		//takes in the team you want to see is in check and the HashMap of all pieces in the game.
		public void Game(String HardCoded[]) {
			int index = 0; 
			while(!CheckMate && index < HardCoded.length) { 					  //a while loop to keep game in motion, checkMate should always check opponent Player
				if (Check(turn) == true) { 			  // this is the 'second' type of Check, after previous player makes move, look for check. 
					CheckMate = CheckMate(turn); 		  //if this check returns true, the enemy piece will be added to attackPieces
					if (CheckMate == true) { 			  //if checkmate is false, a move is possible that removes the check.  
						System.out.println("Game Over."); //attack piece will be removed in the do while loop. 
						in.close(); 
						System.exit(0); //End of Game
					}							
				}
				if (turn == 0) System.out.println("White Turn");
				else System.out.println("Black Turn");
				displayBoard();
				String oxy;
				int ox;
				int oy;
				int nx;
				int ny;
				boolean validteam = false;
				boolean validmove = false;
				boolean check = false; 
				do {
				AttackPieces.clear();
				oxy = HardCoded[index]; //Used to hard code instructions into game. 	 
				ox = StringToInt(oxy,0); //old x
				oy = StringToInt(oxy,1); //old y
				validteam = chessBoard[ox][oy].Getteam() == turn;
				nx = StringToInt(oxy,2);
				ny = StringToInt(oxy,3);
				validmove = chessBoard[ox][oy].Move(ox, oy, nx, ny, chessBoard);
				check = Check(turn);
				
				if (!validmove || check) {
					System.out.println("invalid move");
				}
				if (!validteam) System.out.println("invalid team");
				
				} while(ox > 7 || oy > 7 || chessBoard[ox][oy] == null || !validteam || check || !validmove); //player chooses own piece
				PlayChessMove(ox,oy,nx,ny);
				if (chessBoard[nx][ny].GetEnPassant()) { //removes the piece it is en passanting. 
					enPassant(nx,ny);
					}
				changeTurn();
			}
			index++; 
		}
		public void Game() {
			while(!CheckMate) { 					  //a while loop to keep game in motion, checkMate should always check opponent Player
				if (Check(turn) == true) {
					System.out.println("You are in check."); // this is the 'second' type of Check, after previous player makes move, look for check. 
					CheckMate = CheckMate(turn); 		  	//if this check returns true, the enemy piece will be added to attackPieces
					if (CheckMate == true) { 			  	//if checkmate is false, a move is possible that removes the check.  
						System.out.println("Game Over."); 	//attack piece will be removed in the do while loop. 
						in.close(); 
						System.exit(0); //End of Game
					}							
				}
				if (turn == 0) System.out.println("White Turn");
				else System.out.println("Black Turn");
				displayBoard();
				String oxy;
				int ox;
				int oy;
				int nx;
				int ny;
				boolean validteam = false;
				boolean validmove = false;
				boolean check = false; 
				do {
				AttackPieces.clear();
				oxy = takeInput(); 	 
				ox = StringToInt(oxy,0); //old x
				oy = StringToInt(oxy,1); //old y
				validteam = chessBoard[ox][oy].Getteam() == turn;
				//System.out.println("validteam: " + validteam);
				nx = StringToInt(oxy,2);
				ny = StringToInt(oxy,3);
				validmove = chessBoard[ox][oy].Move(ox, oy, nx, ny, chessBoard);
				//System.out.println("validmove: "+validmove); //debug
				System.out.println("turn: " + turn);
				check = Check(turn);
				if (check) System.out.println("Still in check.");
				//System.out.println("check is " + check); //debug
				if (!validmove) {
					System.out.println("invalid move");
				}
				if (!validteam) System.out.println("Not your turn.");
				
				} while(ox > 7 || oy > 7 || chessBoard[ox][oy] == null || !validteam || check || !validmove); //player chooses own piece
				PlayChessMove(ox,oy,nx,ny);
				if (chessBoard[nx][ny].GetEnPassant()) { //removes the piece it is en passanting. 
					enPassant(nx,ny);
					}
				changeTurn();
			}
		}
			//if in the team's king is in check, it returns true.
		public boolean Check(int team) {//AB = all pieces, CB = chessBoard
			String Kcoordinate;
			Boolean inCheck = false;
			if (team == 0) {
				Kcoordinate = AllPieces.get(KingW); 
			}
			else {
				Kcoordinate = AllPieces.get(KingB);
			}
			int XintKing = StringToInt(Kcoordinate, 0);
			int YintKing = StringToInt(Kcoordinate,1);
			
			for (chessPiece piece : AllPieces.keySet()) {
				if (piece.Getteam() == team) { //if the piece is on the same side, ignore
					continue;
				}
				int Xint = StringToInt(AllPieces.get(piece), 0);  //returns the current x coordinate of the chess piece
				int Yint = StringToInt(AllPieces.get(piece), 1);  //returns the current y coordinate of the chess piece
				if (piece.Move(Xint, Yint, XintKing, YintKing, chessBoard)) {  //if this is true, then King can be reached
					AttackPieces.add(piece);
					inCheck = true; //can add multiple pieces to Attack Pieces
				}
			}
			if (inCheck) {
				return true; //return that the team is in check [then only moves that remove check are allowed]
			}
			else return false; 
		}
		private int StringToInt(String Coord, int charvalue) { //used to take the Key and return the coordinate value held inside. 
			
			char value = Coord.charAt(charvalue);
			int a = Integer.parseInt(String.valueOf(value)); 
			return a;
		}
		
		public String takeInput() {
			System.out.println("Enter [old row][old col][new row][new col]. Ex.) 2526 is row 2 col 5 to row 2 col 6");
			String answer = in.next();
			return(answer);
		}
		//moves pieces, updates coordinates in hashmap, puts dead piece in dead pile
		public void PlayChessMove(int ox, int oy, int nx, int ny) {
			chessBoard[ox][oy].SetFirst(false);
			if (chessBoard[nx][ny] != null) {
				AllPieces.remove(chessBoard[nx][ny]); //remove the enemy piece, can add to a dead list if you'd like 
			}
			String xs = Integer.toString(nx);
			String ys = Integer.toString(ny); //(POTENTIAL ERROR) 
			xs = xs.concat(ys);
			chessBoard[nx][ny] = chessBoard[ox][oy]; //move new piece 
			chessBoard[ox][oy] = null; //set old location to null
			AllPieces.replace(chessBoard[nx][ny], xs); //update hash map (POTENTIAL ERROR)
		}
		public boolean CheckMate(int team) { 
			Boolean MoveKing = false; 
			Boolean AttackAttacker = false;
			Boolean BlockAttacker = false;
			String Kcoordinate;
			King K;
			if (team == 0) {
				Kcoordinate = AllPieces.get(KingW); 
				K = KingW; 
			}
			else {
				Kcoordinate = AllPieces.get(KingB);
				K = KingB;
			}
			int XintKing = StringToInt(Kcoordinate, 0); //x-coordinate of King
			int YintKing = StringToInt(Kcoordinate,1); //y-coordinate of King
			
			//check if the King can make a move
			//Conditions: if coordinate is within chess board, if a movement for King has not been found, and up to King maximum movement
			for(int i = XintKing; i <= 7 && i <= XintKing + 1 && !MoveKing; i++) { 
			for(int j = YintKing; j <= 7 && j <= YintKing + 1 && !MoveKing; j++) {
				MoveKing = K.Move(XintKing, YintKing, i, j, chessBoard);
			}
			}//if you have two pieces attacking, you have to move the king. 
			if (!MoveKing && AttackPieces.size() < 2) { //check if we find enemy piece and see if it can be killed. //
				chessPiece Attacker = AttackPieces.get(0);
				int XintA = StringToInt(AllPieces.get(Attacker), 0);  //returns the current x coordinate of the chess Defender
				int YintA = StringToInt(AllPieces.get(Attacker), 1);  //returns the current y coordinate of the chess Defender
				for (chessPiece Defender : AllPieces.keySet()) {
						if (Defender.Getteam() != team) { //if the Defender is on the other side, ignore
							continue; //might not work
						}
						int XintD = StringToInt(AllPieces.get(Defender),0); //get x coordinate of defender piece
						int YintD = StringToInt(AllPieces.get(Defender),1); //get y coordinate of defender piece
						//if defending piece can reach attacking piece, then a move is possible
						//Attacker.Move(XintA, YintA, XintKing, YintKing, chessBoard); //Might not need this (used to populate attack array)
						for (int i = 0; i < Attacker.AttackPath.size() && !BlockAttacker; i++) { //USED TO SEE IF ATTACK PATH CAN BE BLOCKED
							int XintP = StringToInt(Attacker.AttackPath.get(i),0);
							int YintP = StringToInt(Attacker.AttackPath.get(i),1);
						BlockAttacker = Defender.Move(XintD, YintD, XintP, YintP, chessBoard);
						}
						AttackAttacker = Defender.Move(XintD, YintD, XintA, YintA, chessBoard);   //if this is true, then King can be reached
						}
			}
		if (AttackAttacker || BlockAttacker || MoveKing) {
			return false; //not checkmate
		}
		else return true; 
		} 
		
		private void enPassant(int nx, int ny) {
			//this piece is in new spot, all this does is remove the piece that is in en passant that is above or below it.
			int Movement; //movement as a result of team
			if (turn == 0) { //if it equals white, we make movement 1 to go up the board and check piece down it
					Movement = 1; 
				}
				else {
					Movement = -1; //else it is black and moves down the board.
				}
			chessBoard[nx+Movement][ny] = null;
			AllPieces.remove(chessBoard[nx+Movement][ny]);
		}
		private void changeTurn() {
			if (turn == 0) {
				turn = 1;
			}
			else turn = 0;
		}
		//Pieces will be put in correct positions, added to hashmap
		//x: cols. y: rows, where board goes down
		public void initalizeboard() { //initialize an 8 x 8 chess board
			System.out.println("   - - - Welcome to William's Chess Game - - - \n");
			//white team 
			Pawn P1w = new Pawn(0); 
			Pawn P2w = new Pawn(0);
			Pawn P3w = new Pawn(0);
			Pawn P4w = new Pawn(0);
			Pawn P5w = new Pawn(0);
			Pawn P6w = new Pawn(0);
			Pawn P7w = new Pawn(0);
			Pawn P8w = new Pawn(0);
			AllPieces.put(P1w, "60"); //row 6 col 0
			AllPieces.put(P2w, "61"); //row 6 col 1
			AllPieces.put(P3w, "62");
			AllPieces.put(P4w, "63");
			AllPieces.put(P5w, "64");
			AllPieces.put(P6w, "65");
			AllPieces.put(P7w, "66");
			AllPieces.put(P8w, "67");
			chessBoard[6][0] = P1w; //row 0, col 6
			chessBoard[6][1] = P2w;
			chessBoard[6][2] = P3w;
			chessBoard[6][3] = P4w;
			chessBoard[6][4] = P5w;
			chessBoard[6][5] = P6w;
			chessBoard[6][6] = P7w;
			chessBoard[6][7] = P8w;
			Rook R1w = new Rook(0);
			Rook R2w = new Rook(0);
			AllPieces.put(R1w, "70");
			AllPieces.put(R2w, "77");
			chessBoard[7][0] = R1w; 
			chessBoard[7][7] = R2w;
			Knight H1w = new Knight(0);
			Knight H2w = new Knight(0);
			AllPieces.put(H1w,"71");
			AllPieces.put(H2w,"76");
			chessBoard[7][1] = H1w;
			chessBoard[7][6] = H2w;
			Bishop B1w = new Bishop(0);
			Bishop B2w = new Bishop(0);
			AllPieces.put(B1w, "72");
			AllPieces.put(B2w, "75");
			chessBoard[7][2] = B1w;
			chessBoard[7][5] = B2w;
			Queen Qw = new Queen(0);
			AllPieces.put(Qw, "73");
			chessBoard[7][3] = Qw; 
			AllPieces.put(KingW, "74");
			chessBoard[7][4] = KingW;
			//black team
			Pawn P1b = new Pawn(1); 
			Pawn P2b = new Pawn(1);
			Pawn P3b = new Pawn(1);
			Pawn P4b = new Pawn(1);
			Pawn P5b = new Pawn(1);
			Pawn P6b = new Pawn(1);
			Pawn P7b = new Pawn(1);
			Pawn P8b = new Pawn(1);
			AllPieces.put(P1b, "10"); 
			AllPieces.put(P2b, "11"); 
			AllPieces.put(P3b, "12");
			AllPieces.put(P4b, "13");
			AllPieces.put(P5b, "14");
			AllPieces.put(P6b, "15");
			AllPieces.put(P7b, "16");
			AllPieces.put(P8b, "17");
			chessBoard[1][0] = P1b;
			chessBoard[1][1] = P2b;
			chessBoard[1][2] = P3b;
			chessBoard[1][3] = P4b;
			chessBoard[1][4] = P5b;
			chessBoard[1][5] = P6b;
			chessBoard[1][6] = P7b;
			chessBoard[1][7] = P8b;
			Rook R1b = new Rook(1);
			Rook R2b = new Rook(1);
			AllPieces.put(R1b, "00");
			AllPieces.put(R2b, "07");
			chessBoard[0][0] = R1b;
			chessBoard[0][7] = R2b;
			Knight H1b = new Knight(1);
			Knight H2b = new Knight(1);
			AllPieces.put(H1b,"01");
			AllPieces.put(H2b,"06");
			chessBoard[0][1] = H1b;
			chessBoard[0][6] = H2b;
			Bishop B1b = new Bishop(1);
			Bishop B2b = new Bishop(1);
			AllPieces.put(B1b, "02");
			AllPieces.put(B2b, "05");
			chessBoard[0][2] = B1b;
			chessBoard[0][5] = B2b;
			Queen Qb = new Queen(1);
			AllPieces.put(Qb, "03");
			chessBoard[0][3] = Qb;
			AllPieces.put(KingB, "04");
			chessBoard[0][4] = KingB; 
		}
		
		public void displayBoard() { //[col][row] = [x][y]
			System.out.println("        Cols");
			System.out.print("       ");
			for (int k = 0; k < 8; k++) {
			System.out.print("  "+ k + "   ");
		}
		System.out.println();
		for (int i = 0; i < chessBoard.length; i++) { //this iterates through the rows
			if (i != 0) System.out.print("      " + i);
			if (i == 0) System.out.print("Rows  " + i);
			for (int j = 0; j < chessBoard[i].length; j++) { //this iterates through the columns
			
			if (chessBoard[i][j] != null) {
			System.out.print(" [" + chessBoard[i][j].getDisplayPiece() + "] ");
		}
			else System.out.print(" [  ] ");
		}
		System.out.println("\n");
		}
		}	
}

