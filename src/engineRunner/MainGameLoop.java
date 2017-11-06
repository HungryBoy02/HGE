package engineRunner;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;

public class MainGameLoop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DisplayManager.createDisplay();
		
		while(!Display.isCloseRequested()){
			//Main loop here -HungryBoy02
			DisplayManager.updateDisplay();
			
		}
		//When close is requested -HungryBoy02
		DisplayManager.closeDisplay();
		
	}

}
