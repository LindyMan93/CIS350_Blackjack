// TODO: Auto-generated Javadoc
/**
 * The Class Cell.
 *
 * @author Nathan Lindenbaum
 */
public class Cell {
	
	/** The mine count. */
	private int mineCount;
	
	/** The is flagged. */
	private boolean isFlagged;
	
	/** The is exposed. */
	private boolean isExposed;
	
	/** The is mine. */
	private boolean isMine;

	/**
	 * Instantiates a new cell.
	 */
	public Cell() {
		// TODO Auto-generated constructor stub
		mineCount = 0;
		isFlagged = false;
		isExposed = false;
		isMine = false;
	}

	/**
	 * Instantiates a new cell.
	 *
	 * @param x the x
	 * @param flagged the flagged
	 * @param exposed the exposed
	 * @param mine the mine
	 */
	public Cell(final int x, 
				final boolean flagged, 
				final boolean exposed, 
				final boolean mine) {
		
		mineCount = x;
		isFlagged = flagged;
		isExposed = exposed;
		isMine = mine;

	}

	/**
	 * Sets the mine count.
	 *
	 * @param numOfMines the new mine count
	 */
	public final void setMineCount(final int numOfMines) {
		mineCount = numOfMines;
	}

	/**
	 * Gets the mine count.
	 *
	 * @return the mine count
	 */
	public final int getMineCount() {
		return mineCount;
	}

	/**
	 * Sets the checks if is flagged.
	 *
	 * @param flagged the new checks if is flagged
	 */
	public final void setIsFlagged(final boolean flagged) {
		isFlagged = flagged;
		System.out.println("Flagged");
	}

	/**
	 * Gets the checks if is flagged.
	 *
	 * @return the checks if is flagged
	 */
	public final boolean getIsFlagged() {
		return isFlagged;
	}

	/**
	 * Sets the checks if is exposed.
	 *
	 * @param exposed the new checks if is exposed
	 */
	public final void setIsExposed(final boolean exposed) {
		isExposed = exposed;
	}

	/**
	 * Gets the checks if is exposed.
	 *
	 * @return the checks if is exposed
	 */
	public final boolean getIsExposed() {
		return isExposed;
	}

	/**
	 * Sets the checks if is mine.
	 *
	 * @param mine the new checks if is mine
	 */
	public final void setIsMine(final boolean mine) {
		isMine = mine;
	}

	/**
	 * Gets the checks if is mine.
	 *
	 * @return the checks if is mine
	 */
	public final boolean getIsMine() {
		return isMine;
	}
}
