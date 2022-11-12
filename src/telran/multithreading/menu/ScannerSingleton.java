package telran.multithreading.menu;

import java.util.Scanner;

public class ScannerSingleton {
	
	private static Scanner scanner = new Scanner(System.in);
	
	private ScannerSingleton () {
	}
	
	public static Scanner getScanner () {
		return scanner;
	}

}
