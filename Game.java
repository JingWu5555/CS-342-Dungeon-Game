import java.util.*;

// creates the game object
// contains collection of other objects
// starts the game with the .play() method
public class Game{
	
	private Vector<Place> placeCollection = new Vector<Place>();
	private Vector<Artifact> artifactCollection = new Vector<Artifact>();
	private String name;
	private static Place currentPlace;
	
	
	// passes Scanner to Place, Direction & Artifact Constructor
	public Game(Scanner fileInput){
		
		while(fileInput.hasNextLine()){
    	   
    	   String fileLine = fileInput.nextLine();
    	   // reads in the game name
    	   if(fileLine.indexOf("GDF") > -1){
     		  int delimiterIndex = fileLine.indexOf("//");
     		  String []parsedLine = fileLine.split("\\s+");
     		  int versionNumberIndex = fileLine.indexOf(parsedLine[2]);
     		  name = fileLine.substring(versionNumberIndex, delimiterIndex).trim();
     	      System.out.println("Game: " + name + "\n\n");
     	   }
    	   
    	   if(fileLine.indexOf("PLACES") > -1){
    		  int delimiterIndex = fileLine.indexOf("//");
    		  String []parsedLine = fileLine.split("\\s+");
    	      int nPlaces = Integer.parseInt(fileLine.substring(parsedLine[0].length() + 1, delimiterIndex).trim());
    	      for(int i = 0; i < nPlaces; i++){
	      	      placeCollection.add(new Place(fileInput));
	      	      
	          }
    	      
			  currentPlace = placeCollection.get(0);
    	   }
    	  
    	   if(fileLine.indexOf("DIRECTIONS") > -1){
    		   int delimiterIndex = fileLine.indexOf("//");
     		   String []parsedLine = fileLine.split("\\s+");
     	       int nDirections = Integer.parseInt(fileLine.substring(parsedLine[0].length() + 1, delimiterIndex).trim());
    		   for(int i = 0; i < nDirections; i++){
         	        Direction temp = new Direction(fileInput);
         	        
         	        
     		  }	  
    		   
    	   }
    	   
    	   if(fileLine.indexOf("ARTIFACTS") > -1){
    		   int delimiterIndex = fileLine.indexOf("//");
     		   String []parsedLine = fileLine.split("\\s+");
     	       int nArtifacts = Integer.parseInt(fileLine.substring(parsedLine[0].length() + 1, delimiterIndex).trim());
    		   for(int i = 0; i < nArtifacts; i++){
         	      Artifact temp = new Artifact(fileInput);
         	      
     		  }	  
    		   
    	   }
    	   if(fileLine.isEmpty()){
    		   continue;
    	   }
		}
		
		
	}
	
	//adds Place object to vector
	public void addPlace(Place location){
		placeCollection.add(location);
	}
	
	private boolean checkGameQuit(String keyword){
		if((keyword.equalsIgnoreCase("quit")) || (keyword.equalsIgnoreCase("exit"))){
			System.out.println("You have exited the game.");
			return true;
		}
		return false;
	}
	
