
//Import external classes
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

/*
 * This class has the game board, showing the maze and stationary items 
 * It holds the actual cells of the entire game.
 */
public class Board extends JPanel implements KeyListener, ActionListener {

	// Set up timer for the game
	private Timer gameTimer = new Timer(250, this); // Number represents after how long it goes off (ex. every 250 the
													// timer will go off)

	// Array that has 25x27 little squares (cells)
	private Cell[][] mazeArray = new Cell[25][27];

	// PacMan mover object
	private Mover pacMan;

	// Array to store ghost objects/characters
	private Mover[] ghostArray = new Mover[3];

	// Duration for which the pacman's speed is faster
	private final int speedLimit = 45;
	// Variable to keep track of remaining time until pacman's speed goes back to
	// normal
	private int speedDuration = 0;

	// Declare the sounds as class-level variables so they can accessed by every
	// method
	private Clip pacMoving;
	private Clip pacDying;
	private Clip powerPellet;

	// Used to keep track of the amount of pellets (food) in the game (accumulator)
	private int pellets = 0;
	// Used to keep track of the score of the game (accumulator)
	private int score = 0;
	// Used to keep track of the lives in the game (accumulator)
	static int lives = 3; // start the game with 3 lives
	// Create a variable to keep track of which level the user is on
	static int level = 0;
	// pacman's initial speed
	private int pacManSpeed = 250;
	// High score accumulator
	public static int highScore = 0;
	// List to store all high scores
	private static ArrayList<String> highScores = new ArrayList<>();

	// Constructor method
	public Board() {

		// Format panel
		// Set layout for the panel as a 25x27 grid
		setLayout(new GridLayout(25, 27));
		// Set background color of the panel to black
		setBackground(Color.BLACK);
		// Set bounds/position for the maze
		setBounds(0, 100, 600, 600);

		// Load the board by calling the loadBoard() method
		loadBoard();

		// Load the high score when the game starts
		loadHighScore();

		// Initialize the high score display
		InfoPanel.highScoreLabel.setText("High Score: " + highScore);

	}

	// This method loads the board from a file
	private void loadBoard() {

		// Used to count the rows in the file (accumulator)
		int row = 0;

		// Variable to hold the name of the file
		Scanner inputFile;

		// Try to open the file, catch errors if unsuccessful
		try {

			// Create a scanner to read from the "maze.txt" file
			inputFile = new Scanner(new File(PacManGame.levelsArray[level])); // "level" instead of 0 or 1 or 2

			// Loop through each line in the file (rows)
			while (inputFile.hasNext()) {

				// Reads in the next line from the file and converts it to a character array
				char[] lineArray = inputFile.nextLine().toCharArray();

				// Loop through each character in the line (columns)
				for (int column = 0; column < lineArray.length; column++) {

					// Create a new Cell object with the character from the file
					mazeArray[row][column] = new Cell(lineArray[column]);

					// Check to see if the current element in lineArray is 'F'
					if (lineArray[column] == 'F')
						// If yes, then increment pellets by 1
						pellets++;

					// Otherwise, if the current element in lineArray is 'P'
					else if (lineArray[column] == 'P') {
						// Then, initialize its position with the row & column and set its icon and
						// direction
						// Basically, create a new PacMan character
						pacMan = new Mover(row, column);
						pacMan.setIcon(Icons.PACMAN[0]);
						pacMan.setDirection(0);
					}

					// Otherwise, if the current element in lineArray is '0', '1', or '2'
					else if (lineArray[column] == '0' || lineArray[column] == '1' || lineArray[column] == '2') {

						// Get the numeric value from the character and create a new ghost Mover object
						int gNum = Character.getNumericValue(mazeArray[row][column].getItem());
						ghostArray[gNum] = new Mover(row, column);
						ghostArray[gNum].setIcon(Icons.GHOST[gNum]);

					}

					// Add the Cell to the panel
					add(mazeArray[row][column]);

				}

				// Move to the next row
				row++;

			}

			// Close the file after reading
			inputFile.close();

			// Catch FileNotFoundException and print an error message if the file is not
			// found
		} catch (FileNotFoundException error) {
			System.out.println("File not found!");
		}

	}

