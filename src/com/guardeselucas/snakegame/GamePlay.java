package com.guardeselucas.snakegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
	
	private ImageIcon titleImage;
	private ImageIcon headRightImage;
	private ImageIcon headLeftImage;
	private ImageIcon headUpImage;
	private ImageIcon headDownImage;
	private ImageIcon tail;
	private ImageIcon fruit;
	
	private int lengthOfSnake = 3;
	
	private Timer timer;
	private int delay = 100;
	private int score = 0;
	private int moves = 0;
	
	private int [] fruitXpositions = {25,50,75,100,125,150,175,200,225,250,275,300,325,250,375,400,425,450,475,500,525,550,575,600,
			625,650,675,700,725,750,775,800,825,850};
	
	private int [] fruitYpositions = {75,100,125,150,175,200,225,250,275,300,325,250,375,400,425,450,475,500,525,550,575,600,
			625};
	
	private Random random = new Random();
	private int fruitXpos = random.nextInt(34);;
	private int fruitYpos = random.nextInt(23);;
	
	private int[] snakeXlength = new int[750];
	private int[] snakeYlength = new int[750];
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	
	private boolean gameOver = false;
	
	public GamePlay() {
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		
		if (moves == 0) {
			snakeXlength[2] = 50;
			snakeXlength[1] = 75;
			snakeXlength[0] = 100;

			snakeYlength[2] = 100;
			snakeYlength[1] = 100;
			snakeYlength[0] = 100;

		}
		
		//Display title
		titleImage = new ImageIcon("Header.png");
		titleImage.paintIcon(this, g, 25, 5);
		
		//Display the gameplay border
		g.setColor(Color.DARK_GRAY);
		g.drawRect(24, 74, 851, 577);
		
		//Display gameplay background
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 850, 575);
		
		//Draw score and length
		g.setColor(Color.white);
		g.setFont(new Font("areal", Font.PLAIN, 14));
		g.drawString("Score: " + score, 780, 30);
		
		g.setColor(Color.white);
		g.setFont(new Font("areal", Font.PLAIN, 14));
		g.drawString("Length: " + lengthOfSnake, 780, 50);
		
		//Display snake initial face
		headRightImage = new ImageIcon("snake_faceRight.png");
		headRightImage.paintIcon(this, g ,snakeXlength[0] ,snakeYlength[0]);
		
		for(int i=0; i<lengthOfSnake; i++) {
			if(i==0 && right) {
				headRightImage = new ImageIcon("snake_faceRight.png");
				headRightImage.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
			}else
				if(i==0 && left) {
					headLeftImage = new ImageIcon("snake_faceLeft.png");
					headLeftImage.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
				}else
					if(i==0 && down) {
						headDownImage = new ImageIcon("snake_faceDown.png");
						headDownImage.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
					}else
						if(i==0 && up) {
							headUpImage = new ImageIcon("snake_faceUp.png");
							headUpImage.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
						}
			if(i!=0) {
				tail = new ImageIcon("snake_tail.png");
				tail.paintIcon(this, g, snakeXlength[i], snakeYlength[i]);
			}
		}
		
		//display fruit image
		fruit = new ImageIcon("fruit.png");
		
		//when the snake eat the fruit
		if(fruitXpositions[fruitXpos] == snakeXlength[0] && fruitYpositions[fruitYpos] == snakeYlength[0]) {
			score+=5;
			lengthOfSnake++;
			fruitXpos = random.nextInt(34);
			fruitYpos = random.nextInt(23);
		}
		
		fruit.paintIcon(this, g, fruitXpositions[fruitXpos], fruitYpositions[fruitYpos]);
		
		for(int i=1; i<lengthOfSnake; i++) {
			//snake eats its tail
			if(snakeXlength[i] == snakeXlength[0] && snakeYlength[i] == snakeYlength[0]) {
				right = false;
				left = false;
				up = false;
				down = false;
				
				gameOver = true;
				
				//Game over text
				g.setColor(Color.RED);
				g.setFont(new Font("areal", Font.BOLD, 40));
				g.drawString("Game Over! Score: "+score, 250, 300);
				 
				g.setColor(Color.white);
				g.setFont(new Font("areal", Font.BOLD, 20));
				g.drawString("Press Enter to Restart", 350, 340);
			}
		}
				
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		timer.restart();
		
		if(right) {
			for(int n = lengthOfSnake-1; n>=0; n--) 
				snakeYlength[n+1] = snakeYlength[n];
			
			for(int n = lengthOfSnake-1; n>=0; n--) {
				if(n==0)
					snakeXlength[n] = snakeXlength[n]+25;
				else
					snakeXlength[n] = snakeXlength[n-1];
				
				if(snakeXlength[n] > 850)
					snakeXlength[n] = 25;
			}
			repaint();
		}
			
		if(left) {
			for(int n = lengthOfSnake-1; n>=0; n--) 
				snakeYlength[n+1] = snakeYlength[n];
			
			for(int n = lengthOfSnake-1; n>=0; n--) {
				if(n==0)
					snakeXlength[n] = snakeXlength[n]-25;
				else
					snakeXlength[n] = snakeXlength[n-1];
				if(snakeXlength[n] < 25)
					snakeXlength[n] = 850;
			}
			repaint();
		}
		
		if(up) {
			for(int n = lengthOfSnake-1; n>=0; n--) 
				snakeXlength[n+1] = snakeXlength[n];
			
			for(int n = lengthOfSnake-1; n>=0; n--) {
				if(n==0)
					snakeYlength[n] = snakeYlength[n]-25;
				else
					snakeYlength[n] = snakeYlength[n-1];
				if(snakeYlength[n] < 75)
					snakeYlength[n] = 625;
			}
			repaint();
		}
		
		if(down) {
			for(int n = lengthOfSnake-1; n>=0; n--) 
				snakeXlength[n+1] = snakeXlength[n];
			
			for(int n = lengthOfSnake-1; n>=0; n--) {
				if(n==0)
					snakeYlength[n] = snakeYlength[n]+25;
				else
					snakeYlength[n] = snakeYlength[n-1];
				if(snakeYlength[n] > 625)
					snakeYlength[n] = 75;
			}
			repaint();
		}
		
		
	}

	@Override
	public void keyPressed(KeyEvent k) {
		
		if(k.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			if(!left)
				right = true;
			else { 
				right = false;
				moves++;
			}
			up = false;
			down = false;
		}
		
		if(k.getKeyCode() == KeyEvent.VK_LEFT) {
			if(!right)
				left = true;
			else {
				left = false;
			moves++;
		}

			up = false;
			down = false;
		}
		
		if(k.getKeyCode() == KeyEvent.VK_UP) {
			moves++;
			if(!down)
				up = true;
			else {
				up = false;
				moves++;
			}

			left = false;
			right = false;
		}
		
		if(k.getKeyCode() == KeyEvent.VK_DOWN) {
			moves++;
			if(!up)
				down = true;
			else {
				down = false;
				moves++;
			}

			left = false;
			right = false;
		}
		
		if(k.getKeyCode() == KeyEvent.VK_ENTER  && gameOver) {
			moves = score = 0;
			lengthOfSnake = 3;
			gameOver = false;
			repaint();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent keyPressed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
