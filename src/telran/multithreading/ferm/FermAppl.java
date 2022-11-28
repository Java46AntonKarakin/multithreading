package telran.multithreading.ferm;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.IntStream;

public class FermAppl {

	private static final int N_TRUCKS = 100;
	private static final int N_LOADS = 500;
	private static final int N_RUNS = 1000;
	private static long waitingCounter = 0;
	private static int runningTime = 0;

	public static void main(String[] args) {
		IntStream.range(0, N_RUNS).forEach(x -> runTrucks());

		System.out.printf("Report: elevator1 = %d; elevator2 = %d; runTime = %d; waitingCounter= %d;\n",
				Truck.getElevator1(), Truck.getElevator2(), runningTime / N_RUNS, waitingCounter / N_RUNS);

	}

	private static void runTrucks() {
		Truck[] trucks = new Truck[N_TRUCKS];
		Instant start = Instant.now();
		startTrucks(trucks);
		waitigForFinishing(trucks);
		waitingCounter += Truck.getWaitingCounter();
		runningTime += ChronoUnit.MILLIS.between(start, Instant.now());

	}

	private static void waitigForFinishing(Truck[] trucks) {
		Arrays.stream(trucks).forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

	}

	private static void startTrucks(Truck[] trucks) {
		IntStream.range(0, trucks.length).forEach(i -> {
			trucks[i] = new Truck(1, N_LOADS);
			trucks[i].start();
		});

	}

}
