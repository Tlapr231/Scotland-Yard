import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class SYView extends JFrame {

//	public static void main(String[] args) {
//		SYView view = new SYView();
//	}
	
	//Variables
	private Node[] nodes;
	
	//Not Visible (Runs through all other panes)
	private final JPanel contentPane = new JPanel();
	private final CardLayout cl = new CardLayout();
	
	//Main Menu Pane
	private final JPanel mainPane = new JPanel();
	private final JLabel lblMainBg = new JLabel("");
	private final JButton btnPlay = new JButton("Play");
	private final JButton btnSettings = new JButton("Settings");
	private final JButton btnQuit = new JButton("Quit");
	
	//Setting Menu Pane
	private final JPanel 	settingPane = new JPanel();
	private final JLabel lblSettingBg = new JLabel("");
	private final JButton btnReturn = new JButton("Return");
	

	//File Selection Screen
	private final JPanel fileSelectionPane = new JPanel();
	private final JLabel lblFileSelectionbG = new JLabel("");
	
	//GameScreen
	private final JLayeredPane gamePane = new JLayeredPane();
	private JLabel lblGameBg = new JLabel("");
	private JLabel lblAurora1 = new JLabel("");
	private JLabel lblAurora2 = new JLabel("");
	private JLabel lblAurora3 = new JLabel("");
	private JLabel lblBlackhole = new JLabel("");
	
	private Dictionary[] file1 = new Dictionary[6];
	private Dictionary[] file2 = new Dictionary[6];
	private Dictionary[] file3 = new Dictionary[6];
	
	//File select, Panel 1
	private JPanel fileSelect1Pane = new JPanel();
	private JPanel fileSelect1InfoPane = new JPanel();
	private JPanel fileSelect1PlayPane = new JPanel();
	private JButton btnFileSelect1 = new JButton("New Game");
	private JLabel lblFileSelect1TurnCount = new JLabel("Turn : 1");
	private JButton btnF1Delete = new JButton("Delete");

	
	//File select, Panel 2
	private JPanel fileSelect2Pane = new JPanel();
	private JPanel fileSelect2InfoPane = new JPanel();
	private JPanel fileSelect2PlayPane = new JPanel();
	private JButton btnFileSelect2 = new JButton("New Game");
	private JLabel lblFileSelect2TurnCount = new JLabel("Turn : 1");
	private JButton btnF2Delete = new JButton("Delete");

	
	
	//FileSelect, Panel 3
	private JPanel fileSelect3Pane = new JPanel();
	private JPanel fileSelect3PlayPane = new JPanel();
	private JPanel fileSelect3InfoPane = new JPanel();
	private JButton btnFileSelect3 = new JButton("New Game");
	private JLabel lblFileSelct3TurnCount = new JLabel("Turn : 1");
	private JButton btnF3Delete = new JButton("Delete");

	
	private JButton[] buttons = new JButton[200];
	
	//Data : Active | Spy? | Name | Avatar | Taxi | Bus | Train | Black | Double | Node 

	
	/**
	 * Create the application.
	 */
	public SYView(Node[] nodes) {

		this.nodes = nodes;
		
		//Data : Active | Spy? | Name | Avatar | Taxi | Bus | Train | Black | Double | Node 
		//file2[0] = {"spy" : true, "taxi" : 24, "bus" : 24, "train" : 24, "black": 5, "double" : 2, "active" : true};
		
		//file2[0] = {1, 24, 24, 24, 5, 2, 1}
		//file2[1] = {0, 10, 8, 4, 0, 0, 1};
		//file2[2] = {0, 10, 8, 4, 0, 0, 1};
		//file2[3] = {0, 10, 8, 4, 0, 0, 1};
		//file2[4] = {0, 10, 8, 4, 0, 0, 1};
		//file2[5] = {0, 10, 8, 4, 0, 0, 1};
		
		initialize();
		
		//(int file, boolean active, int playerNumber, boolean spy, String name, String avatar, int taxi, int bus, int train, int black, int doublePlay, int node) 

		setPlayerData(1, true, 1, true, "Player uno", "/Images/avatars/avatar_empty.png", 24, 24, 24, 5, 2, 23);
		setPlayerData(1, true, 2, false, "Player dos", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 67);
		setPlayerData(1, true, 3, false, "Player tres", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 169);
		setPlayerData(1, true, 4, false, "Player quatro", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 190);
		setPlayerData(1, true, 5, false, "Player cinco", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 133);
		setPlayerData(1, true, 6, false, "Player seis", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 187);

		setPlayerData(2, true, 1, false, "Player uno", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 23);
		setPlayerData(2, true, 2, false, "Player dos", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 67);
		setPlayerData(2, true, 3, false, "Player tres", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 169);
		setPlayerData(2, true, 4, true, "Player quatro", "/Images/avatars/avatar_empty.png", 24, 24, 24, 5, 2, 190);
		setPlayerData(2, true, 5, false, "Player cinco", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 133);
		setPlayerData(2, true, 6, false, "Player seis", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 187);

		setPlayerData(3, true, 1, true, "Player uno", "/Images/avatars/avatar_empty.png", 24, 24, 24, 5, 2, 23);
		setPlayerData(3, true, 2, false, "Player dos", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 67);
		setPlayerData(3, true, 3, false, "Player tres", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 169);
		setPlayerData(3, true, 4, false, "Player quatro", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 190);
		setPlayerData(3, true, 5, false, "Player cinco", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 133);
		setPlayerData(3, false, 6, false, "Player seis", "/Images/avatars/avatar_empty.png", 10, 8, 4, 0, 0, 187);

		
		//Main Menu
		
		mainPane.setLayout(null);

		btnPlay.setFocusable(false);
		btnPlay.setBounds(700, 400, 400, 40);
		mainPane.add(btnPlay);
				
		btnSettings.setFocusable(false);
		btnSettings.setBounds(new Rectangle(700, 450, 400, 40));
		mainPane.add(btnSettings);
		
		btnQuit.setFocusable(false);
		btnQuit.setBounds(new Rectangle(700, 500, 400, 40));
		mainPane.add(btnQuit);	
		
		lblMainBg.setIcon(new ImageIcon(SYView.class.getResource("/Images/main_menu_bg.jpg")));
		lblMainBg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMainBg.setBounds(0, 0, 1800, 1000);
		mainPane.add(lblMainBg);
				
		
		//Settings
		
		settingPane.setLayout(null);
		
		btnReturn.setBounds(700, 450, 400, 40);
		settingPane.add(btnReturn);
		
		lblSettingBg.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettingBg.setIcon(new ImageIcon(SYView.class.getResource("/Images/main_menu_bg.jpg")));
		lblSettingBg.setBounds(0, 0, 1800, 1000);
		settingPane.add(lblSettingBg);
				
		
		//File Select
		
		fileSelectionPane.setLayout(null);

		infoPaneInit(fileSelect1InfoPane, file1);
		infoPaneInit(fileSelect2InfoPane, file2);
		infoPaneInit(fileSelect3InfoPane, file3);
		

		//File 1
		fileSelect1Pane.setBounds(99, 150, 466, 700);
		fileSelect1Pane.setLayout(new GridLayout(0, 1, 0, 0));
		fileSelectionPane.add(fileSelect1Pane);
		
		fileSelect1PlayPane.setLayout(null);

		btnFileSelect1.setFocusable(false);
		btnFileSelect1.setBounds(0, 0, 466, 310);
		btnFileSelect1.setFont(new Font("Malgun Gothic", Font.BOLD, 36));
		btnFileSelect1.setForeground(new Color(0, 0, 0));
		fileSelect1PlayPane.add(btnFileSelect1);
		
		lblFileSelect1TurnCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblFileSelect1TurnCount.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
		lblFileSelect1TurnCount.setBounds(0, 310, 466, 40);
		fileSelect1PlayPane.add(lblFileSelect1TurnCount);
		
		btnF1Delete.setFocusable(false);
		btnF1Delete.setBounds(410, 855, 155, 40);
		fileSelectionPane.add(btnF1Delete);
		
		fileSelect1InfoPane.setLayout(new GridLayout(2, 6, 0, 0));
		
		fileSelect1Pane.add(fileSelect1PlayPane);
		fileSelect1Pane.add(fileSelect1InfoPane);

		
		//File 2
		fileSelect2Pane.setBounds(665, 150, 466, 700);
		fileSelect2Pane.setLayout(new GridLayout(0, 1, 0, 0));
		fileSelectionPane.add(fileSelect2Pane);
		
		fileSelect2PlayPane.setLayout(null);

		btnFileSelect2.setForeground(Color.BLACK);
		btnFileSelect2.setFont(new Font("Malgun Gothic", Font.BOLD, 36));
		btnFileSelect2.setFocusable(false);
		btnFileSelect2.setBounds(0, 0, 466, 310);
		fileSelect2PlayPane.add(btnFileSelect2);

		lblFileSelect2TurnCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblFileSelect2TurnCount.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
		lblFileSelect2TurnCount.setBounds(0, 310, 466, 40);
		fileSelect2PlayPane.add(lblFileSelect2TurnCount);
		
		btnF2Delete.setFocusable(false);
		btnF2Delete.setBounds(976, 855, 155, 40);
		fileSelectionPane.add(btnF2Delete);
		
		fileSelect2InfoPane.setLayout(new GridLayout(2, 3, 0, 0));
		
		fileSelect2Pane.add(fileSelect2PlayPane);
		fileSelect2Pane.add(fileSelect2InfoPane);
		
		
		//File 3
		fileSelect3Pane.setBounds(1235, 150, 466, 700);
		fileSelect3Pane.setLayout(new GridLayout(0, 1, 0, 0));
		fileSelectionPane.add(fileSelect3Pane);
		
		fileSelect3PlayPane.setLayout(null);
		
		btnFileSelect3.setForeground(Color.BLACK);
		btnFileSelect3.setFont(new Font("Malgun Gothic", Font.BOLD, 36));
		btnFileSelect3.setFocusable(false);
		btnFileSelect3.setBounds(0, 0, 466, 310);
		fileSelect3PlayPane.add(btnFileSelect3);
		
		lblFileSelct3TurnCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblFileSelct3TurnCount.setFont(new Font("Malgun Gothic", Font.BOLD, 22));
		lblFileSelct3TurnCount.setBounds(0, 310, 466, 40);
		fileSelect3PlayPane.add(lblFileSelct3TurnCount);

		btnF3Delete.setFocusable(false);
		btnF3Delete.setBounds(1546, 855, 155, 40);
		fileSelectionPane.add(btnF3Delete);

		fileSelect3InfoPane.setLayout(new GridLayout(2, 6, 0, 0));
		
		fileSelect3Pane.add(fileSelect3PlayPane);
		fileSelect3Pane.add(fileSelect3InfoPane);
		
		
		//background
		lblFileSelectionbG.setIcon(new ImageIcon(SYView.class.getResource("/Images/main_menu_bg.jpg")));
		lblFileSelectionbG.setHorizontalAlignment(SwingConstants.CENTER);
		lblFileSelectionbG.setBounds(0, 0, 1800, 1000);
		fileSelectionPane.add(lblFileSelectionbG);
		
		
		//Game Screen
		
		gamePane.setLayer(lblGameBg, -2);
		
		lblGameBg.setVerticalAlignment(SwingConstants.TOP);
		lblGameBg.setIcon(new ImageIcon(SYView.class.getResource("/Images/space.jpg")));
		lblGameBg.setBounds(0, 0, 1795, 965);
		gamePane.add(lblGameBg);
		gamePane.setLayer(lblBlackhole, -1);
		
		lblBlackhole.setIcon(new ImageIcon(SYView.class.getResource("/Images/blackhole.png")));
		lblBlackhole.setBounds(129, 285, 400, 400);
		gamePane.add(lblBlackhole);
		gamePane.setLayer(lblAurora1, -1);
		
		lblAurora1.setIcon(new ImageIcon(SYView.class.getResource("/Images/aurora2.png")));
		lblAurora1.setBounds(1075, 319, 640, 348);
		gamePane.add(lblAurora1);
		gamePane.setLayer(lblAurora2, -1);
		
		lblAurora2.setIcon(new ImageIcon(SYView.class.getResource("/Images/aurora.png")));
		lblAurora2.setBounds(868, 357, 348, 640);
		gamePane.add(lblAurora2);
		gamePane.setLayer(lblAurora3, -1);
		
		lblAurora3.setIcon(new ImageIcon(SYView.class.getResource("/Images/aurora2.png")));
		lblAurora3.setBounds(469, 725, 700, 385);
		gamePane.add(lblAurora3);
				
		
		//Adds all the panes
		contentPane.add(mainPane, "Main");
		contentPane.add(settingPane, "Setting");
		contentPane.add(fileSelectionPane, "Selection");
		contentPane.add(gamePane, "Game");
		

		//Initialize all the buttons for the main game
		for (int i = 1; i < 200; i++) {
			nodeInit(i);
		}
		
		//Action Events
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Setting");
			}
		});		

		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "Main");
			}
		});

		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(contentPane, "Selection");
			}
		});
		
		btnFileSelect1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(contentPane, "Game");
			}
		});

		btnFileSelect2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(contentPane, "Game");
			}
		});
	
		btnFileSelect3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(contentPane, "Game");
			}
		});
		
		//=====================================================
		//accual game
		//======================================================
		
		playerOnNode(1);
	
	}

	
	/**
	 * Initialize the contents of the main frame.
	 */
	private void initialize() {
		setBounds(25, 25, 1800, 1000); //300, -1200 for the second screen
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SYView.class.getResource("/Images/Window_Icon.png")));
		setTitle("Mr. X");
		
		contentPane.setLayout(cl);
		getContentPane().add(contentPane);

		setResizable(false);
		setVisible(true);	
		
	}
	
	
	/**
	 * Initialyze all the buttons that serve has node
	 */
	private void nodeInit(int index) {
		String imgSource = "";
		JButton btn = new JButton();
		
		btn.setFocusable(false);
		btn.setEnabled(false);
		//btn.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);

		btn.setBounds(nodes[index].getX(), nodes[index].getY(), 48, 48);
		
		System.out.println(index);
		
		imgSource = "/Images/planets/planet" + Integer.toString(index) +".png";
		btn.setIcon(new ImageIcon(SYView.class.getResource(imgSource)));
				
		buttons[index] = btn;
		gamePane.add(btn);
	}
		
	
	
	//uses these panels : fileSelect2InfoPane
	private void infoPaneInit(JPanel panel, Dictionary[] file) {
		
		for (int playerIndex = 0; playerIndex < 6; playerIndex++) {
			
			if((boolean) file[playerIndex].get("active") == false) {		//if the player isn't active
				panel.add(generateEmptyInfo());
				System.out.println("generating Empty profile " + Integer.toString(playerIndex));
			} else if ((boolean) file[playerIndex].get("spy")){	//if the player is the spy
				panel.add(generateSpyInfo(file[playerIndex]));
				System.out.println("generating Spy profile " + Integer.toString(playerIndex));
			} else {												//all normal players
				panel.add(generatePlayerInfo(file[playerIndex]));				
				System.out.println("generating Player profile " + Integer.toString(playerIndex));
			}
				
		}
	
	}
	
	/**
	 * temparary methode used to initialise player data while i can get a file reader going.
	 * 
	 * @param file
	 * @param active
	 * @param playerNumber
	 * @param spy
	 * @param name
	 * @param avatar
	 * @param taxi
	 * @param bus
	 * @param train
	 * @param black
	 * @param doublePlay
	 * @param node
	 *	Data : Active | Player | Spy | Name | Avatar | Taxi | Bus | Train | Black | Double | Node | File
	 */
	
	private void setPlayerData(int file, boolean active, int playerNumber, boolean spy, String name, String avatar, int taxi, int bus, int train, int black, int doublePlay, int node) {
		Dictionary player = new Hashtable();

		player.put("active", active);
		player.put("player", playerNumber);
		player.put("spy", spy);
		player.put("name", name);
		player.put("avatar", avatar);
		player.put("taxi", taxi);
		player.put("bus", bus);
		player.put("train", train);
		player.put("black", black);
		player.put("double", doublePlay);
		player.put("node", node);
		player.put("file", file);
		
		switch (file) {
		case 1 :
			file1[playerNumber-1] = player;
			break;
		case 2 :
			file2[playerNumber-1] = player;
			break;
		case 3 :
			file3[playerNumber-1] = player;
			break;
		}
		
		
	}
	
	/**
	 * In the game selection screen, this methode will generate a players square based on their data given by a Dictionary
	 * @param player 
	 * 		dictionary of the players information
	 * @return PlayerPane
	 * 		a panel containing all of the players information.
	 */
	private JPanel generatePlayerInfo(Dictionary player) {

		JPanel playerPane = new JPanel();
		
		playerPane.setLayout(null);
		playerPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		
		JLabel lblImg = new JLabel("");
		
		lblImg.setIcon(new ImageIcon(SYView.class.getResource((String) player.get("avatar"))));
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg.setBounds(54, 5, 48, 48);
		playerPane.add(lblImg);
		
		
		JLabel lblName = new JLabel((String) player.get("name"));
		
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(0, 56, 155, 15);
		playerPane.add(lblName);
		
		
		JLabel lblTaxiImg = new JLabel("");
		
		lblTaxiImg.setIcon(new ImageIcon(SYView.class.getResource("/Images/Card(Needs to be worked on).png")));
		lblTaxiImg.setBounds(0, 78, 32, 32);
		playerPane.add(lblTaxiImg);
		
		
		JLabel lblTaxiAmount = new JLabel(Integer.toString((int)player.get("taxi")));
		
		lblTaxiAmount.setBounds(37, 78, 36, 32);
		playerPane.add(lblTaxiAmount);

		
		JLabel lblBusImg = new JLabel("");
		
		lblBusImg.setIcon(new ImageIcon(SYView.class.getResource("/Images/Card(Needs to be worked on).png")));
		lblBusImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblBusImg.setBounds(0, 110, 32, 32);
		playerPane.add(lblBusImg);
		
		
		JLabel lblBusAmount = new JLabel(Integer.toString((int)player.get("bus")));
		
		lblBusAmount.setBounds(37, 110, 36, 32);
		playerPane.add(lblBusAmount);

		
		JLabel lblTrainImg = new JLabel("");
		
		lblTrainImg.setIcon(new ImageIcon(SYView.class.getResource("/Images/Card(Needs to be worked on).png")));
		lblTrainImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrainImg.setBounds(0, 142, 32, 32);
		playerPane.add(lblTrainImg);
		
		
		JLabel lblTrainAmount = new JLabel(Integer.toString((int)player.get("train")));
		
		lblTrainAmount.setBounds(37, 142, 36, 32);
		playerPane.add(lblTrainAmount);
		
		
		JLabel lblNodeAmount = new JLabel(Integer.toString((int)player.get("node")));
		
		lblNodeAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblNodeAmount.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
		lblNodeAmount.setBounds(80, 84, 64, 64);
		playerPane.add(lblNodeAmount);
		
		JLabel lblNodeImg = new JLabel("");
		
		lblNodeImg.setIcon(new ImageIcon(SYView.class.getResource("/Images/Node(Need to be worked).png")));
		lblNodeImg.setBounds(80, 84, 64, 64);
		playerPane.add(lblNodeImg);
		
		return playerPane;
		
	}
	
	
	/**
	 * In the game selection screen, this methode will generate a spys square based on their data given by a Dictionary
	 * @param player 
	 * 		dictionary of the spys information
	 * @return PlayerPane
	 * 		a panel containing all of the spys information.
	 */
	private JPanel generateSpyInfo(Dictionary player) {
		
		JPanel playerPane = new JPanel();
		
		playerPane.setLayout(null);
		playerPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		
		JLabel lblImg = new JLabel("");
		
		lblImg.setIcon(new ImageIcon(SYView.class.getResource((String) player.get("avatar"))));
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg.setBounds(54, 5, 48, 48);
		playerPane.add(lblImg);
		
		
		JLabel lblName = new JLabel((String) player.get("name"));
		
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(0, 56, 155, 15);
		playerPane.add(lblName);
		
		
		JLabel lblTaxiImg = new JLabel("");
		
		lblTaxiImg.setIcon(new ImageIcon(SYView.class.getResource("/Images/Card(Needs to be worked on).png")));
		lblTaxiImg.setBounds(0, 78, 32, 32);
		playerPane.add(lblTaxiImg);
		
		
		JLabel lblTaxiAmount = new JLabel(Integer.toString((int)player.get("taxi")));
		
		lblTaxiAmount.setBounds(37, 78, 36, 32);
		playerPane.add(lblTaxiAmount);

		
		JLabel lblBusImg = new JLabel("");
		
		lblBusImg.setIcon(new ImageIcon(SYView.class.getResource("/Images/Card(Needs to be worked on).png")));
		lblBusImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblBusImg.setBounds(0, 110, 32, 32);
		playerPane.add(lblBusImg);
		
		
		JLabel lblBusAmount = new JLabel(Integer.toString((int)player.get("bus")));
		
		lblBusAmount.setBounds(37, 110, 36, 32);
		playerPane.add(lblBusAmount);

		
		JLabel lblTrainImg = new JLabel("");
		
		lblTrainImg.setIcon(new ImageIcon(SYView.class.getResource("/Images/Card(Needs to be worked on).png")));
		lblTrainImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrainImg.setBounds(0, 142, 32, 32);
		playerPane.add(lblTrainImg);
		
		
		JLabel lblTrainAmount = new JLabel(Integer.toString((int) player.get("train")));
		
		lblTrainAmount.setBounds(37, 142, 36, 32);
		playerPane.add(lblTrainAmount);
		
		
		JLabel lblBlackImg = new JLabel("");
		
		lblBlackImg.setIcon(new ImageIcon(SYView.class.getResource("/Images/Card(Needs to be worked on).png")));
		lblBlackImg.setBounds(78, 78, 32, 32);
		playerPane.add(lblBlackImg);
		
		
		JLabel lblBlackAmount = new JLabel(Integer.toString((int)player.get("black")));
		
		lblBlackAmount.setBounds(115, 78, 36, 32);
		playerPane.add(lblBlackAmount);
		
		
		JLabel lblDoubleImg = new JLabel("");
		
		lblDoubleImg.setIcon(new ImageIcon(SYView.class.getResource("/Images/Card(Needs to be worked on).png")));
		lblDoubleImg.setBounds(78, 110, 32, 32);
		playerPane.add(lblDoubleImg);
		
		
		JLabel lblDoubleAmount = new JLabel(Integer.toString((int)player.get("double")));
		lblDoubleAmount.setBounds(115, 110, 36, 32);
		playerPane.add(lblDoubleAmount);
		
		
		return playerPane;
		
		
	}
	
	
	/**
	 * In the game selection screen, this methode will generate an empty square
	 * 
	 * @return PlayerPane
	 * 		a panel containing the representation of an empty pane.
	 */
	private JPanel generateEmptyInfo() {
		
		JPanel playerPane = new JPanel();
		
		playerPane.setLayout(null);
		playerPane.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		
		JLabel lblImg = new JLabel("");
		
		lblImg.setIcon(new ImageIcon(SYView.class.getResource("/Images/avatars/avatar_empty.png")));
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg.setBounds(54, 5, 48, 48);
		playerPane.add(lblImg);
		
		
		JLabel lblName = new JLabel("Empty");
		
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(0, 56, 155, 15);
		playerPane.add(lblName);
		
		
		return playerPane;
	}
	
	
	/**
	 * to enable and disable the proper buttons depending on a players turn
	 * 
	 * @param node (int)
	 * 		the node the player is currently on.
	 */
	public void playerOnNode(int node) {
		int[] availableNodes = nodes[node].getConnectedNodes();
		
		for (int i = 1; i < 200; i ++) {
			buttons[i].setEnabled(false) ; 
		}
		
		for (int i = 0; i < availableNodes.length; i ++) {
			buttons[availableNodes[i]].setEnabled(true);
		}
		
		buttons[node].setEnabled(true);
	}
}
