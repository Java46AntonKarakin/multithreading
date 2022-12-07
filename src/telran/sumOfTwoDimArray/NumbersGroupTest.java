package telran.sumOfTwoDimArray;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class NumbersGroupTest {
	
	private static final int MIN_NUMBER = 0;
	private static final int MAX_NUMBER = 100;
	private static int nGroupsFuncTest = 10;
	private static int nNumbersInGroupFuncTest = 5;
	private static int nGroupsPerfTest = 15_000;
	private static int nNumbersInGroupPerfTest = 15_000;

	public static void main(String[] args) {
//		functionalTest();
		performanceTest();
	}

	static void functionalTest() {
		int[][] source = getGroups(nGroupsFuncTest,nNumbersInGroupFuncTest);
		NumberGroups ng = new NumberGroups (source);
		System.out.println(ng.computeSum());
	}

	static void performanceTest() {
		int[][] source = getGroups(nGroupsPerfTest,nNumbersInGroupPerfTest);
		NumberGroups ng = new NumberGroups (source);
		System.out.println(ng.computeSum());
	}

	static int[][] getGroups(int nGroups, int nNumbersInGroup) {
		int [] putNumber = {1};
		int [][]res = new int [nGroups][nNumbersInGroup];
		IntStream.range(0, nGroups).forEach(x-> {
			IntStream.range(0, nNumbersInGroup).forEach(y-> {
//				res[x][y] = putNumber[0]++;
				res[x][y] = ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER);
			});
		});
	return res;
	}

}
