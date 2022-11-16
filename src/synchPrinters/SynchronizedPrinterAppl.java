package synchPrinters;

public class SynchronizedPrinterAppl {
	private static final int N_PRINTERS = 3;
	private static final int N_SYMB_IN_LINE = 5;
	private static final int N_SYMB_TOTAL = 20;

	public static void main(String[] args) {
		SynchronizedPrinterManager spm = new SynchronizedPrinterManager(N_PRINTERS, N_SYMB_IN_LINE, N_SYMB_TOTAL);
		spm.launch();
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

	private void checkInputParameters() {
		// TODO Auto-generated method stub
	}

	private PrinterThread[] fillArrayWithChainedPrinters(PrinterThread[] arrOfPrtrs, int iterations,
			int N_SYMB_IN_LINE) {
		for (int i = 0; i < arrOfPrtrs.length; i++) {
			arrOfPrtrs[i] = new PrinterThread(N_SYMB_IN_LINE, iterations, null, i);
			if (i > 0) {
				arrOfPrtrs[i - 1].setNextPrinter(arrOfPrtrs[i]);
			}
		}
		arrOfPrtrs[arrOfPrtrs.length - 1].setNextPrinter(arrOfPrtrs[0]);
		return arrOfPrtrs;
	}

	public void launch() {
		try {
			checkInputParameters();
			PrinterThread[] ArrOfPrinters = fillArrayWithChainedPrinters(new PrinterThread[N_PRINTERS],
					N_SYMB_TOTAL / N_SYMB_IN_LINE, N_SYMB_IN_LINE);
			for (var p : ArrOfPrinters) {
				p.start();
				p.putToSleep(1000);
			}
			System.out.println("something");
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

	private void printMessage() {
		System.out.println(Thread.currentThread().getName().repeat(N_SYMB_IN_LINE) + ";");
	}
	
	public void putToSleep(int sleepTime) throws InterruptedException {
		sleep(sleepTime);
	}
	
	public void run() {
		try {
			sleep(1000);
			while (iterations-- > 0) {
				printMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}