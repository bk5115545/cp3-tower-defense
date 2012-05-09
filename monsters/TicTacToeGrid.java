/**
 * @author Bryan Koch
 * @since August 26, 2011
 * 
 * Creates a Tic-Tac-Toe board with one Human Player and one Computer Player.
 * The Human Player must have a Mouse-type input device.
 * The Computer Player's moves are conditionally caculated by canWin() and canBlock().  Other Computer moves are decided randomly from empty spaces.
 * 
 * To use one must create a new Instance of TicTacToeGrid and call play().  All rules are predetermined and are not ment to be changed.
 * The game created uses a classic grid of 3x3 spaces.  User and Computer will take turns until one gets 3 X's or O's in a row.  Whoever goes first is       
 * decided randomly at runtime.
 * 
 * Please no cheating (like thats possible) and play again sometime.
 * 
 * 
 * Known Bugs:  User may click on invalid pososition.  In this case, no cpu data is changed.  It is like the user lost a turn. :: FIXED
 * 
 **/


import java.awt.Point;


public class TicTacToeGrid
{
	private int[][] board;
	private int x = 0; int y = 0;  //mouse posisition
	private int[] oldQuadrents = new int[9];  //where the user or computer already gone
	private int b = 0; //index of oldQuadrents[]
	private int quadrant = 0; //the quadrant clicked by the user.  Inputted into oldQuadrents[b] if not already exists in oldQuadrents[b].
	private boolean cpuHasWon = false; //relaying game-state to other methods
	private boolean isX = false; //is the user X or not
	Turtle one;

	
	
	/**
	 * The center points of the board spaces to be used to accuretly draw the X's and O's.
	 **/
	//top 3
	private Point q1 = new Point(150, 350);
	private Point q2 = new Point(250, 350);
	private Point q3 = new Point(350, 350);

	//mid 3
	private Point q4 = new Point(150, 250);
	private Point q5 = new Point(250, 250);
	private Point q6 = new Point(350, 250);

	//bottom 3
	private Point q7 = new Point(150, 150);
	private Point q8 = new Point(250, 150);
	private Point q9 = new Point(350, 150);

	//void point :: used to determine if there's no valid moving condition for computer
	private Point q10 = new Point(0, 0);

	
	
	
	
	/**
	 * Creates a new Tic Tac Toe board and allocates all internal valuse to -1.  First player is assigned randomly.
	 **/
	public TicTacToeGrid()
	{	
		board = new int[3][3];
		one = new Turtle();
		one.worldCoordinates(0, 0, 500, 500);

		randomFirstPlayer();
		for (int i = 0; i < 3; i++)
		for (int j = 0; j < 3; j++) board[i][j] = -1; //all starting values are -1
	}
	
	
	/**
	 * If the supplied arg is "x" then user is always X
	 * if the supplied arg is "o" then user is always O
	 * If the supplied arg is not "x" or "o" then player is chosen randomly
	 **/
	public TicTacToeGrid(char ch)
	{
		if ((ch == 'x') || (ch == 'X')) isX = true;  //user is X
		else if ((ch == 'o') || (ch == 'O')) isX = false;  //user is O
		else randomFirstPlayer();
    
		one = new Turtle();
		one.worldCoordinates(0, 0, 500, 500);
		one.clear();
		board = new int[3][3];
		for (int i = 0; i < 3; i++)
		for (int j = 0; j < 3; j++) board[i][j] = -1; //all starting values are -1
	}

	/**
	 * Starts a new game by changeing the Pointers of everything.
	 * When I get it working properly it will be made public, until then just make a new TicTacToeGrid and use that.
	 **/
	public void newGame()
	{
		x = 0; y = 0; //mouse posisition is reset to invalid coordinates
		quadrant = 0; //starting quadrant is nonexistent
		int i = 0; //mouse posisition
		oldQuadrents = new int[9]; //where the user or computer already gone
		b = 0; //index of oldQuadrents[]
		cpuHasWon = false; //relaying game-state to other methods
		isX = false; //is the user X or not
		one = new Turtle();

		one.worldCoordinates(0, 0, 500, 500);

		randomFirstPlayer();
		board = new int[3][3];
		for (int j = 0; j < 3; j++) 
			for (int k = 0; k < 3; k++) board[j][k] = -1; //all starting values are -1
		
		play();
	}


