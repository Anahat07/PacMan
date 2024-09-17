//Import external classes
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.sound.sampled.*;
import javax.swing.*;

//This class creates the ending screen of the game.
public class EndScreen extends JFrame implements ActionListener{

	//GUI ELEMENTS---------------------------------------------------------------------------------------------------
	
	//Labels
	JLabel titleImage = new JLabel(new ImageIcon("images/PacManLogo.png")); //Logo title label
	//Message label
	JLabel thanksLabel = new JLabel("<html><div style='text-align: center; vertical-align: middle;'>Thank you for playing!!<br> See you soon!</html>");
	
	//JLabel for PacMan GIF
	JLabel pacmanLabel = new JLabel(new ImageIcon("images/pacman.gif"));
	//Timer to move the GIF
    private Timer pacmanTimer = new Timer(100, this);
    //Initial x-coordinate for GIF (to move it across the screen)
    private int pacmanX = -40; 
	
	//GAME ELEMENTS--------------------------------------------------------------------------------------------------
	
	/*
	* Flashing text code inspiration from --> 
	* http://www.java2s.com/Tutorials/Java/Graphics_How_to/Text/Make_a_string_flash.htm
	* https://www.rgagnon.com/javadetails/java-0616.html
	*/
	//Create a Timer to create a flashing label (continue label)
	Timer flashingTimer = new Timer(800, this);
	//Variable to track the visibility of the label
	boolean visible = true;
	
    //Declare the sound as a class-level variable so it can accessed by KeyPressed method
    private Clip introSound;
	
	//CONSTRUCTOR METHOD---------------------------------------------------------------------------------------------
	public EndScreen() {
		
		//Set up the frame
		setSize(600,600);
		setLayout(null);
		setTitle("Anahat Chhatwal's PacMan Game");
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Add title image
		titleImage.setBounds(85, 150, 420, 100);
		add(titleImage);
		
		//Format & add message label
		thanksLabel.setBounds(66, 270, 500, 50);
		thanksLabel.setFont(TitleScreen.bodyFont.deriveFont(20f));
		thanksLabel.setForeground(Color.WHITE);
		add(thanksLabel);
		
		//Add Pacman GIF to screen
        add(pacmanLabel);
        //Start timer to move the GIF across screen
        pacmanTimer.start();
	
		//Add sound to the frame
		playIntroSound();
		
		//Start the timer for flashing the continue label
        flashingTimer.start();
		
		//Show the frame
		setVisible(true);
			
	}
	
    //This method moves the Pacman GIF across the screen for visual effects
    /*
     * Sources:
     * https://stackoverflow.com/questions/12566311/displaying-gif-animation-in-java
     * https://coderanch.com/t/333049/java/Move-image-screen
     */
    private void movePacman() {
		
      // Update Pac-Man's position
      pacmanX += 8; //speed 
      pacmanLabel.setBounds(pacmanX, 340, 200, 100); //position

      // Check if Pac-Man goes beyond the frame's width
      if (pacmanX > getWidth()) {
          pacmanX = -50; // Reset Pac-Man to the left side
      }
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
	        //Start playing the clip
	        introSound.start();
	    //Handle exceptions related to unsupported audio file, IO, and line unavailability; print an error message if not found 
		}catch (UnsupportedAudioFileException | IOException | LineUnavailableException error) {
			System.out.println("File not found!");
		}
	}
	
	//This method is called when an action event occurs
	/*
	* Flashing text code inspiration from --> 
	* http://www.java2s.com/Tutorials/Java/Graphics_How_to/Text/Make_a_string_flash.htm
	* https://www.rgagnon.com/javadetails/java-0616.html
	*/
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//When pacman timer is started, call the movePacman method that moves the GIF across the screen
		if(event.getSource() == pacmanTimer) {
			movePacman();
		}
		
		//If event source is the flashing timer
		if(event.getSource() == flashingTimer) {
			//Show or hide the thanks label
			thanksLabel.setVisible(visible);
			//Switch visibility of label
			visible = !visible;
		}	
	}
	
}