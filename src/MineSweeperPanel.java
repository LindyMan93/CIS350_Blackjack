import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// TODO: Auto-generated Javadoc
/**
 * The Class MineSweeperPanel.
 */
public class MineSweeperPanel extends JPanel {
	
	/** The board. */
	private JButton[][] board;
	
	/** The i cell. */
	private Cell iCell;
	
	/** The test. */
	private JButton quitButton, reset, test, returnToHub, save;
	
	/** The game. */
	private MineSweeperGame game;
	
	/** The Mine field. */
	private JPanel quit, mineField;
	
	/** The empty icon. */
	private ImageIcon emptyIcon; // one, two, three, four, five, six, seven,
	
	/** The Mine count. */
	// eight;
	private int boardSize, mineCount;
	
	/** The frame size X. */
	private final int frameSizeX = 1024;
	
	/** The frame size Y. */
	private final int frameSizeY = 768;
	
	/** The button size. */
	private final int buttonSize = 50;
	
	/** The grid size. */
	private final int gridSize = 5;
	

	/**
	 * Instantiates a new mine sweeper panel.
	 */
	public MineSweeperPanel() {

		emptyIcon = new ImageIcon("Mine.jpeg");
		// one = new ImageIcon("");

		game = new MineSweeperGame();
		
		boardSize = game.getBoardSize();
		mineCount = game.getMineCount();
		// Adding minefield Panel
		mineField = new JPanel();
		board = new JButton[boardSize][boardSize];
		mineField.setLayout(new GridLayout(boardSize, boardSize));
		mineField.setSize(frameSizeX, frameSizeY);
		MouseEventHandler meh = new MouseEventHandler();
		// adding array of buttons depending on users size
		// adding event listeners for all buttons
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				Dimension buttSize = new Dimension(buttonSize, buttonSize);
				board[row][col] = new JButton("", emptyIcon);
				board[row][col].setPreferredSize(buttSize);
				board[row][col].addActionListener(new ButtonListener());
				board[row][col].addMouseListener(meh);
				mineField.add(board[row][col]);
			}
		}
		add(mineField);
		// adding quit panel
		quit = new JPanel();
		quit.setLayout(new GridLayout(gridSize, 1));
		returnToHub = new JButton("Game Hub");
		returnToHub.addActionListener(new ButtonListener());
		save = new JButton("Save Streak");
		save.addActionListener(new ButtonListener());
		
		
		quitButton = new JButton("Quit");
		quitButton.addActionListener(new ButtonListener());
		quitButton.setVisible(false);
		reset = new JButton("Reset");
		reset.addActionListener(new ButtonListener());
		test = new JButton("Cheats");
		test.addActionListener(new ButtonListener());
		quit.add(quitButton);
		quit.add(reset);
		quit.add(test);
		quit.add(returnToHub);
		quit.add(save);
		
		add(quit);

	}

	/**
	 * This will display the cell so that the value can be seen on the board.
	 */
	private void displayBoard() {
		for (int row = 0; row < boardSize; row++) {
			for (int col = 0; col < boardSize; col++) {
				iCell = game.getCell(row, col);

				if (iCell.getIsExposed()) {
					board[row][col].setEnabled(false);
				} else {
					board[row][col].setEnabled(true);
				}
				if (iCell.getIsExposed()) {
					if (iCell.getIsMine()) {
						board[row][col].setText("M");
					} else {
						if (iCell.getMineCount() == 0) {
							board[row][col].setText(" ");
						} else {
							board[row][col].setText("" + iCell.getMineCount());
						}
					}
				}
			}
		}

	}
	

	/**
	 * The listener interface for receiving button events.
	 * The class that is interested in processing a button
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's addButtonListener method. When
	 * the button event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ButtonEvent
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * (non-Javadoc) This is a private class that will "listen" for what the
		 * buttons are doing and return the result of which button was pressed
		 * and how.
		 */
		public void actionPerformed(final ActionEvent event) {

			JComponent buttonPressed = (JComponent) event.getSource();
			
			if (buttonPressed == returnToHub) {
				new CentralGameGUI();
			}
			
			String cheater = "You're a Cheater";
			
			if (buttonPressed == reset) {
				game.reset();
			}
			if (buttonPressed == test) {
				String cheats = JOptionPane.showInputDialog(null, cheater);
				if (cheats.equals("Test")) {
					for (int row = 0; row < boardSize; row++) {
						for (int col = 0; col < boardSize; col++) {
							iCell = game.getCell(row, col);
							int cellMineCount = iCell.getMineCount();
							if (iCell.getIsMine()) {
								board[row][col].setText("M");
							} else {
								if (iCell.getMineCount() == 0) {
									board[row][col].setText(" ");
								} else {
									board[row][col].setText("" + cellMineCount);
								}
							}
						}
					}
				}
				if (cheats.equals("Nathan Lindenbaum")) {
					JOptionPane.showMessageDialog(null, "You are the Master!");
					for (int row = 0; row < boardSize; row++) {
						for (int col = 0; col < boardSize; col++) {
							iCell = game.getCell(row, col);
							if (iCell.getIsMine()) {
								board[row][col].setText("M");
							}
						}
					}
				
				}
				if (cheats.equals("Random")) {
					for (int n = 0; n < mineCount; n++) {
						Random random = new Random();
						int col = random.nextInt(boardSize);
						int row = random.nextInt(boardSize);
						iCell = game.getCell(row, col);
						board[row][col].setText("F");
						iCell.setIsFlagged(true);
						if (game.getGameStatus(row, col) == GameStatus.Win) {
							JOptionPane.showMessageDialog(null, "You Win!");
						}
					

					}
				}
			}

		}

	}

	/**
	 * The Class MouseEventHandler.
	 */
	private class MouseEventHandler implements MouseListener {

		/** (non-Javadoc).
		 * @see java.awt.event.MouseListener#mousePressed
		 */
		public void mousePressed(final MouseEvent e) {

		}

		/** (non-Javadoc).
		 * @see java.awt.event.MouseListener#mouseReleased
		 */
		public void mouseReleased(final MouseEvent e) {

		}

		/** (non-Javadoc).
		 * @see java.awt.event.MouseListener#mouseEntered
		 */
		public void mouseEntered(final MouseEvent e) {

		}

		/** (non-Javadoc).
		 * @see java.awt.event.MouseListener#mouseExited
		 */
		public void mouseExited(final MouseEvent e) {

		}

		/**
		 * (non-Javadoc).
		 * 
		 * @see
		 * java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(final MouseEvent event) {
			JComponent buttonPressed = (JComponent) event.getSource();
			boolean control = event.isControlDown();
			String loser = "You're a Loser!";
			// Check if the left or the right buttons were clicked
			if (SwingUtilities.isLeftMouseButton(event) && !control) {
				for (int row = 0; row < boardSize; row++) {
					for (int col = 0; col < boardSize; col++) {
						GameStatus cellS = game.getGameStatus(row, col);
						if (board[row][col] == buttonPressed) {
							game.select(row, col); // see step 6
							displayBoard();
							cellS = game.getGameStatus(row, col);
							if (cellS == GameStatus.Lose) {
								JOptionPane.showMessageDialog(null, loser);
							}
							if (cellS == GameStatus.Win) {
								JOptionPane.showMessageDialog(null, "You Win!");
							}
						}
					}
				}
			}
			if (SwingUtilities.isRightMouseButton(event) || control) {
				// setting button to flagged and status to is flagged
				for (int row = 0; row < boardSize; row++) {
					for (int col = 0; col < boardSize; col++) {
						if (board[row][col] == buttonPressed) {
							iCell = game.getCell(row, col);
							GameStatus cellS = game.getGameStatus(row, col);
							board[row][col].setText("F");
							iCell.setIsFlagged(true);
							if (cellS == GameStatus.Win) {
								JOptionPane.showMessageDialog(null, "You Win");
							}
							if (cellS == GameStatus.Lose) {
								JOptionPane.showMessageDialog(null, "You Lose");
							}
						} 
					}
				}
			}
			

		}
		
	}
}
