import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
    	//Get filename
    	Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the full filepath of your name file");
        String filename = sc.nextLine();

    	Phonetics x = new Phonetics(filename);
    }
}
