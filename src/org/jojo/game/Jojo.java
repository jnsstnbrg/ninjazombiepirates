package org.jojo.game;

import java.util.ArrayList;

import org.jojo.calculations.CalculationThread;
import org.jojo.obstacles.Boundary;
import org.jojo.player.Player;
import org.jojo.player.Player.Direction;

import pbox2d.PBox2D;
import processing.core.PApplet;
import processing.core.PFont;
import processing.event.KeyEvent;

@SuppressWarnings("serial")
public class Jojo extends PApplet {

	private CalculationThread calculationThread;
	
	private PBox2D box2d;
	private PFont font;
	private ArrayList<Boundary> boundaries;
	private Player player;

	@Override
	public void init() {
		frame.setResizable(true);
		super.init();
	}

	@Override
	public void setup() {
		size(500, 500);
		background(255);
		font = createFont("Courier", 10);
		
		calculationThread = new CalculationThread(16);
		calculationThread.start();
	
		box2d = new PBox2D(this);
		box2d.createWorld();
		box2d.setGravity(0, 0);
		
		boundaries = new ArrayList<Boundary>();
		boundaries.add(new Boundary(this, box2d, width / 2, height / 2, 100, 100));

		player = new Player(this, box2d, width / 2, 100);
		calculationThread.addUpdateListener(player);

		registerMethod("keyEvent", this);
	}

	@Override
	public void draw() {
		background(255);

		box2d.step();

		for (Boundary b : boundaries) {
			b.display();
		}

		player.display();

		fill(0);
		textFont(font);
		text(frameRate, width - 45, font.getSize());
	}

	public void keyEvent(KeyEvent e) {
		switch (e.getAction()) {
		case KeyEvent.PRESSED:
			switch (e.getKeyCode()) {
			case 32:
				// space
				player.setDirection(Direction.MS_STOP);
				break;
			case 37:
				// left
				player.setDirection(Direction.MS_LEFT);
				break;
			case 38:
				// up
				player.setDirection(Direction.MS_UP);
				break;
			case 39:
				// right
				player.setDirection(Direction.MS_RIGHT);
				break;
			case 40:
				// down
				player.setDirection(Direction.MS_DOWN);
				break;
			}
			break;
		case KeyEvent.RELEASED:

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