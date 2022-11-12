package telran.multithreading;

public class Printer extends Thread {

	private int N_PRINTS;
	private char SYMB;
	
	public Printer(int N_PRINTS, char SYMB) {
		this.N_PRINTS = N_PRINTS;
		this.SYMB = SYMB;
	}


	@Override
	public void run() {
		for (int i = 0; i < N_PRINTS; i++) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.print(" "+SYMB);
		}
	}
}
