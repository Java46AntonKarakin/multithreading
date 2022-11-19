package telran.multithreading.menu;

import java.util.*;

public class SimpleMenu {

	public SimpleMenu(Game... games) {
		this.games = new ArrayList<>(Arrays.asList(games));
	}

	private ArrayList<Game> games = new ArrayList<>();
	final Scanner scanner = new Scanner(System.in);

	public void launchMenu() {
		try {
			int insertedValue = getInputValue();

			if (insertedValue < 0 || insertedValue > games.size() + 1) {
				throw new IllegalArgumentException(String.format("\"%d\" is wrong, should be in range [1 - %d]",
						insertedValue, (games.size() + 1)));
			}
			if (insertedValue == games.size() + 1) {
				System.out.println("\nYou didn't even try...\n");
				return;
			}

			System.out.printf("*".repeat(20) + "\n%s\n" + "*".repeat(20) + "\n",
					games.get(insertedValue - 1).getGameName());
			games.get(insertedValue - 1).launchGame();
		} catch (IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			launchMenu();
		} catch (InputMismatchException ex) {
			System.out.println("Enter number of the game\n");
			launchMenu();
		}
	}

	private int getInputValue() throws InputMismatchException {
		System.out.println(String.format("*".repeat(20)));
		System.out.println(
				String.format("Wellcome to \"5000 games in 1\"\nSelect game from the list below\n", games.size() + 1));
		System.out.println(String.format("*".repeat(20)));
		for (int i = 0; i < games.size() + 1; i++) {
			if (i == games.size()) {
				System.out.println(String.format("%d - Exit;", games.size() + 1));
			} else {
				System.out.printf("%d - %s;\n", i + 1, games.get(i).getGameName());
			}
		}
		return scanner.nextInt();

	}
}
