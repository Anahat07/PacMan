//Import external classes
import javax.swing.*;

//This class holds the image constants/icons.
public class Icons{
	
	//Image constants for different game elements
	public static final ImageIcon WALL = new ImageIcon("images/Wall.bmp");
	public static final ImageIcon FOOD = new ImageIcon("images/Food.bmp");
	public static final ImageIcon CHERRY = new ImageIcon("images/Cherry.bmp");
	public static final ImageIcon BLANK = new ImageIcon("images/Black.bmp");
	public static final ImageIcon DOOR = new ImageIcon("images/Black.bmp");
	public static final ImageIcon SKULL = new ImageIcon("images/Skull.bmp");
	public static final ImageIcon REDHEART = new ImageIcon("images/RedHeart.bmp");
	public static final ImageIcon EMPTYHEART = new ImageIcon("images/EmptyHeart.bmp");
	public static final ImageIcon BLUEGHOST = new ImageIcon("images/BlueGhost.bmp");
	public static final ImageIcon GATE = new ImageIcon("images/gate.bmp");
	public static final ImageIcon TELEPORT = new ImageIcon("images/teleport.bmp");
	public static final ImageIcon SPEEDUP = new ImageIcon("images/speed.bmp");
	
	//Image constants for different states of PacMan character
	//Array with four different instances for each PacMan icon
	public static final ImageIcon[] PACMAN = {
			
			new ImageIcon("images/PacMan0.gif"),
			new ImageIcon("images/PacMan1.gif"),
			new ImageIcon("images/PacMan2.gif"),
			new ImageIcon("images/PacMan3.gif"),
			
	};
	
	//Image constants for different states of Ghost character
	//Array with three different instances for each ghost
	public static final ImageIcon[] GHOST = {
			
			new ImageIcon("images/Ghost0.bmp"),
			new ImageIcon("images/Ghost1.bmp"),
			new ImageIcon("images/Ghost2.bmp"),
			
	};

}