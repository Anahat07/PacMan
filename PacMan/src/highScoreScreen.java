//Import external classes
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

//This class creates the screen/window that displays the high scores by player name
public class highScoreScreen extends JFrame implements ActionListener{
	
	//JLabel for title
	JLabel titleLabel = new JLabel("Past High Scores:");
	
	//Buttons
	JButton backButton = new JButton("Back"); //back button
	
	//Create a JTable
	/*
	 * Sources:
	 * https://docs.oracle.com/javase/8/docs/api/javax/swing/JTable.html
	 * https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
	 * https://www.geeksforgeeks.org/java-swing-jtable/
	 */
	//Data
	private DefaultTableModel tableModel;
    //Table
    JTable scoresTable = new JTable();
    
    //Constructor method
    public highScoreScreen() {
    	
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
        
        // Create a table model with columns "Player" and "Score"
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Player");
        tableModel.addColumn("Score");
        // Add the columns to the Jtable created above
        scoresTable = new JTable(tableModel);
        
        //Scores table
        // Use JScrollPane to handle scrolling
        JScrollPane scrollPane = new JScrollPane(scoresTable);
        scrollPane.setBounds(15, 90, 560, 400);
        scrollPane.setBackground(Color.BLACK);
		//Format the table
        scoresTable.setFont(TitleScreen.bodyFont.deriveFont(20f));
        scoresTable.setBackground(Color.BLACK);
        scoresTable.setForeground(Color.WHITE);
        scoresTable.setRowHeight(30);
        //Format table header
        scoresTable.getTableHeader().setFont(TitleScreen.bodyFont.deriveFont(20f));
        scoresTable.getTableHeader().setBackground(Color.BLACK);
        scoresTable.getTableHeader().setForeground(Color.WHITE);
        scoresTable.getTableHeader().setPreferredSize(new Dimension(scoresTable.getTableHeader().getWidth(), 50));
        scoresTable.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, Color.WHITE));
        //Set score table size as viewport
        scoresTable.setPreferredScrollableViewportSize(scoresTable.getPreferredSize());
        //Format viewport
        scrollPane.getViewport().setBackground(Color.BLACK);
        // Add the table to the scroll pane
        scrollPane.setViewportView(scoresTable);
        // Add the scroll pane to the frame
        add(scrollPane);
		
        //Title label
        titleLabel.setBounds(130, 0, 500, 100);
		//Format the label
        titleLabel.setFont(TitleScreen.bodyFont.deriveFont(20f));
        titleLabel.setForeground(Color.WHITE);
        //Add the title to the frame
		add(titleLabel);
        
		//Format & add back button
		backButton.setBounds(245, 505, 80, 35);
		backButton.setOpaque(false);
		backButton.setForeground(Color.WHITE);
		backButton.setBorder(new LineBorder(Color.WHITE, 3));
		backButton.setFont(backButton.getFont().deriveFont(20.0f));
		backButton.addActionListener(this);
		add(backButton);
		
        // Load data from the CSV file
        loadDataFromCSV();

        // Display the frame
        setVisible(true);
    	
    }

    //This method loads the data from the high score CSV file
    /*
     * Sources:
     * https://www.youtube.com/watch?v=iJhtRJiv-iU
     * https://www.youtube.com/watch?v=FqiyzchhscQ
     */
    private void loadDataFromCSV() {

	    // Try opening the file
	    try {
	        // Open & read the file
	        Scanner inputHighScore = new Scanner(new File("highscore.csv"));
	        // While the file has a next line
	        while (inputHighScore.hasNextLine()) {
	            // Read the line
	            String data = inputHighScore.nextLine();
	            // Split the entry into name and score
	            String[] parts = data.split(",");
	            // Get the player name part
	            String playerName = parts[0].trim();
	            // Use Integer.valueOf to convert the score string to Integer
	            int score = Integer.valueOf(parts[1].trim()); //trim() is used to remove leading and trailing whitespaces
                // Update the table with player name and score
                updateTable(playerName, score);
	        }
	        // Close the file
	        inputHighScore.close();
	    } catch (FileNotFoundException | NumberFormatException e) {
	        e.printStackTrace();
	    }
		
	}

    //This method adds a new score & player name to the table
	/*
	 * Sources:
	 * https://docs.oracle.com/javase/8/docs/api/javax/swing/JTable.html
	 * https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
	 * https://www.geeksforgeeks.org/java-swing-jtable/
	 */
	private void updateTable(String playerName, int score) {
		
	    // Check if a row with the same player name already exists
	    for (int index = 0; index < tableModel.getRowCount(); index++) {
	        if (tableModel.getValueAt(index, 0).equals(playerName)) {
	            // If the new score is higher, update the score
	            if (score > (int) tableModel.getValueAt(index, 1)) {
	                tableModel.setValueAt(score, index, 1);
	            }
	            return; // Exit the method after updating the score
	        }
	    }

	    // If the player name is not found, add a new row
	    tableModel.addRow(new Object[]{playerName, score});
		
	}
    
    // This method is called when an action event occurs
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//if back button is clicked;
		if(event.getSource() == backButton) {
			//help frame should disappear and name screen should open
			setVisible(false);
			new NameScreen();
		}
		
	}

}