	/**
	 * Draws the tic-tac-toe board in the gui window.
	 * Uses Princeton's Standard Draw class.
	 **/
	private void drawBoard()
	{
		StdDraw.line(200, 100, 200, 400);
		StdDraw.line(300, 100, 300, 400);
		StdDraw.line(100, 200, 400, 200);
		StdDraw.line(100, 300, 400, 300);
	}


	/**
	 * Method to choose the first player based on if the user is X or O
	 **/
	private void randomFirstPlayer()
	{
		if (Math.random() < 0.5) isX = true;
	}

	
		/**
		* Records the X and Y constantly until the mouse is clicked.
		* When the mouse is clicked x and y are stored as class variables.
		**/
	private void updateMouse()
	{	
		while (!StdDraw.mousePressed()) {
			one.delay(50);
			x = (int)StdDraw.mouseX();
			y = (int)StdDraw.mouseY();
		}
	}
	

	/**
	 * Contains all code to draw the user's X or O on the clicked space.  
	 * Conditions determine in which quadrent the user clicks and board[][] is updated to be read by the computer player.
	 * Records the clicked quadrant and checks if space has already been selected by either computer of player
	 **/
	private void drawUser()
	{
		boolean right=true;
		do {
			right=true;
			updateMouse();
			
			//top 3 spaces
			if ((x > 100) && (x < 200) && (y > 300) && (y < 400) && (board[0][0] < 1)) { board[0][0] = 1; quadrant = 1; }
			if ((x > 200) && (x < 300) && (y > 300) && (y < 400) && (board[0][1] < 1)) { board[0][1] = 1; quadrant = 2; }
			if ((x > 300) && (x < 400) && (y > 300) && (y < 400) && (board[0][2] < 1)) { board[0][2] = 1; quadrant = 3; }
			
			//middle 3 spaces
			if ((x > 100) && (x < 200) && (y > 200) && (y < 300) && (board[1][0] < 1)) { board[1][0] = 1; quadrant = 4; }
			if ((x > 200) && (x < 300) && (y > 200) && (y < 300) && (board[1][1] < 1)) { board[1][1] = 1; quadrant = 5; }
			if ((x > 300) && (x < 400) && (y > 200) && (y < 300) && (board[1][2] < 1)) { board[1][2] = 1; quadrant = 6; }
			
			//bottom 3 spaces
			if ((x > 100) && (x < 200) && (y > 100) && (y < 200) && (board[2][0] < 1)) { board[2][0] = 1; quadrant = 7; }
			if ((x > 200) && (x < 300) && (y > 100) && (y < 200) && (board[2][1] < 1)) { board[2][1] = 1; quadrant = 8; }
			if ((x > 300) && (x < 400) && (y > 100) && (y < 200) && (board[2][2] < 1)) { board[2][2] = 1; quadrant = 9; }
			
			//check if we've slready been there :: if yes click a different spot
			for (int j = 0; j < 9; j++) {
				right = oldQuadrents[j] == quadrant ? false:right;
			}
		} while (!right);

		oldQuadrents[b] = quadrant;
		b++;

		one.up();
		if (quadrant == 1) one.goTo(q1.getX(), q1.getY());
		if (quadrant == 2) one.goTo(q2.getX(), q2.getY());
		if (quadrant == 3) one.goTo(q3.getX(), q3.getY());
		if (quadrant == 4) one.goTo(q4.getX(), q4.getY());
		if (quadrant == 5) one.goTo(q5.getX(), q5.getY());
		if (quadrant == 6) one.goTo(q6.getX(), q6.getY());
		if (quadrant == 7) one.goTo(q7.getX(), q7.getY());
		if (quadrant == 8) one.goTo(q8.getX(), q8.getY());
		if (quadrant == 9) one.goTo(q9.getX(), q9.getY());
		one.down();

		if (isX) one.write("X");
		if (!isX) one.write("O");
	}


