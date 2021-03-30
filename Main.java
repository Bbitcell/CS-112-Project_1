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
 * 	Class : Main.java 
 *  
 *  This class shows a tutorial and organizes objects.
 *  
 *  */


import javax.swing.*;
import java.util.Scanner;



// First we import the necessary packages for our program mainly for GUI and user input.



// This is the main class which inherits Jframe

public class Main extends JFrame{

	// This is the width and height of the window displayed (in pixels)
	public static int width = 800;
	public static int height = 500;
	
	public static void main(String[] args) {
		
		
		// Call the method to display a tutorial for the game 
		Tutorial();
		
		// Create a Jframe to contain the modified JPanel
		// Then create a Modified JPanel from the Game_Board class called Board
		
		JFrame frame = new JFrame();
		Game_Board Board = new Game_Board();

		// Print an important message to tell the user how to exit the game because I have disabled the x button on the window.
		
		System.out.println("To exit, please type escape key on your keyboard. ");
		
		// Then add Board object to frame.
		
		frame.add(Board);
		
		// The code below sets the settings for the window such as size, resize, disable X button, and of course make it visible to the user,
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
		
		
		
	}
	
	
	// The method below show a tutorial for the game
	public static void Tutorial() {
		// The game needs to give an option to the user to view the tutorial or not.
		// Therefore, I have created Scanner class to take a input from the user and variable (controls) to store the input.
		
		
		Scanner scan = new Scanner(System.in);
		String controls;

		
		// I have used a nested loop mainly because if the user inputs something other than yes or no, then the program will asks the user again.
		do {
			System.out.println("Would you like to know the instructions? \n Please type 'Yes' or 'No'");
			controls = scan.next();
			// If the user enters a capital letter in their answer then the program should still be able to recognize the input
			// index is reference for the letter in the input which the user has written.
			// control.length is the total length of characters in the input(controls)
			
			for (int index = 0; index <controls.length(); index++) {
				
				// Convert each character to lower case and store in variable.
				char lowercase_letter = Character.toLowerCase(controls.charAt(index));
				
				// Then use a if statement to compare the lower case character to the upper case character.
				if (lowercase_letter != controls.charAt(index) ) 
				{
					// iff false then replace the upper case character in the controls with lower case character (letter)
					controls = controls.replace(controls.charAt(index), lowercase_letter);
				}

			}
			// Then check if the user has written yes or no. If the user enters other than that then the program will the user to enter again.
		} while ( !controls.equals("no") && !controls.equals("yes"));
		
		// if the user enter yes then display a tutorial for the game
		if (controls.equals("yes")) {
			System.out.print("In this game you are the blue square and you need to compete\nwith the red square in eating the green square.\nThe one who has eaten the most wins the game."
					+ "\nHowever, the red square can eat the player and gain a point.\nAlso, if the player goes out of bounds player will lose a point.\n\nUse the arrow keys to move.\nPress space to pause the game and press enter to continue.\n");
			
			// The code below is for convenience sake or else the program will not give the user time to read the tutorial.
			System.out.print("Type anything to start");
			controls = scan.next();
		}
		// Finally close the scan object.
		scan.close();
	}
// Please view the Game_Board.java to see the rest of the project.



}
