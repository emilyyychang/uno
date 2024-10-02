import java.awt.Color;
import java.awt.Graphics;

public class UnoPlayer {
	private UnoPile hand;
	private String name;
	 
	public UnoPlayer(String n){
		this.hand = new UnoPile();
		this.name = n;
 	}
	 
	public String getName() {
		return this.name;
	}
	
 	public void addCard(UnoCard deal) {
 		hand.add(deal);
 	}
 	
 	public UnoCard getNextCard(UnoCard toBeMatched) {
	   // look through this UnoPlayer cards to find one
	   // that matches 
 		
 		//Action inside the method-1: number 0~9
 		if (toBeMatched.getFace() <= 9) { 
	 		for(int x = 0; x < hand.size(); x++) {
	 			if (hand.get(x).getFace() <= 9) {	 				
		 			if (hand.get(x).getColor() == toBeMatched.getColor() ||
					    hand.get(x).getFace() == toBeMatched.getFace()) {
		 					return hand.deal(x);
		 			}
	 			}
	 		} 			
 		}
 		//Action inside the method-2: 10:Reverse, 11:Skip, 12:Draw2, 
 		//			13:Wild and 14:Wild+Draw4 (both colors are assigned by previous player)
 		if (toBeMatched.getFace() >= 10 && toBeMatched.getFace() <= 14) { 
	 		for(int x = 0; x < hand.size(); x++) {
	 			if (hand.get(x).getFace() <= 9) {	 				
		 			if (hand.get(x).getColor() == toBeMatched.getColor()) {
	 					return hand.deal(x);
		 			}
	 			}
	 		} 			
 		}
 		
 		//Cannot find a matched number/color card, see if I have reverse, skip, draw2
 		for(int x = 0; x < hand.size(); x++) {
 			if (hand.get(x).getFace() >= 10 && hand.get(x).getFace() <= 12) {	 				
	 			if (hand.get(x).getColor() == toBeMatched.getColor()) {
	 				return hand.deal(x);
	 			}
 			}
 		} 
 		//nothing else, see if I have wild or wild+draw4?
 		for(int x = 0; x < hand.size(); x++) {
 			if (hand.get(x).getFace() == 13 || hand.get(x).getFace() == 14) {	
 				// assign the color before returning it
 				for(int y = 0; y < hand.size(); y++) {
 					if (hand.get(y).getColor() != Color.black) {
 		 				hand.get(x).setColor(hand.get(y).getColor());
 		 				break;
 					}
 				}
				return hand.deal(x);
 			}
 		} 
 		return null;
 	}
 	
 	public int cardsLeft() {
 		return hand.size();
 	}

	public void printCard() {
		
		System.out.print(name + ":");
		for (int x = 0; x < hand.size(); x++) {
			System.out.print("[" + hand.get(x).getCardName() + "] ");
		}
		System.out.println("");
	}	 
}
