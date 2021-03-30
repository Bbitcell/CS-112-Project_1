/*	Project CS 112
 * 
 * 	Name 	: Mohammed Umer Raheemuddin
 *  Id	 	: 4110718
 *  Section : L03
 *  
 *  
 *  Program Description:
 *  
 *  This program is a game in which the player (blue) has to compete with the enemy (red) in eating the food (green).
 *  The enemy can also attack and eat the player to gain a point. However, the player is slightly faster than the enemy. 
 * 	If the player goes out of the screen then the player will lose a point.
 * 
 * 
 * 	This program contains two classes Main.java and Game_Board.java
 * 	Please run them in the same directory for the program to work as intended.
 *  
 * 	Class : Game_Board.java 
 *  
 *  This class contains the Graphics object, controls for the player, and Ai for the non player objects.
 *  */


// The import below is for the GUI


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import javax.swing.*;


// The import is for the Ai
import java.util.Random; 




// The class is an modified version the swing JPanel. (Inherits the JPanel class)

public class Game_Board extends JPanel implements ActionListener, KeyListener {
	
	
	// First create a timer for the program.
	// The timer updates the game every 140 milliseconds. (Similar to FPS)
	
	Timer time = new Timer(140, this);
	
	// The size of the window so that the player and the AI do not cross outside of the screen.
	
	int width = 800;
	int height = 500;
	
	
	// Random class to randomly generate position the food(green) in the game.
	Random random = new Random();
	int food_x_position = random.nextInt(width - 50);
	int food_y_position = random.nextInt(height - 59);
	
	
	// This is the default location and score of the enemy.
 	int enemy_x_position = 400;
	int enemy_y_position = 250;
    int enemy_score = 0;
	
    
    // This is the default location, velocity, and score of the player. 
	int player_x_position = 200;
	int player_y_position = 250;
	int player_x_velocity, player_y_velocity;
    int player_score = 0;

	
    // These variables will be explained later. But for now they are:
    // These variables are initialized for now. 
    String[] all_food_direction;
    String enemy_direction = "LEFT";
    String food_direction = "LEFT";
    
    // This counter updates the the AI movement.
	int counter = 0;

	// General settings for the JPanel
	public Game_Board() {
		
		// Start the time when the object is created for Actionlistener and KeyListener.
		time.start();
		
		// set settings for the JPanel
		setBackground(Color.BLACK);
		setSize(width, height);
		
		// Add player controls to the JPanel.
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	
	}
	
	// Create Graphics objects for the game 
	public void paint(Graphics objects) {
		
		// Create a environment where the Graphics object can exist.
		
		super.paintComponent(objects);
		Graphics2D game_board = (Graphics2D) objects;
		
		
		// Create a food graphics object, this will be a green square
		Rectangle2D food = new Rectangle2D.Double(food_x_position, food_y_position, 20, 20);
		game_board.setPaint(Color.GREEN);
		game_board.fill(food);
		
		// Create a enemy graphics object, this will be a red square
		Rectangle2D enemy = new Rectangle2D.Double(enemy_x_position, enemy_y_position, 20, 20);
		game_board.setPaint(Color.RED);
		game_board.fill(enemy);
		
		
		// Create a player graphics object, this will be a player square
		Rectangle2D player = new Rectangle2D.Double(player_x_position, player_y_position, 20, 20);
		game_board.setPaint(Color.BLUE);
		game_board.fill(player);
		
		// Note that I am using position variables because they will change according to the time and events.
		
	}

	// This method moves the AI and the player every 140 milliseconds.
	// This method also checks if the any object are eaten or are outside of the screen every 140 milliseconds
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		// Perform the method that checks if the player is out the screen and then resets the player with a penalty.
		player_out_of_bounds();
		
		// Perform the method which checks if the player or the food was eaten.
		// Then moves the player or the food to a new location on the screen
		eaten_reset ();
		
		// This counter makes sure that the AI moves in a sensible way
		// Without this counter the food would move maniacally and the enemy would be very difficult to dodge.
		// More details on the AI methods.
		counter += 1;

		
		// Perform the method which calls the AI of food and enemy.
		food_AI ();
		enemy_AI ();




		
		

