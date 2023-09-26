package Entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

import javax.swing.ImageIcon;

import Main.GamePanel;

public class Spaceship implements KeyListener{
	static Image spaceshipImg;
	public static int spaceshipx;
	public static int spaceshipy;
	public static int spaceshipSpeed;
	public int health;
	private final static HashSet<Integer> keyPressedSet = new HashSet<>();
	
	public Spaceship(Image image, int x, int y, int speed, int health) {
		this.spaceshipImg = image;
		this.spaceshipx = x;
		this.spaceshipy = y;
		this.health = health;
		
	}

	public static Image getSpaceshipImg() {
		return spaceshipImg;
	}

	public static void setSpaceshipImg(Image spaceshipImg) {
		Spaceship.spaceshipImg = spaceshipImg;
	}

	public int getSpaceshipx() {
		return spaceshipx;
	}

	public void setSpaceshipx(int spaceshipx) {
		this.spaceshipx = spaceshipx;
	}

	public int getSpaceshipy() {
		return spaceshipy;
	}

	public void setSpaceshipy(int spaceshipy) {
		this.spaceshipy = spaceshipy;
	}

	public int getSpaceshipSpeed() {
		return spaceshipSpeed;
	}

	public void setSpaceshipSpeed(int spaceshipSpeed) {
		this.spaceshipSpeed = spaceshipSpeed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public static HashSet<Integer> getKeypressedset() {
		return keyPressedSet;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		keyPressedSet.add(keyCode);
		
		if(keyCode == KeyEvent.VK_SPACE) {
			int bulletX = spaceshipx + spaceshipImg.getWidth(null) / 2 - Bullet.WIDTH / 2;
            int bulletY = spaceshipy;
            Bullet bullet = new Bullet(bulletX, bulletY);
            GamePanel.bullets.add(bullet);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		keyPressedSet.remove(keyCode);
		
	}
	
	public static boolean isKeyPressed(int keyCode) {
		return keyPressedSet.contains(keyCode);
	}

	public static void update() {
		// TODO Auto-generated method stub
		if(Spaceship.isKeyPressed(KeyEvent.VK_UP)) {
			spaceshipy -= spaceshipSpeed;}
		else if(isKeyPressed(KeyEvent.VK_DOWN)) {
			spaceshipy += spaceshipSpeed;}
		else if(isKeyPressed(KeyEvent.VK_RIGHT)) {
			spaceshipx += spaceshipSpeed;}
		else if(isKeyPressed(KeyEvent.VK_LEFT)) {
			spaceshipx -= spaceshipSpeed;}
		
		if (spaceshipx < 0) {
			spaceshipx = 0;
        } else if (spaceshipx > GamePanel.PANEL_WIDTH - 85) {
        	spaceshipx = GamePanel.PANEL_WIDTH - 85;
        }

        if (spaceshipy < 0) {
        	spaceshipy = 0;
        } else if (spaceshipy > GamePanel.PANEL_HEIGHT - 110) {
        	spaceshipy = GamePanel.PANEL_HEIGHT - 110;
        }
		
	}
}
