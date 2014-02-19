import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Phonetics {
	private CharSequence[] charToRemove = { "a", "e", "i", "h", "o", "u", "w",
			"y", "\\?" };

	/*
	 * Final character is used to denote what group of letters are equivalent
	 * with this universal character replacing them.
	 */
	private String equivalents[][] = { { "a", "e", "i", "o", "u", "!" },
			{ "c", "g", "j", "k", "q", "s", "x", "y", "z", ">" },
			{ "b", "f", "p", "v", "w", "<" }, { "d", "t", "[" },
			{ "m", "n", "]" } };

	// Used to store map of key and similar names.
	private HashMap<String, ArrayList<String>> mapNames = new HashMap<String, ArrayList<String>>();

	/*
	 * Overloaded method used when arguments/filename are passed in via command
	 * line in the terminal.
	 */
	public Phonetics(ArrayList<String> fileNames, ArrayList<String> inputNames) {
		// User inputs names
		insertNames(fileNames);
		for (int i = 0; i < inputNames.size(); i++) {
			String name = (inputNames.get(i));
			getSimilarNames(name);
		}
	}

	/*
	 * Overloaded constructor, used when you simply call the program in an IDE
	 * and you pass in the filename of names via console and your list of names
	 * to check via console.
	 */
	public Phonetics(String filename) {
		getFilenames(filename);
		getNames();
	}

	/*
	 * Extracts each name from the given file and calls the insert method to
	 * insert these into the map.
	 */
	public void insertNames(ArrayList<String> fileNames) {
		String currentName = "";
		String parsedName = "";

		// Applies rules to all names in the file and adds them map
		for (int i = 0; i < fileNames.size(); i++) {
			currentName = fileNames.get(i);

			// Parse the current name into it's index id
			parsedName = parseName(currentName);

			// Add these to the map with their actual name
			insertIntoMap(parsedName, currentName);
		}
	}

	/*
	 * Used with second constructor to test in an IDE by taking a name in via
	 * the console until you are done and passing them to getSimilarNames method
	 * to output any phonetically similar words.
	 */
	public void getNames() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter a name: ");
		while (sc.hasNextLine()) {
			String name = sc.nextLine();
			if (name.equals("")) {
				System.out.println("Exiting the program");
				System.exit(1);
			} else {
				getSimilarNames(name);
				System.out.println("Please enter a name: ");
			}
		}
	}

	/*
	 * Applies the rules set out in the document to give the final name which
	 * will be used as a key in the map.
	 */
	public String parseName(String name) {
		// Get rid of spaces
		name = name.replaceAll(" ", "");

		// Rule 2, word case is not significant.
		name = name.toLowerCase();

		// First letter is important so take substring of everything after
		String ltrsRemove = name.subSequence(1, name.length()).toString();

		// Get rid of designated letters in rule 3.
		for (int i = 0; i < charToRemove.length; i++) {
			ltrsRemove = ltrsRemove.replaceAll(charToRemove[i].toString(), "");
		}

		name = name.substring(0, 1) + ltrsRemove;
		name = equivLetters(name, equivalents);
		name = removeDoubles(name);

		return name;
	}

	/*
	 * Replaces letters found in the word that are in a certain group with that
	 * groups key letter.
	 */
	public String equivLetters(String name, String[][] letterList) {
		for (int i = 0; i < letterList.length; i++) {
			for (int j = 0; j < letterList[i].length - 1; j++) {
				// Replace the found letter with a special letter designated per
				// group.
				name = name.replaceAll(letterList[i][j],
						letterList[i][letterList[i].length - 1]);
			}
		}
		return name;
	}

	/*
	 * Remove double characters in the name. N.B. I consider AAAA as 2 sets of
	 * doubles, so this would become AA not A.
	 */
	public String removeDoubles(String name) {
		StringBuilder sb = new StringBuilder(name);
		int lengthOfName = name.length();

		for (int i = 0; i < lengthOfName - 1; i++) {
			if (sb.charAt(i) == sb.charAt(i + 1)) {
				sb.deleteCharAt(i + 1);
				lengthOfName--;
			}
		}

		name = sb.toString();
		return name;
	}

	/*
	 * Takes the key and the name, adds them to the map either by a new entry or
	 * by adding it to the end of the ArrayList of names
	 */
	public void insertIntoMap(String key, String name) {
		if (mapNames.containsKey(key)) {
			// Add new name onto the end of the existing ArrayList.
			mapNames.get(key).add(name);
		} else {

			// Create new ArrayList with the unparsed name as the value
			ArrayList<String> arrName = new ArrayList<String>();
			arrName.add(name);
			mapNames.put(key, arrName);
		}
	}

	/*
	 * Pass in parsed name and check for key under that name Output any other
	 * names under that key with the correct formatting Formatting - ',' after
	 * every name except the last.
	 */
	public void getSimilarNames(String name) {
		String parseName = parseName(name);
		if (mapNames.containsKey(parseName)) {
			System.out.print(name + ": ");

			/*
			 * To get correct output as documentation I'm using a for loop to
			 * optimise you can just use .toString() arrayList function
			 */
			for (int i = 0; i < mapNames.get(parseName).size(); i++) {
				System.out.print(mapNames.get(parseName).get(i));

				// To match ',' not happening on final word.
				if (i != (mapNames.get(parseName).size() - 1)) {
					System.out.print(", ");
				}
			}
			System.out.println();
		} else {
			System.out.println(name + ": There is no name stored that matches the parsed name you input");
		}
	}

	/*
	 * **UNUSED METHOD: I used to test my own files and asking the user for the
	 * filename as input instead of passing it in as a parameter, this was
	 * needed as I couldn't pass parameters via command line in my IDE**
	 * 
	 * Open file, read in each line, check if an entry already exists in the
	 * map, if it does append the name onto the end otherwise create new
	 * arraylist add that to the map.
	 */
	public void getFilenames(String filename) {
		try {
			BufferedReader myReader = new BufferedReader(new FileReader(filename));
			String readLine;
			try {
				while ((readLine = myReader.readLine()) != null) {
					String newName = parseName(readLine);

					// Already contains element so append it to end.
					if (mapNames.containsKey(newName)) {

						// Add new name onto the end of the existing ArrayList.
						mapNames.get(newName).add(readLine);
					} else {

						// Create new arraylist with the unparsed name as the
						// value
						ArrayList<String> arrName = new ArrayList<String>();
						arrName.add(readLine);
						mapNames.put(newName, arrName);
					}
				}
			} catch (IOException e) {
				System.out.println("There has been an error reading the specified file.");
				// End program if exception thrown.
				System.exit(1);
			}
		} catch (FileNotFoundException e) {
			System.out.println("The filename you have given does not exist.\nPlease re-run the program.");
			// End program if exception thrown.
			System.exit(1);
		}
	}
}
