//Import external classes
import javax.swing.*;

//This class is a template class that represents one cell on the maze.
public class Cell extends JLabel{
	
	//The character representing the item in the cell (field)
	private char item;

	//This method is the constructor method
	public Cell(char item) {
		super();
		this.item = item;
		
		//Set the icon for the cell based on the item
		setCodeIcon();
	}

	//Getters & setters for the item
	public char getItem() {
		return item;
	}

	public void setItem(char item) {
		this.item = item;
	}

	//toString method
	@Override
	public String toString() {
		return "Cell [item=" + item + "]";
	}
	
	//This method sets the icon for the cell based on its item
	private void setCodeIcon() {
		
		//Tells the program which picture to load based on the item
		if (item == 'P')
            setIcon(Icons.PACMAN[0]);
        else if (item == '0')
            setIcon(Icons.GHOST[0]);
        else if (item == '1')
            setIcon(Icons.GHOST[1]);
        else if (item == '2')
            setIcon(Icons.GHOST[2]);
        else if (item == 'W')
            setIcon(Icons.WALL);
        else if (item == 'F')
            setIcon(Icons.FOOD);
        else if (item == 'D')
            setIcon(Icons.DOOR);
        else if (item == 'C')
        	setIcon(Icons.CHERRY);
        else if (item == 'R')
        	setIcon(Icons.REDHEART);
        else if (item == 'U')
        	setIcon(Icons.BLANK);
        else if (item == 'G')
        	setIcon(Icons.GATE);
        else if (item == 'T')
        	setIcon(Icons.TELEPORT);
        else if (item == 'S')
        	setIcon(Icons.SPEEDUP);

	}
	
}