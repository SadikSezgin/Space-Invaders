package Entity;

import java.util.Iterator;

import Main.GamePanel;

public class Bullet {

	public static final int WIDTH = 5;
	public static final int HEIGHT = 10;
    public static final int SPEED = 10;

    private int x;
    private int y;
    
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public static void update() {
		synchronized(GamePanel.bullets) {
			Iterator<Bullet> iterator = GamePanel.bullets.iterator();
            while (iterator.hasNext()) {
                Bullet bullet = iterator.next();
                bullet.setY(bullet.getY() - bullet.getSpeed());
                if (bullet.getY() < 0) {
                    iterator.remove();
                }
            }
		}
	}
	
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setY(int y) {
		this.y = y;
	}

	public static int getWidth() {
    	return WIDTH;
    }
    
    public int getHeight() {
    	return HEIGHT;
    }
    
    public int getSpeed() {
    	return SPEED;
    }

}
