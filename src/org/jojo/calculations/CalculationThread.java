package org.jojo.calculations;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.jojo.game.DrawObject;

public class CalculationThread implements Runnable {

	private Thread t;
	private int sleepTime;
	private ArrayList<DrawObject> drawObjects = new ArrayList<DrawObject>();

	public CalculationThread(int sleepTime, ArrayList<DrawObject> drawObjects) {
		this.sleepTime = sleepTime;
		this.drawObjects = drawObjects;
	}

	@Override
	public void run() {
		Thread me = Thread.currentThread();

		while (t == me) {
			for (int i = 0; i < drawObjects.size(); i++) {
				DrawObject d = drawObjects.get(i);
				try {
					d.getClass().getMethod("update", new Class[] {}).invoke(d, new Object[] {});
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		t = null;
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

	public void stop() {
		if (t != null) {
			t.interrupt();
		}
		t = null;
	}

	public void dispose() {
		stop();
	}
}
