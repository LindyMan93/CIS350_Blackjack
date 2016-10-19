import edu.gvsu.GVcard;
import edu.gvsu.GVpile;
import javax.swing.JOptionPane;
// TODO: Auto-generated Javadoc
/**
 * This is the a game class for blackjack. It uses the edu.gvsu.* 
 * package. It is only used by the GUI class to run the program.
 * One player will be able to play against the dealer in blackjack.
 * 
 * @author Nathan Lindenbaum
 * @version 1.0
 * @since 2016-10-18
 * @
 *
 */

public class BlackjackGame {
	
	/** The Constant FACECARD. */
	private static final int FACECARD = 10;
	
	/** The Constant JACK. */
	private static final int JACK = 11;
	
	/** The Constant QUEEN. */
	private static final int QUEEN = 12;
	
	/** The Constant KING. */
	private static final int KING = 13;
	
	/** The Constant ACE. */
	private static final int ACE = 14;
	
	/** The Constant NEWACE. */
	private static final int NEWACE = 11;
	
	/** The Constant ACE_DIFF. */
	private static final int ACE_DIFF = 10;
	
	/** The Constant MIN_DECK_CARDS. */
	private static final int MIN_DECK_CARDS = 15;
	
	/** The Constant DEALER_MIN_HAND. */
	private static final int DEALER_MIN_HAND = 17;
	
	/** The Constant CARD_INDENT. */
	private static final int CARD_INDENT = 20;
	
	/** The Constant BLACKJACK. */
	private static final int BLACKJACK = 21;
	
	/** The Constant NOT_BLACKJACK. */
	private static final int NOT_BLACKJACK = 22;
	
	/** The Constant MAX_BET. */
	private static final int MAX_BET = 250;
	
	/** The Constant MIN_BET. */
	private static final int MIN_BET = 5;
	
	/** The Constant CREDITS. */
	private static final int CREDITS = 50;
	
	/** The player count. */
	private int dealerCount, playerCount;
	
	/** The bet. */
	private int creditBalance, bet;
	
	/** The number of acesd. */
	private int numberOfAcesp = 0, numberOfAcesd = 0;
	
	/** The c. */
	private GVcard c;
	
	/** The player cards. */
	private GVpile deck, playerCards;
	
	/** The dealer temp cards. */
	private GVpile playerTempCards, dealerCards, dealerTempCards;
	
	/** The message. */
	private String message;
	
	/** The Constant NOT_ENOUGH. */
	private static final String NOT_ENOUGH = "You dont have enough credits";
	
	/** The Constant TABLE_MIN. */
	private static final String TABLE_MIN = "The table minimum is 5";
	
	/** The Constant TABLE_MAX. */
	private static final String TABLE_MAX = "The table maximum is 250";
	
	/** The Constant VALID_INT. */
	private static final String VALID_INT
		= "Must enter an integer greater than 0";
	
	/** The Constant BET_MESS. */
	private static final String BET_MESS
		= "To change bet click bet. \nAmount to Bet: ";

	/**
	 * Instantiates a new blackjack game.
	 */
	public BlackjackGame() {

		creditBalance = CREDITS;
		bet = 0;
		deck = new GVpile(1, 0, 0);
		createHandPiles();
		newShoe();
		placeBet();

	}

	/**
	 * This method will return the GVpile of the players
	 * cards. This pile is painted in the GUI.
	 * @return playerCards
	 */
	public final GVpile getPlayerCards() {

		return playerCards;
	}

	/**
	 * This method will return the GVpile of dealer
	 * cards. This pile is painted in the GUI.
	 * @return dealerCards
	 */
	public final GVpile getDealerCards() {

		return dealerCards;
	}

	/**
	 * This method will update all the piles that are used
	 * in the game to be empty.
	 * 
	 */
	private void createHandPiles() {

		playerCards = new GVpile(0, CARD_INDENT, 0);
		dealerCards = new GVpile(0, CARD_INDENT, 0);
		dealerTempCards = new GVpile(0, CARD_INDENT, 0);
		playerTempCards = new GVpile(0, CARD_INDENT, 0);
	}

	/**
	 * This method is a getter method for an updating message.
	 * 
	 * @return message
	 */
	public final String getMessage() {

		return message;
	}

	/**
	 * This method will update the deck (1 single deck).
	 * Then it will shuffle the deck and remove the first
	 * card.
	 * 
	 * Uses GVcard and GVpile.
	 */
	private void newShoe() {

		deck = new GVpile(1, 0, 0);
		deck.shuffle();
		deck.pop();
	}

