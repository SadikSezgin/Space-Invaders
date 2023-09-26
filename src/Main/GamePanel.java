package Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Entity.Alien;
import Entity.Alien.AlienBullet;
import Entity.Alien.AlienSpawner;
import Entity.Bullet;
import Entity.Spaceship;

public class GamePanel extends JPanel implements ActionListener{
	
	public static Boolean game_over = false;
	public static Boolean game_finish = false;
	Timer timer;
	
	public final static int PANEL_WIDTH = 1024;
	public final static int PANEL_HEIGHT = 768;
	Image space;
	
	Image spaceshipImg = new ImageIcon(getClass().getResource("spaceship.png")).getImage();
	Spaceship spaceship = new Spaceship(spaceshipImg, 460, 600, 10, 3);
	Image alienImage1 = new ImageIcon(getClass().getResource("alien1_1.png")).getImage();
	Image alienImage2 = new ImageIcon(getClass().getResource("alien2_1.png")).getImage();
	
	public static List<Alien> aliens = new ArrayList<Alien>();
	public static List<Bullet> bullets = new ArrayList<Bullet>();
	public static List<AlienBullet> alienBullets = new ArrayList<AlienBullet>();
	private int score;
	
	private ImageIcon heart_icon;
	private Image game_over_image;
	private int lives;
	
	public static int current_level = 1;
	
	String username;
	
	private static final String SCORE_FILE_PATH = "C:\\Users\\Sadik\\eclipse-workspace\\Term Project\\bin\\scores.txt";
	