	/**
	 * @return true/false True if the user has met winning conditions and false otherwise.
	 **/
	private boolean userHasWon()
	{	
		//hoorizontal winning conditions
		if ((board[0][0] == 1) && (board[0][1] == 1) && (board[0][2] == 1)) return true;
		if ((board[1][0] == 1) && (board[1][1] == 1) && (board[1][2] == 1)) return true;
		if ((board[2][0] == 1) && (board[2][1] == 1) && (board[2][2] == 1)) return true;
		
		//vertical winning conditions
		if ((board[0][0] == 1) && (board[1][0] == 1) && (board[2][0] == 1)) return true;
		if ((board[0][1] == 1) && (board[1][1] == 1) && (board[2][1] == 1)) return true;
		if ((board[0][2] == 1) && (board[1][2] == 1) && (board[2][2] == 1)) return true;
		
		//diagonal winning conditions
		if ((board[0][0] == 1) && (board[1][1] == 1) && (board[2][2] == 1)) return true;
		if ((board[0][2] == 1) && (board[1][1] == 1) && (board[2][0] == 1)) return true;
		
		//if called then the usre has not met any of the winning conditions
		return false;
	
	}


	/**
	 * Determines if the computer can win.
	 * @return Point The winning space fill.
	 * Contains all possible winning conditions and checks if the space is filled before returning the winning point.
	 * Returns q10, the invalid point that is considered null.  Means that the computer cannot win.
	 **/
	private Point canWin()
	{
		//top hooriziontal
		if ((board[0][0] == 2) && (board[0][2] == 2) && (board[0][1] < 1)) return q2;
		if ((board[0][1] == 2) && (board[0][2] == 2) && (board[0][0] < 1)) return q1;
		if ((board[0][0] == 2) && (board[0][1] == 2) && (board[0][2] < 1)) return q3;

		//middle hooriziontal 
		if ((board[1][1] == 2) && (board[1][2] == 2) && (board[1][0] < 1)) return q4;
		if ((board[1][0] == 2) && (board[1][2] == 2) && (board[1][1] < 1)) return q5;
		if ((board[1][0] == 2) && (board[1][1] == 2) && (board[1][2] < 1)) return q6;

		//bottom hooriziontal
		if ((board[2][1] == 2) && (board[2][2] == 2) && (board[2][0] < 1)) return q7;
		if ((board[2][0] == 2) && (board[2][2] == 2) && (board[2][1] < 1)) return q8;
		if ((board[2][0] == 2) && (board[2][1] == 2) && (board[2][2] < 1)) return q9;

		//vertical left
		if ((board[0][0] == 2) && (board[2][0] == 2) && (board[1][0] < 1)) return q4;
		if ((board[1][0] == 2) && (board[2][0] == 2) && (board[0][0] < 1)) return q1;
		if ((board[0][0] == 2) && (board[1][0] == 2) && (board[2][0] < 1)) return q7;

		//vertical middle
		if ((board[0][1] == 2) && (board[2][1] == 2) && (board[1][1] < 1)) return q5;
		if ((board[1][1] == 2) && (board[2][1] == 2) && (board[0][1] < 1)) return q2;
		if ((board[0][1] == 2) && (board[1][1] == 2) && (board[2][1] < 1)) return q8;

		//vertical right
		if ((board[0][2] == 2) && (board[2][2] == 2) && (board[1][2] < 1)) return q6;
		if ((board[1][2] == 2) && (board[2][2] == 2) && (board[0][2] < 1)) return q3;
		if ((board[0][2] == 2) && (board[1][2] == 2) && (board[2][2] < 1)) return q9;

		//diagonals
		if ((board[0][0] == 2) && (board[2][2] == 2) && (board[1][1] < 1)) return q5;
		if ((board[0][2] == 2) && (board[2][0] == 2) && (board[2][2] < 1)) return q5;
		if ((board[1][1] == 2) && (board[2][2] == 2) && (board[0][0] < 1)) return q1;
		if ((board[0][2] == 2) && (board[1][1] == 2) && (board[2][0] < 1)) return q7;
		if ((board[1][1] == 2) && (board[2][0] == 2) && (board[0][2] < 1)) return q3;
		if ((board[0][0] == 2) && (board[1][1] == 2) && (board[2][2] < 1)) return q9;

		//if called then the computer has not met any of the winning conditions
		return q10;
	}


