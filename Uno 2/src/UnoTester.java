import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UnoTester {
	// This is like a new data type that can be one of 4 choices
	private enum TestingMode{CARDS, PILE, DECK, PLAYER, GAME};
	
	// setting the Testing mode to Cards.  Change this when 
	// you are ready to test the Deck class or Player class or Game
	private TestingMode tm = TestingMode.GAME;
	private UnoDeck ud = new UnoDeck();
	private ArrayList<UnoCard> listOfCards = new ArrayList<>();
	private int numRepaints = 0;
	
	public static void main(String[] args) {
		
		System.out.println("Testing classes");
		UnoTester ut = new UnoTester();
		ut.init();
		ut.testUnoCards();
	}
/**
 * This code in the init method
 */
	private void init() {
		JPanel panel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			/** this is a super-important method when working with
			 * javax swing graphics.  All drawing is invoked from here.
			 */
			public void paintComponent(Graphics g) {
				//super.paintComponent(g);// basically clears the canvas
				//numRepaints++;
				//System.out.println("painting!! "+numRepaints);
				drawStuff(g);
			}
		};
		panel.setBackground(new Color(100, 220, 100));
		panel.setPreferredSize(new Dimension(800, 600));
		JFrame frame = new JFrame("Testing Uno Classes");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	protected void drawStuff(Graphics g) {
		switch(tm) {
			case CARDS:
				testUnoCards();
				for(UnoCard uc: this.listOfCards)
					uc.draw(g);
				break;
			case PILE:
				testUnoPile();
				// other code
				break;
			case DECK:
				testUnoDeck();
				UnoCard uc;
				int x = 60; int y = 150;
				for(int i = 0; i < 8; i++) {
					uc = ud.deal();
					uc.flip();
					uc.draw(g, x, y);
					x -=20; y += 30;
				}
				ud.shuffle();
				x = 0;
				y = 0;
				for(int i = 0; i < 20; i++) {
					uc = ud.deal();
				}
				// other code
				break;
			case PLAYER:
				testUnoPlayer();
				// other code
				break;
			case GAME:
				testUnoGame(g);
				// other code
				break;
		}	
	}

	//=================================================
	private void testUnoGame(Graphics g) {
		
		UnoGame ug = new UnoGame();	
		
		//Add 4 players
		UnoPlayer up1 = new UnoPlayer("up1"),
				  up2 = new UnoPlayer("up2"),
				  up3 = new UnoPlayer("up3"),
				  up4 = new UnoPlayer("up4");	
		ug.addPlayer(up1);
		ug.addPlayer(up2);
		ug.addPlayer(up3);
		ug.addPlayer(up4);
		
		//Setup game
		ug.setUpGame();
		
		//Play game
		ug.playGame(g);
	}
	//=================================================
	
	private void testUnoPlayer() {
		UnoPlayer up1 = new UnoPlayer(null),
				  up2 = new UnoPlayer(null);
		UnoDeck ud = new UnoDeck();
		for(int x = 0; x < 7; x++) {
			up1.addCard(ud.deal());
			up2.addCard(ud.deal());
		}
		System.out.println(up1);
		System.out.println(up2);
		UnoCard uc1 = up1.getNextCard(ud.deal());
		UnoCard uc2 = up2.getNextCard(ud.deal());
		System.out.println(up1);
		System.out.println(uc1);
		System.out.println(up2);
		System.out.println(uc2);
		
	}
	private void testUnoPile() {
		UnoPile up = new UnoPile();
		for(int x = 0; x < 5; x++) {
			up.add(this.listOfCards.get(x));
			System.out.println(up);
		}
		up.shuffle();
		for(int x = 0; x < 2; x++) {
			
		}
	}
	private void testUnoDeck() {
		UnoDeck ud = new UnoDeck();
		ud.shuffle();
		for(int i = 0; i < ud.size(); i++) {
			System.out.println(ud.deal());
		}
		System.out.println(ud);
		while(ud.size() > 0) {
			if(ud.size()%3==0) {
				listOfCards.add(ud.deal());
			}
			else {
				ud.deal();
			}
		}
		System.out.println(listOfCards);
		
	}
	private void testUnoCards() {
		UnoCard g1=null, b10=null, y5=null, r2=null, gReverse=null, 
				ySkip=null, rDraw2=null, wild=null, wildDraw4=null;
		// TODO:  Initialize the cards.  Those cards will be added to 
		// the array below and then subsequently added to the 
		// ArrayList instance variable.
		g1 = new UnoCard(Color.green, 1); // green 1
		b10 = new UnoCard(Color.blue, 10); 
		y5 = new UnoCard(Color.yellow, 5); // yellow 5
		r2 = new UnoCard(Color.red, 2); //red draw 2
		gReverse = new UnoCard(Color.green, 10);
		ySkip = new UnoCard(Color.yellow, 11);
		rDraw2 = new UnoCard(Color.red, 12);
		wild = new UnoCard (Color.black, 13);
		wildDraw4 = new UnoCard (Color.black, 14);
		
		UnoCard[] ucArr = new UnoCard[] {g1, b10, y5, r2, gReverse,
				                   ySkip, rDraw2, wild, wildDraw4};
		// this calls the list's addAll method, but I needed to 
		// treat the array like a List.  Arrays.asList allows this
		this.listOfCards.addAll(Arrays.asList(ucArr));
		
		System.out.println(listOfCards);
		System.out.println(listOfCards.remove(0).getColor());
		System.out.println(listOfCards.remove(0).getFace());
	}

}
