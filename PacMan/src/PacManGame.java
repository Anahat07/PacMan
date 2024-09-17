/* ---------------------------------------------------------PROJECT HEADER-----------------------------------------------------------
 * 
 * NAME: Anahat Chhatwal
 * DATE: December 15, 2023
 * COURSE CODE: ICS3U1-01 Mr.Fernandes
 * TITLE: PacMan - Anahat Chhatwal
 * 
 * DESCRIPTION: 
 * 
 * Basic Game Description -->
 * The game involves a player controlling a ‘chomping’ character, PacMan, around a maze filled with food (pellets). 
 * The player moves PacMan around the maze (4 arrow directions, up, down, left and right) and attempts to ‘eat’ the food 
 * (move over - and the food disappears). However, there are ghosts (computer controlled) characters that are attempting to ‘catch’ 
 * (collide into) PacMan, in which case PacMan loses a life. 
 * The game ends when PacMan eats all the food, or loses all its lives, whichever comes first.
 * 
 * MAJOR SKILLS -->
 * Classes, methods, arrays, objects, try-catch, GUI elements (JFrame, ImageIcon, GridLayout, etc.), for loops, array list, Formatter
 *
 * ADDED FEATURES --> 
 * Title Screen, Help Screen (How to play), End Screen, Power-Ups (teleport, speed, and bonus life), High Score with player name, Ghost AI, Lives, 
 * Score Display, Gate, Levels, Sounds, Ghost escalator
 *  - Click on question mark button on name screen to view help screen (how to play)
 *  - Click on 'High Scores' button on name screen to view high scores with player names
 * 
 * AREAS OF CONCERN: 
 * High score sometimes gives an error if file isn't loaded properly
 * Ghost AI is not perfect and sometimes they don't straight after pacman (only move towards it if same row or same column)
 * Teleport icon sometimes disappears when a ghost goes on it
 * Some power-ups glitch and stay in the cell instead of being set to 'E' (empty)
 * Opening of end screen doesn't close the whole game application and the info panel remains open (I was unsure how to close it)
 */

//This class is the application that runs the game.
public class PacManGame {

	//Create an array for the levels - can be accessed by other classes as well
	public static String[] levelsArray = new String[3];
	
	//This method runs the programs
	public static void main(String[] args) {
		
		//Set each level as an instance in the array
		levelsArray[0] = "level1.txt";
		levelsArray[1] = "level2.txt";
		levelsArray[2] = "level3.txt";
		
		//This creates a new class called "TitleScreen" which sets up the title screen window and allows user to enter the game through it
		new TitleScreen();

	}

}