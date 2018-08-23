/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import dataObjects.Window;

/**
 * The Class Output.
 */
public class Output {

	/**
	 * Save CSV.
	 *
	 * @param windows
	 *            the windows
	 */
	public void saveCSV(final List<Window> windows) {
		try {
			final PrintWriter out = new PrintWriter("output.csv");
			out.println("Title,Uptime");
			for (final Window window : windows) {
				final String title = window.getTitle();
				if (title.equals("")) {
					continue;
				}
				final String uptime = "" + window.getUptimeInSeconds();
				out.println(title + "," + uptime);
			}
			out.close();
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
