package org.jojo.game;

public class Position2D {

	public float x, y, width, height;
	
	public Position2D(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	@Override
	public String toString() {
		return "position: " + x + "," + y + " dimensions: " + width + "x" + height;
	}
	
}
