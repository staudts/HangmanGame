import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * A console-based Hangman game. Welcome message printed first. Randomly selected word chosen 
 * from list of words then displayed with asterisks (***) for each character. Player guesses letter. 
 * For each correct letter guessed, the asterisk designating that letter's position is replaced with the 
 * actual letter. Otherwise, an incorrectly guessed letter leads to another addition to the Hangman. There are 8
 * mistakes allowed (that includes a top hat, head, 2 arms, body, 2 legs, and a cane). No penalty for repeat guesses.
 * 
 * Future additions will be... list of words stored in a text file to be read by the program, GUI for the game that depicts the 
 * Hangman and guessed letters, option menu that allows players to add to the list of words and About Game, enums
 *  & difficulty levels, and more!
 * @author staudts
 *
 */
public class HangmanConsoleGame {
	private static final String[] WORDS = {"BALL", "JUMP", "CAT", "DOG", "BANANA", "FISH", "CHIPS", "HOTDOG", 
			"ONOMATOPOEIA", "TRANQUILITY", "SPHYNX", "ZEPHYR"};
	private static final Random GENERATOR = new Random();
	private static final int MAX_ERRORS = 8;
	private static ArrayList<Character> guessedLetters = new ArrayList<Character>();
	// The selected Hangman game word
	private static String selectedWord;
	private static final Scanner USER_INPUT = new Scanner(System.in);
	private static int incorrectGuesses;
	private static int numberOfGuesses;
	
	public static void main(String[] args) {
		
		char letterGuess;
		boolean wordGuessed;
		boolean playAgain;
		String getPlayAgainAnswer;
		boolean validResponse;
		
		
		// Welcome message
		System.out.println("Welcome to Hangman!");
		System.out.println("Created by staudts in July 2020.\n");
		
		// make a do/while loop if player wants to play again
		do {
			guessedLetters.clear();
			incorrectGuesses = 0;
			numberOfGuesses = 0;
			wordGuessed = false;
			playAgain = false;
			validResponse = false;	
			// Randomly selects word from the word list
			selectedWord = WORDS[GENERATOR.nextInt(WORDS.length)];
			
			
			
			while(incorrectGuesses < MAX_ERRORS) {
				System.out.print("Your word is: ");
				printWord();
				
				// Asks player for guess.
				letterGuess = guessLetter();
				
				// Checks if letter is in the word or not.
				if (selectedWord.indexOf(letterGuess) == -1) {
					incorrectGuesses++;
					printHangman();
				}
				
				// Checks if word is solved yet.
				if (checkWord()) {
					wordGuessed = true;
					break;
				}
	
				
				
				System.out.println("Incorrect guesses: " + incorrectGuesses); 
			
			}
	
			printWord();
			
			if (incorrectGuesses < 8 && wordGuessed)
				System.out.println("Congratulations! You are a winner!");
			else
				System.out.println("Uh oh. Better luck next time.");
			
			System.out.println("You made " + numberOfGuesses + " guesses.\n");
			
			// Asks if player wants to play again.
			while (!validResponse) {
				System.out.print("Play again? Y/N: ");
			
				getPlayAgainAnswer = USER_INPUT.next().toUpperCase();
			
				if (getPlayAgainAnswer.equals("Y")) {
					playAgain = true;
					validResponse = true;
					System.out.println();
				}
				else if (getPlayAgainAnswer.equals("N")) {
					playAgain = false;
					validResponse = true;
					System.out.println();
				}
				else {
					validResponse = false;
					System.out.println("Not a valid response. Try again.");
				}
			}	
			
		} while (playAgain);
		
		System.out.println("Fin.");
		
	}
	
	
	/**
	 * Prints the Hangman word that reflects the player's most recent guesses.
	 */
	public static void printWord() {
		if (guessedLetters.size() == 0) {
			for (int i = 0; i < selectedWord.length(); i++)
				System.out.print("* ");
		}
		
		else {
			for (int i = 0; i < selectedWord.length(); i++) {
				boolean letterPresent = false;
				for (int j = 0; j < guessedLetters.size(); j++) {
					if (guessedLetters.get(j) == selectedWord.charAt(i)) {
						System.out.print(guessedLetters.get(j) + " ");
						letterPresent = true;
						break;
					}					
				}
				if (letterPresent == false) System.out.print("* ");
			}
		}
		System.out.println("\n");
	}
	
	/**
	 * Asks user for letter till a valid, unique letter has been given, adds it the list of guessed letters, and returns the letter.
	 * @return A valid guessed letter
	 */
	public static char guessLetter() {
		char letterGuess; 
		
		do {
			letterGuess = getValidInput();	
			numberOfGuesses++;
		}
		while (hasLetterAlreadyBeenGuessed(letterGuess));  
		
		guessedLetters.add(letterGuess);
		
		return letterGuess;
			
	}
	