	// parses the artifact name from user input
	private String parsingArtifactName(String []playerDecision){
		String itemName = "";
		for(int i = 1; i < playerDecision.length; i++){
			itemName += playerDecision[i] + " ";
		}
		return itemName.trim();
	}
	// executes the input taken from command line
	// checks the input for "GET", "USE", "DROP", "INVE"
	private void executeCommand(String command){
		
		//parse the input
		String []playerDecision = command.split(" ");
		String itemName;
		
		//check for command "get"
	    if(command.toUpperCase().contains("GET")){
	    	
			if(playerDecision.length < 2){
				System.out.println("Please enter a valid item's name.");
			}
			else{
				itemName = parsingArtifactName(playerDecision);
				//checks if current place have the artifact user wants to get
				if(currentPlace.doesArtifactExist(itemName) == 0){
					 System.out.println("There is no artifact by the name of " + itemName + " here.\n");
			    }
				else if(currentPlace.doesArtifactExist(itemName) == 2){
					System.out.println(itemName + "is too heavy to move.\n");
				}
				else{
					
				       System.out.println("You have obtained a " + itemName.toLowerCase() + "\n");
					   //adds artifact to the game's collection of artifacts
					   artifactCollection.add(currentPlace.removeArtifactFromPlace(itemName));
				    
				}			
		    }
		}
	    //check for "use"
		else if(command.toUpperCase().contains("USE")){
			if(playerDecision.length < 2){
				System.out.println("Please type a valid item's name");
			}
			else{
				itemName = parsingArtifactName(playerDecision);
				if(artifactCollection.size() == 0){
					System.out.println("You do not have " + itemName + " in your inventory\n");
				}
				else{
					if(searchInventory(itemName)){
						doStufftoArtifact(playerDecision[0], itemName);
					}
					else{
				        
				        ;
					}
			    }
			}
		}
	    //check for "drop"
		else if(command.toUpperCase().contains("DROP")){
			if(playerDecision.length < 2){
				System.out.println("Please type a valid item's name\n");
			}
			else{
				itemName = parsingArtifactName(playerDecision);
				if(artifactCollection.size() == 0){
					   System.out.println("You do not have " + itemName + " in your inventory\n");
				}
				else{
					
					if(artifactCollection.size() == 0){
						System.out.println("You do not have " + itemName + " in your inventory\n");
					}
					else{
						if(searchInventory(itemName)){
							doStufftoArtifact(playerDecision[0], itemName);
						}
						else{
					        
					        ;
						}
				    }
				}
				
			}
		}
	    //checks for "inve"
	    // prints the artifact collection in game
	    // sums up the value + the mobility and prints the name
		else if(command.equalsIgnoreCase("INVE") || command.toUpperCase().equalsIgnoreCase("INVENTORY")){
			if(artifactCollection.size() == 0){
				System.out.println("Inventory is empty!!!");
			}
			else{
				System.out.println("Inventory List:\n");
				int totalPounds = 0;
				int totalValue = 0;
				
				for(int i = 0; i < artifactCollection.size(); i++){
				    System.out.println("     Artifact: " + artifactCollection.get(i).name() + ", Value: " + artifactCollection.get(i).value() + ", Mobility: " + artifactCollection.get(i).size() + " pound");
					totalPounds += artifactCollection.get(i).size();
					totalValue += artifactCollection.get(i).value();
				}
				System.out.println("------------------------------------------");
				System.out.println("total mobility: " + totalPounds);
				System.out.println("total value: " + totalValue + "\n");
			}
		}
		else{
			 // set the current place equal to the destination if it is valid
			 currentPlace = currentPlace.followDirection(command);
		}
		

	}
	// search user's inventory for the artifact
	public boolean searchInventory(String itemName){
		
		for(int i = 0; i < artifactCollection.size(); i++){
			
			if(artifactCollection.get(i).name().equalsIgnoreCase(itemName)){
			    
			    return true;
			}   
		}
		System.out.println("You do not have " + itemName + " in your inventory\n");
		return false;
	}
	// gets the current place
	public static Place getCurrentPlace(){
		return currentPlace;
	}
	
	//prints the game info
	public void print(){
		System.out.println("Game Name: " + name + currentPlace.name());
	}
	
	// uses the artifact
	private void doStufftoArtifact(String command, String itemName){
		 int found = 0;
		 for(int i = 0; i < artifactCollection.size(); i++){
			
			if(artifactCollection.get(i).name().equalsIgnoreCase(itemName)){
			    found = 1;
			    if(command.equalsIgnoreCase("USE")){
			        if(artifactCollection.get(i).checkIfUsable() == true){
			    	   artifactCollection.get(i).use();
			        }
			        else{
			        	System.out.println(artifactCollection.get(i).name() + " can not be used like this.");
			        }
					
				}
				else if(command.equalsIgnoreCase("DROP")){
					System.out.println("You dropped " + artifactCollection.get(i).name() + " from your inventory.\n");
					currentPlace.addArtifact(artifactCollection.get(i));
					artifactCollection.remove(i);
				}
				else{
					;
				}
			}   
		 }
		 if(found == 0){
			    System.out.println("Not a valid artifact name.\nPlease type a valid artifact name.\n");
		 }
	}
	
	//starts the game
	public void play(){
		
		Scanner userInput = new Scanner(System.in);
		currentPlace.display();
		System.out.println("Enter your command: ");
		String command = userInput.nextLine().toUpperCase();
		while(checkGameQuit(command) != true){
			
			
		  if(command.equalsIgnoreCase("look")){
				 currentPlace.display();
				 
		  }
		  else{
			  if((!command.equalsIgnoreCase("quit")) && (!command.equalsIgnoreCase("exit"))){
			     
			     executeCommand(command);
			     
			     
			     if((currentPlace.name().equalsIgnoreCase("EXIT"))){
			    	 
					 break;
			     }
			     
				   
			     
			  }
			  
			   
			
		  }
				
			
		    
			
			
			System.out.println("Enter your command: ");
			// get the next input
			command = userInput.nextLine().toUpperCase();
			
		}
		
	}
	
	
	
	
}