	// This method responds to the arrow keys that are pressed for controlling
	// PacMan's movement
	@Override
	public void keyPressed(KeyEvent key) {

		// Start the game timer if the game is not currently running and PacMan is dead
		if (gameTimer.isRunning() == false && pacMan.isDead() == false) {
			gameTimer.start();
			// pellets = 3; //This was used to test if the levels are working

			// Play PacMan moving sound
			playPacMovingSound();

		}

		// Move PacMan if its not dead
		else if (pacMan.isDead() == false) {

			// Determine the direction to move in based on the key pressed
			int direction = key.getKeyCode() - 37;

			// Variables for the row & column changes (delta)
			int dRow = 0;
			int dCol = 0;

			// Set the row & column changes based on the direction
			if (direction == 0)
				dCol = -1;
			else if (direction == 1)
				dRow = -1;
			else if (direction == 2)
				dCol = 1;
			else if (direction == 3)
				dRow = 1;

			// Update PacMan's icon & direction if the next cell is not a wall
			if (mazeArray[pacMan.getRow() + dRow][pacMan.getColumn() + dCol].getIcon() != Icons.WALL
					&& mazeArray[pacMan.getRow() + dRow][pacMan.getColumn() + dCol].getIcon() != Icons.GATE) {
				pacMan.setIcon(Icons.PACMAN[direction]);
				pacMan.setDirection(direction);
			}

		}

	}

	// This method plays the sound effect for PacMan moving
  	/*
  	 * Some code for sounds found on -->
  	 * https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
  	 * How to continuously loop the sound to play found at -->
  	 * https://www.youtube.com/watch?v=P856ukheHeE
  	 */
	private void playPacMovingSound() {
		// Try to open and play the PacMan moving sound
		try {
			// Open an audio input stream
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sounds/moving.wav"));
			// Get a sound clip resource
			pacMoving = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream
			pacMoving.open(audioIn);
			// Loop the sound to play forever
			pacMoving.loop(Clip.LOOP_CONTINUOUSLY);
			// Start playing the clip
			pacMoving.start();
			// Handle exceptions related to unsupported audio file, IO, and line
			// unavailability; print an error message if not found
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException error) {
			System.out.println("File not found!");
		}

	}

	@Override
	public void keyReleased(KeyEvent key) {
		// not used
	}

	@Override
	public void keyTyped(KeyEvent key) {
		// not used
	}

