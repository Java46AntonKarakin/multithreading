package telran.multithreading.menu;

import java.util.*;

public class SimpleMenu {
	private static final String CHOSEN_GAME = "*".repeat(20) + "\n%s\n" + "*".repeat(20) + "\n";
	private static final String WELCOME_USER = "Wellcome to \"5000 games in 1\"\nSelect game from the list below";
	private static final String SPACER = "*".repeat(20);
	private static final String AFTER_GAME = "Whats next?";
	private static final String GOODBYE = "\nSee ya!\n";
	private static final String WRONG_INPUT = "\"%d\" is wrong, should be in range [1 - %d]";

	public SimpleMenu(Game... games) {
		this.games = new ArrayList<>(Arrays.asList(games));
	}

	private ArrayList<Game> games = new ArrayList<>();
	final Scanner scanner = new Scanner(System.in);

	public void launchMenu() {
		try {
			int insertedValue = getInputValue();
			checkInputValue(insertedValue, games.size() + 1);
			if (insertedValue == games.size() + 1) {
				System.out.println(GOODBYE);
			} else {
				launchSelectedGame(insertedValue);
				launchAfterMenu(insertedValue);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			launchMenu();
		}
	}

	private void launchSelectedGame(int insertedValue) throws InterruptedException {
		System.out.printf(CHOSEN_GAME,games.get(insertedValue - 1).getGameName());
		Thread gameThread = new Thread(() -> {
			games.get(insertedValue - 1).launchGame();
		});
		gameThread.start();
		gameThread.join();
		Thread.sleep(1000);
	}

	private void checkInputValue(int insertedValue, int maxSize) {
		if (insertedValue < 1 || insertedValue > maxSize) {
			throw new IllegalArgumentException(
					String.format(WRONG_INPUT, insertedValue, (games.size() + 1)));
		}
	}

	private void launchAfterMenu(int gameIndex) throws Exception {
		printHeader(new String[] { "\n", SPACER, AFTER_GAME, SPACER, });
		System.out.println("1 - Repeat game;");
		System.out.println("2 - Back to main menu;");
		System.out.println("3 - Exit;");
		int input = scanner.nextInt();
		checkInputValue(input, 3);
		if (input == 1) {
			launchSelectedGame(gameIndex);
			launchAfterMenu(gameIndex);
		} else if (input == 2) {
			launchMenu();
		} else {
		System.out.println(GOODBYE);
		}
	}

	private int getInputValue() throws InputMismatchException {
		printHeader(new String[] { "\n", SPACER, WELCOME_USER, "\n", SPACER });
		System.out.println(" ".repeat(7) + "MAIN MENU");
		showMenuOptions(games.size());
		return scanner.nextInt();
	}

	private void showMenuOptions(int maxSize) {
		for (int i = 0; i < maxSize + 1; i++) {
			if (i == maxSize) {
				System.out.println(String.format("%d - Exit;", maxSize + 1));
			} else {
				System.out.printf("%d - %s;\n", i + 1, games.get(i).getGameName());
			}
		}
	}

	private void printHeader(String[] printable) {
		for (String s : printable) {
			System.out.println(s);
		}
	}
}
