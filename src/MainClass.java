import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		
		// Get names from input file, passed via command line
		ArrayList<String> fileNames = new ArrayList<String>();
		while ((sc.hasNextLine())) {
			fileNames.add(sc.nextLine());
		}

		// Get user's names from args array
		ArrayList<String> inputNames = new ArrayList<String>();
		if (args.length == 0) {
			System.out.println("ERROR: Please re-run program and enter names as parameters");
			System.exit(1);

		}
		for (int i = 0; i < args.length; i++) {
			inputNames.add(args[i]);
		}

		Phonetics x = new Phonetics(fileNames, inputNames);
		
		/*
		  //Un-comment if working in IDE and passing in files from console
		  System.out.println("Please enter the full filepath of your name file");
		  String filename = sc.nextLine(); 
		  Phonetics x = new Phonetics(filename);
		 */
	}
}
