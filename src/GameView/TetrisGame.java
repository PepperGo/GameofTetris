package GameView;

import java.awt.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


//Tomorrow(DayThree): Slider Accident, Click ok then save the setting
//Show the information in the TetrisView
//Mostly:ConfigWindow.java  --> TetrisView

public class TetrisGame extends JFrame{


	public static void main(String[] args) {

			TetrisGame tetrisGame = new TetrisGame();
			
	}
	
	private Toolkit kit = Toolkit.getDefaultToolkit();
	private Dimension screenSize = kit.getScreenSize();
	protected int screenHeight = screenSize.height;
	protected int screenWidth = screenSize.width;

	protected int windowwidth = screenWidth / 2;
	protected int windowheight = screenHeight / 3 * 2 +  screenHeight / 3 * 2 / 15;
	protected int XCWindow = screenWidth / 5;
	protected int YCWindow = screenHeight / 8;
	
	//ConfigWindow configView = new ConfigWindow();
	ConfigWindow configWin;
	ComposeWindow composeWin;


	TetrisGame() {

		super("Game of Tetris from MING SUN");
		setSize(windowwidth, windowheight);
		setLocation(XCWindow, YCWindow);
	
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);

		setLayout(null);
		
		
		
		//TetrisView
		final TetrisView tetrisview = new TetrisView();
		tetrisview.setBounds(0, screenHeight / 3 * 2 / 15, windowwidth, screenHeight / 3 * 2);
		
		
		
		//---------------------------Menu----------------------------------
		JMenuBar bar = new JMenuBar();
		bar.setBounds(5, 10, windowwidth, screenHeight / 3 * 2 / 15);
		
		JMenu setting = new JMenu("         Setting           ");
		setting.setBounds(5, 10, windowwidth/8, screenHeight / 3 * 2 / 15 - 1);
		
		 //New Game
		JMenuItem newGame = new JMenuItem("NewGame");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("New Game............");
				tetrisview.start(8,10, 20, 5, 35);
			}
			
		});
		
		
		//Configuration
		JMenuItem config = new JMenuItem("Configuration");
		config.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("Changing Config...........");
			    configWin = new ConfigWindow();
				configWin.openConfigWindow(tetrisview);
				//ConfigWindow.openConfigWindow();
			}
			
		});
		
		//Compose Shape

		JMenuItem composeShape = new JMenuItem("Compose Shape");
		composeShape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("Composing Shape...........");
				ComposeWindow composeWin = new ComposeWindow();
				composeWin.openComposeWindow(tetrisview);
			}
			
		});
		

		//Exit
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("Closing............");
			}
			
		});
			

		
		
		
		add(tetrisview);
		//add("Center", tetrisview);
		setting.add(newGame);
		setting.add(config);
		setting.add(composeShape);
		setting.add(exit);
		bar.add(setting);
		add(bar);
		
		
		//int speed, int boardwidth, int boardheight, int scorefactor, int difficulty
		
		tetrisview.start(8,10, 20, 5, 35);
		
		
	
		//setBackground(Color.WHITE);

	
		
		show();
		
	}	
	
	


}
