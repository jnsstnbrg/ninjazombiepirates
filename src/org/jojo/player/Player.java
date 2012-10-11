package org.jojo.player;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import pbox2d.PBox2D;
import processing.core.PApplet;
import processing.core.PConstants;

public class Player {

	public enum Direction {
		MS_LEFT, MS_RIGHT, MS_UP, MS_DOWN, MS_STOP;
	}

	private PApplet parent;
	private PBox2D box2d;

	private Body body;
	private float width, height;

	private Direction direction;

	public Player(PApplet parent, PBox2D box2d, float x, float y) {
		this.parent = parent;
		this.box2d = box2d;

		width = 16;
		height = 16;
		direction = Direction.MS_STOP;

		// Build Body
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(box2d.coordPixelsToWorld(x, y));
		body = box2d.createBody(bd);

		// Define a polygon (this is what we use for a rectangle)
		PolygonShape sd = new PolygonShape();
		float box2dW = box2d.scalarPixelsToWorld(width / 2);
		float box2dH = box2d.scalarPixelsToWorld(height / 2); // Box2D considers the width and height of a
		sd.setAsBox(box2dW, box2dH); // rectangle to be the distance from the
		// center to the edge (so half of what we
		// normally think of as width or height.)
		// Define a fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = sd;
		// Parameters that affect physics
		fd.density = 1;
		fd.friction = 0.3f;
		fd.restitution = 0.5f;

		// Attach Fixture to Body
		body.createFixture(fd);
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void update() {
		Vec2 vel = body.getLinearVelocity();
		switch (direction) {
		case MS_LEFT:
			vel.x = -5;
			break;
		case MS_STOP:
			vel.x = 0;
			vel.y = 0;
			break;
		case MS_RIGHT:
			vel.x = 5;
			break;
		case MS_UP:
			vel.y = 5;
			break;
		case MS_DOWN:
			vel.y = -5;
			break;
		}
		body.setLinearVelocity(vel);
	}

	public void display() {
		// We need the Body’s location and angle
		Vec2 pos = box2d.getBodyPixelCoord(body);
		float a = body.getAngle();

		parent.rectMode(PConstants.CENTER);
		parent.pushMatrix();
		parent.translate(pos.x, pos.y); // Using the Vec2 position and float angle to
		parent.rotate(-a); // translate and rotate the rectangle
		parent.fill(175);
		parent.stroke(0);
		parent.rect(0, 0, width, height);
		parent.popMatrix();
	}
}
