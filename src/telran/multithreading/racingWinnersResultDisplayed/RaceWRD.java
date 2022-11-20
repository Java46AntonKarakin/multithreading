package telran.multithreading.racingWinnersResultDisplayed;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import telran.multithreading.menu.Game;

public class RaceWRD implements Game {

	private static String gameName = "Thread Racing Game v1.0";
	private static final int MIN_SLEEP = 2;
	private static final int MAX_SLEEP = 5;
	private static final int MIN_DISTANCE = 100;
	private static final int MAX_DISTANCE = 3500;
	private static final int MIN_PARTICIPANTS = 3;
	private static final int MAX_PARTICIPANTS = 10;
	private static final String enterPrtNumber = "Enter number of threads [%d - %d]:\n";
	private static final String enterDstLength = "Enter length of the distance [%d - %d]:\n";
	private static final String CONGS_TO_WINNER = "Thread#%s is a winner with result %dms;";
	private int participants;
	private int[] distance = new int[1];
	private boolean flWinner;
	Scanner scanner = new Scanner(System.in);
	private static Object mutex = new Object();

	@Override
	public void launchGame() {
		flWinner = false;
		
		try {
			participants = getInputValue(scanner, enterPrtNumber, MIN_PARTICIPANTS, MAX_PARTICIPANTS);
			distance[0] = getInputValue(scanner, enterDstLength, MIN_DISTANCE, MAX_DISTANCE);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			launchGame();
		}
		ThreadGroup group = new ThreadGroup("racers");
		for (int i = 0; i < participants; i++) {
			new Thread(group, () -> {
				try {
					Thread.currentThread().join();
				} catch (InterruptedException e1) {
					LocalDateTime start = LocalDateTime.now();
					while (distance[0]-- > 0) {
						try {
							Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_SLEEP, MAX_SLEEP));
						} catch (InterruptedException e) {
							System.out.printf("Thread %s\n", Thread.currentThread().getName());
							e.printStackTrace();
						}
					}
					becomeWinner(Thread.currentThread().getName(),
							ChronoUnit.MILLIS.between(start, LocalDateTime.now()));
				}

			}, "" + (i + 1)).start();
		}
		group.interrupt();
	}

	@Override
	public String getGameName() {
		return gameName;
	}

	private void becomeWinner(String name, long result) {
		synchronized (mutex) {
			if (!flWinner) {
				flWinner = true;
				System.out.printf(CONGS_TO_WINNER, name, result);
			}
		}
	}
}
