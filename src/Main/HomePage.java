package Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import User.LogIn;
import User.Register;

public class HomePage extends JFrame implements ActionListener, KeyListener{

	JMenuBar menu_bar;
	JMenu file_menu;
	JMenuItem register;
	JMenuItem play_game;
	JMenuItem high_score;
	JMenuItem quit;
	JMenu help_menu;
	JMenuItem about;
	
	private ImageIcon home_screen;
	
	public String username;
	
	private static final String SCORE_FILE_PATH = "C:\\Users\\Sadik\\eclipse-workspace\\Term Project\\bin\\scores.txt";
	private static final String REGISTRATION_FILE_PATH = "C:\\Users\\Sadik\\eclipse-workspace\\Term Project\\bin\\registration.txt";
	
	HomePage(){		
		//Menu Bar
		menu_bar = new JMenuBar();
		
		//File Menu
		file_menu = new JMenu("File");
		register = new JMenuItem("Register");
		play_game = new JMenuItem("Play Game");
		high_score = new JMenuItem("High Score");
		quit = new JMenuItem("Quit");
		file_menu.add(register);
		file_menu.add(play_game);
		file_menu.add(high_score);
		file_menu.add(quit);
		
		//Help Menu
		help_menu = new JMenu("Help");
		about = new JMenuItem("About");
		
		//add menus to menu bar
		menu_bar.add(file_menu);
		menu_bar.add(help_menu);
		help_menu.add(about);
		
		//add action to menu items
		register.addActionListener(this);
		play_game.addActionListener(this);
		high_score.addActionListener(this);
		quit.addActionListener(this);
		about.addActionListener(this);
		
		home_screen = new ImageIcon(getClass().getResource("home.jpg"));
		JLabel home_screen_label = new JLabel(home_screen);
		home_screen_label.setBounds(0, 0, home_screen.getIconWidth(), home_screen.getIconHeight());
		setContentPane(home_screen_label);
		
		this.setBackground(Color.BLACK);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Space Invaders");
		this.setSize(1024, 768);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setJMenuBar(menu_bar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==register) {
			registrationDialog();
			
		}
		if(e.getSource()==play_game) {
			logInDialog();
		}
		if(e.getSource()==high_score) {
			showHighScores();
		}
		if(e.getSource()==quit) {
			System.exit(0);
		}
		if(e.getSource()==about) {
			JOptionPane.showMessageDialog(this, "Sadık Sezgin\n20200702016\nsadik.sezgin@std.yeditepe.edu.tr");
		}		
		
	}

	
	private void registrationDialog() {
		Register registrationPanel = new Register();
		this.setContentPane(registrationPanel);
		this.revalidate();
		this.repaint();
		
	}
	
	private void logInDialog() {
        LogIn logIn = new LogIn();
        Boolean check = LogIn.logIn();
        if(check) {
        	GamePanel gamePanel = new GamePanel();
			this.add(gamePanel);
			this.setContentPane(gamePanel);
			gamePanel.requestFocus();
			gamePanel.setVisible(true);
			gamePanel.setUsername(username);
			revalidate();
        }else JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
	}

	
	private void showHighScores() {
		HighScores highScoresPanel = new HighScores();
        this.setContentPane(highScoresPanel);
        this.revalidate();
        this.repaint();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
