//Import external classes
import javax.swing.*;

//This class represents a movable object in a grid.
public class Mover extends JLabel{
	
	//Fields
	
	//Fields for each row and column
	private int row;
	private int column;
	
	//"delta" --> change in the values (columns & rows)
	//Only possible values are -1, 0, 1
	private int dRow;
	private int dColumn;
	
	//Used to check if the user is dead by setting it to true or false
	private boolean isDead;

	//Constructor method
	public Mover(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	//Getters and setters
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getdRow() {
		return dRow;
	}

	public void setdRow(int dRow) {
		this.dRow = dRow;
	}

	public int getdColumn() {
		return dColumn;
	}

	public void setdColumn(int dColumn) {
		this.dColumn = dColumn;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	//This method updates the position based on deltas
	public void move() {
		row += dRow;
		column += dColumn;
	}
	
	//This method sets the direction based on the input
	public void setDirection(int direction) {
		
		//This resets both deltas back to 0
		dRow = 0;
		dColumn = 0;
		
		//This sets both deltas based on the direction the user is moving
		if(direction == 0) //left
			dColumn = -1;
		else if(direction == 1) //up
			dRow = -1;
		else if(direction == 2) //right
			dColumn = 1;
		else if(direction == 3) //down
			dRow = 1;
		
	}
	
	//This method returns the current direction
	public int getDirection() {
		
		//Check values of dRow and dColumn and return the corresponding values for direction
		if(dRow == 0 && dColumn == -1)
			return 0;
		else if(dRow == -1 && dColumn == 0)
			return 1;
		else if(dRow == 0 && dColumn == 1)
			return 2;
		else
			return 3;
		
	}
	
	//This method returns the next row based on the current direction
	public int getNextRow() {
		return row + dRow;
	}
	
	//This method returns the next column based on the current direction
	public int getNextColumn() {
		return column + dColumn;
	}

}