package telran.multithreading.raceGame;

public class Runner implements Runnable {
	public Runner(int[] sleeps) {
		this.sleeps = sleeps;
	}

	private int[] sleeps;

	@Override
	public void run() {
		for (int i = 0; i < sleeps.length; i++) {
			try {
				if (ThreadsRace.flWinner) {
					return;
				}
				System.out.printf("Thread#%s is running very fast;\n", Thread.currentThread().getName());
				Thread.sleep(sleeps[i]);
			} catch (InterruptedException e) {
				System.out.println("Thread has been interrupted");
			}
		}
		
		if (!ThreadsRace.flWinner) {
			ThreadsRace.flWinner = true;
			System.out.println(String.format("Congratulations to thread #%s;", Thread.currentThread().getName()));
		}
	}
}
