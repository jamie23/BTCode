import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Phonetics {
	  private String[] names;
	  private CharSequence[] charToRemove = {"a","e","i","h","o","u","w","y","\\?"};
	  
	  /*Final character is used to denote what group of letters are equivalent
	   * with this universal character replacing them. 
	   */
	  private String equivalents[][] = {{"a","e","i","o","u", "!"},
			  						   	{"c","g","j","k","q","s","x","y","z",">"}, 
	  									{"b","f","p","v","w","<"},
	  									{"d","t","["},
	  									{"m","n", "]"}
	  								   };
	  									
	  private HashMap<String, ArrayList<String>> mapNames = new HashMap();
		 
	  public Phonetics(String filename){
		  //User inputs names
		  getFilenames(filename);
		  getNames();
	  }
	 
	  
	  public void getNames(){
		  	Scanner sc = new Scanner(System.in);
		  	System.out.println("Please enter a name: ");
	        while(sc.hasNextLine()) {
	        	String name = sc.nextLine();
	        	if(name.equals("")){
	 			   System.out.println("Your name contains no characters, please enter a correct name:");
	        	}else{
		            String parseName = parseName(name);
		            getSimilarNames(name,parseName);
		            System.out.println("Please enter a name: ");
	        	}
	        }
	    }  
	   
	   public String parseName(String name){
		   //Get rid of spaces
		   name = name.replaceAll(" ", "");
		   
		   //Rule 2, word case is not significant.
		   name = name.toLowerCase();
		   
		   //First letter is important so take substring of everything after
		   String ltrsRemove = name.subSequence(1, name.length()).toString();
		   
		   //Get rid of designated letters in rule 3.
		   for(int i=0;i<charToRemove.length;i++){
			   ltrsRemove = ltrsRemove.replaceAll(charToRemove[i].toString(), "");
		   }
		 
		   name = name.substring(0,1) + ltrsRemove;
		   name = equivLetters(name, equivalents);
		   name = removeDoubles(name);
		   
		   return name;
	   }
	   
	   //Give it a name, change the letters which are considered equivalent to a special character
	   public String equivLetters(String name, String[][] letterList){
		   for(int i = 0;i<letterList.length;i++){
			   for(int j = 0;j<letterList[i].length-1;j++){
				   //Replace the found letter with a special letter designated per group.
				  name = name.replaceAll(letterList[i][j], letterList[i][letterList[i].length-1]);
			   }
		   }
		   return name;
	   }
	   
	   /*
	    * Remove double characters in the name. N.B. I consider AAAA as 2 sets 
	    * of doubles, so this would become AA not A.
	    */
	   public String removeDoubles(String name){
	       StringBuilder sb = new StringBuilder(name);
            int lengthOfName = name.length();
		
		    for(int i = 0;i<lengthOfName-1;i++){
			    if(sb.charAt(i) == sb.charAt(i+1)){
					   sb.deleteCharAt(i+1);
					   lengthOfName--;
			    }
			}
				   
			name = sb.toString();
			return name;
	   }
	   
	   /*
	    * Open file, read in each line, check if an entry already exists in the
	    * map, if it does append the name onto the end otherwise create new 
	    * arraylist add that to the map.
	    */
	   public void getFilenames(String filename){
		   HashMap<String, ArrayList<String>>  nameMap = new HashMap();
		   
		   try{
			   BufferedReader myReader = new BufferedReader(new FileReader(filename));
			   String readLine;
			   try{
				   while((readLine = myReader.readLine())!= null){
					   String newName = parseName(readLine);
					   
					   //Already contains element so append it to end.
					   if(mapNames.containsKey(newName)){
						 
						   //Add new name onto the end of the existing ArrayList.
						   mapNames.get(newName).add(readLine);
					   }else{
						  
						   //Create new arraylist with the unparsed name as the value
						   ArrayList<String> arrName = new ArrayList();
						   arrName.add(readLine);
						   mapNames.put(newName, arrName);
					   }
		
				   }
			   }catch(IOException e){
				  System.out.println("There has been an error reading the specified file.");
				  //End program if exception thrown.
				  System.exit(1);
			   }
		   }catch(FileNotFoundException e){
			   System.out.println("The filename you have given does not exist.\nPlease re-run the program.");
			   //End program if exception thrown.
			   System.exit(1);
		   }
	   }
	   
	   /*
	    * Pass in parsed name and check for key under that name.
	    */
	   public void getSimilarNames(String name, String parseName){
		  if(mapNames.containsKey(parseName)){
			  System.out.print(name + ": ");
			 
			  /*To get correct output as documentation I'm using a for loop
			  * to optimise you can just use .toString() arrayList function
			  */
			  for(int i = 0;i<mapNames.get(parseName).size();i++){
				  System.out.print(mapNames.get(parseName).get(i));
				  
				  //To match ',' not happening on final word.
				  if(i!=(mapNames.get(parseName).size()-1)){
					  System.out.print(", ");
				  }
			  }
			  System.out.println();
		  }else{
			  System.out.println("There is no name stored that matches the parsed name you input");
		  }
	   }	
}