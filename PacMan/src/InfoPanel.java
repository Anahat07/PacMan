//Import external classes
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

//This class creates the panel above the game maze that displays the different game specs i.e. score, high score and lives
public class InfoPanel extends JPanel {

	// JLabel for displaying the score
	public static JLabel scoreLabel = new JLabel("Score: 0");
	// JLabel array to display lives
	public static JLabel[] livesArray = new JLabel[3];
	// JLabel for displaying the highscore
	public static JLabel highScoreLabel = new JLabel();
	//JLabel for player name & message
	public static JLabel playerNameLabel = new JLabel();

	//constructor method
	public InfoPanel() {

		//Set up frame
		setBounds(0, 0, 600, 100);
		setLayout(null);
		setBackground(Color.BLACK);

		// Format score label
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setBounds(20, 20, 200, 20);
		scoreLabel.setFont(TitleScreen.bodyFont.deriveFont(12f));
		//Add score label
		add(scoreLabel);

		// Display the lives depending on how many the user has left (0, 1, 2 or 3)
		for (int index = 0; index < 3; index++) {
			//Add a red heart image depending on how many lives they have left
			livesArray[index] = new JLabel(new ImageIcon("images/RedHeart.bmp"));
			//Display the hearts --> format label
			livesArray[index].setBounds(20 + 25 * index, 70, 20, 20);
			add(livesArray[index]);
		}
		
		// Format high score label
		//Display what the label should say
        highScoreLabel.setText("High Score: " + Board.highScore);
		highScoreLabel.setForeground(Color.WHITE);
		highScoreLabel.setBounds(20, 45, 200, 20);
		highScoreLabel.setFont(TitleScreen.bodyFont.deriveFont(12f));
		//Add high score label
		add(highScoreLabel);
		
		// Format player name & message label
		//Display what the label should say
		playerNameLabel.setText("Good Luck " + NameScreen.nameTextArea.getText() + "!");
		playerNameLabel.setForeground(Color.WHITE);
		playerNameLabel.setBounds(215, 45, 400, 20);
		playerNameLabel.setFont(TitleScreen.bodyFont.deriveFont(18f));
		//Add player name & message label
		add(playerNameLabel);

	}

}