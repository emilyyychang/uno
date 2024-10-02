import java.awt.Graphics;
import java.awt.Color;
import java.awt.*;

public class UnoCard {
// What are the attributes?

// use Color.red, Color.blue, etc.
	private Color color;

	// 0-9 are numbers, 10:reverse, 11:skip, 
	// 12:draw2, 13:wild, 14: wild draw 4
	private int face;
	
	private Image img;
	
	public UnoCard(Color green, int f) {
		this.color = green;
		this.face = f;
		// need to make toString
	}

/** This method is called when this UnoCard is being asked
* to draw itself. Where the card draws itself still needs to be
* decided. Should the card keep track of its location or should
* it be told where to draw itself??? If it needs to be told where
* to draw itself, then you need to add parameters to this method.
* @param g
*/
	public void draw(Graphics g) {
	}
	
	public Color getColor() {
		return color;	
	}
	
	public boolean setColor(Color c) {
		// only for Wild(13) and Wild+Draw4(14)
		if (face == 13 || face == 14) {
			color = c;
			return true;
		}
		return false;
	}
	
	// Is String the most appropriate type?
	public int getFace() {
	// TODO Auto-generated method stub
		return face;
	}
	
	public String getCardName() {
		
		String cardName = null;
		char color = 'b';
		
		if (this.color == Color.red) {
			color = 'R';
		}	
		else if (this.color == Color.green) {
			color = 'G';
		}
		else if (this.color == Color.blue) {
			color = 'B';
		}
		else if (this.color == Color.yellow) {
			color = 'Y';
		}
		else {
			color = 'b';
		}
		
		if (this.face <= 9) {
			cardName = Character.toString(color) + String.valueOf(this.face);
		}
		else if (this.face == 10) {  //reverse
			cardName = Character.toString(color) + "-Rev";			
		}
		else if (this.face == 11) {  //skip
			cardName = Character.toString(color) + "-Skip";						
		}
		else if (this.face == 12) {  //draw 2
			cardName = Character.toString(color) + "-Draw2";						
		}
		else if (this.face == 13) {  //Wild
			cardName =  "Wild-" + Character.toString(color);						
		}
		else if (this.face == 14) {  //Wild + draw 4
			cardName =  "Wild-Draw4-" + Character.toString(color);						
		}						
		return cardName; 		
	}
	
	public Object getSymbol() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void flip() {
		// TODO Auto-generated method stub
	}
	
	public void draw(Graphics g, int x, int y) {
		// TODO Auto-generated method stub
	} 
}