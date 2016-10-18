import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.gvsu.GVpile;

/**
 * This is the GUI of the Blackjack game. It will create a 
 * new Blackjack game and all the buttons to play the game.
 * 
 * @author Nathan Lindenbaum
 * @version 1.0
 * @since 2016-10-18
 *
 */
public class GUI implements ActionListener {
	
	static final int BLACKJACK = 21;

	private BlackjackGame game;
	private JFrame window;

	private JButton dealButton;
	private JButton hitButton;
	private JButton standButton;
	private JButton doubleDownButton;
	private JButton betButton;

	private JLabel balance;
	private JLabel message;
	private JLabel dealerCountLabel;
	private JLabel playerCountLabel;

	private JMenuItem quitItem;
	private JMenuItem newGameItem;
	private JMenuItem hintItem;
	private JPanel trayPanel = new JPanel();
	private GVpile playerCards;
	private GVpile dealerCards;

	/**
	 * Constructor is called by static void main.
	 * Sets up all parts of the GUI.
	 */
	public GUI() {

		dealButton = new JButton("Deal");
		hitButton = new JButton("Hit");
		standButton = new JButton("Stand");
		doubleDownButton = new JButton("Double Down");
		betButton = new JButton("Bet");

		balance = new JLabel();
		message = new JLabel();


		window = new JFrame();
		game = new BlackjackGame();
		dealerCountLabel = new JLabel();
		playerCountLabel = new JLabel();

		setupMenus();
		setupFrame();
	}

	/**
	 * This method will run always. It calls the GUI() constructor.
	 * 
	 * @param args Main arguments from commandline.
	 */
	public static void main(final String[] args) {

		new GUI();

	}

	/**
	 * This method sets up the frame with all the buttons
	 * and labels.
	 */
	private void setupFrame() {

		trayPanel.setLayout(new BoxLayout(trayPanel, BoxLayout.X_AXIS));
		playerCards = game.getPlayerCards();
		dealerCards = game.getDealerCards();
		trayPanel.add(playerCards);
		trayPanel.add(dealerCards);
		window.add(playerCards, "Center");
		window.add(dealerCards, "North");

		JPanel bottomPanel = new JPanel();
		bottomPanel.add(dealButton);
		bottomPanel.add(hitButton);
		bottomPanel.add(standButton);
		bottomPanel.add(doubleDownButton);
		bottomPanel.add(betButton);
		bottomPanel.add(balance);
		bottomPanel.add(message);
		balance.setText("Credits: " + game.getCreditBalance());
		message.setText(game.getMessage());

		hitButton.setEnabled(false);
		standButton.setEnabled(false);
		doubleDownButton.setEnabled(false);

		dealButton.addActionListener(this);
		hitButton.addActionListener(this);
		standButton.addActionListener(this);
		doubleDownButton.addActionListener(this);
		betButton.addActionListener(this);

		JPanel topPanel = new JPanel();
		topPanel.add(dealerCountLabel);
		topPanel.add(playerCountLabel);

		window.add(bottomPanel, "South");
		window.add(topPanel, "East");

		window.setTitle("GVSU Blackjack");
		window.setVisible(true);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.pack();

	}

	/**
	 * This method will set up the menu bar with 3 options:
	 * HINT
	 * NEW GAME
	 * QUIT
	 * .
	 * 
	 */
	private void setupMenus() {

		JMenuBar menus = new JMenuBar();
		window.setJMenuBar(menus);

		JMenu fileMenu = new JMenu("File");
		menus.add(fileMenu);

		hintItem = new JMenuItem("Hint Card");
		hintItem.addActionListener(this);

		newGameItem = new JMenuItem("New Game");
		newGameItem.addActionListener(this);
		quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(this);

		fileMenu.add(hintItem);
		fileMenu.add(newGameItem);
		fileMenu.add(quitItem);

	}

