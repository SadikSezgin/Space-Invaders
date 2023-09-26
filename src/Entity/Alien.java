package Entity;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import Entity.Alien.AlienBullet;
import Main.GamePanel;


public class Alien{
	private Image alien_image;
	Image alien_image1 = new ImageIcon(getClass().getResource("alien1_1.png")).getImage();
	Image alien_image2 = new ImageIcon(getClass().getResource("alien2_1.png")).getImage();

	private int health;
	private int alien_health=3;
	public int alien_x_velocity;
	public int alien_y_velocity;
	int alien_x = 1;
	int alien_y = 1;
	private Boolean shooting = true;
	private final Random random = new Random();
	private final int[] NUM_ALIENS_PER_LEVEL = {10, 15, 20};
	private static final int[] ALIEN_XSPEED_PER_LEVEL = {5, 8, 11};
	private static final int[] ALIEN_NEG_XSPEED_PER_LEVEL = {-5, -8, -11};
	private static final int[] ALIEN_YSPEED_PER_LEVEL = {2, 5, 8};
	
	
	public Alien(Image alien, int level) {

		this.alien_image = alien;
		this.health = 3;
		if(random.nextBoolean()) {
			this.alien_x_velocity = ALIEN_XSPEED_PER_LEVEL[level -1];
		}else {
			this.alien_x_velocity = ALIEN_NEG_XSPEED_PER_LEVEL[level -1];
		}
		
		this.alien_y_velocity = ALIEN_YSPEED_PER_LEVEL[level -1];
	
	}
	
	public class AlienSpawner extends Thread{
		private final Random random = new Random();
		
		@Override
		public void run() {
			while(!GamePanel.game_over && !GamePanel.game_finish) {
				for(int i = 0; i < NUM_ALIENS_PER_LEVEL.length; i++) {
					while(NUM_ALIENS_PER_LEVEL[i]>=0) {
						int randomx = random.nextInt(GamePanel.PANEL_WIDTH - 85);
						Image alien_image;
						
						if(random.nextBoolean()) {
							alien_image = alien_image1;
						}else {
							alien_image = alien_image2;
						}
						Alien alien = new Alien(alien_image, GamePanel.current_level);
						alien.setAlien_x(randomx);
						GamePanel.aliens.add(alien);
						//System.out.println("alien spawner");
						AlienBulletSpawner bullet_spawner = new AlienBulletSpawner(alien);
						bullet_spawner.start();
						//System.out.println("bullet spawner");
						int random_delay = random.nextInt(2000) + 1000;
						try {
							Thread.sleep(random_delay);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();	
						}
						NUM_ALIENS_PER_LEVEL[i]--;
					}
					GamePanel.current_level++;
					if(GamePanel.current_level>3) {
						GamePanel.game_finish = true;
					}
				}
			}
		}	
	}
	
	public static void update() {
		synchronized(GamePanel.aliens) {
			Iterator<Alien> iterator = GamePanel.aliens.iterator();
			while(iterator.hasNext()) {
				Alien alien = iterator.next();
				if(alien.getAlien_x() + 113 >= GamePanel.PANEL_WIDTH || alien.getAlien_x() <= 0) {
					alien.alien_x_velocity = alien.alien_x_velocity * -1;
				}
				alien.setAlien_x(alien.getAlien_x()+alien.alien_x_velocity);
				alien.setAlien_y(alien.getAlien_y()+alien.alien_y_velocity);
				
				if(alien.getAlien_y() >= GamePanel.PANEL_HEIGHT) {
					iterator.remove();
				}/*else {
					AlienBulletSpawner bullet_spawner = new AlienBulletSpawner(alien);
					bullet_spawner.start();
				}*/
			}
		}
	}
	
	public static class AlienBullet{
		public static Image alienBulletImage;
		static {
			Class <?> clazz = Alien.class;
			alienBulletImage = new ImageIcon(clazz.getResource("alien_bullet.png")).getImage();
		}
		private int x;
		private int y;
		private static final int SPEED = 9;
		
		public AlienBullet(int x, int y, Image image){
			this.x = x;
			this.y = y;
			this.alienBulletImage = image;
		}
		
		public Image getImage() {
			return alienBulletImage;
		}
		
		public void setImage(Image image) {
			this.alienBulletImage = image;
		}

		public int getSpeed() {
			return SPEED;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}
	
	public class AlienBulletSpawner extends Thread{
		private Alien alien;
		
		public AlienBulletSpawner(Alien alien) {
			this.alien = alien;
		}
		
		@Override
		public void run() {
			while(!GamePanel.game_over) {
				try {
					Thread.sleep(2000);
					if(alien.is_shooting()) {
						int bulletX = alien.getAlien_x()+25; 
			            int bulletY = alien.getAlien_y()+70;
			            AlienBullet bullet = new AlienBullet(bulletX, bulletY, AlienBullet.alienBulletImage);
			            GamePanel.alienBullets.add(bullet);
			            alien.set_shooting(false);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void updateBullet() {
		synchronized(GamePanel.alienBullets) {
			Iterator<AlienBullet> iterator = GamePanel.alienBullets.iterator();
            while (iterator.hasNext()) {
                AlienBullet bullet = iterator.next();
                bullet.setY(bullet.getY() + bullet.getSpeed());
                if (bullet.getY() > GamePanel.PANEL_HEIGHT) {
                    iterator.remove();
                }
            }
		}
	}
	
	public Image getImage() {
		return alien_image;
	}
	
	public void damage() {
		health--;
	}
	
	public boolean dead() {
		return health <= 0;
	}
	
	public int getAlien_x() {
		return alien_x;
	}
	
	public void setAlien_x(int alien_x) {
		this.alien_x = alien_x;
	}
	
	public int getAlien_y() {
		return alien_y;
	}
	
	public void setAlien_y(int alien_y) {
		this.alien_y = alien_y;
	}
	
	public Boolean is_shooting() {
		return shooting;
	}
	
	public void set_shooting(Boolean shooting) {
		this.shooting = shooting;
	}
	
}
