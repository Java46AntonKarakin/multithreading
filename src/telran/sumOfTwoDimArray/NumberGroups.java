package telran.sumOfTwoDimArray;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class NumberGroups {

	private int[][] groups;
	private int nThreads;
	private AtomicLong sum = new AtomicLong(0);

	public NumberGroups(int[][] groups, int nThreads) {
		this.groups = groups;
		this.nThreads = nThreads;
	}

	public NumberGroups(int[][] groups) {
		this(groups, 4);
	}

	public long computeSum(){
		ExecutorService service = Executors.newFixedThreadPool(nThreads);
		var start = Instant.now();
		IntStream.range(0, groups.length).forEach(i -> {
			Future<Long> res = service.submit(new OneGroupSum(groups[i]));
			try {
				sum.addAndGet(res.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		service.shutdown();
		System.out.printf("running time = %s\n" , ChronoUnit.MILLIS.between(start, Instant.now()));
		return sum.get();
	}

	public final int getnThreads() {
		return nThreads;
	}

	public final void setnThreads(int nThreads) {
		this.nThreads = nThreads;
	}
}
