package engineRunner;

import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class MainGameLoop {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		//Open GL Expects verticies to be defined counter clockwise
		  float[] vertices = {
				    -0.5f, 0.5f, 0f, //V0
				    -0.5f, -0.5f, 0f, //V1
				    0.5f, -0.5f, 0f, //V2
				    0.5f, 0.5f, 0f //V4
				  };
		  
		  int[] indices = {
				  0,1,3, //Top left triangle
				  3,1,2 //Bottom right triangle
		  };
		
		  RawModel model = loader.loadToVAO(vertices,indices);
		  
		while(!Display.isCloseRequested()){
			//Main loop here -HungryBoy02
			
			renderer.prepere();
			
			//render
			
			renderer.render(model);
			DisplayManager.updateDisplay();
			
		}
		
		//When close is requested -HungryBoy02
		
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}
