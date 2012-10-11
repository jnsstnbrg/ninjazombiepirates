package org.jojo.calculations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class CalculationThread implements Runnable {

	private Thread t;
	private int sleepTime;
	private ArrayList<UpdateListener> updateListeners = new ArrayList<UpdateListener>();

	public CalculationThread(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public void addUpdateListener(Object object) {
		try {
			Method method = object.getClass().getMethod("update", new Class[] {});
			updateListeners.add(new UpdateListener(object, method));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.err.println("No method called update in " + object.getClass().getSimpleName());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Thread me = Thread.currentThread();
		while (t == me) {

			for (UpdateListener ul : updateListeners) {
				try {
					ul.getMethod().invoke(ul.getObject(), new Object[] {});
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
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