	/**
	 * This method uses the GVpile command of .pop().
	 * It will clear out all the values that are currently
	 * in each of the piles.
	 * 
	 */
	private void clearTable() {

		while (dealerCards.pop() != null) {
			assert true;
		}
		while (playerCards.pop() != null) {
			assert true;
		}
		while (dealerTempCards.pop() != null) {
			assert true;
		}
		while (playerTempCards.pop() != null) {
			assert true;
		}
	}

	/**
	 * This method is called by the GUI when a player 
	 * wants to double their bet and only draw one
	 * card. It will update the current bet to double
	 * what it was originally.
	 *
	 */
	public final void doubleDown() {

		creditBalance -= bet;
		bet = 2 * bet;
	}

	/**
	 * This method will deal the initial 4 cards. 2 to 
	 * the player and 2 to the dealer. It will then check
	 * to see if the player has a Blackjack.
	 */
	public final void deal() {

		message = "Dealing Next Hand";
		//checking to see if there are enough cards for the hand
		if (deck.getComponentCount() < MIN_DECK_CARDS) {
			message = "Starting a New Shoe";
			newShoe();
		}
		//checks if the current bet is greater than balance
		if (bet > creditBalance) {
			placeBet();
		}
		clearTable();
		creditBalance -= bet;
		dealerCount = 0;
		playerCount = 0;
		numberOfAcesp = 0;
		numberOfAcesd = 0;

		//add player first card and update playerCount
		c = deck.pop();
		playerCount += realCardValue(c, "player");
		playerCards.push(c);
		playerTempCards.push(playerCards.pop());
		playerCards.push(playerTempCards.pop());
		playerCards.topCard().faceUp();

		//add dealer first card and update dealerCount
		c = deck.pop();
		dealerCount += realCardValue(c, "dealer");
		dealerCards.push(c);
		dealerCards.topCard().faceDown();

		//add player second card and update playerCount
		c = deck.pop();
		playerCount += realCardValue(c, "player");
		playerCards.push(c);
		playerCards.topCard().faceUp();

		//add dealer second card and update dealerCount
		c = deck.pop();
		dealerCount += realCardValue(c, "dealer");
		dealerCards.push(c);
		dealerCards.topCard().faceUp();

		//adjusting counts if two aces
		adjustHandValueAce("player");
		adjustHandValueAce("dealer");

		//check if player has blackjack
		if (playerCount == BLACKJACK) {
			message = "Player Blackjack!";
			creditBalance = creditBalance + (bet * 2) + (bet / 2);
		}

	}

	/**
	 * This method will pop a card from the deck.
	 * Then it will update the player count and
	 * check to see if the player busted.
	 */
	public final void playerDraw() {
		c = deck.pop();
		playerCount += realCardValue(c, "player");
		playerCards.push(c);
		playerCards.topCard().faceUp();
		adjustHandValueAce("player");
		if (playerCount > BLACKJACK) {
			message = "Player Bust";
			creditBalance -= bet;
		}
		System.out.println("Player Count hit: " + playerCount);
	}

	/**
	 * This method will pop a card from the deck.
	 * Then it will update the dealer count and
	 */
	public final void dealerDraw() {
		c = deck.pop();
		dealerCount += realCardValue(c, "dealer");
		dealerCards.push(c);
		dealerCards.topCard().faceUp();
		adjustHandValueAce("dealer");
	}

	/**
	 * First, this method will flip the first dealer card
	 * by adding it to a new pile and then popping it off.
	 * Then, it will continue to call {@link #dealerDraw()}
	 * until the dealer count is greater than 17.
	 * Last, it will check if player is a winner {@link #checkWinner()}
	 */
	public final void stand() {
		dealerTempCards.push(dealerCards.pop());
		dealerTempCards.push(dealerCards.pop());
		dealerCards.push(dealerTempCards.pop());
		dealerCards.topCard().faceUp();
		dealerCards.push(dealerTempCards.pop());
		dealerCards.topCard().faceUp();
		while (dealerCountTotal() < DEALER_MIN_HAND) {
			dealerDraw();
		}
		if (checkWinner()) {
			creditBalance = creditBalance + (bet * 2);
			message = "Player Wins!";
		} else {
			if (playerCount == dealerCount) {
				creditBalance = creditBalance + bet;
				message = "It's a Push";
			} else {
				message = "Dealer Wins";
			}
		}
	}

