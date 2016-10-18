import edu.gvsu.GVcard;
import edu.gvsu.GVpile;
import javax.swing.JOptionPane;
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
	private final int faceCard = 10;
	private final int jack = 11;
	private final int queen = 12;
	private final int king = 13;
	private final int ace = 14;
	private final int newAce = 11;
	private final int aceDiff = 10;
	private final int minDeckCards = 15;
	private final int dealerMinHand = 17;
	private final int cardIndent = 20;
	private final int blackjack = 21;
	private final int notBlackjack = 22;
	private final int maxBet = 250;
	private final int minBet = 5;
	private final int credits = 50;
	private int dealerCount, playerCount;
	private int creditBalance, bet;
	private int numberOfAcesp = 0, numberOfAcesd = 0;
	private GVcard c;
	private GVpile deck, playerCards;
	private GVpile playerTempCards, dealerCards, dealerTempCards;
	private String message;
	private final String notEnough = "You dont have enough credits";
	private final String tableMin = "The table minimum is 5";
	private final String tableMax = "The table maximum is 250";
	private final String validInt = "Must enter an integer greater than 0";
	private final String betMess = "To change bet click bet. \nAmount to Bet: ";

	/**
	 * 
	 */
	public BlackjackGame() {

		creditBalance = credits;
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

		playerCards = new GVpile(0, cardIndent, 0);
		dealerCards = new GVpile(0, cardIndent, 0);
		dealerTempCards = new GVpile(0, cardIndent, 0);
		playerTempCards = new GVpile(0, cardIndent, 0);
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
		if (deck.getComponentCount() < minDeckCards) {
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
		if (playerCount == blackjack) {
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
		if (playerCount > blackjack) {
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
		while (dealerCountTotal() < dealerMinHand) {
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
		String sbet = JOptionPane.showInputDialog(betMess);
		try {
			bet = Integer.parseInt(sbet);
			if (bet > creditBalance) {
				JOptionPane.showMessageDialog(null, notEnough);
				placeBet();
			}
			if (bet < minBet) {
				JOptionPane.showMessageDialog(null, tableMin);
				placeBet();
			}
			if (bet > maxBet) {
				JOptionPane.showMessageDialog(null, tableMax);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, validInt);
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
		if (x == jack || x == queen || x == king || x == ace) {
			if (x == ace) {
				x = newAce;
				if (hand.equals("player")) {
					numberOfAcesp += 1;
				}
				if (hand.equals("dealer")) {
					numberOfAcesd += 1;
				}
				System.out.println("cards values being added: " + x);
				return x;
			} else {
				x = faceCard;
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
				&& playerCount <= blackjack);
		boolean dealerBustScore = (playerCount <= blackjack 
				&& dealerCount > blackjack);
		boolean dealerBJ = (dealerCount == blackjack);
		boolean playerBJ = (playerCount == blackjack && !dealerBJ);
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
				&& playerCount > blackjack) {
			if (numberOfAcesp == 1 || (numberOfAcesp == 2 
					&& playerCount == notBlackjack)) {
				playerCount = playerCount - aceDiff;
				numberOfAcesp = numberOfAcesp - 1;
			}
			if (numberOfAcesp == 2 && playerCount != notBlackjack) {
				playerCount = playerCount - (aceDiff * 2);
				numberOfAcesp = numberOfAcesp - 2;
			}
		}
		if (hand.equals("dealer") && numberOfAcesd > 0 
				&& dealerCount > blackjack) {
			if (numberOfAcesd == 1 || (numberOfAcesd == 2 
					&& dealerCount == notBlackjack)) {
				dealerCount = dealerCount - aceDiff;
				numberOfAcesd = numberOfAcesd - 1;
			}
			if (numberOfAcesd == 2 && dealerCount != notBlackjack) {
				dealerCount = dealerCount - (aceDiff * 2);
				numberOfAcesd = numberOfAcesd - 2;
			}
		}
	}

}
