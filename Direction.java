
import java.util.*;
import java.lang.Math.*;

// direction class for direction object so user can choose which direction to go
// responsible for traveling from place to place
public class Direction{
	
	//initializes the variables
	private int ID;
	private int lockPattern;
	private int locked = 0;
	private Place source;
	private Place destination;
    private DirType dir;
	
    // parses the direction section of the gdf file and makes the objects for each
	Direction(Scanner fileInput){
		
		while(fileInput.hasNextLine()){
	    		String inputText = fileInput.nextLine();
	            
	    		if(inputText.isEmpty()){
	    			  continue;
	    		}
	    		else if((inputText.indexOf("ARTIFACTS") > -1)){
	    			//Artifacts
	    			
	    			return;
	    		}
	    		else if(inputText.indexOf("DIRECTIONS") > -1){
	    			continue;
	    		}
	    		else if(inputText.startsWith("//")){
	    			continue;
	    		}
	    		else if(inputText.indexOf("//") >=0 ){
	    			int delimiterIndex = inputText.indexOf("//");
		    		inputText = inputText.substring(0, delimiterIndex);
		    		String []parsedLine = inputText.split("\\s+");
		    		ID = Integer.parseInt(parsedLine[0]);
		    		int lockCompare = Integer.parseInt(parsedLine[3]);
	    			source = Place.getPlaceByID(Integer.parseInt(parsedLine[1]));
					
	    			dir = DirType.valueOf(parsedLine[2].toUpperCase());
					if(lockCompare <= 0){
						lock();
					}
					
		    		destination  = Place.getPlaceByID(Math.abs(lockCompare));
		    		
		    		lockPattern = Integer.parseInt(parsedLine[4]);
					source.addDirection(this);
	    			
	    		   
	    		    
	  			    return;
	    		}
		
	   }
	}
	
	// creates abbreviation and text for each direction
	private enum DirType{
		N("North", "N"), S("South", "S"), W("West", "W"), E("East", "E"), U("Up", "U"), D("Down", "D"), SW("Southwest", "SW"),
		NW("Northwest", "NW"), NE("Northeast", "NE"), SE("Southeast", "SE"), SSE("South-Southeast", "SSE"), 
		NNE("North-Northeast", "NNE"), SSW("South-Southwest", "SSW"), NNW("North-Northwest", "NNW"),
		WNW("West-Northwest", "WNW"), WSW("West-Southwest", "WSW"), ENE("East-Northeast", "ENE"), ESE("East-Southeast", "ESE"), NONE("None", "None");
		
		private String text;
		private String abbreviation;
		
		private DirType(String text, String abbreviation){
			this.text = text;
			this.abbreviation = abbreviation;
			
		}
		
		// returns the text(direction name)
		@Override
		public String toString() {
			
			return text;
		}
		
		//match input string with direction abbreviation or text
		private boolean match(String s){
			String[] userInput = s.split(" ");
		    String directionToFollow = userInput[userInput.length - 1];
			if(directionToFollow.equalsIgnoreCase(abbreviation) || directionToFollow.equalsIgnoreCase(text)){
				return true;
			}
			else{
				return false;
			}
			
		}
	}
	
	// calls the enum match method
	public boolean match(String s){
		
		if(dir.match(s)){
			return true;
		}
		else{
			return false;
		}
	}
	
	// locks the room
	public void lock(){
		locked = 1;
	}
	
	//uses the key
	public void useKey(Artifact key){
		if(lockPattern == 0){
			System.out.println("Nothing happened for Direction " + dir.toString());
			
		}
	    else if(key.matchPattern(lockPattern) && locked == 1){
			unlock();
			System.out.println("You unlocked direction " + dir.toString());
		}
		else if(key.matchPattern(lockPattern) && locked == 0){
			lock();
			System.out.println("You locked direction " + dir.toString());
		}
		
		else{
			System.out.println("Direction " + dir.toString() + " is still locked.");
		}
			
		
	}
	
	//unlocks the room
	public void unlock(){
		locked = 0;
	}
	// checks if direction is locked
	public boolean isLocked(){
		if(locked == 1){
			return true;
		}
		else{
			return false;
		}
	}
	// debugging purposes
	public void print(){
		
		System.out.println(ID + " ");
		System.out.println(source + " ");
		System.out.println(ID + " ");
		System.out.println(ID + " ");
	}
	// go to the direction if it is not locked
	public Place follow(){
		if(isLocked() == false){
			destination.display();
			return destination;
		}
		else{
			System.out.println("This direction is locked. Please pick another one.\n");
			return source;
		}
	}
	
	
	
}