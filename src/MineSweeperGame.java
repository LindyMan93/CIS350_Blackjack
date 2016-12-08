import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// TODO: Auto-generated Javadoc
/**
 * The Class MineSweeperGame.
 *
 * @author Nathan Lindenbaum
 */
public class MineSweeperGame {

	/**
	 * heart of the game. This class will determine each cells characteristics
	 * as well as determine the size and number of mines the user wants to play
	 * with.
	 */
	private Cell[][] board;
	
	/** The status. */
	private GameStatus status;
	
	/** The total mine count. */
	private int totalMineCount; // default number of mines
	
	/** The neighbor mine count. */
	private int neighborMineCount;
	
	/** The user board size. */
	private int userBoardSize;
	
	/** The default size. */
	private static final int DEFAULT_SIZE = 10;
	
	/** The default mines. */
	private static final int DEFAULT_MINES = 9;
	
	/** The max size. */
	private static final int MAX_SIZE = 31;
	
	/** The min size. */
	private static final int MIN_SIZE = 2;
	
	/** The Constant dBoardMessage. */
	private static final String BOARDMESSAGE = "Default Board has been set";
	
	/** The valid board mess. */
	private static String validBoardMess = "Enter Values between 3 and 30";

	/**
	 * This will start the game with the users parameters.
	 */
	public MineSweeperGame() {
		String sizeInput = JOptionPane.showInputDialog(null, "Board Size");
		int size = Integer.parseInt(sizeInput);
		if (size <= MIN_SIZE || size >= MAX_SIZE) {
			JOptionPane.showMessageDialog(null, BOARDMESSAGE);
			JOptionPane.showMessageDialog(null, validBoardMess);
			size = DEFAULT_SIZE;
		}
		String mineInput = JOptionPane.showInputDialog(null, "Mine Count");
		int mines = Integer.parseInt(mineInput);
		if (mines >= (size * size)) {
			String lessBoardS = "Enter Values less than board size squared";
			JOptionPane.showMessageDialog(null, "Default Mines have been set");
			JOptionPane.showMessageDialog(null, lessBoardS);
			mines = DEFAULT_MINES;
		}

		setBoardSize(size);
		setMineCount(mines);
		board = new Cell[userBoardSize][userBoardSize];
		for (int row = 0; row < userBoardSize; row++) {
			for (int col = 0; col < userBoardSize; col++) {
				// this constructor creates a new with mineCount of 0, flagged
				// false,
				// exposed = false, isMine = false
				board[row][col] = new Cell(0, false, false, false);
			}
		}
		Random random = new Random();
		int mineCount = 0;
		while (mineCount < totalMineCount) { // placing 10 mines (Default)
			int col = random.nextInt(userBoardSize);
			int row = random.nextInt(userBoardSize);

			if (!board[row][col].getIsMine()) {
				board[row][col].setIsMine(true);
				mineCount++;
			}
		}

		for (int row = 0; row < userBoardSize; row++) {
			for (int col = 0; col < userBoardSize; col++) {
				neighbors(row, col);
			}
		}

	}

	/**
	 * Sets the board size.
	 *
	 * @param x the new board size
	 */
	// sets board size
	public final void setBoardSize(final int x) {
		userBoardSize = x;
	}

	/**
	 * Sets the mine count.
	 *
	 * @param x the new mine count
	 */
	// sets number of mines being used
	public final void setMineCount(final int x) {
		totalMineCount = x;
	}

	/**
	 * Gets the board size.
	 *
	 * @return the board size
	 */
	// returns board size
	public final int getBoardSize() {
		return userBoardSize;
	}

	/**
	 * Gets the mine count.
	 *
	 * @return the mine count
	 */
	// returns numbers of mines
	public final int getMineCount() {
		return totalMineCount;
	}

