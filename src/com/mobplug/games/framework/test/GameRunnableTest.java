/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mobplug.games.framework.test;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import com.mobplug.games.framework.BaseGame;
import com.mobplug.games.framework.BaseGameRunnable;
import com.mobplug.games.framework.game2d.BaseGameRenderer2D;
import com.mobplug.games.framework.interfaces.GameRunnable;
public class GameRunnableTest  {
	static class TestRenderer extends BaseGameRenderer2D<TestGame> {
	    private int rendercount = 0;
		public TestRenderer(TestGame game, JFrame jFrame, boolean fullscreen) {
			super(game, jFrame, fullscreen);
		}

		@Override
		protected void render(Graphics g) {
	        rendercount++;
	        g.setColor(Color.WHITE);
	        g.fillRect(0, 0, width, height);
	        g.setColor(Color.red);
	        g.drawString(rendercount + " : " + game.getUpdateCount(), 100, 100);			
		}		
	}
	
	static class TestGame extends BaseGame {
		private static final long serialVersionUID = 1L;
		
		private int updatecount = 0;
	    
		@Override
		public int getUpdateCount() {
			return updatecount;
		}

		@Override
		public void update(long gameTime) {
			updatecount++;
		}
			
	}
	
    public static void main(String[] args) {
        JFrame jframe = new JFrame("Testing");
        jframe.setSize(800, 600);
        TestGame game = new TestGame();
        TestRenderer renderer = new TestRenderer(game, jframe, false);
        GameRunnable gameRunnable = new BaseGameRunnable<TestGame>(renderer, game);
        gameRunnable.start();
    }

}