	/**
	 * This method is called when the player selects the HintCard button.
	 * It will show, in a new frame, a hint card that tells when to hit
	 * and stand
	 */
	private void displayHintCard() {

		JFrame hint = new JFrame();
		ImageIcon hintJPG = new ImageIcon("BJ_Strat.jpg");
		JLabel hintHolder = new JLabel(hintJPG);
		hint.getContentPane().add(hintHolder);
		hint.setVisible(true);
		hint.pack();
	}
	
	/**
	 * This method will return a String that updates the dealers
	 * count label to the present count.
	 * @return ("Dealer Hand: " + game.dealerCountTotal())
	 */
	private String dealerCountMess() { 
		
		return ("Dealer Hand: " + game.dealerCountTotal());
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed
	 * (java.awt.event.ActionEvent)
	 * 
	 * This method is used to indicate what button is pressed.
	 * Once the button is determined it will be follow the correct course
	 * of action. Then all the messages will be updated.
	 * 
	 * @param e event from button press
	 */
	public final void actionPerformed(final ActionEvent e) {

		JComponent buttonPressed = (JComponent) e.getSource();
		if (buttonPressed == newGameItem) {
			new GUI();
		}
		if (buttonPressed == hintItem) {
			displayHintCard();
		}
		if (buttonPressed == hitButton) {
			game.playerDraw();
			doubleDownButton.setEnabled(false);
			if (game.playerCountTotal() > BLACKJACK) {
				dealButton.setEnabled(true);
				betButton.setEnabled(true);
				hitButton.setEnabled(false);
				standButton.setEnabled(false);
				doubleDownButton.setEnabled(false);
			}
			if (game.playerCountTotal() == BLACKJACK) {
				game.stand();
				dealerCountLabel.setText(dealerCountMess());
				dealButton.setEnabled(true);
				betButton.setEnabled(true);
				hitButton.setEnabled(false);
				standButton.setEnabled(false);
				doubleDownButton.setEnabled(false);
			}
		}
		if (buttonPressed == dealButton) {
			game.deal();
			playerCards.repaint();
			dealerCards.repaint();
			dealerCountLabel.setText("");
			message.setText(game.getMessage());
			if (game.playerCountTotal() == BLACKJACK) {
				hitButton.setEnabled(false);
				doubleDownButton.setEnabled(false);
				standButton.setEnabled(false);
				dealButton.setEnabled(true);
				betButton.setEnabled(true);
			} else {
				hitButton.setEnabled(true);
				doubleDownButton.setEnabled(true);
				standButton.setEnabled(true);
				dealButton.setEnabled(false);
				betButton.setEnabled(false);
			}

		}
		if (buttonPressed == betButton) {
			game.placeBet();
		}

		if (buttonPressed == standButton) {
			game.stand();
			dealerCountLabel.setText(dealerCountMess());
			dealButton.setEnabled(true);
			betButton.setEnabled(true);
			hitButton.setEnabled(false);
			standButton.setEnabled(false);
			doubleDownButton.setEnabled(false);
		}

		if (buttonPressed == doubleDownButton) {
			game.doubleDown();
			game.playerDraw();
			game.stand();
			dealerCountLabel.setText(dealerCountMess());
			dealButton.setEnabled(true);
			betButton.setEnabled(true);
			hitButton.setEnabled(false);
			standButton.setEnabled(false);
			doubleDownButton.setEnabled(false);
			game.placeBet();
		}
		playerCountLabel.setText("Player Hand: " + game.playerCountTotal());
		balance.setText("Credits: " + game.getCreditBalance());
		message.setText(game.getMessage());
		if (game.getCreditBalance() < 0) {
			message.setText("No Money. Start New Game");
			balance.setText("Credits: 0");
			dealButton.setEnabled(false);
			betButton.setEnabled(false);
			hitButton.setEnabled(false);
			standButton.setEnabled(false);
			doubleDownButton.setEnabled(false);
		}

	}

}