	/**
	 * Asks for player's guess till a valid input is given, i.e. an alphabet letter.
	 * @return a valid guess (an alphabet letter)
	 */
	public static char getValidInput() {
		char guessedLetter = '?';
		boolean invalidAnswer = true;
		
		while (invalidAnswer) {
			
			// Gets player's guess.
			System.out.print("Guess a letter: ");
			guessedLetter = USER_INPUT.next().toUpperCase().charAt(0); 	
			System.out.println();
			// Checks if player's guess is a letter.
			if (Character.isLetter(guessedLetter)) {
				invalidAnswer = false;		
			}
			else System.out.println("Not a letter. Try again.");
		}
		
		return guessedLetter;
	}
	
	/**
	 * Checks if a letter has already been guessed and prints list of guessed letters if so.
	 * @param letter The guessed letter to be checked
	 * @return Returns true if a letter has already been guessed; otherwise, returns false.
	 */
	public static boolean hasLetterAlreadyBeenGuessed(char letterGuess) {
		if (guessedLetters.contains(letterGuess)) {
			System.out.println("This letter has already been guessed. Try again.");
			System.out.print("Previously guessed letter(s): ");
			for (char letter: guessedLetters)
				System.out.print(letter + " ");
			System.out.println("\n");
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the selected Hangman word has been correctly guessed.
	 * @return True if the Hangman word has been correctly guessed; otherwise, returns false.
	 */
	public static boolean checkWord() {
		int lettersPresent = 0;
		
		for (int i = 0; i < selectedWord.length(); i++) {
			for (int j = 0; j < guessedLetters.size(); j++) {
				if (guessedLetters.get(j) == selectedWord.charAt(i)) {
					lettersPresent++;
					break;
				}					
			}
		}
		if (lettersPresent == selectedWord.length()) return true;
		return false;
	}
	
	/**
	 * Prints the Hangman based on how many incorrect guesses given by player.
	 */
	public static void printHangman() {
		switch (incorrectGuesses) {
		case 1:	System.out.println(" ____________");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||              ");
				System.out.println(" ||       ( x  x )");
				System.out.println(" ||           3");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println("—————");
				break;
		case 2: System.out.println(" ____________");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||              ");
				System.out.println(" ||       ( x  x )");
				System.out.println(" ||           3");
				System.out.println(" ||          ||");
				System.out.println(" ||          ||");
				System.out.println(" ||          ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println("—————");
				break;
		case 3: System.out.println(" ____________");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||              ");
				System.out.println(" ||       ( x  x )");
				System.out.println(" ||           3");
				System.out.println(" ||         /||");
				System.out.println(" ||        / ||");
				System.out.println(" ||          ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println("—————");
				break;
		case 4: System.out.println(" ____________");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||              ");
				System.out.println(" ||       ( x  x )");
				System.out.println(" ||           3");
				System.out.println(" ||         /||\\");
				System.out.println(" ||        / || \\");
				System.out.println(" ||          ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println("—————");
				break;
		case 5: System.out.println(" ____________");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||              ");
				System.out.println(" ||       ( x  x )");
				System.out.println(" ||           3");
				System.out.println(" ||         /||\\");
				System.out.println(" ||        / || \\");
				System.out.println(" ||          ||");
				System.out.println(" ||          /");
				System.out.println(" ||         /");
				System.out.println(" ||");
				System.out.println("—————");
				break;
		case 6: System.out.println(" ____________");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||          |");
				System.out.println(" ||              ");
				System.out.println(" ||       ( x  x )");
				System.out.println(" ||           3");
				System.out.println(" ||         /||\\");
				System.out.println(" ||        / || \\");
				System.out.println(" ||          ||");
				System.out.println(" ||          /\\");
				System.out.println(" ||         /  \\");
				System.out.println(" ||");
				System.out.println("—————");
				break;
		case 7: System.out.println(" ____________");
				System.out.println(" ||          |");
				System.out.println(" ||         ____");
				System.out.println(" ||        |    |");
				System.out.println(" ||       ———————— ");
				System.out.println(" ||       ( x  x )");
				System.out.println(" ||           3");
				System.out.println(" ||          ||");
				System.out.println(" ||         /||\\");
				System.out.println(" ||        / || \\");
				System.out.println(" ||          /\\");
				System.out.println(" ||         /  \\");
				System.out.println(" ||");
				System.out.println(" ||");
				System.out.println("—————");
				break;
		case 8: System.out.println(" ____________");
				System.out.println(" ||          |");
				System.out.println(" ||         ____");
				System.out.println(" ||        |    |");
				System.out.println(" ||       ———————— ");
				System.out.println(" ||       ( x  x )");
				System.out.println(" ||           3");
				System.out.println(" ||          ||");
				System.out.println(" ||         /||\\");
				System.out.println(" ||        / || \\");
				System.out.println(" ||          /\\ /\\");
				System.out.println(" ||         /  \\ |");
				System.out.println(" ||              |");
				System.out.println(" ||              |");
				System.out.println("—————");
				break;
		default: break;
		}
	}
	
	
}