	/**
	 * Determines if the computer can block.
	 * @return Point The block point.
	 * Contains all possible blocking conditions and checks if the space is filled before returning the block point.
	 * Returns q10, the invalid point that is considered null.  Means that the computer cannot block.
	 **/
	private Point canBlock()
	{
		if (((board[0][1] == 1) && (board[0][2] == 1)) || ((board[2][2] == 1) && (board[1][1] == 1)) || ((board[1][0] == 1) && (board[2][0] == 1)) && (board[0][0] < 1)) return q1;
		if (((board[0][0] == 1) && (board[0][2] == 1)) || ((board[2][1] == 1) && (board[1][1] == 1)) && (board[0][1] < 1)) return q2;
		if (((board[0][0] == 1) && (board[0][1] == 1)) || ((board[2][0] == 1) && (board[1][1] == 1)) || ((board[2][2] == 1) && (board[1][2] == 1)) && (board[0][2] < 1)) return q3;

		if (((board[1][2] == 1) && (board[1][1] == 1)) || ((board[0][0] == 1) && (board[2][0] == 1)) && (board[1][0] < 1)) return q4;
		if (((board[1][0] == 1) && (board[1][2] == 1)) || ((board[0][0] == 1) && (board[2][2] == 1)) || ((board[2][0] == 1) && (board[0][2] == 1)) || ((board[0][1] == 1) && (board[2][1] == 1)) && (board[1][1] < 1)) return q5;
		if (((board[1][1] == 1) && (board[1][0] == 1)) || ((board[0][2] == 1) && (board[2][2] == 1) && (board[1][2] < 1))) return q6;

		if (((board[0][0] == 1) && (board[1][0] == 1)) || ((board[1][1] == 1) && (board[0][2] == 1)) || ((board[2][1] == 1) && (board[2][2] == 1)) && (board[2][0] < 1)) return q7;
		if (((board[1][1] == 1) && (board[0][1] == 1)) || ((board[2][0] == 1) && (board[2][2] == 1)) && (board[2][1] < 1)) return q8;
		if (((board[2][0] == 1) && (board[2][1] == 1)) || ((board[0][0] == 1) && (board[1][1] == 1)) || ((board[0][2] == 1) && (board[1][2] == 1)) && (board[2][2] < 1)) return q9;
		
		//called if computer can not block
		return q10;

	}