	// This method performs the movement of a Mover object
	private void performMove(Mover mover) {

		// Get the next position of the mover and check its status against the numerical
		// values
		if (mover.getNextRow() >= 0 && mover.getNextRow() < 25 && mover.getNextColumn() >= 0
				&& mover.getNextColumn() < 27) {

			// Get the current and next cells
			Cell currentCell = mazeArray[mover.getRow()][mover.getColumn()];
			Cell nextCell = mazeArray[mover.getNextRow()][mover.getNextColumn()];

			// Check for teleportation through the "doors"
			if (mover.getColumn() == 1) {
				mover.setColumn(25);
				mazeArray[12][1].setIcon(Icons.DOOR);
			} else if (mover.getColumn() == 25) {
				mover.setColumn(1);
				mazeArray[12][25].setIcon(Icons.DOOR);
			}

			// Update cell icons based on the next cell's content till the time the next
			// cell is not a wall
			if (nextCell.getIcon() != Icons.WALL) {

				// If mover is not PacMan & the current cell has food, update to food icon
				if (mover != pacMan && currentCell.getItem() == 'F')
					currentCell.setIcon(Icons.FOOD);
				// Otherwise, update to blank icon
				else
					currentCell.setIcon(Icons.BLANK);

				// If mover is not PacMan & the current cell has power pellet, update to cherry
				// icon
				if (mover != pacMan && currentCell.getItem() == 'C')
					currentCell.setIcon(Icons.CHERRY);
				// Otherwise, update to blank icon
				else
					currentCell.setIcon(Icons.BLANK);

				// If PacMan and current cell has cherry power pellet;
				if (mover == pacMan && currentCell.getItem() == 'C') {
					// Update current cell to empty
					currentCell.setItem('E');
					// Increase score by 50 points
					score += 50;
					// Play the sound effect
					playPowerPelletSound();
				}

				// If mover is not PacMan & the current cell has life power-up, update to red
				// heart icon
				if (mover != pacMan && currentCell.getItem() == 'R')
					currentCell.setIcon(Icons.REDHEART);
				// Otherwise, update to blank icon
				else {
					currentCell.setIcon(Icons.BLANK);
				}

				// If mover is pacMan and it eats the red heart icon;
				if (mover == pacMan && currentCell.getItem() == 'R') {

					// Check if the lives are less than 3
					if (lives < 3) {
						// Increment lives and update the lives display
						lives++;
						InfoPanel.livesArray[lives - 1].setVisible(true);
						// Play the sound effect
						playPowerPelletSound();
						// set the cell item as empty
						currentCell.setItem('E');
						// Otherwise, if lives is already 3;
					} else if (lives == 3) {
						// Set the cell item as empty and display the message
						currentCell.setItem('E');
						// Play the sound effect
						playPowerPelletSound();
						JOptionPane.showMessageDialog(this, "Too bad!! You already have 3 lives!!");
					}
				}

				// If mover is not PacMan & the current cell has teleport power-up, update to
				// purple portal icon
				if (mover != pacMan && currentCell.getItem() == 'T')
					currentCell.setIcon(Icons.TELEPORT);
				// Otherwise, update to blank icon
				else
					currentCell.setIcon(Icons.BLANK);

				// If mover is not PacMan & the current cell has speed power-up, update to pink
				// arrow icon
				if (mover != pacMan && currentCell.getItem() == 'S')
					currentCell.setIcon(Icons.SPEEDUP);
				// Otherwise, update to blank icon
				else
					currentCell.setIcon(Icons.BLANK);

				// If PacMan and current cell has speed power-up;
				if (mover == pacMan && currentCell.getItem() == 'S') {
					// Update cell to empty
					currentCell.setItem('E');
					// Play the sound effect
					playPowerPelletSound();
					// Make the speed duration equal to speed limit to go back to normal speed once
					// limit is reached
					speedDuration = speedLimit;
					// Apply speed boost (increase speed)
					pacManSpeed = 100;
					// Set game timer's delay to be the speed
					gameTimer.setDelay(pacManSpeed);
				}

				// If mover is not PacMan & the current cell has gate, set to gate icon
				if (mover != pacMan && currentCell.getItem() == 'G')
					currentCell.setIcon(Icons.GATE);

				// Move the mover object
				mover.move();
				// Get the current cell after the move
				currentCell = mazeArray[mover.getRow()][mover.getColumn()];

				// Teleport if pacMan eats the teleport power up (purple portal)
				if (mover == pacMan && currentCell.getItem() == 'T') {
					// Play the sound effect
					playPowerPelletSound();
					// Make the original pacman disappear
					pacMan.setIcon(Icons.BLANK);
					// Display winning message
					JOptionPane.showMessageDialog(this,
							"You just ate a teleportation pellet! Press the arrow keys to see where PacMan teleported to...");
					// Remove the 'T' item from the current cell
					currentCell.setIcon(Icons.BLANK);
					currentCell.setItem('E');
					// Teleport to the set row & column
					pacMan.setRow(21);
					pacMan.setColumn(5);
					// Update the cell at the new position with PacMan's icon
					mazeArray[pacMan.getRow()][pacMan.getColumn()].setIcon(pacMan.getIcon());
				}

				// Check for collision with PacMan
				if (collided())
					// Call the death method
					death();
				// If no collision is detected, update the current cell's icon to the Mover's
				// icon
				else
					currentCell.setIcon(mover.getIcon());

				// If PacMan and current cell has food, update score & item
				if (mover == pacMan && currentCell.getItem() == 'F') {

					// Update score & item
					score++;
					// Update the score label
					InfoPanel.scoreLabel.setText("Score: " + score);
					currentCell.setItem('E');

					// Load the highest score from the CSV file to display it
					int highestScore = loadHighestScore();

					if (score > highScore) {
						highScore = score;
						// Update the high score display
						InfoPanel.highScoreLabel.setText("High Score: " + highestScore);
					}

					// Check if the user won
					// If all food items (including power pellets) are eaten, the user wins
					if (allFoodEaten()) {
						// Stop game timer
						gameTimer.stop();
						// Display winning message
						JOptionPane.showMessageDialog(this, "You cleared the board!");
						// Increase level (3 max levels)
						level++;
						// Stop the pacman moving sound (since it restarts at start of next level)
						pacMoving.stop();
						// Set score back to 0
						score = 0;
						// Set pellets back to 0
						pellets = 0;
						// Remove everything from board
						removeAll();
						// Reload new board
						loadBoard();
						// Save the high score when the game ends
						saveHighScore();
					}

				}

			}

		}

	}

