public class TicTacToeDriver {
	
	public static void main(String[] args) {
		
		TicTacToeGrid game = new TicTacToeGrid();
		game.play();
		if (game.prompt("Play Again?", "Yes", "No") == 1) game.newGame(); 
		else {game.say("Fine then don't have more fun..."); game.close(1000); }
		if (game.prompt("Did you enjoy the game?", "Yes", "No")==1) { game.say("Thank You."); game.close(1000);}
		else { game.say("Then I guess you need to play again."); game.close(1000);}
		
	}
}