	/**
	 * This method will return the present value of
	 * the dealers cards.
	 * @return dealerCount
	 */
	public final int dealerCountTotal() {
		return dealerCount;
	}

	/**
	 * This method will return the present value of
	 * the players cards.
	 * @return playerCount
	 */
	public final int playerCountTotal() {
		return playerCount;
	}

	/**
	 * This method will update the players bet. It will
	 * open a JOptionPane and ask the player to input an
	 * integer greater than 0. It will check to make sure
	 * it is a valid choice, then update the bet.
	 */
	public final void placeBet() {
		String sbet = JOptionPane.showInputDialog(BET_MESS);
		try {
			bet = Integer.parseInt(sbet);
			if (bet > creditBalance) {
				JOptionPane.showMessageDialog(null, NOT_ENOUGH);
				placeBet();
			}
			if (bet < MIN_BET) {
				JOptionPane.showMessageDialog(null, TABLE_MIN);
				placeBet();
			}
			if (bet > MAX_BET) {
				JOptionPane.showMessageDialog(null, TABLE_MAX);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, VALID_INT);
			placeBet();
		}
	}

	/**
	 * This method will return the present amount of 
	 * credits.
	 * @return creditBalance
	 */
	public final int getCreditBalance() {
		return creditBalance;
	}

	/**
	 * This method will take two parameters. cardCheck is the
	 * GVcard the needs to have the correct value associated 
	 * to it. hand is just a String to indicate which player's
	 * ace count will be updated. the local variable x is the 
	 * cards value and will be updated if it is a face card.
	 * @param cardCheck GVcard associated with player card
	 * @param hand String to indicate which player's ace count is updated
	 * @return x
	 */
	private int realCardValue(final GVcard cardCheck, final String hand) {
		int x = cardCheck.getValue();
		if (x == JACK || x == QUEEN || x == KING || x == ACE) {
			if (x == ACE) {
				x = NEWACE;
				if (hand.equals("player")) {
					numberOfAcesp += 1;
				}
				if (hand.equals("dealer")) {
					numberOfAcesd += 1;
				}
				System.out.println("cards values being added: " + x);
				return x;
			} else {
				x = FACECARD;
				System.out.println("cards values being added: " + x);
				return x;
			}
		} else {
			System.out.println("cards values being added: " + x);
			return x;
		}
	}
	
	/**
	 * This method will check to see if the player has a 
	 * winning hand. 
	 * @return playerBetterScore || dealerBustScore || playerBJ
	 */
	private boolean checkWinner() {
		boolean playerBetterScore = (playerCount > dealerCount 
				&& playerCount <= BLACKJACK);
		boolean dealerBustScore = (playerCount <= BLACKJACK 
				&& dealerCount > BLACKJACK);
		boolean dealerBJ = (dealerCount == BLACKJACK);
		boolean playerBJ = (playerCount == BLACKJACK && !dealerBJ);
		return playerBetterScore || dealerBustScore || playerBJ;
	}

	/**
	 * This method will be called after each new card is dealt.
	 * It will check to see if the player or dealer has an ace.
	 * Then it will update the playerCount or dealerCount accordingly.
	 * @param hand See if player has ace
	 */
	private void adjustHandValueAce(final String hand) {
		if (hand.equals("player") && numberOfAcesp > 0 
				&& playerCount > BLACKJACK) {
			if (numberOfAcesp == 1 || (numberOfAcesp == 2 
					&& playerCount == NOT_BLACKJACK)) {
				playerCount = playerCount - ACE_DIFF;
				numberOfAcesp = numberOfAcesp - 1;
			}
			if (numberOfAcesp == 2 && playerCount != NOT_BLACKJACK) {
				playerCount = playerCount - (ACE_DIFF * 2);
				numberOfAcesp = numberOfAcesp - 2;
			}
		}
		if (hand.equals("dealer") && numberOfAcesd > 0 
				&& dealerCount > BLACKJACK) {
			if (numberOfAcesd == 1 || (numberOfAcesd == 2 
					&& dealerCount == NOT_BLACKJACK)) {
				dealerCount = dealerCount - ACE_DIFF;
				numberOfAcesd = numberOfAcesd - 1;
			}
			if (numberOfAcesd == 2 && dealerCount != NOT_BLACKJACK) {
				dealerCount = dealerCount - (ACE_DIFF * 2);
				numberOfAcesd = numberOfAcesd - 2;
			}
		}
	}

}