	// This method plays sound effect when pacMan eats a power pellet
  	/*
  	 * Some code for sounds found on -->
  	 * https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
  	 * How to continuously loop the sound to play found at -->
  	 * https://www.youtube.com/watch?v=P856ukheHeE
  	 */
	private void playPowerPelletSound() {

		// Try to open and play the PacMan eating power pellet sound
		try {
			// Open an audio input stream
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sounds/fruiteat.wav"));
			// Get a sound clip resource
			powerPellet = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream
			powerPellet.open(audioIn);
			// Start playing the clip
			powerPellet.start();
			// Handle exceptions related to unsupported audio file, IO, and line
			// unavailability; print an error message if not found
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException error) {
			System.out.println("File not found!");
		}

	}

	// This method determines when the board is clear (user wins)
	private boolean allFoodEaten() {
		// Iterate through the mazeArray and check if there is any remaining food or
		// power pellets left
		for (int row = 0; row < mazeArray.length; row++) {
			for (int col = 0; col < mazeArray[row].length; col++) {
				if (mazeArray[row][col].getIcon() == Icons.FOOD || mazeArray[row][col].getIcon() == Icons.CHERRY
						|| mazeArray[row][col].getIcon() == Icons.TELEPORT
						|| mazeArray[row][col].getIcon() == Icons.REDHEART
						|| mazeArray[row][col].getIcon() == Icons.SPEEDUP) {
					// If there is any remaining food or power pellet, return false
					return false;
				}
			}
		}
		// If no food or power pellet is found, return true (all food is eaten)
		return true;
	}

	// This method checks if PacMan has collided with any of the ghosts
	private boolean collided() {

		// Check each ghost's position compared to PacMan's position
		for (Mover ghost : ghostArray) { // enhanced for loop (loop that goes through the entire ghost array)

			// If the ghost's position matches PacMan's position, then return true
			// (collision took place)
			if (ghost.getRow() == pacMan.getRow() && ghost.getColumn() == pacMan.getColumn()) {
				return true;
			}
		}
		// If no collision is detected, return false
		return false;
	}

	// This method resets the game (if less than 3 lives are lost)
	private void resetGame() {
		// Reset PacMan's position back to it's original position
		pacMan.setRow(15);
		pacMan.setColumn(14);
		// Set PacMan as alive
		pacMan.setDead(false);
		// Reset PacMan's icon to original icon
		pacMan.setIcon(Icons.PACMAN[0]);
		// Reset PacMan's direction to left (original direction)
		pacMan.setDirection(0);
		// Stop the game timer and show a life lost message
		JOptionPane.showMessageDialog(this, "You lost 1 life!! Press space key to reset PacMan and continue.");
	}

