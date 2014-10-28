package com.mobplug.games.framework.game2d;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.mobplug.games.framework.interfaces.Game;
import com.mobplug.games.framework.interfaces.GameRenderer;

/**
 * Handles rendering on a Swing JFrame
 * @author andreban
 *
 * @param <G>
 */
public abstract class BaseGameRenderer2D<G extends Game> implements GameRenderer<G>{
    private JFrame myJFrame;
    private boolean fullscreen;
    private GraphicsDevice graphicsDevice;
    private BufferStrategy bufferStrategy;

    protected G game;
    protected int width;
    protected int height;
    
    public BaseGameRenderer2D(G game, 
    		JFrame jFrame, boolean fullscreen) {
        this.myJFrame = jFrame;
        this.fullscreen = fullscreen;
        this.game = game;
        init();    	
    }
    
    private void init() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();

        //we are using active rendering!!
        myJFrame.setIgnoreRepaint(true);

        //TODO should we use undecorated widow?
        myJFrame.setUndecorated(true);
        
        //TODO should we allow resizing?
        myJFrame.setResizable(false);
        if (fullscreen) {
            if (graphicsDevice.isFullScreenSupported()) {

                graphicsDevice.setFullScreenWindow(myJFrame);
            } else {
                System.err.println("Fullscreen not Supported!");
                myJFrame.setVisible(true);//fallback to window mode
            }
        } else {
            myJFrame.setVisible(true);
        }

        width = myJFrame.getBounds().width;
        height = myJFrame.getBounds().height;

        initBufferStrategy();
    }

    private void initBufferStrategy() {
        try {
            EventQueue.invokeAndWait(new Runnable(){

                public void run() {
                    myJFrame.createBufferStrategy(2);
                }
            });
        } catch(Exception ex) {
            ex.printStackTrace();
            System.err.println("Error Creating Buffer Strategy");
            System.exit(1);
        }

        try {
            Thread.sleep(500);
        } catch(InterruptedException ex) {
        }
        bufferStrategy = myJFrame.getBufferStrategy();
    }

    protected abstract void render(Graphics g);    
    
	@Override
	public void render() {
        Graphics g = bufferStrategy.getDrawGraphics();
        render(g);
        g.dispose();
        if (!bufferStrategy.contentsLost())
            bufferStrategy.show();
        else
            System.err.println("Content Lost");

        Toolkit.getDefaultToolkit().sync();
	}
}
