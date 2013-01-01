package org.hyperion.rs2.model;

/**
 * Represents a single graphic request.
 * @author Graham Edgecombe
 *
 */
public class Graphic {
	
	/**
	 * Creates an graphic with no delay.
	 * @param id The id.
	 * @return The new graphic object.
	 */
	public static Graphic create(int id, int height) {
		return create(id, 0, height);
	}
	
	/**
	 * Creates a graphic.
	 * @param id The id.
	 * @param delay The delay.
	 * @return The new graphic object.
	 */
	public static Graphic create(int id, int delay, int height) {
		return new Graphic(id, delay, height);
	}
	
	/**
	 * The id.
	 */
	private int id;
	
	/**
	 * The delay.
	 */
	private int delay;
	
	/**
	 * The map height of the graphic.
	 */
	private int height;
	
	/**
	 * Creates a graphic.
	 * @param id The id.
	 * @param delay The delay.
	 */
	private Graphic(int id, int delay, int height) {
		this.id = id;
		this.delay = delay;
		this.height = height;
	}
	
	/**
	 * Gets the id.
	 * @return The id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the delay.
	 * @return The delay.
	 */
	public int getDelay() {
		return delay;
	}
	
	
	/**
	 * Gets the map height of the graphics.
	 * @return
	 */
	public int getHeight() {
		return height;
	}

}
