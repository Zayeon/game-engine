package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class DisplayManager {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int FPS_CAP = 120;

	private static long lastFrameTime;
	private static float delta;
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3, 3)
				.withForwardCompatible(true)
				.withProfileCore(true);
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Test");
			
			Display.create(new PixelFormat().withDepthBits(24).withSamples(4), attribs);
            GL11.glEnable(GL13.GL_MULTISAMPLE);
        } catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime)/1000f;
		lastFrameTime = currentFrameTime;
	}

	public static float getFrameTimeSeconds(){

		return delta;
	}
	
	public static void closeDisplay() {
		Display.destroy();
	}

	private static long getCurrentTime(){
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
}
