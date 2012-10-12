package org.jojo.obstacles;

import org.jojo.game.DrawObject;
import org.jojo.game.Position2D;

public interface ObstacleInterface extends DrawObject {

	public void setPosition(Position2D position);
	public Position2D getPosition();
	
}
