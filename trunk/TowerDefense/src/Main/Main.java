package Main;
import java.awt.Dimension;

import com.golden.gamedev.GameLoader;


public class Main {
	
	public static void main(String[] args) {
		final GameLoader g = new GameLoader();
		g.setup(new Game(), new Dimension(800,600), false);
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				g.start(); //blocks until game is finished
			}
		});
		t.setPriority(10);
		t.start();
		while(g.getGame().isRunning()) { //but we want that ^^^
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}
}
