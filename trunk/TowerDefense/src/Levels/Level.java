package Levels;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.Game;
import Monsters.Monster;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.Timer;

public abstract class Level extends Sprite {
	private static final long serialVersionUID = -8770973762571139572L;
	Game g;
	Point[] instructions;
	BufferedImage levelBackground;
	
	ArrayList<Monster> waveQueue;
	int waveIndex = 0;
	int waveSize;
	
	Timer addingRate;
	
	DoublePoint[] directionAndSpeeds;
	
	public Point[] getMovementInstructions() {
		return instructions;
	}
	
	public int getMovementInstructionsSize() {
		return instructions.length;
	}
	
	public void setInstructions(Point[] ps) {
		instructions = ps;
		waveSize = ps.length;
	}
	
	public void setBackgroundImage(BufferedImage bf) {
		levelBackground = bf;
	}
	
	public BufferedImage getBackgroundImage() {
		return levelBackground;
	}
	
	public Game getGame() {
		return g;
	}
	
	public void render(Graphics2D g) {
		g.drawImage(levelBackground, 0,0, null);
	}
	
	public void setWaveQueue(ArrayList<Monster> ls) {
		waveQueue = ls;
		waveSize = ls.size();
	}
	
	public ArrayList<Monster> getWaveQueue() {
		return waveQueue;
	}
	
	public void setSpawnRate(int milliseconds) {
		addingRate = new Timer(milliseconds);
	}
	
	@Override
	public void update(long time) {
		if(addingRate.action(time) && waveIndex<waveSize-1) {
			g.getEnviro().addMonster(waveQueue.get(waveIndex++));
		}
	}

	public int getWaveSize() {
		return waveSize;
	}

	public int getWaveRemaining() {
		return waveSize-waveIndex;
	}
	
	public void setGame(Game g) {
		this.g = g;
	}
	
	public void setDirectionAndSpeeds(DoublePoint[] p) {
		directionAndSpeeds = p;
	}

	public DoublePoint getDirection(int movementInstructionIndex) {
		try {
			System.out.println(directionAndSpeeds[movementInstructionIndex]);
			return directionAndSpeeds[movementInstructionIndex];
		} catch (Exception e) { return null; }
	}
	
	
	
	

	public class DoublePoint {
		public double x;
		public double y;
		
		public DoublePoint(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
}
