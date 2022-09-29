 
import java.awt.event.*;
import java.util.Random;
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
	
	static final int SCREEN_HEIGHT = 600;
	static final int SCREEN_WIDTH = 600;
	static final int UNIT_SIZE = 40;
	static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
	static int DELAY = 100;
	int x[] = new int[GAME_UNITS];
	int y[] = new int[GAME_UNITS];
	char direction='R';
	int appleEaten=0;
	int bodyParts = 6;
	boolean running;
	int appleX;int appleY;
	Timer timer;
	Random random;
	
	GamePanel() {
		
		random = new Random();
		this.setPreferredSize(new Dimension(600, 600));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		startGame();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	
	}
	public void draw(Graphics g) {
		
		
		if(running) {
			
			for(int i=0; i<SCREEN_WIDTH; i++) {
			g.drawLine(i*UNIT_SIZE,  0, i*UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(0,  i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
		}
		
		for(int i=0; i<bodyParts; i++) {
			if(i==0) {
				g.setColor(Color.red);
		        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			else {
				g.setColor(Color.gray);
		        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
		}
		
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
		g.setFont(new Font("Ink Free", Font.ITALIC, 30));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("TFA7A: "+appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("TFA7A: "+appleEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		
	
		
	}
	public void startGame() {
		newApple();
		running =true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	public void newApple() {
		
		appleX= random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY= random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void move() {
		
		
		if(running) {
			for(int i=bodyParts; i>0; i--) {
				x[i] = x[i-1];
				y[i]= y[i-1];
			}
			switch(direction) {
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			}
		}
	}
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			newApple();
			appleEaten++;
			bodyParts++;
		}
	
	}
	public void checkCollisions() {
		if(x[0] < 0) {
			running = false;
		}
		if(y[0] < 0) {
			running = false;
		}
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
	}
	public void gameOver(Graphics g) {
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 35));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Rak Fl Khsran aHmadi(Game Over)", (SCREEN_WIDTH - metrics.stringWidth("Rak Fl Khsran aHmadi(Game Over)"))/2, SCREEN_HEIGHT/2);
		
		g.setFont(new Font("Ink Free", Font.ITALIC, 30));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("TFA7A: "+appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("TFA7A: "+appleEaten))/2, g.getFont().getSize());
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
   
	public class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if(direction != 'D')
				direction = 'U';
				
			}
			switch(e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				if(direction != 'U')
				direction = 'D';
				
			}
			switch(e.getKeyCode()) {
			case KeyEvent.VK_RIGHT:
				if(direction != 'L')
				direction = 'R';
				
			}
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R')
				direction = 'L';
				
			}
			
			
		}
	}
}