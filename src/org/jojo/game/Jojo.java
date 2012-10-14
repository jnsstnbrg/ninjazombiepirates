package org.jojo.game;

import java.util.ArrayList;

import org.jojo.calculations.CalculationThread;
import org.jojo.obstacles.Boundary;
import org.jojo.obstacles.ObstacleInterface;
import org.jojo.player.Player;
import org.jojo.player.Player.Direction;

import pbox2d.PBox2D;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.KeyEvent;

@SuppressWarnings("serial")
public class Jojo extends PApplet {

	private CalculationThread calculationThread;
	private ArrayList<DrawObject> drawObjects = new ArrayList<DrawObject>();

	private PBox2D b2world;
	private PFont font;

	private ArrayList<ObstacleInterface> obstacles = new ArrayList<ObstacleInterface>();
	public static Player player;

	@Override
	public void init() {
		frame.setResizable(true);
		super.init();
	}

	@Override
	public void setup() {
		size(800, 600, OPENGL);
		font = createFont("Monaco", 10);

		// Set up world
		b2world = new PBox2D(this);
		b2world.createWorld();
		b2world.setGravity(0, 0);

		// Add test obstacle
		obstacles.add(new Boundary(this, b2world, 0, 0, 100, 100));
		obstacles.add(new Boundary(this, b2world, width - 100, height - 100, 100, 100));

		for (ObstacleInterface o : obstacles) {
			drawObjects.add((DrawObject) o);
		}
		drawObjects.add(player = new Player(this, b2world, width / 2, 100));

		calculationThread = new CalculationThread(16, drawObjects);
		calculationThread.start();

		registerMethod("keyEvent", this);
	}

	@Override
	public void draw() {
		background(0);

		b2world.step();

		for (ObstacleInterface o : obstacles) {
			if (o.getPosition().x + (100 * 2) > width || o.getPosition().x + 100 < 0) {
				obstacles.remove(o);
			}
		}

		for (DrawObject d : drawObjects) {
			d.draw();
		}

		camera(player.getPosition().x, player.getPosition().y, (float) ((height / 2.0) / Math.tan(PI * 30.0 / 180.0)), player.getPosition().x, player.getPosition().y, 0f, 0f, 1f, 0f);

		fill(255);
		textFont(font);
		text(frameRate, width - 45, font.getSize());
	}

	public void keyEvent(KeyEvent e) {
		switch (e.getAction()) {
		case KeyEvent.PRESSED:
			switch (e.getKeyCode()) {
			case java.awt.event.KeyEvent.VK_SPACE:
				player.setDirection(Direction.MS_STOP);
				break;
			case java.awt.event.KeyEvent.VK_A:
				player.setDirection(Direction.MS_LEFT);
				break;
			case java.awt.event.KeyEvent.VK_W:
				player.setDirection(Direction.MS_UP);
				break;
			case java.awt.event.KeyEvent.VK_D:
				player.setDirection(Direction.MS_RIGHT);
				break;
			case java.awt.event.KeyEvent.VK_S:
				player.setDirection(Direction.MS_DOWN);
				break;
			}
			break;
		}
	}

	public static void main(String args[]) {
		PApplet.println(args);
		ArrayList<String> argList = new ArrayList<String>();
		argList.add("--location=0,0");
		argList.add("--bgcolor=" + "FFFFFF");
		argList.add("--hide-stop");
		argList.add("org.jojo.game.Jojo");
		PApplet.main(argList.toArray(new String[0]));
	}

}