import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class UnoGame {
    
	private int currPlayer;
    private int direction;	//clockwise, 0:counter-clockwise
    private ArrayList<UnoPlayer> players;
    private UnoPile discardPile;
    private UnoPile drawPile;	
    private UnoDeck deck;		

	public UnoGame() {
		currPlayer=0;
	    direction = 1;	//clockwise, 0:counter-clockwise
	    players = new ArrayList<>();
	    discardPile = new UnoPile();
	    drawPile = new UnoPile();
	    deck = new UnoDeck();	
	}
	
    public void addPlayer(UnoPlayer up) {
   	 	this.players.add(up); 	
    }
    
    // deals numCardsEach to each UnoPlayer in players
    // you can deal more cards initially if you want, the rest of the cards become drawPile
    public void dealCards(int numCardsEach) {
    	for (int x = 0; x < numCardsEach; x++)	{
	    	for (int p = 0; p < players.size(); p++)	{
	    		players.get(p).addCard(deck.deal());
	    	}    		
    	}  	
    	//move the rest of the cards to drawPile
   	 	for (int x = 0; x < deck.size(); x++) {
   	 		drawPile.add(deck.deal());
   	 	}
    }
    
    // remove a card from draw pile and add it to discard pile
    // the topmost card of the discard pile is the one facing up
    private void fillDrawPile() {
   	 	if (drawPile.size() > 0) {
   	    	//make sure the first card is not Wild or Wild-Draw4
	    	for (int c = 0; c < drawPile.size(); c++)	{
	    		if (drawPile.get(c).getFace() <= 12) {
		   	 		discardPile.add(drawPile.deal(c));
		   	 		break;
	    		}
	    	}   
   	 	}
   	 	else {
   	 		// Move all the discardPile back to drawPile
	    	for (int c = 0; c < discardPile.size(); c++)	{
	    		drawPile.add(discardPile.deal());
	    	}   
	    	
	    	//shuffle
	    	drawPile.shuffle();
	    	
	    	// remove a card from draw pile and add it to discard pile
	    	//make sure the first card is not Wild or Wild-Draw4
	    	for (int c = 0; c < drawPile.size(); c++)	{
	    		if (drawPile.get(c).getFace() <= 12) {
	      	 		discardPile.add(drawPile.deal());   
		   	 		break;
	    		}
	    	}   
   	 	}
    }
    
    public void setUpGame() {
   	 	deck.shuffle();		// of course
   	 	dealCards(7);		// you can deal more cards initially if you want, the rest of the cards become drawPile
   	 	fillDrawPile();		// remove a card from draw pile and add it to discard pile below
    }

    // game is over if any players are out of cards
    public boolean gameOver() {	 	
    	for (int p = 0; p < players.size(); p++) {
    		if (players.get(p).cardsLeft() == 0) {
    			return true;
    		}
    	}    		
   	 	return false;
    }
    
    public void updateState(boolean rev) {
    	if (rev == true) {
    		if (direction == 0) {
    			direction = 1;	//clockwise
    		}
    		else if (direction == 1) {
    			direction = 0;	//counter-clockwise
    		}  	   		
    	}
		if (direction == 1) { 	//clockwise
			currPlayer += 1;
			if (currPlayer >= players.size()) {
				currPlayer = 0;	//go to the first player
			}
		}
		else {	//counter-clockwise
			currPlayer -= 1;
			if (currPlayer < 0) {
				currPlayer = players.size() - 1; //go to the last player
			}		
		}  	   		
    }
    
    public void playGame(Graphics g) {
    	
    	UnoCard currCard;
    	UnoCard playerMatchCard;
    	UnoPlayer currUP;
    	boolean acted = false;
    	int rd = 0;
    	    	
	 	// the one on the top of the discard pile.
	 	currCard = discardPile.get(discardPile.size() - 1); 
	 	
   	 	while(!gameOver()) {
   	 		rd ++;
   			draw(g, currCard, rd);
    	 		
   	 		// get the current Player from the List
   	 		currUP = players.get(currPlayer);	 		
   	 		
      		 // once a card can be played, adjust the state of the game accordingly
      		 // advance to next player.  For instance, if a skip was played
      		 // or a draw2 was played or reverse was played, how does the
      		 // Game keep track of that?
   			if (currCard.getFace() == 10 && acted == false) {		//reverse
   				updateState(true);
   				acted = true;
   			}
   			else if (currCard.getFace() == 11 && acted == false) {  //skip
   				updateState(false);					
   				acted = true;
   			}
   			else if (currCard.getFace() == 12 && acted == false) {  //draw 2
   				if (drawPile.size() == 0) fillDrawPile();
   				currUP.addCard(drawPile.deal());
   				if (drawPile.size() == 0) fillDrawPile();
   				currUP.addCard(drawPile.deal());
	   	   	 	System.out.println("====> " + "<" + currUP.getName() + ">" + " DRAW 2 cards from draw pile");   	

   				updateState(false);
   				acted = true;
    		}
   			else if (currCard.getFace() == 14 && acted == false) {  //Wild + draw 4
   				if (drawPile.size() == 0) fillDrawPile();
   				currUP.addCard(drawPile.deal());
   				if (drawPile.size() == 0) fillDrawPile();
   				currUP.addCard(drawPile.deal());
   				if (drawPile.size() == 0) fillDrawPile();
   				currUP.addCard(drawPile.deal());
   				if (drawPile.size() == 0) fillDrawPile();
   				currUP.addCard(drawPile.deal());
	   	   	 	System.out.println("====> " + "<" + currUP.getName() + ">" + " DRAW 4 cards from draw pile");   	

   				updateState(false);
   				acted = true;
   			}
   			else {	// ask the current Player to play a card that matches
   	   	 		playerMatchCard = currUP.getNextCard(currCard);
	   	   	 	if (playerMatchCard == null) {
	   	   	 		// while the player has no card that matches, that player needs to draw another card
	   	 			// or we could limit it to a max number of cards to draw like 5, for instance
	   	   	 		int maxDraw = 6;
	   	   	 		int count = 0;
	   	   	 		while(playerMatchCard == null && count < maxDraw) {
	   	   	 			count ++;
	   	   	 			
	   	   	 			if (drawPile.size() == 0) fillDrawPile();
	   	   				currUP.addCard(drawPile.deal());	   	   				
	   		   	   	 	System.out.println("====> " + "<" + currUP.getName() + ">" + " DRAW 1 card from draw pile");   	
	   		   	   	 	
   		   	   	 		playerMatchCard = currUP.getNextCard(currCard);
   		   	   	 		if (playerMatchCard != null) {
   		   	   	 			System.out.println("====> " + "<" + currUP.getName() + ">" + " THROW the card [" + playerMatchCard.getCardName() + "]");   	
   		   	   	 		}
   		   	   	 		else if (count >= maxDraw) {
   		   	   	 			System.out.println("====> " + "<" + currUP.getName() + ">" + " reaches max number of cards to draw.");   		   	   	 			
   		   	   	 		}
		   	   	 	}
	   	   	 	}
	   	   	 	else {
   	   	 			System.out.println("====> " + "<" + currUP.getName() + ">" + " THROW the card [" + playerMatchCard.getCardName() + "]");   	
	   	 		}  	   	 	
	   	   	 	updateState(false);
	   	   	 	discardPile.add(currCard);
	   	   	 	currCard = playerMatchCard;
	   			acted = false;
   			}		 
   	 	}
   	 	
   	   	for (int p = 0; p < players.size(); p++) {
    		if (players.get(p).cardsLeft() == 0) {
    			System.out.println("");
    			System.out.println("Game Over! The player " + players.get(p).getName() + " wins !!!");
    		}
    	}    	   	
    }



    /**
     * Draws the current Game state.  Draw and Discard
     * Piles will be drawn in the middle.  Players will be
     * drawn in the following locations:
     *             <0>
     *  	3	Discard   Draw    1
     *             	2
     *
     */
    public void draw(Graphics g, UnoCard currCard, int rd) {
    	
		String name[];
		name = new String[players.size()];
		
		for (int x = 0; x < players.size(); x++) {
			if (x == currPlayer) {
				name[x] = "<" + players.get(x).getName() + ">";   				
			}
			else {
   				name[x] = players.get(x).getName();   				    				
			} 				
		}   		
		System.out.println("");   	
    	System.out.println("---------------------- " + String.valueOf(rd) + " -----------------------");   
    	if (direction == 1) {
        	System.out.println(" Direction = Clockwise");        		
    	}
    	else {
        	System.out.println(" Direction = Counter-clockwise");        		
    	}

		if (players.size() == 4) {
        	System.out.println("            " + name[0]);
        	System.out.println("  " + name[3] + "  " + "[" + currCard.getCardName() + "]" + 
        					   " " + "[]" + "  " + name[1]);
        	System.out.println("            " + name[2]);
        	System.out.println("");   	
        	
    	}
    	else {
    		System.out.println(name[0] + "'s turn");
    		System.out.println("Discard card is:" + currCard.getCardName());
        	System.out.println("");   	
    	}
		
		for (int x = 0; x < players.size(); x++) {
			if (x == currPlayer) {
				players.get(x).printCard();				
			}
			else {
				System.out.println(players.get(x).getName() + " has " + String.valueOf(players.get(x).cardsLeft()) + " card(s) now.");
			}
		}     		
    }
}



