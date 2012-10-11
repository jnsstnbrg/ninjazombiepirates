package org.jojo.game;

import java.util.ArrayList;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class Jojo extends PApplet {

	public void setup() {
		size(500, 500, P3D);
	}
	
	public void draw() {
		
	}

	public static void main(String args[]) {
		PApplet.println(args);
		ArrayList<String> argList = new ArrayList<String>();
		argList.add("--location=0,0");
		argList.add("--bgcolor=" + 000000);
		argList.add("--hide-stop");
		argList.add("org.jojo.game.Jojo");
		PApplet.main(argList.toArray(new String[0]));
	}

}