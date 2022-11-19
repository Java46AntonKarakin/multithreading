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
	private int participants;
	private int distance;
	static boolean flWinner = false;
	Scanner scanner = new Scanner(System.in);
	private static Object mutex = new Object();

	@Override
	public void launchGame() {
		participants = getInputValue(scanner, enterPrtNumber, MIN_PARTICIPANTS, MAX_PARTICIPANTS);
		distance = getInputValue(scanner, enterDstLength, MIN_DISTANCE, MAX_DISTANCE);
		ThreadGroup group = new ThreadGroup("racers");
		for (int i = 0; i < participants; i++) {
			var a = new Thread(group, new Racer(), "" + (i + 1));
			a.start();
		}
		group.interrupt();
	}

	@Override
	public String getGameName() {
		return gameName;
	}

	private void becomeWinner(String name, long result) {
		synchronized (mutex) {
			if (flWinner) {
				return;
			}
			flWinner = true;
			System.out.printf("Thread#%s is a winner with result %dms;", name, result);
		}
	}

	class Racer implements Runnable {
		@Override
		public void run() {
			try {
				Thread.currentThread().join();
			} catch (InterruptedException e1) {
				LocalDateTime start = LocalDateTime.now();
				for (int i = 0; i < distance; i++) {
					try {
						Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_SLEEP, MAX_SLEEP));
					} catch (InterruptedException e) {
						System.out.println("Thread has been interrupted");
					}
				}
				becomeWinner(Thread.currentThread().getName(), ChronoUnit.MILLIS.between(start, LocalDateTime.now()));
			}

		}
	}
}