	// This method is called when PacMan collides with a ghost to mark the end of
	// the game
	private void death() {
		// Decrement lives
		lives--;
		// Display the lives in the info panel --> heart images
		InfoPanel.livesArray[lives].setVisible(false);
		// Stop game timer if a life is lost
		gameTimer.stop();
		// Stop the PacMan moving sound effect if a life is lost
		pacMoving.stop();
		// Play the PacMan dying sound effect
		playPacDyingSound();
		// Update the cell with PacMan's position to display a skull icon
		mazeArray[pacMan.getRow()][pacMan.getColumn()].setIcon(Icons.SKULL);

		// If there are remaining lives, reset PacMan's position and continue the game
		if (lives > 0) {
			resetGame();
		} else {
			// If no lives left, end the game
			// Set PacMan as dead
			pacMan.setDead(true);
			// Show a "game over" message
			JOptionPane.showMessageDialog(this, "GAME OVER");
			// Hide the frame
			setVisible(false);
			// Display the end/thank you screen
			new EndScreen();
			// Save the high score when the game ends
			saveHighScore();
		}
	}

	// This method plays the sound of PacMan dying
  	/*
  	 * Some code for sounds found on -->
  	 * https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
  	 * How to continuously loop the sound to play found at -->
  	 * https://www.youtube.com/watch?v=P856ukheHeE
  	 */
	private void playPacDyingSound() {
		// Try to open and play the PacMan dying sound
		try {
			// Open an audio input stream
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sounds/killed.wav"));
			// Get a sound clip resource
			pacDying = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream
			pacDying.open(audioIn);
			// Start playing the clip
			pacDying.start();
			// Handle exceptions related to unsupported audio file, IO, and line
			// unavailability; print an error message if not found
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException error) {
			System.out.println("File not found!");
		}
	}

	// This method moves the ghosts towards pacMan and in random directions
	private void moveGhosts() {

		// Repeat for each ghost in the ghostArray
		for (Mover ghost : ghostArray) {

			// Variable for direction
			int dir = 0;

			// Get current cell value
			Cell currentCell = mazeArray[ghost.getRow()][ghost.getColumn()];
			// Get next cell value
			Cell nextCell = mazeArray[ghost.getRow()][ghost.getColumn()];

			// Create the escalator
			// If the current cell is 'U'
			if (currentCell.getItem() == 'U') {
				// Set the ghosts' direction as UP
				dir = 1;
				// Otherwise, if the ghosts are moving downwards & next cell item is 'G' (gate;
			} else if (ghost.getDirection() == 3 && nextCell.getItem() == 'G') {
				// Set the ghosts' direction as RIGHT
				dir = 2;

				// GHOST AI --> used my own logic as well as some of Aishu's
			} else if (pacMan.getRow() == ghost.getRow()) {
				// If pacman is to the left of ghost
				if (pacMan.getColumn() < ghost.getColumn()) {
					// set ghost's direction to left
					dir = 0;
					// Otherwise, pacman is to the right of ghost
				} else if (pacMan.getColumn() > ghost.getColumn()) {
					// set ghost's direction to right
					dir = 2;
				}
			} else if (pacMan.getColumn() == ghost.getColumn()) {
				// Otherwise, pacman is above ghost
				if (pacMan.getRow() < ghost.getRow()) {
					// set ghost's direction to up
					dir = 1;
					// Otherwise, pacman is below ghost
				} else if (pacMan.getRow() > ghost.getRow()) {
					// set ghost's direction to down
					dir = 3;
				}

				// Otherwise;
			} else {
				// Generate a random direction for the ghost to move
				do {
					dir = (int) (Math.random() * 4);
					// Prevents the ghost from going back and forth in a direction
				} while (Math.abs(ghost.getDirection() - dir) == 2);
			}

			// Set the ghost's direction as the random direction that was found above
			ghost.setDirection(dir);

			// If PacMan is not dead, perform the ghost's move
			if (!pacMan.isDead()) {
				performMove(ghost);
			}
		}

	}