	// when this method is called it determines the characteristics of the cell
	// input.
	/**
	 * Select.
	 *
	 * @param row the row
	 * @param col the col
	 */
	//
	public final void select(final int row, final int col) {
		if (board[row][col].getIsFlagged()) {
			return; // is flagged do nothing
		} else {
			if (board[row][col].getMineCount() == 0) {

				spreadSpace(row, col);
				board[row][col].setIsExposed(true);
			}
			board[row][col].setIsExposed(true);

		}
		if (board[row][col].getIsMine()) {
			System.out.println("This is a mine");
			status = GameStatus.Lose;
		}

	}

	/**
	 * Spread space.
	 *
	 * @param row            = row of cell
	 * @param col            = column of cell
	 * 
	 *            This method is called when a button/cell is clicked that has a
	 *            minecount of 0 it will make the 8 cells around it show their
	 *            values.
	 */
	private void spreadSpace(final int row, final int col) {
		if (!validCoordinates(row, col) || board[row][col].getIsExposed()) {
			return;
		}
		if (board[row][col].getMineCount() == 0) {
			board[row][col].setIsExposed(true);
			for (int n = row - 1; n <= row + 1; n++) {
				for (int c = col - 1; c <= col + 1; c++) {
					if (n == row && c == col) {
						continue;
					} else {
						spreadSpace(n, c);
					}
				}
			}
		}
		if (board[row][col].getMineCount() > 0) {
			board[row][col].setIsExposed(true);
		}
	}

	/**
	 * Valid coordinates.
	 *
	 * @param row the row
	 * @param col 
	 * this tells the program if it is a valid coordinate. if the
	 * input is not a button, then it will return false and send and
	 * error code.
	 * @return true, if successful
	 */
	private boolean validCoordinates(final int row, final int col) {
		return !(row < 0 || col < 0 
				|| row >= board.length || col >= board.length);
	}

	/**
	 * Neighbors.
	 *
	 * @param row the row
	 * @param col            
	 * this method calculates the value of each cell. it will display
	 * the number of mines that are next to it.
	 */
	public final void neighbors(final int row, final int col) {
		neighborMineCount = 0;
		for (int n = row - 1; n <= row + 1; n++) {
			for (int c = col - 1; c <= col + 1; c++) {
				if (n == row && c == col) {
					continue;
				} else {
					if (validCoordinates(n, c) && board[n][c].getIsMine()) {
						neighborMineCount = neighborMineCount + 1;
					}
				}
			}
		}
		board[row][col].setMineCount(neighborMineCount);
	}

	/**
	 * this is called from the Panel class and will reset the game so that the
	 * user can keep playing.
	 */
	public final void reset() {
		JFrame frame = new JFrame("MineSweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel();
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Gets the game status.
	 *
	 * @param row the row
	 * @param col            
	 * this is calling the enum class GameStatus. it will return the
	 * state of which the user is in.
	 * @return the game status
	 */
	public final GameStatus getGameStatus(final int row, final int col) {
		if (status != GameStatus.Lose) {
			int flaggedMines = 0;
			for (int n = 0; n < userBoardSize; n++) {
				for (int c = 0; c < userBoardSize; c++) {
					if (board[n][c].getIsMine() && board[n][c].getIsFlagged()) {
						flaggedMines = flaggedMines + 1;
					} 
				}

			}
			if (flaggedMines == 0 || flaggedMines < totalMineCount) {
				status = GameStatus.NotOverYet;
			}
			if (flaggedMines == totalMineCount) {
				status = GameStatus.Win;
			}
			if (flaggedMines > totalMineCount) {
				status = GameStatus.Lose;
			}
		}
		return status;
	}

	/**
	 * Gets the cell.
	 *
	 * @param row the row
	 * @param col            This returns the cell that has been selected
	 * @return the cell
	 */
	public final Cell getCell(final int row, final int col) {
		return board[row][col];
	}

	/**
	 * this calculates the total number of flagged cells on the board.
	 *
	 * @return the int
	 */
	public final int totalFlaggedCount() {
		int totalFlaggedCount = 0;
		for (int row = 0; row < userBoardSize; row++) {
			for (int col = 0; col < userBoardSize; col++) {
				if (board[row][col].getIsFlagged()) {
					totalFlaggedCount = totalFlaggedCount + 1;
				}
			}
		}
		return totalFlaggedCount;
	}
}
