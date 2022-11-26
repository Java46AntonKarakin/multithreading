package telran.multithreading.ferm;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Truck extends Thread {
	private int load;
	private int nLoads;
	private static long elevator1;
	private static long elevator2;
	private static final Lock lock1 = new ReentrantLock(true);
	private static final Lock lock2 = new ReentrantLock(true);
	private static AtomicInteger waitingCounter = new AtomicInteger(0);

	public Truck(int load, int nLoads) {
		this.load = load;
		this.nLoads = nLoads;
	}

	@Override
	public void run() {
		for (int i = 0; i < nLoads; i++) {
			/*
			 * to run 'loadElevator1' with different lock object insert 'lock2' as a second
			 * argument
			 */
			loadElevator1(load, lock2);
			loadElevator2(load);
		}
	}

	private static void loadElevator2(int load) {
		boolean counterIncreased = false;
		while (!lock1.tryLock()) {
			if (!counterIncreased) {
				counterIncreased = true;
				waitingCounter.incrementAndGet();
			}
//			waitingCounter.incrementAndGet();
		}
		try {
			elevator2 += load;
		} finally {
			lock1.unlock();
		}
	}

	static private void loadElevator1(int load, Lock lock) {
		boolean counterIncreased = false;
		while (!lock.tryLock()) {
			if (!counterIncreased) {
				counterIncreased = true;
				waitingCounter.incrementAndGet();
			}
//			waitingCounter.incrementAndGet();
		}
		try {
			elevator1 += load;
		} finally {
			lock.unlock();
		}
	}

	public static long getElevator1() {
		return elevator1;
	}

	public static long getElevator2() {
		return elevator2;
	}

	public static int getWaitingCounter() {
		return waitingCounter.get();
	}

}