	/**
	 * Determines if the computer can set itself up for a win
	 * @return SET_UP_POINT The point that the computer will go to to set itself up for a win
	 **/
	private Point canSetUp()
	{
		//top left 
		if ((board[0][0] == 2) && (board[0][1] < 1) && (board[0][2] < 1)) return q2;
		if ((board[0][0] == 2) && (board[1][1] < 1) && (board[2][2] < 1)) return q5;
		if ((board[0][0] == 2) && (board[1][0] < 1) && (board[2][0] < 1)) return q4;

		//top mid
		if ((board[0][1] == 2) && (board[0][0] < 1) && (board[0][2] < 1)) return q3;
		if ((board[0][1] == 2) && (board[1][1] < 1) && (board[2][1] < 1)) return q5;

		//top right
		if ((board[0][2] == 2) && (board[0][0] < 1) && (board[0][1] < 1)) return q1;
		if ((board[0][2] == 2) && (board[1][1] < 1) && (board[2][0] < 1)) return q5;
		if ((board[0][2] == 2) && (board[0][2] < 1) && (board[2][2] < 1)) return q9;

		//middle left
		if ((board[1][0] == 2) && (board[0][0] < 1) && (board[2][0] < 1)) return q1;
		if ((board[1][0] == 2) && (board[1][1] < 1) && (board[1][2] < 1)) return q5;

		//middle middle
		if ((board[1][1] == 2) && (board[1][0] < 1) && (board[1][2] < 1)) return q6;
		if ((board[1][1] == 2) && (board[0][1] < 1) && (board[2][1] < 1)) return q8;
		if ((board[1][1] == 2) && (board[0][2] < 1) && (board[2][0] < 1)) return q7;
		if ((board[1][1] == 2) && (board[0][0] < 1) && (board[2][2] < 1)) return q9;

		//middle right
		if ((board[1][2] == 2) && (board[0][2] < 1) && (board[2][2] < 1)) return q3;
		if ((board[1][2] == 2) && (board[1][1] < 1) && (board[1][0] < 1)) return q5;

		//bottom left
		if ((board[2][0] == 2) && (board[2][1] < 1) && (board[2][2] < 1)) return q8;
		if ((board[2][0] == 2) && (board[0][0] < 1) && (board[1][0] < 1)) return q4;
		if ((board[2][0] == 2) && (board[1][1] < 1) && (board[0][2] < 1)) return q5;

		//bottom middle
		if ((board[2][1] == 2) && (board[2][0] < 1) && (board[2][2] < 1)) return q9;
		if ((board[2][1] == 2) && (board[0][1] < 1) && (board[1][1] < 1)) return q5;

		//bottom right
		if ((board[2][2] == 2) && (board[2][1] < 1) && (board[2][0] < 1)) return q8;
		if ((board[2][2] == 2) && (board[0][2] < 1) && (board[1][2] < 1)) return q6;
		if ((board[2][2] == 2) && (board[1][1] < 1) && (board[0][0] < 1)) return q5;
		
		//returned if the computer cannot set itself up for a win
		return q10;
	}


