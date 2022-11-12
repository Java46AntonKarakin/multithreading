package telran.multithreading.appl;

import telran.multithreading.menu.SimpleMenu;
import telran.multithreading.raceGame.RaceGame;

public class RacingGameAppl {
	

	public static void main(String[] args) {
		new SimpleMenu(new RaceGame()).launchMenu();
	}
}
