#Readme file for BT code:

###How the program works:


The program was built using Eclipse and tested on OSX 10.9.1.

The program can run in 2 ways, either via command line or in console.


###General flow:

	1. It takes a list of surnames and a path to your surnames file (These can be in the format of parameters or input via console).
	2. Converts the files surnames according to the rules set out in the documentation.
	3. Equivalent letters are converted to a group specific letter e.g. group 1's token is !, this replaces any occurence of this letter.
	4. The converted surnames are stored in a map where the key is the converted surname and the value is an ArrayList of names that match this value.
	5. Your input surnames are then converted to their equivalent according to the rules.
	6. The map is then queried to see if the converted name is a key stored in the map.
	7. If yes the relevant surnames are output to screen otherwise a relevant message is presented saying no match is found.

#####To run on command line:

Compile the code with:

"javac MainClass.java Phonetics.java"


Run the code with:

"java MainClass <name1> <name2> <nameN> < Filepath"

e.g.

"java MainClass Winton "Van Damme" Jones Simon  McDowell  < /Users/jamie/Documents/workspace/BTCode/src/Surnames.txt"

gives the output:

	"Winton: Van Damme

	Van Damme: Van Damme

	Jones: Jonas, Johns, Saunas

	Simon: There is no name stored that matches the parsed name you input

	McDowell: There is no name stored that matches the parsed name you input"


#####To run via console:

	1) Open in IDE.
	2) Comment out the code from line 10 to line 27 (This code deals with getting arguments and file from the command line)
	3) Run the program, it will prompt you for a filepath.
		The filepath is in the format:
		/Users/jamie/Documents/workspace/BTCode/src/Surnames.txt
	4) If the file path is correct you will be prompted to enter a name.
	5) If a phonetically similar name is found in the map, the original name and similar names are displayed.
	6) Otherwise a non-match message is output. 
	7) End the program by entering nothing when prompted for a name.

###Assumptions I made:

When removing repeated letters, I consider AAAA as 2 sets of doubles, so this would become AA not A.


###Extras:

1) When testing I wrote my code in eclipse, I wanted quick testing without having to switch to command line, so wrote the commented out lines of code found at the bottom of the main method. Uncommenting these lines and commenting out from line 10 to line 27 will allow the program to prompt you to enter a filepath+filename and use this to locate your name file. From here you can then input names and check the file for similar names. When finished simply hit enter when prompted for another name and it will end the program. 

2) To use names with multiple letters in the command line you must put them in quotes, e.g. "van damme" so that it is recognised as one word for a parameter.