	/**
	* Contains all ways for the computer to move.  The computer will only do the first available move; It will not move more than once for each turn.
	* The computer first attempts to win.  
	* It then blocks the user.  If user does not need to be blocked or there is not an open space to block the user then nothing happens here.
	* If the user does not need to be blocked then the computer attempts to set itself up, for a win, off of its other moves.
	* If the computer has not moved then a random, unfilled space, is filled.
	**/
	private int moveComp()
	{	
		boolean hasMoved=false;
		boolean right=true;

		Point winPoint = canWin();
		if ((winPoint != q10) && (!hasMoved)) {
			
			one.up();
			one.goTo(winPoint.getX(), winPoint.getY());
			if (((int)one.x() == (int)q1.getX()) && ((int)one.y() == (int)q1.getY())) { board[0][0] = 2; quadrant = 1; }
			if (((int)one.x() == (int)q2.getX()) && ((int)one.y() == (int)q2.getY())) { board[0][1] = 2; quadrant = 2; }
			if (((int)one.x() == (int)q3.getX()) && ((int)one.y() == (int)q3.getY())) { board[0][2] = 2; quadrant = 3; }
			if (((int)one.x() == (int)q4.getX()) && ((int)one.y() == (int)q4.getY())) { board[1][0] = 2; quadrant = 4; }
			if (((int)one.x() == (int)q5.getX()) && ((int)one.y() == (int)q5.getY())) { board[1][1] = 2; quadrant = 5; }
			if (((int)one.x() == (int)q6.getX()) && ((int)one.y() == (int)q6.getY())) { board[1][2] = 2; quadrant = 6; }
			if (((int)one.x() == (int)q7.getX()) && ((int)one.y() == (int)q7.getY())) { board[2][0] = 2; quadrant = 7; }
			if (((int)one.x() == (int)q8.getX()) && ((int)one.y() == (int)q8.getY())) { board[2][1] = 2; quadrant = 8; }
			if (((int)one.x() == (int)q9.getX()) && ((int)one.y() == (int)q9.getY())) { board[2][2] = 2; quadrant = 9; }
			one.down();

		for (int k = 0; k < 9; k++) {
			right = oldQuadrents[k] == quadrant ? false : right;
		}
		
		if ((isX) && (right)) one.write("O");
		else if (right) one.write("X");
		
		if (right) {
			hasMoved = true;
			cpuHasWon = true;
			return 0;
		}

    }

		Point blockPoint = canBlock();
		if ((blockPoint != q10) && (!hasMoved)) {
		right=true;
		
		  one.up();
		  one.goTo(blockPoint.getX(), blockPoint.getY());
		  if (((int)one.x() == (int)q1.getX()) && ((int)one.y() == (int)q1.getY())) { board[0][0] = 2; quadrant = 1; }
		  if (((int)one.x() == (int)q2.getX()) && ((int)one.y() == (int)q2.getY())) { board[0][1] = 2; quadrant = 2; }
		  if (((int)one.x() == (int)q3.getX()) && ((int)one.y() == (int)q3.getY())) { board[0][2] = 2; quadrant = 3; }
		  if (((int)one.x() == (int)q4.getX()) && ((int)one.y() == (int)q4.getY())) { board[1][0] = 2; quadrant = 4; }
		  if (((int)one.x() == (int)q5.getX()) && ((int)one.y() == (int)q5.getY())) { board[1][1] = 2; quadrant = 5; }
		  if (((int)one.x() == (int)q6.getX()) && ((int)one.y() == (int)q6.getY())) { board[1][2] = 2; quadrant = 6; }
		  if (((int)one.x() == (int)q7.getX()) && ((int)one.y() == (int)q7.getY())) { board[2][0] = 2; quadrant = 7; }
		  if (((int)one.x() == (int)q8.getX()) && ((int)one.y() == (int)q8.getY())) { board[2][1] = 2; quadrant = 8; }
		  if (((int)one.x() == (int)q9.getX()) && ((int)one.y() == (int)q9.getY())) { board[2][2] = 2; quadrant = 9; }
		  one.down();
		
		  for (int m = 0; m < 9; m++) {
			  right = oldQuadrents[m] == quadrant ? false : right;
		  }
		  if ((isX) && (right)) one.write("O");
		  else if (right) one.write("X");

		  if (right) {  
		      oldQuadrents[b] = quadrant;
		  	  b++;
			  hasMoved=true;
			  return 0;
		  }

		}

		Point setUpPoint = canSetUp();
		if ((!hasMoved) && (setUpPoint != q10)) {
			right=true;
			one.up();
		  one.goTo(setUpPoint.getX(), setUpPoint.getY());
		  if (((int)one.x() == (int)q1.getX()) && ((int)one.y() == (int)q1.getY())) { board[0][0] = 2; quadrant = 1; }
		  if (((int)one.x() == (int)q2.getX()) && ((int)one.y() == (int)q2.getY())) { board[0][1] = 2; quadrant = 2; }
		  if (((int)one.x() == (int)q3.getX()) && ((int)one.y() == (int)q3.getY())) { board[0][2] = 2; quadrant = 3; }
		  if (((int)one.x() == (int)q4.getX()) && ((int)one.y() == (int)q4.getY())) { board[1][0] = 2; quadrant = 4; }
		  if (((int)one.x() == (int)q5.getX()) && ((int)one.y() == (int)q5.getY())) { board[1][1] = 2; quadrant = 5; }
		  if (((int)one.x() == (int)q6.getX()) && ((int)one.y() == (int)q6.getY())) { board[1][2] = 2; quadrant = 6; }
		  if (((int)one.x() == (int)q7.getX()) && ((int)one.y() == (int)q7.getY())) { board[2][0] = 2; quadrant = 7; }
		  if (((int)one.x() == (int)q8.getX()) && ((int)one.y() == (int)q8.getY())) { board[2][1] = 2; quadrant = 8; }
		  if (((int)one.x() == (int)q9.getX()) && ((int)one.y() == (int)q9.getY())) { board[2][2] = 2; quadrant = 9; }
		  one.down();

		for (int n = 0; n < 9; n++) {
			right = oldQuadrents[n] == quadrant ? false : right;
		}
		if ((isX == true) && (right)) one.write("O");
		else if (right) one.write("X");

		if (right) {
			oldQuadrents[b] = quadrant;
			b++;
			hasMoved=true;
			return 0;
		}

	}

		if (!hasMoved) {
			  Point randomPoint = q10;
			  do {
					right=true;

					int ran = (int)(Math.random() * 9.0) + 1;
					if ((ran == 8) && (board[2][1] < 1)) randomPoint = q8;
					if ((ran == 1) && (board[0][0] < 1)) randomPoint = q1;
					if ((ran == 6) && (board[1][2] < 1)) randomPoint = q6;
					if ((ran == 3) && (board[0][2] < 1)) randomPoint = q3;
					if ((ran == 5) && (board[1][1] < 1)) randomPoint = q5;
					if ((ran == 2) && (board[0][1] < 1)) randomPoint = q2;
					if ((ran == 7) && (board[2][0] < 1)) randomPoint = q7;
					if ((ran == 4) && (board[1][0] < 1)) randomPoint = q4;
					if ((ran == 9) && (board[2][2] < 1)) randomPoint = q9;

					one.up();
					one.goTo(randomPoint.getX(), randomPoint.getY());

					if (((int)one.x() == (int)q1.getX()) && ((int)one.y() == (int)q1.getY())) { board[0][0] = 2; quadrant = 1; }
					if (((int)one.x() == (int)q2.getX()) && ((int)one.y() == (int)q2.getY())) { board[0][1] = 2; quadrant = 2; }
					if (((int)one.x() == (int)q3.getX()) && ((int)one.y() == (int)q3.getY())) { board[0][2] = 2; quadrant = 3; }
					if (((int)one.x() == (int)q4.getX()) && ((int)one.y() == (int)q4.getY())) { board[1][0] = 2; quadrant = 4; }
					if (((int)one.x() == (int)q5.getX()) && ((int)one.y() == (int)q5.getY())) { board[1][1] = 2; quadrant = 5; }
					if (((int)one.x() == (int)q6.getX()) && ((int)one.y() == (int)q6.getY())) { board[1][2] = 2; quadrant = 6; }
					if (((int)one.x() == (int)q7.getX()) && ((int)one.y() == (int)q7.getY())) { board[2][0] = 2; quadrant = 7; }
					if (((int)one.x() == (int)q8.getX()) && ((int)one.y() == (int)q8.getY())) { board[2][1] = 2; quadrant = 8; }
					if (((int)one.x() == (int)q9.getX()) && ((int)one.y() == (int)q9.getY())) { board[2][2] = 2; quadrant = 9; }
					one.down();

					for (int w = 0; w < oldQuadrents.length; w++) {
						right = oldQuadrents[w] == quadrant ? false : right;
					}

				if ((isX) && (right)) { one.write("O"); } 
				else if (right) one.write("X");
				} while (!right);

				if (right) {
					oldQuadrents[b] = quadrant;
					b++;
					hasMoved=true;
					return 0;
				}

			}

		return -2;
	}



