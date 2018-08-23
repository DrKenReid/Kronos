/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import dataObjects.Window;

/**
 * The Class Input.
 */
public class Input {

	/**
	 * Checks if CSV file exists from previous runs.
	 *
	 * @return true, if is exists
	 */
	public boolean isExists() {
		final File f = new File("output.csv");
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}

	/**
	 * Read CSV.
	 *
	 * @return the list
	 */
	public List<Window> readCSV() {
		final List<Window> windows = new ArrayList<>();
		try {
			final File file = new File("output.csv");

			final BufferedReader br = new BufferedReader(new FileReader(file));

			String line;
			// ignore first line in CSV, headers are unimportant.
			br.readLine();
			while ((line = br.readLine()) != null) {
				final String[] pieces = line.split(",");
				// these ints are to prevent a title of a window having commas disrupting
				// reading the CSV
				final int secondLastIndex = pieces.length - 2;
				final int lastIndex = pieces.length - 1;
				final Window window = new Window(pieces[secondLastIndex]);
				window.setUptimeInSeconds(Integer.parseInt(pieces[lastIndex]));
				windows.add(window);
			}
			br.close();
		}
		catch (final Exception e) {
			System.err.println(e);
		}
		return windows;
	}
}
