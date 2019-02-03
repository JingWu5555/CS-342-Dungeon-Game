import java.util.*;

// responsible for creating the artifact objects

public class Artifact{
	
	private int ID;
	private int mobility;
	private int value;
	private int placeID;
	private int keyPattern;
	private String name;
	private String description;
	private Place artifactLocation;
	
	//parses the artifact section of the gdf file
	 
	public Artifact(Scanner fileInput){
		
		while(fileInput.hasNextLine()){
    		String inputText = fileInput.nextLine();
            
    		if(inputText.isEmpty()){
    			 try{
    				 placeID = Integer.parseInt(fileInput.nextLine());
    				 artifactLocation = Place.getPlaceByID(placeID);
    			 }
    			 catch(NumberFormatException e){
    				 continue;
    			 }
    			 continue;
    		}
    		else if(inputText.startsWith("//")){
    			continue;
    		}
    		else{
    			
    			int delimiterIndex = inputText.indexOf("//");
    			int numberQuotes = 0;
    			if(delimiterIndex > -1){
    			   inputText = inputText.substring(0, delimiterIndex);
    			}
    			
    			String []p = inputText.split("\\s+");
    			
    			try{
					ID = Integer.parseInt(p[0]);
    				value = Integer.parseInt(p[1]);
    				mobility = Integer.parseInt(p[2]);
    				keyPattern = Integer.parseInt(p[3]); 
    				name = "";
    				for(int i = 4; i < p.length; i++){
        		    	String space = " ";
        		    	name += (p[i] + space);
        		    }
    				name = name.trim();
    				
    			}
    			catch(NumberFormatException e){
    				continue;
    			}
    			
    		    try{
    		       numberQuotes = Integer.parseInt(fileInput.nextLine());
    		    }
    		    catch(NumberFormatException e){
    		    	continue;
    		    }
    			description = "";
    			for(int i = 0; i <numberQuotes; i++){
    				String newLine = "\n";
    				description += (fileInput.nextLine() + newLine);
    			}
				artifactLocation.addArtifact(this);
    			/*
    			 System.out.println(placeID);
    			 System.out.print(weight + " ");
    			 System.out.print(movable + " ");
    			 System.out.print(unlockNumber + " ");
    			 System.out.print(itemName + "\n");
    			 System.out.println(numberQuotes);
    			 System.out.println(description);
    			 */
    			 return;
    			 
    		}
		}
	}
	
	//return value of the artifact
	public int value(){
		return value;
	}
	
	// returns the mobility of artifact
	public int size(){
		 return mobility;
	}
	//returns the name of artifact
	public String name(){
		return name;
	}
	
	//returns description of artifact
	public String description(){
		return description;
	}
	
	// uses the key artifact 
	public void use(){
		 Game.getCurrentPlace().useKey(this);
	}
	// checks if the artifact can be used
	public boolean checkIfUsable(){
		if(keyPattern == 0){
			return false;
		}
		return true;
	}
	//checks if the key matches the direction's lockPattern
	public boolean matchPattern(int directionLock){
		if(directionLock == keyPattern){
			return true;
		}
		return false;
		
	}
		
	
}