	/**
	 * Contains all rules for gameplay and stops game upon a winning or losing condition being reached.
	 * @return status The String containing the status of the game.  "The Computer Has Won." | "The Almighty User Has Won" | "CAT GAME - NO WINNER"
	 **/
	public String play()
	{
		drawBoard();
		
		if (!isX) {
		for (int i = 0; i < 5; i++)
		{
			moveComp();
			if (cpuHasWon) {
			one.up();
			one.goTo(250, 450);
			one.penColor("red");
			one.write("The Computer Has Won.");
			one.down();
			one.delay(1000);
			return "The Computer Has Won.";
        }

		if (i == 4) {
          one.up();
          one.goTo(250, 450);
          one.penColor("GOLD");
          one.write("CAT GAME - NO WINNER");
          one.delay(1000);
          return "CAT";
        }

        drawUser();
        if (userHasWon()) {
          one.up();
          one.goTo(250, 450);
          one.penColor("BLUE");
          one.write("Congradulations, You have beaten a dumb machine.");
          one.down();
          return "The Almighty User Has Won";
        }

        if (i == 4) {
          one.up();
          one.goTo(250, 450);
          one.penColor("GOLD");
          one.write("CAT GAME - NO WINNER");
          one.delay(1000);
          return "CAT";
			}
		}
	}
    else for (int i = 0; i < 5; i++)
		{
			drawUser();
		if (userHasWon()) {
			one.up();
			one.goTo(250, 450);
			one.penColor("BLUE");
			one.write("Congradulations, You have beaten a dumb machine.");
			one.down();
			one.delay(1000);
			return "The Almighty User Has Won";
		}

		if (i == 4) {
			one.up();
			one.goTo(250.0, 450.0);
			one.penColor("GOLD");
			one.write("CAT GAME - NO WINNER");
			one.delay(1000);
			return "CAT GAME - NO WINNER";
			}

        moveComp();
        if (cpuHasWon) {
          one.up();
          one.goTo(250, 450);
          one.penColor("red");
          one.write("The Computer Has Won.");
          one.down();
          one.delay(1000);
          return "The Computer Has Won.";
        }

			if (i == 4) {
				one.up();
				one.goTo(250, 450);
				one.penColor("GOLD");
				one.write("CAT GAME - NO WINNER");
				one.delay(1000);
				return "CAT GAME - NO WINNER";
			}
		}
		return "ERROR";
	}	


