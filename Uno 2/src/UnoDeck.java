import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics;

public class UnoDeck extends UnoPile{
// ArrayList<UnoCard>() list; BAD!!!!!
	
	
	public UnoDeck() {
		Color [] marks = {Color.red, Color.green, Color.blue, Color.yellow};
		for (Color c : marks) {
			for(int x = 0; x < 14; x++) {
				add(new UnoCard(c, x)); // use add from UnoPile not ArrayList
			}
			
			// need 2 reverses b/c there are 2 reverses per color
			add (new UnoCard(c, 10)); 
			add (new UnoCard(c, 10));
			
			// need 2 skips b/c there are 2 skips per color
			add (new UnoCard(c, 11)); 
			add (new UnoCard(c, 11)); 
			
			// need 2 draw 2 b/c there are 2 draw 2 per color
			add (new UnoCard(c, 12)); 
			add (new UnoCard(c, 12));
			
			// need 4 wild b/c there are 4 wild per color
			add (new UnoCard(Color.black, 13));
			add (new UnoCard(Color.black, 13));
			add (new UnoCard(Color.black, 13));
			add (new UnoCard(Color.black, 13));
			
			// need 4 wild+4 b/c there are 4 wild+4 per color
			add (new UnoCard(Color.black, 14));
			add (new UnoCard(Color.black, 14));
			add (new UnoCard(Color.black, 14));
			add (new UnoCard(Color.black, 14));
		}
	}
	
	// not sure what we want to do when we draw the deck.  
	// maybe draw a facedown card.  Do we need to pass in the location?
	public void draw(Graphics g) {
		
	}
}