	// This method creates a file to save the high scores at end of each game (using
	// formatter)
	/*
	 * Sources used for this code:
	 * https://stackoverflow.com/questions/22339123/adding-highscores-to-java-game-
	 * from-console-to-jpanel-saving-highscore-in-en
	 * https://www.daniweb.com/programming/software-development/threads/359437/
	 * creating-a-high-score-and-reading-from-game
	 * https://youtu.be/FqiyzchhscQ?feature=shared
	 */
	private static void saveHighScore() {

		// Clear the existing highScores list
		highScores.clear();

		// Add the current game's score and player name to the array list
		highScores.add(NameScreen.nameTextArea.getText() + "," + highScore);

		// Try to open/create the file to store high scores
		try {
			// Open & write the file to store all high scores
			Formatter outputHighScore = new Formatter(new FileWriter("highscore.csv", true));
			// Add the current player's name and score to the file
			outputHighScore.format("%s,%d%n", NameScreen.nameTextArea.getText(), highScore);
			// Close the file
			outputHighScore.close();
			// Error if it doesn't work
		} catch (IOException event) {
			event.printStackTrace();
		}
	}

	// This method loads the high scores from the file opened/created above and
	// reads them (using Scanner)
	/*
	 * Sources used for this code:
	 * https://stackoverflow.com/questions/22339123/adding-highscores-to-java-game-
	 * from-console-to-jpanel-saving-highscore-in-en
	 * https://www.daniweb.com/programming/software-development/threads/359437/
	 * creating-a-high-score-and-reading-from-game
	 * https://youtu.be/FqiyzchhscQ?feature=shared
	 */
	private static void loadHighScore() {

		// Try opening the file
		try {
			// Open & read the file
			Scanner inputHighScore = new Scanner(new File("highscore.csv"));
			// Clear the list before loading new names
			highScores.clear();
			// While the file has a next line
			while (inputHighScore.hasNextLine()) {
				// Read the line
				String entry = inputHighScore.nextLine();
				// Add it to the array list
				highScores.add(entry);
			}
			// Close the file
			inputHighScore.close();
			// Error if it doesn't work
		} catch (FileNotFoundException event) {
			event.printStackTrace();
		}
	}

	// This method loads the highest score out of all the scores from the CSV file
	// to display it
	/*
	 * Sources used for this code:
	 * https://stackoverflow.com/questions/22339123/adding-highscores-to-java-game-
	 * from-console-to-jpanel-saving-highscore-in-en
	 * https://www.daniweb.com/programming/software-development/threads/359437/
	 * creating-a-high-score-and-reading-from-game
	 * https://youtu.be/FqiyzchhscQ?feature=shared
	 * https://ioflood.com/blog/java-string-split/#:~:text=To%20split%20a%20string%
	 * 20in,of%20the%20given%20regular%20expression.
	 */
	private static int loadHighestScore() {

		// Create variable that keeps track of highest score
		int highestScore = 0;

		// Try opening the file
		try {
			// Open & read the file
			Scanner inputHighScore = new Scanner(new File("highscore.csv"));
			// While the file has a next line
			while (inputHighScore.hasNextLine()) {
				// Read the line
				String entry = inputHighScore.nextLine();
				// Split the entry into name and score
				String[] parts = entry.split(",");
				// Use Integer.valueOf to convert the score string to Integer
				Integer scorePart = Integer.valueOf(parts[1].trim()); // trim() is used to remove leading and trailing
																		// whitespaces
				// Extract the integer value from the Integer object
				int score = scorePart.intValue();
				// Check if the current score is higher than the highest score
				if (score > highestScore) {
					highestScore = score;
				}
			}
			// Close the file
			inputHighScore.close();
		} catch (FileNotFoundException | NumberFormatException e) {
			e.printStackTrace();
		}

		// Return the highestScore value so it can be used in PerformMove()
		return highestScore;
	}

	// This method is called when an action occurs.
	@Override
	public void actionPerformed(ActionEvent event) {

		// If the game timer triggers an action
		if (event.getSource() == gameTimer) {
			// Then, perform PacMan's move
			performMove(pacMan);
			// Move the ghosts
			moveGhosts();
		}

		// Update the remaining time for speed boost
		if (speedDuration > 0) {
			speedDuration--;
			// Check if the time has run out
			if (speedDuration == 0) {
				// Reset PacMan's speed to the original value
				pacManSpeed = 250;
				gameTimer.setDelay(pacManSpeed);
			}
		}

	}

}