	GamePanel(){
		
		setFocusable(true);
		requestFocusInWindow();
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		score = 0;
		lives = 3;
		
		space = new ImageIcon(getClass().getResource("space.jpg")).getImage();
		heart_icon = new ImageIcon(getClass().getResource("heart.png"));
		game_over_image = new ImageIcon(getClass().getResource("gameOver1.png")).getImage();
		
		
		
		Alien alien = new Alien(AlienBullet.alienBulletImage, PANEL_HEIGHT);
		Alien.AlienSpawner alienSpawner = alien.new AlienSpawner();
		alienSpawner.start();		
		
		
		timer = new Timer(26, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(space, 0, 0, null);
		
		synchronized(aliens) {
			for(Alien alien : aliens) {
				int alien_x = alien.getAlien_x();
				int alien_y = alien.getAlien_y();
				Image alien_image = alien.getImage();
				g2D.drawImage(alien.getImage(), alien_x, alien_y, null);	
			}
		}
		
		synchronized(alienBullets){
			for(AlienBullet alien_bullet : alienBullets) {
				int alien_bullet_x = alien_bullet.getX();
				int alien_bullet_y = alien_bullet.getY();
				Image alien_bullet_image = alien_bullet.getImage();
				g2D.drawImage(alien_bullet_image, alien_bullet_x, alien_bullet_y, null);
				//System.out.println("bullet drawed");
			}
		}
		
		g2D.drawImage(spaceshipImg, spaceship.getSpaceshipx(), spaceship.getSpaceshipy(), null );
		
		synchronized (bullets) {
            for (Bullet bullet : bullets) {
                g2D.setColor(Color.YELLOW);
                g2D.fillRect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
            }
            
        }
		
		Font font = new Font("Arial", Font.BOLD, 24);
		g.setFont(font);
		FontMetrics fontMetrics = g.getFontMetrics(font);
		String scoreText = Integer.toString(score);
		int score_textWidth = fontMetrics.stringWidth(scoreText);
		g.setColor(Color.WHITE);
		g.drawString(scoreText, (PANEL_WIDTH - score_textWidth) / 2, fontMetrics.getHeight());
		
		String levelText = Integer.toString(current_level);
		String level_level_text = "Level: " + levelText;
		//int level_textWidth = fontMetrics.stringWidth(level_level_text);
		g.drawString(level_level_text, 10, fontMetrics.getHeight());
		
		int heart_icon_width = heart_icon.getIconWidth();
		for(int i=0 ; i<lives; i++) {
			g.drawImage(heart_icon.getImage(), PANEL_WIDTH - heart_icon_width * (i + 1) - 20, 10, null);
		}
		
		if(game_over) {
			g.drawImage(game_over_image, 0, 0, null);
		}
		
		if(game_finish) {
			Font end_game_font = new Font("Arial", Font.BOLD, 40);
			g.setFont(end_game_font);
			FontMetrics end_game_fontMetrics = g.getFontMetrics(end_game_font);
			String end_game_text = "Congratulations! You Win!";
			//String final_score = Integer.toString(score);
			String final_score_text = "Your score: " + score;
			int text_width = end_game_fontMetrics.stringWidth(end_game_text);
			int score_width = end_game_fontMetrics.stringWidth(final_score_text);
			g.setColor(Color.GREEN);
			g.drawString(end_game_text, (PANEL_WIDTH - text_width) / 2, 
					(PANEL_HEIGHT/2) - end_game_fontMetrics.getHeight() );
			g.drawString(final_score_text, (PANEL_WIDTH - score_width) / 2, 
					(PANEL_HEIGHT/2) + end_game_fontMetrics.getHeight());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!game_over && !game_finish) {
			
			Spaceship.update();
			
			Bullet.update();
		
			Alien.update();
			
			Alien.updateBullet();
				
			check_collisions();
				
			repaint();
		}			
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	private void check_collisions() {
		
		Rectangle spaceship_bounds = new Rectangle(spaceship.getSpaceshipx(), spaceship.getSpaceshipy(), 85, 110);
		
        synchronized (aliens) {
            Iterator<Alien> alien_iterator = aliens.iterator();
            while (alien_iterator.hasNext()) {
                Alien alien = alien_iterator.next();
                Rectangle alien_bounds = new Rectangle(alien.getAlien_x(), alien.getAlien_y(),
                        113, 85);
                
                if(spaceship_bounds.intersects(alien_bounds)) {
                	lives--;
                	if(lives <= 0) {
                		game_over = true;
                	}
                	alien_iterator.remove();
                	
                }

                synchronized (bullets) {
                    Iterator<Bullet> bullet_iterator = bullets.iterator();
                    while (bullet_iterator.hasNext()) {
                        Bullet bullet = bullet_iterator.next();
                        Rectangle bulletBounds = new Rectangle(bullet.getX(), bullet.getY(),
                                bullet.getWidth(), bullet.getHeight());
                        if (bulletBounds.intersects(alien_bounds)) {
                            alien.damage();
                            if(alien.getImage().equals(alienImage1)) {
                            	score += 10;
                            }else if(alien.getImage().equals(alienImage2)) {
                            	score += 20;
                            }
                            bullet_iterator.remove();
                            if (alien.dead()) {
                            	alien_iterator.remove();
                            }
                        }
                    }
                }
            }
        }
        
        synchronized(alienBullets) {
        	Iterator<AlienBullet> abullet_iterator = alienBullets.iterator();
        	while(abullet_iterator.hasNext()) {
        		AlienBullet abullet = abullet_iterator.next();
        		Rectangle abullet_bounds = new Rectangle(abullet.getX(), abullet.getY(),
        				38, 50);
        		
        		if(spaceship_bounds.intersects(abullet_bounds)) {
        			lives--;
        			if(lives <= 0) {
        				game_over = true;
        			}
        			abullet_iterator.remove();
        		}
        	}
        }
        
        if(game_over) {
        	writeHighScore(username, this.score);
        }else if(game_finish){
        	writeHighScore(username, this.score);
        }
    }
	
	public void writeHighScore(String username, int score) {
		String file_index = readFromFile(SCORE_FILE_PATH);
		String[] lines = file_index.split("\n");
		
		boolean is_higher_score = false;
		
		for(int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			
			if(line.isEmpty() || !line.contains(",")) {
				continue;
			}
			
			String[] parts = line.split("\\,");
			
			if(parts.length>=2) {
				String existing_username = parts[0];	
				int existing_score = Integer.parseInt(parts[1]);
				
				if(existing_username.equals(username) && existing_score < score) {
					parts[1] = Integer.toString(score);
					lines[i] = String.join(",", parts);
					is_higher_score = true;
					break;
				}
			}
		}
		
		if(!is_higher_score) {
			boolean is_existing_user = false;
			
			for(int i = 0; i < lines.length; i++) {
				String line = lines[i].trim();
				
				if(line.isEmpty() || !line.contains(",")) {
					continue;
				}
				
				String[] parts = lines[i].split(",");
				
				if(parts.length>=2) {
					String existing_username = parts[0];

					if(existing_username.equals(username)) {
						is_existing_user = true;
						break;
					}
				}
			}
			
			if(!is_existing_user) {
				String newline  = username + "," + score;
				lines = Arrays.copyOf(lines, lines.length+1);
				lines[lines.length-1] = newline;
			}
		}
		if(file_index.isEmpty()) {
			String updated_content = String.join("", lines);
			writeToFile(SCORE_FILE_PATH, updated_content);
		}else {
			String updated_content = String.join("\n", lines);
			writeToFile(SCORE_FILE_PATH, updated_content);
		}
	}
	
	private String readFromFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
        }

        return content.toString();
    }
	
	public static void writeToFile(String filePath, String content) {
	    try {
	        File file = new File(filePath);

	        // Create a FileWriter object
	        FileWriter fileWriter = new FileWriter(file);

	        // Create a BufferedWriter object to write to the file
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

	        // Write content to the file
	        bufferedWriter.write(content);

	        // Close the resources
	        bufferedWriter.close();
	        fileWriter.close();
	    } catch (IOException e) {
	        System.out.println("An error occurred while writing to the file: " + e.getMessage());
	    }
	}
}















