import java.util.Random;

/**
 * A console-based Hangman game. Welcome message printed first. Randomly selected word chosen 
 * from list of words then displayed with asterisks (***) for each character. Player guesses letter. 
 * For each correct letter guessed, the asterisk designating that letter's position is replaced with the 
 * actual letter. Otherwise, an incorrectly guessed letter leads to another addition to the Hangman. There are 8
 * attempts allowed that includes (a top hat, head, 2 arms, body, 2 legs, and a cane). No penalty for repeat guesses.
 * 
 * Future additions will be... list of words stored in a text file to be read by the program, GUI for the game that depicts the 
 * Hangman and guessed letters, option menu that allows players to add to the list of words and About Game, and more.
 * @author staudts
 *
 */
public class HangmanConsoleGame {
	private static String[] words = {"BALL", "JUMP", "CAT", "HAMBURGER"};
	public static char[] guessedLetters;
	public static String selectedWord;
	
	public static void main(String[] args) {
		Random generator = new Random();
		guessedLetters = new char[0];
		int incorrectGuesses = 0;
		
		// Welcome message
		System.out.println("Welcome to Hangman!");
		System.out.println("Created by staudts in July 2020.\n");
		
		do {
		
		// Randomly selects word from the word list
		selectedWord = words[generator.nextInt(words.length)];
		
		System.out.println("Your word is: ");
		printWord();
		incorrectGuesses++;
		System.out.println("The word is " + selectedWord);

		}
		while (incorrectGuesses < 8);
		System.out.println("Fin.");
		
	}
	
	
	/**
	 * Prints the Hangman word that reflects the player's most recent guesses.
	 */
	public static void printWord() {
		if (guessedLetters.length == 0) {
			for (char wordCharacters: selectedWord.toCharArray())
				System.out.print("* ");
			System.out.println();
		}
		
		System.out.println("This has not yet been fully implemented.");
	}
	
	/**
	 * Asks user for letter and updates game accordingly.
	 */
	public static void guessAndTest() {
		
	}
	
	/**
	 * Prints the Hangman based on how many incorrect guesses given by player.
	 */
	public static void printHangman() {
		System.out.println("This has not yet been implemented.");
	}
	
	
}
