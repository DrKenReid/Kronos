/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import dataObjects.Window;

/**
 * The Class PieChart.
 */
public class PieChart extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates the chart.
	 *
	 * @param windows
	 *            the windows
	 * @return the j free chart
	 */
	private static JFreeChart createChart(final List<Window> windows) {
		final JFreeChart chart = ChartFactory.createPieChart("Kronus - Window Data", // chart title
				PieChart.createDataset(windows), // data
				true, // include legend
				true, false);

		return chart;
	}

	/**
	 * Creates the dataset.
	 *
	 * @param windows
	 *            the windows
	 * @return the pie dataset
	 */
	private static PieDataset createDataset(final List<Window> windows) {
		// More than 10 items looks hideous, so only show 10.
		final List<Window> localList = new ArrayList<>(windows);
		Collections.sort(windows, (w1, w2) -> {
			return ((Integer) w1.getUptimeInSeconds()).compareTo(w2.getUptimeInSeconds());
		});
		List<Window> tenItemsInList = new ArrayList<>();
		if (localList.size() >= 10) {
			tenItemsInList = localList.subList(0, 10);
		}
		else {
			tenItemsInList = localList;
		}
		final DefaultPieDataset dataset = new DefaultPieDataset();
		for (final Window window : tenItemsInList) {
			dataset.setValue(window.getTitle(), window.getUptimeInSeconds());
		}
		return dataset;
	}

	/**
	 * Instantiates a new pie chart.
	 *
	 * @param windows
	 *            the windows
	 */
	public PieChart(final List<Window> windows) {
		super("Kronos - Window Data");
		final JFreeChart chart = PieChart.createChart(windows);
		SwingUtilities.invokeLater(() -> {
			final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			final int width = (int) screenSize.getWidth();
			final int height = (int) screenSize.getHeight();
			this.setSize(width, height);
			this.setLocationRelativeTo(null);
			// super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.setVisible(true);
		});
		final ChartPanel panel = new ChartPanel(chart);
		this.setContentPane(panel);
	}
}
