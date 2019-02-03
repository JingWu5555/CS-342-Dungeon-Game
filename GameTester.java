import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

// Jing Wu
// jwu210


// main class, calls all the other classes
public class GameTester{

    // main function
    public static void main(String[] args){
		
       System.out.println("Name: Jing Wu");
	   System.out.println("Net-id: jwu210");
	   System.out.println("Game Description & Instructions: ");
	   System.out.println("  This program creates an text based game, where you can explore 14 rooms.");
	   System.out.println("  You play this game by choosing different directions and each direction will lead you to a different location");
	   System.out.println("  To move from place to place, simply type \"go\" follow by the direction you want to go in and hit enter.");
	   System.out.println("  You may pick up, drop, use artifacts that are avaialbe in each room\n  You will also have an inventory that you may see at all times.");
	   System.out.println("  Or you could simply type the first letter of the direction such as \"N\" or \"n\".");
	   System.out.println("  For example, \"go N\" or \"Go North\" is perfectly acceptable as the direction will be case insensitive.");
	   System.out.println("  Beware as some directions may be locked, so you might have to take an alternative route.");
	   System.out.println("  But if you have the right key, you may be able to unlock the direction");
	   System.out.println("  To quit or exit the game, either type \"quit\" or \"exit\" which is case insensitive as well.");
	   System.out.println("  Another way to exit the game is to reach the \"EXIT\" which is west of the \"Entrance Hall\".\n\n");
       int success = 0;
	   Scanner fileInput = null;
	   // takes in the file 
       while(success != 1){
    	  try{
    		 Scanner input = new Scanner(System.in);
    		 String userInput = input.nextLine();
    		  
    		 if(!userInput.equalsIgnoreCase("quit")){
    			 File filename = new File(userInput);
        	     fileInput = new Scanner(filename);
        	     success = 1;
        	     
    		 }
    		 else{
    	         success = 1;
				 System.out.println("Program Exited");
				 System.exit(0);
    		 }
    	  }
    	  catch(FileNotFoundException e){
    		  success = 0;
    		  System.out.println("File does not exist. Please try again\n");
    		 
    	  }
        }
       //creates game object and starts the game
		Game game = new Game(fileInput);
		game.play();
		fileInput.close();
		//close scanner at the end
	}



}