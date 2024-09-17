//Import external classes
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.*;

//This class creates the frame to ask the player to input their player name and view the instructions to play the game if needed
//The player can also view the high scores though this frame
public class NameScreen extends JFrame implements ActionListener{

	//GUI ELEMENTS---------------------------------------------------------------------------------------------------------------------
	
	//Jlabel used to ask the player's name
	JLabel nameLabel = new JLabel("Enter player name: ");
	//Text Area to allow the player to enter their name
	static JTextArea nameTextArea = new JTextArea();
	
	//buttons
	JButton helpButton = new JButton(new ImageIcon("images/helpButton.png")); //help button (instructions on how to play)
	JButton continueButton = new JButton("CONTINUE"); //continue to next frame button
	JButton highScoreButton = new JButton("HIGH SCORES"); //display high scores button
	
	//JLabel for PacMan GIF
	JLabel pacmanLabel = new JLabel(new ImageIcon("images/pacman.gif"));
	//Timer to move the GIF
    private Timer pacmanTimer = new Timer(100, this);
    //Initial x-coordinate for GIF (to move it across the screen)
    private int pacmanX = -40; 
	
    //OTHER GAME ELEMENTS---------------------------------------------------------------------------------------------------------------
    
    //Declare the sound as a class-level variable so it can accessed by KeyPressed method
    private Clip introSound;
    
    //Constructor method----------------------------------------------------------------------------------------------------------------
    public NameScreen() {
    	
		//Set up the frame
		setSize(700,400);
		setLayout(null);
		setTitle("Anahat Chhatwal's PacMan Game");
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Frame border
        Color borderColor = Color.decode("#f18200");
        LineBorder lineBorder = new LineBorder(borderColor, 5);
        getRootPane().setBorder(lineBorder);
		
		//Add name label to frame (asking user to enter their name)
		nameLabel.setBounds(30, 110, 500, 50);
		//Format the label
		nameLabel.setFont(TitleScreen.bodyFont.deriveFont(20f));
		nameLabel.setForeground(Color.WHITE);
		add(nameLabel);
		
		//Add text area to frame (allowing user to enter their name)
		nameTextArea.setBounds(400, 120, 250, 30);
		//Format the label
		nameTextArea.setFont(TitleScreen.bodyFont.deriveFont(20f));
		nameTextArea.setBackground(Color.BLACK);
		nameTextArea.setForeground(Color.WHITE);
		nameTextArea.setBorder(new LineBorder(Color.WHITE, 1));
		//nameTextArea.setForeground(Color.WHITE);
		add(nameTextArea);
		
		//Add help button to give user instructions on how to play the game
		helpButton.setBounds(625, 15, 50, 50);
		helpButton.addActionListener(this);
		helpButton.setOpaque(false);
		helpButton.setForeground(Color.WHITE);
		helpButton.setBorder(new LineBorder(Color.WHITE, 3));
		helpButton.setFont(helpButton.getFont().deriveFont(20.0f));
		add(helpButton);
		
		//Add continue button to allow user to move on to next frame
		continueButton.setBounds(260, 180, 150, 50);
		continueButton.addActionListener(this);
		continueButton.setOpaque(false);
		continueButton.setForeground(Color.WHITE);
		continueButton.setBorder(new LineBorder(Color.WHITE, 3));
		continueButton.setFont(TitleScreen.bodyFont.deriveFont(15f));
		add(continueButton);
		
		//Add high score button to allow user to view past high scores
		highScoreButton.setBounds(415, 15, 200, 50);
		highScoreButton.addActionListener(this);
		highScoreButton.setOpaque(false);
		highScoreButton.setForeground(Color.WHITE);
		highScoreButton.setBorder(new LineBorder(Color.WHITE, 3));
		highScoreButton.setFont(TitleScreen.bodyFont.deriveFont(15f));
		add(highScoreButton);
		
		//Add Pacman GIF to screen
        add(pacmanLabel);
        //Start timer to move the GIF across screen
        pacmanTimer.start();
		
		//Add sound to the frame
		playIntroSound();
		
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
      pacmanLabel.setBounds(pacmanX, 260, 200, 100); //position

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
  	        //Loop the sound to play forever
  	        introSound.loop(Clip.LOOP_CONTINUOUSLY);
  	        //Start playing the clip
  	        introSound.start();
  	    //Handle exceptions related to unsupported audio file, IO, and line unavailability; print an error message if not found 
  		}catch (UnsupportedAudioFileException | IOException | LineUnavailableException error) {
  			System.out.println("File not found!");
  		}
  	}
    
	//This method is called when an action event occurs
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//When pacman timer is started, call the movePacman method that moves the GIF across the screen
		if(event.getSource() == pacmanTimer) {
			movePacman();
		}
		
		//if help button is clicked;
		if(event.getSource() == helpButton) {
			//Name frame should disappear and help frame should open
			setVisible(false);
			new helpScreen();
            //Stop the sound from continuing to play
            introSound.stop();
		}
		
		//if continue button is clicked;
		if(event.getSource() == continueButton) {
			//Name frame should disappear and game GUI frame should open
			setVisible(false);
			new PacManGUI();
            //Stop the sound from continuing to play
            introSound.stop();
		}
		
		//if high score button is clicked;
		if(event.getSource() == highScoreButton) {
			//Name frame should disappear and high scores frame should open
			setVisible(false);
			new highScoreScreen();
            //Stop the sound from continuing to play
            introSound.stop();
		}
	}

}