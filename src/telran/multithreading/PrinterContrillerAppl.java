package telran.multithreading;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PrinterContrillerAppl {
	public static void main(String[] args) throws InterruptedException {
		Printer pr1 = new Printer(10, '*');
		Printer pr2 = new Printer(10, '$');
		Instant start = Instant.now();
		pr1.start();
		pr2.start();
		pr1.join();
		pr2.join();
		System.out.printf("\nrun time = %d;\n", ChronoUnit.MILLIS.between(start, Instant.now()));
	}
}