		// Move the player according to the key pressed.
		// More details below
		player_x_position += player_x_velocity;
		player_y_position += player_y_velocity;
		
		
		// Update the game according to the timer (140 millisecond)
		repaint();
	}
	
	// Method to check if the player is out of the window
	public void player_out_of_bounds() {
		
		// This if statement gives a penalty to the player if the player goes outside of the screen
		// The player is then moved to the start position.
		
		if (player_x_position < 0 || player_x_position > (width-40) || player_y_position < 0 || player_y_position > (height-40)) {
			player_x_position = 300;
			player_y_position = 300;
			
			// deduct a point from the player
			player_score -= 1;
		}
		}
	
	
	
	
	
	// Method to move the Graphic objects to a new place when eaten.
	// Also give a point the one who ate the food or player.
	// By eaten, I mean either the player or the enemy have collied with the food.
	public  void eaten_reset () {
		
		// The if/else if determines who ate the food (green) and gives a point to the one who ate the food.
		// The food is then moved to a randomly generated position.
		

		
		// if food(green) is eaten by the player (blue).
		if ((food_x_position + 35) > player_x_position && (food_x_position - 35) < player_x_position && (food_y_position + 35) > player_y_position && (food_y_position - 35) < player_y_position) {
	
			food_x_position = random.nextInt((width-50));
			food_y_position = random.nextInt((height-50));
			
			// give a point to the player
			player_score += 1;
	
		}
		// if food(green) is eaten by the enemy (red).
		else if ((food_x_position + 35) > enemy_x_position && (food_x_position - 35) < enemy_x_position && (food_y_position + 35) > enemy_y_position && (food_y_position - 35) < enemy_y_position) {
	
			food_x_position = random.nextInt((width-50));
			food_y_position = random.nextInt((height-50));
			
			
			// give a point to the enemy
			enemy_score += 1;
	
		}
	
		// This if statement checks if the player (blue) was eaten by the enemy (red).
		// Then gives a point to the enemy (red)
		if ((enemy_x_position + 35) > player_x_position && (enemy_x_position - 35) < player_x_position && (enemy_y_position + 35) > player_y_position && (enemy_y_position - 35) < player_y_position) {
			player_x_position = 300;
			player_y_position = 300;
			enemy_x_position = 600;
			enemy_y_position = 300;
			
			// give a point to the enemy
			enemy_score += 1;
	
		}
	}
	
	// Method for the food AI
	public void food_AI () {
		

		// If the food is out side of the screen then remove some directions in which the food can move
		
		// if the food is outside of the screen at the west direction, then the food must move right to return back to the screen
		if (food_x_position < 20 ) {
			all_food_direction = new String[]{"RIGHT"};
			}
		// if the food is outside of the screen at the east direction, then the food must move left to return back to the screen
		else if (food_x_position > (width-50) ) {
			all_food_direction = new String[]{"LEFT"};
			}
		
		// if the food is outside of the screen at the north direction, then the food must move down to return back to the screen
		else if (food_y_position < 20) {
			all_food_direction = new String[]{"DOWN"};
				
			}
		// if the food is outside of the screen at the south direction, then the food must move up to return back to the screen
		else if (food_y_position > (height-50)) {
			all_food_direction = new String[]{"UP"};
	
			}
		
		// If the food is in the screen then the food can go at all direction.
		else {
			all_food_direction = new String[]{"UP","DOWN","RIGHT","LEFT"};
			}
			
			
		// This if statement makes sure that food picks a direction then after some time. The food can then decided again, which direction it would like to go.
		if (counter % 4 ==0 ) {

	
			// randomly choose the direction which the food can go.
		food_direction = all_food_direction[random.nextInt(all_food_direction.length)];
		} 
		
		
		
		// After the direction has been picked.
		// Moves the food to that direction. 
		// Default speed is 15.
		
		switch(food_direction) {
		
		case "RIGHT":
			food_x_position += 15;
			break;

		case "LEFT":
			food_x_position += -15;
			break;
			
		case "UP":
			food_y_position += -15;
			break;
		case "DOWN":
			
			food_y_position += 15;
			break;
		
	}
	}
	// Method for the food AI
	public void enemy_AI () {
		
		// This nested if statement checks if the player is close to the enemy.
		
		if ((enemy_x_position + 115) > player_x_position && (enemy_x_position - 115) < player_x_position && (enemy_y_position + 115) > player_y_position && (enemy_y_position - 115) < player_y_position) {
			// IF true.
			// The enemy is bit slower to realize because of this if statement.
			if (counter % 2 ==0 ) {
				// These if and else if statements determine the direction which the enemy can move to catch the player
				
				if (enemy_y_position < (player_y_position -20)) {
					enemy_direction = "DOWN";
				}
				else if (enemy_y_position > (player_y_position + 20)) {
					enemy_direction = "UP";
				}
				if (enemy_x_position < (player_x_position - 20))
				{
					enemy_direction = "RIGHT";
				}
				else if (enemy_x_position > (player_x_position + 20)) {
					enemy_direction = "LEFT";
			}
		}
		}
		
		// if the player is not close to the enemy then move towards the food.
		else {
			
			// The if statement below makes enemy change its direction bit slower
		if (counter % 2 ==0 ) {
			
			// These if and else if statements determine the direction that the enemy can go to catch the food.
			
			if (enemy_y_position < (food_y_position -20)) {
				enemy_direction = "DOWN";
			}
			else if (enemy_y_position > (food_y_position + 20)) {
				enemy_direction = "UP";
			}
			if (enemy_x_position < (food_x_position - 20))
			{
				enemy_direction = "RIGHT";
			}
			else if (enemy_x_position > (food_x_position + 20)) {
				enemy_direction = "LEFT";
		}
		}
		}
		
		// Once the direction has been decided then move to towards that direction.
		
		switch(enemy_direction) {
		
		
		case "RIGHT":
			enemy_x_position += 15;
			break;

		case "LEFT":
			enemy_x_position += -15;
			break;
			
		case "UP":
			enemy_y_position += -15;
			break;
		case "DOWN":
			
			enemy_y_position += 15;
			break;
		
	}
	}
	
	
	// Method to recognize the key press by the user on the keyboard.
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		// This translates the key press into valid format which we can use to compare.
		int key = e.getKeyCode();
		
		
		// if the user clicked the if and else if statement below then call the methods which determine the velocity of the player.
		if (key == KeyEvent.VK_UP) {
			up();
		}
		else if (key == KeyEvent.VK_DOWN) {
			down();
		}
		else if (key == KeyEvent.VK_LEFT) {
			left();
		}
		else if (key == KeyEvent.VK_RIGHT) {
			right();
		}
		
		
		
		
		
		/// The else if statement pauses the game when the user presses spacebar key.  
		
		else if (key == KeyEvent.VK_SPACE) {
			
			// if true then the game will stop and displays a message about the score.
			time.stop();
			System.out.println("The game is paused. \nPress enter to continue.");
			

			System.out.println("The player score is :" + player_score);
			System.out.println("The Enemy score is :" + enemy_score);


		}
		
		/// The else if statement resumes the game when the user presses enter key.  
		
		else if (key == KeyEvent.VK_ENTER) {
			time.start();
		}
		
		
		
		
		
		/// The else if statement closes the game when the user presses escape key and determines if the player has won or lost.  
		else if (key == KeyEvent.VK_ESCAPE) {
			System.out.println("The player score is :" + player_score);
			System.out.println("The Enemy score is :" + enemy_score);
			if (player_score > enemy_score) {
				System.out.println("You won!!");
			}
			else if (player_score < enemy_score) {
					System.out.println("You lost!!");
				}
			else 
				{
					System.out.println("Draw!!");
				}
				System.exit(key);
		


		}
		

	}



	// Method which move the player to the direction key pressed
	public void up() {
		player_x_velocity = 0;
		player_y_velocity = -17;
	}
	public void down() {
		player_x_velocity = 0;
		player_y_velocity = 17;
	}
	public void right() {
		player_x_velocity = 17;
		player_y_velocity = 0;
	}
	public void left() {
		player_x_velocity = -17;
		player_y_velocity = 0;
	}
	
	
	
// Now that class is ready to be declared in the Main.java
	
	
	
	
	
	
	
	
	// Redundant method. Required by the compiler. 
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	// Redundant method. Required by the compiler. 
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
