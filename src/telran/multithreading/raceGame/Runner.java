package telran.multithreading.raceGame;

public class Runner extends Thread {
	public Runner(int[] sleeps) {
		this.sleeps = sleeps;
	}

	private int[] sleeps;

	@Override
	public void run() {
		for (int i = 0; i < sleeps.length; i++) {
			try {
				if (RaceGame.flWinner) {
					return;
				}
				System.out.printf("Thread#%s is running very fast;\n", Thread.currentThread().getName());
				Thread.sleep(sleeps[i]);
			} catch (InterruptedException e) {
				System.out.println("Thread has been interrupted");
			}
		}
		if (!RaceGame.flWinner) {
			RaceGame.flWinner = true;
			System.out.println(String.format("Congratulations to thread #%s;", Thread.currentThread().getName()));
		}
	}
}
