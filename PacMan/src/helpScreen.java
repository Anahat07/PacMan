//Import external classes
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.*;

//This class creates the screen/window that displays the game instructions/How to play
public class helpScreen extends JFrame implements ActionListener{

	// GUI ELEMENTS---------------------------------------------------------------------------------------------------

	// Labels
	JLabel TitleLabel = new JLabel("How To Play:"); //Title label
	JLabel helpDescription = new JLabel(); //Description/game instructions label
	JLabel helpFeatures = new JLabel(); //Game features label

	// Buttons
	JButton backButton = new JButton("Back"); //Back button
	
	// GAME FEATURES--------------------------------------------------------------------------------------------------
	
    //Declare the sound as a class-level variable so it can accessed by KeyPressed method
    private Clip introSound;
	
    //Constructor Method
	public helpScreen() {
		
		//Set up the frame
		setSize(600,600);
		setLayout(null);
		setTitle("Anahat Chhatwal's PacMan Game");
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Frame border
        Color borderColor = Color.decode("#f18200");
        LineBorder lineBorder = new LineBorder(borderColor, 5);
        getRootPane().setBorder(lineBorder);
		
		//Title label
		TitleLabel.setBounds(180, 0, 500, 100);
		//Format the label
		TitleLabel.setFont(TitleScreen.bodyFont.deriveFont(20f));
		TitleLabel.setForeground(Color.WHITE);
		//Add the label to frame
		add(TitleLabel);
		
		//Help description label
		helpDescription.setBounds(43, -50, 500, 500);
		helpDescription.setText("<html><div style='text-align: center; vertical-align: middle;'>To play, use the arrow keys on your keyboard to move Pacman through a maze filled with dots (pellets). "
				+ "The objective is to eat all the pellets while avoiding the ghosts that roam the maze. "
				+ "If you do collide with a ghost, you will lose a life. Remember; you only have 3 total lives! "
				+ "Each pellet scores points and if you collect all pellets on a board, you will be taken to the next level. "
				+ "Each level has new features and a different difficulty level. "
				+ "If you land on a red heart during the game, you will get a bonus life. "
				+ "If you land on a purple portal, you will be teleported to a different location on the game board. "
				+ "If you land on a pink arrow, you will get a speed bonus and Pacman will speed up. "
				+ "To view your highest score in the game, click on 'High Score' button and scroll to find your name on the list!</html>");
		//Format the label
		helpDescription.setFont(helpDescription.getFont().deriveFont(16.0f));
		helpDescription.setForeground(Color.WHITE);
		//Add the label
		add(helpDescription);
		
		//Help features label
		helpFeatures.setBounds(43, 170, 500, 500);
		helpFeatures.setText("<html><b>Game Features:</b><br>"
				+ "• If you land on a cherry during the game, you will earn 50 extra points.<br>"
				+ "• If you land on a red heart, you will get a bonus life.<br>"
				+ "• If you land on a purple portal, you will be teleported to a different location on the game board.<br>"
				+ "• If you land on a pink arrow, you will get a speed bonus and Pacman will speed up.</html>");
		//Format the label
		helpFeatures.setFont(helpFeatures.getFont().deriveFont(16.0f));
		helpFeatures.setForeground(Color.WHITE);
		//Add the label
		add(helpFeatures);
		
		//Format & add back button
		backButton.setBounds(245, 505, 80, 35);
		backButton.setOpaque(false);
		backButton.setForeground(Color.WHITE);
		backButton.setBorder(new LineBorder(Color.WHITE, 3));
		backButton.setFont(backButton.getFont().deriveFont(20.0f));
		backButton.addActionListener(this);
		add(backButton);
		
		//Add sound to the frame
		playIntroSound();
		
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

	//This method is called when an action event occurs i.e. button clicked
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//if back button is clicked;
		if(event.getSource() == backButton) {
			//help frame should disappear and name screen should open
			setVisible(false);
			new NameScreen();
			//Stop sound
			introSound.stop();
		}
	}
	
}