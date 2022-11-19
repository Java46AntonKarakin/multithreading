package telran.multithreading.racingAllResultsDisplayed;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import telran.multithreading.menu.Game;

public class RaceARD implements Game {
	private static String gameName = "Thread Racing Game v2.0";
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
	private TreeMap<Long, String> results = new TreeMap<>();
	private static final Object mutex = new Object();
	Scanner scanner = new Scanner(System.in);

	@Override
	public void launchGame() {
		try {
			// ask user to put number of the racers and length of the distance
			participants = getInputValue(scanner, enterPrtNumber, MIN_PARTICIPANTS, MAX_PARTICIPANTS);
			distance = getInputValue(scanner, enterDstLength, MIN_DISTANCE, MAX_DISTANCE);

			// create threads (i.e. racers)
			ThreadGroup group = new ThreadGroup("racers");
			for (int i = 0; i < participants; i++) {
				getRacer(i, group).start();
			}
			group.interrupt();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			launchGame();
		}
	}

	// creating racers
	private Thread getRacer(int currIndex, ThreadGroup group) {
		return new Thread(group, () -> {
			try {
//				System.out.printf("Thread#%s joined at %s\n", Thread.currentThread().getName(),
//						LocalDateTime.now().format(DateTimeFormatter.ofPattern("H:mm:s.n")));
				Thread.currentThread().join();
			} catch (InterruptedException e1) {
//				System.out.printf("Thread#%s interruptet at %s\n", Thread.currentThread().getName(),
//						LocalDateTime.now().format(DateTimeFormatter.ofPattern("H:mm:s.n")));
				LocalDateTime start = LocalDateTime.now();
				for (int i = 0; i < distance; i++) {
					try {
						Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_SLEEP, MAX_SLEEP + 1));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
//				System.out.printf("Thread#%s finished at %s\n", Thread.currentThread().getName(),
//						LocalDateTime.now().format(DateTimeFormatter.ofPattern("H:mm:s.n")));
				handleResult(ChronoUnit.MILLIS.between(start, LocalDateTime.now()), Thread.currentThread().getName());
			}
		}, "" + (currIndex + 1));
	}

	@Override
	public String getGameName() {
		return gameName;
	}

	// each racer puts his summary (it's name and the time between start and finish)
	// to the summary table (TreeMap)
	// racers with the same time will share the place in the summary table
	private void handleResult(long result, String threadName) {
		synchronized (mutex) {
			results.merge(result, threadName, (v1, v2) -> {
				return v1 + ", " + v2;
			});
		}
		if (--participants == 0) {
			printResults();
		}
	}

	private void printResults() {
		System.out.println("place  | time, ms  |  thread №");
		int[] idx = { 1 };
		results.entrySet().stream().forEach(e -> {
			System.out.printf("  %s    |    %s    |     %s\n", idx[0]++, e.getKey(), e.getValue());
		});
	}
}