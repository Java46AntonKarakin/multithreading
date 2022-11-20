package telran.multithreading.menu;

import java.util.Scanner;

public interface Game {
	void launchGame();

	String getGameName();

	default int getInputValue(Scanner scanner, String type, int minValue, int maxValue)
			throws IllegalArgumentException {
		System.out.printf(type, minValue, maxValue);
		int res = scanner.nextInt();
		checkInputValue(res, minValue, maxValue);
		return res;
	}

	private void checkInputValue(int value, int min, int max) throws IllegalArgumentException {
		if (value < min || value > max) {
			throw new IllegalArgumentException(
					String.format("\"%d\" is wrong number. Should be in range [%d - %d]\n", value, min, max));
		}
	}
}
