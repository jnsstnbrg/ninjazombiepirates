package org.jojo.obstacles;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jojo.game.DrawObject;
import org.jojo.game.Position2D;

import pbox2d.PBox2D;
import processing.core.PApplet;
import processing.core.PConstants;

public class Boundary extends DrawObject implements ObstacleInterface {

	private PApplet parent;
	
	private Position2D position;
	private Body b;

	public Boundary(PApplet parent, PBox2D box2d, float x, float y, float width, float height) {
		this.parent = parent;
		
		position = new Position2D(x, y, width, height);

		// Define the polygon
		PolygonShape sd = new PolygonShape();
		// Figure out the box2d coordinates
		float box2dW = box2d.scalarPixelsToWorld(width / 2);
		float box2dH = box2d.scalarPixelsToWorld(height / 2);
		// We're just a box
		sd.setAsBox(box2dW, box2dH);

		// Create the body
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		bd.position.set(box2d.coordPixelsToWorld(position.x, position.y));
		b = box2d.createBody(bd);

		// Attached the shape to the body using a Fixture
		b.createFixture(sd, 1);
	}
	
	@Override
	public void update() {
		
	}

	// Draw the boundary, if it were at an angle we'd have to do something fancier
	@Override
	public void draw() {
		parent.rectMode(PConstants.CENTER);
		parent.fill(0);
		parent.stroke(0);
		parent.rect(position.x, position.y, position.width, position.height);
	}
	
	public void setPosition(Position2D position) {
		this.position = position;
	}

	@Override
	public Vec2 getPosition() {
		return b.getPosition();
	}

}
