package telran.multithreading.menu;

import java.util.*;

public class SimpleMenu {

	public SimpleMenu(Game... games) {
		this.games = new ArrayList<>(Arrays.asList(games));
	}

	private ArrayList<Game> games = new ArrayList<>();
	Scanner scanner = new Scanner(System.in);

	public void launchMenu() {
		try {
			int insertedValue = getInputValue();

			if (insertedValue == games.size() + 1) {
				System.out.println("\nThanks for playing!\nSee ya");
				return;
			}

			System.out.printf("*".repeat(20) + "\nLet the %s begins!\n" + "*".repeat(20) + "\n",
					games.get(insertedValue - 1).getGameName());
			games.get(insertedValue - 1).launchGame(scanner);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private int getInputValue() throws Exception {
		for (int i = 0; i < games.size() + 1; i++) {
			if (i == games.size()) {
				System.out.println(String.format("%d - Exit;", games.size() + 1));
			} else {
				System.out.printf("%d - launch %s;\n", i + 1, games.get(i).getGameName());
			}
		}
		return scanner.nextInt();

	}
}
