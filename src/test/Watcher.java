/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package test;

import java.util.ArrayList;
import java.util.List;

import io.Output;
import system.AccessWin32;

/**
 * The Class Watcher.
 */
public class Watcher implements Runnable {

	/** The paused. */
	boolean paused = false;

	/** The pause lock. */
	private Object pauseLock = new Object();

	/** The telescope. */
	AccessWin32 telescope = new AccessWin32();

	/** The windows. */
	private List<dataObjects.Window> windows = new ArrayList<>();

	/**
	 * Gets the lock.
	 *
	 * @return the lock
	 */
	public Object getLock() {
		return this.pauseLock;
	}

	/**
	 * Gets the windows.
	 *
	 * @return the windows
	 */
	public List<dataObjects.Window> getWindows() {
		return this.windows;
	}

	/**
	 * Observe.
	 */
	public void observe() {
		long time = System.currentTimeMillis();
		final Output output = new Output();
		while (true) {
			synchronized (this.getLock()) {
				if (this.paused) {
					try {
						this.pauseLock.wait();
					}
					catch (final Exception e) {
						System.err.println(e);
					}
				}
				final String currWindowTitle = this.telescope.getActiveWindowTitle();
				boolean found = false;
				for (final dataObjects.Window window : this.getWindows()) {
					if (window.getTitle().equals(currWindowTitle)) {
						window.incrementUptime();
						found = true;
						break;
					}
				}
				if (!found) {
					this.getWindows().add(new dataObjects.Window(currWindowTitle));
				}
				this.sleep(1000);
				// update CSV every 10 seconds or so.
				if (time + 10000 >= time) {
					time = System.currentTimeMillis();
					output.saveCSV(this.windows);
				}
			}
		}
	}

	/**
	 * Pause.
	 */
	public void pause() {
		// you may want to throw an IllegalStateException if !running
		this.paused = true;
	}

	/**
	 * Resume.
	 */
	public void resume() {
		synchronized (this.pauseLock) {
			this.paused = false;
			this.pauseLock.notifyAll(); // Unblocks thread
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		this.observe();
	}

	/**
	 * Sets the lock.
	 *
	 * @param lock
	 *            the new lock
	 */
	public void setLock(final Object lock) {
		this.pauseLock = lock;
	}

	/**
	 * Sets the windows.
	 *
	 * @param windows
	 *            the new windows
	 */
	public void setWindows(final List<dataObjects.Window> windows) {
		this.windows = windows;
	}

	/**
	 * Sleep.
	 *
	 * @param ms
	 *            the ms
	 */
	public void sleep(final long ms) {
		try {
			Thread.sleep(ms);
		}
		catch (final InterruptedException e) {
			System.err.println(e);
		}
	}
}
