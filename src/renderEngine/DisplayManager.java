package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;

	public static void createDisplay() {
		// Attr Setup -HungryBoy02
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("The Secret Weapon [ALPHA 0.0.0.0.4]");
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);

	}

	public static void updateDisplay() {
		// Render the frames, FPS Cap is 120 (line 14) -HungryBoy02
		Display.sync(FPS_CAP);
		Display.update();

	}

	public static void closeDisplay() {
		// Real simple here -HungryBoy02
		Display.destroy();

	}

}
