import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class CentralGameGUI.
 */
public class CentralGameGUI implements ActionListener {
	
	/** The game hub frame. */
	private JFrame gameHub;
	
	/** The game menus. */
	private JPanel gameMenus = new JPanel();
	
	/** The buttons. */
	private JButton blackjackGame, minesweeperGame, displayHighScores;
	
	/** The high scores text area. */
	private JTextArea highScores;
	
	/** The frame size X. */
	private final int frameSizeX = 500;
	
	/** The frame size Y. */
	private final int frameSizeY = 300;
	
	
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		
		// TODO Auto-generated method stub
		new CentralGameGUI();
	}

	/**
	 * Instantiates a new central game GUI.
	 */
	public CentralGameGUI() {
		
		// TODO Auto-generated constructor stub
		blackjackGame = new JButton("Play Blackjack");
		minesweeperGame = new JButton("Play Minesweeper");
		displayHighScores = new JButton("Current High Scores");
		
		blackjackGame.addActionListener(this);
		minesweeperGame.addActionListener(this);
		displayHighScores.addActionListener(this);
		
		highScores = new JTextArea();
		
		gameHub = new JFrame();
		
		setupFrame();
		
	}
	
	/**
	 * Setup frame.
	 */
	private void setupFrame() {
		
		gameMenus.setLayout(new BorderLayout());
		
		gameMenus.setBackground(Color.BLUE);
		
		gameMenus.add(blackjackGame, BorderLayout.EAST);
		gameMenus.add(minesweeperGame, BorderLayout.WEST);
		gameMenus.add(displayHighScores, BorderLayout.SOUTH);
		
		highScores.setText("Current High Scores:");
		highScores.setEditable(false);
		gameMenus.add(highScores, BorderLayout.CENTER);
		
		gameHub.add(gameMenus);
		gameHub.setTitle("Game Hub");
		gameHub.setVisible(true);
		gameHub.setSize(frameSizeX, frameSizeY);
		gameHub.setResizable(false);

		gameHub.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BufferedReader buff = null;
		  try {
		       buff = new BufferedReader(new FileReader("HighScores.txt"));
		       String str;
		       while ((str = buff.readLine()) != null) {
		       highScores.append("\n" + str);
		   }
		 } catch (IOException e) {
		  } finally {
		    try { 
		    	buff.close(); 
		    	} catch (Exception ex) { 
		    		
		    	}
		    }
		
	}

	/** 
	 * @see java.awt.event.ActionListener#actionPerformed
	 * @param e
	 */
	public final void actionPerformed(final ActionEvent e) {
		JComponent buttonPressed = (JComponent) e.getSource();
		
		if (buttonPressed == blackjackGame) {
			new BlackjackGUI();
		}
		if (buttonPressed == minesweeperGame) {
			new MineSweeper();
		}
		

	}

}
