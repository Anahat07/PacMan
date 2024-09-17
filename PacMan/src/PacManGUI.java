//Import external classes
import javax.swing.*;

/*
 * This is the GUI class. 
 * It sets up the window that holds the actual game.
 */
public class PacManGUI extends JFrame {
	
	//Creates/sets up the window that holds the PacMan game (the actual game board)
	//This is a panel that goes ON the frame
	private Board board = new Board();
	//Create a panel to display the game specs (score, lives, high score, and player message)
	private InfoPanel infoPanel = new InfoPanel();
	
	//This method is a constructor method that builds the frame
	public PacManGUI() {
		
		//set up the frame --> dimensions & title
		setSize(600,700);
		setTitle("Anahat Chhatwal's PacMan Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add the game board to the frame
		add(board);
		
		//Add the information/specs panel to the frame
		add(infoPanel);
	    
		//KeyListener is used for handling keyboard input 
		//When user presses keys on their keyboard, the game will work (characters will move around)
		//Add key listener to the frame
		addKeyListener(board);
		
		//make the frame visible
		setVisible(true);
		
	}

}