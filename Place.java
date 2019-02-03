import java.util.*;

// responsible for creating place objects and storing direction objects
public class Place{
	
	
	
	private Vector<Direction> dir = new Vector<Direction>();
	private static HashMap<Integer, Place> placeMap = new HashMap<Integer, Place>();
	private Vector<Artifact> artifactCollection = new Vector<Artifact>();
    private int ID;
	private static int exitCreated = 0;
	private static int nowhereCreated = 0;
	private String name;
	private String description;
	
	public Place (int ID, String name, String description){
		this.ID = ID;
		this.name = name;
		this.description = description;
	}
	// parses and creates place objects
	public Place(Scanner fileInput){
		while(fileInput.hasNextLine()){
    		String inputText = fileInput.nextLine();
            
    		if(inputText.isEmpty()){
    			 
    			 continue;
    			 
    		}
    		else if(inputText.startsWith("//")){
    			continue;
    		}
    		else if((inputText.indexOf("DIRECTIONS") > -1)){
    			//Artifacts
 
    			break;
    		}
    		else if(inputText.indexOf("//") >=0 ){
    			
    			int delimiterIndex = inputText.indexOf("//");
    			inputText = inputText.substring(0, delimiterIndex);
    			String []p = inputText.split("\\s+");
    			try{
    				ID = Integer.parseInt(p[0]);
    			}
    			catch(NumberFormatException e){
    				continue;
    			}
    			name = "";
    		    for(int i = 1; i < p.length; i++){
    		    	String space = " ";
    		    	name += (p[i] + space);
    		    }
    		    name = name.trim();
    		    int ndescr = 0;
    		    try{
    		       ndescr = Integer.parseInt(fileInput.nextLine());
    		    }
    		    catch(NumberFormatException e){
    		    	continue;
    		    }
    			
    			/*if(inputText.length() <= 2){
    			  numberQuotes = Integer.parseInt(inputText);
    			}*/
    		    description = "";
    			for(int i = 0; i <ndescr; i++){
    				String newLine = "\n";
    				description += (fileInput.nextLine() + newLine);
    			}
				placeMap.put(ID, this);
    			
    			
    			return;
    		}
		}
	}
	// returns the place's name	
	public String name(){
		return name;
	}
	// return string of description
    public String description(){
		return description;
	}
    // add direction object to vector
    public void addDirection(Direction location){
		dir.add(location);
		
	}
    // uses the key
    //passes artifact to direction
	public void useKey(Artifact key){
		
		for(int i = 0; i < dir.size(); i++){
			 dir.get(i).useKey(key);	
		}
	}
	// gets the place object using its id and returns the object
	public static Place getPlaceByID(int ID){
	   if(ID == 1 && exitCreated == 0){
			
			Place exit = new Place(ID, "EXIT", "You have exited the game");
		    placeMap.put(ID, exit);
			exitCreated = 1;
	   } 
	   if(ID == 0 && nowhereCreated == 0){
			Place nowhere = new Place(ID, "NOWHERE", "It is locked and leads to nowhere.");
		    placeMap.put(ID, nowhere);
		    
			nowhereCreated = 1;
		}
		if(placeMap.containsKey(ID)){
			return placeMap.get(ID);
		}
		return null;
	}
	// adds the artifact to this place's collection of artifacts
	public void addArtifact(Artifact item){
		artifactCollection.add(item);
	}
    // prints out the description of the place
    public void display(){
    	System.out.println("Your Current location: " + name() + "\nDescription: ");
    	System.out.println(description());
		//System.out.print("\n");
		if(artifactCollection.size() != 0){
			for(int i = 0; i < artifactCollection.size(); i++){
				
				System.out.println("You see a " + artifactCollection.get(i).name());
				System.out.println(artifactCollection.get(i).description());
			}
		}
    }
    // for debugging purposes
    public void print(){
    	System.out.print(ID + " ");
    	System.out.println(name());
    	System.out.println(description());
		
    }
	// checks if the artifact is even in this place's collection of items
	public int doesArtifactExist(String itemName){
		if(artifactCollection.size() == 0){
			return 0;
		}
		for(int i = 0; i < artifactCollection.size(); i++){
			 if(artifactCollection.get(i).name().equalsIgnoreCase(itemName)){
				 if(artifactCollection.get(i).size() == -1){
					 return 2;
				 }
				 else{
			         return 1;
				 }
			 }   
	    }
		return 0;
	}
	
	// if the current place have the artifact then it removes the artifact from this place's artifact collection
	public Artifact removeArtifactFromPlace(String itemName){
		Artifact returnArtifact = null;
		if(artifactCollection.size() == 0){
			return null;
		}
	    else{
			for(int i = 0; i < artifactCollection.size(); i++){
				 if(artifactCollection.get(i).name().equalsIgnoreCase(itemName)){
					
			             returnArtifact = artifactCollection.get(i);
			         
			             
			         //removes artifact from this place's collection of artifacts
					     artifactCollection.remove(i);
					 
					 
			     }   
				
			}
		}
		return returnArtifact;
		
	}
    // go to the direction specified by the user
	public Place followDirection(String location){
		int found = 0;
		for(int i = 0; i < dir.size(); i++){
			if(dir.get(i).match(location) == true){
				found = 1;
				return dir.get(i).follow();
			}
		}
		if(found == 0){
			System.out.println("Not a valid direction. Pick another direction.\n");
		}
		return this;
	}
	
	
	
}