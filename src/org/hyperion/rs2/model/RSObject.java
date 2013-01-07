package org.hyperion.rs2.model;

public final class RSObject {
	
	private final int id, type;
	
	private final Location location;
	
	public RSObject(int id, Location location, int type) {
		this.id = id;
		this.location = location;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	
	public Location getLocation() {
		return location;
	}

	public int getType() {
		return type;
	}
}
