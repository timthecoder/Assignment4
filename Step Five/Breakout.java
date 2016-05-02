/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;
	
	//instance of paddle
	private GRect paddle;
	private GOval ball;
	private double vx, vy;
	
	//centers the bricks
	private double xMark = APPLICATION_WIDTH /2- ((BRICK_WIDTH * NBRICKS_PER_ROW)+(NBRICKS_PER_ROW -1)*BRICK_SEP)/2;
	private double yMark = BRICK_Y_OFFSET;
	
	//sets the random speed for the vx 
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public void run() {
		addMouseListeners();
		createPaddle();
		createBricks(xMark,yMark);
		
		//initializes the vx and vy
		vx = rgen.nextDouble(1.0,3.0);
		if(rgen.nextBoolean(0.5)) vx = -vx;
		vy = 3.0;
		
		createBall();
		
		//moves the ball in the box
		while(true){
			ball.move(vx, vy);
			checkWalls();
			GObject collider = getCollidingObject();
			if(collider == paddle){
				vy = -vy;
			}else if(collider != paddle && collider != null){
				remove(collider);
				vy= -vy;
			}
			pause(10);
		}
		
		
		
	}
	
	
	
	//this creates the paddle location
	private void createPaddle(){
		
		paddle = new GRect(getWidth()/2 -PADDLE_WIDTH/2,getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT,
				PADDLE_WIDTH,PADDLE_HEIGHT  );
		paddle.setFilled(true);
		add(paddle);
	}
	
	
	
	//this controls the paddle location with the mouse
	public void mouseMoved(MouseEvent e){
		if(e.getX() <( getWidth() - PADDLE_WIDTH)){
			paddle.setLocation(e.getX(), getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}else{
			paddle.setLocation(getWidth() - PADDLE_WIDTH, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
		
	}
	
	private void createBall(){
		
		ball = new GOval(getWidth()/2 -BALL_RADIUS,getHeight()/2 - BALL_RADIUS, 2*BALL_RADIUS, 2*BALL_RADIUS );
		ball.setFilled(true);
		add(ball);
	}
	
	private void checkWalls(){
		
		//checks the left wall
		if(ball.getX() < 0){
			vx = -vx;
		}
		//checks the right wall
		else if(ball.getX() + 2*BALL_RADIUS > getWidth() ){
			vx = -vx;
		}
		//checks the top wall
		else if(ball.getY() < 0){
			vy = -vy;
		}
		//checks the bottom wall
		else if(ball.getY() + 2*BALL_RADIUS > getHeight()){
			//loses the game
			remove(ball);
		}
	}
	
	//checks if the ball collides into an object
	private GObject getCollidingObject(){
		
		//check top left corner
		if(getElementAt(ball.getX(), ball.getY()) != null){
			return getElementAt(ball.getX(), ball.getY());
		}
		//check top right corner
		else if(getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY()) != null){
			return getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY());
		}
		//checks bottom left corner
		else if(getElementAt(ball.getX(), ball.getY()+2*BALL_RADIUS) != null){
			return getElementAt(ball.getX(), ball.getY()+2*BALL_RADIUS);
		}
		//checks bottom right corner
		else if(getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY()+2*BALL_RADIUS) != null){
			return getElementAt(ball.getX()+2*BALL_RADIUS, ball.getY()+2*BALL_RADIUS);
		}
		return null;
	}
	
	
	
	private void createBricks(double x, double y){
		
		for(int row = 0; row < NBRICK_ROWS; row++){
			
			for( int col = 0; col < NBRICKS_PER_ROW; col++){
				
				GRect brick = new GRect( x + col *(BRICK_WIDTH + BRICK_SEP) , y + row *(BRICK_HEIGHT + BRICK_SEP),
						BRICK_WIDTH,BRICK_HEIGHT);
				brick.setFilled(true);
				
				//sets the brick color
				switch(row){
					case 0: brick.setColor(Color.RED);break;
					case 1: brick.setColor(Color.RED);break;
					case 2: brick.setColor(Color.ORANGE);break;
					case 3: brick.setColor(Color.ORANGE);break;
					case 4: brick.setColor(Color.YELLOW);break;
					case 5: brick.setColor(Color.YELLOW);break;
					case 6: brick.setColor(Color.GREEN);break;
					case 7: brick.setColor(Color.GREEN);break;
					case 8: brick.setColor(Color.CYAN);break;
					case 9: brick.setColor(Color.CYAN);break; 
					default: break;
				}
				add(brick);
			}
		}
	}
	
	
	
	
	
	
	
}
