/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package dataObjects;

/**
 * The Class Window.
 */
public class Window {

	/** The title. */
	private final String title;

	/** The uptime in seconds. */
	private int uptimeInSeconds = 0;

	/**
	 * Instantiates a new window.
	 *
	 * @param title
	 *            the title
	 */
	public Window(final String title) {
		this.title = title;
		this.uptimeInSeconds++;

	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Gets the uptime in seconds.
	 *
	 * @return the uptime in seconds
	 */
	public int getUptimeInSeconds() {
		return this.uptimeInSeconds;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.title == null ? 0 : this.title.hashCode());
		return result;
	}

	/**
	 * Increment uptime.
	 */
	public void incrementUptime() {
		this.setUptimeInSeconds(this.getUptimeInSeconds() + 1);
	}

	/**
	 * Sets the uptime in seconds.
	 *
	 * @param uptimeInSeconds
	 *            the new uptime in seconds
	 */
	public void setUptimeInSeconds(final int uptimeInSeconds) {
		this.uptimeInSeconds = uptimeInSeconds;
	}
}
