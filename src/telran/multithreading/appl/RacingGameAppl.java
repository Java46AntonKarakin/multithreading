package telran.multithreading.appl;

import telran.multithreading.menu.SimpleMenu;
import telran.multithreading.racingAllResultsDisplayed.RaceARD;
import telran.multithreading.racingWinnersResultDisplayed.RaceWRD;

public class RacingGameAppl {
	

	public static void main(String[] args) {
		new SimpleMenu(new RaceWRD(), new RaceARD()).launchMenu();
	}
}
