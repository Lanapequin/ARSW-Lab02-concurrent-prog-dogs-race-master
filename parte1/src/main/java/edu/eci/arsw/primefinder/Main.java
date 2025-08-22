package edu.eci.arsw.primefinder;


import java.time.Duration;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lepeanutbutter. lanapequin
 * @version August 22, 2025.
 */
public class Main {

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Main.class.getName());

		PrimeFinderThread firstThread=new PrimeFinderThread(0, 10000000);
		PrimeFinderThread secondThread=new PrimeFinderThread(10000001, 20000000);
		PrimeFinderThread thirdThread=new PrimeFinderThread(20000001, 30000000);

		LocalTime startTime = LocalTime.now();

		firstThread.start();
		secondThread.start();
		thirdThread.start();

		try {
			firstThread.join();
			secondThread.join();
			thirdThread.join();
		} catch (InterruptedException e) {
			logger.log(Level.WARNING, "Interrupted", e);
			Thread.currentThread().interrupt();
		}

		LocalTime endTime = LocalTime.now();

		Duration duration = Duration.between(startTime, endTime);
		logger.log(Level.INFO, "Total execution time: {0} milliseconds", duration.toMillis());
	}
}
