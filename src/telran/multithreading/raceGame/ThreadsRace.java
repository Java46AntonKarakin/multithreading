package telran.multithreading.raceGame;

import java.util.Random;
import java.util.Scanner;

import telran.multithreading.menu.Game;

public class ThreadsRace implements Game {

	private static String gameName = "ThreadRacingGame";

	private static final int MIN_SLEEP = 2;
	private static final int MAX_SLEEP = 5;

	private static final int MIN_DISTANCE = 100;
	private static final int MAX_DISTANCE = 3500;

	private static final int MIN_PARTICIPANTS = 3;
	private static final int MAX_PARTICIPANTS = 10;

	private int participants;
	private int distance;
	static boolean flWinner;

	public ThreadsRace() {
		flWinner = false;
	}

	@Override
	public void launchGame(Scanner scanner) {
		getParameters(scanner);
		int[] sleeps = new Random().ints(MIN_SLEEP, MAX_SLEEP).limit(distance).toArray();
		for (int i = 0; i < participants; i++) {
			new Thread(new Runner(sleeps), "" + (i + 1)).start();
		}
	}

	private void getParameters(Scanner scanner) {
		try {
			System.out.printf("Enter number of threads [%d - %d]:\n", MIN_PARTICIPANTS, MAX_PARTICIPANTS);
			participants = scanner.nextInt();
			checkInput(participants, MIN_PARTICIPANTS, MAX_PARTICIPANTS);
			System.out.printf("Enter length of the distance [%d - %d]:\n", MIN_DISTANCE, MAX_DISTANCE);
			distance = scanner.nextInt();
			checkInput(distance, MIN_DISTANCE, MAX_DISTANCE);
		} catch (Exception ex) {
			System.out.printf("RaceGame -> getParameters -> \n%s\n", ex.getMessage());
			System.exit(0);
		}
	}

	@Override
	public String getGameName() {
		return gameName;
	}

	private void checkInput(int value, int min, int max) throws Exception {
		if (value < min || value > max) {
			throw new IllegalArgumentException(String.format(
					"\"%d\" is wrong. Should be in range [%d - %d];\nWhy, Mr. Anderson? Why, why?\n", value, min, max));
		}
	}
}
