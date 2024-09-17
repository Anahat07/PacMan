//Import external classes
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

//This class creates the title screen of the game.
public class TitleScreen extends JFrame implements KeyListener, ActionListener{

	//GUI ELEMENTS---------------------------------------------------------------------------------------------------
	
	//Labels
	JLabel backgroundImage = new JLabel(new ImageIcon("images/PacManBackground.png")); //Background image label
	JLabel titleImage = new JLabel(new ImageIcon("images/PacManLogo.png")); //Title image label
	JLabel continueLabel = new JLabel("Press space to continue."); //Continue to next screen label
	
	//Create a panel to add the labels on top of the background image
	JPanel upperPanel = new JPanel();
	
	//Load the custom font
	static Font bodyFont = loadFont();
	
	//GAME ELEMENTS--------------------------------------------------------------------------------------------------
	
	/*
	* Flashing text code inspiration from --> 
	* http://www.java2s.com/Tutorials/Java/Graphics_How_to/Text/Make_a_string_flash.htm
	* https://www.rgagnon.com/javadetails/java-0616.html
	*/
	//Create a Timer to create a flashing label (continue label)
	Timer flashingTimer = new Timer(500, this);
	//Variable to track the visibility of the label
	boolean visible = true;
	
    //Declare the sound as a class-level variable so it can accessed by KeyPressed method
    private Clip introSound;
	
	//CONSTRUCTOR METHOD---------------------------------------------------------------------------------------------
	public TitleScreen() {
		
		//Set up the frame --> dimensions & title
		setSize(600,600);
		setLayout(null);
		setTitle("Anahat Chhatwal's PacMan Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set up upper panel (to add the labels on top of the background image)
		upperPanel.setSize(600,600);
		upperPanel.setLayout(null);
		upperPanel.setOpaque(false); //transparent background
		add(upperPanel);
		
		//Add background image --> to main frame
		backgroundImage.setBounds(-5, 0, 610, 600);
		add(backgroundImage);
		
		//Add title image --> to upper panel
		titleImage.setBounds(75, 200, 420, 100);
		upperPanel.add(titleImage);
		
		//Add continue label --> to upper panel
		continueLabel.setBounds(54, 300, 500, 50);
		//Format the label
		continueLabel.setFont(bodyFont.deriveFont(20f));
		continueLabel.setForeground(Color.WHITE);
		upperPanel.add(continueLabel);
	
		//Add sound to the frame
		playIntroSound();
		
		//Start the timer for flashing the continue label
        flashingTimer.start();
        
		//Add key listener to the frame
        addKeyListener(this);
		
		//Show the frame
		setVisible(true);
			
	}

	//This method plays the introductory sound when the game begins.
	/*
	 * Some code for sounds found on -->
	 * https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
	 * How to continuously loop the sound to play found at -->
	 * https://www.youtube.com/watch?v=P856ukheHeE
	 */
	private void playIntroSound() {
		//Try to open and play the introductory sound
		try {
			//Open an audio input stream
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("sounds/gamebeginning.wav"));
			//Get a sound clip resource
	        introSound = AudioSystem.getClip();
	        //Open audio clip and load samples from the audio input stream
	        introSound.open(audioIn);
	        //Loop the sound to play forever
	        introSound.loop(Clip.LOOP_CONTINUOUSLY);
	        //Start playing the clip
	        introSound.start();
	    //Handle exceptions related to unsupported audio file, IO, and line unavailability; print an error message if not found 
		}catch (UnsupportedAudioFileException | IOException | LineUnavailableException error) {
			System.out.println("File not found!");
		}
	}

	//This method adds & loads a custom font for the application
	/*
	 * Code was researched and found at --> https://youtu.be/evhE5TT31qY?feature=shared
	 * Fonts found at --> https://www.1001fonts.com/retro+video-game-fonts.html
	 */
	private static Font loadFont() {
		//Initialize Font object
		Font bodyFont;
		//Try to load the custom font
		try {
			//Create a Font object from the specified font file
			bodyFont = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("fonts/bodyFont.ttf"));
			//Get the local graphics environment
			GraphicsEnvironment font = GraphicsEnvironment.getLocalGraphicsEnvironment();
			//Register the custom font with the graphics environment
			font.registerFont(bodyFont);
			//Return the loaded font
			return bodyFont;
		//Catch FontFormatException & IOException; print an error message if the file is not found & return null
		} catch (FontFormatException | IOException error) {
			System.out.println("File not found!");
			return null;
		}
	}

	//This method handles key presses
	@Override
	public void keyPressed(KeyEvent Key) {
		
		//KeyCode found at --> https://stackoverflow.com/questions/61819151/spacebar-keycode-32-to-act-as-enter-key-except-inside-an-input-field#:~:text=company%20blog-,Spacebar%20(keycode%2032)%20to%20act%20as%20Enter%20key,except%20inside%20an%20input%20field
		
		//Check if the pressed key is the space key
		if(Key.getKeyCode() == 32) { 
            //Stop the Timer when the space key is pressed
            flashingTimer.stop();
            //Stop the sound from continuing to play
            introSound.stop();
            //Hide the frame
    		setVisible(false);
    		//This creates a new class called "NameScreen" which sets up the window that asks the user for their name & explains the game
    		new NameScreen();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		//not used
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//not used
	}

	//This method is called when an action event occurs
	/*
	* Flashing text code inspiration from --> 
	* http://www.java2s.com/Tutorials/Java/Graphics_How_to/Text/Make_a_string_flash.htm
	* https://www.rgagnon.com/javadetails/java-0616.html
	*/
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//If event source is the flashing timer
		if(event.getSource() == flashingTimer) {
			//Show or hide the continue label
			continueLabel.setVisible(visible);
			//Switch visibility of label
			visible = !visible;
		}
		
	}

}