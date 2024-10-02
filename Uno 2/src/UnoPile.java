import java.awt.Graphics;
import java.util.ArrayList;

public class UnoPile {
	
	private ArrayList<UnoCard> pile;
	
	public UnoPile() {
		pile = new ArrayList<>();	
	}
	
	public void draw(Graphics g) {
	}

	public int size() {		
		return pile.size();
	}
	
	public UnoCard deal() {
		int id = pile.size() - 1;
		UnoCard TopUnoCard = pile.get(id);	//get the last/top one
		pile.remove(id);
		return TopUnoCard;
	}
	
	public UnoCard deal(int id) {
		if (id < pile.size()) {
			UnoCard Card = pile.get(id);	//get the last/top one
			pile.remove(id);
			return Card;			
		}
		else
			return null;
	}
	
	public void add(UnoCard unoCard) {
		pile.add(unoCard);
	}

	public UnoCard get(int id) {
		return pile.get(id);
	}
	
	public void shuffle() {
		// need to make shuffle method; use math.random
		ArrayList<UnoCard> UnoCardListNew = new ArrayList<UnoCard>();
		
		boolean exist = false;
		int CardID[] = new int[pile.size()];	//create a random card id array
		for (int x = 0; x < pile.size(); x++) {
			if (x == 0) {
				//first random number must have no duplicate problem
				//Math.random() returns a double value with a positive sign, greater than or equal to 0.0 and less than 1.0
				CardID[x] = (int)(Math.random()*pile.size()); 	//between 0 ~ pile.size()-1
			}
			else {
				exist = true;
				while (exist == true)	{
					exist = false;
					int id = (int)(Math.random()*pile.size());	// potential random id, between 0 ~ pile.size()-1					
					for (int y = 0; y < x; y++) {	//check if the id has been in CardID[] or not
						if (CardID[y] == id) {
							exist = true;	//this id has been in CardID[], need to find another one.
							break;
						}
					}					
					if (exist == false) {	//if not in CardID[], then adopt it.
						CardID[x] = id;
					}
				}
			}			
		}
		for (int x = 0; x < pile.size(); x++) {	//save the card to UnoCardListNew in the order of CardID[] 
			UnoCard u = pile.get(CardID[x]);
			UnoCardListNew.add(u);
		} 
		pile = UnoCardListNew;
	}
}
