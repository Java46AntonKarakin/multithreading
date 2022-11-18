package synchPrinters;

public class SynchronizedPrinterAppl {
	private static final int N_PRINTERS = 3;
	private static final int N_SYMB_IN_LINE = 5;
	private static final int N_SYMB_TOTAL = 15;

	public static void main(String[] args) {
		SynchronizedPrinterManager spm = new SynchronizedPrinterManager(N_PRINTERS, N_SYMB_IN_LINE, N_SYMB_TOTAL);
		spm.launchPrinters();
	}
}

class SynchronizedPrinterManager {

	private final int N_PRINTERS;
	private final int N_SYMB_IN_LINE;
	private final int N_SYMB_TOTAL;

	public SynchronizedPrinterManager(int N_PRINTERS, int N_SYMB_IN_LINE, int N_SYMB_TOTAL) {
		this.N_PRINTERS = N_PRINTERS;
		this.N_SYMB_IN_LINE = N_SYMB_IN_LINE;
		this.N_SYMB_TOTAL = N_SYMB_TOTAL;
	}

	private PrinterThread[] fillArrayWithChainedPrinters(PrinterThread[] arrOfPrtrs) {
		int iterations = N_SYMB_TOTAL / N_SYMB_IN_LINE;
		arrOfPrtrs[0] = new PrinterThread(N_SYMB_IN_LINE, iterations, null, 0);
		for (int i = 1; i < N_PRINTERS; i++) {
			arrOfPrtrs[i] = new PrinterThread(N_SYMB_IN_LINE, iterations, null, i);
			arrOfPrtrs[i - 1].setNextPrinter(arrOfPrtrs[i]);
		}
		arrOfPrtrs[arrOfPrtrs.length - 1].setNextPrinter(arrOfPrtrs[0]);
		return arrOfPrtrs;
	}

	public void launchPrinters() {
		try {
			PrinterThread[] arrOfPrinters = fillArrayWithChainedPrinters(new PrinterThread[N_PRINTERS]);
			for (var p : arrOfPrinters) {
				p.start();
			}
			arrOfPrinters[0].interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class PrinterThread extends Thread {
	PrinterThread nextPrinter;
	private int N_SYMB_IN_LINE;
	private int iterations;

	public PrinterThread(int N_SYMB_IN_LINE, int iterations, PrinterThread nextPrinter, int name) {
		super(name + "");
		this.nextPrinter = nextPrinter;
		this.iterations = iterations;
		this.N_SYMB_IN_LINE = N_SYMB_IN_LINE;
	}

	public void setNextPrinter(PrinterThread nextPrinter) {
		this.nextPrinter = nextPrinter;
	}

	public void run() {
		while (iterations > 0) {
			try {
				join();
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName().repeat(N_SYMB_IN_LINE) + ";");
				nextPrinter.interrupt();
				iterations--;
			}
		}
	}
}