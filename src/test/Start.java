/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package test;

import gui.GUI;
import io.Input;

/**
 * The Class Start.
 */
public class Start {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	@SuppressWarnings("unused")
	public static void main(final String[] args) {
		System.out.println("Running..");
		final Input input = new Input();
		// Threaded to allow GUI to work separately to Watcher.
		final Runnable runnable = new Watcher();
		// if run before, read in existing CSV file, and update that.
		if (input.isExists()) {
			((Watcher) runnable).setWindows(input.readCSV());
		}
		final Thread telescope = new Thread(runnable, "telescope");
		telescope.start();

		new GUI(runnable, telescope);

	}
}
