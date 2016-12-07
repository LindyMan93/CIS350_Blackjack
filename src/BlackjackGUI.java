import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import edu.gvsu.GVpile;

// TODO: Auto-generated Javadoc
/**
 * This is the GUI of the Blackjack game. It will create a 
 * new Blackjack game and all the buttons to play the game.
 * 
 * @author Nathan Lindenbaum
 * @version 1.0
 * @since 2016-10-18
 *
 */
public class BlackjackGUI implements ActionListener {
	
	/** The Constant BLACKJACK. */
	static final int BLACKJACK = 21;

	/** The game. */
	private BlackjackGame game;
	
	/** The window. */
	private JFrame window;

	/** The deal button. */
	private JButton dealButton;
	
	/** The hit button. */
	private JButton hitButton;
	
	/** The stand button. */
	private JButton standButton;
	
	/** The double down button. */
	private JButton doubleDownButton;
	
	/** The bet button. */
	private JButton betButton;

	/** The balance. */
	private JLabel balance;
	
	/** The message. */
	private JLabel message;
	
	/** The dealer count label. */
	private JLabel dealerCountLabel;
	
	/** The player count label. */
	private JLabel playerCountLabel;
	
	/** The win streak. */
	private JLabel winStreak;

	/** The quit item. */
	private JMenuItem quitItem;
	
	/** The save win streak. */
	private JMenuItem saveWinStreak;
	
	/** The new game item. */
	private JMenuItem newGameItem;
	
	/** The hint item. */
	private JMenuItem hintItem;
	
	/** The tray panel. */
	private JPanel trayPanel = new JPanel();
	
	/** The player cards. */
	private GVpile playerCards;
	
	/** The dealer cards. */
	private GVpile dealerCards;

	/**
	 * Constructor is called by static void main.
	 * Sets up all parts of the GUI.
	 */
	public BlackjackGUI() {

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
		winStreak = new JLabel();

		setupMenus();
		setupFrame();
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
		bottomPanel.add(winStreak);
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

		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
		saveWinStreak = new JMenuItem("Save Wins");
		saveWinStreak.addActionListener(this);
		
		fileMenu.add(hintItem);
		fileMenu.add(newGameItem);
		fileMenu.add(quitItem);
		fileMenu.add(saveWinStreak);
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
		hint.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	 * Action performed.
	 *
	 * @param e event from button press
	 * @see java.awt.event.ActionListener#actionPerformed
	 * (java.awt.event.ActionEvent)
	 * 
	 * This method is used to indicate what button is pressed.
	 * Once the button is determined it will be follow the correct course
	 * of action. Then all the messages will be updated.
	 */
	public final void actionPerformed(final ActionEvent e) {

		JComponent buttonPressed = (JComponent) e.getSource();
		if (buttonPressed == saveWinStreak) {
			int bestWins = game.getBestGameWinStreak();
			File highScores = new File("HighScores.txt");
			try {
				if (highScores.exists() == false) {
					highScores.createNewFile();
				}
				PrintWriter out = new PrintWriter(highScores);
				String init = JOptionPane.showInputDialog(null, "Initials");
				out.append("Blackjack Win Streak by " + init + " of " + bestWins);
				out.close();
			} catch (IOException x) {
				
			}
		}
		
		if (buttonPressed == newGameItem) {
			new BlackjackGUI();
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
		winStreak.setText("Win Streak: " + game.getWinStreak());
	}

}
