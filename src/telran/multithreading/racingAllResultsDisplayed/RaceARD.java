package telran.multithreading.racingAllResultsDisplayed;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import telran.multithreading.menu.Game;

public class RaceARD implements Game {
	private static String gameName = "Thread Racing Game v2.0 (show all results)";
	private static final int MIN_SLEEP = 2;
	private static final int MAX_SLEEP = 5;
	private static final int MIN_DISTANCE = 100;
	private static final int MAX_DISTANCE = 3500;
	private static final int MIN_PARTICIPANTS = 3;
	private static final int MAX_PARTICIPANTS = 10;
	private static final String RESULT_FORM = "  %s    |    %s    |     %s\n";
	private static final String RESULT_HEADER = "place  | time, ms  |  thread №";
	private static final String enterPrtNumber = "Enter number of threads [%d - %d]:\n";
	private static final String enterDstLength = "Enter length of the distance [%d - %d]:\n";
	private int participants;
	private int distance;
	private TreeMap<Long, String> results = new TreeMap<>();
	private static final Object mutex = new Object();


	Scanner scanner = new Scanner(System.in);
	ThreadGroup group = new ThreadGroup("racers");

	@Override
	public void launchGame() {
		try {
			participants = getInputValue(scanner, enterPrtNumber, MIN_PARTICIPANTS, MAX_PARTICIPANTS);
			distance = getInputValue(scanner, enterDstLength, MIN_DISTANCE, MAX_DISTANCE);
			for (int i = 0; i < participants; i++) {
				createNewRacer(i, group).start();
			}
			group.interrupt();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			launchGame();
		}
	}

	private Thread createNewRacer(int currIndex, ThreadGroup group) {
		return new Thread(group, () -> {
			try {
				Thread.currentThread().join();
			} catch (InterruptedException e1) {
				LocalDateTime start = LocalDateTime.now();
				for (int i = 0; i < distance; i++) {
					try {
						Thread.sleep(ThreadLocalRandom.current().nextInt(MIN_SLEEP, MAX_SLEEP + 1));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				handleResult(ChronoUnit.MILLIS.between(start, LocalDateTime.now()), Thread.currentThread().getName());
			}
		}, "" + (currIndex + 1));
	}

	@Override
	public String getGameName() {
		return gameName;
	}

	private void handleResult(long result, String threadName) {
		synchronized (mutex) {
			results.merge(result, threadName, (v1, v2) -> {
				return v1 + ", " + v2;
			});
		}
		if (--participants == 0) {
			printResults();
			results.clear();
		}
	}

	private void printResults() {
		System.out.println(RESULT_HEADER);
		int[] idx = { 1 };
		results.entrySet().stream().forEach(e -> {
			System.out.printf(RESULT_FORM, idx[0]++, e.getKey(), e.getValue());
		});
	}
}