
public class ChessRun {
	String TestInstructions [] = {"6141"}; 

	public static void main(String[] args) {
		//ChessRun something = new ChessRun(); 
		Chess newGame = new Chess();
		newGame.initalizeboard();
		newGame.Game();
		//newGame.Game(something.TestInstructions);
	}
	
}
