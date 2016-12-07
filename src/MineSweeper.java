import javax.swing.JFrame;

// TODO: Auto-generated Javadoc
/**
 * The Class MineSweeper.
 *
 * @author Nathan Lindenbaum
 */
public class MineSweeper {

	/**
	 * Constructor that creates new Minesweeper frame and game.
	 */
	public MineSweeper() {
		// Starting MineSweeper GUI
		JFrame frame = new JFrame("MineSweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel();
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);

	}

}