	/**
	 * Did the user win?
	 * @return boolean Ture if the computer did not win, including tie games, and false otherwise.
	 **/
	public boolean userWin()
	{
		return !cpuHasWon;
	}




	public void say(String words) {
		
		one.clear();
		one.up();
		one.goTo(250,250);
		one.down();
		one.penColor("Green");
		one.write(words);
		
	}

	//helper for prompt()
	private void drawOptionBox()
	{
		one.down();
		one.face(270);
		one.forward(100);
		one.left(90);
		one.forward(400);
		one.left(90);
		one.forward(100);
		one.left(90);
		one.forward(400);
	}
	

		/**
		 * prompt the user to click on one of two options
		 * @param question The question to ask the user
		 * @param option1 The top option 
		 * @param option2 The bottom option
		 **/
	  public int prompt(String question, String option1, String option2)
	  {
		one.penColor("BLUE");
		one.speed(0);
		one.clear();
		one.up();
		one.goTo(50,400);
		one.down();
		drawOptionBox();
		one.up();
		one.goTo(250,350);
		one.down();
		one.write(option1);
		one.up();
		one.goTo(50,200);
		drawOptionBox();
		one.up();
		one.goTo(250,150);
		one.write(option2);
		one.up();
		one.goTo(250,450);
		one.down();
		one.write(question);
		updateMouse();
		boolean right=false;
		do{ 
			if ((x > 50) && (x < 450) && (y > 300) && (y < 400)){ right=true; return 1; }
			if ((x > 50) && (x < 450) && (y > 100) && (y < 200)){ right=true; return 2; }
		} while (!right);
		return 0;
	  }
	  
	  public void wait(int miliseconds) {
		  one.delay(miliseconds);
	  }


	public void close(int miliseconds) {
		one.delay(miliseconds);
		one.bye();
	}


	/**
	 * ONLY FOR TESTING 
	 * This method should not be public when the class is actually used.
	 **/
	private static void main(String[]args)
	{
		TicTacToeGrid game = new TicTacToeGrid();
		game.play();
		if (game.prompt("Play Again?", "Yes", "No") == 1) game.newGame(); 
		else {game.say("Fine then don't have more fun..."); game.close(1000); }
		if (game.prompt("Did you enjoy the game?", "Yes", "No")==1) { game.say("Thank You."); game.close(1000);}
		else { game.say("Then I guess you need to play again."); game.close(1000);}
	}
}
